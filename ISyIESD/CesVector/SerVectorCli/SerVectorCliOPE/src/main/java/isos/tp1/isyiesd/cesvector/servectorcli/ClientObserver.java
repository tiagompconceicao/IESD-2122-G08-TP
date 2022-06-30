package isos.tp1.isyiesd.cesvector.servectorcli;

import io.grpc.stub.StreamObserver;
import lockManager.Response;

public class ClientObserver implements StreamObserver<Response> {
    private boolean isCompleted=false; private boolean success=false;
    public boolean IsSuccessful() { return success; }
    public boolean isCompleted() { return isCompleted; }

    @Override
    public void onNext(Response reply) {
        success = reply.getStatus();
        String s = success ? "success" : "aborted";
        System.out.println("Async Callback (OnNext "+ s +").");
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error on call:"+throwable.getMessage());
        isCompleted=true; success=false;
    }

    @Override
    public void onCompleted() {
        System.out.println("Async Callback (OnCompleted).");
        isCompleted=true;
    }
}

