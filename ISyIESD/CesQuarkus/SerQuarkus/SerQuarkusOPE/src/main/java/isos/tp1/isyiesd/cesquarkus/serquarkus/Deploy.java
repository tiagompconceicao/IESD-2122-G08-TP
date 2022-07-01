package isos.tp1.isyiesd.cesquarkus.serquarkus;


import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Deploy {
    public static void main(String... args) {
        Quarkus.run(isos.tp1.isyiesd.cesregistry.serregistry.SiteServer , args);
        Quarkus.run(isos.tp1.isyiesd.cestc.sertm.SiteServer , args);
    }
}
