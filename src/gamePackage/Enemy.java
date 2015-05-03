package gamePackage;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Enemy extends Character{
	public ArrayList<Item> reward_items;
	public int reward_gold;
	public int reward_experience;
	public int attributepoints;
	public Armor gear_armor;
	public Weapon gear_weapon;
	public boolean mouse_hovered = true;
	
	public Enemy(float tile_x, float tile_y) throws SlickException{
		frameWidth = 128;
		frameHeight = 112;
		position_x = tile_x;
		position_y = tile_y;
		sprite_idle = new SpriteSheet(new Image("Textures/goatman_idle_0.png"),frameWidth,frameHeight);
		anim_idle[0] = new Animation(sprite_idle,200);
		anim_idle[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_1.png"),frameWidth,frameHeight),200);
		anim_idle[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_2.png"),frameWidth,frameHeight),200);
		anim_idle[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_3.png"),frameWidth,frameHeight),200);
		anim_walking[0] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_0.png"),frameWidth,frameHeight),200);
		anim_walking[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_1.png"),frameWidth,frameHeight),200);
		anim_walking[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_2.png"),frameWidth,frameHeight),200);
		anim_walking[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_3.png"),frameWidth,frameHeight),200);
		attribute_name = "EnemyName";
		pixelWidth = 30;
		pixelHeight = 55;
		pixelTranslation_x = 30;
		pixelTranslation_y = -45;
	}
	
	public void attack(){
		
	}
	public void roam(){
		
	}
	public void kill(){
		
	}
}
