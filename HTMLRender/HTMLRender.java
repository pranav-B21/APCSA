import java.util.Scanner;
/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author Pranav Belligundu
 *	@version 3rd
 */
public class HTMLRender {
	
	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private final int TOKENS_SIZE = 10000;	// size of array
	
	//
	private int limit;
	private boolean limitThere;
	private String currLine;
	private int counter;
	
	//enums
	private enum States { NONE, PARA, BOLD, ITALIC, QUOTE, H1,
			H2, H3, H4, H5, H6, HR, BREAK, PREFORMATTED }; 
	private States state;
	
	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
	private HTMLUtilities util;
	
	/**
	 * Constructor where the instance vars are intialized
	 */	
	public HTMLRender() {
		// Initialize token array
		tokens = null;
		counter = 0;
		limit = 80;
		limitThere = true;
		state = States.NONE;
		currLine = "";
		
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
		util = new HTMLUtilities();
	}
	/**
	 * Main method where methods are called
	 * 
	 * @param String[] args
	 */
	public static void main(String[] args) {
		HTMLRender hf = new HTMLRender();
		hf.getTokens(args);
		hf.run();
	}
	/**
	 *	This method calls certain methods to print the text on the
	 * 	JPanel.
	 * 
	 * 	If the line is a token, it wont print but it will consider its
	 * 	functions.
	 * 
	 * 	If the line isnt a token, it will print the text based on if its
	 * 	punctuation or not and also print spaces when needed
	 * 	
	 */
	private void run() {
		for (int i = 0; i < counter; i++) {
			String line = tokens[i];
			if (line.charAt(0) == '<') {
				state = changeState(line.toLowerCase());
			}
			else {
				boolean punc = isPunctuation(line);
				if (limitThere && currLine.length() + line.length() > limit) {
					browser.println();
					currLine = "";
				}
				if (punc)
					printLine(line);
				else if (i > 0 && tokens[i-1].equals("<q>"))
					printLine(line);
				else {
					line = " " + line;
					printLine(line);
				}
				currLine += line;
			}
		}
	}
	/**
	 *	Prints out the different tokens functions 
	 * 
	 * 	@param String line		the token
	 */
	private void printLine(String line) {
		switch (state) {
			case QUOTE:
				browser.print(line);
				break;
			case BOLD:
				browser.printBold(line);
				break;
			case ITALIC:
				browser.printItalic(line);
				break;
			case H1:
				browser.printHeading1(line);
				break;
			case H2:
				browser.printHeading2(line);
				break;
			case H3:
				browser.printHeading3(line);
				break;
			case H4:
				browser.printHeading4(line);
				break;
			case H5:
				browser.printHeading5(line);
				break;
			case H6:
				browser.printHeading6(line);
				break;
			case PREFORMATTED:
				browser.print(line);
				browser.printBreak();
				break;
			default:
				browser.print(line);
				break;
		}
	}
	/**
	 *	Sets the enum to the type of token it is
	 * 
	 * 	@param String line		the token
	 */
	private States changeState(String line) {
		switch (line) {
			case "<b>":
				limit = 80;
				limitThere = true;
				return States.BOLD;
			case "<i>":
				limit = 80;
				limitThere = true;
				return States.ITALIC;
			case "<q>": 
				browser.print(" \"");
				limit = 80;
				limitThere = true;
				return States.QUOTE;
			case "</q>":
				browser.print("\"");
				limit = 80;
				limitThere = true;
				return States.QUOTE;
			case "<p>":
				currLine = "";
				browser.printBreak();
				break;
			case "<h1>":
				limit = 40;
				currLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H1;
			case "<h2>":
				limit = 50;
				currLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H2;
			case "<h3>":
				limit = 60;
				limitThere = true;
				return States.H3;
			case "<h4>":
				limit = 80;
				limitThere = true;
				return States.H4;
			case "<h5>":
				limit = 100;
				currLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H5;
			case "<h6>":
				limit = 1000;
				currLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H6;
			case "<hr>":
				browser.printHorizontalRule();
				break;
			case "<br>":
				currLine = "";
				browser.printBreak();
				break;
			case "<pre>":
				limitThere = false;
				browser.printBreak();
				return States.PREFORMATTED;
			case "</p>": 
				browser.printBreak();
				currLine = "";
				break;
			case "</h1>": case "</h2>": case "</h3>": case "</h4>": 
			case "</h5>": case "</h6>": case "</pre>":
				browser.printBreak();
				currLine = "";
				limit = 80;
				break;
		}
		return States.NONE;
	}
	/**
	 *	Reads the files and puts all the tokens into 1 array
	 * 
	 * 	@param String [] args		the token
	 */
	public void getTokens(String[] args){
		Scanner input = null;
		String fileName = "";
		
		// if the command line contains the file name, then store it
		if (args.length > 0)
			fileName = args[0];
		// otherwise print out usage message
		else {
			System.out.println("Usage: java HTMLTester <htmlFileName>");
			System.exit(0);
		}
		
		// Open the HTML file
		input = FileUtils.openToRead(fileName);
	
		String [] arr = new String[TOKENS_SIZE];
		// Read each line of the HTML file, tokenize, then print tokens
		while (input.hasNext()) {
			String line = input.nextLine();
			String [] curLine = util.tokenizeHTMLString(line);
			for(int i = 0; i < curLine.length; i++){
				arr[counter] = curLine[i];
				counter++;
			}
		}
		tokens = new String[counter];
		for(int i = 0; i<counter; i++){
			tokens[i] = arr[i];
			
		}
		
		input.close();
	}
	/**
	 *	Returns true or false if the token is a punctuation or not
	 * 
	 * 	@return boolean 		if token is punctuation
	 * 	@param String line		the token
	 */
	private boolean isPunctuation(String str) {
		String[] arr = {".", ",", ";", ":", "(", ")", "?", "!", "=", "&", "~", "+", "-"};
		for (int i = 0; i < arr.length; i++) 
			if (str.equals(arr[i])) return true;
		return false;
	}
	
	
	
}
