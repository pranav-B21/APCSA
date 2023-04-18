/**
 *	The game of Pig.
 *	(Description here)
 *
 *	@author	
 *	@since	
 */
public class PigGame {
	private int userScore, totalUserScore;
	private int compScore, totalCompScore;
	private boolean user, run;
	private String userInput, compInput;
	private Dice dice = new Dice();
	private int rolledNum;
	
	/**	Print the introduction to the game */
	public void printIntroduction() {
		System.out.println("\n");
		System.out.println("______ _         _____");
		System.out.println("| ___ (_)       |  __ \\");
		System.out.println("| |_/ /_  __ _  | |  \\/ __ _ _ __ ___   ___");
		System.out.println("|  __/| |/ _` | | | __ / _` | '_ ` _ \\ / _ \\");
		System.out.println("| |   | | (_| | | |_\\ \\ (_| | | | | | |  __/");
		System.out.println("\\_|   |_|\\__, |  \\____/\\__,_|_| |_| |_|\\___|");
		System.out.println("          __/ |");
		System.out.println("         |___/");
		System.out.println("\nThe Pig Game is human vs computer. Each takes a"
							+ " turn rolling a die and the first to score");
		System.out.println("100 points wins. A player can either ROLL or "
							+ "HOLD. A turn works this way:");
		System.out.println("\n\tROLL:\t2 through 6: add points to turn total, "
							+ "player's turn continues");
		System.out.println("\t\t1: player loses turn");
		System.out.println("\tHOLD:\tturn total is added to player's score, "
							+ "turn goes to other player");
		System.out.println("\n");
	}
	public void runGame(){
		user = true;
		run = true;
		do{
			System.out.println("\n**** USER Turn ***\n");
			System.out.println("Your turn score:\t"+userScore);
			System.out.print("Your total score:\t"+totalUserScore);
			System.out.println();
			while(user){
				userInput = Prompt.getString("\n(r)oll or (h)old ");
				if(userInput.equals("r")){
					System.out.println("You ROLL");
					rolledNum = dice.roll();
					dice.printDice();
					if(rolledNum == 1){
						System.out.println("Your total score:\t"+totalUserScore);
						userScore = 0;
						user = false;
					}
					else{
						userScore += rolledNum;
						System.out.println("Your turn score:\t"+userScore);
						System.out.println("Your total score:\t"+totalUserScore);
					}
				}
				else if(userInput.equals("h")){
					totalUserScore += userScore;
					System.out.println("\nYour total score:\t"+totalUserScore);
					if(totalUserScore > 100) userWin();
					userScore = 0;
					user = false;
				}
				else userInput = Prompt.getString("(r)oll or (h)old ");	
			}
			System.out.println("\n**** COMPUTER'S Turn ***\n");
			System.out.println("Computer's turn score:\t"+compScore);
			System.out.print("Computer's total score:\t"+totalCompScore);
			System.out.println();
			while(!user){
				compInput = Prompt.getString("\nPress enter for computer's turn");
				if(compInput.equals("")){
					System.out.println("Computer will ROLL ");
					rolledNum = dice.roll();
					dice.printDice();
					if(rolledNum == 1){
						System.out.println("Computer's total score:\t"+totalCompScore);
						compScore = 0;
						user = true;
					}
					else{
						compScore += rolledNum;
						System.out.println("Computer's turn score:\t"+compScore);
						System.out.println("Computer's total score:\t"+totalCompScore);
					}
				}
				if(compScore > 20){
					totalCompScore += compScore;
					System.out.println("\nComputer's total score:\t"+totalCompScore);
					if(totalCompScore > 100) computerWin();
					compScore = 0;
					user = true;
				}
			}
		}while(totalCompScore < 100 && totalUserScore < 100);
	}
	public void computerWin(){
		System.out.println("\nYOU LOST :(\n\nThanks for playing the Pig Game!!!\n\n\n");
		System.exit(1);
	}
	public void userWin(){
		System.out.println("\nCongratulations!!! YOU WON!!!!\n\nThanks for playing the Pig Game!!!\n\n\n");
		System.exit(1);
	}
	public static void main(String[] args){
		PigGame play = new PigGame();
		play.printIntroduction();
		play.runGame();
	}
	
}
