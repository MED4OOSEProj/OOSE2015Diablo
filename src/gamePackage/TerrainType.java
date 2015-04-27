package gamePackage;

import org.newdawn.slick.Image;

public class TerrainType {
	String name;
	String description;
	int movementcost;
	Image terrainImage;
	boolean movementBlocked;
	
	public TerrainType(String name, int movementcost, String description, Image terrainImage, boolean movementBlocked){
		this.name = name;
		this.description = description;
		this.movementcost = movementcost;
		this.terrainImage = terrainImage;
		this.movementBlocked = movementBlocked;
	}
}
