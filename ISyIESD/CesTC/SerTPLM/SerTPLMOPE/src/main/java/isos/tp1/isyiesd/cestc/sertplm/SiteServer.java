package isos.tp1.isyiesd.cestc.sertplm;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.logging.Logger;

/* Endpoint that exposes the Transaction Manager
 */
public class SiteServer {

    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static int serverPort = 9002;

    public static void main(String[] args) {

        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }

        try {
            //Launching server
            final Server svc = ServerBuilder.forPort(serverPort)
                    .addService(new ConcurrencyManager(2,4))
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