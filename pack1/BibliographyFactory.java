//Jacinthe Beaudry (40126080)
//COMP249
//Assignment #3 
//November 13th 2020


/**
 * 
 * @author Jacinthe Beaudry (40126080)
 * COMP249	
 * Assignment #3 
 * Pack1	
 * Due Date: November 13th 2020
 *
 */

package pack1;

import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.FileReader;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.Scanner;
	import java.util.StringTokenizer;
	
	
	
	/**
 * 
 * This is BibliographyFactory class. It is used to read various .bib files that contain one or many articles.
 * These articles will then be converted to bibliographies that respectively follow these reference format: IEEE, ACM and NJ.
 * 
 * @author Jacinthe Beaudry 40126080
 * @version 1.0
 *
 */
	
	
public class BibliographyFactory {
	
	private static int fileNumber = 10;									//the amount of files that will be read, static
	private static String [] fileName = new String[fileNumber];			//the array of names of original files 
	private static String [] newFileNameIEEE = new String[fileNumber];	//the array of names of IEEE output files
	private static String [] newFileNameACM = new String[fileNumber];	//the array of names of ACM output files
	private static String [] newFileNameNJ = new String[fileNumber];	//the array of names of NJ output files
	private static File [] aFileArray = new File [fileNumber];			//the array of original files 
	private static File [] aFileArrayIEEE = new File [fileNumber];		//the array of IEEE output files
	private static File [] aFileArrayACM = new File [fileNumber];		//the array of ACM output files
	private static File [] aFileArrayNJ = new File [fileNumber];		//the array of NJ output files
	private static BufferedReader br = null;	                    	//buffer variable					
	private static PrintWriter pw =null;								//printWriter variable
	private static int invalidNumber = 0;								//keeps track of invalid files amount					

	
	
