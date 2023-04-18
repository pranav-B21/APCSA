/**
 *  You have a Jackpot machine. There are four "wheels" showing
 *  numbers. Prompt the user for the number of sides of each wheel.
 *  The machine generates four numbers until all four are the same.
 *
 *  @author Mr Greenstein
 */

public class LittleJackpot
{
	private Dice wheel1, wheel2, wheel3, wheel4;
	private int numberofsides;
	
	// Constructor
	public LittleJackpot ( )
	{
		wheel1 = wheel2 = wheel3 = wheel4 = new Dice(6);
		numberofsides = 6;
	}
	
	public static void main (String [] args)
	{
		LittleJackpot run = new LittleJackpot();
		run.getSidesTrials();
		run.runTrials();
	}
	
	/**
	 *  Prompt user for number of sides on each wheel.
	 */
	public void getSidesTrials ( )
	{
		System.out.println("\n\n");
		numberofsides = Prompt.getInt("Please enter the number of sides for each wheel",4,100);
		System.out.println("\n");
	}
	
	/**
	 *  Run the number of trials collecting the number of spins.
	 */
	public void runTrials() {
		wheel1 = new Dice(numberofsides);
		wheel2 = new Dice(numberofsides);
		wheel3 = new Dice(numberofsides);
		wheel4 = new Dice(numberofsides);
		System.out.println("\n\n");
		spinWheel();
		System.out.println("It took " + wheel1.getRollCount() + " spins to win "
					+ "the jackpot.\n\n");
	}
	
	/**
	 *  Spin each wheel until all the numbers are equal.
	 */
	public void spinWheel ( )
	{
		boolean done = false;
		do
		{
			wheel1.roll();
			wheel2.roll();
			wheel3.roll();
			wheel4.roll();
			System.out.printf("%4d %4d %4d %4d%n", 
				wheel1.getValue(), wheel2.getValue(), wheel3.getValue(),
				wheel4.getValue());
			if (wheel1.getValue() == wheel2.getValue())
			{
				if (wheel2.getValue() == wheel3.getValue())
				{
					if (wheel3.getValue() == wheel4.getValue())
					{
						done = true;
					}
				}
			}
		}
		while(!done);
	}
}
