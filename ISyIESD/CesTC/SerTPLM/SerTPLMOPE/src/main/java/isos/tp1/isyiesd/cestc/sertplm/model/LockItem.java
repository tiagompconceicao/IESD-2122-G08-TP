package isos.tp1.isyiesd.cestc.sertplm.model;

public class LockItem {

    private int tid;
    private boolean read;
    private boolean write;

    public LockItem(int tid){
        this.tid = tid;
        read = true;
        write = true;
    }

    public int getTid() {
        return tid;
    }

    public boolean isReadable(){
        return read;
    }
    public boolean isWritable(){
        return write;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }
}