	// this method will be called when a file contains an invalid input
	// invalid meaning empty
	//the method, when called, will then delete the output files
		public static void deleteOutputFiles(int i) {
			
				if(aFileArrayIEEE[i].exists()) {
					aFileArrayIEEE[i].delete();
				}
				if(aFileArrayACM[i].exists()) {
					aFileArrayACM[i].delete();
				}
				if(aFileArrayNJ[i].exists()) {
					aFileArrayNJ[i].delete();
				}
		}
	
	
	//  this method checks if the String contains numbers
		//since bufferedReader doesn't know when a char is a number
		public static boolean isNumber(String str) {  
	        for (int i = str.length(); --i >= 0;) {  
	            int chr = str.charAt(i);  
	            if (chr < 48 || chr > 57)  
	                return false;  
	        }  
	        return true;  
	    }  
	
	
		/*
		 * This method takes as input one file array and the index.
		 * it is used to validate the .bib files
		 * 
		 */
		public static void processFilesForValidation(File[] afileArray, int fileIndex)
											throws FileInvalidException, IOException  {
			
			String nextStr;     			//will be read by the BufferedReader
			int fieldValid = -1;			//identifies if a field is valid
			int fieldEnd = 0;				//also identifies if a field is valid
			String fieldName;				//name of the field
			String fieldContent;			//content of the field
			boolean anArticle = false;      //identify if an article
			int articleAmnt = 0;			//amount of articles in one file
			int articleIndex = 0;   		//keeps track of the articles we are processing
			
			
			
			for (int i = fileIndex; i < fileNumber; i++) {
				
				br = new BufferedReader (new FileReader(aFileArray[i]));
				articleAmnt = 0;
				articleIndex = 0;
				nextStr = br.readLine();
				
				while (nextStr != null) {     //while there is a String to read, this goes on
					if (nextStr.length() == 0) {     
						nextStr = br.readLine();       
						continue;
					}
					if (nextStr.indexOf("@ARTICLE{") >= 0) {    //this notes that the buffer found a string announcing an article
						articleAmnt++;    //article count goes up by one 
					}
					nextStr = br.readLine();
				}
			
			
			
			//this throws an exception if the file contains no articles
			if (articleAmnt == 0) {
				invalidNumber++;
				throw new FileInvalidException();
			}
			
			//if the file contains articles, it will be put
			//in this array
			Article [] articleArray = new Article[articleAmnt];
			br.close();
			
			
			
			
			//this point on, the files exist and all contain articles
			
			
			
			
			// reading the fields
			
			
			br = new BufferedReader(new FileReader(aFileArray[i]));	
			nextStr = br.readLine();   //reads line
			
			while (nextStr != null) {     			//while the string isn't empty
				if (nextStr.length() == 0) {
					nextStr = br.readLine();
					continue;
				}
				
				if (nextStr.indexOf("@ARTICLE{") >= 0) {
					anArticle = false;
					Article aArticle = new Article();    //article object created 
					
					while (!anArticle) {   //remains false as long as we are reading the same article
						nextStr = br.readLine();
						if (nextStr == null || nextStr.length()==0)
							continue;
									
							
							else{
									fieldValid = nextStr.indexOf("={");
									fieldEnd=nextStr.indexOf("},");
								
										if (fieldValid >= 0 && fieldEnd < 0) {
											//System.out.println("yun");
											while (!nextStr.contains("},")) {    //as long as the article goes on
												String temp = br.readLine();   //stores the current String
												nextStr = nextStr.concat(temp);
												System.out.println(temp);
											}
										}
										//else
											
										if (fieldValid >= 0 && fieldEnd >= 0) { 
											fieldName = nextStr.substring (0, fieldValid);
											fieldContent= nextStr.substring ((fieldValid + 2),fieldEnd);
											//if a field is empty
											if (fieldContent.equals("")) {   
												invalidNumber++;
												throw new FileInvalidException(aFileArray, fieldName, i);
											}
											//else
													//this switch assigns each Article Value to the object's parameters
													//it depends on the field's string
													switch(fieldName) {  
													case "author": aArticle.setAuthor(fieldContent);
																break;
																
													case "journal": aArticle.setJournal(fieldContent);
																break;
																
													case "title": aArticle.setTitle(fieldContent);
																break;
																
													case "year": aArticle.setYear(fieldContent);
																break;
																
													case "volume": aArticle.setVolume(fieldContent);
																break;
																
													case "number": aArticle.setNumber(fieldContent);
																break;
																
													case "pages": aArticle.setPages(fieldContent);
																break;
																
													case "keywords": aArticle.setKeywords(fieldContent);
																break;
																
													case "doi": aArticle.setDoi(fieldContent);
																break;
																
													case "ISSN": aArticle.setIssn(fieldContent);
																break;
																
													case "month": aArticle.setMonth(fieldContent);
																break;
													default: 
														System.out.println(fieldName);
													}
										}
							}
						  	
						if (nextStr.equals("}")) {   //if one of the fields is empty
							if(aArticle.getAuthor()==null||aArticle.getJournal()==null||aArticle.getTitle()==null||
								aArticle.getYear()==null||aArticle.getVolume()==null||aArticle.getNumber()==null||
								aArticle.getPages()==null||aArticle.getKeywords()==null||aArticle.getDoi()==null||
								aArticle.getIssn()==null||aArticle.getMonth()==null) {
								invalidNumber++;
								throw new FileInvalidException(aFileArray, i);    //throws error
							}
							else { //if no field is empty, the article object is cloned to the copy
								anArticle = true;
								articleIndex++;
								articleArray[articleIndex - 1] = aArticle.clone();	
							}
									
						}
					}
				
				}
					
				nextStr = br.readLine();	//goes to the next article
			}
			br.close();
			
			// write: IEEE
			pw = new PrintWriter(new FileOutputStream(newFileNameIEEE[i]));     //creates the IEEE files (array of)
			for (int j = 0; j < articleAmnt; j++) {
				if(articleArray[j] == null) {    //if one of the array articles is empty
					break;
				}
				else {    //writes to the files, IEEE format
					pw.println(ieeeFormat(articleArray[j])+". \""+articleArray[j].getTitle()+"\", "+articleArray[j].getJournal()+", vol. "+articleArray[j].getVolume()+
							", no. "+articleArray[j].getNumber()+", p. "+articleArray[j].getPages()+", "+articleArray[j].getMonth()+" "+articleArray[j].getYear()+"\n");
				}
			}
			pw.close();	
			
			// write: ACM
			pw = new PrintWriter(new FileOutputStream(newFileNameACM[i]));   //creates the IEEE files (array of)
			for(int  j = 0; j < articleAmnt; j++) {
				
				if(articleArray[j]==null) {		//if one of the array articles is empty
					break;
				}
				else {   //writes to the files, ACM format
					pw.println("["+(j+1)+"]\t"+acmFormat(articleArray[j])+". "+articleArray[j].getYear()+". "+articleArray[j].getTitle()+". "+articleArray[j].getJournal()+". "+articleArray[j].getVolume()+
							", "+articleArray[j].getNumber()+"("+articleArray[j].getYear()+"), "+articleArray[j].getPages()+". DOI:https://doi.org/"+"\n");
				}
			}
			pw.close();	
			
			// write: NJ
			pw = new PrintWriter(new FileOutputStream(newFileNameNJ[i]));//creates the NJ files (array of)
			for(int j = 0; j < articleAmnt; j++) {
			
				if(articleArray[j]==null) {   //if one of the array articles is empty
					break;
				}
				else {   //writes to the files, NJ format
					pw.println(njFormat(articleArray[j])+". "+articleArray[j].getTitle()+". "+articleArray[j].getJournal()+". "+articleArray[j].getVolume()+
							", "+articleArray[j].getPages()+"("+articleArray[j].getYear()+").\n");
				}
			}
			pw.close();	
		}
}   
		

