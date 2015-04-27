package gamePackage;

import java.util.ArrayList;

import org.newdawn.slick.Sound;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class GameLevel implements TileBasedMap {
	public GameLevel(){
		createRandomMap();
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
	
	public ArrayList<GameObject>[][] grid_objects;
	public int[][] grid_terrainIDs;
	public Sound sound_track;
	
	@Override
	public boolean blocked(PathFindingContext ctx, int y, int x) {
		// TODO Auto-generated method stub
		return grid_terrainIDs[y][x] != 0;
	}

	@Override
	public float getCost(PathFindingContext arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 1.0f;
	}

	@Override
	public int getHeightInTiles() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getWidthInTiles() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void pathFinderVisited(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
