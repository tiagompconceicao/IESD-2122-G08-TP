package isos.tp1.isyiesd.cestc.sertm;

import ICoordinator.ICoordinatorGrpc;
import ICoordinator.ServiceEndpoint;
import ICoordinator.VectorServices;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

//import javax.xml.ws.Endpoint;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Endpoint that exposes the Transaction Manager
 */
public class SiteServer {

    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String thisIP = "localhost";
    private static int thisPort = 9001;
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

            coordinatorProxy.registerTM(ServiceEndpoint
              .newBuilder()
              .setIp(thisIP)
              .setPort(thisPort)
              .setName("TM")
              .build());
            VectorServices vs = coordinatorProxy.getVectorServices(Empty.newBuilder().build());


            //Tem de conhecer os "nomes" e ip/port dos vectores
            List<ServiceEndpoint> vectorEndpoints = vs.getVectorsList();
            //Transaction manager instance shared with interfaces TX and XA
            TransactionManagerV2 transactionManager = new TransactionManagerV2(vectorEndpoints);
            TransactionManagerTX transactionManagerTX = new TransactionManagerTX(transactionManager);
            TransactionManagerXA transactionManagerXA = new TransactionManagerXA(transactionManager);

            //Launching server
            final Server svc = ServerBuilder.forPort(thisPort)
                    .addService(transactionManagerTX)
                    .addService(transactionManagerXA)
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