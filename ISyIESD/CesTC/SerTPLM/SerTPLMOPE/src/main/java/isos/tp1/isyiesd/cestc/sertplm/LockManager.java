package isos.tp1.isyiesd.cestc.sertplm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import isos.tp1.isyiesd.cestc.sertplm.model.LockItem;
import isos.tp1.isyiesd.cestc.sertplm.model.Waiter;
import lockManager.ILockManagerGrpc;
import lockManager.LockElement;
import lockManager.LockRequest;
import lockManager.UnlockRequest;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class LockManager extends ILockManagerGrpc.ILockManagerImplBase {

    //List with the locks of all vector positions

    private final LockItem[] lockElements= {null,null,null,null};
    private final AtomicReferenceArray<LockItem> locks = new AtomicReferenceArray<>(lockElements);


    //List with the waiting queue for each vector position

    private final ConcurrentLinkedQueue<Waiter> requestsQueue = new ConcurrentLinkedQueue<>();

    public LockManager(){
    }


    //If lock is available then get the lock and returns instantly
    //Or add StreamObserver to the queue and wait
    @Override
    public void getLocks(LockRequest request, StreamObserver<Empty> responseObserver){
        synchronized (locks){

            ArrayList<Boolean> lockStatus = new ArrayList<>(request.getLocksCount());

            for (int i = 0; i< request.getLocksCount(); i++){
                LockElement requestLock = request.getLocks(i);
                LockItem lock = locks.get(requestLock.getPos());

                if ( lock == null){
                    //No lock in position

                    System.out.println("Lock set on position:" + requestLock.getPos() + " in mode: " + requestLock.getLockMode() + " in transaction: " + request.getTid());
                    lockPosition();
                    locks.set(requestLock.getPos(), requestLock);
                    lockStatus.set(i,true);

                } else if (lock.getLockMode().equals("read") && lock.getLockMode().equals(lock.getLockMode())) {
                    //Read lock and the request is also a read lock
                    System.out.println("Lock skipped on position:" + requestLock.getPos() + " in mode: " + requestLock.getLockMode() + " in transaction: " + request.getTid());
                    lockStatus.set(i,true);

                } else {
                    //Will wait because there is a write lock
                    System.out.println("Wait on position:" + requestLock.getPos() + " in mode: " + requestLock.getLockMode() + " in transaction: " + request.getTid());
                    lockStatus.set(i,false);
                }
            }

            ArrayList<LockElement> missingLocks = getMissingLocks(request,lockStatus);
            if (missingLocks.isEmpty()){
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
            } else {
                requestsQueue.add(new Waiter(missingLocks,responseObserver,request.getTid()));
            }



        }
    }

    private ArrayList<LockElement> getMissingLocks(LockRequest request, ArrayList<Boolean> lockStatus) {
        ArrayList<LockElement> missingLocks = new ArrayList<>();

        for (int i = 0; i < request.getLocksCount() ;i++){
            if (!lockStatus.get(i)){
                missingLocks.add(request.getLocks(i));
            }
        }

        return missingLocks;
    }

    @Override
    public void releaseLocks(LockRequest request, StreamObserver<Empty> responseObserver){
        //TODO To be implemented
    }


    //Release unlock
    //If is anyone waiting for the lock of the position released, give lock
    //Or just get lock available
    @Override
    public void unlock(UnlockRequest request, StreamObserver<Empty> responseObeserver){
        synchronized (locks){

            for (int i =0; i < locks.length(); i++){
                if (locks.get(i).getTid() == request.getTid()){
                    //release this lockElement
                    releaseElement(locks.get(i));
                }
            }

            responseObeserver.onNext(Empty.newBuilder().build());
            responseObeserver.onCompleted();
        }
    }

    private void releaseElement(LockElement releasedElement){

        //The resquester client is immediately freed
        //System.out.println("Lock released on position:" + request.getPos()  + " in transaction: " + request.getTid());

        if (requestsQueue.isEmpty()){
            locks.set(releasedElement.getPos(),null);
            return;
        }

        for (Waiter waiter : requestsQueue) {

        }

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
        }


    }
}
