package isos.tp1.isyiesd.cestc.sercoordinator;

import ICoordinator.ICoordinatorGrpc;
import com.google.protobuf.Empty;
import getSum.ICheckSumGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import io.grpc.stub.StreamObserver;
import ICoordinator.ServiceEndpoint;
import ICoordinator.VectorServices;
import ICoordinator.Number;
import ICoordinator.Result;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import vector.IVectorGrpc;

public class Coordinator extends ICoordinatorGrpc.ICoordinatorImplBase {
    //info do TM
    private ServiceEndpoint tm = null;
    //info do TPLM
    private ServiceEndpoint tplm = null;
    //info dos Vector Services
    private final LinkedList<ServiceEndpoint> vectorServices = new LinkedList<>();
    private final Object lock = new Object();
    //expected number of vector services
    private final int numberOfVectorServices;
    //current number of registered vector services
    private int currentNumberOfVectorServices;
    private final SimpleDateFormat formatter;
    //timeout de espera que os Vector Services se registem para dar a sua info
    private final int timeoutMillisVecReg = 120000;
    //timeout de espera que o TM se registe para dar a sua info
    private final int timeoutMillisTM = 120000;

    public Coordinator(int numberOfVectorServices) {
        this.numberOfVectorServices = numberOfVectorServices;
        this.currentNumberOfVectorServices = 0;
        this.formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(formatter.format(new Date())+": Coordinator Initiated.");
    }

