import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;

public class KingCrab extends CrabCritter{

    public boolean oneAway(Actor a) {
		ArrayList<Location> actorLocs = getGrid().getEmptyAdjacentLocations(a.getLocation());
		ArrayList<Location> crabLocs = getGrid().getEmptyAdjacentLocations(getLocation());

		boolean done = true;
		for (int i = 0; i < actorLocs.size(); i++) {
			done = true;
			for (Location loc: crabLocs) {
				if (actorLocs.get(i).equals(loc))
					done = false;
			}
			if (done) {
				a.moveTo(actorLocs.get(i));
				return true;
			}
		}
		
		return false;
	}
	
	public void processActors(ArrayList<Actor> actors){
		for (Actor a : actors){
			if (!oneAway(a)){
				a.removeSelfFromGrid();
			}
		} 
	}
}
