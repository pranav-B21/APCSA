import info.gridworld.actor.Actor;
import java.awt.Color;

public class Egg extends Actor{
	private int life;
	public Egg(){
		setColor(Color.WHITE);
		life = 1;
	}
	public void act(){
		life++;
		darken();
		if(life >= 45){
			setColor(Color.RED);
		}
		if(life >= 50){
			Chicken chicken = new Chicken();
			chicken.putSelfInGrid(getGrid(), getLocation());
		}
	}
	private void darken(){
		Color c = getColor();
        int red = (int) (c.getRed() * (1 - 0.05));
        int green = (int) (c.getGreen() * (1 - 0.05));
        int blue = (int) (c.getBlue() * (1 - 0.05));

        setColor(new Color(red, green, blue));
	}
}
