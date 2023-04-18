import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
/**
 *	Snake Game - 
 *	
 *	@author		Pranav Belligundu
 *	@since		May 12, 2022
 */
public class SnakeGame {
	
	private Snake snake;		// the snake in the game
	private SnakeBoard board;	// the game board
	private Coordinate target;	// the target for the snake
	private int score;			// the score of the game
	private char[][] gb;

	/*	Constructor	*/
	public SnakeGame() { 
		snake = new Snake((int)(Math.random() * 14) + 1, 
						  (int)(Math.random() * 18) + 1); //randomly generates a place for the snake
		board = new SnakeBoard(20, 20);
		target = new Coordinate(10, 5);
		score = 0;
		gb = board.getBoard();
	}
	
	/*	Main method	*/
	public static void main(String[] args) {
		SnakeGame game = new SnakeGame();
		game.playGame();
	}

	public void playGame() {

		boolean running = true;
		while (running) {
			gb = board.getBoard();
			board.printBoard(snake, target);
			boolean bool = false;
			String input = getMove();

			switch(input){
				case "h":
					helpMenu();
				case "q":
					exitGame();
				case "a":
					bool = move("left", snake, target); 
				case "d":
					bool = move("right", snake, target); 
				case "w":
					bool = move("up", snake, target); 
				case "s":
					bool = move("down", snake, target); 
				case "f":
					String save = Prompt.getString("Save game? (y or n)");
					while (!save.equals("y") && !save.equals("n")) {
						save = Prompt.getString("Save game? (y or n)");
					}

					if (save.equals("y")) {
						saveGame();
					}
				case "r":
					loadGame();
				}
				
				if (bool) {
				score++;
				int y = (int) (Math.random() * 19) + 1;
				int x = (int) (Math.random() * 19) + 1;
				Coordinate coord = new Coordinate(x, y);
				while (inSnake(coord)) {
					y = (int) (Math.random() * (19)) + 1;
					x = (int) (Math.random() * (19)) + 1;
					coord = new Coordinate(x, y);
				}

				target = coord;
			}
				
		}
	}
	/**
	 * Gets the user input
	 */
	public String getMove(){
		String move = Prompt.getString("Score: " + score + " (w - North, s - South, d - East, a - West, h - Help)");
		while (!move.equalsIgnoreCase("w") && !move.equalsIgnoreCase("a") &&
				!move.equalsIgnoreCase("s") && !move.equalsIgnoreCase("d") &&
				!move.equalsIgnoreCase("h") && !move.equalsIgnoreCase("f") &&
				!move.equalsIgnoreCase("r") && !move.equalsIgnoreCase("q")) {
			move = Prompt.getString("Score: " + score + " (w - North, s - South, d - East, a - West, h - Help)");
		}
		return move;
	}
	
	/**
	 * 	moves the snake to which direction the user inputed
	 */
	private boolean move(String direction, Snake snake, Coordinate target) {
		Coordinate prev = snake.get(0).getValue();
		Coordinate coord = null;
		boolean bool = false;
		if (direction.equals("left")) {
			coord = new Coordinate(snake.get(0).getValue().getX() - 1, snake.get(0).getValue().getY());
		}

		else if (direction.equals("right")) {
			coord = new Coordinate(snake.get(0).getValue().getX() + 1, snake.get(0).getValue().getY());
		}

		else if (direction.equals("up")) {
			coord = new Coordinate(snake.get(0).getValue().getX(), snake.get(0).getValue().getY() - 1);
		}

		else if (direction.equals("down")) {
			coord = new Coordinate(snake.get(0).getValue().getX(), snake.get(0).getValue().getY() + 1);

		}
		if (coord.getX() < 1 || coord.getX() > gb[0].length - 2 || coord.getY() < 1
				|| coord.getY() > gb.length - 2) { //check if snake leaves border
			exitGame();
		}

		if (inSnake(coord)) // checks if snake hits itself
			exitGame();
		snake.get(0).setValue(coord);
		if (coord.equals(target)) {
			bool = true;
		}

		for (int i = 1; i < snake.size(); i++) {
			Coordinate localPrev = snake.get(i).getValue();
			snake.get(i).setValue(prev);
			prev = localPrev;
		}

		if (bool)
			snake.add(prev); // adds length to the snake

		return bool;
	}
	/**
	 *  Loads a game from the saved games
	 */
	private void loadGame() {
		snake.get(0).setValue(null);
		snake.get(0).setNext(null);
		board = new SnakeBoard(20, 20);
		Scanner saveFile = FileUtils.openToRead("snakeGameSave.txt");

		String scoreStr = saveFile.nextLine();
		score = Integer.parseInt(scoreStr.substring(scoreStr.indexOf(" ") + 1).trim());

		String targetStr = saveFile.nextLine();
		targetStr = targetStr.substring(targetStr.indexOf(" ") + 1);
		Coordinate targetCoord = new Coordinate(Integer.parseInt(targetStr.charAt(0) + ""),
				Integer.parseInt(targetStr.substring(targetStr.indexOf(" ") + 1).trim()));
		target = targetCoord;

		saveFile.nextLine();

		String line = new String();

		while (saveFile.hasNextLine()) {
			line = saveFile.nextLine();
			Coordinate node = new Coordinate(Integer.parseInt(line.substring(0, line.indexOf(" "))),
					Integer.parseInt(line.substring(line.indexOf(" ") + 1).trim()));
			snake.add(node);
		}
	}

