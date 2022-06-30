package isos.tp1.isyiesd.cesvector.servectorcli;

import com.google.protobuf.Empty;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lockManager.LockRequest;
import lockManager.ResourceElement;
import lockManager.UnlockRequest;
import transactionManagerTX.Result;
import transactionManagerTX.Transaction;
import vector.ReadMessage;
import vector.WriteMessage;

/**
 * Hello world!
 */
public class VectorClient2 {

    private static final int READ_MODE = 1;
    private static final int WRITE_MODE = 2;

    private static String coordinatorIP = "localhost";
    private static final int coordinatorPort = 9000;

    private static final Logger logger = Logger.getLogger(VectorClient2.class.getName());
    private static ConnectionManager cm;

    private static void threadMethod() {
        String name = Thread.currentThread().getName();
        Transaction transaction = null;
        try {
            //Creates a transaction
            transaction = cm.transactionManagerProxy.txBegin(Empty.newBuilder().build());

            System.out.println("Transaction created with id: " + transaction.getTid());
            logger.info("Transaction "+transaction.getTid()+" Running on thread '{"+name+"}'");

            int tid = transaction.getTid();
            LockRequest locksRequest = LockRequest.newBuilder().setTid(tid)
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(0).setLockType(READ_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(0).setLockType(WRITE_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(2).setLockType(READ_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(2).setLockType(WRITE_MODE).build())
              .build();

            //Get all necessary locks
            logger.info("Transaction: "+transaction.getTid()+" || "+"Waiting for locks...");
            cm.lockManagerProxy.getLocks(locksRequest);
            logger.info("Transaction: "+transaction.getTid()+" || "+"Locks acquired!");

            int v, res;
            int x = 100;

            //equivalent to port.read(0);
            int y = cm.vectorServices.get("VectorService_1")
              .read(ReadMessage.newBuilder().setTid(transaction.getTid()).setPos(0).build()).getValue();
            res = y - x;
            logger.info("Transaction: "+transaction.getTid()+" || "+"Read value: "+y+" from VectorService_1, index: 0");

            Thread.sleep(10000);

            //equivalent to port.write(0, res);
            cm.vectorServices.get("VectorService_1")
              .write(WriteMessage.newBuilder().setTid(transaction.getTid()).setPos(0).setValue(res).build());
            logger.info("Transaction: "+transaction.getTid()+" || "+"Wrote value: "+res+" in VectorService_1, index: 0");

            Thread.sleep(10000);

            //equivalent to port.read(2);
            v = cm.vectorServices.get("VectorService_2")
              .read(ReadMessage.newBuilder().setTid(transaction.getTid()).setPos(2).build()).getValue();
            res = v + x;
            logger.info("Transaction: "+transaction.getTid()+" || "+"Read value: "+v+" from VectorService_2, index: 2");

            Thread.sleep(10000);

            //equivalent to port.write(2, res);
            cm.vectorServices.get("VectorService_2")
              .write(WriteMessage.newBuilder().setTid(transaction.getTid()).setPos(2).setValue(res).build());
            logger.info("Transaction: "+transaction.getTid()+" || "+"Wrote value: "+res+" in VectorService_2, index: 2");

            //Commit
            Result r = cm.transactionManagerProxy.txCommit(transaction);
            if(r.getStatus()) {
                logger.info("Transaction: "+transaction.getTid()+" || "+"Committed Successfully!");
            } else {
                logger.info("Transaction: "+transaction.getTid()+" || "+"Rolled Back due to Error!");
            }
            //Unlock all resources locked
            cm.lockManagerProxy.unlock(UnlockRequest.newBuilder().setTid(transaction.getTid()).build());
            logger.info("Transaction: "+transaction.getTid()+" || "+"Locks Released.");

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Transaction: "+transaction.getTid()+" || "+"Error:" + ex.getMessage());
        }

    }

    public static void main(String[] args) throws InterruptedException {
        if(args.length == 1) {
            coordinatorIP = args[0];
        }
        cm = new ConnectionManager(coordinatorIP, coordinatorPort, logger);
        // We can create multiples threads referencing the same method
        List<Thread> ths = Arrays.asList(
          new Thread(VectorClient2::threadMethod),
          new Thread(VectorClient2::threadMethod)
        );

        ths.forEach(Thread::start);
        logger.info("New threads started, waiting for them to end");

        ths.forEach(VectorClient2::uninterruptibleJoin);
        logger.info("New threads ended, finishing test");
        /*IRegistry.Result r = cm.coordinatorProxy.checkInvariant(Empty.newBuilder().build());
        if(r.getStatus()) {
            logger.info("Invariant is Valid!");
        } else {
            logger.info("Invariant is Not Valid!");
        }*/
        cm.shutdown();
    }

    public static void uninterruptibleJoin(Thread th) {
        while(true){
            try {
                th.join();
                return;
            } catch (InterruptedException e) {
                logger.info("Ignoring InterruptionException on join");
            }
        }
    }
}