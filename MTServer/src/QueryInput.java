
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adityabahuguna
 */
public class QueryInput implements Serializable {
    
    private ArrayList<String> query;
    private static final long serialVersionUID = 42L;
    
    public ArrayList<String> getQuery()
    {
        return query;
    }
    
    public void setter(ArrayList<String> query) {
        this.query = query;
    } 
    
}
