package gamePackage;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

//implements tilebasedmap in order for the slick pathfinding library to use the map.
public class GameLevel implements TileBasedMap {
	ArrayList<GameObject> objectsInLevel = new ArrayList<GameObject>();
	int[][] grid_terrainIDs;
	Sound sound_track;
	int levelWidth;
	int levelHeight;
	Mover moverChar;
	static int[][] checklist = new int[25][25];
	static int[][] gridTest = new int[25][25];
	// Stops the function from making infinite paths.
    private static final int maxPathLength = 100;
    
	public GameLevel(){
		createRandomMap();
	}

	/**
	 * Gets a path from the start position to the goal position
	 * @param mover The mover object moving along the path (a Character is a Mover)
	 * @return The path to the destination goalX and goalY. Null if no path is found.
	 */
	public Path getPath(Mover mover, int startX, int startY, int goalX, int goalY){
		
		AStarPathFinder pathFinder = new AStarPathFinder(this, maxPathLength, false);
        Path path = pathFinder.findPath(mover, startX, startY, goalX, goalY);
        return path;
	}

	/**
	 * Fills the grid_terrainIDs array with 0's and 1's to make a new random map
	 */
	public void createRandomMap(){
		//random map generation
		MapBlock map = new MapBlock(); 
		levelWidth = 25;
		levelHeight = 25;
		// reset checklist
				for(int i = 0; i < levelWidth; i++)
					for(int j = 0; j < levelHeight; j++)
						checklist[i][j] = 0;
		//Fill the map with stone. 
		for(int i = 0; i < levelWidth; i++){
			for(int j = 0; j < levelHeight; j++)
				gridTest[i][j] = 1;
		}
		map.generateMapBlock(levelWidth/2, levelHeight/2,1);
		grid_terrainIDs = gridTest;
	}
	
	/**
	 * Fills map with enemies.
	 */
	public void createEnemies() throws SlickException{
		int temp = 0;
		for(int i = 0; i < levelWidth; i++)
			for(int j = 0; j < levelHeight; j++)
				if (!Game.terrainTypes[grid_terrainIDs[j][i]].blocksPath){
					temp++;
					if (temp%10 == 0 && !(Math.abs(j-(int)Game.player.position_x) < 5 && Math.abs(i-(int)Game.player.position_y) < 5)){
						//place an enemy every 10 unblocked tile, but not within 5 tiles of the player
						objectsInLevel.add(new Enemy(i,j,("Goatman "+(temp+1))));
					}
				}
	}
	
	/**
	 * Checks if the given position will make the character collide with another object
	 * @param character The character checking if there will be a collision with it
	 * @param x The x tile position to be checked
	 * @param y The y tile position to be checked
	 * @return The object which the character will collide with. Null if no collision will occur.
	 */
	public GameObject collidingObject(Character character, float x, float y){
		for(GameObject colliderObject : objectsInLevel){
			//Exclude the colliding character
			if(colliderObject != character){
				
				//Check if x and y is within another character's collision size
				if(Math.abs(colliderObject.position_x - x) < colliderObject.collision_size + character.collision_size &&
				   Math.abs(colliderObject.position_y - y) < colliderObject.collision_size + character.collision_size){
					if(colliderObject instanceof Character && (((Character)colliderObject).dead || ((Character)colliderObject).dying)){
						continue;
					}
					return colliderObject;
				}
			}
		}
		
		return null;
	}
	
	
	/**
	 * Checks if the given tile is passable by a mover.
	 * @param arg0 The context in which the passability is being used. Has a mover object. If null, uses moverChar in GameLevel as mover.
	 * @param arg1 The x tile position to be checked
	 * @param arg2 The y tile position to be checked
	 * @return True if the tile is blocked, false if it is passable.
	 */
	@Override
	public boolean blocked(PathFindingContext arg0, int arg1, int arg2) {
		//Because pathfindingcontext cannot be instantiated, a workaround to call this method with a specific character is implemented
		//If arg0 is null, use the moverChar as mover instead.
		Mover mover;
		if(arg0 != null)
			mover = arg0.getMover();
		else
			mover = moverChar;
		//If the tile is out of bounds, the path is blocked
		if(arg1 < 0 || arg2 < 0 || arg1 > levelHeight-1 || arg2 > levelWidth-1)
			return true;
		
		//if an enemy occupies the space, and the player is not attacking it, count the tile as blocked, in order to move around it.
		for(GameObject gameobj : Game.gameLevel.objectsInLevel){
			if(gameobj instanceof Character && gameobj != mover){
				if(!((Character)gameobj).dead && !((Character)gameobj).dying){
					if(Math.round(gameobj.position_x) == arg1 && Math.round(gameobj.position_y) == arg2){
						if(((Character)(mover)).attackTarget != gameobj){
							return true;
						}
					}
				}
			}
		}
		
		//grid_terrainIDs is [y][x], which is why arg2 and arg1 are swapped when returning the blocked boolean
		return Game.terrainTypes[grid_terrainIDs[arg2][arg1]].blocksPath;
	}
	
	/**
	 * Checks if the given tile is passable by a character.
	 * @param character The character which will move to the tile.
	 * @param end_x The x tile position to be checked
	 * @param end_y The y tile position to be checked
	 * @return True if the tile is blocked, false if it is passable.
	 */
	public boolean blockedChar(Character character, int end_x, int end_y) {
		moverChar = character;
		return blocked(null, end_x, end_y);
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	@Override
	public int getHeightInTiles() {
		// TODO Auto-generated method stub
		return levelHeight;
	}

	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return levelWidth;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	
}