	/** 
	 * Saves game so it can be played later
	 */
	private void saveGame() {
		System.out.println("Saving game file to snakeGameSave.txt");
		PrintWriter saveFile = FileUtils.openToWrite("snakeGameSave.txt");
		saveFile.println("Score " + score);
		saveFile.println("Target " + target.getX() + " " + target.getY());
		saveFile.println("Snake " + snake.size());
		for (int i = 0; i < snake.size(); i++) {
			saveFile.println(snake.get(i).getValue().getX() + " " + snake.get(i).getValue().getY());
		}
		saveFile.close();
		System.out.println("\nGame is over\nScore = " + score + "\n\nThanks for playing SnakeGame!!");
		System.exit(0);
	}
	
	/**
	 * checks if a location exists in the snake
	 */
	private boolean inSnake(Coordinate target) {
		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i).getValue().equals(target))
				return true;
		}

		return false;
	}
	
	/** 
	 * quits the game
	 */
	private void exitGame() {
		String quit = Prompt.getString("Do you really want to quit (y or n)");
		while (!quit.equals("y") && !quit.equals("n")) {
			quit = Prompt.getString("Do you really want to quit (y or n)");
		}
		if (quit.equals("y")) {
			System.out.println("\nGame is over\nScore = " + score + "\n\nThanks for playing SnakeGame!!");
			System.exit(0);
		}
	}
	
	/**	Print the game introduction	*/
	public void printIntroduction() {
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___|  (____  /__|_ \\\\___  >\\______  (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
							"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
							"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
							"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}
	
	/**	Print help menu	*/
	public void helpMenu() {
		System.out.println("\nCommands:\n" +
							"  w - move north\n" +
							"  s - move south\n" +
							"  d - move east\n" +
							"  a - move west\n" +
							"  h - help\n" +
							"  f - save game to file\n" +
							"  r - restore game from file\n" +
							"  q - quit");
		Prompt.getString("Press enter to continue");
	}
}
/*
			if (input.equalsIgnoreCase("h")) {
				helpMenu();
			}
			else if (input.equals("q")) {
				String quit = Prompt.getString("Do you really want to quit (y or n)");
				while (!quit.equals("y") && !quit.equals("n")) {
					quit = Prompt.getString("Do you really want to quit (y or n)");
				}

				if (quit.equals("y")) {
					exitGame();
				}
			} 
			else if (input.equals("a")) {
				increase = move("left", snake, target);
			} else if (input.equals("d")) {
				increase = move("right", snake, target);
			} else if (input.equals("w")) {
				increase = move("up", snake, target);
			} else if (input.equals("s")) {
				increase = move("down", snake, target);
			} else if (input.equals("f")) {
				String save = Prompt.getString("Save game? (y or n)");
				while (!save.equals("y") && !save.equals("n")) {
					save = Prompt.getString("Save game? (y or n)");
				}

				if (save.equals("y")) {
					saveGame();
				}
			} else if (input.equals("r")) {
				loadGame();
			}

			if (increase) {
				score++;
				int y = (int) (Math.random() * 19) + 1;
				int x = (int) (Math.random() * 19) + 1;
				Coordinate coord = new Coordinate(x, y);
				while (inSnake(coord)) {
					y = (int) (Math.random() * (19)) + 1;
					x = (int) (Math.random() * (19)) + 1;
					coord = new Coordinate(x, y);
				}

				target = coord;
			}
			*/
