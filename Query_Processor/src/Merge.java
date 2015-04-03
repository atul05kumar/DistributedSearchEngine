
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;


/**
 *
 * @author ankur
 */
public class Merge {
    private ArrayList<ArrayList<Cell> > listings;
    
    public Merge(ArrayList<ArrayList<Cell> > p) {
        listings = p;
    }
    
    public void sort_it() {
         Comparator<ArrayList<Cell>> x = new Comparator<ArrayList<Cell>>() {
            @Override
            public int compare(ArrayList<Cell> o1, ArrayList<Cell> o2)
            {
                if (o1 == null) return -1;
                else if (o2 == null) return 1;
                else if(o1.size() > o2.size()) return 1;
                else if(o2.size() > o1.size()) return -1;
                else return 0;
            }
        };

       Collections.sort(listings,  x);
    }
    
    public void rank_it(ArrayList<Cell> res) {
        Comparator<Cell> x = new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2)
            {
                return o2.getFreq() - o1.getFreq();
            }
        };

       Collections.sort(res,  x);
    }
    
    public ArrayList<Cell> match() {
        sort_it();
        
        ArrayList<Cell> first_post = listings.get(0);
        if (first_post == null) return null;
        else {
            ArrayList<Cell> result = new ArrayList<>();
            ArrayList<Cell> copy = null;
            int id1, id2, len = first_post.size();
            int x, y;
            
            for(Cell it : first_post) {
                result.add(it);
            }
            
            for (int i = 1; i < listings.size(); i++) {
                copy = new ArrayList<>();
                id1 = id2 = 0;
                len = result.size();
               
                while (id1 < len && id2 < listings.get(i).size()) {
                    x = result.get(id1).getDocId();
                    y = listings.get(i).get(id2).getDocId();
                    if (x == y) {
                        copy.add(new Cell(x, listings.get(i).get(id2).getFreq() + result.get(id1).getFreq()));
                        id1++;
                        id2++;
                    }
                    else if (x > y) id2++;
                    else id1++;
                }
                result = copy;
            }
            
            rank_it(result);
            return result;
        }
    }
}
