package isos.tp1.isyiesd.cestc.sertplm;

import com.google.protobuf.Empty;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import io.grpc.stub.StreamObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import lockManager.ILockManagerGrpc;
import lockManager.LockRequest;
import lockManager.ResourceElement;
import lockManager.Response;
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
    //Registo das referencias "responseObserver"
    private final HashMap<Integer, StreamObserver<Response>> lockRequests = new HashMap<>();
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
    public void getLocks(LockRequest req, StreamObserver<Response> responseObserver) {
        lock.lock();
        try{
            System.out.println(formatter.format(new Date())+": Set of Locks required for Transaction: " + req.getTid());
            lockRequests.put(req.getTid(), responseObserver);
            //põe a info das lock que esta transacção precisa
            transactionScopes.put(req.getTid(), req.getElementsList());
            //fast-path
            if(tryToObtainLocks(req.getElementsList())) {
                lockRequests.remove(req.getTid());
                System.out.println(formatter.format(new Date())+": Locks Obtained for Transaction: " + req.getTid());
                responseObserver.onNext(Response.newBuilder().setStatus(true).build());
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
                    lockRequests.remove(req.getTid());
                    responseObserver.onError(e);
                    return;
                }
                if(lockRequests.get(req.getTid()) == null) {
                    return;
                }
                if (tryToObtainLocks(req.getElementsList())) {
                    lockRequests.remove(req.getTid());
                    System.out.println(formatter.format(new Date())+": Locks Obtained for Transaction: " + req.getTid());
                    responseObserver.onNext(Response.newBuilder().setStatus(true).build());
                    responseObserver.onCompleted();
                    return;
                }
                remaining = deadline - System.currentTimeMillis();
                if (remaining <= 0) {
                    lockRequests.remove(req.getTid());
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

    private void releaseLocks(int tid) {
        //obtem as locks desta transação
        List<ResourceElement> elementsToUnlock = transactionScopes.get(tid);
        if(elementsToUnlock != null) {
            for (ResourceElement le : elementsToUnlock) {
                LockElement lockElement = elementLockResources[le.getServerID()][le.getElementIndex()];
                lockElement.isReadLockAvailable = true;
                lockElement.isWriteLockAvailable = true;
            }
            transactionScopes.remove(tid);
            //sinaliza pedidos de obtenção de locks à espera para obter.
        }
        waitingRequests.signalAll();

        System.out.println(formatter.format(new Date())+": Released locks of Transaction: " +
          tid);
    }

    @Override
    public void unlock(UnlockRequest req, StreamObserver<Empty> responseObserver) {
        lock.lock();
        try {
            releaseLocks(req.getTid());

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void abortRequest(LockRequest request, StreamObserver<Empty> responseObserver) {
        lock.lock();
        try {
            System.out.println(formatter.format(new Date())+": Aborted lock request for Transaction: " +
              request.getTid());
            StreamObserver<Response> ro  =  lockRequests.remove(request.getTid());
            if(ro != null) {
                ro.onNext(Response.newBuilder().setStatus(false).build());
                ro.onCompleted();
            } else {
                //release locks
                releaseLocks(request.getTid());
            }
            waitingRequests.signalAll();
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } finally {
            lock.unlock();
        }
    }
}
