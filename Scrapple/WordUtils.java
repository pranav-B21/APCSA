import java.util.Scanner;
/**
 *	Provides utilities for word games:
 *	1. finds all words in the dictionary that match a list of letters
 *	2. prints an array of words to the screen in tabular format
 *	3. finds the word from an array of words with the highest score
 *	4. calculates the score of a word according to a table
 *
 *	Uses the FileUtils and Prompt classes.
 *	
 *	@author Pranav Belligundu
 *	@since	Oct 11, 2021
 */
 
public class WordUtils
{
	private String[] words;		// the dictionary of words
	
	// File containing dictionary of almost 100,000 words.
	private final String WORD_FILE = "wordList.txt";
	
	/* Constructor */
	public WordUtils(){ 
		words = new String[90934];
		loadWords();
	}
	
	/**	Load all of the dictionary from a file into words array. */
	private void loadWords () { 
		Scanner input = FileUtils.openToRead(WORD_FILE);
		for(int i = 0; i<words.length;i++){
			words[i] = input.next();
		}
	}
	
	/**	Find all words that can be formed by a list of letters.
	 *  @param letters	string containing list of letters
	 *  @return			array of strings with all words found.
	 */
	public String [] findAllWords (String letters)
	{	
		String allWords = "";
		int num = 0;
		String[] matchedWords; 
		
		for(int i = 0; i < words.length;i++){
			if(isWordMatch(words[i],letters)){
				if(words[i].length() > 3){
					allWords += words[i] + " ";
					num++;
				}
				
			}
		}
		matchedWords = new String[num];
		for(int i = 0; i <matchedWords.length;i++){
			matchedWords[i] = allWords.substring(0, allWords.indexOf(" "));
			allWords = allWords.substring(allWords.indexOf(" ")+1);
		}
		return matchedWords;
	}
	/**
	 *  Decides if a word matches a group of letters.
	 *
	 *  @param word  The word to test.
	 *  @param letters  A string of letters to compare
	 *  @return  true if the word matches the letters, false otherwise
	 */
	public boolean isWordMatch (String word, String letters) {
		for(int a = 0; a < word.length(); a++){
			char c = word.charAt(a);
			if(letters.indexOf(c) > -1) // if char exists in the word
				letters = letters.substring(0,letters.indexOf(c)) +
					letters.substring(letters.indexOf(c)+1);
			
			else return false;
		}
		return true;
	}
	/**	Print the words found to the screen.
	 *  @param words	array containing the words to be printed
	 */
	public void printWords (String [] wordList) { 
		for(int i = 0; i < wordList.length; i++){
			if(i%5 == 0) System.out.println();
			System.out.printf("%-15s",wordList[i]);
		}
	}
	
	/**	Finds the highest scoring word according to a score table.
	 *
	 *  @param word  		An array of words to check
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return   			The word with the highest score
	 */
	public String bestWord (String [] wordList, int [] scoreTable)
	{
		String bestWord = "";
		int score;
		for(int i = 0; i<wordList.length;i++){
			score = getScore(bestWord, scoreTable);
			if(getScore(wordList[i],scoreTable)>score){
				bestWord = wordList[i];
			}
		}
		return bestWord;
	}
	
	/**	Calculates the score of one word according to a score table. 
	 *
	 *  @param word			The word to score
	 *  @param scoreTable	An array of 26 integer scores in letter order
	 *  @return				The integer score of the word
	 */
	public int getScore (String word, int [] scoreTable){
		int score = 0;
		for(int i = 0; i < word.length(); i++){
			score += scoreTable[word.charAt(i)-'a'];//adds the score together
		}
		//if a word has 2 repetitve letters, then it multiplies the score by 2
		for(int i = 0; i < word.length()-1; i++){
			if(word.charAt(i) == word.charAt(i+1)){
				score += scoreTable[word.charAt(i)-'a'];
				score = score * 2;
			}
		}
		return score;
	}
	
	/***************************************************************/
	/************************** Testing ****************************/
	/***************************************************************/
	public static void main (String [] args)
	{
		WordUtils wu = new WordUtils();
		wu.run();
	}
	
	public void run() {
		loadWords();
		String letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
		String [] word = findAllWords(letters);
		System.out.println();
		printWords(word);
		
		// Score table in alphabetic order according to Scrabble
		int [] scoreTable = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; // total 26 scores for all letters
		String best = bestWord(word,scoreTable);
		System.out.println("\nHighest scoring word: " + best + "\nScore = " 
							+ getScore(best, scoreTable) + "\n");
	}
}
