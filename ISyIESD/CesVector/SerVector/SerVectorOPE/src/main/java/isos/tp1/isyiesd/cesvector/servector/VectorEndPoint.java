package isos.tp1.isyiesd.cesvector.servector;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import transactionManagerAX.Result;
import transactionManagerXA.ITransactionManagerXAGrpc;
import transactionManagerXA.ITransactionManagerXAGrpc.ITransactionManagerXABlockingStub;
import transactionManagerXA.RegistryMessage;
import vector.VectorResponse;


public class VectorEndPoint {
    private final String vectorServiceName;

    private final int[] vector = {300, 234, 56, 789};

    //Registo de quais as transações que estão a fazer operações no vector
    LinkedList<TransactionRegister> transactionsRegistry;
    //Registo de quais as operações que cada transação quer fazer
    HashMap<Integer, Transac> transacHistory;
    //Proxy ao Transaction manager XA-proto
    ITransactionManagerXABlockingStub tmProxy;
    private final SimpleDateFormat formatter;

    public VectorEndPoint(String tmIP, int tmPort, String vectorServiceName) {
        this.vectorServiceName = vectorServiceName;
        transacHistory = new HashMap<>();
        transactionsRegistry = new LinkedList<>();
        ManagedChannel tmChannel = ManagedChannelBuilder.forAddress(tmIP, tmPort)
          .usePlaintext()
          .build();
        tmProxy = ITransactionManagerXAGrpc.newBlockingStub(tmChannel);
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(formatter.format(new Date())+": Vector Service: "+vectorServiceName+" Initiated.");
    }

    public void read(int tid, int pos, StreamObserver<VectorResponse> responseObserver) {
        System.out.println(formatter.format(new Date())+": Read Called on index: " + pos + " for Transaction: "+ tid);
        //verifica se a transação está no registo de transações.
        if(getTransactionRegisterIndex(tid) == -1) {
            //adiciona o registo da participação da transação
            transactionsRegistry.add(new TransactionRegister(tid));
            //regista a participação no transation Manager
            tmProxy.xaReg(RegistryMessage.newBuilder().setTid(tid).setSender(vectorServiceName).build());
            System.out.println(formatter.format(new Date())+": Registered Transaction "+tid+" in Transaction Manager. " +
              "Read Op.");
        }
        //não regista a operação de read pois é "inofenciva"
        responseObserver.onNext(VectorResponse.newBuilder().setValue(vector[pos]).build());
        responseObserver.onCompleted();
    }

    public void write(int tid, int pos, int value, StreamObserver<Empty> responseObserver) {
        System.out.println(formatter.format(new Date())+": Write Called on index: " + pos + " for Transaction: "+ tid);
        //verifica se a transação está no registo de transações.
        if(getTransactionRegisterIndex(tid) == -1) {
            //adiciona o registo da participação da transação
            transactionsRegistry.add(new TransactionRegister(tid));
            //regista a participação no transation Manager
            tmProxy.xaReg(RegistryMessage.newBuilder().setTid(tid).setSender(vectorServiceName).build());
            System.out.println(formatter.format(new Date())+": Registered Transaction "+tid+" in Transaction Manager. " +
              "Write Op.");
        }
        //regista a operação de write pois pode precisar de ser cancelada.
        Transac t = transacHistory.get(tid);
        //se o registo de operações está vazio.
        if(t == null) {
            t = new Transac();
            t.writes.add(new Write(pos, value));
            //adiciona registo de write na transacção
            transacHistory.put(tid, t);
        } else {
            //adiciona registo de write na transacção
            t.writes.add(new Write(pos, value));
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public void prepare(int tid, StreamObserver<Result> responseObserver) {
        if(getTransactionRegisterIndex(tid) != -1) {
            responseObserver.onNext(Result.newBuilder().setStatus(true).build());
            System.out.println(formatter.format(new Date())+": Prepared to Commit Transaction: "+ tid);
        } else {
            responseObserver.onNext(Result.newBuilder().setStatus(false).build());
            System.out.println(formatter.format(new Date())+": Failed to Prepare Commit for Transaction: "+ tid);
        }
        responseObserver.onCompleted();
    }

    public void commit(int tid, StreamObserver<Empty> responseObserver) {
        //obtem as operações da transação
        Transac t = transacHistory.get(tid);
        //Efectiva as alterações ao vector
        for(Write w : t.writes) {
            vector[w.pos] = w.newValue;
        }
        System.out.println(formatter.format(new Date())+": Committed changes of Transaction: "+ tid);
        //obtem o index do registo de transações
        int trIndex = getTransactionRegisterIndex(tid);
        //remove a transação do registo de transações
        transactionsRegistry.remove(trIndex);
        //remove o registo das operações da transação
        transacHistory.remove(tid);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    public void rollback(int tid, StreamObserver<Empty> responseObserver) {
        System.out.println(formatter.format(new Date())+": Rollback Called for Transaction: "+ tid);
        //obtem o index do registo de transações
        int trIndex = getTransactionRegisterIndex(tid);
        //remove a transação do registo de transações
        transactionsRegistry.remove(trIndex);
        //remove o registo das operações da transação
        transacHistory.remove(tid);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    private int getTransactionRegisterIndex(int tid) {
        for(int i = 0; i < transactionsRegistry.size(); i++) {
            if(transactionsRegistry.get(i).transacID == tid) {
                return i;
            }
        }
        return -1;
    }

    public int getSum() {
        int sum = 0;
        for(int val : vector) {
            sum = sum + val;
        }
        return sum;
    }
}
