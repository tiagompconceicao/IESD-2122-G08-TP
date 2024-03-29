package isos.tp1.isyiesd.cesvector.servector;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import io.grpc.stub.StreamObserver;
import transactionManagerXA.ITransactionManagerXAGrpc;
import transactionManagerXA.RegistryMessage;
import vector.IVectorGrpc;
import vector.ReadMessage;
import vector.VectorResponse;
import vector.WriteMessage;
import java.util.Arrays;
import java.util.List;


public class Vector extends IVectorGrpc.IVectorImplBase{

//    private final List<Integer> vector = Arrays.asList(300, 234, 56, 789);
//    private static final Object lock = new Object();
//    private final String serverIP;
//    private final ITransactionManagerXAGrpc.ITransactionManagerXABlockingStub tmStub;
    private VectorEndPoint vep;

    public Vector(VectorEndPoint vep) {
        this.vep = vep;
    }
//    public Vector(String serverIP,String tmServerIP, int tmServerPort){
//        this.serverIP = serverIP;
//        //Setup connection to resource manager
//        ManagedChannel tmChannel = ManagedChannelBuilder.forAddress(tmServerIP, tmServerPort)
//                .usePlaintext()
//                .build();
//
//        tmStub = ITransactionManagerXAGrpc.newBlockingStub(tmChannel);
//
//    }

    @Override
    public void read(ReadMessage message, StreamObserver<VectorResponse> responseObserver){
        vep.read(message.getTid(), message.getPos(), responseObserver);
//        synchronized (lock){
//            int value = vector.get(message.getPos());
//            VectorResponse response = VectorResponse.newBuilder().setValue(value).build();
//
//            System.out.println("Was read value: " + value);
//            responseObserver.onNext(response);
//            responseObserver.onCompleted();
//        }
    }

    @Override
    public void write(WriteMessage message, StreamObserver<Empty> responseObserver){
        vep.write(message.getTid(), message.getPos(), message.getValue(), responseObserver);
//        synchronized (lock){
//            //call xaReg and send the tID
//            axReg(message.getTid());
//            vector.set(message.getPos(),message.getValue());
//
//
//            System.out.println("Value: " + message.getValue() + " was written in position: " + message.getPos());
//            responseObserver.onNext(Empty.newBuilder().build());
//            responseObserver.onCompleted();
//        }
    }

//    public void axReg(int tid){
//        RegistryMessage message = RegistryMessage.newBuilder().setTid(tid).setSender(serverIP).build();
//        this.tmStub.xaReg(message);
//    }
//
//    public int getVariance(){
//        int sum = 0;
//        for (int value:vector) {
//            sum += value;
//        }
//        return sum;
//    }
    @Override
    public void checkSum(ReadMessage request, StreamObserver<VectorResponse> responseObserver) {
        vep.getSum(request.getTid(), responseObserver);
    }
}