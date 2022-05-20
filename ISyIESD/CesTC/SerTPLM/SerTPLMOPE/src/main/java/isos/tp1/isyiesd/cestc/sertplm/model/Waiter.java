package isos.tp1.isyiesd.cestc.sertplm.model;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lockManager.LockElement;

import java.util.ArrayList;

public class Waiter {

    private int tid;
    private ArrayList<LockElement> lockElements;
    private StreamObserver<Empty> responseObserver;

    public Waiter(ArrayList<LockElement> lockElements, StreamObserver<Empty> responseObserver, int tid){
        this.tid = tid;
        this.lockElements = lockElements;
        this.responseObserver = responseObserver;
    }

    public ArrayList<LockElement> getLockElements() {
        return lockElements;
    }

    public StreamObserver<Empty> getResponseObserver() {
        return responseObserver;
    }

    public int getTid() {
        return tid;
    }
}
