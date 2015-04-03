package crawleralpha;

import static crawleralpha.Mycrawler.db;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Aniket
 */
public class Crawltask extends Thread {
    
    Queue<String> qarl ;
    int visited;
    String sentdata;
    
    Crawltask(String sd) {
    qarl = new LinkedList<>();
    qarl.add(sd);
    visited = 0;
    }
    
    
    
     @Override
     public void run() {
          
        try {
            processPagec();
        } catch (SQLException ex) {
            Logger.getLogger(Crawltask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Crawltask.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    
     }
    
    public void processPagec() throws SQLException, IOException {

                while (visited < Mycrawler.LIMIT) {
                    
                String URL = qarl.element();
                qarl.remove();
                visited++;
                
                System.out.println(" Url supplied : " + URL + "****************");
                Mycrawler.writer_obj.writetoFile2(Mycrawler.logfile, " Url supplied : " + URL + "****************\n");
                
		String sql = "select * from " + Mycrawler.table_name + " where URL = '" + URL + "'";
		ResultSet rs = db.runSql(sql);
		if(rs.next()){
 
		}else{
			//store the URL to database to avoid parsing again			
 
			//get useful information
                        Document doc;
                        try {
			 doc = Jsoup.connect(URL).get();
                         //  System.out.print("Page IS :\n" + doc.text() + "\n");
                         sentdata = "";
                         sentdata += doc.text();
                         
                         Curstate.urladder(URL, sentdata);
                      
                        
                        } catch (IOException e){
                            System.out.println("Not an html page");
                            Mycrawler.writer_obj.writetoFile2(Mycrawler.logfile, "Not an Html page \n");
                            continue;
                        }
                        
 
			//get all links and recursively call the processPage method
			Elements questions = doc.select("a[href]");
                        
                        for(Element link: questions) {
                            String tmpurl;
                            tmpurl = link.attr("abs:href");
				if(link.attr("href").contains("http")) {
                                    if (link.attr("href").contains("'"))
                                        continue;
                                    
                                    System.out.println("URL trying to added :" + tmpurl);
                                    String tsql = "";
                                    
                                    try {
                                    tsql = "select * from " + Mycrawler.table_name +" where URL = '" + tmpurl + "'";
                                    } catch(Exception e) {
                                        continue;
                                    }
                                    ResultSet trs = db.runSql(tsql);
                                    
                                    if(!trs.next()) {
                                       
                                        qarl.add(link.attr("abs:href"));                                
                                        System.out.println("URL added :" + (link.attr("abs:href")));
                                        
                                    } else {
                                        System.out.println("URL NOT added :" + (link.attr("abs:href")));
                                    }
                                                               
				  
                                }
			}
		}
              }
	}
} 