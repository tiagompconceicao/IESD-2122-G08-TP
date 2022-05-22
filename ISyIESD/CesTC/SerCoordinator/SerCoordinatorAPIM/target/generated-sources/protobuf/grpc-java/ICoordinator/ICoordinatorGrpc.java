package ICoordinator;

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
    comments = "Source: ICoordinator.proto")
public final class ICoordinatorGrpc {

  private ICoordinatorGrpc() {}

  public static final String SERVICE_NAME = "forum.ICoordinator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterVectorServiceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerVectorService",
      requestType = ICoordinator.ServiceEndpoint.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterVectorServiceMethod() {
    io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint, com.google.protobuf.Empty> getRegisterVectorServiceMethod;
    if ((getRegisterVectorServiceMethod = ICoordinatorGrpc.getRegisterVectorServiceMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getRegisterVectorServiceMethod = ICoordinatorGrpc.getRegisterVectorServiceMethod) == null) {
          ICoordinatorGrpc.getRegisterVectorServiceMethod = getRegisterVectorServiceMethod =
              io.grpc.MethodDescriptor.<ICoordinator.ServiceEndpoint, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerVectorService"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.ServiceEndpoint.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("registerVectorService"))
              .build();
        }
      }
    }
    return getRegisterVectorServiceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.VectorServices> getGetVectorServicesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getVectorServices",
      requestType = com.google.protobuf.Empty.class,
      responseType = ICoordinator.VectorServices.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.VectorServices> getGetVectorServicesMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, ICoordinator.VectorServices> getGetVectorServicesMethod;
    if ((getGetVectorServicesMethod = ICoordinatorGrpc.getGetVectorServicesMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getGetVectorServicesMethod = ICoordinatorGrpc.getGetVectorServicesMethod) == null) {
          ICoordinatorGrpc.getGetVectorServicesMethod = getGetVectorServicesMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, ICoordinator.VectorServices>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getVectorServices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.VectorServices.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("getVectorServices"))
              .build();
        }
      }
    }
    return getGetVectorServicesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerTM",
      requestType = ICoordinator.ServiceEndpoint.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTMMethod() {
    io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint, com.google.protobuf.Empty> getRegisterTMMethod;
    if ((getRegisterTMMethod = ICoordinatorGrpc.getRegisterTMMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getRegisterTMMethod = ICoordinatorGrpc.getRegisterTMMethod) == null) {
          ICoordinatorGrpc.getRegisterTMMethod = getRegisterTMMethod =
              io.grpc.MethodDescriptor.<ICoordinator.ServiceEndpoint, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerTM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.ServiceEndpoint.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("registerTM"))
              .build();
        }
      }
    }
    return getRegisterTMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.ServiceEndpoint> getGetTMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getTM",
      requestType = com.google.protobuf.Empty.class,
      responseType = ICoordinator.ServiceEndpoint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.ServiceEndpoint> getGetTMMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, ICoordinator.ServiceEndpoint> getGetTMMethod;
    if ((getGetTMMethod = ICoordinatorGrpc.getGetTMMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getGetTMMethod = ICoordinatorGrpc.getGetTMMethod) == null) {
          ICoordinatorGrpc.getGetTMMethod = getGetTMMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, ICoordinator.ServiceEndpoint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getTM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.ServiceEndpoint.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("getTM"))
              .build();
        }
      }
    }
    return getGetTMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTPLMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerTPLM",
      requestType = ICoordinator.ServiceEndpoint.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTPLMMethod() {
    io.grpc.MethodDescriptor<ICoordinator.ServiceEndpoint, com.google.protobuf.Empty> getRegisterTPLMMethod;
    if ((getRegisterTPLMMethod = ICoordinatorGrpc.getRegisterTPLMMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getRegisterTPLMMethod = ICoordinatorGrpc.getRegisterTPLMMethod) == null) {
          ICoordinatorGrpc.getRegisterTPLMMethod = getRegisterTPLMMethod =
              io.grpc.MethodDescriptor.<ICoordinator.ServiceEndpoint, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerTPLM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.ServiceEndpoint.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("registerTPLM"))
              .build();
        }
      }
    }
    return getRegisterTPLMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.ServiceEndpoint> getGetTPLMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getTPLM",
      requestType = com.google.protobuf.Empty.class,
      responseType = ICoordinator.ServiceEndpoint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.ServiceEndpoint> getGetTPLMMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, ICoordinator.ServiceEndpoint> getGetTPLMMethod;
    if ((getGetTPLMMethod = ICoordinatorGrpc.getGetTPLMMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getGetTPLMMethod = ICoordinatorGrpc.getGetTPLMMethod) == null) {
          ICoordinatorGrpc.getGetTPLMMethod = getGetTPLMMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, ICoordinator.ServiceEndpoint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getTPLM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.ServiceEndpoint.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("getTPLM"))
              .build();
        }
      }
    }
    return getGetTPLMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.Number> getGetNumberOfVectorServicesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getNumberOfVectorServices",
      requestType = com.google.protobuf.Empty.class,
      responseType = ICoordinator.Number.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.Number> getGetNumberOfVectorServicesMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, ICoordinator.Number> getGetNumberOfVectorServicesMethod;
    if ((getGetNumberOfVectorServicesMethod = ICoordinatorGrpc.getGetNumberOfVectorServicesMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getGetNumberOfVectorServicesMethod = ICoordinatorGrpc.getGetNumberOfVectorServicesMethod) == null) {
          ICoordinatorGrpc.getGetNumberOfVectorServicesMethod = getGetNumberOfVectorServicesMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, ICoordinator.Number>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getNumberOfVectorServices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.Number.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("getNumberOfVectorServices"))
              .build();
        }
      }
    }
    return getGetNumberOfVectorServicesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.Result> getCheckInvariantMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkInvariant",
      requestType = com.google.protobuf.Empty.class,
      responseType = ICoordinator.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ICoordinator.Result> getCheckInvariantMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, ICoordinator.Result> getCheckInvariantMethod;
    if ((getCheckInvariantMethod = ICoordinatorGrpc.getCheckInvariantMethod) == null) {
      synchronized (ICoordinatorGrpc.class) {
        if ((getCheckInvariantMethod = ICoordinatorGrpc.getCheckInvariantMethod) == null) {
          ICoordinatorGrpc.getCheckInvariantMethod = getCheckInvariantMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, ICoordinator.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkInvariant"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ICoordinator.Result.getDefaultInstance()))
              .setSchemaDescriptor(new ICoordinatorMethodDescriptorSupplier("checkInvariant"))
              .build();
        }
      }
    }
    return getCheckInvariantMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ICoordinatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ICoordinatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ICoordinatorStub>() {
        @java.lang.Override
        public ICoordinatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ICoordinatorStub(channel, callOptions);
        }
      };
    return ICoordinatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ICoordinatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ICoordinatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ICoordinatorBlockingStub>() {
        @java.lang.Override
        public ICoordinatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ICoordinatorBlockingStub(channel, callOptions);
        }
      };
    return ICoordinatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ICoordinatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ICoordinatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ICoordinatorFutureStub>() {
        @java.lang.Override
        public ICoordinatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ICoordinatorFutureStub(channel, callOptions);
        }
      };
    return ICoordinatorFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ICoordinatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void registerVectorService(ICoordinator.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterVectorServiceMethod(), responseObserver);
    }

    /**
     */
    public void getVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.VectorServices> responseObserver) {
      asyncUnimplementedUnaryCall(getGetVectorServicesMethod(), responseObserver);
    }

    /**
     */
    public void registerTM(ICoordinator.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterTMMethod(), responseObserver);
    }

    /**
     */
    public void getTM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.ServiceEndpoint> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTMMethod(), responseObserver);
    }

    /**
     */
    public void registerTPLM(ICoordinator.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterTPLMMethod(), responseObserver);
    }

    /**
     */
    public void getTPLM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.ServiceEndpoint> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTPLMMethod(), responseObserver);
    }

    /**
     */
    public void getNumberOfVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.Number> responseObserver) {
      asyncUnimplementedUnaryCall(getGetNumberOfVectorServicesMethod(), responseObserver);
    }

    /**
     */
    public void checkInvariant(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getCheckInvariantMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterVectorServiceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ICoordinator.ServiceEndpoint,
                com.google.protobuf.Empty>(
                  this, METHODID_REGISTER_VECTOR_SERVICE)))
          .addMethod(
            getGetVectorServicesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                ICoordinator.VectorServices>(
                  this, METHODID_GET_VECTOR_SERVICES)))
          .addMethod(
            getRegisterTMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ICoordinator.ServiceEndpoint,
                com.google.protobuf.Empty>(
                  this, METHODID_REGISTER_TM)))
          .addMethod(
            getGetTMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                ICoordinator.ServiceEndpoint>(
                  this, METHODID_GET_TM)))
          .addMethod(
            getRegisterTPLMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ICoordinator.ServiceEndpoint,
                com.google.protobuf.Empty>(
                  this, METHODID_REGISTER_TPLM)))
          .addMethod(
            getGetTPLMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                ICoordinator.ServiceEndpoint>(
                  this, METHODID_GET_TPLM)))
          .addMethod(
            getGetNumberOfVectorServicesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                ICoordinator.Number>(
                  this, METHODID_GET_NUMBER_OF_VECTOR_SERVICES)))
          .addMethod(
            getCheckInvariantMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                ICoordinator.Result>(
                  this, METHODID_CHECK_INVARIANT)))
          .build();
    }
  }

  /**
   */
  public static final class ICoordinatorStub extends io.grpc.stub.AbstractAsyncStub<ICoordinatorStub> {
    private ICoordinatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ICoordinatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ICoordinatorStub(channel, callOptions);
    }

    /**
     */
    public void registerVectorService(ICoordinator.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterVectorServiceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.VectorServices> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVectorServicesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerTM(ICoordinator.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterTMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.ServiceEndpoint> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerTPLM(ICoordinator.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterTPLMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTPLM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.ServiceEndpoint> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTPLMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNumberOfVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.Number> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetNumberOfVectorServicesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkInvariant(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ICoordinator.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCheckInvariantMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ICoordinatorBlockingStub extends io.grpc.stub.AbstractBlockingStub<ICoordinatorBlockingStub> {
    private ICoordinatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ICoordinatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ICoordinatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty registerVectorService(ICoordinator.ServiceEndpoint request) {
      return blockingUnaryCall(
          getChannel(), getRegisterVectorServiceMethod(), getCallOptions(), request);
    }

    /**
     */
    public ICoordinator.VectorServices getVectorServices(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetVectorServicesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty registerTM(ICoordinator.ServiceEndpoint request) {
      return blockingUnaryCall(
          getChannel(), getRegisterTMMethod(), getCallOptions(), request);
    }

    /**
     */
    public ICoordinator.ServiceEndpoint getTM(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetTMMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty registerTPLM(ICoordinator.ServiceEndpoint request) {
      return blockingUnaryCall(
          getChannel(), getRegisterTPLMMethod(), getCallOptions(), request);
    }

    /**
     */
    public ICoordinator.ServiceEndpoint getTPLM(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetTPLMMethod(), getCallOptions(), request);
    }

    /**
     */
    public ICoordinator.Number getNumberOfVectorServices(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetNumberOfVectorServicesMethod(), getCallOptions(), request);
    }

    /**
     */
    public ICoordinator.Result checkInvariant(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getCheckInvariantMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ICoordinatorFutureStub extends io.grpc.stub.AbstractFutureStub<ICoordinatorFutureStub> {
    private ICoordinatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ICoordinatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ICoordinatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerVectorService(
        ICoordinator.ServiceEndpoint request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterVectorServiceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ICoordinator.VectorServices> getVectorServices(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVectorServicesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerTM(
        ICoordinator.ServiceEndpoint request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterTMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ICoordinator.ServiceEndpoint> getTM(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerTPLM(
        ICoordinator.ServiceEndpoint request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterTPLMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ICoordinator.ServiceEndpoint> getTPLM(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTPLMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ICoordinator.Number> getNumberOfVectorServices(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetNumberOfVectorServicesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ICoordinator.Result> checkInvariant(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getCheckInvariantMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_VECTOR_SERVICE = 0;
  private static final int METHODID_GET_VECTOR_SERVICES = 1;
  private static final int METHODID_REGISTER_TM = 2;
  private static final int METHODID_GET_TM = 3;
  private static final int METHODID_REGISTER_TPLM = 4;
  private static final int METHODID_GET_TPLM = 5;
  private static final int METHODID_GET_NUMBER_OF_VECTOR_SERVICES = 6;
  private static final int METHODID_CHECK_INVARIANT = 7;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ICoordinatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ICoordinatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_VECTOR_SERVICE:
          serviceImpl.registerVectorService((ICoordinator.ServiceEndpoint) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_VECTOR_SERVICES:
          serviceImpl.getVectorServices((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<ICoordinator.VectorServices>) responseObserver);
          break;
        case METHODID_REGISTER_TM:
          serviceImpl.registerTM((ICoordinator.ServiceEndpoint) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_TM:
          serviceImpl.getTM((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<ICoordinator.ServiceEndpoint>) responseObserver);
          break;
        case METHODID_REGISTER_TPLM:
          serviceImpl.registerTPLM((ICoordinator.ServiceEndpoint) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_TPLM:
          serviceImpl.getTPLM((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<ICoordinator.ServiceEndpoint>) responseObserver);
          break;
        case METHODID_GET_NUMBER_OF_VECTOR_SERVICES:
          serviceImpl.getNumberOfVectorServices((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<ICoordinator.Number>) responseObserver);
          break;
        case METHODID_CHECK_INVARIANT:
          serviceImpl.checkInvariant((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<ICoordinator.Result>) responseObserver);
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

  private static abstract class ICoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ICoordinatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ICoordinator.ICoordinatorOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ICoordinator");
    }
  }

  private static final class ICoordinatorFileDescriptorSupplier
      extends ICoordinatorBaseDescriptorSupplier {
    ICoordinatorFileDescriptorSupplier() {}
  }

  private static final class ICoordinatorMethodDescriptorSupplier
      extends ICoordinatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ICoordinatorMethodDescriptorSupplier(String methodName) {
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
      synchronized (ICoordinatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ICoordinatorFileDescriptorSupplier())
              .addMethod(getRegisterVectorServiceMethod())
              .addMethod(getGetVectorServicesMethod())
              .addMethod(getRegisterTMMethod())
              .addMethod(getGetTMMethod())
              .addMethod(getRegisterTPLMMethod())
              .addMethod(getGetTPLMMethod())
              .addMethod(getGetNumberOfVectorServicesMethod())
              .addMethod(getCheckInvariantMethod())
              .build();
        }
      }
    }
    return result;
  }
}
