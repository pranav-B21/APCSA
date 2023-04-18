// imports go here
import java.util.Scanner;
import java.io.PrintWriter;
/**
 *	MVCipher - Add your description here
 *	Requires Prompt and FileUtils classes.
 *	
 *	@author	Pranav Belligundu
 *	@since	Sept 17, 2021
 */
public class MVCipher {
	
	// fields go here
	private String key, inFile, outFile, choice;
	private int encrpytDecrypt;
	private Scanner reader;
	private PrintWriter writer;
	
	private final int LENGTH = 26;//length of the alphabet
	private final int LENGTH_UPPER = 64;
	private final int LENGTH_LOWER = 96;
	/** Constructor */
	public MVCipher() { 
	}
	public static void main(String[] args) {
		MVCipher mvc = new MVCipher();
		mvc.run();
	}
	/**
	 *	Method header goes here
	 */
	public void run() {
		System.out.println("\n Welcome to the MV Cipher machine!\n");
		
		/* Prompt for a key and change to uppercase
		   Do not let the key contain anything but alpha
		   Use the Prompt class to get user input */
		boolean leave = true;
		do{
			key = Prompt.getString("\nPlease input a word to use as a key (letters only)");
			for(int i = 0; i < key.length(); i++){
				if((int)key.charAt(i) >= 65 && (int)key.charAt(i) <= 90) leave = true;
				if((int)key.charAt(i) >= 97 && (int)key.charAt(i) <= 122) leave = true;
				else leave = false;
			}
		}while(!leave);
		key = key.toUpperCase();
		/* Prompt for encrypt or decrypt */
		encrpytDecrypt = Prompt.getInt("\nEncrypt or decrypt?",1,2);
		
		/* Prompt for an input file name */
		if(encrpytDecrypt == 1){
			choice = "encrypt";
		}
		else{
			choice = "decrypt";
		}	
		inFile = Prompt.getString("Name of file to "+choice);
		
		/* Prompt for an output file name */
		outFile = Prompt.getString("Name of output File");
		
		/* Read input file, encrypt or decrypt, and print to output file */
		reader = FileUtils.openToRead(inFile);
		String text = readFile();
		String output = convert(text);
		
		writer = FileUtils.openToWrite(outFile);
		writer.print(output);
		
		/* Don't forget to close your output file */
		writer.close();
		reader.close();
	}
	public String readFile(){
		String str = "";
		while(reader.hasNext()){
			str += reader.nextLine() + "\n";
		}
		return str;
	}
	public String convert(String text){
		String result = "";
		char letter = ' ';
		int letterNum = 0;
		/*
		int[] intKeys = new int[key.length()];
		for(int i = 0; i < intKeys.length; i++){
			intKeys[i] = (int)key.charAt(i)-64;
			//System.out.println(intKeys[i]);
		} 
		*/
		for(int i = 0; i < text.length(); i++){
			letter = text.charAt(i);
			if(letter >= 'A' && letter <= 'Z'){
				letterNum ++;
				result += toUpperCase(letterNum, letter);
			}
			else if(letter >= 'a' && letter <= 'z'){
				letterNum ++;
				result += toLowerCase(letterNum, letter);
			}
			else result += letter;
		}
		//add intKeys value then subtract again by 64 for upper case and 96 for lower case
		return result;
	}
	public char toUpperCase(int letterNum, char letter){
		int shift = (int)(key.charAt(letterNum%key.length()) - 'A' +1);
		letter = (char)('a' + (letter + shift - 'a')%26);
		return letter;
	}
	public char toLowerCase(int letterNum, char letter){
		int shift = (int)(key.charAt(letterNum%key.length()) - 'a' +1);
		letter = (char)('A' + (letter + shift - 'A')%26);
		return letter;
	}
	// other methods go here
	
}
