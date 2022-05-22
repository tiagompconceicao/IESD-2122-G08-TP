package isos.tp1.isyiesd.cestc.sertm;

import ICoordinator.ServiceEndpoint;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import transactionManagerAX.Result;
import transactionManagerAX.Transaction;


public class TransactionManagerV2 {

    List<ServiceEndpoint> servers;

    // Vector Services (RM - Resource Manager) que participam nas transacções.
    HashMap<Integer, LinkedList<VectorEndpointConnInfo>> transactionsRMs = new HashMap<>();
    //iterador que fornece um ID às transacções
    private int maxTid = 0;
    private Object lock = new Object();
    //espera das respostas ao rollback, committed, e rollback_prepared
    private final int timeout = 30000;
    private final SimpleDateFormat formatter;

    public TransactionManagerV2(List<ServiceEndpoint> servers) {
        this.servers = servers;
        this.formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(formatter.format(new Date())+": Transaction Manager Initiated.");
    }

    public void begin(StreamObserver<transactionManagerTX.Transaction> responseObserver) {
        synchronized (lock) {
            //obter um T-id novo
            int tid = maxTid;
            //incrementar o T-id
            maxTid++;
            System.out.println(formatter.format(new Date())+": Transaction Begun with T-id: " + tid);
            responseObserver.onNext(transactionManagerTX.Transaction.newBuilder().setTid(tid).build());
            responseObserver.onCompleted();
        }
    }

    public void commit(int tID, StreamObserver<transactionManagerTX.Result> responseObserver) {
        System.out.println(formatter.format(new Date())+": Commit Called for Transaction: " + tID);
        //Obtem os vector services que participam nesta transação
        LinkedList<VectorEndpointConnInfo> tRMs = transactionsRMs.get(tID);
        //Call prepare to commit on all vector services (keep result in "readyToCommit")
        ListenableFuture<Result>[] readyToCommit = new ListenableFuture[tRMs.size()];
        for(int i=0; i < tRMs.size(); i++) {
            readyToCommit[i] = tRMs.get(i).serverProxy.xaPrepare(Transaction.newBuilder().setTid(tID).build());
        }
        //Check if all "readyToCommit" are indeed true
        LinkedList<Integer> readyToCommitServers = new LinkedList<>();
        //to keep check if some prepare as failed
        boolean serverFailedToPrepare = false;
        for(int i=0; i < tRMs.size(); i++) {
            try {
                if(!readyToCommit[i].get(timeout, TimeUnit.MILLISECONDS).getStatus()) {
                    System.out.println("    -Server: "+tRMs.get(i).name +" failed to Prepare.");
                    serverFailedToPrepare = true;
                } else {
                    System.out.println("    -Server: "+tRMs.get(i).name +" prepared successfully.");
                    readyToCommitServers.add(i);
                }
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }
        //Se algum vector service falhou a preparar-se, dá-se rollback aqueles que deram prepare com
        //sucesso.
        if(serverFailedToPrepare) {
            responseObserver.onNext(transactionManagerTX.Result.newBuilder().setStatus(false).build());
            responseObserver.onCompleted();
            rollBackReadyToCommit(tID, readyToCommitServers);
            return;
        }
        //O prepare to commit deu-se com sucesso em todos os vector services, dá-se então o commit
        ListenableFuture[] committed = new ListenableFuture[tRMs.size()];
        for(int i=0; i < tRMs.size(); i++) {
            committed[i] = tRMs.get(i).serverProxy.xaCommit(Transaction.newBuilder().setTid(tID).build());
        }
        //Espera-se pelas respostas de todos os commits
        for(int i=0; i < tRMs.size(); i++) {
            try {
                committed[i].get(timeout, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }
        responseObserver.onNext(transactionManagerTX.Result.newBuilder().setStatus(true).build());
        responseObserver.onCompleted();
        //remove a info de participação na transação
        transactionsRMs.remove(tID);
        System.out.println(formatter.format(new Date())+": Commit Completed for Transaction: " + tID);
    }

    private void rollBackReadyToCommit(int tID, LinkedList<Integer> readyToCommitServers) {
        System.out.println(formatter.format(new Date())+": Commit Cancelled for Transaction: " + tID +
          " Rolling back..");
        //Obtem os vector services que participam nesta transação
        LinkedList<VectorEndpointConnInfo> tRMs = transactionsRMs.get(tID);
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
        //remove a info de participação na transação
        transactionsRMs.remove(tID);
    }


    public void rollback(int tID, StreamObserver<Empty> responseObserver) {
        System.out.println(formatter.format(new Date())+": RollBack Called for Transaction: " + tID);
        //Obtem os vector services que participam nesta transação
        LinkedList<VectorEndpointConnInfo> tRMs = transactionsRMs.get(tID);
        ListenableFuture[] rolledBack = new ListenableFuture[tRMs.size()];
        //Manda dar Rollback a todos os vector services que participaram na transação
        for(int i=0; i < tRMs.size(); i++) {
            rolledBack[i] = tRMs.get(i).serverProxy.xaRollback(Transaction.newBuilder().setTid(tID).build());
        }
        //Espera pela resposta dos vector services ao rollback
        for(int i=0; i < tRMs.size(); i++) {
            try {
                rolledBack[i].get(timeout, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                responseObserver.onError(e);
            }
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
        //remove a info de participação na transação
        transactionsRMs.remove(tID);
    }

    public void registerVectorParticipation(int tid, String sender, StreamObserver<Empty> responseObserver) {
        //tenta encontrar a info do vector service correspondente
        ServiceEndpoint vectorEP = findVectorEP(sender);
        if(vectorEP == null) {
            responseObserver.onError(new Exception("Error: Sender Not Recognised by TM!"));
            return;
        }
        //Obtem os vector services que participam nesta transação
        LinkedList<VectorEndpointConnInfo> value = transactionsRMs.get(tid);
        //Cria a informação do vector endpoint
        VectorEndpointConnInfo v = new VectorEndpointConnInfo(vectorEP.getIp(), vectorEP.getPort(), vectorEP.getName());
        //Se ainda não havia nenhum vector endpoint registado para esta transação
        if(value == null)  {
            LinkedList<VectorEndpointConnInfo> newValue = new LinkedList<>();
            newValue.add(v);
            transactionsRMs.put(tid, newValue);
        } else {
            //Se já havia pelo menos um Vector Service registado
            value.add(v);
        }
        System.out.println(formatter.format(new Date())+": Vector Participation for: "+sender+" " +
          "registered to Transaction: " + tid);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    private ServiceEndpoint findVectorEP(String sender) {
        //Encontra o vector endpoint info pelo nome de servidor
        for (ServiceEndpoint vep : servers) {
            if(vep.getName().equals(sender)) {
                return vep;
            }
        }
        return null;
    }
}
