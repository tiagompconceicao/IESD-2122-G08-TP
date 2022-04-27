package isos.tp1.isyiesd.cestc.sertm;

import javax.xml.ws.Endpoint;

/**
 * Endpoint that exposes the Transaction Manager
 */
public class SiteServer {
    public static void main(String[] args) {
        Endpoint endpointAX = Endpoint.create(new TransactionManagerAX());
        Endpoint endpointTX = Endpoint.create(new TransactionManagerTX());
        System.out.println("Starting AX Server...");
        endpointAX.publish("http://localhost:2059/TransactionManagerAX");
        System.out.println("Starting TX Server...");
        endpointTX.publish("http://localhost:2059/TransactionManagerTX");
    }
}
