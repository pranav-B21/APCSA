import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class Fox extends Critter{
	int hunger;
	boolean eaten;
	
	public Fox(){
		hunger = 0;
		eaten = false;
		setColor(null);
	}
	
	public void makeMove(Location loc){
		System.out.println(hunger);
		if(hunger >= 20){
			Tombstone tomb = new Tombstone();
			tomb.putSelfInGrid(getGrid(), getLocation());
		}
		else if(hunger >= 10 && eaten == true){ eaten = false; hunger = 0;}
		else moveTo(loc);
	}
	

	public void processActors(ArrayList<Actor> actors){
		for (Actor a : actors)
        {
            if ((a instanceof Chicken) && eaten == false){
                Tombstone tomb = new Tombstone();
				tomb.putSelfInGrid(getGrid(), a.getLocation());
				eaten = true; 
				hunger = 0;
			}
        }
	}
    
  
    //ITS MOVING TO PLACES WITH ACTORS ALREADY THERE, FIXXXX
    public ArrayList<Location> getMoveLocations()
    {
		Grid g = getGrid();
		Location loc = getLocation();
		ArrayList<Location> moveLocs = new ArrayList<Location>();
		ArrayList<Actor> chick = getChickens();
		double dist = 100.0;
		
		if(chick.size() == 0) {
			hunger ++;
			return getGrid().getEmptyAdjacentLocations(getLocation());
		}
		for(int i = 0; i < chick.size(); i ++){
			double cRow = (double)(chick.get(i).getLocation().getRow());
			double cCol = (double)(chick.get(i).getLocation().getCol());
			
			double lRow = (double)(loc.getRow());
			double lCol = (double)(loc.getCol());
			
			double currDist =  calculateDistanceBetweenPoints(lCol, lRow, cCol, cRow);
			
			if(currDist <= dist){
				dist = currDist;
				Location chickenLoc = chils
				ck.get(i).getLocation();
				if(getGrid().isValid(chickenLoc) && eaten == false){
					int dir = loc.getDirectionToward(chickenLoc);
					moveLocs.add(loc.getAdjacentLocation(dir));
				}
				
			}
	
		}
		
		hunger++;
		
		return moveLocs;
		
		
    }
    
    private double calculateDistanceBetweenPoints(double x1, double y1, double x2, double y2) {       
		return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
	}
	
	private ArrayList<Actor> getChickens()
    {
        ArrayList<Location> allActors = getGrid().getOccupiedLocations();
        ArrayList<Actor> onlyChickens = new ArrayList<Actor>();
        for(Location loc: allActors){
			if(getGrid().get(loc) instanceof Chicken){ 
				onlyChickens.add(getGrid().get(loc));
			}
		}
		return onlyChickens;
    }

}
