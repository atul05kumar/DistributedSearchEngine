package crawleralpha;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.SQLException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Aniket
 */

public class Mycrawler {
    static String table_name = "Record1";
    static String logfile = "pagedata/crawl_log1.txt";
    static int LIMIT = 100000;
    static Mwriter writer_obj;

    volatile static int added = 0;

    int NTHREADS = 4;

    public static  DB db = new DB();

    String[] seedurl = {"http://timesofindia.indiatimes.com", "http://www.geeksforgeeks.org", "http://www.thehindu.com", "http://www.foxsports.com"};

	public void begincrawl() throws SQLException, IOException {
            System.getProperties().put("http.proxyHost", "172.31.1.3");
            System.getProperties().put("http.proxyPort", "8080");
            System.getProperties().put("http.proxyUser", "iit2012###");
            System.getProperties().put("http.proxyPassword", "######");

            db.runSql2("TRUNCATE " + table_name + ";");

            writer_obj = new Mwriter();
            pageProcessor();
	}

        public void pageProcessor() {

            Crawltask[] thread = new Crawltask[NTHREADS];

            for (int i = 0; i < NTHREADS; ++i){
                thread[i] = new Crawltask(seedurl[i]);
                thread[i].start();
            }

        }

        // For Debugging
        public void processPage2() throws SQLException, IOException{
            Document dc;
            String testcase = "http://www.ic.unicamp.br/~meidanis/courses/mc336/2009s2/prolog/problemas/";
            try {
               dc = Jsoup.connect(testcase).get();
               String s = dc.text();

                System.out.println(s);
            } catch (IOException e) {
                System.out.println("Not Possible !!!");
            }
        }
}

