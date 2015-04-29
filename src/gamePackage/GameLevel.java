package gamePackage;

import java.util.ArrayList;

import org.newdawn.slick.Sound;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class GameLevel implements TileBasedMap {
	public ArrayList<GameObject>[][] grid_objects;
	public ArrayList<Character> charactersInLevel = new ArrayList<Character>();
	public int[][] grid_terrainIDs;
	public Sound sound_track;
	int levelWidth = 10;
	int levelHeight = 10;
	
	// Stops the function from making infinite paths. (not incredibly important for such a small game)
    private static final int maxPathLength = 100;

    /**
    private static final int startX = 1;
    private static final int startY = 1;

    private static final int goalX = 1;
    private static final int goalY = 6;
	*/
    
	public GameLevel(){
		createRandomMap();
	}

	public Path getPath(int startX, int startY, int goalX, int goalY){
		
		AStarPathFinder pathFinder = new AStarPathFinder(this, maxPathLength, false);
        Path path = pathFinder.findPath(null, startX, startY, goalX, goalY);
        return path;
	}

	
	public void createRandomMap(){
		//temporary map generation
		grid_terrainIDs = new int[][]{
		        {1,1,1,1,1,1,1,1,1,1},
		        {1,0,0,0,0,0,1,1,1,1},
		        {1,0,1,1,1,0,1,1,1,1},
		        {1,0,1,1,1,0,0,0,1,1},
		        {1,0,0,0,1,1,1,0,1,1},
		        {1,1,1,0,1,1,1,0,0,0},
		        {1,0,1,0,0,0,0,0,1,0},
		        {1,0,1,1,1,1,1,1,1,0},
		        {1,0,0,0,0,0,0,0,0,0},
		        {1,1,1,1,1,1,1,1,1,0}
		    };
	}
	
	public boolean colliding(Character character, float x, float y){
		for(Character othercharacter : charactersInLevel){
			//Exclude the colliding character
			if(othercharacter != character){
				//Check if x and y is within another character's collision size
				if(Math.abs(othercharacter.position_x - x) < othercharacter.collision_size &&
				   Math.abs(othercharacter.position_y - y) < othercharacter.collision_size){
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public boolean blocked(PathFindingContext arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		//System.out.println("x:"+arg2+"y:"+arg1+" terraintype: "+grid_terrainIDs[arg2][arg1]);
		if(arg1 < 0 || arg2 < 0 || arg1 > levelHeight-1 || arg2 > levelWidth-1)
			return true;
		
		return Game.terrainTypes[grid_terrainIDs[arg2][arg1]].blocksPath;
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
