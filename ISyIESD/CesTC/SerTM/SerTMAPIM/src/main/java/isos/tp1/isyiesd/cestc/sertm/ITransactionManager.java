package isos.tp1.isyiesd.cestc.sertm;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(name = "ITransactionManager", targetNamespace = "http://iesd21.isos.isyiesd.cestc.sertmserver")
@SOAPBinding(style = Style.DOCUMENT)
public interface ITransactionManager {

    @WebMethod
    int tx_begin();

    @WebMethod
    boolean tx_commit(int tid);

    @WebMethod
    boolean tx_rollback(int tid);

}
