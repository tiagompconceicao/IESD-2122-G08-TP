package isos.tp1.isyiesd.cestc.sertm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import transactionManagerXA.ITransactionManagerXAGrpc;
import transactionManagerXA.RegistryMessage;


public class TransactionManagerXA extends ITransactionManagerXAGrpc.ITransactionManagerXAImplBase {

    private TransactionManager tm;
    public TransactionManagerXA(TransactionManager transactionManager){
        this.tm = transactionManager;
    }

    //Register activity from a transaction
    @Override
    public void xaReg(RegistryMessage message, StreamObserver<Empty> responseObserver){
        tm.registerActivity(message.getTid(),message.getSender());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
