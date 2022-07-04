package isos.tp1.isyiesd.cestc.sertm;

import IRegistry.IRegistryGrpc;
import IRegistry.ServiceEndpoint;
import IRegistry.VectorServices;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

//import javax.xml.ws.Endpoint;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Endpoint that exposes the Transaction Manager
 */
public class SiteServer {

    private static final String SERVICE_VAR_NAME = "REGISTRY_SERVICE_SERVICE_HOST";
    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static String thisIP = "localhost";
    private static final int thisPort = 9001;
    private static String coordinatorIP = "localhost";
    private static final int coordinatorPort = 9000;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    public static void main(String[] args) {
        if (args.length == 1) {
            coordinatorIP = args[0];
        }

        String envVarIP = System.getenv(SERVICE_VAR_NAME);
        if (envVarIP != null){
            coordinatorIP = envVarIP;
        }

        try {
            thisIP = getOwnIp();
            logger.info("Going to connect to: " + coordinatorIP);
            logger.info("Going to register service with IP: " + thisIP);

            thisIP = getOwnIp();
            ManagedChannel coordinatorChannel = ManagedChannelBuilder
                .forAddress(coordinatorIP, coordinatorPort)
                .usePlaintext()
                .build();
            IRegistryGrpc.IRegistryBlockingStub coordinatorProxy = IRegistryGrpc
              .newBlockingStub(coordinatorChannel);

            //Regista-se no Coordinator como TM
            coordinatorProxy.registerService(ServiceEndpoint
                .newBuilder()
                .setIp(thisIP)
                .setPort(thisPort)
                .setType("TM")
                .setName("TM1")
                .build());

            System.out.println(formatter.format(new Date())+": Registered on Coordinator as TM.");
            VectorServices vs = coordinatorProxy.getVectorServices(Empty.newBuilder().build());
            System.out.println(formatter.format(new Date())+": Obtained Vector Services Info");
            //Tem de conhecer os Vector Services para puder comunicar (AX-proto)
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
            logger.info("TM Server started, listening on " + thisPort);
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