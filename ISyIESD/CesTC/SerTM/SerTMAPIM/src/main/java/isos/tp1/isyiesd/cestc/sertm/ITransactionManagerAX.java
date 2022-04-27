package isos.tp1.isyiesd.cestc.sertm;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(name = "ITransactionManagerAX", targetNamespace = "http://iesd21.isos.isyiesd.cestc.sertmserver")
@SOAPBinding(style = Style.DOCUMENT)
public interface ITransactionManagerAX {

    @WebMethod
    boolean ax_reg(int tid);

}
