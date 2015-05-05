package gamePackage;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Enemy extends Character{
	public ArrayList<Item> reward_items = new ArrayList<Item>();
	public ArrayList<Vector2> roamArea = new ArrayList<Vector2>();
	public int reward_gold;
	public int reward_experience;
	public int attributepoints;
	public Armor gear_armor;
	public Weapon gear_weapon;
	public boolean mouse_hovered = true;
	private float aggroArea = 3f;
	boolean areaPulled = false;
	float spawnpos_x;
	float spawnpos_y;
	int roamSize;
	int roamIdleTime;
	int roamIdleTimeBase;
	long lastIdleTime;
	
	public Enemy(float tile_x, float tile_y) throws SlickException{
		position_x = tile_x;
		position_y = tile_y;
		spawnpos_x = position_x;
		spawnpos_y = position_y;
		sprite_idle = new SpriteSheet(new Image("Textures/goatman_idle_0.png"),128,112);
		anim_idle[0] = new Animation(sprite_idle,200);
		anim_idle[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_1.png"),128,112),200);
		anim_idle[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_2.png"),128,112),200);
		anim_idle[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_idle_3.png"),128,112),200);
		anim_walking[0] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_0.png"),128,112),200);
		anim_walking[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_1.png"),128,112),200);
		anim_walking[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_2.png"),128,112),200);
		anim_walking[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_walking_3.png"),128,112),200);
		anim_attacking[0] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_0.png"),128,112),150);
		anim_attacking[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_1.png"),128,112),150);
		anim_attacking[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_2.png"),128,112),150);
		anim_attacking[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_attacking_3.png"),128,112),150);
		anim_dying[0] = new Animation(new SpriteSheet(new Image("Textures/goatman_dying_0.png"),128,112),200);
		anim_dying[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_dying_0.png"),128,112),200);
		anim_dying[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_dying_0.png"),128,112),200);
		anim_dying[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_dying_0.png"),128,112),200);
		attribute_name = "EnemyName";
		pixelWidth = 30;
		pixelHeight = 55;
		pixelTranslation_x = 27;
		pixelTranslation_y = -45;
		screenPosTranslationWhenAttacking_x = 0;
		roamSize = 4;
		attribute_damage = 3;
		roamIdleTimeBase = 3000;
		roamIdleTime = roamIdleTimeBase;
		createRoamArea();
		lastIdleTime = System.currentTimeMillis();
	}
	
	public void attack(){
		
	}
	public void roam(){
			if(Math.abs(Game.player.position_x - position_x) <= aggroArea && Math.abs(Game.player.position_y - position_y) <= aggroArea){
				//pull a monster, and the monsters around that monster
				attackMove(Game.player);
				if(!areaPulled){
					for(GameObject gameobj : Game.gameLevels[Game.currentLevel].objectsInLevel){
						if(gameobj instanceof Enemy){
							if((Math.abs(gameobj.position_x - position_x) <= aggroArea && (Math.abs(gameobj.position_y - position_y) <= aggroArea))){
								((Enemy) gameobj).attackMove(Game.player);
							}
						}
					}
					areaPulled = true;
				}
				
			}
			else if(currentAction == Action.IDLE && System.currentTimeMillis()-lastIdleTime > roamIdleTime){
				lastIdleTime = System.currentTimeMillis();
				roamIdleTime = (int)(roamIdleTimeBase*Math.random());
				if(roamArea.size() >0){
					Vector2 movelocation = roamArea.get((int)(Math.random()*roamArea.size()));
					moveTo(Math.round(position_x), Math.round(position_y), (int)movelocation.getX(), (int)movelocation.getY(), false);
				}
			}
			
	}
	
	void createRoamArea(){
		for(int x = 0; x < roamSize; x++){
			for(int y = 0; y < roamSize; y++){
				if(x >= 0 && y >= 0 && x < Game.gameLevels[Game.currentLevel].getWidthInTiles() && y < Game.gameLevels[Game.currentLevel].getHeightInTiles()){
					if(!Game.terrainTypes[Game.gameLevels[Game.currentLevel].grid_terrainIDs[x][y]].blocksPath){
						roamArea.add(new Vector2(x,y));
					}
				}
			}
		}
	}
}
