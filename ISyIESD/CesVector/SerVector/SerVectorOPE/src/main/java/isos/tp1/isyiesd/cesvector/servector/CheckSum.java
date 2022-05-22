package isos.tp1.isyiesd.cesvector.servector;

import com.google.protobuf.Empty;
import getSum.ICheckSumGrpc;
import getSum.SumResult;
import io.grpc.stub.StreamObserver;

public class CheckSum extends ICheckSumGrpc.ICheckSumImplBase {
    private VectorEndPoint vep;
    public CheckSum(VectorEndPoint vep) {
        this.vep = vep;
    }
    @Override
    public void getSum(Empty request, StreamObserver<SumResult> responseObserver) {
        int val = vep.getSum();
        responseObserver.onNext(SumResult.newBuilder().setValue(val).build());
        responseObserver.onCompleted();
    }
}
