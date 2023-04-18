/**
 *	Yahtzee is a game where 2 users try to get the most amount of points
 * 	by rolling 5 die. After each round a player can decide which scoring
 * 	category they want to use, and once a category is used it cant be used
 * 	again
 * 
 * 	The player with the highest roll will go first, and if its a tie
 * 	players will roll again
 *
 *	@author	Pranav Belligundu
 *	@since	Sept 23, 2021
 */
 
public class Yahtzee {
	
	//fields
	private YahtzeePlayer player1, player2;
	private DiceGroup dg;
	private YahtzeeScoreCard scoreCardP1;
	private YahtzeeScoreCard scoreCardP2;
	
	//main method
	public static void main(String[] args){
		Yahtzee pranav = new Yahtzee();
		pranav.printHeader();
		pranav.playerChoose();
	}
	
	//constructor to initialize fields
	public Yahtzee(){
		player1 = new YahtzeePlayer();
		player2 = new YahtzeePlayer();
		dg = new DiceGroup();
		scoreCardP1 = new YahtzeeScoreCard();
		scoreCardP2 = new YahtzeeScoreCard();
	}
	
	/**
	 * 	This method selects which player goes first.
	 * 
	 * 	It is done by rolling the die and the player who got the most points
	 *  after one roll goes first. If its a tie they roll again
	 * 
	 */
	public void playerChoose(){
		player1.setName(Prompt.getString("Player 1, please enter your first name "));
		System.out.println();
		player2.setName(Prompt.getString("Player 2, please enter your first name "));
		
		//find out who goes first
		System.out.println();
		int playerOneSum, playerTwoSum = 0;
		do{
			Prompt.getString("Let's see who will go first. " +
				player1.getName()+", hit enter to roll the dice");//player 1
			dg.rollDice();
			dg.printDice();
			playerOneSum = dg.getTotal();
			System.out.println();
			
			Prompt.getString(player2.getName()+", it's your turn. "+
				"Please hit enter to roll the dice");//player 2
			dg.rollDice();
			dg.printDice();
			playerTwoSum = dg.getTotal();
			System.out.println();
			
			if(playerOneSum == playerTwoSum){
				System.out.println("Whoops, we have a tie (both rolled 17). Looks like we'll have to try that again . . . ");
			}
		}while(playerOneSum == playerTwoSum);
		
		System.out.println(player1.getName()+", you rolled a sum of "
			+playerOneSum+", and "+player2.getName()
			+", you rolled a sum of "+playerTwoSum);
		if(playerOneSum > playerTwoSum){
			System.out.println(player1.getName()+", since your sum was higher, you'll roll first");
			runGame("player1");
		}
		else{
			System.out.println(player2.getName()+", since your sum was higher, you'll roll first");
			runGame("player2");
		}
	}
	
