package gamePackage;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

public class GameObject implements Comparable<GameObject>{
	public SpriteSheet sprite_idle;
	public float position_x;
	public float position_y;
	public float screenPosition_x;
	public float screenPosition_y;
	public int frameWidth;
	public int frameHeight;
	public int pixelWidth;
	public int pixelHeight;
	public int pixelTranslation_x;
	public int pixelTranslation_y;
	float collision_size = 0.5f;
	
	public GameObject(){}
	
	public Animation getCurrentAnimation(){
			return new Animation(sprite_idle,200);
	}

	@Override
	public int compareTo(GameObject o) {
		float height = position_y-position_x;
		if(this instanceof Item){
			height += 1000;
		}
		float comparedCharHeight = o.position_y-o.position_x;
		//return in descending order
		return (int)(comparedCharHeight*100-height*100);
	}
}
