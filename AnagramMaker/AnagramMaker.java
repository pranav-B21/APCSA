import java.util.ArrayList;
/**
 *	AnagramMaker - <description goes here>
 *
 *	Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 *	@author	Pranav Belligundu
 *	@since	Jan 18th, 2022
 */
public class AnagramMaker {
								
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
	public AnagramMaker() {
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	
	public static void main(String[] args) {
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	
	/**	The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run() {
		printIntroduction();
		getWords();
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
	public void getWords(){
		System.out.println();
		String word = "";
		while (!word.equals("q")){
			word = Prompt.getString("Word(s), name or phrase (q to quit)");
			if (!word.equals("q")) {
				numWords = Prompt.getInt("Number of words in anagram");
				maxPhrases = Prompt.getInt("Maximum number of anagrams to print");
				System.out.println();
				ArrayList<String> anagram = new ArrayList<>();
				word = nonAlpha(word);
				runAnagrams(word, anagram);
				System.out.println("\nStopped at " + numPhrases	+ " anagrams\n");
				numPhrases = 0;
			}
		}
	}
	/**
	 * This method is the recursive statement that finds the total amount of
	 * anagrams
	 * @param		phrase		the phrase given by ui
	 * @param		anagram		ArrayList holding words made out of used 
	 * 
	 * base case: if phrase contains no characters and anagram holds numWords amount
	 * 	of elements, then multiple anagrams are made and stored in
	 * 	anagram and later printed on the screen
	 * base case: an extra base case to increase speed. Determines if the 
	 * continuation of the recursion is needed if the max number of phrases
	 * are already foubnd for the given phrase.
	 */
	private void runAnagrams(String phrase, ArrayList<String> anagram) {	
		if (phrase.length() == 0 && anagram.size() == numWords) {
			for (String s : anagram)
				System.out.print(s + " ");
			System.out.println();
			numPhrases++;
			return;
		}
		
		if (anagram.size() == numWords) //base case
			return;
		else {
			//calls a method that finds all words with the given phrase
			ArrayList<String> allWords = wu.allWords(phrase);
			for(int i = 0; i < allWords.size(); i++){
				// adds an anagram that is from the word bank
				anagram.add(allWords.get(i)); 
				
				//removes letters from the new phrase that was already in the inputed phrase
				String newPhrase = removeLetters(allWords.get(i), phrase);
				
				//calls method recursivly
				runAnagrams(newPhrase, anagram);
				anagram.remove(anagram.size()-1);
				
				//puts a restriction on how many anagrams can be made
				if(numPhrases >= maxPhrases) return;
			}
		}
	}
	/**
	 * Method that removes letters from the new anagram
	 * 
	 * @param		remove 		the new phrase
	 * @param 		word		the letters that are targeted 	
	 * @return 		word 		with the words shared removed
	 */
	public String removeLetters(String remove, String word){
		for (int i = 0; i < remove.length(); i++) {
			int index = word.indexOf(remove.charAt(i));
			word = word.substring(0, index) + word.substring(index+1);
		}
		return word;
	}
	/**
	 * removes non alphabetic characters
	 * 
	 * @param		word		input
	 * @return		str 		word with characters that arent letters
	 */
	public String nonAlpha(String phrase){
		String alpha = "";
		for (int i = 0; i < phrase.length(); i++){
			char c = Character.toLowerCase(phrase.charAt(i));
			if ( c >= 'a' && c <= 'z')
				alpha += c;
		}
		return alpha;
	}
}