	// IEEE author converter
	public static String ieeeFormat(Article a){
		
		if (a != null) { //if the string isn't empty
			String author = a.getAuthor();  //gets author String (full)
			int tokenNumber = 0;
			StringTokenizer authorToken = new StringTokenizer(author);
			tokenNumber = authorToken.countTokens();    //length of String
			String [] tokenArray = new String [tokenNumber];
			
			for (int i = 0; i < tokenNumber; i++) {
				tokenArray [i] = authorToken.nextToken();   //this stores each token in the array
			}	
			
			for (int i = 0; i < tokenNumber; i++) {     //if the token is the author's name, adds a space after it
				if(tokenArray[i].contains(".")) 
				tokenArray[i] = tokenArray[i].concat(" ");	//space added here
			}
			
			String newAuthor = tokenArray[0];   //assigns the Author concat tokens, full name
			for (int i = 1; i < tokenNumber; i++) {
				
				if(tokenArray[i].equals("and")) {     //if the token is "and"
					newAuthor = newAuthor.concat(", ");  // puts comma, signifies end of current author name
				}
				else {      //if token is not "and", adds author name to name array
					newAuthor = newAuthor.concat(tokenArray[i]);
				}
			}
			return newAuthor;     //returns full author string
			
			
		}
		else {
			return null;
		}
	}	
	
	
	//ACM author converter
	public static String acmFormat(Article a){
		if (a != null) {      //if author string isn't empty
			String author = a.getAuthor();      //accessor
			int indexAnd = author.indexOf(" and "); //returns the position of "and"
			
			if ((indexAnd) > 0) {   //finds the name situated between "and" and concat et al
				author = (author.substring(0, indexAnd) + author.concat(" et al"));    
			
			}
			
		
			return author;    //returns author	
	}
	else {
			return null;
		}
	}
	
	
	// NJ author convert
	public static String njFormat(Article a) {
		if (a != null) {  //if author string isn't empty
			String author = a.getAuthor(); //accessor
			int tokenNumber = 0;
			StringTokenizer authorToken = new StringTokenizer(author);    //tokenizes the author string
			tokenNumber = authorToken.countTokens();   //counts the amount of tokens
			String [] tokenArray = new String [tokenNumber];     //creates the array that will store the tokens
			
			for (int i = 0; i < tokenNumber; i++) {        //puts the tokens in the array
				tokenArray [i]= authorToken.nextToken();      
			}	
			
			for (int i = 0; i < tokenNumber; i++) {     //if the token contains a dot --->first name
				if (tokenArray[i].contains(".")) 
				tokenArray[i]=tokenArray[i].concat(" ");	//adds a space after the first name
			}
			
			String newAuthor = tokenArray[0];
			for(int i = 1; i < tokenNumber; i++) {    
				
				if(tokenArray[i].equals("and")) {      //if the token is "and" ----> end of full name
					newAuthor = newAuthor.concat(" & ");    //adds an and to the end of name
				}
				else {
					newAuthor = newAuthor.concat(tokenArray[i]);      //adds the full names to the array
				}
			}
			return newAuthor;

		}
		else {
			return null;
		}
	}	
		
