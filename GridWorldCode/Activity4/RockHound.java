import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import java.util.ArrayList; 
import info.gridworld.actor.Rock; 

public class RockHound extends Critter{
	
	public void processActors(ArrayList<Actor> actors){
		for(int i = 0; i < actors.size(); i++){
			Actor a = actors.get(i);
			if(a instanceof Rock)
				a.removeSelfFromGrid();
		}
	}
}
