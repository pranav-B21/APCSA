/*
 * A version of a flower
 * 
 * @author Pranav Belligundu
 */
 
import info.gridworld.actor.Actor;

public class Tombstone extends Actor{
	int lifetime;
	
	/**
     * Constructs a flower of a green color, and lifetime
     * @param lifetime 		lifetime of the blossom, dies after reaches
     * 						0
     */
	public Tombstone(int lifetime){
		this.lifetime = lifetime;
	}
	
	/**
     * Constructs a green flower and lifetime at 10.
     */
	public Tombstone(){
		lifetime = 20;
	}
	
	/**
     * Causes the color of this flower to darken, and after lifetime
     * reaches 0, it removes itself from the grid.
     */
	public void act(){
		lifetime --;
		if(lifetime <= 0) removeSelfFromGrid();
	}
	
}
