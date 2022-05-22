package isos.tp1.isyiesd.cestc.sertplm;

import ICoordinator.ICoordinatorGrpc;
import ICoordinator.ServiceEndpoint;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.logging.Logger;

/* Endpoint that exposes the Transaction Manager
 */
public class SiteServer {

    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String thisIP = "localhost";
    private static int thisPort = 9002;
    private static String coordinatorIP = "localhost";
    private static int coordinatorPort = 9000;

    public static void main(String[] args) {
        if (args.length == 1) {
            thisPort = Integer.parseInt(args[0]);
        } else if (args.length == 2) {
            thisIP = args[0];
            thisPort = Integer.parseInt(args[1]);
        } else if (args.length == 4) {
            thisIP = args[0];
            thisPort = Integer.parseInt(args[1]);
            coordinatorIP = args[0];
            coordinatorPort = Integer.parseInt(args[1]);
        }
        try {
            ManagedChannel coordinatorChannel = ManagedChannelBuilder
              .forAddress(coordinatorIP, coordinatorPort)
              .usePlaintext()
              .build();
            ICoordinatorGrpc.ICoordinatorBlockingStub coordinatorProxy = ICoordinatorGrpc
              .newBlockingStub(coordinatorChannel);

            coordinatorProxy.registerTPLM(ServiceEndpoint
              .newBuilder()
              .setIp(thisIP)
              .setPort(thisPort)
              .setName("TPLM")
              .build());

            int n = coordinatorProxy.getNumberOfVectorServices(Empty.newBuilder().build()).getValue();

            //Launching server
            final Server svc = ServerBuilder.forPort(thisPort)
                    .addService(new ConcurrencyManager(n,4))
                    .build()
                    .start();
            logger.info("Server started, listening on " + thisPort);

            System.err.println("*** server await termination");
            svc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}