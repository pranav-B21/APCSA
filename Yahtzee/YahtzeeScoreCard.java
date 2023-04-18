/**
 *	The scoreCard keeps trakc of the score for each player, and puts
 * 	them into different categories based on what the user chose. 
 * 
 * 	It also calculates the amount of points the user has
 *
 *	@author	Pranav Belligundu
 *	@since	September 23, 2021
 */
public class YahtzeeScoreCard {
	private int score[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private Dice[] die = new Dice[5];
	/**
	 *  Print the scorecard header
	 */
	public void printCardHeader() {
		System.out.println("\n");
		System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n");
		System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse " +
						"Strt Strt Chnc Ytz!\n");
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	
	/**
	 *  Prints the player's score
	 */
	 
	public void printPlayerScore(YahtzeePlayer player) {
		System.out.printf("| %-12s |", player.getName());
		for (int i = 1; i < 14; i++) {
			if (score[i] > -1)
				System.out.printf(" %2d |", score[i]);
			else System.out.printf("    |");
		}
		System.out.println();
		System.out.printf("+----------------------------------------------------" +
						"---------------------------+\n");
	}
	/**
	 * This method calls the correct method depending on the users choice
	 */
	public void choice(int num, DiceGroup dg){
		if(changeScore(1,dg) && num == 1) numberScore(num, dg);
		else if(changeScore(2,dg) && num == 2) numberScore(num, dg);
		else if(changeScore(3,dg) && num == 3) numberScore(num, dg);
		else if(changeScore(4,dg) && num == 4)	numberScore(num, dg);
		else if(changeScore(5,dg) && num == 5) numberScore(num, dg);
		else if(changeScore(6,dg) && num == 6) numberScore(num, dg);
		else if(changeScore(7,dg) && num == 7) threeOfAKind(dg);
		else if(changeScore(8,dg) && num == 8) fourOfAKind(dg);
		else if(changeScore(9,dg) && num == 9) fullHouse(dg);
		else if(changeScore(10,dg) && num == 10) smallStraight(dg);
		else if(changeScore(11,dg) && num == 11) largeStraight(dg);
		else if(changeScore(12,dg) && num == 12) chance(dg);
		else if(changeScore(13,dg) && num == 13) yahtzeeScore(dg);
	}
	/**
	 *  Change the scorecard based on the category choice 1-13.
	 *
	 *  @param choice The choice of the player 1 to 13
	 *  @param dg  The DiceGroup to score
	 *  @return  true if change succeeded. Returns false if choice already taken.
	 */
	public boolean changeScore(int choice, DiceGroup dg) {
		if(score[choice] != -1) return false;
		else return true;
	}
	
	/**
	 *  Change the scorecard for a number score 1 to 6
	 *
	 *  @param choice The choice of the player 1 to 6
	 *  @param dg  The DiceGroup to score
	 */
	public void numberScore(int choice, DiceGroup dg){
		int counter = 0;
		for(int i = 0; i<die.length;i++){
			die[i] = dg.getDie(i);
			if(die[i].getLastRollValue() == choice) counter++;
		}
		if(changeScore(choice, dg) == true)score[choice] = choice*counter;
		else (score[choice]) = 0;
	}
	
	/**
	 *	Updates the scorecard for Three Of A Kind choice.
	 * 
	 * 	score a 0 if a 3 of a kind doesnt exist and is chosen
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void threeOfAKind(DiceGroup dg) {
		
		int count[] = getCountArray(dg);
		boolean valuePlaced = false;
		for(int i = 0; i<count.length;i++){
			if(count[i] == 3){
				score[7] = (i+1)*3;
				valuePlaced = true;
			}
		}
		if(!valuePlaced) score[7] = 0;
	}
	/**
	 *	Updates the scorecard for Four Of A Kind choice.
	 * 
	 * 	score a 0 if a 4 of a kind doesnt exist and is chosen
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fourOfAKind(DiceGroup dg){
		int count[] = getCountArray(dg);
		
		boolean valuePlaced = false;
		for(int i = 0; i<count.length;i++){
			if(count[i] == 4){
				score[8] = (i+1)*4;
				valuePlaced = true;
			}
		}
		if(!valuePlaced) score[8] = 0;
	}
	/**
	 *	roll where you have both a three of a kind and a pair
	 * 
	 * 	score a 0 if a full house doesnt exist and is chosen
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void fullHouse(DiceGroup dg) {
		int count[] = getCountArray(dg);
		
		boolean two = false;
		boolean three = false;
		for(int i = 0; i<count.length;i++){
			if(count[i] == 2) two = true;
			if(count[i] == 3) three = true;
		}
		if(two && three) score[9] = 25;
		else score[9] = 0;
	}
	/**
	 *	A small straight is 4 consecutive faces,
	 * 
	 * 	score a 0 if a small straight doesnt exist and is chosen
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void smallStraight(DiceGroup dg){
		
		
		int counter = 0;
		for(int i = 0; i<die.length;i++) die[i] = dg.getDie(i);
		for(int i = 0; i<die.length-1;i++){
			if(die[i].getLastRollValue() == die[i+1].getLastRollValue() - 1) counter++;
		}
		
		if(counter == 3) score[10] = 30;
		else score[10] = 0;
	}
	/**
	 *	A large straight is 5 consecutive faces,
	 * 
	 * 	score a 0 if a large straight doesnt exist and is chosen
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void largeStraight(DiceGroup dg) {
		
		int counter = 0;
		for(int i = 0; i<die.length;i++) die[i] = dg.getDie(i);
		for(int i = 0; i<die.length-1;i++){
			if(die[i].getLastRollValue() == die[i+1].getLastRollValue() - 1) counter++;
		}
		
		if(counter == 4) score[11] = 40;
		else score[11] = 0;
	}
	/**
	 *	A chance adds all the dice values
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void chance(DiceGroup dg) {
		int total = 0;
		for(int i = 0; i<die.length;i++){
			die[i] = dg.getDie(i);
			total += die[i].getLastRollValue();
		}
		score[12] = total;
	}
	/**
	 *	A Yahtzee is a 5 of a kind (all die faces are the same), and it scores 50 points. 
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public void yahtzeeScore(DiceGroup dg){
		int count[] = getCountArray(dg);
		
		for(int i = 0; i<count.length;i++){
			System.out.println(count[i]);
			if(count[i] == 5 ){
				score[13] = 50;
			}
			else{
				score[13] = 0;
			}
		}
	}
	/**
	 *	returns the array where the number of each dice is counted in an array
	 *
	 *	@param dg	The DiceGroup to score
	 */	
	public int[] getCountArray(DiceGroup dg){
		int counter = 0;
		String str = "";
		String nums[] = {"1","2","3","4","5","6"};
		int arr1[] = new int[6];
		
		for(int i = 0; i<die.length;i++){
			die[i] = dg.getDie(i);
			str += die[i].getLastRollValue() + "";
		}
		for(int i = 0; i<nums.length;i++){
			for(int j = 0; j < 5; j++){
				if(nums[i].equals(str.substring(j,j+1))) arr1[i]++;
			}
		}
		return arr1;
	}
	/**
	 *	Getter method that returns the array which contains the array with scores
	 *
	 *	@return score	The array that contains all the scores
	 */	
	public int[] getScore(){
		return score;
	}

}
