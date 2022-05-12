package isos.tp1.isyiesd.cesvector.servectorcli;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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

    private static String serverIP = "localhost";
    private static final int serverPort = 9000;

    private static String tmServerIP = "localhost";
    private static final int tmServerPort = 9001;
    public static final Logger logger = Logger.getLogger(VectorClient.class.getName());


    public static void main(String[] args) throws InterruptedException {

        ManagedChannel rmChannel=null;
        ManagedChannel tmChannel=null;
        if (args.length == 1) serverIP = args[0];
        try {
            //Setup connection to transaction manager
            tmChannel = ManagedChannelBuilder.forAddress(tmServerIP, tmServerPort)
                    .usePlaintext()
                    .build();
            ITransactionManagerTXGrpc.ITransactionManagerTXBlockingStub tmStub = ITransactionManagerTXGrpc.newBlockingStub(tmChannel);

            //Setup connection to resource manager
            rmChannel = ManagedChannelBuilder.forAddress(serverIP, serverPort)
                    .usePlaintext()
                    .build();
            IVectorGrpc.IVectorBlockingStub rmStub = IVectorGrpc.newBlockingStub(rmChannel);

            //Creates a transaction
            Transaction transaction = tmStub.txBegin(Empty.newBuilder().build());

            System.out.println("Transaction created with id: " + transaction.getTid());

            //Missing TLPM Service to get and releases locks

            int v, res;
            int x = 100;

            //equivalent to port.read(0);
            VectorResponse vectorResponse = rmStub.read(ReadMessage.newBuilder().setPos(0).build());
            res = vectorResponse.getValue() - x;
            Thread.sleep(1000);

            //equivalent to port.write(0, res);
            rmStub.write(WriteMessage.newBuilder().setPos(0).setValue(res).build());
            Thread.sleep(1000);

            //equivalent to port.read(2);
            v = rmStub.read(ReadMessage.newBuilder().setPos(2).build()).getValue();
            res = v + x;
            Thread.sleep(1000);

            //equivalent to port.write(2, res);
            rmStub.write(WriteMessage.newBuilder().setPos(2).setValue(res).build());


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

    }
}
