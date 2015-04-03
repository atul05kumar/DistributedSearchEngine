package crawleralpha;

import static crawleralpha.Mycrawler.db;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Aniket
 */
public class Curstate {
   static String cursql;
   

public static synchronized void urladder(String URL, String sentdata) throws SQLException, IOException {
   
    Mycrawler.added = Mycrawler.added + 1;
    
    Mycrawler.writer_obj.writetoFile("pagedata/id" + (Mycrawler.added) +".txt", sentdata);
    // writer_obj.writetoFile("pagedata/id"+ (added) +".txt",doc.text());
                           
    cursql = "INSERT INTO  `Crawler`.`" + Mycrawler.table_name +"` " + "(`URL`) VALUES " + "(?);";
    PreparedStatement stmt = db.conn.prepareStatement(cursql, Statement.RETURN_GENERATED_KEYS);
    stmt.setString(1, URL);
    stmt.execute();
    }
}
