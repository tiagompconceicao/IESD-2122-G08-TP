package isos.tp1.isyiesd.cesvector.servector;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.HashMap;
import java.util.LinkedList;
import transactionManagerAX.Result;
import transactionManagerXA.ITransactionManagerXAGrpc;
import transactionManagerXA.ITransactionManagerXAGrpc.ITransactionManagerXABlockingStub;
import transactionManagerXA.RegistryMessage;
import vector.VectorResponse;


public class VectorEndPoint {
    private final String vectorServiceName;

    private final int[] vector = {300, 234, 56, 789};

    LinkedList<TransactionRegister> transactionsRegistry;
    HashMap<Integer, Transac> transacHistory;
    ITransactionManagerXABlockingStub tmProxy;

    public VectorEndPoint(String tmIP, int tmPort, String vectorServiceName) {
        this.vectorServiceName = vectorServiceName;
        transacHistory = new HashMap<>();
        transactionsRegistry = new LinkedList<>();
        ManagedChannel tmChannel = ManagedChannelBuilder.forAddress(tmIP, tmPort)
          .usePlaintext()
          .build();
        tmProxy = ITransactionManagerXAGrpc.newBlockingStub(tmChannel);
    }

    public void read(int tid, int pos, StreamObserver<VectorResponse> responseObserver) {
        System.out.println("Read Called on index: " + pos + " for Transaction: "+ tid);
        if(getTransactionRegisterIndex(tid) == -1) {
            transactionsRegistry.add(new TransactionRegister(tid));
            tmProxy.xaReg(RegistryMessage.newBuilder().setTid(tid).setSender(vectorServiceName).build());
        }
        responseObserver.onNext(VectorResponse.newBuilder().setValue(vector[pos]).build());
        responseObserver.onCompleted();
    }

    public void write(int tid, int pos, int value, StreamObserver<Empty> responseObserver) {
        System.out.println("Write Called on index: " + pos + " for Transaction: "+ tid);
        if(getTransactionRegisterIndex(tid) == -1) {
            transactionsRegistry.add(new TransactionRegister(tid));
            tmProxy.xaReg(RegistryMessage.newBuilder().setTid(tid).setSender(vectorServiceName).build());
        }
        Transac t = transacHistory.get(tid);
        if(t == null) {
            t = new Transac();
            t.writes.add(new Write(pos, value));
            transacHistory.put(tid, t);
        } else {
            t.writes.add(new Write(pos, value));
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public void prepare(int tid, StreamObserver<Result> responseObserver) {
        System.out.println("Prepare to Commit Called for Transaction: "+ tid);
        if(getTransactionRegisterIndex(tid) != -1) {
            responseObserver.onNext(Result.newBuilder().setStatus(true).build());
        } else {
            responseObserver.onNext(Result.newBuilder().setStatus(false).build());
        }
        responseObserver.onCompleted();
    }

    public void commit(int tid, StreamObserver<Empty> responseObserver) {
        System.out.println("Commit Called for Transaction: "+ tid);
        Transac t = transacHistory.get(tid);
        for(Write w : t.writes) {
            vector[w.pos] = w.newValue;
        }
        int trIndex = getTransactionRegisterIndex(tid);
        transactionsRegistry.remove(trIndex);
        transacHistory.remove(tid);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public void rollback(int tid, StreamObserver<Empty> responseObserver) {
        System.out.println("Rollback Called for Transaction: "+ tid);
        int trIndex = getTransactionRegisterIndex(tid);
        transactionsRegistry.remove(trIndex);
        transacHistory.remove(tid);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    private int getTransactionRegisterIndex(int tid) {
        for(int i = 0; i < transactionsRegistry.size(); i++) {
            if(transactionsRegistry.get(i).transacID == tid) {
                return i;
            }
        }
        return -1;
    }

    public int getSum() {
        int sum = 0;
        for(int val : vector) {
            sum = sum + val;
        }
        return sum;
    }
}
