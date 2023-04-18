/**
 *	Target.java
 *
 *	To compile:	javac -cp .:mvAcm.jar SimpleGraphics.java
 *	To execute:	java -cp .:mvAcm.jar SimpleGraphics
 *
 *	@author	Pranav Belligundu
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

/*
 * This the main class where Target.java is run
*/
public class Target extends GraphicsProgram {
	
	/*	All fields and constants should be declared here.
	 *	Only constants (final) are initialized here. */
	private GOval[] circles;
	private final double WIDTH = 15;

	
	/**	The init() method is executed before the run() method.
	 *	All initialization steps should be performed here.
	 */
	public void init() {
		circles = new GOval[5];//initializes the circles array 
	}
	
	/**	The run() method is executed after init().
	 *	The bulk of the program should be performed here.
	 * 	
	 * 	The alternating colors in the semicircle is caused by drawing
	 * 	circles on top of each other causing the pattern. And the 
	 * 	diameter of the circle decreases as each circle is drawn. 
	 *  
	 * 	The if statements are used to determine whether the circle is
	 * 	red or white. 
	 */
	public void run() {
		int xpos = 125; // y-coordinate of the brick
		int ypos = 225; //x-coordinate of the brick
		int diameter = 500;
		for(int i = 0; i < circles.length;i++){
			circles[i] = new GOval(xpos, ypos, diameter, diameter);
			circles[i].setFilled(true);
			if(i%2 == 0){
				circles[i].setFillColor(Color.RED);
			}
			else if(i%2 == 1){
				circles[i].setFillColor(Color.WHITE);
			}
			add(circles[i]);
			
			diameter -= 50;
			xpos += 25; //moves the x position to the right
			ypos += 25; //moves the y position down
			
		}
	}
}
