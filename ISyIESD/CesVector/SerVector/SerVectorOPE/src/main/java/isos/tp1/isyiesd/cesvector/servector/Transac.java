package isos.tp1.isyiesd.cesvector.servector;

import java.util.LinkedList;

public class Transac {
    public LinkedList<Write> writes;
    public Transac() {
        writes = new LinkedList<Write>();
    }
}
