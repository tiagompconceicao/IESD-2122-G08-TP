package isos.tp1.isyiesd.cestc.sertplm;

import IRegistry.IRegistryGrpc;
import IRegistry.ServiceEndpoint;
import com.google.protobuf.Empty;
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

/* Endpoint that exposes the Transaction Manager
 */
public class SiteServer {

    private static final String SERVICE_VAR_NAME = "REGISTRY_SERVICE_SERVICE_HOST";
    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String thisIP = "localhost";
    private static final int thisPort = 9002;
    private static String coordinatorIP = "172.21.83.158";
    private static final int coordinatorPort = 9000;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        if (args.length == 1) {
            coordinatorIP = args [0];
        }

        String envVarIP = System.getenv(SERVICE_VAR_NAME);
        if (envVarIP != null){
            coordinatorIP = envVarIP;
        }

        try {
            thisIP = getOwnIp();
            logger.info("Going to connect to: " + coordinatorIP);
            logger.info("Going to register service with IP: " + thisIP);

            //cria conexão ao coordenador
            ManagedChannel coordinatorChannel = ManagedChannelBuilder
              .forAddress(coordinatorIP, coordinatorPort)
              .usePlaintext()
              .build();
            IRegistryGrpc.IRegistryBlockingStub coordinatorProxy = IRegistryGrpc
              .newBlockingStub(coordinatorChannel);

            //regista-se no coordenador como TPLM (Concurrency Manager)
            coordinatorProxy.registerService(ServiceEndpoint
              .newBuilder()
              .setIp(thisIP)
              .setPort(thisPort)
              .setType("TPLM")
              .build());
            System.out.println(formatter.format(new Date())+": Registered on Coordinator as TPLM (" +
              "Concurrency Manager).");

            //Obtem o numero de Vector services que vão existir
            int n = coordinatorProxy.getNumberOfVectorServices(Empty.newBuilder().build()).getValue();

            //Launching server
            final Server svc = ServerBuilder.forPort(thisPort)
                    .addService(new ConcurrencyManager(n,4))
                    .build()
                    .start();
            logger.info("TPLM Server started, listening on " + thisPort);

            System.err.println("*** server await termination");
            svc.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getOwnIp() {
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