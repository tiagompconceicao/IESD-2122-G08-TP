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
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import vector.IVectorGrpc;

public class Coordinator extends ICoordinatorGrpc.ICoordinatorImplBase {
    private ServiceEndpoint tm = null;
    private ServiceEndpoint tplm = null;
    private LinkedList<ServiceEndpoint> vectorServices = new LinkedList<>();
    private final Object lock = new Object();
    private int numberOfVectorServices;
    private int currentNumberOfVectorServices;
    private int vectorSum = 300 + 234 + 56 + 789;

    public Coordinator(int numberOfVectorServices) {
        this.numberOfVectorServices = numberOfVectorServices;
        this.currentNumberOfVectorServices = 0;
    }

    @Override
    public void registerVectorService(ServiceEndpoint request, StreamObserver<Empty> responseObserver) {
        synchronized (lock) {
            System.out.println("VectorService: " + request.getName() + " Registered.");
            vectorServices.add(request);
            currentNumberOfVectorServices++;
            if(currentNumberOfVectorServices == numberOfVectorServices) {
                lock.notifyAll();
                System.out.println("Finished Registering All Vector Services.");
            }
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getVectorServices(Empty request, StreamObserver<VectorServices> responseObserver) {
        synchronized (lock) {
            System.out.println("getVectorServices called.");
            if(currentNumberOfVectorServices != numberOfVectorServices) {
                int timeoutMillis = 60000;
                long deadline = System.currentTimeMillis() + timeoutMillis;
                long remaining = deadline - System.currentTimeMillis();
                //wait-path
                while (true) {
                    try {
                        System.out.println("VectorServices are missing register. Waiting..");
                        lock.wait(remaining);
                    } catch (InterruptedException e) {
                        responseObserver.onError(e);
                        return;
                    }
                    if (currentNumberOfVectorServices == numberOfVectorServices) {
                        System.out.println("All Vector Services given. Wait Done.");
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
                        responseObserver.onError(new TimeoutException());
                        return;
                    }
                }
            }
            System.out.println("All Vector Services given.");
            VectorServices.Builder vsB = VectorServices.newBuilder();
            for(ServiceEndpoint sep : vectorServices) {
               vsB.addVectors(sep);
            }
            responseObserver.onNext(vsB.build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void registerTM(ServiceEndpoint request, StreamObserver<Empty> responseObserver) {
        synchronized (lock) {
            System.out.println("TM Registered.");
            tm = request;
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
            lock.notifyAll();
        }
    }

    @Override
    public void getTM(Empty request, StreamObserver<ServiceEndpoint> responseObserver) {
        synchronized (lock) {
            System.out.println("TM Required.");
            if(tm == null) {
                int timeoutMillis = 60000;
                long deadline = System.currentTimeMillis() + timeoutMillis;
                long remaining = deadline - System.currentTimeMillis();
                //wait-path
                while (true) {
                    try {
                        System.out.println("TM still not Registered. Waiting..");
                        lock.wait(remaining);
                    } catch (InterruptedException e) {
                        responseObserver.onError(e);
                        return;
                    }
                    if (tm != null) {
                        System.out.println("TM given.");
                        responseObserver.onNext(tm);
                        responseObserver.onCompleted();
                        return;
                    }
                    remaining = deadline - System.currentTimeMillis();
                    if (remaining <= 0) {
                        responseObserver.onError(new TimeoutException());
                        return;
                    }
                }
            }
            System.out.println("TM given.");
            responseObserver.onNext(tm);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void registerTPLM(ServiceEndpoint request, StreamObserver<Empty> responseObserver) {
        synchronized (lock) {
            System.out.println("TPLM Registered.");
            tplm = request;
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getTPLM(Empty request, StreamObserver<ServiceEndpoint> responseObserver) {
        synchronized (lock) {
            System.out.println("TPLM given.");
            responseObserver.onNext(tplm);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getNumberOfVectorServices(Empty request, StreamObserver<Number> responseObserver) {
        synchronized (lock) {
            System.out.println("Number of VectorServices given.");
            responseObserver.onNext(Number.newBuilder().setValue(numberOfVectorServices).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void checkInvariant(Empty request, StreamObserver<Result> responseObserver) {
        synchronized (lock) {
            int sum = 0;
            for (ServiceEndpoint sep : vectorServices) {
                ManagedChannel sepMC = ManagedChannelBuilder
                  .forAddress(sep.getIp(), sep.getPort())
                  .usePlaintext()
                  .build();
                ICheckSumGrpc.ICheckSumBlockingStub sepProxy = ICheckSumGrpc.newBlockingStub(sepMC);
                sum = sum + sepProxy.getSum(Empty.newBuilder().build()).getValue();
            }
            if(sum == numberOfVectorServices*vectorSum) {
                responseObserver.onNext(Result.newBuilder().setStatus(true).build());
            } else {
                responseObserver.onNext(Result.newBuilder().setStatus(false).build());
            }
            responseObserver.onCompleted();
        }
    }
}
