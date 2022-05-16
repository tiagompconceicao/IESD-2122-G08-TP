package isos.tp1.isyiesd.cestc.sertplm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lockManager.ILockManagerGrpc;
import lockManager.LockRequest;
import lockManager.UnlockRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LockManager extends ILockManagerGrpc.ILockManagerImplBase {

    //List with the locks of all vector positions
    private final List<Boolean> locks = Arrays.asList(false,false,false,false);

    //List with the waiting queue for each vector position
    private final HashMap<Integer, LinkedList<StreamObserver<Empty>>> requestsQueue = new HashMap<>();

    public LockManager(){
        for(int i = 0; i<4; i++){
            requestsQueue.put(i,new LinkedList<>());
        }
    }


    //If lock is available then get the lock and returns instantly
    //Or add StreamObserver to the queue and wait
    @Override
    public void lock(LockRequest request, StreamObserver<Empty> responseObserver){
        synchronized (locks){

            if (!locks.get(request.getPos())){
                locks.set(request.getPos(),true);

                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            } else {
                requestsQueue.get(request.getPos()).add(responseObserver);
            }
        }
    }


    //Release unlock
    //If is anyone waiting for the lock of the position released, give lock
    //Or just get lock available
    @Override
    public void unlock(UnlockRequest request, StreamObserver<Empty> responseObeserver){
        synchronized (locks){

            responseObeserver.onNext(Empty.newBuilder().build());
            responseObeserver.onCompleted();

            if (!requestsQueue.get(request.getPos()).isEmpty()){
                StreamObserver<Empty> waiter = requestsQueue.get(request.getPos()).pop();

                waiter.onNext(Empty.newBuilder().build());
                waiter.onCompleted();
            } else {
                locks.set(request.getPos(),false);
            }
        }
    }
}
