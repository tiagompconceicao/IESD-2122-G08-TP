package isos.tp1.isyiesd.cestc.sertm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionManager {

    //Save transaction ids and save Vectors operations of each transaction
    private final HashMap<Integer,List<String>> transactions = new HashMap<>();

    private int maxTID = 0;

    public TransactionManager(){

    }

    public int createTransaction(){
        int tid = ++maxTID;
        transactions.put(tid,new ArrayList<>());

        return tid;
    }
}
