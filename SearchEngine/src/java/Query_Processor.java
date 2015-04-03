import com.mysql.jdbc.Connection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ankur
 */
public class Query_Processor {

    public static Connection conn;
    public static ArrayList<String> stopList;

    public static void set_properties() {
        System.getProperties().put("http.proxyHost", "172.31.1.4");
        System.getProperties().put("http.proxyPort", "8080");
        System.getProperties().put("http.proxyUser", "iit2012019");
        System.getProperties().put("http.proxyPassword", "knnstl");
    }

    public static void connect_to_db() {
        try {
            String url = "jdbc:mysql://172.19.19.73:3306/Crawler";
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = (Connection) DriverManager.getConnection(url, "root", "");
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pre_setup() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("/home/ankur/NetBeansProjects/SearchEngine/StopWordList.txt"));
        
        stopList = new ArrayList<String>();

        String tokens[] = br.readLine().trim().split(",");

        for (String token : tokens) {
            stopList.add(token);
        }
    
    }
    
    public static void initialise() {
        
        try {
            pre_setup();
            set_properties();
            connect_to_db();
        } catch (Exception ex) {
            System.out.println("init: " + ex);
        }
    }
    
    public static ArrayList<String> fire_query(String query) {
        Query obj;
        obj = new Query(query);
        Thread t;
        t = new Thread(obj);
        t.setName(query + " " + System.currentTimeMillis());
        t.start();
        
        try {
            t.join();
        } catch (InterruptedException ex) {
            System.out.println("fire_query: " + ex);
        }
        
        return obj.getUrls();
    }
    
    public static void end() {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
