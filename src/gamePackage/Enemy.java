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
	
	public Enemy(float pos_x, float pos_y) throws SlickException{
		position_x = pos_x;
		position_y = pos_y;
		anim_idle[0] = new Animation(new SpriteSheet(new Image("Textures/player_idle_0.png"),96,112),200);
		anim_idle[1] = new Animation(new SpriteSheet(new Image("Textures/player_idle_1.png"),96,112),200);
		anim_idle[2] = new Animation(new SpriteSheet(new Image("Textures/player_idle_2.png"),96,112),200);
		anim_idle[3] = new Animation(new SpriteSheet(new Image("Textures/player_idle_3.png"),96,112),200);
		anim_walking[0] = new Animation(new SpriteSheet(new Image("Textures/player_walking_0.png"),96,112),200);
		anim_walking[1] = new Animation(new SpriteSheet(new Image("Textures/player_walking_1.png"),96,112),200);
		anim_walking[2] = new Animation(new SpriteSheet(new Image("Textures/player_walking_2.png"),96,112),200);
		anim_walking[3] = new Animation(new SpriteSheet(new Image("Textures/player_walking_3.png"),96,112),200);
		attribute_name = "EnemyName";
	}
	
	public void attack(){
		
	}
	public void roam(){
		
	}
	public void kill(){
		
	}
}
