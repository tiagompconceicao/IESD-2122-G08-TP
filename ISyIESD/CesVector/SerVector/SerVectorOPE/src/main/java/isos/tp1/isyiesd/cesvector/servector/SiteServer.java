package isos.tp1.isyiesd.cesvector.servector;

import IRegistry.IRegistryGrpc;
import IRegistry.ServiceEndpoint;
import IRegistry.ServiceRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class SiteServer {

    private static final String SERVICE_VAR_NAME = "REGISTRY_SERVICE_SERVICE_HOST";
    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String thisIP = "localhost";
    private static int thisPort = 9005;
    private static String coordinatorIP = "172.21.83.158";
    private static final int coordinatorPort = 30961;
    //name of this Vector Service
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        if (args.length == 1) {
            thisPort = Integer.parseInt(args[0]);
        }

        String envVarIP = System.getenv(SERVICE_VAR_NAME);
        if (envVarIP != null){
            coordinatorIP = envVarIP;
        }

        try {
            thisIP = getOwnIp();
            logger.info("Going to connect to: " + coordinatorIP);
            logger.info("Going to register service with IP: " + thisIP);

            //connecção ao coordenador
            ManagedChannel coordinatorChannel = ManagedChannelBuilder
              .forAddress(coordinatorIP, coordinatorPort)
              .usePlaintext()
              .build();
            IRegistryGrpc.IRegistryBlockingStub coordinatorProxy = IRegistryGrpc
              .newBlockingStub(coordinatorChannel);

            //regista-se no coordenador como VectorService
            String vectorServiceName = coordinatorProxy.registerService(ServiceEndpoint
              .newBuilder()
              .setIp(thisIP)
              .setPort(thisPort)
              .setType("Vector")
              .build()).getName();

            System.out.println(formatter.format(new Date())+": Registered on Coordinator as a Vector" +
              "Service.");
            System.out.println("    -Getting TM info.");
            //Obtem Info do Transaction Manager
            ServiceEndpoint tm = coordinatorProxy.getService(ServiceRequest.newBuilder().setType("TM").setName("TM1").build());
            System.out.println(formatter.format(new Date())+": TM Info Obtained.");

            //inicia o Vector Service com a info de connexão do TM e o nome do Vector Service
            VectorEndPoint vectorEP = new VectorEndPoint(tm.getIp(), tm.getPort(), vectorServiceName);
            //inicia o serviço de comunicação com o TM (AX-proto)
            TransactionManagerAX transactionManagerService = new TransactionManagerAX(vectorEP);
            //inicia o serviço Vector
            Vector vectorService = new Vector(vectorEP);

            //Launching server
            final Server svc = ServerBuilder.forPort(thisPort)
                    .addService(vectorService)
                    .addService(transactionManagerService)
                    .build()
                    .start();
            logger.info("Vector Service Server started, listening on " + thisPort);

            System.err.println("*** server await termination");
            svc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getOwnIp(){
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return socket.getLocalAddress().getHostAddress();
    }

}