package isos.tp1.isyiesd.cestc.sertm;

import ICoordinator.ServiceEndpoint;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import transactionManagerAX.Result;
import transactionManagerAX.Transaction;


public class TransactionManagerV2 {

    List<ServiceEndpoint> servers;

    // Servidores (RM - Resource Manager) que participam nas transações.
    HashMap<Integer, LinkedList<VectorEndpoint>> transactionsRMs = new HashMap<>();

    private int maxTid = 0;
    private Object lock = new Object();
    private final int timeout = 5000;

    public TransactionManagerV2(List<ServiceEndpoint> servers) {
        this.servers = servers;
    }

    public void begin(StreamObserver<transactionManagerTX.Transaction> responseObserver) {
        synchronized (lock) {
            int tid = maxTid;
            maxTid++;
            System.out.println("Transaction Begun with id: " + tid);
            responseObserver.onNext(transactionManagerTX.Transaction.newBuilder().setTid(tid).build());
            responseObserver.onCompleted();
        }
    }

    public void commit(int tID, StreamObserver<transactionManagerTX.Result> responseObserver) {
        System.out.println("Commit Called for Transaction: " + tID);
        //Get all Vector Services participating in transaction tID
        LinkedList<VectorEndpoint> tRMs = transactionsRMs.get(tID);
        //Get all Results from "prepare to commit" call, sent to all Vector Services
        ListenableFuture<Result>[] readyToCommit = new ListenableFuture[tRMs.size()];
        for(int i=0; i < tRMs.size(); i++) {
            readyToCommit[i] = tRMs.get(i).serverProxy.xaPrepare(Transaction.newBuilder().setTid(tID).build());
        }
        //Process Response to last calls
        LinkedList<Integer> readyToCommitServers = new LinkedList<>();
        boolean serverFailedToPrepare = false;
        for(int i=0; i < tRMs.size(); i++) {
            try {
                if(!readyToCommit[i].get(timeout, TimeUnit.MILLISECONDS).getStatus()) {
                    System.out.println("Server: "+tRMs.get(i).name +" failed to Prepare.");
                    serverFailedToPrepare = true;
                } else {
                    System.out.println("Server: "+tRMs.get(i).name +" prepared successfully.");
                    readyToCommitServers.add(i);
                }
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }
        if(serverFailedToPrepare) {
            responseObserver.onNext(transactionManagerTX.Result.newBuilder().setStatus(false).build());
            responseObserver.onCompleted();
            rollBackReadyToCommit(tID, readyToCommitServers);
            return;
        }
        ListenableFuture<Empty>[] committed = new ListenableFuture[tRMs.size()];
        for(int i=0; i < tRMs.size(); i++) {
            committed[i] = tRMs.get(i).serverProxy.xaCommit(Transaction.newBuilder().setTid(tID).build());
        }
        for(int i=0; i < tRMs.size(); i++) {
            try {
                committed[i].get(timeout, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }
        responseObserver.onNext(transactionManagerTX.Result.newBuilder().setStatus(true).build());
        responseObserver.onCompleted();
        transactionsRMs.remove(tID);
        System.out.println("Commit Completed for Transaction: " + tID);
    }

    private void rollBackReadyToCommit(int tID, LinkedList<Integer> readyToCommitServers) {
        System.out.println("Commit Cancelled for Transaction: " + tID + " Rolling Back.");
        LinkedList<VectorEndpoint> tRMs = transactionsRMs.get(tID);
        ListenableFuture<Empty>[] doneRollingBack = new ListenableFuture[tRMs.size()];
        for(int i=0; i < readyToCommitServers.size(); i++) {
            doneRollingBack[i] = tRMs
              .get(readyToCommitServers.get(i))
              .serverProxy
              .xaRollback(Transaction.newBuilder()
                .setTid(tID)
                .build()
              );
        }
        transactionsRMs.remove(tID);
    }


    public void rollback(int tID, StreamObserver<Empty> responseObserver) {
        System.out.println("RollBack Called for Transaction: " + tID);
        LinkedList<VectorEndpoint> tRMs = transactionsRMs.get(tID);
        ListenableFuture<Empty>[] rolledBack = new ListenableFuture[tRMs.size()];
        for(int i=0; i < tRMs.size(); i++) {
            rolledBack[i] = tRMs.get(i).serverProxy.xaRollback(Transaction.newBuilder().setTid(tID).build());
        }
        for(int i=0; i < tRMs.size(); i++) {
            try {
                rolledBack[i].get(timeout, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
        transactionsRMs.remove(tID);
    }

    public void registerVectorParticipation(int tid, String sender, StreamObserver<Empty> responseObserver) {
        System.out.println("Vector Participation ("+sender+") Called for Transaction: " + tid);
        ServiceEndpoint vectorEP = findVectorEP(sender);
        if(vectorEP == null) {
            responseObserver.onError(new Exception("Error: Sender Not Recognised by TM!"));
        }
        LinkedList<VectorEndpoint> value = transactionsRMs.get(tid);
        VectorEndpoint v = new VectorEndpoint(vectorEP.getIp(), vectorEP.getPort(), vectorEP.getName());
        if(value == null)  {
            LinkedList<VectorEndpoint> newValue = new LinkedList<>();
            newValue.add(v);
            transactionsRMs.put(tid, newValue);
        } else {
            value.add(v);
            //se nao resultar:
            //transactionsRMs.put(tid, value);
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    private ServiceEndpoint findVectorEP(String sender) {
        for (ServiceEndpoint vep : servers) {
            if(vep.getName().equals(sender)) {
                return vep;
            }
        }
        return null;
    }
}
