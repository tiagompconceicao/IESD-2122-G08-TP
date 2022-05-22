package isos.tp1.isyiesd.cesvector.servectorcli;

import com.google.protobuf.Empty;
import java.util.logging.Level;
import java.util.logging.Logger;
import lockManager.LockRequest;
import lockManager.ResourceElement;
import lockManager.UnlockRequest;
import transactionManagerTX.Result;
import transactionManagerTX.Transaction;
import vector.ReadMessage;
import vector.WriteMessage;

public class VectorClient3 {
    private static final int READ_MODE = 1;
    private static final int WRITE_MODE = 2;

    private static String coordinatorIP = "localhost";
    private static int coordinatorPort = 9000;

    private static final Logger logger = Logger.getLogger(VectorClient3.class.getName());

    public static void main(String[] args) {
        if (args.length == 1) {
            coordinatorPort = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            coordinatorIP = args[0];
            coordinatorPort = Integer.parseInt(args[1]);
        }
        Transaction transaction = null;
        try {
            ConnectionManager cm = new ConnectionManager(coordinatorIP, coordinatorPort, logger);
            ICoordinator.Result result = cm.coordinatorProxy.checkInvariant(Empty.newBuilder().build());
            if(result.getStatus()) {
                logger.info("Invariant is Valid!");
            } else {
                logger.info("Invariant is Not Valid!");
            }
            //Creates a transaction
            transaction = cm.transactionManagerProxy.txBegin(Empty.newBuilder().build());

            System.out.println("Transaction created with id: " + transaction.getTid());

            int tid = transaction.getTid();
            LockRequest locksRequest = LockRequest.newBuilder().setTid(tid)
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(0).setLockType(READ_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(0).setLockType(WRITE_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(2).setLockType(READ_MODE).build())
              .addElements(ResourceElement.newBuilder().setServerID(1).setElementIndex(2).setLockType(WRITE_MODE).build())
              .build();

            //Get all necessary locks
            logger.info("Transaction: " + transaction.getTid() + " || " + "Waiting for locks...");
            cm.lockManagerProxy.getLocks(locksRequest);
            logger.info("Transaction: " + transaction.getTid() + " || " + "Locks acquired!");

            int v, res;
            int x = 100;

            //equivalent to port.read(0);
            int y = cm.vectorServices.get("VectorService_1")
              .read(ReadMessage.newBuilder().setTid(transaction.getTid()).setPos(0).build()).getValue();
            res = y - x;
            logger.info("Transaction: " + transaction.getTid() + " || " + "Read value: " + y + " from VectorService_1, index: 0");

            Thread.sleep(10000);

            //equivalent to port.write(0, res);
            cm.vectorServices.get("VectorService_1")
              .write(WriteMessage.newBuilder().setTid(transaction.getTid()).setPos(0).setValue(res).build());
            logger.info("Transaction: " + transaction.getTid() + " || " + "Wrote value: " + res + " in VectorService_1, index: 0");

            Thread.sleep(10000);

            //equivalent to port.read(2);
            v = cm.vectorServices.get("VectorService_2")
              .read(ReadMessage.newBuilder().setTid(transaction.getTid()).setPos(2).build()).getValue();
            res = v + x;
            logger.info("Transaction: " + transaction.getTid() + " || " + "Read value: " + v + " from VectorService_2, index: 2");

            Thread.sleep(10000);

            //equivalent to port.write(2, res);
            cm.vectorServices.get("VectorService_2")
              .write(WriteMessage.newBuilder().setTid(transaction.getTid()).setPos(2).setValue(res).build());
            logger.info("Transaction: " + transaction.getTid() + " || " + "Wrote value: " + res + " in VectorService_2, index: 2");

            //Commit
            Result r = cm.transactionManagerProxy.txCommit(transaction);
            if (r.getStatus()) {
                logger.info("Transaction: " + transaction.getTid() + " || " + "Committed Successfully!");
            } else {
                logger.info("Transaction: " + transaction.getTid() + " || " + "Rolled Back due to Error!");
            }
            //Unlock all resources locked
            cm.lockManagerProxy.unlock(UnlockRequest.newBuilder().setTid(transaction.getTid()).build());
            logger.info("Transaction: " + transaction.getTid() + " || " + "Locks Released.");

            result = cm.coordinatorProxy.checkInvariant(Empty.newBuilder().build());
            if(result.getStatus()) {
                logger.info("Invariant is Valid!");
            } else {
                logger.info("Invariant is Not Valid!");
            }
            cm.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
            if(transaction != null) {
                logger.log(Level.SEVERE, "Transaction: " + transaction.getTid() + " || " + "Error:" + ex.getMessage());
            }
        }
    }
}
