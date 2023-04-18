import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.Color;
import java.util.ArrayList;

public class BlusterCritter extends Critter{
	private int factor;
	private int totalActors;
	public BlusterCritter(int num){
		factor = num;
		totalActors = 0;
	}
	
	public ArrayList<Actor> getActors()
    {
		Location loc = getLocation();
		ArrayList<Actor> actors =  new ArrayList<>();
		for(int i = loc.getRow()-2; i <= loc.getRow() + 2; i++){
			for(int j = loc.getCol()-2; i <= loc.getCol() + 2; i++){
				Location newLoc = new Location(i,j);
				if(getGrid().isValid(newLoc)){
					Actor a = getGrid().get(newLoc);
					if(a instanceof Critter){
						actors.add(a);
						totalActors++;
					}
				}
			}
		}
		return actors;
	}
	
	public void processActors(ArrayList<Actor> actors)
    {
        if (totalActors < factor){
			Color c = getColor();
			int red = c.getRed();
			int green = c.getGreen();
			int blue = c.getBlue();
			if(red > 0) red--;
			if(green > 0) green--;
			if(blue > 0) blue--;
			setColor(new Color(red, green, blue)); 
        }
        else{
			Color c = getColor();
			int red = c.getRed();
			int green = c.getGreen();
			int blue = c.getBlue();
			if(red > 255) red++;
			if(green > 255) green++;
			if(blue > 255) blue++;
			setColor(new Color(red, green, blue)); 
		}
    }
}
