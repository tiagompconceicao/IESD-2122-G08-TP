package isos.tp1.isyiesd.cestc.sertm;

import javax.jws.WebService;

@WebService(endpointInterface = "isos.tp1.isyiesd.cesvector.servector.ITransactionManager")
public class TransactionManager implements ITransactionManager {


    @Override
    public int tx_begin() {
        //TODO Implement method
        return 0;
    }

    @Override
    public boolean tx_commit(int tid) {
        //TODO Implement method
        return false;
    }

    @Override
    public boolean tx_rollback(int tid) {
        //TODO Implement method
        return false;
    }
}
