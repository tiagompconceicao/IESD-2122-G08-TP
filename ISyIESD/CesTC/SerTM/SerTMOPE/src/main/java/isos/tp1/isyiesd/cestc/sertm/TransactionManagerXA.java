package isos.tp1.isyiesd.cestc.sertm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import transactionManagerXA.ITransactionManagerXAGrpc;
import transactionManagerXA.RegistryMessage;


public class TransactionManagerXA extends ITransactionManagerXAGrpc.ITransactionManagerXAImplBase {

    @Override
    public void xaReg(RegistryMessage message, StreamObserver<Empty> responseObserver){
        //TODO To be implemented
    }
}
