/**
 *	SimpleGraphics.java
 *
 *	To compile:	javac -cp .:mvAcm.jar SimpleGraphics.java
 *	To execute:	java -cp .:mvAcm.jar SimpleGraphics
 *
	@author	Pranav Belligundu
 *	@since	Sept 9, 2021
 */
 
/*	All package classes should be imported before the class definition.
 *	"java.awt.Color" means package java.awt contains class Color. */
import java.awt.Color;

/*	The following libraries are in the acm.jar file. */
import acm.program.GraphicsProgram;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Bricks extends GraphicsProgram {
	
	/*	All fields and constants should be declared here.
	 *	Only constants (final) are initialized here. */
	private GRect[] bricks;
	private final int HEIGHT = 20;
	private final int WIDTH = 50;
	private final int CENTER = 365;
	
	/**	The init() method is executed before the run() method.
	 *	All initialization steps should be performed here.
	 */
	public void init() {
		bricks = new GRect[55];
	}
	
	/**	The run() method is executed after init().
	 *	The bulk of the program should be performed here.
	 *	This run method contains a nested loop.
	 * 		The first for loop determines the amount off rows and the
	 * 		amount of blocks. It decends from 10 to 1]
	 * 
	 * 		The second for loop draws the bricks on the JPanel
	 */
	public void run() {
		int xpos = CENTER-265;
		int xpos2 = 265;
		int ypos = 0;
		for(int row = 10; row > 0; row--){
			xpos2 -= 25;
			xpos = CENTER -xpos2;
			
			for(int col = 0; col < row; col++){
				bricks[col] = new GRect(xpos, ypos, WIDTH, HEIGHT);
				bricks[col].setFilled(true);
				bricks[col].setFillColor(Color.WHITE);
				add(bricks[col]);
				xpos += 50;
			}
			ypos += 20;
		}
	}
}
