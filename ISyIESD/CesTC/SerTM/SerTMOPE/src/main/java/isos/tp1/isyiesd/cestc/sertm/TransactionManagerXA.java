package isos.tp1.isyiesd.cestc.sertm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import transactionManagerXA.ITransactionManagerXAGrpc;
import transactionManagerXA.RegistryMessage;


public class TransactionManagerXA extends ITransactionManagerXAGrpc.ITransactionManagerXAImplBase {

    private TransactionManagerV2 tm;
    public TransactionManagerXA(TransactionManagerV2 transactionManager){
        this.tm = transactionManager;
    }

    //Por simplificação assumimos que não é necessário um metodo de unregister porque
    //os TransactionsID são unicos (A não ser que dê a volta a MAX_INTEGER_VALUE).
    //Register activity from a transaction
    @Override
    public void xaReg(RegistryMessage message, StreamObserver<Empty> responseObserver){
        tm.registerVectorParticipation(message.getTid(), message.getSender(), responseObserver);
//        tm.registerActivity(message.getTid(),message.getSender());
//
//        responseObserver.onNext(Empty.newBuilder().build());
//        responseObserver.onCompleted();
    }
}