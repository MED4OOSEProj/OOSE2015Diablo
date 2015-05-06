package gamePackage;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player extends Character{
	public Item[][] inventory = new Item[6][4];
	public Item[] equipment = new Item[4];
	public int gold;
	public int attribute_level;
	public int attribute_experience;
	public int attribute_strength;
	public int attribute_dexterity;
	public int attribute_vitality;
	public int attributepoints;

	public Player() throws SlickException{
		//Sets the player attributes
			sprite_idle = new SpriteSheet(new Image("Textures/player_idle_0.png"),96,112);
			anim_idle[0] = new Animation(sprite_idle,200);
			anim_idle[1] = new Animation(new SpriteSheet(new Image("Textures/player_idle_1.png"),96,112),200);
			anim_idle[2] = new Animation(new SpriteSheet(new Image("Textures/player_idle_2.png"),96,112),200);
			anim_idle[3] = new Animation(new SpriteSheet(new Image("Textures/player_idle_3.png"),96,112),200);
			anim_attacking[0] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_0.png"),128,112),150);
			anim_attacking[1] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_1.png"),128,112),150);
			anim_attacking[2] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_2.png"),128,112),150);
			anim_attacking[3] = new Animation(new SpriteSheet(new Image("Textures/player_attacking_3.png"),128,112),150);
			anim_walking[0] = new Animation(new SpriteSheet(new Image("Textures/player_walking_0.png"),96,112),200);
			anim_walking[1] = new Animation(new SpriteSheet(new Image("Textures/player_walking_1.png"),96,112),200);
			anim_walking[2] = new Animation(new SpriteSheet(new Image("Textures/player_walking_2.png"),96,112),200);
			anim_walking[3] = new Animation(new SpriteSheet(new Image("Textures/player_walking_3.png"),96,112),200);
			anim_dying[0] = new Animation(new SpriteSheet(new Image("Textures/player_dying_0.png"),128,112),200);
			anim_dying[1] = new Animation(new SpriteSheet(new Image("Textures/player_dying_1.png"),128,112),200);
			anim_dying[2] = new Animation(new SpriteSheet(new Image("Textures/player_dying_2.png"),128,112),200);
			anim_dying[3] = new Animation(new SpriteSheet(new Image("Textures/player_dying_3.png"),128,112),200);
			position_x = 12;
			position_y = 12;
			attribute_name = "PlayerName";
			pixelTranslation_x = 32;
			pixelTranslation_y = -48;
			screenPosTranslationWhenAttacking_x = -16;
			attribute_strength = 10;
			attribute_dexterity = 10;
			attribute_vitality = 1000;
			
			equipment[EquipmentType.WEAPON] = new Weapon();
			calculateStats();
			attribute_health_current = attribute_health_max;
	}
	
	public void calculateStats(){
		attribute_health_max = attribute_vitality*5;
		attribute_damage = ((Weapon)equipment[EquipmentType.WEAPON]).attribute_attackdmg +(int)(attribute_strength/4);
		attribute_attackSpeed = ((Weapon)equipment[EquipmentType.WEAPON]).attribute_attackspeed;
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
	
	public void drinkPotion(int slotID){
		
	}
}
