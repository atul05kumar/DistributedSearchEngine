
import java.io.Serializable;
import java.util.Hashtable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adityabahuguna
 */
public class TinyIndexTable implements Serializable {
    private Hashtable<String, Integer> myhash;
    private int docid;
    public static long serialVersionUID = 43L;
    
    public TinyIndexTable(Hashtable<String, Integer> myhash, Integer docid) {
        this.docid = docid;
        this.myhash = myhash;
    }
    
    public int getDocId() {
        return docid;
    }
    
    public Hashtable<String, Integer> getHashTable() {
        return myhash;
    }
    
}
