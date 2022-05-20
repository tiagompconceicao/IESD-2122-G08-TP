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


    //Prepares to commit
    @Override
    public void xaPrepare(Transaction transaction, StreamObserver<Variance> responseObserver){
        //TODO To be implemented
    }


    //Commit
    @Override
    public void xaCommit(Transaction transaction, StreamObserver<Result> responseObserver){
        //TODO To be implemented
    }

    //Rollback
    @Override
    public void xaRollback(Transaction transaction, StreamObserver<Result> responseObserver){
        //TODO To be implemented
    }
}