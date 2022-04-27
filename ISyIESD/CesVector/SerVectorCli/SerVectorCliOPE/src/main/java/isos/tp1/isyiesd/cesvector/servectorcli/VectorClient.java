package isos.tp1.isyiesd.cesvector.servectorcli;

import isos.tp1.isyiesd.cesvector.servector.IVector;
import isos.tp1.isyiesd.cesvector.servector.VectorService;

/**
 * Hello world!
 */
public class VectorClient {

    public static void main(String[] args) throws InterruptedException {

        VectorService vectorService = new VectorService();
        IVector port = vectorService.getVectorPort();

        int v, res;
        int x = 100;

        v = port.read(0);
        res = v - x;
        Thread.sleep(100);

        port.write(0, res);
        Thread.sleep(100);

        v = port.read(2);
        res = v + x;
        Thread.sleep(100);

        port.write(2, res);

    }
}
