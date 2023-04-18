/**
	File Utilities for reading and writing
	@author Pranav Belligundu
	@since Aug 19, 2021
**/
import java.io.*;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtils{
	/** 
	 * opens a file to read using the Scanner class
	 * @param fileName 		name of the file to open
	 * @return 				the Scanner object to the file
	**/
	public static Scanner openToRead(String fileName){
		Scanner input = null;
		try{
			input = new Scanner(new File(fileName));
		}catch(FileNotFoundException e){
			System.err.println("ERROR: cannot open "+fileName+" for reading.");
			System.exit(42);
		}
		return input;
	}
	/** 
	 * opens a file to write using the PrintWriter class
	 * @param fileName 		name of the file to open
	 * @return 				the printWriter object to the file
	**/
	public static PrintWriter openToWrite(String fileName){
		PrintWriter output = null;
		try{
			output = new PrintWriter(new File(fileName));
		}catch(FileNotFoundException e){
			System.err.println("ERROR: cannot open "+fileName+" for writing.");
			System.exit(43);
		}
		return output;
	}
}