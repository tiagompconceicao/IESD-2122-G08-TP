package isos.tp1.isyiesd.cesregistry.serregistry;

import IRegistry.IRegistryGrpc;
import IRegistry.Result;
import IRegistry.ServiceEndpoint;
import IRegistry.Number;
import IRegistry.ServiceRequest;
import IRegistry.VectorServices;
import com.google.protobuf.Empty;
import getSum.ICheckSumGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class Registry extends IRegistryGrpc.IRegistryImplBase {
    //info do TM
    private ServiceEndpoint tm = null;
    //info do TPLM
    private ServiceEndpoint tplm = null;

    private String VECTOR_SERVICES = "Vector";

    //info dos Vector Services
    private final LinkedList<ServiceEndpoint> vectorServices = new LinkedList<>();
    private final ConcurrentHashMap<String,LinkedList<ServiceEndpoint>> services = new ConcurrentHashMap();
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

    public Registry(int numberOfVectorServices) {
        this.numberOfVectorServices = numberOfVectorServices;
        this.currentNumberOfVectorServices = 0;
        this.formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(formatter.format(new Date())+": Coordinator Initiated.");
    }

    @Override
    public void registerService(ServiceEndpoint serviceEndpoint, StreamObserver<Empty> responseObserver){
        synchronized (lock){
            //Verify if endpoint type exists
            if (services.containsKey(serviceEndpoint.getType())) {
                //Verify if service is already registered
                if (getServiceByName(serviceEndpoint.getType(),serviceEndpoint.getName()) != null){
                    responseObserver.onError(new Throwable("Service already exists"));
                    return;
                }
                services.get(serviceEndpoint.getType()).push(serviceEndpoint);
                if (serviceEndpoint.getType().equals(VECTOR_SERVICES)){
                    currentNumberOfVectorServices++;
                }

            } else {
                services.put(serviceEndpoint.getType(),new LinkedList<>());
                services.get(serviceEndpoint.getType()).push(serviceEndpoint);
                if (serviceEndpoint.getType().equals(VECTOR_SERVICES)){
                    currentNumberOfVectorServices++;
                }
            }

            lock.notifyAll();
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getService(ServiceRequest serviceRequest, StreamObserver<ServiceEndpoint> responseObserver){
        synchronized (lock){
            if (!services.containsKey(serviceRequest.getType())){
                responseObserver.onError(new Throwable("Requested service type does not exists"));
                return;
            }

            ServiceEndpoint serviceEndpoint = getService(serviceRequest.getType(),serviceRequest.getName());

            if (serviceEndpoint == null){
                responseObserver.onError(new Throwable("Requested service does not exists"));
                return;
            }

            responseObserver.onNext(serviceEndpoint);
            responseObserver.onCompleted();
        }
    }

    private ServiceEndpoint getService(String type,String name){
        System.out.println(formatter.format(new Date())+": service requested.");
        ServiceEndpoint tmService = getServiceByName(type,name);
        if(tmService == null) {
            long deadline = System.currentTimeMillis() + timeoutMillisTM;
            long remaining = deadline - System.currentTimeMillis();
            //wait-path
            while (true) {
                try {
                    System.out.println(formatter.format(new Date())+": waiting service request notified, " +
                            "still waiting on service info.");
                    lock.wait(remaining);
                } catch (InterruptedException e) {
                    return null;
                }
                tmService = getServiceByName(type,name);
                if (tmService != null) {
                    System.out.println(formatter.format(new Date())+": service info given to requester.");
                    return tmService;
                }
                remaining = deadline - System.currentTimeMillis();
                if (remaining <= 0) {
                    System.out.println(formatter.format(new Date())+": Timeout Reached Cancelling" +
                            "service info requests.");
                    return null;
                }
            }
        }
        System.out.println(formatter.format(new Date())+": service info given to requester.");
        return tmService;
    }


    private ServiceEndpoint getServiceByName(String type, String name){
        for (ServiceEndpoint service: services.get(type)) {
            if (service.getName().equals(name)){
                return service;
            }
        }
        return null;
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
                        for(ServiceEndpoint sep : services.get(VECTOR_SERVICES)) {
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
            for(ServiceEndpoint sep : services.get(VECTOR_SERVICES)) {
               vsB.addVectors(sep);
            }
            System.out.println(formatter.format(new Date())+": All Vector Services present and given to requester.");
            responseObserver.onNext(vsB.build());
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
            for (ServiceEndpoint sep : services.get(VECTOR_SERVICES)) {
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
