package isos.tp1.isyiesd.cesvector.servector;

import ICoordinator.ICoordinatorGrpc;
import ICoordinator.ServiceEndpoint;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

//import javax.xml.ws.Endpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class SiteServer {

    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String thisIP = "localhost";
    private static int thisPort = 9003;
    private static String coordinatorIP = "localhost";
    private static int coordinatorPort = 9000;
    //name of this Vector Service
    private static String vectorServiceName = "VectorService_1";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        if (args.length == 2) {
            vectorServiceName = args[0];
            thisPort = Integer.parseInt(args[1]);
        } else if (args.length == 3) {
            vectorServiceName = args[0];
            thisIP = args[1];
            thisPort = Integer.parseInt(args[2]);
        } else if (args.length == 5) {
            vectorServiceName = args[0];
            thisIP = args[1];
            thisPort = Integer.parseInt(args[2]);
            coordinatorIP = args[3];
            coordinatorPort = Integer.parseInt(args[4]);
        }

        try {
            //connecção ao coordenador
            ManagedChannel coordinatorChannel = ManagedChannelBuilder
              .forAddress(coordinatorIP, coordinatorPort)
              .usePlaintext()
              .build();
            ICoordinatorGrpc.ICoordinatorBlockingStub coordinatorProxy = ICoordinatorGrpc
              .newBlockingStub(coordinatorChannel);

            //regista-se no coordenador como VectorService
            coordinatorProxy.registerVectorService(ServiceEndpoint
              .newBuilder()
              .setIp(thisIP)
              .setPort(thisPort)
              .setName(vectorServiceName)
              .build());
            System.out.println(formatter.format(new Date())+": Registered on Coordinator as a Vector" +
              "Service.");
            System.out.println("    -Getting TM info.");
            //Obtem Info do Transaction Manager
            ServiceEndpoint tm = coordinatorProxy.getTM(Empty.newBuilder().build());
            System.out.println(formatter.format(new Date())+": TM Info Obtained.");

            //inicia o Vector Service com a info de connexão do TM e o nome do Vector Service
            VectorEndPoint vectorEP = new VectorEndPoint(tm.getIp(), tm.getPort(), vectorServiceName);
            //inicia o serviço de comunicação com o TM (AX-proto)
            TransactionManagerAX transactionManagerService = new TransactionManagerAX(vectorEP);
            //inicia o serviço Vector
            Vector vectorService = new Vector(vectorEP);
            //incia o serviço CheckSum
            CheckSum cs = new CheckSum(vectorEP);

            //Launching server
            final Server svc = ServerBuilder.forPort(thisPort)
                    .addService(vectorService)
                    .addService(transactionManagerService)
                    .addService(cs)
                    .build()
                    .start();
            logger.info("Vector Service Server started, listening on " + thisPort);

            System.err.println("*** server await termination");
            svc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}