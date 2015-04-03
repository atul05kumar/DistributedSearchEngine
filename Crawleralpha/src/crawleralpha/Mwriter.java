package crawleralpha;

import java.io.*;

/**
 *
 * @author Aniket
 */
public class Mwriter {
    
    private String path;
    private boolean append_to_file = true;
    public void writetoFile (String filename, String textLine) throws IOException {
        
        PrintWriter print_line = new PrintWriter(filename);       
        
        print_line.printf("%s" + "%n" , textLine);
        
        print_line.close();
        
    }
    
    public void writetoFile2( String filename, String data ) throws IOException
    {	
            FileWriter fileWritter = new FileWriter(filename,true);
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	    bufferWritter.write(data);
    	    bufferWritter.close();
 
    }
       
}

