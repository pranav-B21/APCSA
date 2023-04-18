/**
 * Coordinate class that keeps track of the coordinate of the snake
 * 
 * @author Pranav Belligundu
 * 
 */
public class Coordinate implements Comparable<Coordinate>{
	private int row; 
	private int col;
	public Coordinate(int r, int c){
		row = r;
		col = c;
	}
	
	/* Accessor methods */
	public int getRow(){return row;}
	public int getCol(){return col;}
	
	@Override
	public boolean equals(Object other)
	{
		if (other != null && other instanceof Coordinate &&
				row == ((Coordinate)other).row && 
				col == ((Coordinate)other).col)
			  return true;
		return false;
	}
	
	public int compareTo(Coordinate other) {
		return 0;
	}
}
