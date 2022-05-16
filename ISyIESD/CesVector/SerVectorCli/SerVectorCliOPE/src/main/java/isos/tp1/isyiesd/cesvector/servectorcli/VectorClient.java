package isos.tp1.isyiesd.cesvector.servectorcli;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lockManager.ILockManagerGrpc;
import lockManager.LockRequest;
import lockManager.UnlockRequest;
import transactionManagerTX.ITransactionManagerTXGrpc;
import transactionManagerTX.Transaction;
import vector.IVectorGrpc;
import vector.ReadMessage;
import vector.VectorResponse;
import vector.WriteMessage;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class VectorClient {

    private static String vectorServerIP = "localhost";
    private static final int vectorServerPort = 9000;

    private static String tmServerIP = "localhost";
    private static final int tmServerPort = 9001;
    private static String lmServerIP = "localhost";
    private static final int lmServerPort = 9002;
    public static final Logger logger = Logger.getLogger(VectorClient.class.getName());


    public static void main(String[] args) throws InterruptedException {

        ManagedChannel rmChannel=null;
        ManagedChannel tmChannel=null;
        ManagedChannel lmChannel=null;
        if (args.length == 1) vectorServerIP = args[0];
        try {

            //Setup connection to transaction manager
            tmChannel = ManagedChannelBuilder.forAddress(tmServerIP, tmServerPort)
                    .usePlaintext()
                    .build();
            ITransactionManagerTXGrpc.ITransactionManagerTXBlockingStub tmStub = ITransactionManagerTXGrpc.newBlockingStub(tmChannel);

            //Setup connection to resource manager
            rmChannel = ManagedChannelBuilder.forAddress(vectorServerIP, vectorServerPort)
                    .usePlaintext()
                    .build();
            IVectorGrpc.IVectorBlockingStub rmStub = IVectorGrpc.newBlockingStub(rmChannel);


            //Setup connection to lock manager
            lmChannel = ManagedChannelBuilder.forAddress(lmServerIP, lmServerPort)
                    .usePlaintext()
                    .build();

            ILockManagerGrpc.ILockManagerBlockingStub lmStub = ILockManagerGrpc.newBlockingStub(lmChannel);

            //Creates a transaction
            Transaction transaction = tmStub.txBegin(Empty.newBuilder().build());

            System.out.println("Transaction created with id: " + transaction.getTid());

            int v, res;
            int x = 100;

            //Calls to Vector already sent with transaction ID

            //Lock position 0
            lmStub.lock(LockRequest.newBuilder().setTid(transaction.getTid()).setPos(0).build());
            System.out.println("Lock acquired");

            //equivalent to port.read(0);
            VectorResponse vectorResponse = rmStub.read(ReadMessage.newBuilder().setTid(transaction.getTid()).setPos(0).build());
            res = vectorResponse.getValue() - x;
            Thread.sleep(100);

            //equivalent to port.write(0, res);
            rmStub.write(WriteMessage.newBuilder().setTid(transaction.getTid()).setPos(0).setValue(res).build());
            Thread.sleep(100);

            //Unlock position 0
            lmStub.unlock(UnlockRequest.newBuilder().setTid(transaction.getTid()).setPos(0).build());
            System.out.println("Lock released");

            //Lock position 2
            lmStub.lock(LockRequest.newBuilder().setTid(transaction.getTid()).setPos(2).build());
            System.out.println("Lock acquired");

            //equivalent to port.read(2);
            v = rmStub.read(ReadMessage.newBuilder().setTid(transaction.getTid()).setPos(2).build()).getValue();
            res = v + x;
            Thread.sleep(100);

            //equivalent to port.write(2, res);
            rmStub.write(WriteMessage.newBuilder().setTid(transaction.getTid()).setPos(2).setValue(res).build());

            //Unlock position 2
            lmStub.unlock(UnlockRequest.newBuilder().setTid(transaction.getTid()).setPos(2).build());
            System.out.println("Lock released");

            //Commit
            tmStub.txCommit(transaction);


            //Missing commits or rollbacks calls to Transaction manager

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Error:" + ex.getMessage());
        }

        if (rmChannel!=null) {
            //Unsubscribe user from server
            logger.log(Level.INFO, "Shutdown channel to server ");
            rmChannel.shutdown().awaitTermination(2, TimeUnit.SECONDS);
        }

        if (tmChannel!=null) {
            //Unsubscribe user from server
            logger.log(Level.INFO, "Shutdown channel to server ");
            tmChannel.shutdown().awaitTermination(2, TimeUnit.SECONDS);
        }

        if (lmChannel!=null) {
            //Unsubscribe user from server
            logger.log(Level.INFO, "Shutdown channel to server ");
            lmChannel.shutdown().awaitTermination(2, TimeUnit.SECONDS);
        }

    }
}
