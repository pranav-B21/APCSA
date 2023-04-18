import java.util.ArrayList;
import java.lang.NumberFormatException;
/**
 *	PegSolitaire game.
 *	The program simulates a game of peg solitaire. This is a one player game.
 * 	The objective of the game is to remove all but one of the 32 pegs on the board.
 * 	Pegs jumps horizontally and vertically into empty locations and thejumped peg
 * 	is removed. The game is played until all pegs are removed or no more moves
 * 	can be done.
 *
 *	@author	Mitul Marimuthu
 *	@since	November 15, 2021
 *
 *	<detailed description goes here>
 */
public class PegSolitaire2 {
	
	// fields
	private PegBoard gameBoard;
	
	/** constructor */
	public PegSolitaire2() {
		gameBoard = new PegBoard();
	}
	
	/** methods */
	
	public static void main(String[] args) {
		PegSolitaire2 ps = new PegSolitaire2();
		ps.printIntroduction();
		ps.run();
	}
	
	private void run() {
		String[] input;
		Location jump;
		Location selected;
		do {
			gameBoard.printBoard();
			input = getInput();
			if (!input[0].equals("q")) {
				selected = new Location(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
				ArrayList<Location> jumps = getPossibleJumps(selected.getRow(), 
					selected.getCol());
				if (jumps.size() > 1)
					jump = printOptions(jumps);
				else
					jump = jumps.get(0);
				movePieces(selected, jump);
			}
		} while (!input[0].equals("q") && movesRemaining());
		gameBoard.printBoard();
		System.out.println("Your score: " + gameBoard.pegCount() + " pegs remaining");
		System.out.println("\nThank you for playing Peg Solitaire!\n");
	}
	
	private void movePieces(Location before, Location after){
		gameBoard.removePeg(before.getRow(), before.getCol());
		gameBoard.putPeg(after.getRow(), after.getCol());
		int colChange = after.getCol() - before.getCol();
		int rowChange = after.getRow() - before.getRow();
		if (colChange < 0) gameBoard.removePeg(after.getRow(), before.getCol() + colChange  + 1);
		else if (colChange > 0) gameBoard.removePeg(after.getRow(), before.getCol() + colChange - 1);
		else if (rowChange > 0) gameBoard.removePeg(before.getRow() + rowChange - 1, after.getCol());
		else if (rowChange < 0) gameBoard.removePeg(before.getRow() + rowChange + 1, after.getCol());
	}
	
	private Location printOptions(ArrayList<Location> jumps) {
		System.out.println("\nPossible peg jump locations:");
		for(int i = 0; i < jumps.size(); i++) {
			System.out.printf("%s%d%7s%n", " ", i, jumps.get(i));
		}
		int input = Prompt.getInt("Enter location", 0, jumps.size()-1);
		return jumps.get(input);
	}
	
	private boolean movesRemaining(){
		for (int i = 0; i < gameBoard.getBoardSize(); i++)
			for(int j = 0; j < gameBoard.getBoardSize(); j++)
				if (gameBoard.isPeg(i,j) && getPossibleJumps(i,j).size() > 0) {
					System.out.println("true");
					return true;
				}
		System.out.println("false");
		return false;
	}
	private ArrayList<Location> getPossibleJumps(int row, int col) {
		ArrayList<Location> jumps = new ArrayList<Location>();
		if (gameBoard.isPeg(row - 1, col) && (!gameBoard.isPeg(row-2, col) &&
			gameBoard.isValidLocation(row-2, col)))
			jumps.add(new Location(row-2, col));
		if (gameBoard.isPeg(row+1, col) && (!gameBoard.isPeg(row+2, col) &&
			gameBoard.isValidLocation(row+2, col)))
			jumps.add(new Location(row+2, col));
		if (gameBoard.isPeg(row, col-1) && (!gameBoard.isPeg(row, col-2) &&
			gameBoard.isValidLocation(row, col-2)))
			jumps.add(new Location(row, col-2));
		if (gameBoard.isPeg(row, col+1) && (!gameBoard.isPeg(row, col+2) &&
			gameBoard.isValidLocation(row, col+2)))
			jumps.add(new Location(row, col+2));
		return jumps;
	}
	private String[] getInput() {
		boolean found = false;
		String str = "";
		do {
			str = Prompt.getString("Jumper peg - row col (ex. 3 5, q=quit)");
			if (str.equals("q"))
				found = true;
			else {
				String[] values = str.split(" +");
				if (values.length == 2 && isInts(values) && gameBoard.isPeg(
					Integer.parseInt(values[0]), Integer.parseInt(values[1])) &&
					getPossibleJumps(Integer.parseInt(values[0]), Integer.parseInt(values[1])).size()
						> 0)
					found = true;
				else
					System.out.println("Invalid jumper peg -> " + str);
			}
		} while (!found);
		return str.split(" +");
	}
	
	private boolean isInts(String[] values) {
		for (int i = 0; i < values.length; i++) {
			try {
				int num = Integer.parseInt(values[i]);
			} catch (NumberFormatException e) {
				return false;
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
