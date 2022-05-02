package isos.tp1.isyiesd.cesvector.servectorcli;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
    public static final Logger logger = Logger.getLogger(VectorClient.class.getName());

    //Code adapted to use gRPC
    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel=null;
        if (args.length == 1) serverIP = args[0];
        try {
            channel = ManagedChannelBuilder.forAddress(serverIP, serverPort)
                    .usePlaintext()
                    .build();
            IVectorGrpc.IVectorBlockingStub stub = IVectorGrpc.newBlockingStub(channel);

            int v, res;
            int x = 100;

            //port.read(0);
            VectorResponse vectorResponse = stub.read(ReadMessage.newBuilder().setPos(0).build());
            res = vectorResponse.getValue() - x;
            Thread.sleep(100);

            //port.write(0, res);
            stub.write(WriteMessage.newBuilder().setPos(0).setValue(res).build());
            Thread.sleep(100);

            //port.read(2);
            v = stub.read(ReadMessage.newBuilder().setPos(2).build()).getValue();
            res = v + x;
            Thread.sleep(100);

            //port.write(2, res);
            stub.write(WriteMessage.newBuilder().setPos(2).setValue(res).build());



        } catch (Exception ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Error:" + ex.getMessage());
        }
        if (channel!=null) {
            //Unsubscribe user from server
            logger.log(Level.INFO, "Shutdown channel to server ");
            channel.shutdown().awaitTermination(2, TimeUnit.SECONDS);
        }

    }
}
