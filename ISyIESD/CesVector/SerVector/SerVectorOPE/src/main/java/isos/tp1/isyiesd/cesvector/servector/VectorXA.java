package isos.tp1.isyiesd.cesvector.servector;

import javax.jws.WebService;
import java.util.Arrays;
import java.util.List;

@WebService(endpointInterface = "isos.tp1.isyiesd.cesvector.servector.IVectorXA")
public class VectorXA implements IVectorXA {


    @Override
    public boolean xa_prepare(int tid) {
        //TODO TO BE IMPLEMENTED
        return false;
    }

    @Override
    public boolean xa_commit(int tid) {
        //TODO TO BE IMPLEMENTED
        return false;
    }

    @Override
    public boolean xa_rollback(int tid) {
        //TODO TO BE IMPLEMENTED
        return false;
    }
}
