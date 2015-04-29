package gamePackage;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

public class Character extends GameObject {
	// variables
	public Animation[] anim_attacking = new Animation[4];
	public Animation anim_dying;
	public Animation[] anim_walking = new Animation[4];
	public Animation[] anim_idle = new Animation[4];
	public Sound sound_movement;
	public Sound sound_attacking;
	public Sound sound_dying;
	public boolean dead;
	public int attribute_health_max;
	public int attribute_health_current;
	public String attribute_name;
	private int direction = 1;
	private Action currentAction = Action.IDLE;
	
	public enum Action{
		IDLE,ATTACKING,DYING,WALKING
	}
	
	// Methods
	public void kill(){
		
	}
	
	public void setAction(Action action){
		currentAction = action;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	public Animation getCurrentAnimation(){
		switch(currentAction){
		case IDLE:
			return anim_idle[direction];
		case ATTACKING:
			return anim_attacking[direction];
		case DYING:
			return anim_dying;
		case WALKING:
			return anim_walking[direction];
		default:
			return anim_idle[direction];
		}
	}
}
