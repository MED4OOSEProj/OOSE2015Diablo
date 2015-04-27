package gamePackage;

import java.util.ArrayList;

import org.newdawn.slick.Sound;

public class GameLevel {
	public GameLevel(){
		createRandomMap();
	}
	
	public void createRandomMap(){
		
	}
	
	public ArrayList<GameObject>[][] grid_objects;
	public int[][] grid_terrainIDs;
	public Sound sound_track;
}
