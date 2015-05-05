package gamePackage;

import java.util.Random;

public class MapBlock {
	int[][] size = new int[][]{
			/*left*/	{1,1,1}, /*Top*/
						{1,0,1},
			/*Bottom*/	{1,1,1}  /*right*/
					};
	Random rnd = new Random();
	boolean topLeft = rnd.nextBoolean(); 
	boolean topRight = rnd.nextBoolean();
	boolean bottomLeft = rnd.nextBoolean();
	boolean bottomRight = rnd.nextBoolean();
	int xCenter, yCenter;
	static int distFromCenterToCenter = 2;
	
	public MapBlock(){
		if(topLeft == false && topRight == false && bottomLeft == false && bottomRight == false){
			topLeft = true; 
			topRight = true; 
			bottomLeft = true; 
			bottomRight = true; 
		}	
		if(topLeft == true){
			size[0][1] = 0; 
		}
		if(topRight == true){
			size[1][2] = 0; 
		}
		if(bottomLeft == true){
			size[1][0] = 0; 
		}
		if(bottomRight == true){
			size[2][1] = 0; 
		}
	}

	public void generateMapBlock(int x, int y, int priorBlock){
		// Make a block
		MapBlock block = new MapBlock();
		// The block's coordinates
		block.xCenter = x; 
		block.yCenter = y;
		
		GameLevel.checklist[x][y] = 1; 
			for(int ki = -1; ki <= 1; ki++)
				for(int kj = -1; kj <= 1; kj++)
						GameLevel.gridTest[x+ki][y+kj] = block.size[ki+1][kj+1];
					
		// Check if there is an opening in topLeft && is the new block outside the map && is there already a block there?
		if (block.topLeft == true && block.xCenter-distFromCenterToCenter > 0 && GameLevel.checklist[x-distFromCenterToCenter][y] == 0) {
			generateMapBlock(x-distFromCenterToCenter,y,0);
			GameLevel.gridTest[x-1][y] = 0;
		}
		// Check if there is an opening in topRight && is the new block outside the map && is there already a block there?
		if (block.topRight == true && block.yCenter+distFromCenterToCenter < 24 && GameLevel.checklist[x][y+distFromCenterToCenter] == 0) {
			generateMapBlock(x,y+distFromCenterToCenter,1);
			GameLevel.gridTest[x][y+1] = 0;
		}
		// Check if there is an opening in bottomLeft && is the new block outside the map && is there already a block there?
		if (block.bottomLeft == true && block.yCenter-distFromCenterToCenter > 0 && GameLevel.checklist[x][y-distFromCenterToCenter] == 0) {
			generateMapBlock(x,y-distFromCenterToCenter,2);
			GameLevel.gridTest[x][y-1] = 0;
		}
		// Check if there is an opening in bottomRight && is the new block outside the map && is there already a block there?
		if (block.bottomRight == true && block.xCenter+distFromCenterToCenter < 24 && GameLevel.checklist[x+distFromCenterToCenter][y] == 0) {
			generateMapBlock(x+distFromCenterToCenter,y,3);
			GameLevel.gridTest[x+1][y] = 0;
		}
		
		switch (priorBlock){
		case 0: 
			bottomRight = true; 
			break;
		case 1: 
			bottomLeft = true; 
			break;
		case 2: 
			topRight = true; 
			break;
		case 3: 
			topLeft = true; 
			break;
		default: 
			break;
		}
		
			
	}
	
}
