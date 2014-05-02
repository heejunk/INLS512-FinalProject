import java.util.List;
import java.util.Properties;
import java.io.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.time.*;
import edu.stanford.nlp.util.CoreMap;

public class SUTime {

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

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		    }  
		}    
		
		for (int i = 0 ; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				
				String inputFileName = listOfFiles[i].getName();  
				String inputDirFile = "corpus\\" + inputFileName ;
				String outputFileName = "results\\" + "TE-" + listOfFiles[i].getName();
				String inputText = Utility.readFile(inputDirFile) ;		
		 
			    Properties props = new Properties();
			    AnnotationPipeline pipeline = new AnnotationPipeline();
			    pipeline.addAnnotator(new PTBTokenizerAnnotator(false));
			    pipeline.addAnnotator(new WordsToSentencesAnnotator(false));
			    pipeline.addAnnotator(new POSTaggerAnnotator(false));
			    pipeline.addAnnotator(new TimeAnnotator("sutime", props));
		
			    Annotation annotation = new Annotation(inputText);
			    annotation.set(CoreAnnotations.DocDateAnnotation.class, "2015-03-24");
			    pipeline.annotate(annotation);
			    //Utility.writeStringToFile(outputFileName, annotation.get(CoreAnnotations.TextAnnotation.class));
			    List<CoreMap> timexAnnsAll = annotation.get(TimeAnnotations.TimexAnnotations.class);
			    for (CoreMap cm : timexAnnsAll) {
			      List<CoreLabel> tokens = cm.get(CoreAnnotations.TokensAnnotation.class);
			      Utility.writeStringToFile(outputFileName, inputFileName + "|" + cm + "|" + 
			    	  cm.get(TimeExpression.Annotation.class).getTemporal().getTimexType() + "|" +
			          tokens.get(0).get(CoreAnnotations.CharacterOffsetBeginAnnotation.class) +
			          "-" + tokens.get(tokens.size() - 1).get(CoreAnnotations.CharacterOffsetEndAnnotation.class) + '|' + "\n");
			    }

				
			}
			
			
			
		}
		
	
	}
	
	catch (Exception e) {//Catch exception if any
		System.err.println("Error: " + e.getMessage());			
	}
	  
	
  }

}

class Utility {
	
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
