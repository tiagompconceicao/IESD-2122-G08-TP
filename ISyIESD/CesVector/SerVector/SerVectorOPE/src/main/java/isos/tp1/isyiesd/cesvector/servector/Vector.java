package isos.tp1.isyiesd.cesvector.servector;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import vector.IVectorGrpc;
import vector.ReadMessage;
import vector.VectorResponse;
import vector.WriteMessage;
import java.util.Arrays;
import java.util.List;


public class Vector extends IVectorGrpc.IVectorImplBase{

    private final List<Integer> vector = Arrays.asList(300, 234, 56, 789);

    private static final Object lock = new Object();


    @Override
    public void read(ReadMessage message, StreamObserver<VectorResponse> responseObserver){
        synchronized (lock){
            int value = vector.get(message.getPos());
            VectorResponse response = VectorResponse.newBuilder().setValue(value).build();

            System.out.println("Was read value: " + value);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void write(WriteMessage message, StreamObserver<Empty> responseObserver){
        synchronized (lock){
            vector.set(message.getPos(),message.getValue());


            System.out.println("Value: " + message.getValue() + " was written in position: " + message.getPos());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }
}
