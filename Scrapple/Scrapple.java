/**
 *  A simple version of the Scrabble game where the user plays against the computer.
 *
 *  @author	Pranav Belligundu
 *  @since	Oct 14, 2021
 */

public class Scrapple {
	
	//Field Vars
	public int [] scores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10,
					 		1, 1, 1, 1, 4, 4, 8, 4, 10};
	//A String which contains the tiles
	private String tilesRemaining = 
					"AAAAAAAAAABBCCDDDDEEEEEEEEEEEEEFFGGGHHIIIIIIIII" +
					"JKLLLLMMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ";				 		
					
	private final int NUMTILES = 8;			// the number of tiles per player
	private final int MIN_WORD_LENGTH = 4;	// minimum of 4 characters
	
	//Fields for playerScore and player input
	private int playerScore;
	private int computerScore;
	
	//A String which holds the users or computers hand
	private String playerHand, computerHand; 
	private String word;
	
	private WordUtils utils; //object to access the WordUtils Class
	
	/* Constructor */
	public Scrapple() {
		playerScore = 0;
		computerScore = 0;
		word = "";
		playerHand = "";
		computerHand = "";
		utils = new WordUtils();
	}
	/**
	 * This method runs the majority of the game
	 * 
	 * 	It creates the hands, gets the user input and prints out all the info.
	 * 	The game alternates between the computer and the user in the game. 
	 * 	The computer chooses the best words with the most points accounting.
	 * 
	 * 	Although, the user has to create a word from the word bank, and if it 
	 * 	isnt a word or not in the library. The game quits. 
	 * 
	 * 	In the end if no words can be created by either of the hands, the game
	 * 	ends and total scores are printed. 
	 */
	public void run() {
		System.out.println("\nHere are the tiles remaining in the pool of letters");
		
		//local vars// Example F to C in Java
// This file must be named FahrenheitToCelsius.java 
import java.util.Scanner;
public class FahrenheitToCelsius {
    public static final  double  LOW_TEMP_F_WARNING=0.;
    public static final  double  HIGH_TEMP_F_WARNING=100.;
    public static final  int     MAX_LOOP=5;
    public static void main(String[] args) {
        Scanner scanFaren = new Scanner(System.in);
        double Fahrenheit = 0;
        double Celsius = 0;
        for(int i=0; i<MAX_LOOP; i++){
        System.out.print("\nEnter a temperature in Fahrenheit: ");
            if(scanFaren.hasNextDouble())
                {
                    Fahrenheit=scanFaren.nextDouble();
                    Celsius = ( Fahrenheit- 32.)*5./9.;
                }else{
                System.out.println("Data entry error - try again\n");
                System.exit(-1); }
            System.out.println("The temperature in Celsius is: "+Celsius);
       
            // Check for high temperature and issue a warning if necessary
            if(Fahrenheit > HIGH_TEMP_F_WARNING){
                System.out.print("Remember to hydrate\n");}
            // Check for low temperature and issue a warning if necessary
            if(Fahrenheit < LOW_TEMP_F_WARNING ){
                System.out.print("Remember to pack Long underwear\n");}
        }
        System.exit(-1);
    }
}
		boolean valid = true;
		boolean noMoreWords = false;
		boolean playerTurn = true;
		int playerOrComputer = -1;
		
		do{
			
			playerHand = createHand(playerHand); //creates the hand
			computerHand = createHand(computerHand); //creates the hand
			String [] allWordsPlayer = utils.findAllWords(playerHand.toLowerCase());
			String [] allWordsComp = utils.findAllWords(computerHand.toLowerCase());
			printInfo(playerScore, computerScore);	
			printHands();	
			
			if(allWordsPlayer.length == 0){
				valid = false;
				noMoreWords = true;
				playerOrComputer = 1;
			}
			else if(allWordsComp.length == 0){
				valid = false;
				noMoreWords = true;
				playerOrComputer = 0;
			}
		
			if(playerTurn && !noMoreWords){ //user turn
				word = Prompt.getString("Please enter a word created from your current set of tiles");
				boolean isWord = isWord(allWordsPlayer, word);
				if(!isWord || word.length() < MIN_WORD_LENGTH || 
				!utils.isWordMatch(word,playerHand.toLowerCase())){
					valid = false;
				}
				else{
					valid = true;
					//adds the score
					playerScore += utils.getScore(word,scores); 
					
					 //removes the letters used from the hand
					playerHand = removeFromHand(playerHand.toLowerCase(), word);
				}
			}
			else if(!playerTurn && !noMoreWords){ //computer turn
				String word2 = Prompt.getString("It's the computer's turn. Hit ENTER on the keyboard to continue");
				if(allWordsComp.length == 0) valid = false;
				//takes all the words and creates an array with all the scores
				int [] allScores = new int[allWordsComp.length];
				for(int i = 0; i < allScores.length; i++){
					if(allWordsComp[i].length() > 3){
						allScores[i] = utils.getScore(allWordsComp[i],scores);
					}
					else allScores[i] = 0;
				}
				
				//find the maximum amount of points from the whole array
				int maximum = allScores[0];   // start with the first value
				for (int i=1; i<allScores.length; i++) {
					if (allScores[i] > maximum) {
						maximum = allScores[i];   // new maximum
						word = allWordsComp[i];
					}
				}
				System.out.println();
				System.out.println("The computer chose: "+word);
				
				//adds the score
				computerScore += maximum;
				
				//removes the letters used from the hand
				computerHand = removeFromHand(computerHand.toLowerCase(),word);
			}
			if(isDoublePoints(word) && valid) System.out.println("BONUS WORD SCORE!!!");
			playerTurn = !playerTurn;
			
		}while(valid);
		
		System.out.println();
		if(noMoreWords){
			if(playerOrComputer == 1) System.out.println("No more words can be created by the player!");
			else if(playerOrComputer == 0) System.out.println("No more words can be created by the comuter!");
			printHands();		
			System.out.printf("%-18s%d", "Player Score:", playerScore);
			System.out.println();
			System.out.printf("%-18s%d", "Computer Score:", computerScore);
			System.out.println("\n");
			System.out.println("Thank you for playing Scrapple");
			System.out.println("\n\n");
		}
		
	}
	/**	
	 * 	returns true or false if the word exists in the library
	 * 
	 * 	@param word		The word that the user or computer chose
	 * 	@param [] arr	An array containing all the words that can be created
	 * 					with the letters in the hand
	 * 	@return			a boolean 
	 */
	public boolean isWord(String [] arr, String word){
		for(int i = 0; i < arr.length; i++){
			if(arr[i].equals(word)){
				return true;
			}
		}
		return false;
	}
	/**	
	 * 	returns true or false based off if the word contains consecutive letters
	 * 
	 * 	@param word		The word that the user or computer chose
	 * 	@return			a boolean 
	 */
	public boolean isDoublePoints(String word){
		for(int i = 0; i < word.length()-1; i++){
			if(word.charAt(i) == word.charAt(i+1)){
				return true;
			}
		}
		return false;
	}
	/**	
	 * 	This method removes the letters from the word so the same words
	 * 	wont be reused
	 * 
	 *  @param hand		A string which contains either the users or computers hand
	 * 	@param word		The word that the user or computer chose
	 * 	@return			A hand with the new hand  
	 */
	public String removeFromHand(String hand, String word){
		int index = 0;
		
		for(int i = 0; i < word.length(); i++){
			index = hand.indexOf(""+word.charAt(i));
			if(index != -1){
				if(index < word.length()){
				hand = hand.substring(0,index)+hand.substring(index+1);
				}
				else{
					hand = hand.substring(0,index);
				}
			}
			
		}
		return hand.toUpperCase();
	}
	/**	
	 * 	This method creates the users or comptuers hand by randomly
	 * 	picking words from tilesRemaining
	 * 
	 *  @param hand		A string which contains either the users or computers hand
	 * 	@return			A hand with the new hand  
	 */
	public String createHand(String hand){
		int index = 0;
		if(hand.length() >= 8) return hand;
		
		while(tilesRemaining.length() > 0 && hand.length() < NUMTILES){
			index = getRandNum();
			hand += tilesRemaining.charAt(index)+"";
			
			if(index < tilesRemaining.length()){
				tilesRemaining = tilesRemaining.substring(0,index)
				+ tilesRemaining.substring(index+1);
			}
			else{
				tilesRemaining = tilesRemaining.substring(0,index);
			}

		}
		
		return hand;
	}
	/**
	 * Returns a random number from 0 - the lenght of tilesRemaining
	 * This is used as an index to randomly pick letters from the String
	 */
	public int getRandNum(){
		return (int)(Math.random()*tilesRemaining.length());
	}
	
	/**
	 *  Prints the tiles that are left in the hand
	 */
	public void printHands(){
		System.out.println();
		System.out.printf("%-40s","THE TILES IN YOUR HAND ARE:");
		for(int i = 0; i < playerHand.length(); i++){
			System.out.print(playerHand.charAt(i)+" ");
		}
		System.out.println();
		System.out.printf("%-40s","THE TILES IN THE COMPUTER HAND ARE:");
		for(int i = 0; i < computerHand.length(); i++){
			System.out.print(computerHand.charAt(i)+" ");
		}
		System.out.println("\n");
		
	}
	/**
	 *  Print the information to show the tiles and amount of points
	 */
	public void printInfo(int pScore, int cScore){
		System.out.println();
		for (int i = 0; i < tilesRemaining.length(); i ++) {
			if (i % 20 == 0) System.out.println();
			System.out.print(tilesRemaining.charAt(i)+ " ");
			
		}
		System.out.println("\n");
		
		System.out.printf("%-18s%d", "Player Score:", pScore);
		System.out.println();
		System.out.printf("%-18s%d", "Computer Score:", cScore);
		System.out.println();
	}
	/**
	 *  Print the introduction screen for Scrapple.
	 */
	public void printIntroduction() {
		System.out.print(" _______     _______     ______     ______    ");
		System.out.println(" ______    ______   __          _______");
		System.out.print("/\\   ___\\   /\\  ____\\   /\\  == \\   /\\  __ \\   ");
		System.out.println("/\\  == \\  /\\  == \\ /\\ \\        /\\  ____\\");
		System.out.print("\\ \\___   \\  \\ \\ \\____   \\ \\  __<   \\ \\  __ \\  ");
		System.out.println("\\ \\  _-/  \\ \\  _-/ \\ \\ \\_____  \\ \\  __\\");
		System.out.print(" \\/\\______\\  \\ \\______\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\ ");
		System.out.println(" \\ \\_\\     \\ \\_\\    \\ \\______\\  \\ \\______\\");
		System.out.print("  \\/______/   \\/______/   \\/_/ /_/   \\/_/\\/_/ ");
		System.out.println("  \\/_/      \\/_/     \\/______/   \\/______/ TM");
		System.out.println();
		System.out.print("This game is a modified version of Scrabble. ");
		System.out.println("The game starts with a pool of letter tiles, with");
		System.out.println("the following group of 100 tiles:\n");
		
		for (int i = 0; i < tilesRemaining.length(); i ++) {
			System.out.printf("%c ", tilesRemaining.charAt(i));
			if (i == 49) System.out.println();
		}
		System.out.println("\n");
		System.out.printf("The game starts with %d tiles being chosen at ran", NUMTILES);
		System.out.println("dom to fill the player's hand. The player must");
		System.out.printf("then create a valid word, with a length from 4 to %d ", NUMTILES);
		System.out.println("letters, from the tiles in his/her hand. The");
		System.out.print("\"word\" entered by the player is then checked. It is first ");
		System.out.println("checked for length, then checked to make ");
		System.out.print("sure it is made up of letters from the letters in the ");
		System.out.println("current hand, and then it is checked against");
		System.out.print("the word text file. If any of these tests fail, the game");
		System.out.println(" terminates. If the word is valid, points");
		System.out.print("are added to the player's score according to the following table ");
		System.out.println("(These scores are taken from the");
		System.out.println("game of Scrabble):");
		
		// Print line of letter scores
		char c = 'A';
		for (int i = 0; i < 26; i++) {
			System.out.printf("%3c", c);
			c = (char)(c + 1);
		}
		System.out.println();
		for (int i = 0; i < scores.length; i++) System.out.printf("%3d", scores[i]);
		System.out.println("\n");
		
		System.out.print("The score is doubled (BONUS) if the word has consecutive double ");
		System.out.println("letters (e.g. ball).\n");
		
		System.out.print("Once the player's score has been updated, more tiles are ");
		System.out.println("chosen at random from the remaining pool");
		System.out.printf("of letters, to fill the player's hand to %d letters. ", NUMTILES);
		System.out.println("The player again creates a word, and the");
		System.out.print("process continues. The game ends when the player enters an ");
		System.out.println("invalid word, or the letters in the");
		System.out.println("pool and player's hand run out. Ready? Let's play!\n");
		
		Prompt.getString("HIT ENTER on the keyboard to continue");

	}
	/* main method */
	public static void main(String [] args) {
		Scrapple sjr = new Scrapple();
		sjr.printIntroduction();
		sjr.run();
	}
	
}
