package isos.tp1.isyiesd.cesvector.servector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(name = "IVectorXA", targetNamespace = "http://iesd21.isos.isyiesd.cesvector.servectorserver")
@SOAPBinding(style = Style.DOCUMENT)
public interface IVectorXA {

    @WebMethod
    boolean xa_prepare(int tid);

    @WebMethod
    boolean xa_commit(int tid);

    @WebMethod
    boolean xa_rollback(int tid);

}
