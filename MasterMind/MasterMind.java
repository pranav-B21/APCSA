/**
 *	Plays the game of MasterMind.
 *		
 *	The users goal in this game is to find the code that the computer 
 * 	generates randomly. If the user finds the code in less than 10 turns
 * 	the user wins the game. At the end of each turn, the program tells
 * 	how many exact and partial matches the user got. Exact matches is when
 * 	the location and the letter is exact, whereas partial matches is the right
 * 	letter at the wrong spot.
 * 
 *	@author Pranav Belligundu
 *	@since September 30, 2021
 */

public class MasterMind {

	private boolean reveal;			// true = reveal the master combination
	private PegArray[] guesses;		// the array of guessed peg arrays
	private PegArray master;		// the master (key) peg array
	
	// Constants
	private final int PEGS_IN_CODE = 4;		// Number of pegs in code
	private final int MAX_GUESSES = 10;		// Max number of guesses
	private final int PEG_LETTERS = 6;		// Number of different letters on pegs
											// 6 = A through F

	public MasterMind(){
		reveal = false;
		guesses = new PegArray[MAX_GUESSES];
		for(int i = 0; i < guesses.length; i++) guesses[i] = new PegArray(4);
		master = new PegArray(4);
	}
	/**
	 *	Print the introduction screen
	 */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| ___  ___             _              ___  ___ _             _                       |");
		System.out.println("| |  \\/  |            | |             |  \\/  |(_)           | |                      |");
		System.out.println("| | .  . |  __ _  ___ | |_  ___  _ __ | .  . | _  _ __    __| |                      |");
		System.out.println("| | |\\/| | / _` |/ __|| __|/ _ \\| '__|| |\\/| || || '_ \\  / _` |                      |");
		System.out.println("| | |  | || (_| |\\__ \\| |_|  __/| |   | |  | || || | | || (_| |                      |");
		System.out.println("| \\_|  |_/ \\__,_||___/ \\__|\\___||_|   \\_|  |_/|_||_| |_| \\__,_|                      |");
		System.out.println("|                                                                                    |");
		System.out.println("| WELCOME TO MONTA VISTA MASTERMIND!                                                 |");
		System.out.println("|                                                                                    |");
		System.out.println("| The game of MasterMind is played on a four-peg gameboard, and six peg letters can  |");
		System.out.println("| be used.  First, the computer will choose a random combination of four pegs, using |");
		System.out.println("| some of the six letters (A, B, C, D, E, and F).  Repeats are allowed, so there are |");
		System.out.println("| 6 * 6 * 6 * 6 = 1296 possible combinations.  This \"master code\" is then hidden     |");
		System.out.println("| from the player, and the player starts making guesses at the master code.  The     |");
		System.out.println("| player has 10 turns to guess the code.  Each time the player makes a guess for     |");
		System.out.println("| the 4-peg code, the number of exact matches and partial matches are then reported  |");
		System.out.println("| back to the user. If the player finds the exact code, the game ends with a win.    |");
		System.out.println("| If the player does not find the master code after 10 guesses, the game ends with   |");
		System.out.println("| a loss.                                                                            |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME MASTERMIND!                                                        |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n");
	}
	
	/**
	 *	Print the peg board to the screen
	 */
	public void printBoard() {
		// Print header
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
		System.out.print("| MASTER |");
		for (int a = 0; a < PEGS_IN_CODE; a++)
			if (reveal)
				System.out.printf("   %c   |", master.getPeg(a).getLetter());
			else
				System.out.print("  ***  |");
		System.out.println(" Exact Partial |");
		System.out.print("|        +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("               |");
		// Print Guesses
		System.out.print("| GUESS  +");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------|");
		for (int g = 0; g < MAX_GUESSES - 1; g++) {
			printGuess(g);
			System.out.println("|        +-------+-------+-------+-------+---------------|");
		}
		printGuess(MAX_GUESSES - 1);
		// print bottom
		System.out.print("+--------+");
		for (int a = 0; a < PEGS_IN_CODE; a++) System.out.print("-------+");
		System.out.println("---------------+");
	}
	
	/**
	 *	Print one guess line to screen
	 *	@param t	the guess turn
	 */
	public void printGuess(int t) {
		System.out.printf("|   %2d   |", (t + 1));
		// If peg letter in the A to F range
		char c = guesses[t].getPeg(0).getLetter();
		if (c >= 'A' && c <= 'F')
			for (int p = 0; p < PEGS_IN_CODE; p++)
				System.out.print("   " + guesses[t].getPeg(p).getLetter() + "   |");
		// If peg letters are not A to F range
		else
			for (int p = 0; p < PEGS_IN_CODE; p++)
				System.out.print("       |");
		System.out.printf("   %d      %d    |\n",
							guesses[t].getExact(), guesses[t].getPartial());
	}
	/**
	 * This method asks for user input and calls the necessary methods
	 * to run the game. 
	 * 
	 * It also determines who the winner is.
	 * 
	 * Using a do-while loop to restrict up to 10 guesses
	 * 
	 */
	public void runGame(){
		int exact = 0;
		int partial = 0;
		int counter = 0;
		String guess = "";
		
		String prompt = Prompt.getString("Hit the Enter key to start the game");
		printBoard();
		for(int i = 0; i < PEGS_IN_CODE; i++){
			master.getPeg(i).setLetter(getLetter());
		}
	
		do{
			guess = getGuess();
			for(int i = 0; i < 4; i++){
				guesses[counter].getPeg(i).setLetter(guess.charAt(i));
			}
			exact = guesses[counter].getExactMatches(master);
			partial = guesses[counter].getPartialMatches(master);
			
			if(exact == 4 || counter == MAX_GUESSES -1) reveal = true;
			System.out.println(counter);
			printBoard();
			counter ++;
		}while(!reveal && counter < MAX_GUESSES);
		if(counter == 10){
			System.out.println("Oops. You were unable to find the "+
			"solution in 10 guesses.\n");
		}
		else System.out.println("Nice Work! You found the master code in "+
			counter+" guesses!\n");
	}
	/**
	 * This method calls the Prompt class and gets the user input
	 * 
	 * 
	 */
	public String getGuess(){
		String guess = "";
		boolean valid = false;
		do{
			System.out.println();
			guess = Prompt.getString("Enter the code using (A,B,C,D,E,F)." 
			+ "For example, ABCD or abcd from left-to-right");
			guess = guess.toUpperCase();
			if(guess.length() == 4){
				for(int i = 0; i < 4; i++){
					valid = true;
					if(guess.charAt(i) < 65 || guess.charAt(i) > 70){
						valid = false;
					}
				}
			}
			else valid = false;
				
			if(valid == false) System.out.println("ERROR: Bad input, try again.");
		}while(!valid);
		return guess;
		
	}
	/**
	 * Returns a random character between a-f to create the master code
	 * 
	 * @return char returns a character between a-f
	 */
	public char getLetter(){
		int counter = 0;
		int num = 1 + (int)(7*Math.random());
		if(num == 1) return 'A';
		else if(num == 2) return 'B';
		else if(num == 3) return 'C';
		else if(num == 4) return 'D';
		else if(num == 5) return 'E';
		else return 'F';
	}
	/**
	 * Main method that calles the instructions method and the runGame method
	 */
	public static void main(String [] args){
		MasterMind master = new MasterMind();
		master.printIntroduction();
		master.runGame();
		
	}

}
