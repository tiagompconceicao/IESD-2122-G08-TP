package isos.tp1.isyiesd.cestc.sertm;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import transactionManagerAX.ITransactionManagerAXGrpc;
import transactionManagerAX.ITransactionManagerAXGrpc.ITransactionManagerAXFutureStub;

public class VectorEndpointConnInfo {
    String name;
    ITransactionManagerAXFutureStub serverProxy;
    public VectorEndpointConnInfo(String ip, int port, String sender) {
        this.name = sender;
        ManagedChannel rmChannel = ManagedChannelBuilder.forAddress(ip, port)
          .usePlaintext()
          .build();
        serverProxy = ITransactionManagerAXGrpc.newFutureStub(rmChannel);
    }
}
