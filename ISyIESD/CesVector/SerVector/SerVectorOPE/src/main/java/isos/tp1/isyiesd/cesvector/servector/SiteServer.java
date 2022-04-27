package isos.tp1.isyiesd.cesvector.servector;

import javax.xml.ws.Endpoint;

/**
 * Hello world!
 */
public class SiteServer {

    public static void main(String[] args) {
        Endpoint vectorEndpoint = Endpoint.create(new Vector());
        Endpoint vectorXAEndpoint = Endpoint.create(new VectorXA());
        System.out.println("Starting VectorServer...");
        vectorEndpoint.publish("http://localhost:2058/Vector");
        System.out.println("Starting VectorXAServer...");
        vectorXAEndpoint.publish("http://localhost:2058/VectorXA");
    }

}
