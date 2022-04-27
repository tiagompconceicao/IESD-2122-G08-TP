package isos.tp1.isyiesd.cestc.sertm;

import javax.jws.WebService;

@WebService(endpointInterface = "isos.tp1.isyiesd.cestc.sertm.ITransactionManagerTX")
public class TransactionManagerTX implements ITransactionManagerTX {

    //Must this instance receive a Controller Instance to manage transactions ?

    @Override
    public int tx_begin() {
        //TODO To be implemented
        return 0;
    }

    @Override
    public boolean tx_commit(int tid) {
        //TODO To be implemented
        return false;
    }

    @Override
    public boolean tx_rollback(int tid) {
        //TODO To be implemented
        return false;
    }
}
