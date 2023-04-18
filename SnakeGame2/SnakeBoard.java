/**
 *	<Describe the SnakeBoard here>
 *
 *	@author	Pranav Belligundu
 *	@since	
 */
public class SnakeBoard {
	
	/*	fields	*/
	private char[][] board;			// The 2D array to hold the board
	private int width;				//width of the board
	private int height;				//height of the board
	
	/*	Constructor	*/
	public SnakeBoard(int height, int width) {
		board = new char[width][height];
		this.width = width;
		this.height = height;
	}
	
	/**
	 *	Print the board to the screen.
	 */
	public void printBoard(Snake snake, Coordinate target) {
		System.out.println();
		printFrame(0);
		for(int i = 0; i < board.length; i++){
			printRows(board[i], i+1);
		}
		printFrame(board.length + 1);
	}
	
	public void printFrame(int index){
		System.out.printf("%-2d", index);
		for(int i = 0; i < board[0].length + 2; i++){
			if(i == 0 || i == board[0].length + 1) System.out.printf("%2s","+");
			else System.out.printf("%3s", "-");
		}
		System.out.println();
	}

	public void printRows(char[] arr, int index){
		System.out.printf("%-3d|", index);
		for(int i = 0; i < arr.length; i++){
			System.out.printf("%3s", ""+arr[i]);
		}
		System.out.print(" |\n");
	}
	/* Helper methods go here	*/
	
	/*	Accessor methods	*/

	
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	
	public static void main(String[] args) {
		// Create the board
		int height = 10, width = 15;
		SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		Coordinate cord = new Coordinate(3,3);
		Snake snake = new Snake(cord);
		// Place the target
		Coordinate target = new Coordinate(1, 7);
		// Print the board
		sb.printBoard(snake, target);
	}
}
/**
 * 
 * for(int i = 0; i <= width + 1; i++){
			for(int j = 0; j <= height + 1; j++){
				if((i == 0 && j == 0) || (i == board.length && j == 0)||
					(i == 0 && j == board[0].length) || (i == board.length && j == board[0].length))
					System.out.print("+");
				else if(j == 0 || j == board[0].length)
					System.out.print("|");
				else if(i == 0 || i == board.length)
					System.out.print(" - ");
			}
			System.out.println();
		}
		*/
