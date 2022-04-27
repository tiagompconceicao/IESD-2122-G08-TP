package isos.tp1.isyiesd.cestc.sertm;

import javax.xml.ws.Endpoint;

/**
 * Endpoint that exposes the Transaction Manager
 */
public class SiteServer {
    public static void main(String[] args) {
        Endpoint ep = Endpoint.create(new TransactionManager());
        System.out.println("Starting SiteServer...");
        ep.publish("http://localhost:2058/TransactionManager");
    }
}
