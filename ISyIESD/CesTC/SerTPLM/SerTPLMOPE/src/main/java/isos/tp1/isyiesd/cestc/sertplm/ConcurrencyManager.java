package isos.tp1.isyiesd.cestc.sertplm;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    //Logic Locks associated with all elements of all vectors
    private final LockElement[][] elementLockResources;
    //Lock para gerir acesso aos recursos "elementLockResources"
    private final Lock lock = new ReentrantLock();
    private final Condition waitingRequests = lock.newCondition();
    //mapa entre T-id e os Logic Locks que precisam
    private final HashMap<Integer, List<ResourceElement>> transactionScopes = new HashMap<>();
    private final SimpleDateFormat formatter;
    //espera que uma transação faz para obter as locks que neccessita
    private final int timeoutMillisLocks = 120000;

    ConcurrencyManager(int numbOfServers, int resourceArraySize) {
        //instancia todos os logic lock elements
        elementLockResources = new LockElement[numbOfServers][resourceArraySize];
        for (int i = 0; i < numbOfServers; i++){
            for (int j = 0; j < resourceArraySize; j++){
                elementLockResources[i][j] = new LockElement();
            }
        }
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(formatter.format(new Date())+": TPLM (Concurrency Manager) initiated.");
    }

    //lockType_1 = read, lockType_2 = write
    @Override
    public void getLocks(LockRequest req, StreamObserver<Empty> responseObserver) {
        System.out.println(formatter.format(new Date())+": Set of Locks required for Transaction: " + req.getTid());
        lock.lock();
        try{
            //põe a info das lock que esta transacção precisa
            transactionScopes.put(req.getTid(), req.getElementsList());
            //fast-path
            if(tryToObtainLocks(req.getElementsList())) {
                System.out.println(formatter.format(new Date())+": Set of Locks given to Transaction: " + req.getTid());
                responseObserver.onNext(Empty.newBuilder().build());
                responseObserver.onCompleted();
                return;
            }
            long deadline = System.currentTimeMillis() + timeoutMillisLocks;
            long remaining = deadline - System.currentTimeMillis();
            //wait-path
            while (true) {
                try {
                    waitingRequests.await(remaining, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    responseObserver.onError(e);
                    return;
                }
                if (tryToObtainLocks(req.getElementsList())) {
                    System.out.println(formatter.format(new Date())+": Set of Locks given to Transaction: " + req.getTid());
                    responseObserver.onNext(Empty.newBuilder().build());
                    responseObserver.onCompleted();
                    return;
                }
                remaining = deadline - System.currentTimeMillis();
                if (remaining <= 0) {
                    responseObserver.onError(new TimeoutException());
                    System.out.println(formatter.format(new Date())+": Lock Request Timed out for Transaction: " + req.getTid());
                    return;
                }
            }
        }finally {
            lock.unlock();
        }
    }

    private boolean tryToObtainLocks(List<ResourceElement> elementsToLock) {
        //Verifica se se podem obter. (ex.: não quer um lock write quando um lock read está obtido)
        if(!areLocksObtainable(elementsToLock)) return false;
        //Obtem as locks logicas dos elementos
        obtainLocks(elementsToLock);
        return true;
    }

    private boolean areLocksObtainable(List<ResourceElement> elementsToLock) {
        //Logica de disponibilidade das locks de cada elemento
        for(ResourceElement re : elementsToLock) {
            LockElement lockElement = elementLockResources[re.getServerID()][re.getElementIndex()];
            if (!(re.getLockType() == 1 && lockElement.isReadLockAvailable ||
              re.getLockType() == 2 && lockElement.isWriteLockAvailable)) {
                return false;
            }
        }
        return true;
    }

    private void obtainLocks(List<ResourceElement> elementsToLock) {
        //Logica de obtenção das locks de cada elemento
        for(ResourceElement re : elementsToLock) {
            LockElement lockElement = elementLockResources[re.getServerID()][re.getElementIndex()];
            // READ lock obtain
            if (re.getLockType() == 1 && lockElement.isReadLockAvailable ) {
                //permite outros reads mas proibe writes
                lockElement.isWriteLockAvailable = false;
            }
            // WRITE lock obtain
            if(re.getLockType() == 2 && lockElement.isWriteLockAvailable) {
                //proibe outros writes e reads
                lockElement.isWriteLockAvailable = false;
                lockElement.isReadLockAvailable = false;
            }
        }
    }

    @Override
    public void unlock(UnlockRequest req, StreamObserver<Empty> responseObserver) {
        lock.lock();
        try {
            //obtem as locks desta transação
            List<ResourceElement> elementsToUnlock = transactionScopes.get(req.getTid());
            for (ResourceElement le : elementsToUnlock) {
                LockElement lockElement = elementLockResources[le.getServerID()][le.getElementIndex()];
                lockElement.isReadLockAvailable = true;
                lockElement.isWriteLockAvailable = true;
            }
            transactionScopes.remove(req.getTid());
            //sinaliza pedidos de obtenção de locks à espera para obter.
            waitingRequests.signalAll();

            System.out.println(formatter.format(new Date())+": Unlocked locks of Transaction: " +
              req.getTid());

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } finally {
            lock.unlock();
        }
    }
}
