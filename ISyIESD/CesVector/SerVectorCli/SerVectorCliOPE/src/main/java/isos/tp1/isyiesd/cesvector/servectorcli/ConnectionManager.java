package isos.tp1.isyiesd.cesvector.servectorcli;

import IRegistry.IRegistryGrpc;
import IRegistry.ServiceRequest;
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

    public ConnectionManager(String ip, int port, Logger logger) {
        vectorServices = new HashMap<>();
        vectorServiceChannels = new LinkedList<>();
        ConnectionManager.logger = logger;

        coordinatorChannel = ManagedChannelBuilder
          .forAddress(ip, port)
          .usePlaintext()
          .build();
        coordinatorProxy = IRegistryGrpc.newBlockingStub(coordinatorChannel);

        ServiceEndpoint transactionManagerEP = coordinatorProxy.getService(ServiceRequest.newBuilder().setType("TM").setName("TM1").build());
        transactionManagerChannel = ManagedChannelBuilder
          .forAddress(transactionManagerEP.getIp(), transactionManagerEP.getPort())
          .usePlaintext()
          .build();
        transactionManagerProxy = ITransactionManagerTXGrpc.newBlockingStub(transactionManagerChannel);

        VectorServices vectorServiceEP = coordinatorProxy.getVectorServices(Empty.newBuilder().build());
        for(ServiceEndpoint sEP : vectorServiceEP.getVectorsList()) {
            ManagedChannel vectorServiceChannel = ManagedChannelBuilder
              .forAddress(sEP.getIp(), sEP.getPort())
              .usePlaintext()
              .build();
            IVectorBlockingStub proxy = IVectorGrpc.newBlockingStub(vectorServiceChannel);
            vectorServices.put(sEP.getName(), proxy);
            vectorServiceChannels.add(vectorServiceChannel);
        }

        ServiceEndpoint lockManagerEP = coordinatorProxy.getService(ServiceRequest.newBuilder().setType("TPLM").setName("TPLM1").build());
        lockManagerChannel = ManagedChannelBuilder
          .forAddress(lockManagerEP.getIp(), lockManagerEP.getPort())
          .usePlaintext()
          .build();
        lockManagerProxy = ILockManagerGrpc.newBlockingStub(lockManagerChannel);
        lockManagerProxyAsync = ILockManagerGrpc.newStub(lockManagerChannel);
    }

    private static void close(ManagedChannel mc, String server) throws InterruptedException {
        if (mc!=null) {
            //Unsubscribe user from server
            logger.log(Level.INFO, "Shutdown channel to server: " + server);
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
    }
}