	public static void main(String[] args) {
		
		
		System.out.println("---------------------------------------------\n"
				+ " Welcome to Jacinthe's Bibliography factory!"
				+ " \n---------------------------------------------\n");
		
		
		for (int i = 0; i < fileNumber; i++) {
			//the fileName array will create the file names that will be stored
			//it uses the fileNumber variable to number the files
			fileName[i] = "Latex"+(i+1)+".bib";
			aFileArray[i] = new File(fileName[i]);
			
			//scanner variable
			Scanner isInFile = null;
			
			try {
				isInFile = new Scanner(new FileReader(aFileArray[i]));
					isInFile.close();
			}catch(FileNotFoundException e) {							   
				System.out.println("Could not open input "+fileName[i]+" for reading.\n\n"
						+ "Please check if file exists! "
						+ "Program will terminate after closing any opened files.");
				System.exit(0);	
			} 
		}
		
		//this creates the output json files and their arrays
		//this only executes if the original files were found
		
		for(int i = 0; i < fileNumber; i++) {
			newFileNameIEEE[i] = "IEEE" + (i + 1) + ".json";
			newFileNameACM[i] = "ACM" + (i + 1) + ".json";
			newFileNameNJ[i] = "NJ" + (i + 1) + ".json";
			aFileArrayIEEE[i] = new File(newFileNameIEEE[i]);
			aFileArrayACM[i] = new File(newFileNameACM[i]);
			aFileArrayNJ[i] = new File(newFileNameNJ[i]);	
		}
		
		
		
		// IEEE file format, file not found
				for (int i = 0; i < fileNumber; i++) {
					try {
						pw = new PrintWriter(new FileOutputStream(newFileNameIEEE[i]));  //tries to write to the IEEE files
						pw.close();
					} 
					catch (FileNotFoundException e) {
						System.out.println(newFileNameIEEE[i]+"cannot be opened/created");
						System.out.println("All other created output files will be deleted");	
						for(int j = 0; j <= i; j++)  //if can't write to them, deletes the files
							deleteOutputFiles(i);
						System.out.println("Program will terminate.");
						System.exit(0);	
					}
				}
				
				// ACM file format, file not found
				for(int i = 0; i < fileNumber; i++) {
					try {
						pw = new PrintWriter(new FileOutputStream(newFileNameACM[i]));   //tries to write to the ACM files
						pw.close();
					}
					catch(FileNotFoundException e) {
						System.out.println(newFileNameACM[i]+"cannot be opened/created");
						System.out.println("All other created output files will be deleted");	
						for(int j = 0; j <= i; j++) //if can't write to them, deletes the files
							deleteOutputFiles(i);
						System.out.println("Program will terminate.");
						System.exit(0);	
					}
				}

				// NJ file format, file not found
				//this tries to write to the NJ files
				for (int i = 0; i < fileNumber; i++) {
					try {
						pw = new PrintWriter(new FileOutputStream(newFileNameNJ[i]));   //tries to write to the NJ files
						pw.close();
					}
					catch(FileNotFoundException e) {
						System.out.println(newFileNameNJ[i]+"cannot be opened/created");
						System.out.println("All other created output files will be deleted");	
						for(int j = 0; j <= i; j++) //if can't write to them, deletes the files
							deleteOutputFiles(i);
						System.out.println("Program will terminate.");
						System.exit(0);	
					}
				}
				
				
				try {    //uses the processFileValidation() method
						processFilesForValidation(aFileArray, 0);
				}
				catch(FileInvalidException e) {
					
				}
				catch(IOException e) {       //io exception
						System.out.println("Error: An error has occurred while closing file. ");
						System.out.println("Program will terminate.");
						System.exit(0);	
				}
				finally {      //message with valid and invalid files amount
						System.out.println("A total of "+invalidNumber+" files were invalid, and could not be processed. All other "+
											(fileNumber-invalidNumber)+" \"Valid\" files have been created.");
					}
			
				
					
					//user must write the name of the file they wish to view
				System.out.print("\nPlease enter the name of one of the files that you need to review:");
				Scanner scan = new Scanner(System.in);
				String s = scan.nextLine();
				try {       //read files
					br = new BufferedReader(new FileReader(s));
					System.out.println("\nHere are the contents of the successfully created Json File:"+ s + "\n");
					String nextStr = br.readLine();
					while (nextStr != null) {
						System.out.println(nextStr);
						nextStr = br.readLine();
					}
					System.out.println("Goodbye! Hope you have enjoyed creating the needed files using the Bibliography Factory.");
						
				} 
				catch (FileNotFoundException e) {
					System.out.println("Could not open input file. File does not exist; possibly it could not be created!");
					System.out.println("\nHowever, you will be allowed another chance to enter another file name.");
					System.out.print("Please enter the name of one of the files that you need to review:");
					s = scan.nextLine();
					try {
						br = new BufferedReader(new FileReader(s));
						System.out.println("Here are the contents of the successfully created Json File:"+s);
						String nextStr = br.readLine();
						while (nextStr != null) {
							System.out.println(nextStr);
							nextStr = br.readLine();
						}
					} 
					catch (FileNotFoundException e1) {
						System.out.println("\nCould not open input file again! Either file does not exist or could not be created.");
						System.out.println("Sorry I am unable to display your desired files! Program will exit!");
						System.out.println("\n\n--------------------------------------");
						System.out.println("Thank you for using Yanqi's program! ");
						System.exit(0);
							
					}
					catch (IOException e1) {     //closing file error
						System.out.println("Error: An error has occurred while closing file. ");
						System.out.println("Program will terminate.");
						System.exit(0);	
						}
				}
					catch (IOException e) {       //closing file error
						System.out.println("Error: An error has occurred while closing file. ");
						System.out.println("Program will terminate.");
						System.exit(0);	
					}
					finally{
						scan.close();   //closes the scanner
						System.out.println("\n\n---------------------------------");  //final message
						System.out.println("Thanks for using the Bibliography Factory! "
								+ "Hope you had as much fun as I did! ");
					}
			}

}

