/**
 *	The object to store US state information.
 *
 *	@author	Pranav Belligundu
 *	@since	
 */
public class State implements Comparable<State>
{
	private String name;
	private String abbreviation;
	private int population;
	private int area;
	private int reps;
	private String capital;
	private int month;
	private int day;
	private int year;
	
	public State(String n, String a, int p, int ar, int r, String c, int m, int d, int y) 
	{
		name = n;
		abbreviation = a;
		population = p;
		area = ar;
		reps = r;
		capital = c;
		month = m;
		day = d;
		year = y;
	}
	
	public int compareTo(State other) 
	{
		return name.compareTo(other.name);
	}
	
	public String getName ( )
	{
		return name;
	}
	
	public String toString() 
	{
		return String.format("%-21s%-12s%-11d%-8d%-5d%-21s%-3d%-3d%-4d",
							name,abbreviation,population,area,reps,capital,
							month, day, year);
	}
}
