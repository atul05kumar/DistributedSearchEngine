
import java.io.BufferedOutputStream;
import java.lang.Runnable;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author ankur
 */
public class Query implements Runnable {
    public ArrayList<String> tokens;
    private String query;
    private QueryInput obj;
    private ArrayList<String> urls;
    private Result_Set postings;
    public static final int[] toSlave = {2, 2, 2, 2, 2, 1, 1, 3, 2, 2, 1, 3, 3, 3, 1, 1, 3, 3 , 3, 1, 2, 3, 2, 3, 1, 3};
    private static final String SLAVE1_IP = "172.17.11.212";
    private static final String SLAVE2_IP = "172.19.18.211";
    private static final String SLAVE3_IP = "172.17.11.213";
    private static final int SOCKET_PORT = 4501;
    private Socket sock;
    
    public Query(String q) {
        query = q;
        urls = new ArrayList<String>();
        tokens = new ArrayList<String>();
        postings = null;
        obj = null;
        
        //tokenizing the query
        query = query.toLowerCase();
        String x = "";
        for(String str : query.split("\\b")) {
            if(Query_Processor.stopList.contains(str)) {
            } else {
                x += str;
            }
        }
        
        query = x;
        query = Stemmer.initiate(query);
        String[] dummy = query.trim().split("\\s+");
        for (String i : dummy) {
            tokens.add(i);
        }
    }
    
    public ArrayList<String> getUrls() {
        return this.urls;
    }
    
    private Result_Set send_and_receive(String address, Result_Set postings) {
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        Socket sock = null;
        
        try { 
            //connecting to slave
            try {
                sock = new Socket(address, SOCKET_PORT);
                System.out.println("Connected to " + address);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            
            //sending tokens and receiving postings
            try {
                
                os = new ObjectOutputStream(sock.getOutputStream());
                if (os == null) System.out.println("Could not get output stream");
           
                os.writeObject(obj);
                os.flush();
                
                is = new ObjectInputStream(sock.getInputStream());
                if (is == null) System.out.println("Could not get input stream");
                postings = (Result_Set)is.readObject();
                return postings;
            } catch (Exception ex) {
                System.out.println("here: " + ex);
            }
            
            if (is != null) is.close();
            if (os != null) os.close();
            if (sock != null) sock.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return null;
    }
    
    @Override
    public void run() {
        ArrayList<String> d1 = new ArrayList<String>();
        ArrayList<String> d2 = new ArrayList<String>();
        ArrayList<String> d3 = new ArrayList<String>();
        
        int id;
        for (String x : tokens) {
            id = toSlave[x.charAt(0) - 'a'];
            if (id == 1) d1.add(x);
            else if (id == 2) d2.add(x);
            else d3.add(x);
        }
        
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        
        obj = new QueryInput();
        obj.setter(d1);
        Result_Set r1 = null;
        r1 = send_and_receive(SLAVE1_IP, r1);
        
        obj = new QueryInput();
        obj.setter(d2);
        Result_Set r2 = null;
        r2 = send_and_receive(SLAVE2_IP, r2);
        
        obj = new QueryInput();
        obj.setter(d3);
        Result_Set r3 = null;
        r3 = send_and_receive(SLAVE3_IP, r3);
        
        System.out.println(r1.getResults());
        System.out.println(r2.getResults());
        System.out.println(r3.getResults());
        
        postings = new Result_Set();
        ArrayList<ArrayList<Cell>> ans = new ArrayList<ArrayList<Cell>>();
        ArrayList<ArrayList<Cell>> dumy = new ArrayList<ArrayList<Cell>>();
        dumy = r1 == null ? null : r1.getResults();
        for (ArrayList<Cell> x : dumy) {
            ans.add(x);
        }
        
        dumy = r2 == null ? null : r2.getResults();
        for (ArrayList<Cell> x : dumy) {
            ans.add(x);
        }
        
        dumy = r3 == null ? null : r3.getResults();
        for (ArrayList<Cell> x : dumy) {
            ans.add(x);
        }
        
        postings.setter(ans);
        
        
        ArrayList<Cell> results = null;
        if (postings.getResults().isEmpty()) System.out.println("No ResultSet returned :(");
        else {    
           // System.out.println(postings.getResults());
            Merge listing = new Merge(postings.getResults());
            results = listing.match();
        }
        
        try {
            Statement stmt = Query_Processor.conn.createStatement();
            ResultSet rs;
           // System.out.println(results.size());
            for(Cell page : results) {              
                String sql = "select URL from record3 where RecordID = " + page.getDocId() + ";";
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                   // System.out.println(page.getDocId() + " " + rs.getString(1));
                    urls.add(rs.getString(1));
                }
            }
            System.out.println(urls);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
