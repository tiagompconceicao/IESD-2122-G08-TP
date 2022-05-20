import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ConcurrencyManager implements iConcurrencyManager {
    private int numbOfServers;
    private int resourceArraySize;
    private LockElement[][] elementLockResources = new LockElement[numbOfServers][resourceArraySize];
    private final Lock lock = new ReentrantLock();
    private final Condition waitingRequests = lock.newCondition();
    private final int timeoutMillis = 3000;
    private HashMap<Integer, ResourceElement[]> transactionScopes = new HashMap<>();

    ConcurrencyManager(int numbOfServers, int resourceArraySize) {
        this.numbOfServers = numbOfServers;
        this.resourceArraySize = resourceArraySize;
    }

    //lockType_1 = read, lockType_2 = write
    @Override
    public void lock(LockRequestInfo req, Boolean responseObserver) {
        lock.lock();
        try{
            transactionScopes.put(req.Tid, req.elementsToLock);
            //fast-path
            if(tryToObtainLocks(req.elementsToLock)) {
                responseObserver = true; // reply to client "lock obtained"
                transactionScopes.remove(req.Tid);
                return;
            }
            long deadline = System.currentTimeMillis() + timeoutMillis;
            long remaining = deadline - System.currentTimeMillis();
            //wait-path
            while (true) {
                try {
                    waitingRequests.await(remaining, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    responseObserver = false; //reply to client "interruptedException"
                    return;
                }
                if (tryToObtainLocks(req.elementsToLock)) {
                    responseObserver = true; // reply to client "lock obtained"
                    transactionScopes.remove(req.Tid);
                    return;
                }
                remaining = deadline - System.currentTimeMillis();
                if (remaining <= 0) {
                    responseObserver = false; //reply to client "timeoutException"
                    return;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    private boolean tryToObtainLocks(ResourceElement[] elementsToLock) {
        if(!areLocksObtainable(elementsToLock)) return false;
        obtainLocks(elementsToLock);
        return true;
    }

    private boolean areLocksObtainable(ResourceElement[] elementsToLock) {
        for(ResourceElement re : elementsToLock) {
            LockElement lockElement = elementLockResources[re.serverID][re.elementIndex];
            if (!(re.lockType == 1 && lockElement.isReadLockAvailable || re.lockType == 2 && lockElement.isWriteLockAvailable)) {
                return false;
            }
        }
        return true;
    }

    private void obtainLocks(ResourceElement[] elementsToLock) {
        for(ResourceElement re : elementsToLock) {
            LockElement lockElement = elementLockResources[re.serverID][re.elementIndex];
            // READ
            if (re.lockType == 1 && lockElement.isReadLockAvailable ) {
                lockElement.isWriteLockAvailable = false;
            }
            // WRITE
            if(re.lockType == 2 && lockElement.isWriteLockAvailable) {
                lockElement.isWriteLockAvailable = false;
                lockElement.isReadLockAvailable = false;
            }
        }
    }

    @Override
    public void unlock(UnlockRequestInfo req) {
        synchronized (lock) {
            ResourceElement[] elementsToUnlock = transactionScopes.get(req.Tid);
            for (ResourceElement le : elementsToUnlock) {
                LockElement lockElement = elementLockResources[le.serverID][le.elementIndex];
                lockElement.isReadLockAvailable = true;
                lockElement.isWriteLockAvailable = true;
            }
            waitingRequests.signalAll();
        }
    }
}
