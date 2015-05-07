package gamePackage;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

public class GameObject implements Comparable<GameObject>{
	public SpriteSheet sprite_idle;
	public float position_x;
	public float position_y;
	public float screenPosition_x;
	public float screenPosition_y;
	public int pixelWidth;
	public int pixelHeight;
	public int pixelTranslation_x;
	public int pixelTranslation_y;
	float collision_size = 0.30f;
	
	
	public GameObject(){}
	
	
	/**
	 * Gets the Animation which this object is performing
	 * @return The current Animation object which should be drawn
	 */
	public Animation getCurrentAnimation(){
			return new Animation(sprite_idle,200);
	}
	
	
	@Override
	public int compareTo(GameObject o) {
		//The higher an object is on the screen, the sooner it should be drawn, in order for it to be drawn in the back.
		float height = Game.gameLevel.getWidthInTiles()+position_y-position_x;
		if(this instanceof Item){
			//items should be in front of dead bodies, but behind everything else.
			height += 99;
		}
		else if(this instanceof Character){
			if(((Character)this).dead){
				//dead bodies should be drawn behind everything else
				height += 100;
			}
			
		}
		float comparedCharHeight = Game.gameLevel.getWidthInTiles()+o.position_y-o.position_x;
		if(o instanceof Item){
			comparedCharHeight += 99;
		}
		else if(o instanceof Character){
			if(((Character)o).dead){
				comparedCharHeight += 100;
			}
			
		}
		//return in descending order
		if(comparedCharHeight > height) return 1;
		else if (comparedCharHeight == height) return 0;
		else if(comparedCharHeight < height) return -1;
		return 0;
	}
}

