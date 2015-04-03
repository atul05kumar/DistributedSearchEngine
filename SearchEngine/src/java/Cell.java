import java.io.Serializable;

/**
 *
 * @author ankur
 */
public class Cell implements Serializable {
    int docid;
    int freq;
    private static final long serialVersionUID = 42L;
  

    public Cell(int docid, int freq) {
        this.freq = freq;
        this.docid = docid;
    }
    
    @Override
    public String toString() {
        return docid + " " + freq;
    }
    
    public int getDocId() {
        return docid;
    }

    public int getFreq() {
        return freq;
    }
}