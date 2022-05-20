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
    private static String rmServerIP = "localhost";
    private static int rmServerPort = 9000;
    private static int serverPort = 9001;

    public static void main(String[] args) {

        //Deviamos implementar uma espécie de nó central (tipo servidor JINI para gerir a transparência à localização?)
        switch (args.length){
            case 3:
                serverPort = Integer.parseInt(args[2]);
            case 2:
                rmServerPort = Integer.parseInt(args[1]);
            case 1:
                rmServerIP = args[0];
                break;
        }

        //Transaction manager instance shared with interfaces TX and XA
        TransactionManager transactionManager = new TransactionManager();
        TransactionManagerTX transactionManagerTX = new TransactionManagerTX(transactionManager);
        TransactionManagerXA transactionManagerXA = new TransactionManagerXA(transactionManager);

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