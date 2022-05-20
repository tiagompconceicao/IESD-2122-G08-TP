package isos.tp1.isyiesd.cestc.sertm;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import transactionManagerAX.ITransactionManagerAXGrpc;
import transactionManagerAX.Transaction;
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
            //Get connection to all vector server that the transaction tid communicated
            //Run xaPrepare for all vector servers
            //When all ok, run xaCommit for all vector servers

            //create connections to the vector servers that the transaction tid communicated
            createTransactionConnections(tid);

            //Prepare
            for (ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub: stubs) {
                stub.xaPrepare(Transaction.newBuilder().setTid(tid).build());
            }

            //Commit
            for (ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub: stubs) {
                stub.xaCommit(Transaction.newBuilder().setTid(tid).build());
            }

            for (ManagedChannel channel:servers){
                channel.shutdown().awaitTermination(1, TimeUnit.MILLISECONDS);
            }
            servers.clear();
            stubs.clear();
        }

        return true;
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

    private void createTransactionConnections(int tid){
        servers.clear();
        stubs.clear();
        for (String server: transactions.get(tid)){
            ManagedChannel channel = ManagedChannelBuilder.forAddress(server, 9000)
                    .usePlaintext()
                    .build();
            servers.add(channel);
            ITransactionManagerAXGrpc.ITransactionManagerAXBlockingStub stub = ITransactionManagerAXGrpc.newBlockingStub(channel);
            stubs.add(stub);
        }
    }
}