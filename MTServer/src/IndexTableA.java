
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adityabahuguna
 */

public class IndexTableA {
    
    static Hashtable<String, ArrayList<Cell> > INDEX = new Hashtable<>();
    
    public static ArrayList<Cell> answerQuery(String term) {
        
        if(INDEX.containsKey(term)) {
            return INDEX.get(term);
        } else {
            return null;
        }
        
    }
    
    public static synchronized void update(TinyIndexTable newIndex) {
        //System.out.println("Reached here");
        
        Hashtable<String, Integer> hash = newIndex.getHashTable();
        Set<String> keys = hash.keySet();
        
        int docid = newIndex.getDocId();
      //  System.out.println("INside update i got this id:" + docid);
        
        Set<String> terms = hash.keySet();
        System.out.println(""+ terms.size());
        
        for(String term : terms) {
            int freq = hash.get(term);
           // System.out.println("TERM: " + term + " Freq:" + freq);
            Cell newCell = new Cell(docid, freq);
            if(INDEX.containsKey(term)) {
                INDEX.get(term).add(newCell);
            } else {
                ArrayList<Cell> to_add = new ArrayList<>();
                to_add.add(newCell);
                INDEX.put(term, to_add);
            }
        }
        
        System.out.println("Done Working " + INDEX);
    }
}
