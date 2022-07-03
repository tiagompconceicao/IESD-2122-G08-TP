package isos.tp1.isyiesd.cesregistry.serregistry;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class SiteServer {
    public static final Logger logger = Logger.getLogger(SiteServer.class.getName());
    private static final int serverPort = 9000;
    private static int numberOfVectorServices = 2;

    public static void main(String[] args) {
        if (args.length == 1) {
            numberOfVectorServices = Integer.parseInt(args[0]);
        }

        try {
            showOwnIp();
            //Launching server
            final Server svc = ServerBuilder.forPort(serverPort)
              .addService(new Registry(numberOfVectorServices))
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

    private static void showOwnIp() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            System.out.println("Local IP is: " + socket.getLocalAddress().getHostAddress());
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
