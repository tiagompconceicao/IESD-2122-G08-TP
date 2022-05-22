package isos.tp1.isyiesd.cestc.sertm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import transactionManagerTX.ITransactionManagerTXGrpc;
import transactionManagerTX.Result;
import transactionManagerTX.Transaction;

public class TransactionManagerTX extends ITransactionManagerTXGrpc.ITransactionManagerTXImplBase {


    private TransactionManagerV2 tm;
    public TransactionManagerTX(TransactionManagerV2 transactionManager){
        this.tm = transactionManager;
    }


    //Generates a transaction id, saves it and sends it to the client
    @Override
    public void txBegin(Empty request, StreamObserver<Transaction> responseObserver) {
        tm.begin(responseObserver);
//        int tid = tm.begin();
//
//        Transaction transaction = Transaction.newBuilder().setTid(tid).build();
//
//        responseObserver.onNext(transaction);
//        responseObserver.onCompleted();
    }

    //Checks the list of operations of a transaction ,calls axPrepare for each vector server, then if all ok calls axCommit
    @Override
    public void txCommit(Transaction message, StreamObserver<Result> responseObserver) {
        tm.commit(message.getTid(), responseObserver);
//        boolean res = false;
//        try {
//            res = tm.commit(message.getTid());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        responseObserver.onNext(Result.newBuilder().setStatus(res).build());
//        responseObserver.onCompleted();
    }

    //Abort changes, also calls axRollback
    @Override
    public void txRollback(Transaction message, StreamObserver<Empty> responseObserver) {
        tm.rollback(message.getTid(), responseObserver);
//        boolean res = false;
//        try {
//            res = tm.rollback(message.getTid());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        responseObserver.onNext(Result.newBuilder().setStatus(res).build());
//        responseObserver.onCompleted();
    }
}