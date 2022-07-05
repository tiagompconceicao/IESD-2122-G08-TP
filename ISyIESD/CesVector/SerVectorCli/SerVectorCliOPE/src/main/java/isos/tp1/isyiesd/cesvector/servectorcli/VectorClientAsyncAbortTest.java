package isos.tp1.isyiesd.cesvector.servectorcli;

import com.google.protobuf.Empty;
import java.util.logging.Level;
import java.util.logging.Logger;
import lockManager.LockRequest;
import lockManager.ResourceElement;
import transactionManagerTX.Transaction;

public class VectorClientAsyncAbortTest {
    private static final int READ_MODE = 1;
    private static final int WRITE_MODE = 2;

    private static String nodeIP = "localhost";
    private static final int registryPort = 30961;

    private static final Logger logger = Logger.getLogger(VectorClientAsyncAbortTest.class.getName());

    public static void main(String args[]) {
        if (args.length == 1) {
            nodeIP = args[0];
        }

        Transaction transaction = null;
        try {
            ConnectionManager cm = new ConnectionManager(nodeIP, registryPort, logger);

            transaction = cm.transactionManagerProxy.txBegin(Empty.newBuilder().build());

            System.out.println("Transaction created with id: " + transaction.getTid());

            int tid = transaction.getTid();

            //Get all necessary locks ----------------------------------
            logger.info("Transaction: " + transaction.getTid() + " || " + "Waiting for locks...");
            LockRequest locksRequest = LockRequest.newBuilder().setTid(tid)
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(0).setLockType(READ_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(0).setLockType(WRITE_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(2).setLockType(READ_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(2).setLockType(WRITE_MODE).build())
              .build();
            ClientObserver replyStreamObserver = new ClientObserver();
            cm.lockManagerProxyAsync.getLocks(locksRequest, replyStreamObserver);
            Thread.sleep(1000);
            cm.lockManagerProxy.abortRequest(locksRequest);
            while (!replyStreamObserver.isCompleted()) {
                try {
                    logger.info("DEBUGG: Waiting for Async return (Locks).");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(!replyStreamObserver.IsSuccessful()) {
                logger.info("Transaction: " + transaction.getTid() + " || " + "Acquiring Locks Aborted!");
                cm.transactionManagerProxy.txRollback(transaction);
                cm.shutdown();
            } else {
                logger.info("Transaction: " + transaction.getTid() + " || " + "Acquired Locks!");
                cm.transactionManagerProxy.txRollback(transaction);
                cm.shutdown();
            }

        }  catch (Exception ex) {
            ex.printStackTrace();
            if(transaction != null) {
                logger.log(Level.SEVERE, "Transaction: " + transaction.getTid() + " || " + "Error:" + ex.getMessage());
            }
        }
    }
}