class FileInvalidException extends Exception{
	
	/**
	 * This class takes care of all the file exceptions
	 * 
	 * @author Jacinthe Beaudry 40126080
	 * @version 1.0
	 */
	private static final long serialVersionUID = 1L;

	// Constructor 
	public FileInvalidException()
	{
		super("Error: Input file cannot be parsed due to missing information");
		System.out.println(this.getMessage());
		System.out.println("Program will terminate.");
		System.exit(0);
	}
	
	public FileInvalidException(String s)
	{
		super(s);
	}
	
	//constructor that takes care of files with an empty field
	public FileInvalidException(File[] aFileArray ,int i)
	{
		super("Error: Input file cannot be parsed due to missing information in the file: "+ aFileArray[i]);
		System.out.println(this.getMessage());
		System.out.println("Program will terminate.");
		System.exit(0);
	}
	
	// constructor that takes care of invalid files
	public FileInvalidException(File[] aFileArray, String fieldName, int i)
	{
		super("Error: Detected Empty Field!\n"+
				"============================\n\n"+
				"Problem detected with input file: "+aFileArray[i]+"\n"+
				"File is Invalid: Field \""+fieldName+"\" is Empty. Processing stoped at this point. Other empty fields may be present as well!\n");

		System.out.println(this.getMessage());
		BibliographyFactory.deleteOutputFiles(i); 
		try {
			BibliographyFactory.processFilesForValidation(aFileArray,(i+1));
		}catch(FileInvalidException e) {
		}catch(IOException e) {
			System.out.println("Error: An error has occurred while closing file. ");
			System.out.println("Program will terminate.");
			System.exit(0);	
		}	
	}
	
	public String getMessage()
	{
		return super.getMessage();
	}
	
}


