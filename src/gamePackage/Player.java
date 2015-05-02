package gamePackage;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Character{
	public Item[][] inventory;
	public Item[] equipment;
	public int gold;
	public int attribute_level;
	public int attribute_experience;
	public int attribute_strength;
	public int attribute_dexterity;
	public int attribute_vitality;
	public int attributepoints;
	public GameLevel level;

	public Player() throws SlickException{
		//Sets the player attributes
			frameWidth = 96;
			frameHeight = 112;
			sprite_idle = new SpriteSheet(new Image("Textures/player_idle_0.png"),frameWidth,frameHeight);
			anim_idle[0] = new Animation(sprite_idle,200);
			anim_idle[1] = new Animation(new SpriteSheet(new Image("Textures/player_idle_1.png"),frameWidth,frameHeight),200);
			anim_idle[2] = new Animation(new SpriteSheet(new Image("Textures/player_idle_2.png"),frameWidth,frameHeight),200);
			anim_idle[3] = new Animation(new SpriteSheet(new Image("Textures/player_idle_3.png"),frameWidth,frameHeight),200);
			anim_attacking[0] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_0.png"),128,frameHeight),150);
			anim_attacking[1] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_1.png"),128,frameHeight),150);
			anim_attacking[2] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_2.png"),128,frameHeight),150);
			anim_attacking[3] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_3.png"),128,frameHeight),150);
			anim_walking[0] = new Animation(new SpriteSheet(new Image("Textures/player_walking_0.png"),frameWidth,frameHeight),200);
			anim_walking[1] = new Animation(new SpriteSheet(new Image("Textures/player_walking_1.png"),frameWidth,frameHeight),200);
			anim_walking[2] = new Animation(new SpriteSheet(new Image("Textures/player_walking_2.png"),frameWidth,frameHeight),200);
			anim_walking[3] = new Animation(new SpriteSheet(new Image("Textures/player_walking_3.png"),frameWidth,frameHeight),200);
			position_x = 2;
			position_y = 4;
			attribute_name = "PlayerName";
			pixelTranslation_x = 32;
			pixelTranslation_y = -48;
			lastAttackTime = (float)(System.nanoTime()/1000000);
	}
	
	public void moveAndPickUp(Item target){
		//Player.equipment[EquipmentType.FEET] = 
	}
	
	public void moveAndAttack(Enemy target){
		
	}
	
	public void move(int x, int y){
		
	}
	
	public void dropItem(Item item){
		
	}
	
	public void kill(){
		
	}
}
