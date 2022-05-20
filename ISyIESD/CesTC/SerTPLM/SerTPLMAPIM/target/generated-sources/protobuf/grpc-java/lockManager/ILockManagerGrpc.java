package lockManager;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.1)",
    comments = "Source: ILockManager.proto")
public final class ILockManagerGrpc {

  private ILockManagerGrpc() {}

  public static final String SERVICE_NAME = "forum.ILockManager";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<lockManager.LockRequest,
      com.google.protobuf.Empty> getGetLocksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLocks",
      requestType = lockManager.LockRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lockManager.LockRequest,
      com.google.protobuf.Empty> getGetLocksMethod() {
    io.grpc.MethodDescriptor<lockManager.LockRequest, com.google.protobuf.Empty> getGetLocksMethod;
    if ((getGetLocksMethod = ILockManagerGrpc.getGetLocksMethod) == null) {
      synchronized (ILockManagerGrpc.class) {
        if ((getGetLocksMethod = ILockManagerGrpc.getGetLocksMethod) == null) {
          ILockManagerGrpc.getGetLocksMethod = getGetLocksMethod =
              io.grpc.MethodDescriptor.<lockManager.LockRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getLocks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lockManager.LockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ILockManagerMethodDescriptorSupplier("getLocks"))
              .build();
        }
      }
    }
    return getGetLocksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<lockManager.UnlockRequest,
      com.google.protobuf.Empty> getUnlockMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "unlock",
      requestType = lockManager.UnlockRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<lockManager.UnlockRequest,
      com.google.protobuf.Empty> getUnlockMethod() {
    io.grpc.MethodDescriptor<lockManager.UnlockRequest, com.google.protobuf.Empty> getUnlockMethod;
    if ((getUnlockMethod = ILockManagerGrpc.getUnlockMethod) == null) {
      synchronized (ILockManagerGrpc.class) {
        if ((getUnlockMethod = ILockManagerGrpc.getUnlockMethod) == null) {
          ILockManagerGrpc.getUnlockMethod = getUnlockMethod =
              io.grpc.MethodDescriptor.<lockManager.UnlockRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "unlock"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  lockManager.UnlockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ILockManagerMethodDescriptorSupplier("unlock"))
              .build();
        }
      }
    }
    return getUnlockMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ILockManagerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ILockManagerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ILockManagerStub>() {
        @java.lang.Override
        public ILockManagerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ILockManagerStub(channel, callOptions);
        }
      };
    return ILockManagerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ILockManagerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ILockManagerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ILockManagerBlockingStub>() {
        @java.lang.Override
        public ILockManagerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ILockManagerBlockingStub(channel, callOptions);
        }
      };
    return ILockManagerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ILockManagerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ILockManagerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ILockManagerFutureStub>() {
        @java.lang.Override
        public ILockManagerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ILockManagerFutureStub(channel, callOptions);
        }
      };
    return ILockManagerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ILockManagerImplBase implements io.grpc.BindableService {

    /**
     */
    public void getLocks(lockManager.LockRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getGetLocksMethod(), responseObserver);
    }

    /**
     */
    public void unlock(lockManager.UnlockRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getUnlockMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetLocksMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lockManager.LockRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_GET_LOCKS)))
          .addMethod(
            getUnlockMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                lockManager.UnlockRequest,
                com.google.protobuf.Empty>(
                  this, METHODID_UNLOCK)))
          .build();
    }
  }

  /**
   */
  public static final class ILockManagerStub extends io.grpc.stub.AbstractAsyncStub<ILockManagerStub> {
    private ILockManagerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ILockManagerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ILockManagerStub(channel, callOptions);
    }

    /**
     */
    public void getLocks(lockManager.LockRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetLocksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unlock(lockManager.UnlockRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUnlockMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ILockManagerBlockingStub extends io.grpc.stub.AbstractBlockingStub<ILockManagerBlockingStub> {
    private ILockManagerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ILockManagerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ILockManagerBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty getLocks(lockManager.LockRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetLocksMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty unlock(lockManager.UnlockRequest request) {
      return blockingUnaryCall(
          getChannel(), getUnlockMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ILockManagerFutureStub extends io.grpc.stub.AbstractFutureStub<ILockManagerFutureStub> {
    private ILockManagerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ILockManagerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ILockManagerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> getLocks(
        lockManager.LockRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetLocksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> unlock(
        lockManager.UnlockRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUnlockMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LOCKS = 0;
  private static final int METHODID_UNLOCK = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ILockManagerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ILockManagerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_LOCKS:
          serviceImpl.getLocks((lockManager.LockRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_UNLOCK:
          serviceImpl.unlock((lockManager.UnlockRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ILockManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ILockManagerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return lockManager.ILockManagerOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ILockManager");
    }
  }

  private static final class ILockManagerFileDescriptorSupplier
      extends ILockManagerBaseDescriptorSupplier {
    ILockManagerFileDescriptorSupplier() {}
  }

  private static final class ILockManagerMethodDescriptorSupplier
      extends ILockManagerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ILockManagerMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ILockManagerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ILockManagerFileDescriptorSupplier())
              .addMethod(getGetLocksMethod())
              .addMethod(getUnlockMethod())
              .build();
        }
      }
    }
    return result;
  }
}
