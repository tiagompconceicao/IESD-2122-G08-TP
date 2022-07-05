package isos.tp1.isyiesd.cesvector.servectorcli;

import IRegistry.IRegistryGrpc;
import IRegistry.ServiceEndpointClient;
import IRegistry.ServiceRequest;
import IRegistry.VectorServicesClient;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import lockManager.ILockManagerGrpc;
import transactionManagerTX.ITransactionManagerTXGrpc;
import transactionManagerTX.ITransactionManagerTXGrpc.ITransactionManagerTXBlockingStub;
import vector.IVectorGrpc.IVectorBlockingStub;
import vector.IVectorGrpc;
import IRegistry.ServiceEndpoint;
import IRegistry.VectorServices;
import lockManager.ILockManagerGrpc.ILockManagerBlockingStub;
import lockManager.ILockManagerGrpc.ILockManagerStub;

public class ConnectionManager {
    public IRegistryGrpc.IRegistryBlockingStub coordinatorProxy;

    public ITransactionManagerTXBlockingStub transactionManagerProxy;
    public ILockManagerBlockingStub lockManagerProxy;
    public ILockManagerStub lockManagerProxyAsync;
    public HashMap<String, IVectorBlockingStub> vectorServices;

    private final ManagedChannel coordinatorChannel;
    private final ManagedChannel transactionManagerChannel;
    private final LinkedList<ManagedChannel> vectorServiceChannels;
    private final ManagedChannel lockManagerChannel;

    public static Logger logger;

    public ConnectionManager(String nodeIP, int port, Logger logger) {
        vectorServices = new HashMap<>();
        vectorServiceChannels = new LinkedList<>();
        ConnectionManager.logger = logger;

        coordinatorChannel = ManagedChannelBuilder
          .forAddress(nodeIP, port)
          .usePlaintext()
          .build();
        coordinatorProxy = IRegistryGrpc.newBlockingStub(coordinatorChannel);

        ServiceEndpointClient transactionManagerEP = coordinatorProxy.getServiceClient(ServiceRequest.newBuilder().setName("TM1").build());
        transactionManagerChannel = ManagedChannelBuilder
          .forAddress(nodeIP, transactionManagerEP.getPort())
          .usePlaintext()
          .build();
        transactionManagerProxy = ITransactionManagerTXGrpc.newBlockingStub(transactionManagerChannel);

        VectorServicesClient vectorServiceEP = coordinatorProxy.getVectorServicesClient(Empty.newBuilder().build());
        for(ServiceEndpointClient sEP : vectorServiceEP.getVectorsList()) {
            ManagedChannel vectorServiceChannel = ManagedChannelBuilder
              .forAddress(nodeIP, sEP.getPort())
              .usePlaintext()
              .build();
            IVectorBlockingStub proxy = IVectorGrpc.newBlockingStub(vectorServiceChannel);
            vectorServices.put(sEP.getName(), proxy);
            vectorServiceChannels.add(vectorServiceChannel);
        }

        ServiceEndpointClient lockManagerEP = coordinatorProxy.getServiceClient(ServiceRequest.newBuilder().setName("TPLM1").build());
        lockManagerChannel = ManagedChannelBuilder
          .forAddress(nodeIP, lockManagerEP.getPort())
          .usePlaintext()
          .build();
        lockManagerProxy = ILockManagerGrpc.newBlockingStub(lockManagerChannel);
        lockManagerProxyAsync = ILockManagerGrpc.newStub(lockManagerChannel);
    }

    private static void close(ManagedChannel mc, String server) throws InterruptedException {
        if (mc!=null) {
            //Unsubscribe user from server
            mc.shutdown().awaitTermination(2, TimeUnit.SECONDS);
        }
    }

    public void shutdown() throws InterruptedException {
        ConnectionManager.close(coordinatorChannel, "CoordinationServer");
        ConnectionManager.close(transactionManagerChannel, "TransactionManagerServer");
        ConnectionManager.close(lockManagerChannel, "LockManagerServer");
        for(ManagedChannel mc : vectorServiceChannels) {
            ConnectionManager.close(mc, "VectorServiceServer");
        }

        logger.log(Level.INFO, "Shut down channels");
    }
}