	/**
	 *	This method runs the game in a do-while loop and only allows 13 
	 * 	rounds. 
	 * 
	 * 	After rounds have been completed the winner is declared
	 */
	public void runGame(String first){
		printCard();
		int rounds = 1;
		do{
			System.out.println("Round "+rounds+" of 13 rounds");
			if(first.equals("player1")){
				playerOneRoll(first);	
				playerTwoRoll(first);
			}
			if(first.equals("player2")){
				playerTwoRoll(first);
				playerOneRoll(first);	
			}
			rounds++;
		}while(rounds < 14);
		int scoreP1[] = scoreCardP1.getScore();
		int p1Score = 0;
		
		int scoreP2[] = scoreCardP2.getScore();
		int p2Score = 0;
		for(int i = 1; i<scoreP1.length;i++){
			if(scoreP1[i] != -1) p1Score += scoreP1[i];
		}
		for(int i = 1; i<scoreP2.length;i++){
			if(scoreP2[i] != -1) p2Score += scoreP2[i];
		}
		if(p1Score > p2Score) System.out.println("\n"+player1.getName()+", congratulations you won! And you scored -> "+p1Score+" points.");
		else System.out.println("\n"+player2.getName()+", congratulations you won! And you scored -> "+p2Score+" points.");
		
	}
	/**
	 * 	Player 1 
	 * 
	 *	Asks the user to roll the die and asks the user which category
	 * 	they want to use. If a category has been chosen it asks the 
	 * 	user again
	 */
	public void playerOneRoll(String first){
		int counter = 0;
		String rawHold = "";
		int choose;
		
		System.out.print("\n\n");
		Prompt.getString(player1.getName()+", it's your turn to play. "+
		"Please hit enter to roll the dice");//player 1
				
		dg.rollDice();
		dg.printDice();
		do{
			rawHold = Prompt.getString("Which di(c)e would you like to keep?  Enter the values you'd like to 'hold' without\n"
			+"spaces.  For examples, if you'd like to 'hold' die 1, 2, and 5, enter 125\n"
			+"(enter -1 if you'd like to end the turn)");
			counter++;
			if(rawHold.equals("-1")){
				dg.printDice();
			}
			else{
				dg.rollDice(rawHold);
				dg.printDice();
			}
					
		}while(!(rawHold.equals("-1") || counter >= 2));
		counter = 0;
		
		printCard();
			
		System.out.println("\t\t  1    2    3    4    5    6    7    8    9   10   11   12   13\n");
		System.out.print(player1.getName()+", now you need to make a choice. ");
		do{
			choose = Prompt.getInt("Pick a valid integer from the list above");
		}while(scoreCardP1.changeScore(choose,dg) == false);
		
		scoreCardP1.choice(choose, dg);
				
		printCard();
		
		
	}
	/**
	 * 	Player 2
	 * 
	 *	Asks the user to roll the die and asks the user which category
	 * 	they want to use. If a category has been chosen it asks the 
	 * 	user again
	 */
	public void playerTwoRoll(String first){
		int counter = 0;
		String rawHold = "";
		int choose;
		
		System.out.print("\n\n");
		Prompt.getString(player2.getName()+", it's your turn to play. "+
		"Please hit enter to roll the dice");//player 2
				
		dg.rollDice();
		dg.printDice();
		do{
			rawHold = Prompt.getString("Which di(c)e would you like to keep?  Enter the values you'd like to 'hold' without\n"
			+"spaces.  For examples, if you'd like to 'hold' die 1, 2, and 5, enter 125\n"
			+"(enter -1 if you'd like to end the turn)");
			counter++;
			if(rawHold.equals("-1")){
				dg.printDice();
			}
			else{
				dg.rollDice(rawHold);
				dg.printDice();
			}
					
		}while(!(rawHold.equals("-1") || counter >= 2));
		counter = 0;
		
		printCard();
			
		System.out.println("\t\t  1    2    3    4    5    6    7    8    9   10   11   12   13\n");
		System.out.print(player2.getName()+", now you need to make a choice. ");
		do{
			choose = Prompt.getInt("Pick a valid integer from the list above");
		}while(scoreCardP2.changeScore(choose,dg) == false);
			
		scoreCardP2.choice(choose, dg);
				
		printCard();
	}
	/**
	 * 	Prints the header with the rules
	 */
	public void printHeader() {
		System.out.println("\n");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
		System.out.println("|                                                                                    |");
		System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
		System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
		System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
		System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
		System.out.println("| trying to get a good combination.                                                  |");
		System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
		System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
		System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
		System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
		System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
		System.out.println("| on the score card, it can't be chosen again.                                       |");
		System.out.println("|                                                                                    |");
		System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
		System.out.println("+------------------------------------------------------------------------------------+");
		System.out.println("\n\n");
	}
	/**
	 * 	Prints the score card
	 */
	public void printCard(){
		scoreCardP1.printCardHeader();
		scoreCardP1.printPlayerScore(player1);
		scoreCardP2.printPlayerScore(player2);
	}
}
