package isos.tp1.isyiesd.cestc.sertm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import transactionManagerTX.ITransactionManagerTXGrpc;
import transactionManagerTX.Result;
import transactionManagerTX.Transaction;

public class TransactionManagerTX extends ITransactionManagerTXGrpc.ITransactionManagerTXImplBase {


    private TransactionManager tm;
    public TransactionManagerTX(TransactionManager transactionManager){
        this.tm = transactionManager;
    }


    //Generates a transaction id, saves it and sends it to the client
    @Override
    public void txBegin(Empty request, StreamObserver<Transaction> responseObserver) {
        int tid = tm.createTransaction();

        Transaction transaction = Transaction.newBuilder().setTid(tid).build();

        responseObserver.onNext(transaction);
        responseObserver.onCompleted();
    }

    //Checks the list of operations of a transaction ,calls axPrepare for each vector server, then if all ok calls axCommit
    @Override
    public void txCommit(Transaction message, StreamObserver<Result> responseObserver) {
        boolean res = false;
        try {
            res = tm.commit(message.getTid());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onNext(Result.newBuilder().setStatus(res).build());
        responseObserver.onCompleted();
    }

    //Abort changes, also calls axRollback
    @Override
    public void txRollback(Transaction message, StreamObserver<Result> responseObserver) {
        boolean res = false;
        try {
            res = tm.rollback(message.getTid());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onNext(Result.newBuilder().setStatus(res).build());
        responseObserver.onCompleted();
    }
}