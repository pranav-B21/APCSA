import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList; 

public class ChameleonKid extends ChameleonCritter{
	
	public ArrayList<Actor> getActors()
    {
		ArrayList<Actor> actors = new ArrayList<>();
		getNewLocations(actors,Location.AHEAD);
		getNewLocations(actors,Location.HALF_CIRCLE);
		
		return actors;
    }
    
    public void getNewLocations(ArrayList<Actor> actors,int d){
		Grid gr = getGrid();
		Location loc = getLocation();

		if (gr.isValid(loc.getAdjacentLocation(getDirection() + loc.AHEAD))) {
			Actor a = getGrid().get(loc.getAdjacentLocation(getDirection() + loc.AHEAD));
			if (a != null) actors.add(a);
		}
		
	}
}
