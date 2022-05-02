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

    private static List<Integer> vector = Arrays.asList(300, 234, 56, 789);


    @Override
    public void read(ReadMessage message, StreamObserver<VectorResponse> responseObserver){
        //TODO To be implemented
    }

    @Override
    public void write(WriteMessage message, StreamObserver<Empty> responseObserver){
        //TODO To be implemented
    }
}
