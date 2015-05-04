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
	private float aggroArea = 3f;
	
	public Enemy(float tile_x, float tile_y) throws SlickException{
		frameHeight = 112;
		position_x = tile_x;
		position_y = tile_y;
		sprite_idle = new SpriteSheet(new Image("Textures/goatman_idle_0.png"),128,frameHeight);
		anim_idle[0] = new Animation(sprite_idle,200);
		anim_idle[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_1.png"),128,frameHeight),200);
		anim_idle[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_2.png"),128,frameHeight),200);
		anim_idle[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_3.png"),128,frameHeight),200);
		anim_walking[0] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_0.png"),128,frameHeight),200);
		anim_walking[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_1.png"),128,frameHeight),200);
		anim_walking[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_2.png"),128,frameHeight),200);
		anim_walking[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_3.png"),128,frameHeight),200);
		anim_attacking[0] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_0.png"),128,frameHeight),150);
		anim_attacking[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_1.png"),128,frameHeight),150);
		anim_attacking[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_2.png"),128,frameHeight),150);
		anim_attacking[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_3.png"),128,frameHeight),150);
		attribute_name = "EnemyName";
		pixelWidth = 30;
		pixelHeight = 55;
		pixelTranslation_x = 27;
		pixelTranslation_y = -45;
		screenPosTranslationWhenAttacking_x = 0;
	}
	
	public void attack(){
		
	}
	public void roam(){
		if(Math.abs(Game.player.position_x - position_x) <= aggroArea && Math.abs(Game.player.position_y - position_y) <= aggroArea){
			//pull a monster, and the monsters around that monster
			attackMove(Game.player);
			for(GameObject gameobj : Game.gameLevels[Game.currentLevel].objectsInLevel){
				if(gameobj instanceof Enemy){
					if((Math.abs(gameobj.position_x - position_x) <= aggroArea && (Math.abs(gameobj.position_y - position_y) <= aggroArea))){
						((Enemy) gameobj).attackMove(Game.player);
					}
				}
			}
		}
		else{
			
		}
			
	}
	public void kill(){
		
	}
}
