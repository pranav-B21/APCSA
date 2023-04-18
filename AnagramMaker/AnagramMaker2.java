import java.util.ArrayList;
import java.util.List;
/**
 *	AnagramMaker - Prompts the user for a phrase, the number of words they
 * 	want in their anagrams and the maximum number of phrases (n) they would like
 * 	to be made. Then recursively finds up to n anagrams that can be made
 * 	from the inputted phrase. Keeps going until the user quits.
 *
 *	Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 *	@author	Mitul Marimuthu
 *	@since	January 18, 2022
 */
public class AnagramMaker2 {
								
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	
	private WordUtilities wu;	// the word utilities for building the word
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed
		
	/*	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 */
	public AnagramMaker2() {
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	
	public static void main(String[] args) {
		AnagramMaker2 am = new AnagramMaker2();
		am.run();
	}
	
	/**	The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run() {
		printIntroduction();
		runAnagramMaker();
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction() {
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a word, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	
	/**
	 *	Prompt the user for a phrase of characters, then create anagrams from those
	 *	characters.
	 */
	public void runAnagramMaker() {
		System.out.println();
		String word = "";
		do {
			word = Prompt.getString("Word(s), name or phrase (q to quit)");
			if (!word.equals("q")) {
				numWords = Prompt.getInt("Number of words in anagram");
				maxPhrases = Prompt.getInt("Maximum number of anagrams to print");
				System.out.println();
				ArrayList<String> anagram = new ArrayList<>();
				word = removeNonAlpha(word);
				findAnagrams(word, anagram);
				System.out.println("\nStopped at " + numPhrases	+ " anagrams\n");
				numPhrases = 0;
			}
		} while (!word.equals("q"));
	}
	
	/**
	 * recursive method to find maxPhrase amount of anagrams from the given
	 * phrase
	 * @param		phrase		the phrase to make anagrams out of
	 * @param		anagram		ArrayList holding words made out of used 
	 * 							characters
	 * base case: if phrase has no characters and anagram holds numWords amount
	 * 	of elements - means that a successful anagram is made
	 * base case: if the amount of elements in anagram is equal to the numWords
	 * 	requested ( after first base case ) - all continuation is redundant if
	 * 	the max number of words of a phrase has already been found. 
	 */
	private void findAnagrams(String phrase, ArrayList<String> anagram) {
		/*ArrayList<String> all = wu.allWords(phrase);
		for (String s : all)
			System.out.println(s);*/
		
		if (phrase.length() == 0 && anagram.size() == numWords) {
			for (String s : anagram)
				System.out.print(s + " ");
			System.out.println();
			numPhrases++;
			return;
		}
		if (anagram.size() == numWords)
			return;
		else {
			ArrayList<String> all = wu.allWords(phrase);
			for (String s : all) {
				anagram.add(s);
				String newPhrase = removeLetter(phrase, s);
				findAnagrams(newPhrase, anagram);
				anagram.remove(anagram.size()-1);
				if (numPhrases >= maxPhrases)
					return;
			}
		}
	}
	/**
	 * removes the selected letters form the original phrase
	 * @param		phrase		the original phrase
	 * @param		letters		the letters to remove from phrase
	 * @return					new phrase with targeted letters removed
	 * frequenciies arrays
	 */
	private String removeLetter(String phrase, String letters) {
		for (int i = 0; i < letters.length(); i++) {
			int index = phrase.indexOf(letters.charAt(i));
			phrase = phrase.substring(0, index) + phrase.substring(index+1);
		}
		return phrase;
	}
	
	/**
	 * removes non alphabetic characters from a string
	 * @param		word		string to check
	 * @return					string with only alpha characters
	 */
	private String removeNonAlpha(String word) {
		String alpha = "";
		for (int i = 0; i < word.length(); i++){
			char c = Character.toLowerCase(word.charAt(i));
			if ( c >= 'a' && c <= 'z')
				alpha += c;
		}
		return alpha;
	}
	
}
