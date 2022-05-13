package isos.tp1.isyiesd.cesvector.servector;

import io.grpc.stub.StreamObserver;
import transactionManagerAX.ITransactionManagerAXGrpc;
import transactionManagerAX.Result;
import transactionManagerAX.Transaction;
import transactionManagerAX.Variance;


public class TransactionManagerAX extends ITransactionManagerAXGrpc.ITransactionManagerAXImplBase {

    private Vector vector;

    public TransactionManagerAX(Vector vector) {
        this.vector = vector;
    }


    //Calculates sum of the values of vector and returns it ???
    @Override
    public void xaPrepare(Transaction transaction, StreamObserver<Variance> responseObserver){
        responseObserver.onNext(Variance.newBuilder().setValue(vector.getVariance()).build());
        responseObserver.onCompleted();
    }


    //Confirmation of the success of the transaction
    @Override
    public void xaCommit(Transaction transaction, StreamObserver<Result> responseObserver){
        //TODO To be implemented
    }

    //Rollback the transaction
    @Override
    public void xaRollback(Transaction transaction, StreamObserver<Result> responseObserver){
        //TODO To be implemented
    }
}
