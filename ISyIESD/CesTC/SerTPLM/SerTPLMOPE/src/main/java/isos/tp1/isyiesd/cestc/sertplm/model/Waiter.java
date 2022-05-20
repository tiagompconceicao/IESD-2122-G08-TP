package isos.tp1.isyiesd.cestc.sertplm.model;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lockManager.LockElement;

public class Waiter {

    private int tid;
    private LockElement lockElement;
    private StreamObserver<Empty> responseObserver;

    public Waiter(LockElement lockElement, StreamObserver<Empty> responseObserver, int tid){
        this.tid = tid;
        this.lockElement = lockElement;
        this.responseObserver = responseObserver;
    }

    public LockElement getLockElement() {
        return lockElement;
    }

    public StreamObserver<Empty> getResponseObserver() {
        return responseObserver;
    }

    public int getTid() {
        return tid;
    }
}
