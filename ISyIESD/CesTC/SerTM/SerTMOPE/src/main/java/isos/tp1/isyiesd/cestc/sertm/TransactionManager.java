package isos.tp1.isyiesd.cestc.sertm;

import javax.jws.WebService;

@WebService(endpointInterface = "isos.tp1.isyiesd.cestc.sertm.ITransactionManager")
public class TransactionManager implements ITransactionManager {


    @Override
    public int tx_begin() {
        return 0;
    }

    @Override
    public boolean tx_commit(int tid) {
        return false;
    }

    @Override
    public boolean tx_rollback(int tid) {
        return false;
    }
}
