package isos.tp1.isyiesd.cesregistry.serregistry;

import IRegistry.IRegistryGrpc;
import IRegistry.ServiceEndpoint;
import IRegistry.Number;
import IRegistry.ServiceEndpointClient;
import IRegistry.ServiceRequest;
import IRegistry.VectorServices;
import IRegistry.VectorServicesClient;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

public class Registry extends IRegistryGrpc.IRegistryImplBase {

    private ServiceEndpoint tm = null;
    private ServiceEndpoint tplm = null;

    //info dos Vector Services
    private final ConcurrentHashMap<String,ServiceEndpoint> vectorServices = new ConcurrentHashMap();
    private final ConcurrentHashMap<String,Integer> servicesClientPorts = new ConcurrentHashMap();
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

        servicesClientPorts.put("TM1",30962);
        servicesClientPorts.put("TPLM1",30963);
        for (int i = 1;i <= numberOfVectorServices; i++){
            servicesClientPorts.put("Vector"+i,30963+i);
        }
    }

    @Override
    public void registerService(ServiceEndpoint serviceEndpoint, StreamObserver<Empty> responseObserver){
        synchronized (lock){
            //Verify if endpoint type exists
            switch (serviceEndpoint.getName()){
                case "TM1":
                    this.tm = serviceEndpoint;
                    break;
                case "TPLM1":
                    this.tplm = serviceEndpoint;
                    break;
                default:
                    vectorServices.put(serviceEndpoint.getName(), serviceEndpoint);
                    currentNumberOfVectorServices = vectorServices.size();
                    break;
            }

            System.out.println(formatter.format(new Date())+": Service: " + serviceEndpoint.getName() + " registered.");

            lock.notifyAll();
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getService(ServiceRequest serviceRequest, StreamObserver<ServiceEndpoint> responseObserver){
        synchronized (lock){
            ServiceEndpoint serviceEndpoint = getService(serviceRequest.getName());

            if (serviceEndpoint == null){
                responseObserver.onError(new Throwable("Requested service does not exists"));
                return;
            }

            responseObserver.onNext(serviceEndpoint);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void getServiceClient(ServiceRequest request, StreamObserver<ServiceEndpointClient> responseObserver){
        ServiceEndpointClient serviceEndpointClient = ServiceEndpointClient
                .newBuilder()
                .setName(request.getName())
                .setPort(servicesClientPorts.get(request.getName()))
                .build();

        responseObserver.onNext(serviceEndpointClient);
        responseObserver.onCompleted();
    }

    @Override
    public void getVectorServicesClient(Empty request, StreamObserver<VectorServicesClient> responseObserver) {
        VectorServicesClient.Builder vsB = VectorServicesClient.newBuilder();

        for (int i = 1;i <= numberOfVectorServices; i++){
            String name = "Vector"+i;
            int port = servicesClientPorts.get(name);
            vsB.addVectors(ServiceEndpointClient
                    .newBuilder()
                    .setName(name)
                    .setPort(port)
                    .build());
        }

        responseObserver.onNext(vsB.build());
        responseObserver.onCompleted();
    }

    private ServiceEndpoint getService(String name){
        System.out.println(formatter.format(new Date())+": Service: " + name + " requested.");

        //fast-path
        ServiceEndpoint service = getServiceByName(name);
        if(service == null) {
            long deadline = System.currentTimeMillis() + timeoutMillisTM;
            long remaining = deadline - System.currentTimeMillis();

            //wait-path
            while (true) {
                try {
                    System.out.println(formatter.format(new Date())+": Waiting for service: " + name);
                    lock.wait(remaining);
                } catch (InterruptedException e) {
                    return null;
                }
                service = getServiceByName(name);
                if (service != null) {

                    System.out.println(formatter.format(new Date())+": Service: " + name + " info given to requester.");
                    return service;
                }
                remaining = deadline - System.currentTimeMillis();
                if (remaining <= 0) {

                    System.out.println(formatter.format(new Date())+": Timeout Reached. Cancelling service: "
                            + name + " info requests.");
                    return null;
                }
            }
        }

        System.out.println(formatter.format(new Date())+": Service: " + name + " info given to requester.");
        return service;
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
                        for(ServiceEndpoint sep : vectorServices.values()) {
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
            for(ServiceEndpoint sep : vectorServices.values()) {
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

    private ServiceEndpoint getServiceByName(String name){
        ServiceEndpoint service = null;
        switch (name){
            case "TM1":
                if (tm != null){
                    service = tm;
                }
                break;
            case "TPLM1":
                if (tplm != null){
                    service = tplm;
                }
                break;
            default:
                service = vectorServices.get(name);
                break;
        }
        return service;
    }

}
