import com.mysql.jdbc.Connection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

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
        BufferedReader br = new BufferedReader(new FileReader("StopWordList.txt"));
        stopList = new ArrayList<>();

        String tokens[] = br.readLine().trim().split(",");

        for (String token : tokens) {
            stopList.add(token);
        }
    
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String query;
        Query obj;
        Thread t;

        try {
            pre_setup();
            set_properties();
            connect_to_db();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        

        System.out.println("Please enter your query:");
        query = in.nextLine();
        while (query.equals("exit") == false) {
            obj = new Query(query);
            t = new Thread(obj);
            t.setName(query + " " + System.currentTimeMillis());
            t.start();
            query = in.nextLine();
        }

        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
