
import java.util.Objects;


/**
 *
 * @author ankur
 */
public class Pair{
    private Integer first;
    private Integer second;
    private Integer id;

    public Pair(Integer first, Integer second, Integer id) {
    	super();
    	this.first = first;
    	this.second = second;
        this.id = id;
    }

    @Override
    public int hashCode() {
    	int hashFirst = first != null ? first.hashCode() : 0;
    	int hashSecond = second != null ? second.hashCode() : 0;

    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }
    
    public int compareTo(Pair  op) {  
        if (!Objects.equals(this.first, op.first)) return this.first - op.first;
        else return this.second - op.second;
    }

    public String toString()
    { 
           return "(" + first + ", " + second + ")"; 
    }

    public Integer getFirst() {
    	return first;
    }

    public void setFirst(Integer first) {
    	this.first = first;
    }

    public Integer getSecond() {
    	return second;
    }

    public void setSecond(Integer second) {
    	this.second = second;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
}
