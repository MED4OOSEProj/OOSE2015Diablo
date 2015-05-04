package gamePackage;

import java.util.Random;

public class MapBlock {
	//int[][] size = new int[][]{
	///*left*/	{1,1,1,1,1,1,1,1,1}, /*Top*/
	//			{1,0,0,0,0,0,0,0,1},
	//			{1,0,0,0,0,0,0,0,1}, 
	//			{1,0,0,0,0,0,0,0,1},
	//			{1,0,0,0,0,0,0,0,1},
	//			{1,0,0,0,0,0,0,0,1}, 
	//			{1,0,0,0,0,0,0,0,1},
	//			{1,0,0,0,0,0,0,0,1},
	///*Bottom*/	{1,1,1,1,1,1,1,1,1}  /*right*/
	//		};
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
		if(topLeft == true){
//			size[3][0] = 0; 
//			size[4][0] = 0;
			size[1][0] = 0; 
		}
		if(topRight == true){
//			size[8][3] = 0; 
//			size[8][4] = 0;
			size[2][1] = 0; 
		}
		if(bottomLeft == true){
//			size[0][3] = 0; 
//			size[0][4] = 0;
			size[0][1] = 0; 
		}
		if(bottomRight == true){
//			size[3][8] = 0; 
//			size[4][8] = 0;
			size[1][2] = 0; 
		}
	}

	public static void generateMapBlock(int x, int y){
		// Make a block
		MapBlock block = new MapBlock();
		// The block's coordinates
		block.xCenter = x; 
		block.yCenter = y;
		GameLevel.checklist[x][y] = 1; 
			for(int ki = -1; ki < 1; ki++)
				for(int kj = -1; kj < 1; kj++)
						GameLevel.gridTest[x+ki][y+kj] = block.size[ki+1][kj+1];
					
		// Check if there is an opening in topLeft && is the new block outside the map && is there already a block there?
		if (block.topLeft == true && block.yCenter-distFromCenterToCenter > 0 && GameLevel.checklist[x][y-distFromCenterToCenter] == 0) {
			generateMapBlock(x,y-distFromCenterToCenter);
		}
		// Check if there is an opening in topRight && is the new block outside the map && is there already a block there?
		if (block.topRight == true && block.xCenter+distFromCenterToCenter < 24 && GameLevel.checklist[x+distFromCenterToCenter][y] == 0) {
			generateMapBlock(x+distFromCenterToCenter,y);
		}
		// Check if there is an opening in bottomLeft && is the new block outside the map && is there already a block there?
		if (block.bottomLeft == true && block.xCenter-distFromCenterToCenter > 0 && GameLevel.checklist[x-distFromCenterToCenter][y] == 0) {
			generateMapBlock(x-distFromCenterToCenter,y);
		}
		// Check if there is an opening in bottomRight && is the new block outside the map && is there already a block there?
		if (block.bottomRight == true && block.yCenter+distFromCenterToCenter < 24 && GameLevel.checklist[x][y+distFromCenterToCenter] == 0) {
			generateMapBlock(x,y+distFromCenterToCenter);
		}

	}
}
