package gamePackage;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

public class Character extends GameObject {
	// variables
	public SpriteSheet sprite_attacking;
	public SpriteSheet sprite_dying;
	public SpriteSheet sprite_moving;
	public Sound sound_movement;
	public Sound sound_attacking;
	public Sound sound_dying;
	public boolean dead;
	public int attribute_health_max;
	public int attribute_health_current;
	public String attribute_name;
	
	// Methods
	public void kill(){
		
	}
}
