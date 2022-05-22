package IRegistry;

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
    comments = "Source: IRegistry.proto")
public final class IRegistryGrpc {

  private IRegistryGrpc() {}

  public static final String SERVICE_NAME = "forum.IRegistry";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterVectorServiceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerVectorService",
      requestType = IRegistry.ServiceEndpoint.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterVectorServiceMethod() {
    io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint, com.google.protobuf.Empty> getRegisterVectorServiceMethod;
    if ((getRegisterVectorServiceMethod = IRegistryGrpc.getRegisterVectorServiceMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getRegisterVectorServiceMethod = IRegistryGrpc.getRegisterVectorServiceMethod) == null) {
          IRegistryGrpc.getRegisterVectorServiceMethod = getRegisterVectorServiceMethod =
              io.grpc.MethodDescriptor.<IRegistry.ServiceEndpoint, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerVectorService"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.ServiceEndpoint.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("registerVectorService"))
              .build();
        }
      }
    }
    return getRegisterVectorServiceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.VectorServices> getGetVectorServicesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getVectorServices",
      requestType = com.google.protobuf.Empty.class,
      responseType = IRegistry.VectorServices.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.VectorServices> getGetVectorServicesMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, IRegistry.VectorServices> getGetVectorServicesMethod;
    if ((getGetVectorServicesMethod = IRegistryGrpc.getGetVectorServicesMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getGetVectorServicesMethod = IRegistryGrpc.getGetVectorServicesMethod) == null) {
          IRegistryGrpc.getGetVectorServicesMethod = getGetVectorServicesMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, IRegistry.VectorServices>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getVectorServices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.VectorServices.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("getVectorServices"))
              .build();
        }
      }
    }
    return getGetVectorServicesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerTM",
      requestType = IRegistry.ServiceEndpoint.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTMMethod() {
    io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint, com.google.protobuf.Empty> getRegisterTMMethod;
    if ((getRegisterTMMethod = IRegistryGrpc.getRegisterTMMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getRegisterTMMethod = IRegistryGrpc.getRegisterTMMethod) == null) {
          IRegistryGrpc.getRegisterTMMethod = getRegisterTMMethod =
              io.grpc.MethodDescriptor.<IRegistry.ServiceEndpoint, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerTM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.ServiceEndpoint.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("registerTM"))
              .build();
        }
      }
    }
    return getRegisterTMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.ServiceEndpoint> getGetTMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getTM",
      requestType = com.google.protobuf.Empty.class,
      responseType = IRegistry.ServiceEndpoint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.ServiceEndpoint> getGetTMMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, IRegistry.ServiceEndpoint> getGetTMMethod;
    if ((getGetTMMethod = IRegistryGrpc.getGetTMMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getGetTMMethod = IRegistryGrpc.getGetTMMethod) == null) {
          IRegistryGrpc.getGetTMMethod = getGetTMMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, IRegistry.ServiceEndpoint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getTM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.ServiceEndpoint.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("getTM"))
              .build();
        }
      }
    }
    return getGetTMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTPLMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerTPLM",
      requestType = IRegistry.ServiceEndpoint.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint,
      com.google.protobuf.Empty> getRegisterTPLMMethod() {
    io.grpc.MethodDescriptor<IRegistry.ServiceEndpoint, com.google.protobuf.Empty> getRegisterTPLMMethod;
    if ((getRegisterTPLMMethod = IRegistryGrpc.getRegisterTPLMMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getRegisterTPLMMethod = IRegistryGrpc.getRegisterTPLMMethod) == null) {
          IRegistryGrpc.getRegisterTPLMMethod = getRegisterTPLMMethod =
              io.grpc.MethodDescriptor.<IRegistry.ServiceEndpoint, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerTPLM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.ServiceEndpoint.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("registerTPLM"))
              .build();
        }
      }
    }
    return getRegisterTPLMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.ServiceEndpoint> getGetTPLMMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getTPLM",
      requestType = com.google.protobuf.Empty.class,
      responseType = IRegistry.ServiceEndpoint.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.ServiceEndpoint> getGetTPLMMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, IRegistry.ServiceEndpoint> getGetTPLMMethod;
    if ((getGetTPLMMethod = IRegistryGrpc.getGetTPLMMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getGetTPLMMethod = IRegistryGrpc.getGetTPLMMethod) == null) {
          IRegistryGrpc.getGetTPLMMethod = getGetTPLMMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, IRegistry.ServiceEndpoint>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getTPLM"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.ServiceEndpoint.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("getTPLM"))
              .build();
        }
      }
    }
    return getGetTPLMMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.Number> getGetNumberOfVectorServicesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getNumberOfVectorServices",
      requestType = com.google.protobuf.Empty.class,
      responseType = IRegistry.Number.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.Number> getGetNumberOfVectorServicesMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, IRegistry.Number> getGetNumberOfVectorServicesMethod;
    if ((getGetNumberOfVectorServicesMethod = IRegistryGrpc.getGetNumberOfVectorServicesMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getGetNumberOfVectorServicesMethod = IRegistryGrpc.getGetNumberOfVectorServicesMethod) == null) {
          IRegistryGrpc.getGetNumberOfVectorServicesMethod = getGetNumberOfVectorServicesMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, IRegistry.Number>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getNumberOfVectorServices"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.Number.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("getNumberOfVectorServices"))
              .build();
        }
      }
    }
    return getGetNumberOfVectorServicesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.Result> getCheckInvariantMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkInvariant",
      requestType = com.google.protobuf.Empty.class,
      responseType = IRegistry.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      IRegistry.Result> getCheckInvariantMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, IRegistry.Result> getCheckInvariantMethod;
    if ((getCheckInvariantMethod = IRegistryGrpc.getCheckInvariantMethod) == null) {
      synchronized (IRegistryGrpc.class) {
        if ((getCheckInvariantMethod = IRegistryGrpc.getCheckInvariantMethod) == null) {
          IRegistryGrpc.getCheckInvariantMethod = getCheckInvariantMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, IRegistry.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "checkInvariant"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  IRegistry.Result.getDefaultInstance()))
              .setSchemaDescriptor(new IRegistryMethodDescriptorSupplier("checkInvariant"))
              .build();
        }
      }
    }
    return getCheckInvariantMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static IRegistryStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IRegistryStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IRegistryStub>() {
        @java.lang.Override
        public IRegistryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IRegistryStub(channel, callOptions);
        }
      };
    return IRegistryStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static IRegistryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IRegistryBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IRegistryBlockingStub>() {
        @java.lang.Override
        public IRegistryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IRegistryBlockingStub(channel, callOptions);
        }
      };
    return IRegistryBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static IRegistryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IRegistryFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IRegistryFutureStub>() {
        @java.lang.Override
        public IRegistryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IRegistryFutureStub(channel, callOptions);
        }
      };
    return IRegistryFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class IRegistryImplBase implements io.grpc.BindableService {

    /**
     */
    public void registerVectorService(IRegistry.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterVectorServiceMethod(), responseObserver);
    }

    /**
     */
    public void getVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.VectorServices> responseObserver) {
      asyncUnimplementedUnaryCall(getGetVectorServicesMethod(), responseObserver);
    }

    /**
     */
    public void registerTM(IRegistry.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterTMMethod(), responseObserver);
    }

    /**
     */
    public void getTM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.ServiceEndpoint> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTMMethod(), responseObserver);
    }

    /**
     */
    public void registerTPLM(IRegistry.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterTPLMMethod(), responseObserver);
    }

    /**
     */
    public void getTPLM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.ServiceEndpoint> responseObserver) {
      asyncUnimplementedUnaryCall(getGetTPLMMethod(), responseObserver);
    }

    /**
     */
    public void getNumberOfVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.Number> responseObserver) {
      asyncUnimplementedUnaryCall(getGetNumberOfVectorServicesMethod(), responseObserver);
    }

    /**
     */
    public void checkInvariant(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getCheckInvariantMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterVectorServiceMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                IRegistry.ServiceEndpoint,
                com.google.protobuf.Empty>(
                  this, METHODID_REGISTER_VECTOR_SERVICE)))
          .addMethod(
            getGetVectorServicesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                IRegistry.VectorServices>(
                  this, METHODID_GET_VECTOR_SERVICES)))
          .addMethod(
            getRegisterTMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                IRegistry.ServiceEndpoint,
                com.google.protobuf.Empty>(
                  this, METHODID_REGISTER_TM)))
          .addMethod(
            getGetTMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                IRegistry.ServiceEndpoint>(
                  this, METHODID_GET_TM)))
          .addMethod(
            getRegisterTPLMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                IRegistry.ServiceEndpoint,
                com.google.protobuf.Empty>(
                  this, METHODID_REGISTER_TPLM)))
          .addMethod(
            getGetTPLMMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                IRegistry.ServiceEndpoint>(
                  this, METHODID_GET_TPLM)))
          .addMethod(
            getGetNumberOfVectorServicesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                IRegistry.Number>(
                  this, METHODID_GET_NUMBER_OF_VECTOR_SERVICES)))
          .addMethod(
            getCheckInvariantMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                IRegistry.Result>(
                  this, METHODID_CHECK_INVARIANT)))
          .build();
    }
  }

  /**
   */
  public static final class IRegistryStub extends io.grpc.stub.AbstractAsyncStub<IRegistryStub> {
    private IRegistryStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IRegistryStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IRegistryStub(channel, callOptions);
    }

    /**
     */
    public void registerVectorService(IRegistry.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterVectorServiceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.VectorServices> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetVectorServicesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerTM(IRegistry.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterTMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.ServiceEndpoint> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerTPLM(IRegistry.ServiceEndpoint request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterTPLMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTPLM(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.ServiceEndpoint> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetTPLMMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getNumberOfVectorServices(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.Number> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetNumberOfVectorServicesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkInvariant(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<IRegistry.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCheckInvariantMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class IRegistryBlockingStub extends io.grpc.stub.AbstractBlockingStub<IRegistryBlockingStub> {
    private IRegistryBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IRegistryBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IRegistryBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty registerVectorService(IRegistry.ServiceEndpoint request) {
      return blockingUnaryCall(
          getChannel(), getRegisterVectorServiceMethod(), getCallOptions(), request);
    }

    /**
     */
    public IRegistry.VectorServices getVectorServices(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetVectorServicesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty registerTM(IRegistry.ServiceEndpoint request) {
      return blockingUnaryCall(
          getChannel(), getRegisterTMMethod(), getCallOptions(), request);
    }

    /**
     */
    public IRegistry.ServiceEndpoint getTM(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetTMMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty registerTPLM(IRegistry.ServiceEndpoint request) {
      return blockingUnaryCall(
          getChannel(), getRegisterTPLMMethod(), getCallOptions(), request);
    }

    /**
     */
    public IRegistry.ServiceEndpoint getTPLM(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetTPLMMethod(), getCallOptions(), request);
    }

    /**
     */
    public IRegistry.Number getNumberOfVectorServices(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetNumberOfVectorServicesMethod(), getCallOptions(), request);
    }

    /**
     */
    public IRegistry.Result checkInvariant(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getCheckInvariantMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class IRegistryFutureStub extends io.grpc.stub.AbstractFutureStub<IRegistryFutureStub> {
    private IRegistryFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IRegistryFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IRegistryFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerVectorService(
        IRegistry.ServiceEndpoint request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterVectorServiceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<IRegistry.VectorServices> getVectorServices(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetVectorServicesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerTM(
        IRegistry.ServiceEndpoint request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterTMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<IRegistry.ServiceEndpoint> getTM(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> registerTPLM(
        IRegistry.ServiceEndpoint request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterTPLMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<IRegistry.ServiceEndpoint> getTPLM(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetTPLMMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<IRegistry.Number> getNumberOfVectorServices(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetNumberOfVectorServicesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<IRegistry.Result> checkInvariant(
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
    private final IRegistryImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(IRegistryImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_VECTOR_SERVICE:
          serviceImpl.registerVectorService((IRegistry.ServiceEndpoint) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_VECTOR_SERVICES:
          serviceImpl.getVectorServices((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<IRegistry.VectorServices>) responseObserver);
          break;
        case METHODID_REGISTER_TM:
          serviceImpl.registerTM((IRegistry.ServiceEndpoint) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_TM:
          serviceImpl.getTM((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<IRegistry.ServiceEndpoint>) responseObserver);
          break;
        case METHODID_REGISTER_TPLM:
          serviceImpl.registerTPLM((IRegistry.ServiceEndpoint) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_TPLM:
          serviceImpl.getTPLM((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<IRegistry.ServiceEndpoint>) responseObserver);
          break;
        case METHODID_GET_NUMBER_OF_VECTOR_SERVICES:
          serviceImpl.getNumberOfVectorServices((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<IRegistry.Number>) responseObserver);
          break;
        case METHODID_CHECK_INVARIANT:
          serviceImpl.checkInvariant((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<IRegistry.Result>) responseObserver);
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

  private static abstract class IRegistryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    IRegistryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return IRegistry.IRegistryOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("IRegistry");
    }
  }

  private static final class IRegistryFileDescriptorSupplier
      extends IRegistryBaseDescriptorSupplier {
    IRegistryFileDescriptorSupplier() {}
  }

  private static final class IRegistryMethodDescriptorSupplier
      extends IRegistryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    IRegistryMethodDescriptorSupplier(String methodName) {
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
      synchronized (IRegistryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new IRegistryFileDescriptorSupplier())
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
