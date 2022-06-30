package isos.tp1.isyiesd.cesvector.servectorcli;

import com.google.protobuf.Empty;
import java.util.logging.Level;
import java.util.logging.Logger;
import lockManager.LockRequest;
import lockManager.ResourceElement;
import transactionManagerTX.Transaction;

public class VectorClientAbortTest {
    private static final int READ_MODE = 1;
    private static final int WRITE_MODE = 2;

    private static String coordinatorIP = "localhost";
    private static int coordinatorPort = 9000;

    private static final Logger logger = Logger.getLogger(VectorClientAbortTest.class.getName());

    public static void main(String args[]) {
        if (args.length == 1) {
            coordinatorPort = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            coordinatorIP = args[0];
            coordinatorPort = Integer.parseInt(args[1]);
        }

        Transaction transaction = null;
        try {
            ConnectionManager cm = new ConnectionManager(coordinatorIP, coordinatorPort, logger);

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

            logger.info("Transaction: " + transaction.getTid() + " || " + "Acquiring Locks Aborted!");
            cm.transactionManagerProxy.txRollback(transaction);
            cm.shutdown();
            return;

        }  catch (Exception ex) {
            ex.printStackTrace();
            if(transaction != null) {
                logger.log(Level.SEVERE, "Transaction: " + transaction.getTid() + " || " + "Error:" + ex.getMessage());
            }
        }
    }
}
