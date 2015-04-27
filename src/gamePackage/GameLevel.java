package gamePackage;

import java.util.ArrayList;

import org.newdawn.slick.Sound;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class GameLevel implements TileBasedMap {
	public ArrayList<GameObject>[][] grid_objects;
	public int[][] grid_terrainIDs;
	public Sound sound_track;
	int levelWidth = 10;
	int levelHeight = 10;
	
    private static final int maxPathLength = 100;

    private static final int startX = 1;
    private static final int startY = 1;

    private static final int goalX = 1;
    private static final int goalY = 6;
	
	public GameLevel(){
		createRandomMap();
		
	}

	public Path getPath(TileBasedMap map){
        AStarPathFinder pathFinder = new AStarPathFinder(map, maxPathLength, false);
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
	
	@Override
	public boolean blocked(PathFindingContext arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return Game.terrainTypes[grid_terrainIDs[arg1][arg2]].blocksPath;
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
