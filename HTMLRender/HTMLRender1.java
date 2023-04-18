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
 *	@author Mitul Marimuthu
 *	@version 1.0
 *  @since November 8, 2021
 */
public class HTMLRender1 {
	
	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private int index;
	private int limit;
	private boolean limitThere;
	private String currentLine;
	private enum States {NONE, PRINT, BOLD, ITALIC, H1, H2, H3, H4, H5, H6, QUOTE,
			PREFORMATTED};
	private States state;
	private final int TOKENS_SIZE = 100000;	// size of array

	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
	
		
	public HTMLRender1() {
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		index = 0;
		limit = 80;
		limitThere = true;
		state = States.NONE;
		currentLine = "";
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
	}
	
	
	public static void main(String[] args) {
		HTMLRender1 hf = new HTMLRender1();
		String fileName = "";
		if (args.length > 0)
			fileName = args[0];
		// otherwise print out usage message
		else {
			System.out.println("Usage: java HTMLTester <htmlFileName>");
			System.exit(0);
		}
		hf.getTokens(fileName);
		hf.runProgram();
	}
	
	private void runProgram() {
		for (int i = 0; i < index; i++) {
			String line = tokens[i];
			System.out.println(line);
			if (line.indexOf("<") != -1) {
				state = changeState(line);
			}
			else {
				boolean punctuation = isPunctuation(line);
				if (limitThere && currentLine.length() + line.length() > limit) {
					browser.println();
					currentLine = "";
				}
				if (punctuation)
					printTheLine(line);
				else if (i > 0 && tokens[i-1].equals("<q>"))
					printTheLine(line);
				else {
					line = " " + line;
					printTheLine(line);
				}
				currentLine += line;
			}
		}
	}
	
	private boolean isPunctuation(String str) {
		String[] punc = {".", ",", ";", ":", "(", ")", "?", "!", "=", "&", "~", "+", "-"};
		for (int i = 0; i < punc.length; i++) 
			if (str.equals(punc[i])) return true;
		return false;
	}
	
	private void printTheLine(String line) {
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
	
	private States changeState(String line) {
		switch (line) {
			case "<b>": case "<B>":
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
			case "<p>": case "<P>":
				currentLine = "";
				browser.printBreak();
				break;
			case "<h1>": case "<H1>":
				limit = 40;
				currentLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H1;
			case "<h2>": case "<H2>":
				limit = 50;
				currentLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H2;
			case "<h3>": case "<H3>":
				limit = 60;
				limitThere = true;
				return States.H3;
			case "<h4>": case "<H4>":
				limit = 80;
				limitThere = true;
				return States.H4;
			case "<h5>": case "<H5>":
				limit = 100;
				currentLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H5;
			case "<h6>": case "<H6>":
				limit = 1000;
				currentLine = "";
				browser.printBreak();
				limitThere = true;
				return States.H6;
			case "<hr>": case "<HR>":
				browser.printHorizontalRule();
				break;
			case "<br>":
				currentLine = "";
				browser.printBreak();
				break;
			case "<pre>":
				limitThere = false;
				browser.printBreak();
				return States.PREFORMATTED;
			case "</p>": case "</P>": 
				browser.printBreak();
				currentLine = "";
				break;
			case "</h1>": case "</H1>": case "</h2>": case "</h3>": case "</h4>": 
			case "</h5>": case "</h6>": case "</H2>": case "</H3>": case "</H4>":
			case "</H5>": case "</H6>": case "</pre>":
				browser.printBreak();
				currentLine = "";
				limit = 80;
				break;
		}
		return States.NONE;
	}
	
	private void getTokens(String fileName) {
		Scanner input = null;
		HTMLUtilities util = new HTMLUtilities();
		// Open the HTML file
		input = FileUtils.openToRead(fileName);
		while(input.hasNext()) {
			String line = input.nextLine();
			String[] lineTokens = util.tokenizeHTMLString(line);
			addTokens(lineTokens);
		}
	}
	
	private void addTokens(String[] newTokens) {
		for (int i = 0; i < newTokens.length; i++) {
			tokens[index] = newTokens[i];
			index++;
		}
	}
	
	public void run() {
		// Sample renderings from HtmlPrinter class
		
		// Print plain text without line feed at end
		browser.print("First line");
		
		// Print line feed
		browser.println();
		
		// Print bold words and plain space without line feed at end
		browser.printBold("bold words");
		browser.print(" ");
		
		// Print italic words without line feed at end
		browser.printItalic("italic words");
		
		// Print horizontal rule across window (includes line feed before and after)
		browser.printHorizontalRule();
		
		// Print words, then line feed (printBreak)
		browser.print("A couple of words");
		browser.printBreak();
		browser.printBreak();
		
		// Print a double quote
		browser.print("\"");
		
		// Print Headings 1 through 6 (Largest to smallest)
		browser.printHeading1("Heading1");
		browser.printHeading2("Heading2");
		browser.printHeading3("Heading3");
		browser.printHeading4("Heading4");
		browser.printHeading5("Heading5");
		browser.printHeading6("Heading6");
		
		// Print pre-formatted text (optional)
		browser.printPreformattedText("Preformat Monospace\tfont");
		browser.printBreak();
		browser.print("The end");
		
	}
}
