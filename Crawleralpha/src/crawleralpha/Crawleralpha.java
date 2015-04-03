package crawleralpha;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Aniket
 */

public class Crawleralpha {

  public static void main(String[] args) throws SQLException, IOException {
 
        Mycrawler obj = new Mycrawler();
        obj.begincrawl();
        
    }
    
}