package isos.tp1.isyiesd.cestc.sertm;

import javax.jws.WebService;

@WebService(endpointInterface = "isos.tp1.isyiesd.cestc.sertm.ITransactionManagerAX")
public class TransactionManagerAX implements ITransactionManagerAX {

    //Must this instance receive a Controller Instance to manage transactions ?



    //Method to register the association of the work of a transaction on a server
    //Add server name to the tail of the work schedule of a transaction

    @Override
    public boolean ax_reg(int tid) {
        //TODO To be implemented
        return false;
    }
}
