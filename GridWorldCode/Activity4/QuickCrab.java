import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;

public class QuickCrab extends CrabCritter{
	public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        checkRight(locs);
        checkLeft(locs);
        if (locs.size() == 0)
			return super.getMoveLocations(); 
        return locs;
    }
    public void checkRight(ArrayList<Location> locs){
		Location loc = getLocation();
		Grid g = getGrid();
		
		Location newLoc = loc.getAdjacentLocation(loc.RIGHT).getAdjacentLocation(loc.RIGHT);
	//	System.out.println(newLoc);
		if (g.isValid(newLoc) && g.get(newLoc) == null) locs.add(newLoc);
	}
	
	public void checkLeft(ArrayList<Location> locs){
		Location loc = getLocation();
		Grid g = getGrid();
		
		Location newLoc = loc.getAdjacentLocation(loc.LEFT).getAdjacentLocation(loc.LEFT);
		if (g.isValid(newLoc) && g.get(newLoc) == null) locs.add(newLoc);
	}
}
