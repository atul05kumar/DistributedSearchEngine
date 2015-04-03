import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ankur
 */
public class Result_Set implements Serializable {
    private ArrayList<ArrayList<Cell> > results;
    private static final long serialVersionUID = 42L;

    public ArrayList<ArrayList<Cell> > getResults() {
        return results;
    }

    public void setter(ArrayList<ArrayList<Cell> > results) {
        this.results = results;
    }
}