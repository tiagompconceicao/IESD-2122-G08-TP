package isos.tp1.isyiesd.cesregistry.serregistry;

import IRegistry.IRegistryGrpc;
import IRegistry.RegisterResult;
import IRegistry.ServiceEndpoint;
import IRegistry.Number;
import IRegistry.ServiceRequest;
import IRegistry.VectorServices;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

public class Registry extends IRegistryGrpc.IRegistryImplBase {
    private String VECTOR_SERVICES = "Vector";

    //info dos Vector Services
    private final ConcurrentHashMap<String,ServiceEndpoint> services = new ConcurrentHashMap();
    private int vectorID = 1;
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


    //TODO: HashMap<Nome,ServiceEndpoint>
    //Corrigir lógica
    //Sempre substituição ou criação de entradas no hashmap (não existem replicas)
    @Override
    public void registerService(ServiceEndpoint serviceEndpoint, StreamObserver<RegisterResult> responseObserver){
        synchronized (lock){
            //Verify if endpoint type exists
            if (!services.containsKey(serviceEndpoint.getType())) {
                services.put(serviceEndpoint.getType(),new LinkedList<>());
            }
            if (serviceEndpoint.getName().isEmpty()){
                //Vector
                ServiceEndpoint newEndpoint = ServiceEndpoint.newBuilder()
                        .setType(serviceEndpoint.getType())
                        .setName(serviceEndpoint.getType()+vectorID)
                        .setIp(serviceEndpoint.getIp())
                        .setPort(serviceEndpoint.getPort()).build();
                services.get(serviceEndpoint.getType()).push(newEndpoint);
                vectorID++;
            } else {
                //TM ou TPLM
                services.get(serviceEndpoint.getType()).clear();
                services.get(serviceEndpoint.getType()).push(serviceEndpoint);
            }

            if (serviceEndpoint.getType().equals(VECTOR_SERVICES)){
                currentNumberOfVectorServices++;
            }

            String serviceName = serviceEndpoint.getType() + services.get(serviceEndpoint.getType()).size();
            lock.notifyAll();
            responseObserver.onNext(RegisterResult.newBuilder().setName(serviceName).build());
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

}
