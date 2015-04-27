package gamePackage;

import java.util.ArrayList;

import org.newdawn.slick.Sound;

public class GameLevel {
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
}