    @Override
    public void registerVectorService(ServiceEndpoint request, StreamObserver<Empty> responseObserver) {
        synchronized (lock) {
            //Adiciona a informação de um Vector Service em memória.
            vectorServices.add(request);
            System.out.println(formatter.format(new Date())+": VectorService: " + request.getName() + " Registered.");
            currentNumberOfVectorServices++;
            //Quando obtiver o número esperado de Vector Services.
            if(currentNumberOfVectorServices == numberOfVectorServices) {
                //Notifica esperas sobre o lock (nomeadamente o TM que pode estar à espera da
                // informação dos Vector Services)
                lock.notifyAll();
                System.out.println("    -Finished Registering All Vector Services.");
            }
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getVectorServices(Empty request, StreamObserver<VectorServices> responseObserver) {
        synchronized (lock) {
            System.out.println(formatter.format(new Date())+": Registered Vector Services where Requested.");
            //Se os Vector Services não estiverem todos registados:
            if(currentNumberOfVectorServices != numberOfVectorServices) {
                System.out.println("    -VectorServices are missing register. Waiting..");
                long deadline = System.currentTimeMillis() + timeoutMillisVecReg;
                long remaining = deadline - System.currentTimeMillis();
                //wait-path
                while (true) {
                    try {
                        lock.wait(remaining);
                    } catch (InterruptedException e) {
                        responseObserver.onError(e);
                        return;
                    }
                    //Se todos os vector services já se tiverem registado
                    if (currentNumberOfVectorServices == numberOfVectorServices) {
                        System.out.println(formatter.format(new Date())+": Wait Done. All Vector Services " +
                          "Registered, Giving Info to Requesters.");
                        VectorServices.Builder vsB = VectorServices.newBuilder();
                        for(ServiceEndpoint sep : vectorServices) {
                            vsB.addVectors(sep);
                        }
                        responseObserver.onNext(vsB.build());
                        responseObserver.onCompleted();
                        return;
                    }
                    remaining = deadline - System.currentTimeMillis();
                    if (remaining <= 0) {
                        System.out.println(formatter.format(new Date())+": Timeout Reached Cancelling" +
                          "Vector Services info requests.");
                        responseObserver.onError(new TimeoutException());
                        return;
                    }
                }
            }
            VectorServices.Builder vsB = VectorServices.newBuilder();
            for(ServiceEndpoint sep : vectorServices) {
               vsB.addVectors(sep);
            }
            System.out.println(formatter.format(new Date())+": All Vector Services present and given to requester.");
            responseObserver.onNext(vsB.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void registerTM(ServiceEndpoint request, StreamObserver<Empty> responseObserver) {
        synchronized (lock) {
            tm = request;
            System.out.println(formatter.format(new Date())+": TM Registered.");
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
            //Notifica esperas sobre o lock (nomeadamente Vector Services que podem estar à espera da
            // informação do TM).
            lock.notifyAll();
        }
    }

    @Override
    public void getTM(Empty request, StreamObserver<ServiceEndpoint> responseObserver) {
        synchronized (lock) {
            System.out.println(formatter.format(new Date())+": TM info requested.");
            if(tm == null) {
                long deadline = System.currentTimeMillis() + timeoutMillisTM;
                long remaining = deadline - System.currentTimeMillis();
                //wait-path
                while (true) {
                    try {
                        System.out.println(formatter.format(new Date())+": waiting TM request notified, " +
                          "still waiting on TM info.");
                        lock.wait(remaining);
                    } catch (InterruptedException e) {
                        responseObserver.onError(e);
                        return;
                    }
                    if (tm != null) {
                        System.out.println(formatter.format(new Date())+": TM info given to requester.");
                        responseObserver.onNext(tm);
                        responseObserver.onCompleted();
                        return;
                    }
                    remaining = deadline - System.currentTimeMillis();
                    if (remaining <= 0) {
                        System.out.println(formatter.format(new Date())+": Timeout Reached Cancelling" +
                          "TM info requests.");
                        responseObserver.onError(new TimeoutException());
                        return;
                    }
                }
            }
            System.out.println(formatter.format(new Date())+": TM info given to requester.");
            responseObserver.onNext(tm);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void registerTPLM(ServiceEndpoint request, StreamObserver<Empty> responseObserver) {
        synchronized (lock) {
            tplm = request;
            System.out.println(formatter.format(new Date())+": TPLM Registered.");
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getTPLM(Empty request, StreamObserver<ServiceEndpoint> responseObserver) {
        synchronized (lock) {
            System.out.println(formatter.format(new Date())+": TPLM info given to requester.");
            responseObserver.onNext(tplm);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getNumberOfVectorServices(Empty request, StreamObserver<Number> responseObserver) {
        synchronized (lock) {
            System.out.println(formatter.format(new Date())+": Number of Vector Servers given to requester.");
            responseObserver.onNext(Number.newBuilder().setValue(numberOfVectorServices).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void checkInvariant(Empty request, StreamObserver<Result> responseObserver) {
        synchronized (lock) {
            System.out.println(formatter.format(new Date())+": Check Invariant of Vector Services " +
              "requested.");
            int sum = 0;
            for (ServiceEndpoint sep : vectorServices) {
                ManagedChannel sepMC = ManagedChannelBuilder
                  .forAddress(sep.getIp(), sep.getPort())
                  .usePlaintext()
                  .build();
                ICheckSumGrpc.ICheckSumBlockingStub sepProxy = ICheckSumGrpc.newBlockingStub(sepMC);
                int sumOfVector = sepProxy.getSum(Empty.newBuilder().build()).getValue();
                System.out.println("    -Sum of Vector Service: "+sep.getName()+" is: "+sumOfVector);
                sum = sum + sumOfVector;
            }
            //sum of starting values in vector services
            int vectorSum = 300 + 234 + 56 + 789;
            if(sum == numberOfVectorServices* vectorSum) {
                System.out.println(formatter.format(new Date())+": Check Invariant of Vector Services " +
                  "is valid.");
                responseObserver.onNext(Result.newBuilder().setStatus(true).build());
            } else {
                System.out.println(formatter.format(new Date())+": Check Invariant of Vector Services " +
                  "is not valid.");
                responseObserver.onNext(Result.newBuilder().setStatus(false).build());
            }
            responseObserver.onCompleted();
        }
    }
}
