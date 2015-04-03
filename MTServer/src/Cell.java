
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adityabahuguna
 */
public class Cell implements Serializable {
    int docid;
    int freq;
    private static final long serialVersionUID = 42L;
    
    public Cell(int docid, int freq) {
        
        this.freq = freq;
        this.docid = docid;
        
    }
    
    public String toString() {
        return ("["+docid + "," + freq + "]");
    }
    
    public int getDocId() {
        return docid;
    }
    
    public int getFreq() {
        return freq;
    }
}
