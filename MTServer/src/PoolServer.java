import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author adityabahuguna
 */

public class PoolServer {  
    public final static int UPDATE_PORT = 4401;
    public final static int QUERY_PORT = 4501;
    
    public static void main(String[] args) throws IOException {
        
        ServerSocket server1, server2;
        
        System.getProperties().put("http.proxyHost", "172.31.1.4");
        System.getProperties().put("http.proxyPort", "8080");
        System.getProperties().put("http.proxyUser", "iit2012036");
        System.getProperties().put("http.proxyPassword", "Atulis*no.1");
        
        System.out.println("HOST IS: " + InetAddress.getLocalHost());
        
        
        ExecutorService outerPool = Executors.newFixedThreadPool(2);
        
        server1 = new ServerSocket(UPDATE_PORT);
        server2 = new ServerSocket(QUERY_PORT);
        
        Callable<Void> task1 = new UpdateIndexTable(server1);
        Callable<Void> task2 = new AnswerQuery(server2);
        
        outerPool.submit(task1);
        outerPool.submit(task2);
    }
    
    private static class UpdateIndexTable implements Callable<Void> {
        private ServerSocket server1;
        
        UpdateIndexTable(ServerSocket server1) {
            this.server1 = server1;
        }
        
        public Void call() throws IOException {
            ExecutorService innerPool1 = Executors.newFixedThreadPool(3);
            
            while(true) {
                System.out.println("Waiting for the client to connnect");
                Socket connection = server1.accept();
                System.out.println("Waiting for the client over~!!!");
                //Create a new task
                Callable<Void> new_task = new UpdateTask(connection);
                innerPool1.submit(new_task);
            }
            
        }
        
        private static class UpdateTask implements Callable<Void> {
            Socket connection;
            ObjectInputStream IS;
            
            UpdateTask(Socket connection) {
                this.connection = connection;
            }
            
            public Void call() throws IOException, ClassNotFoundException {
                IS = new ObjectInputStream(connection.getInputStream());
                TinyIndexTable tinyTable = (TinyIndexTable)IS.readObject();
                System.out.println("I got this id: " + tinyTable.getDocId());
                
                IndexTableA.update(tinyTable);
                
                System.out.println("" + IndexTableA.INDEX);
                connection.close();
                return null;
            }
        }
    }
    
    
    private static class AnswerQuery implements Callable<Void> {
        private ServerSocket server2;
        
        AnswerQuery(ServerSocket server2) {
            this.server2 = server2;
        }
        
        public Void call() throws IOException {
            ExecutorService innerPool2 = Executors.newFixedThreadPool(5);
            
            while(true) {
                
                Socket connection = server2.accept();
                //Create a new Search Task
                Callable<Void > new_task = new SearchTask(connection);
                innerPool2.submit(new_task);
            }
            
        }
        
        private static class SearchTask implements Callable<Void> {
        
            private Socket connection = null;
            private ObjectInputStream inStream = null;
            private ObjectOutputStream outStream = null;
           // private BufferedInputStream bis = null;

            SearchTask(Socket connection) throws SocketException, IOException {
                this.connection = connection;

            }

            public Void call() throws ClassNotFoundException, InterruptedException {
                try {

                  inStream = new ObjectInputStream(connection.getInputStream());
                  QueryInput inputQuery = (QueryInput)inStream.readObject();
                  ArrayList<String> query = inputQuery.getQuery();
                  System.out.println(query);
                  Result_Set resultObj = new Result_Set();
                  ArrayList<ArrayList<Cell> > results = new ArrayList<>();

                  for(int i = 0;i < query.size();i++) {
                      String term = query.get(i);
                      ArrayList<Cell> posting = IndexTableA.answerQuery(term);
                      results.add(posting);
                  }

                  resultObj.setter(results);

                  outStream = new ObjectOutputStream(connection.getOutputStream());
                  outStream.writeObject(resultObj);
                  outStream.flush();

                  outStream.close();
                  inStream.close();

                } catch(IOException ex){

                } finally {
                    try {
                        connection.close();
                    } catch(IOException e) {

                    }
                }

                return null;
            }
        }
    }
    
    
    
   
}
