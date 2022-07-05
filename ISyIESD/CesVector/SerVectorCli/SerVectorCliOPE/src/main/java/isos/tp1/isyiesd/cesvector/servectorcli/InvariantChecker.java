package isos.tp1.isyiesd.cesvector.servectorcli;

import IRegistry.ServiceEndpointClient;
import com.google.protobuf.Empty;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lockManager.LockRequest;
import lockManager.ResourceElement;
import lockManager.UnlockRequest;
import transactionManagerTX.Result;
import transactionManagerTX.Transaction;
import vector.IVectorGrpc;
import vector.ReadMessage;

public class InvariantChecker {
    private static final int READ_MODE = 1;
    private static final int WRITE_MODE = 2;

    private static String nodeIP = "localhost";
    private static final int registryPort = 30961;

    private static final Logger logger = Logger.getLogger(VectorClient3.class.getName());

    public static void main(String[] args) {
        if (args.length == 1) {
            nodeIP = args[0];
        }

        Transaction transaction = null;
        try {
            ConnectionManager cm = new ConnectionManager(nodeIP, registryPort, logger);
            //Creates a transaction
            transaction = cm.transactionManagerProxy.txBegin(Empty.newBuilder().build());

            System.out.println("Transaction created with id: " + transaction.getTid());

            int numberOfServers = cm.coordinatorProxy.getNumberOfVectorServices(Empty.newBuilder().build()).getValue();

            int tid = transaction.getTid();
            LockRequest.Builder locksRequestBuilder = LockRequest.newBuilder().setTid(tid);

            for (int i = 0; i < numberOfServers ; i++){
                for (int j = 0; j < 4; j++){
                    locksRequestBuilder.addElements(ResourceElement.newBuilder().setServerID(i).setElementIndex(j).setLockType(READ_MODE).build())
                            .addElements(ResourceElement.newBuilder().setServerID(i).setElementIndex(j).setLockType(WRITE_MODE).build());
                }
            }

            LockRequest locksRequest = locksRequestBuilder.build();

            //Get all necessary locks
            logger.info("Transaction: " + transaction.getTid() + " || " + "Waiting for locks...");
            cm.lockManagerProxy.getLocks(locksRequest);
            logger.info("Transaction: " + transaction.getTid() + " || " + "Locks acquired!");

            List<ServiceEndpointClient> vectors = cm.coordinatorProxy.getVectorServicesClient(Empty.newBuilder().build()).getVectorsList();

            int sum = 0;
            for (ServiceEndpointClient sep : vectors) {
                ManagedChannel sepMC = ManagedChannelBuilder
                        .forAddress(nodeIP, sep.getPort())
                        .usePlaintext()
                        .build();
                IVectorGrpc.IVectorBlockingStub sepProxy = IVectorGrpc.newBlockingStub(sepMC);
                int sumOfVector = sepProxy.checkSum(ReadMessage.newBuilder().setTid(tid).build()).getValue();
                System.out.println("    -Sum of Vector Service: "+sep.getName()+" is: "+sumOfVector);
                sum = sum + sumOfVector;
            }

            //sum of starting values in vector services
            int vectorSum = 300 + 234 + 56 + 789;
            if(sum == numberOfServers * vectorSum) {
                logger.info("Invariant is Valid!");
            } else {
                logger.info("Invariant is Not Valid!");
            }

            //Commit
            Result r = cm.transactionManagerProxy.txCommit(transaction);
            if (r.getStatus()) {
                logger.info("Transaction: " + transaction.getTid() + " || " + "Committed Successfully!");
            } else {
                logger.info("Transaction: " + transaction.getTid() + " || " + "Rolled Back due to Error!");
            }


            //Unlock all resources locked
            cm.lockManagerProxy.unlock(UnlockRequest.newBuilder().setTid(transaction.getTid()).build());
            logger.info("Transaction: " + transaction.getTid() + " || " + "Locks Released.");

            cm.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
            if(transaction != null) {
                logger.log(Level.SEVERE, "Transaction: " + transaction.getTid() + " || " + "Error:" + ex.getMessage());
            }
        }
    }
}
