package isos.tp1.isyiesd.cesvector.servector;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import transactionManagerAX.ITransactionManagerAXGrpc;
import transactionManagerAX.Result;
import transactionManagerAX.Transaction;


public class TransactionManagerAX extends ITransactionManagerAXGrpc.ITransactionManagerAXImplBase {

    private VectorEndPoint vep;

    public TransactionManagerAX(VectorEndPoint vep) {
        this.vep = vep;
    }

    @Override
    public void xaPrepare(Transaction request, StreamObserver<Result> responseObserver) {
        vep.prepare(request.getTid(), responseObserver);
    }

    @Override
    public void xaCommit(Transaction request, StreamObserver<Empty> responseObserver) {
        vep.commit(request.getTid(), responseObserver);
    }

    @Override
    public void xaRollback(Transaction request, StreamObserver<Empty> responseObserver) {
        vep.rollback(request.getTid(), responseObserver);
    }
}