
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
public class Result_Set implements Serializable {
    
    private ArrayList<ArrayList<Cell> > results;
    private static final long serialVersionUID = 42L;
    
    public ArrayList<ArrayList<Cell> > getResults() {
        return results;
    }
    
    public void setter(ArrayList<ArrayList<Cell> > results)  {
        this.results = results;
    }   
}
