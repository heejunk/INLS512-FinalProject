import java.util.List;
import java.util.Properties;
import java.io.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.*;
import edu.stanford.nlp.util.CoreMap;

public class CLEFBatch {

  /** Example usage:
   *  java SUTimeDemo "Three interesting dates are 18 Feb 1997, the 20th
   of july and 4 days from today."
   *
   *  @param args Strings to interpret
   */
  public static void main(String[] args) {
	
	try {
		
		
		//to get the file name in your foler
		File folder = new File("corpus\\");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0 ; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				
				String inputFileName = listOfFiles[i].getName();  
				String outputFileName = "results\\" + "clef.bat";
						    
			    Utility2.writeStringToFile(outputFileName, "CALL bin\\metamap13 -N C:\\Groups\\CLEF\\Corpus\\" +
			    		inputFileName + " " + "C:\\Groups\\CLEF\\Results\\" +
			    		inputFileName.replace(".txt", "_interim.txt") + "\n") ; 
			    		//"PING 127.0.0.1 -n 30 >nul" + "\n");		
			}
			
			
			
		}
		
	
	}
	
	catch (Exception e) {//Catch exception if any
		System.err.println("Error: " + e.getMessage());			
	}
	  
	
  }

}

class Utility2 {
	
	public static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	public static void writeStringToFile(String filePathAndName, String stringToBeWritten) throws IOException{    
		try
		{
			String filename= filePathAndName;
			boolean append = true;
			FileWriter fw = new FileWriter(filename,append);

			fw.write(stringToBeWritten);//appends the string to the file
			//fw.write("\n");
			fw.close();
		}

		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}
	}
}
