/**
 *  You have a Jackpot machine. There are four "wheels" showing
 *  numbers. Prompt the user for the number of sides of each wheel.
 *  The machine generates four numbers until all four are the same.
 *
 *  @author Pranav
 * 	@Date August 23, 2021
 */

public class Casino
{
	//private Dice[] wheels;
	private Dice wheel1, wheel2, wheel3, wheel4;
	private int[] averages;
	private int trials;
	private int total;
	private String asterisk = "";
	
	// Constructor
	public Casino ()
	{
		wheel1 = wheel2 = wheel3 = wheel4 = new Dice(6);//default
		averages = new int[20];
		trials = 0;//number of trails conducted
	}
	public static void main (String [] args)
	{
		Casino run = new Casino();
		run.getTrials();
		run.runTrials();
		run.runHistogram();
	}
	public void getTrials ( )
	{
		System.out.println("\n\n");
		trials = Prompt.getInt("Please enter the number of trials(10-100000)",10,100000);
		System.out.println("\n");
	}
	public void runTrials() {
		for(int i = 4; i<=20;i++)
		{
			for(int j = 0; j <= trials; j++){
				wheel1 = new Dice(i);
				wheel2 = new Dice(i);
				wheel3 = new Dice(i);
				wheel4 = new Dice(i);
				spinWheel();
				total += wheel1.getRollCount();
				//System.out.println(total);
			}
			averages[i-4] = total/trials;
			//System.out.println(averages[i-4]);
		}
	}
	public void spinWheel ( )
	{
		boolean done = false;
		do
		{
			wheel1.roll();
			wheel2.roll();
			wheel3.roll();
			wheel4.roll();
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
	public void runHistogram(){
		double max = Double.valueOf(findMax(averages));
		double amtOfAsterisks = 0.0;
		System.out.println("Number\n of\tAve number\nsides\tof spins");
		for(int i = 4; i<=20; i++){
			amtOfAsterisks = (averages[i-4]/max)*60.0;
			for(int j=0;j<(int)amtOfAsterisks;j++){
				asterisk += "*";
			}
			System.out.println(i+":\t"+averages[i-4]+"\t"+asterisk);
			asterisk = "";
		}
		System.out.print("\n\n\n");
	}
	public int findMax(int[] t){
		int maximum = t[0];   // start with the first value
		for (int i=1; i<t.length; i++) {
			if (t[i] > maximum) {
				maximum = t[i];   // new maximum
			}
		}
		return maximum;
	}
}
