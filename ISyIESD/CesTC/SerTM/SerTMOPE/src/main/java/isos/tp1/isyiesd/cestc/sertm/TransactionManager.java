package isos.tp1.isyiesd.cestc.sertm;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import transactionManagerAX.ITransactionManagerAXGrpc;
import transactionManagerAX.Transaction;
import transactionManagerAX.Variance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TransactionManager {

    //Save transaction ids and save Vectors operations of each transaction
    private final HashMap<Integer,List<String>> transactions = new HashMap<>();
    private final List<ManagedChannel> servers = new ArrayList<>();
    private final List<ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub> stubs = new ArrayList<>();
    private final Object lock = new Object();

    private int maxTID = 0;

    public TransactionManager(){

    }

    public int createTransaction(){
        int tid;
        synchronized (lock){
            tid = ++maxTID;
            transactions.put(tid,new ArrayList<>());
        }

        return tid;
    }

    public void registerActivity(int tid, String server){
        synchronized (lock){
            transactions.get(tid).add(server);
        }
    }

    public boolean commit(int tid) throws InterruptedException {
        boolean res;
        synchronized (lock) {
            createTransactionConnections(tid);
            if(checkVariance(tid)){
                for (ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub: stubs) {
                    stub.xaCommit(Transaction.newBuilder().setTid(tid).build());
                }
                res = true;
            } else {
                for (ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub: stubs) {
                    stub.xaRollback(Transaction.newBuilder().setTid(tid).build());
                }
                res = false;
            }

            for (ManagedChannel channel:servers){
                channel.shutdown().awaitTermination(1, TimeUnit.MILLISECONDS);
            }
            servers.clear();
            stubs.clear();
        }

        return res;
    }

    public boolean rollback(int tid) throws InterruptedException {
        synchronized (lock){
            createTransactionConnections(tid);
            for (ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub: stubs) {
                stub.xaRollback(Transaction.newBuilder().setTid(tid).build());
            }

            for (ManagedChannel channel:servers){
                channel.shutdown().awaitTermination(1, TimeUnit.MILLISECONDS);
            }
        }
        return true;
    }

    private boolean checkVariance(int tid){
        int sum = 0;
        for (ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub: stubs) {
            Variance variance = stub.xaPrepare(Transaction.newBuilder().setTid(tid).build());
            sum += variance.getValue();
        }
        return sum == 0;
    }


    private void createTransactionConnections(int tid){
        servers.clear();
        stubs.clear();
        for (String server: transactions.get(tid)){
            ManagedChannel channel = createConnection(server, 9000);
            servers.add(channel);
            ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub = ITransactionManagerAXGrpc.newBlockingStub(channel);
            stubs.add(stub);
        }
    }

    private ManagedChannel createConnection(String serverIP, int serverPort){
        return ManagedChannelBuilder.forAddress(serverIP, serverPort)
                .usePlaintext()
                .build();
    }
}
