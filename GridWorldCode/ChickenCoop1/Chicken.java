
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class Chicken extends Critter{
	private int steps;
	
	public Chicken(){
		setColor(Color.WHITE);
		steps = 1;
	}
	
	public void makeMove(Location loc)
    {
        if(steps >= 300){
			Tombstone tomb = new Tombstone();
			tomb.putSelfInGrid(getGrid(), getLocation());
		}
		else if(steps >= 280 && steps % 4 == 0){
			darken();
			moveOrTurn();
		}
		else if(steps >= 200 && steps % 2 == 0){
			moveOrTurn();
		}
		else moveOrTurn();
    }
	
	private void moveOrTurn()
    {
        int num = (int)(Math.random() * 2);
        if(num == 0 && canMove()){
			Location loc = getLocation();
			Location next = loc.getAdjacentLocation(getDirection());
			moveTo(next);
			if(steps <= 200 && Math.random() < 0.05){
				Egg egg = new Egg();
				egg.putSelfInGrid(getGrid(), loc);
			}
		}
		else{
			int dir = (int)(Math.random() * 8);
			setDirection(getDirection() + dir * 45);
		}
		steps++;
    }
	
	private void darken(){
		Color c = getColor();
        int red = (int) (c.getRed() * (1 - 0.05));
        int green = (int) (c.getGreen() * (1 - 0.05));
        int blue = (int) (c.getBlue() * (1 - 0.05));

        setColor(new Color(red, green, blue));
	}

    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null);
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
	
	public void processActors(ArrayList<Actor> actors){}
}
