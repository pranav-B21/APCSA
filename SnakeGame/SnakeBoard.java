/**
 *	<Describe the SnakeBoard here>
 *
 *	@author	Pranav Belligundu
 *	@since	
 */
public class SnakeBoard {
	
	/*	fields	*/
	private char[][] board;			// The 2D array to hold the board
	
	/*	Constructor	*/
	public SnakeBoard(int height, int width) {
		board = new char[height + 2][width + 2];
		printBorder();
	}
	
	/**
	 * prints the border of the board
	 */
	public void printBorder(){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (i == 0 || i == board.length - 1) {
					if (j == 0 || j == board[0].length - 1) board[i][j] = '+';
					else board[i][j] = '-';
				} else if (i != 0 && i != board.length - 1) {
					if (j == 0 || j == board[0].length - 1) board[i][j] = '|';
					else board[i][j] = ' ';
				}
			}
		}
	}
	
	/**
	 *	Print the board to the screen.
	 */
	public void printBoard(Snake snake, Coordinate target) {
		resetBoard();
		addItemsToArray(snake, target);
		for (char[] arr: board) {
			for (char a: arr) {
				System.out.print(a+" ");
			}
			System.out.println();
		}

	}
	/** 
	 *  Clears the entire board
	 */
	public void resetBoard() {
		for (int i = 1; i < board.length - 1; i++) {
			for (int j = 1; j < board[0].length - 1; j++) {
				board[i][j] = ' ';
			}
		}
	}
	
	// Helper methods
	private void addItemsToArray(Snake snake, Coordinate target) {

		for (int i = 0; i < snake.size(); i++) {
			int x = snake.get(i).getValue().getX();
			int y = snake.get(i).getValue().getY();
			
			if (i == 0)
				board[y][x] = '@';
			else
				board[y][x] = '*';
		}

		board[target.getY()][target.getX()] = '+';
	}

	// Accessor methods	
	public char[][] getBoard() {
		return board;
	}
}
