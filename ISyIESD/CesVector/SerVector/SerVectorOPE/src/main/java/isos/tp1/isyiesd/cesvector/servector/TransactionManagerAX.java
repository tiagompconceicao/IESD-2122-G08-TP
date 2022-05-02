package isos.tp1.isyiesd.cesvector.servector;


import io.grpc.stub.StreamObserver;
import transactionManagerAX.ITransactionManagerAXGrpc;
import transactionManagerAX.Result;
import transactionManagerAX.Transaction;

//I don't know why the methods aren't being recognized

public class TransactionManagerAX extends ITransactionManagerAXGrpc.ITransactionManagerAXImplBase {

    @Override
    public void xaPrepare(Transaction transaction, StreamObserver<Result> responseObserver){
        responseObserver.onCompleted();
    }


    @Override
    public void xaCommit(Transaction transaction, StreamObserver<Result> responseObserver){
        responseObserver.onCompleted();
    }

    @Override
    public void xaRollback(Transaction transaction, StreamObserver<Result> responseObserver){

        responseObserver.onCompleted();
    }
}
