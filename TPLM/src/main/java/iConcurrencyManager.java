public interface iConcurrencyManager {
    void lock(LockRequestInfo req, Boolean Response);
    void unlock(UnlockRequestInfo req);
}
