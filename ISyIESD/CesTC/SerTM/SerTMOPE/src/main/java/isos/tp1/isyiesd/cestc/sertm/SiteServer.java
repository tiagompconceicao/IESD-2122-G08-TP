package isos.tp1.isyiesd.cestc.sertm;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import javax.xml.ws.Endpoint;
import java.util.logging.Logger;

/**
 * Endpoint that exposes the Transaction Manager
 */
public class SiteServer {

    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String serverIP = "localhost";
    private static int serverPort = 9000;
    private static String managerIP = "localhost";
    private static int managerPort = 9000;
    public static void main(String[] args) {

        //Deviamos implementar uma espécie de nó central (tipo servidor JINI para gerir a transparência à localização?)
        switch (args.length){
            case 2:
                serverPort = Integer.parseInt(args[1]);
            case 1:
                serverIP = args[0];
                break;
        }

        TransactionManagerTX transactionManagerTX = new TransactionManagerTX();
        TransactionManagerXA transactionManagerXA = new TransactionManagerXA();

        try {
            //Launching server
            final Server svc = ServerBuilder.forPort(serverPort)
                    .addService(transactionManagerTX)
                    .addService(transactionManagerXA)
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
