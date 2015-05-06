package gamePackage;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.Path;

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
	int spawnpos_x;
	int spawnpos_y;
	int roamSize;
	int roamIdleTime;
	int roamIdleTimeBase;
	long lastIdleTime;
	
	public Enemy(float tile_x, float tile_y, String name) throws SlickException{
		position_x = tile_x;
		position_y = tile_y;
		spawnpos_x = (int)position_x;
		spawnpos_y = (int)position_y;
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
		anim_dying[1] = new Animation(new SpriteSheet(new Image("Textures/goatman_dying_1.png"),128,112),200);
		anim_dying[2] = new Animation(new SpriteSheet(new Image("Textures/goatman_dying_2.png"),128,112),200);
		anim_dying[3] = new Animation(new SpriteSheet(new Image("Textures/goatman_dying_3.png"),128,112),200);
		attribute_name = name;
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
	
	public void roam(){
			if(Math.abs(Game.player.position_x - position_x) <= aggroArea && Math.abs(Game.player.position_y - position_y) <= aggroArea){
				//if there is a path to the player, move there
				//attackTarget = Game.player;
				//if(isThereAPathTo(Math.round(position_x), Math.round(position_y), Math.round(Game.player.position_x),Math.round(Game.player.position_y))){
					attackMove(Game.player);
					//pull a monster, and the monsters around that monster
					if(!areaPulled){
						for(GameObject gameobj : Game.gameLevel.objectsInLevel){
							if(gameobj instanceof Enemy){
								if((Math.abs(gameobj.position_x - position_x) <= aggroArea && (Math.abs(gameobj.position_y - position_y) <= aggroArea))){
									((Enemy) gameobj).attackMove(Game.player);
								}
							}
						}
						areaPulled = true;
					}
				//}
			}
			if(currentAction == Action.IDLE && System.currentTimeMillis()-lastIdleTime > roamIdleTime){
				lastIdleTime = System.currentTimeMillis();
				roamIdleTime = (int)(roamIdleTimeBase*Math.random());
				if(roamArea.size() >0){
					Vector2 movelocation = roamArea.get((int)(Math.random()*roamArea.size()));
					moveTo(Math.round(position_x), Math.round(position_y), (int)movelocation.getX(), (int)movelocation.getY(), false);
				}
			}
			
			
			
	}
	
	void createRoamArea(){
		for(int x = spawnpos_x; x < spawnpos_x+roamSize; x++){
			for(int y = spawnpos_y; y < spawnpos_y+roamSize; y++){
				if(x >= 0 && y >= 0 && x < Game.gameLevel.getWidthInTiles() && y < Game.gameLevel.getHeightInTiles()){
					if(!Game.terrainTypes[Game.gameLevel.grid_terrainIDs[y][x]].blocksPath){
						roamArea.add(new Vector2(x,y));
					}
				}
			}
		}
	}
}
