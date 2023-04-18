import java.util.ArrayList;
/**
 *	PegSolitaire game.
 *	<short description of game goes here>
 *
 *	@author	Pranav Belligundu
 *	@since	November 21, 2021
 *
 *	<detailed description goes here>
 */
public class PegSolitaire {
	
	// fields
	private PegBoard board;
	private String input;
	String[] arr;
	private int row;
	private int col;
	
	private String [] values;
		
	/** constructor */
	public PegSolitaire() { 
		board = new PegBoard();
		row = 0;
		col = 0;
	}
	
	/** methods */
	public static void main(String[] args) {
		PegSolitaire ps = new PegSolitaire();
		ps.printIntroduction();
		ps.run();
	}
	private void run() {
		do {
			board.printBoard();
			input = getInput();
			arr = input.split(" +");
			row = Integer.parseInt(arr[0]);
			col = Integer.parseInt(arr[1]);
			Location selected = new Location(row,col);
			ArrayList<Location> totalJumps = getPossibleJumps(selected.getRow(),selected.getCol());
			
			if(totalJumps.size() == 1){//if only one option to move
				movePegs(selected,totalJumps.get(0));
			}
			else if(totalJumps.size() > 1){//if more than one option to moves
				System.out.println("Possible peg jump locations:");
				for(int i = 0; i < totalJumps.size(); i++){
					System.out.print(" "+i+" "+totalJumps.get(i));
					System.out.println();
				}
				int num = Prompt.getInt("Enter location", 0, totalJumps.size()-1);
				movePegs(selected,totalJumps.get(num));
			}
		}while(!arr[0].equals("q") && movesRemaining());
		board.printBoard();
		System.out.println("Your score: " + board.pegCount() + " pegs remaining");
		System.out.println("\nThank you for playing Peg Solitaire!\n");
	}
	public void movePegs(Location prev, Location current){
		board.removePeg(prev.getRow(),prev.getCol());
		board.putPeg(current.getRow(),current.getCol());
		int col = current.getCol() - prev.getCol();//if it went up or down
		int row = current.getRow() - prev.getRow();//if it went right or left
		
		//removes the middle piece
		if (col < 0) board.removePeg(current.getRow(), prev.getCol() + col  + 1);
		else if (col > 0) board.removePeg(current.getRow(), prev.getCol() + col - 1);
		else if (row > 0) board.removePeg(prev.getRow() + row - 1, current.getCol());
		else if (row < 0) board.removePeg(prev.getRow() + row + 1, current.getCol());
	}
	private boolean movesRemaining(){
		for (int i = 0; i < board.getBoardSize(); i++){
			for(int j = 0; j < board.getBoardSize(); j++){
				if (board.isPeg(i,j) && getPossibleJumps(i,j).size() > 0) {
					return true;
				}
			}
		}
		return false;
		
	}
	public String getInput(){
		boolean valid = false;
		String input = "";
		do{ // gets which location they need to insert the peg
			input = Prompt.getString("Jumper peg - row col (ex.3 5, q=quit)");
			if(input.equals("q")){
				valid = true;
			}
			else{
				String[] values = input.split(" +");
				if (values.length == 2){ // if user input 2 strings
					valid = true;
				}
				else if(isInts(values)){ // if the values is an int
					valid = true;
				}
				else if (board.isPeg(Integer.parseInt(values[0]), 
					Integer.parseInt(values[1]))){ //if that peg exists
					valid = true;
				}
				else if(getPossibleJumps(Integer.parseInt(values[0]),
					Integer.parseInt(values[1])).size() > 0){ //if that peg has moves
					valid = true;
				}
				else //ask user to type input again
					System.out.println("Invalid jumper peg ->"+values[0]+" "+values[1]);
			}	
		}while(!valid);
		return input;
	}
	private ArrayList<Location> getPossibleJumps(int row, int col) {
		ArrayList<Location> jumps = new ArrayList<Location>();
		if (board.isPeg(row - 1, col) && (!board.isPeg(row-2, col) &&
			board.isValidLocation(row-2, col)))
			jumps.add(new Location(row-2, col));
		if (board.isPeg(row+1, col) && (!board.isPeg(row+2, col) &&
			board.isValidLocation(row+2, col)))
			jumps.add(new Location(row+2, col));
		if (board.isPeg(row, col-1) && (!board.isPeg(row, col-2) &&
			board.isValidLocation(row, col-2)))
			jumps.add(new Location(row, col-2));
		if (board.isPeg(row, col+1) && (!board.isPeg(row, col+2) &&
			board.isValidLocation(row, col+2)))
			jumps.add(new Location(row, col+2));
		return jumps;
	}
	private boolean isInts(String[] values) {
		for (int i = 0; i < values.length; i++) {
			for(int j = 0; j<values[i].length(); j++){
				if(!Character.isDigit(values[i].charAt(j))){
					return false;
					
				}
			}
		}
		return true;
	}
	/**
	 *	Print the introduction to the game
	 */
	public void printIntroduction() {
		System.out.println("  _____              _____       _ _ _        _ ");
		System.out.println(" |  __ \\            / ____|     | (_) |      (_)");
		System.out.println(" | |__) |__  __ _  | (___   ___ | |_| |_ __ _ _ _ __ ___ ");
		System.out.println(" |  ___/ _ \\/ _` |  \\___ \\ / _ \\| | | __/ _` | | '__/ _ \\");
		System.out.println(" | |  |  __/ (_| |  ____) | (_) | | | || (_| | | | |  __/");
		System.out.println(" |_|   \\___|\\__, | |_____/ \\___/|_|_|\\__\\__,_|_|_|  \\___|");
		System.out.println("             __/ |");
		System.out.println("            |___/");
		System.out.println("\nWelcome to Peg Solitaire!!!\n");
		System.out.println("Peg Solitaire is a game for one player. The " +
							"goal is to remove all\n" +
							"but one of the 32 pegs from a special board. " +
							"The board is a 7x7\n" +
							"grid with the corners cut out (shown below)." +
							" Pegs are placed in all");
		System.out.println("grid locations except the center which is " +
							"left empty. Pegs jump\n" +
							"over other pegs either horizontally or " +
							"vertically into empty\n" +
							"locations and the jumped peg is removed. Play " +
							"continues until\n" +
							"there are no more jumps possible, or there " +
							"is one peg remaining.");
		System.out.println("\nLet's play!!!\n");
	}
	
}
