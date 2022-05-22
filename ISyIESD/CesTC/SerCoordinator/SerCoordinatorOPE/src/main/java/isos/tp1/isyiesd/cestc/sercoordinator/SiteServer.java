package isos.tp1.isyiesd.cestc.sercoordinator;

import ICoordinator.ICoordinatorGrpc;
import io.grpc.BindableService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.util.logging.Logger;

public class SiteServer {
    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static int serverPort = 9002;
    private static int numberOfVectorServices = 2;

    public static void main(String[] args) {
        if (args.length == 1) {
            numberOfVectorServices = Integer.parseInt(args[0]);
        }else if (args.length == 2) {
            numberOfVectorServices = Integer.parseInt(args[0]);
            serverPort = Integer.parseInt(args[1]);
        }
        try {
            //Launching server
            final Server svc = ServerBuilder.forPort(serverPort)
              .addService(new Coordinator(numberOfVectorServices))
              .build()
              .start();
            logger.info("Coordination Server started, listening on " + serverPort + " - Expecting " +
              numberOfVectorServices+" Vector Services");
            System.err.println("*** server await termination");
            svc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
