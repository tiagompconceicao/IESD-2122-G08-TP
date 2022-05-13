package isos.tp1.isyiesd.cestc.sertm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionManager {

    //Save transaction ids and save Vectors operations of each transaction
    private final HashMap<Integer,List<String>> transactions = new HashMap<>();
    private final Object lock = new Object();

    private int maxTID = 0;

    public TransactionManager(){

    }

    public int createTransaction(){
        int tid;
        synchronized (lock){
            tid = ++maxTID;
            transactions.put(tid,new ArrayList<>());
        }

        return tid;
    }

    public void registerActivity(int tid, String server){
        synchronized (lock){
            transactions.get(tid).add(server);
        }
    }

    public boolean commit(int tid){
        synchronized (lock){
            for (String server: transactions.get(tid)) {
                //Call axPrepare for each server
                //Then calculate the sum of Vectors values
                //Verify if is all ok
            }
        }

        return true;
    }

    public void rollback(){

    }
}
