package gamePackage;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class GameLevel implements TileBasedMap {
	public ArrayList<GameObject> objectsInLevel = new ArrayList<GameObject>();
	public int[][] grid_terrainIDs;
	public Sound sound_track;
	int levelWidth;
	int levelHeight;
	Mover moverChar;
	public static int[][] checklist = new int[25][25];
	public static int[][] gridTest = new int[25][25];
	// Stops the function from making infinite paths. (not incredibly important for such a small game)
    private static final int maxPathLength = 100;

    /**
    private static final int startX = 1;
    private static final int startY = 1;

    private static final int goalX = 1;
    private static final int goalY = 6;
     *  
	*/
    
	public GameLevel(){
		createRandomMap();
	}

	public Path getPath(Mover mover, int startX, int startY, int goalX, int goalY){
		
		AStarPathFinder pathFinder = new AStarPathFinder(this, maxPathLength, false);
        Path path = pathFinder.findPath(mover, startX, startY, goalX, goalY);
        return path;
	}

	
	public void createRandomMap(){
		//random map generation
		MapBlock map = new MapBlock(); 
		levelWidth = 25;
		levelHeight = 25;
		//Fill the map with stone. 
		for(int i = 0; i < levelWidth; i++){
			for(int j = 0; j < levelHeight; j++)
				gridTest[i][j] = 1;
		}
		map.generateMapBlock(13, 13,1);
		grid_terrainIDs = gridTest;
				/*new int[][]{
		        {1,1,1,1,1,1,1,1,1,1},
		        {1,0,0,0,0,0,1,1,1,1},
		        {1,0,0,0,0,0,1,1,1,1},
		        {1,0,0,0,1,0,0,0,1,1},
		        {1,0,0,0,1,1,1,0,1,1},
		        {1,1,1,0,1,1,1,0,0,0},
		        {1,0,1,0,0,0,0,0,1,0},
		        {1,0,1,1,1,1,1,1,1,0},
		        {1,0,0,0,0,0,0,0,0,0},
		        {1,1,1,1,1,1,1,1,1,0}
		    };*/
	}
	public void createEnemies() throws SlickException{
		int temp = 0;
		for(int i = 0; i < levelWidth; i++)
			for(int j = 0; j < levelHeight; j++)
				if (checklist[j][i] == 1){
					temp++;
					if (temp%10 == 0){
						// System.out.println(j + " " + i);
						objectsInLevel.add(new Enemy(j,i));
					}
				}	
	}
	
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
	
	@Override
	public boolean blocked(PathFindingContext arg0, int arg1, int arg2) {
		Mover mover;
		if(arg0 != null)
			mover = arg0.getMover();
		else
			mover = moverChar;
		// TODO Auto-generated method stub
		//System.out.println("x:"+arg2+"y:"+arg1+" terraintype: "+grid_terrainIDs[arg2][arg1]);
		if(arg1 < 0 || arg2 < 0 || arg1 > levelHeight-1 || arg2 > levelWidth-1)
			return true;
		
		//if an enemy occupies the space, and the player is not attacking it, count the tile as blocked, in order to move around it.
		for(GameObject gameobj : Game.gameLevels[Game.currentLevel].objectsInLevel){
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
		
		return Game.terrainTypes[grid_terrainIDs[arg2][arg1]].blocksPath;
	}
	
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
