package isos.tp1.isyiesd.cestc.sertplm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import isos.tp1.isyiesd.cestc.sertplm.model.Waiter;
import lockManager.ILockManagerGrpc;
import lockManager.LockElement;
import lockManager.LockRequest;
import lockManager.UnlockRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LockManager extends ILockManagerGrpc.ILockManagerImplBase {

    //List with the locks of all vector positions
    private final List<LockElement> locks = Arrays.asList(null,null,null,null);

    //List with the waiting queue for each vector position
    private final HashMap<Integer, LinkedList<Waiter>> requestsQueue = new HashMap<>();

    private final Object lock = new Object();

    public LockManager(){
        for(int i = 0; i<4; i++){
            requestsQueue.put(i,new LinkedList<>());
        }
    }


    //If lock is available then get the lock and returns instantly
    //Or add StreamObserver to the queue and wait
    @Override
    public void lock(LockRequest request, StreamObserver<Empty> responseObserver){
        synchronized (this.lock){

            LockElement requestLock = request.getLock();
            LockElement lock = locks.get(requestLock.getPos());

            if ( lock == null){
                //No lock in position

                System.out.println("Lock set on position:" + requestLock.getPos() + " in mode: " + requestLock.getLockMode() + " in transaction: " + request.getTid());
                locks.set(requestLock.getPos(), requestLock);
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();

            } else if (lock.getLockMode().equals("read") && lock.getLockMode().equals(lock.getLockMode())) {
                //Read lock and the request is also a read lock
                System.out.println("Lock skipped on position:" + requestLock.getPos() + " in mode: " + requestLock.getLockMode() + " in transaction: " + request.getTid());
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();

            } else {
                //Will wait because there is a write lock
                System.out.println("Wait on position:" + requestLock.getPos() + " in mode: " + requestLock.getLockMode() + " in transaction: " + request.getTid());
                requestsQueue.get(requestLock.getPos()).add(new Waiter(requestLock,responseObserver,request.getTid()));
            }
        }
    }


    //Release unlock
    //If is anyone waiting for the lock of the position released, give lock
    //Or just get lock available
    @Override
    public void unlock(UnlockRequest request, StreamObserver<Empty> responseObeserver){
        synchronized (this.lock){

            //The resquester client is immediately freed
            System.out.println("Lock released on position:" + request.getPos()  + " in transaction: " + request.getTid());
            responseObeserver.onNext(Empty.newBuilder().build());
            responseObeserver.onCompleted();

            if (!requestsQueue.get(request.getPos()).isEmpty()){
                //StreamObserver<Empty> waiter = requestsQueue.get(request.getPos()).pop();
                Waiter waiter = requestsQueue.get(request.getPos()).pop();

                if (waiter.getLockElement().getLockMode().equals("read")){

                    //free all read locks because a read lock got the position lock
                    for (Waiter waitRequest:requestsQueue.get(request.getPos())) {
                        if (waitRequest.getLockElement().getLockMode().equals("read")){
                            StreamObserver<Empty> waiterResponse = waitRequest.getResponseObserver();

                            waiterResponse.onNext(Empty.newBuilder().build());
                            waiterResponse.onCompleted();
                        }
                    }
                }
                //Verify if next lock is a read one, if yes, unlock all read lock requests

                System.out.println("Lock released on position:" + waiter.getLockElement().getPos()  + " in transaction: " + waiter.getTid());
                StreamObserver<Empty> waiterResponse = waiter.getResponseObserver();
                waiterResponse.onNext(Empty.newBuilder().build());
                waiterResponse.onCompleted();
            } else {

                locks.set(request.getPos(),null);
            }
        }
    }
}
