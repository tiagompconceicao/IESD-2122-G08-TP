package isos.tp1.isyiesd.cestc.sertplm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lockManager.ILockManagerGrpc;
import lockManager.LockRequest;
import lockManager.ResourceElement;
import lockManager.UnlockRequest;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ConcurrencyManager extends ILockManagerGrpc.ILockManagerImplBase {
    private final LockElement[][] elementLockResources;
    private final Lock lock = new ReentrantLock();
    private final Condition waitingRequests = lock.newCondition();
    private final HashMap<Integer, List<ResourceElement>> transactionScopes = new HashMap<>();

    ConcurrencyManager(int numbOfServers, int resourceArraySize) {
        elementLockResources = new LockElement[numbOfServers][resourceArraySize];
        for (int i = 0; i < numbOfServers; i++){
            for (int j = 0; j < resourceArraySize; j++){
                elementLockResources[i][j] = new LockElement();
            }
        }
    }

    //lockType_1 = read, lockType_2 = write
    @Override
    public void getLocks(LockRequest req, StreamObserver<Empty> responseObserver) {
        lock.lock();
        try{
            transactionScopes.put(req.getTid(), req.getElementsList());
            //fast-path
            if(tryToObtainLocks(req.getElementsList())) {
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
                return;
            }

            int timeoutMillis = 30000;
            long deadline = System.currentTimeMillis() + timeoutMillis;
            long remaining = deadline - System.currentTimeMillis();
            //wait-path
            while (true) {
                try {
                    waitingRequests.await(remaining, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    responseObserver.onError(e);
                    responseObserver.onCompleted();
                    return;
                }
                if (tryToObtainLocks(req.getElementsList())) {
                    responseObserver.onNext(Empty.newBuilder().build());
                    responseObserver.onCompleted();
                    return;
                }
                remaining = deadline - System.currentTimeMillis();
                if (remaining <= 0) {
                    responseObserver.onError(new TimeoutException());
                    responseObserver.onCompleted();
                    return;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    private boolean tryToObtainLocks(List<ResourceElement> elementsToLock) {
        if(!areLocksObtainable(elementsToLock)) return false;
        obtainLocks(elementsToLock);
        return true;
    }

    private boolean areLocksObtainable(List<ResourceElement> elementsToLock) {
        for(ResourceElement re : elementsToLock) {
            LockElement lockElement = elementLockResources[re.getServerID()][re.getElementIndex()];
            if (!(re.getLockType() == 1 && lockElement.isReadLockAvailable || re.getLockType() == 2 && lockElement.isWriteLockAvailable)) {
                return false;
            }
        }
        return true;
    }

    private void obtainLocks(List<ResourceElement> elementsToLock) {
        for(ResourceElement re : elementsToLock) {
            LockElement lockElement = elementLockResources[re.getServerID()][re.getElementIndex()];
            // READ
            if (re.getLockType() == 1 && lockElement.isReadLockAvailable ) {
                lockElement.isWriteLockAvailable = false;
            }
            // WRITE
            if(re.getLockType() == 2 && lockElement.isWriteLockAvailable) {
                lockElement.isWriteLockAvailable = false;
                lockElement.isReadLockAvailable = false;
            }
        }
    }

    @Override
    public void unlock(UnlockRequest req, StreamObserver<Empty> responseObserver) {
        lock.lock();
        try {
            List<ResourceElement> elementsToUnlock = transactionScopes.get(req.getTid());
            for (ResourceElement le : elementsToUnlock) {
                LockElement lockElement = elementLockResources[le.getServerID()][le.getElementIndex()];
                lockElement.isReadLockAvailable = true;
                lockElement.isWriteLockAvailable = true;
            }
            transactionScopes.remove(req.getTid());
            waitingRequests.signalAll();

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } finally {
            lock.unlock();
        }
    }
}
