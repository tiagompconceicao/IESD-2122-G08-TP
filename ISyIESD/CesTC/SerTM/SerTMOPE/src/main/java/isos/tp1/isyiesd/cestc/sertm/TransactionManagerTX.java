package isos.tp1.isyiesd.cestc.sertm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import transactionManagerTX.ITransactionManagerTXGrpc;
import transactionManagerTX.Result;
import transactionManagerTX.Transaction;

public class TransactionManagerTX extends ITransactionManagerTXGrpc.ITransactionManagerTXImplBase {

    @Override
    public void txBegin(Empty request, StreamObserver<Transaction> responseObserver) {
        //TODO To be implemented
    }

    @Override
    public void txCommit(Transaction message, StreamObserver<Result> responseObserver) {
        //TODO To be implemented
    }

    @Override
    public void txRollback(Transaction message, StreamObserver<Result> responseObserver) {
        //TODO To be implemented
    }
}
