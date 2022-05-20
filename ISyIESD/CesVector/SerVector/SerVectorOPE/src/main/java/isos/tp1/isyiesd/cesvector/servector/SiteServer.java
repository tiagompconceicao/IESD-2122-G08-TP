package isos.tp1.isyiesd.cesvector.servector;

import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import transactionManagerAX.Transaction;
import vector.IVectorGrpc;

import javax.xml.ws.Endpoint;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class SiteServer {

    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String tmServerIP = "localhost";
    private static String serverIP= "localhost";
    private static int tmServerPort = 9001;
    private static int serverPort = 9000;

    public static void main(String[] args) {

        //Deviamos implementar uma espécie de nó central (tipo servidor JINI para gerir a transparência à localização?)
        switch (args.length){
            case 3:
                tmServerPort = Integer.parseInt(args[2]);
            case 2:
                tmServerIP = args[1];
            case 1:
                serverIP = args[0];
                break;
        }

        Vector vector = new Vector(serverIP,tmServerIP,tmServerPort);
        TransactionManagerAX transactionManager = new TransactionManagerAX(vector);

        try {
            //Launching server
            final Server svc = ServerBuilder.forPort(serverPort)
                    .addService(vector)
                    .addService(transactionManager)
                    .build()
                    .start();
            logger.info("Server started, listening on " + serverPort);

            System.err.println("*** server await termination");
            svc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}