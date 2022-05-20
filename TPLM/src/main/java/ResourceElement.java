import java.util.concurrent.locks.Condition;

public class ResourceElement {
    int serverID = 0;
    int elementIndex = 0;
    int lockType; //1=read, 2=write
}
