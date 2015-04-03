import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ankur
 */
public class QueryInput implements Serializable {
    private ArrayList<String> query;
    private static final long serialVersionUID = 42L;
    
    public ArrayList<String> getQuery() {
        return query;
    }
    
    public void setter(ArrayList<String> query) {
        this.query = query;
    }
}