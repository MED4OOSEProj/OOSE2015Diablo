package gamePackage;
import gamePackage.Character.Action;

import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.FontUtils;
import org.newdawn.slick.util.pathfinding.Path;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

public class Game extends BasicGame
{
	Image treeimg;
	String str;
	GameLevel gamelevel;
	ArrayList<Button> buttons = new ArrayList<Button>();
	int menuId = 0;
	Font awtFont = new Font("Times New Roman", Font.TRUETYPE_FONT, 18);
	TrueTypeFont buttonFont;
	static TerrainType[] terrainTypes = new TerrainType[6];
	public static GameLevel[] gameLevels = new GameLevel[3];
	public static int currentLevel = 0;
	int windowWidth;
	int windowHeight;
	Player player;
	Enemy enemy1;

	
	public Game(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//Called once, upon starting the program
		windowWidth = gc.getWidth();
		windowHeight = gc.getHeight();
		//gc.setTargetFrameRate(300);
		
		player = new Player();
		enemy1 = new Enemy(3,1);
		
		//Creates different types of terrains
		terrainTypes[0] = new TerrainType("Wood floorboards",1,"The boards creak a little", new Image("Textures/tile_ground.png"),false);
		terrainTypes[1] = new TerrainType("Stone Wall",1,"The wall blocks your path", new Image("Textures/tile_wall.png"),true);
		
		
		//Creates game levels
		gameLevels[0] = new GameLevel();
		
		
		
		buttonFont = new TrueTypeFont(awtFont, false);
		treeimg = new Image("Textures/tree1.png");
		goToMainMenu(gc);
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		//Makes changes to models, is called once per i milliseconds
		//updates stuff
		player.getCurrentAnimation().update(i);
		enemy1.getCurrentAnimation().update(i);
		
		//System.out.println("delta: "+i);
		
		if(menuId == 2){
			//movement
			player.move(i);
			enemy1.move(i);
		}
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//Is called every time a render has completed, so as fast as the hardware can do it.
		//g.drawString("Hello World!", 250+(System.currentTimeMillis()/10)%50, 200);
		//treeimg.draw(250+(System.currentTimeMillis()/10)%50, 200);
		
		
		
		// change loadMenu to mainMenu to see the mainMenu buttons. Next step?> If statement
		// menuId == 0 gives mainMenu, menuId == 1, gives loadMenu, menuId == 2 shows the game
		if (menuId == 0){
			for(Button button : buttons){
				g.setColor(org.newdawn.slick.Color.darkGray);
				g.fillRect(button.posX, button.posY, button.width, button.height);
				g.setColor(org.newdawn.slick.Color.black);
				FontUtils.drawCenter(buttonFont, button.text, button.posX, button.posY, button.width);
			}
		} 
		else if (menuId == 1){
			for(Button button : buttons){
				g.setColor(org.newdawn.slick.Color.darkGray);
				g.fillRect(button.posX, button.posY, button.width, button.height);
				g.setColor(org.newdawn.slick.Color.black);
				FontUtils.drawCenter(buttonFont, button.text, button.posX, button.posY, button.width);
			}
		}
		else if (menuId == 2){
			
			
			//tiles
			for(int x = 0; x < gameLevels[currentLevel].getWidthInTiles(); x++){
				for(int y = 0; y < gameLevels[currentLevel].getHeightInTiles(); y++){
					terrainTypes[gameLevels[currentLevel].grid_terrainIDs[x][y]].terrainImage.draw(Math.round(x*80+y*80-(player.position_y*80+player.position_x*80)+windowWidth/2-80),
																								   Math.round(y*40-x*40+(player.position_y*40-player.position_x*40)+windowHeight/2-40));
				}
			}
			//draw the player
			player.getCurrentAnimation().draw(windowWidth/2-45, windowHeight/2-86);
			//draw an enemy
			enemy1.getCurrentAnimation().draw(Math.round(enemy1.position_x*80+enemy1.position_y*80-(player.position_y*80+player.position_x*80)+windowWidth/2-80)+35,
											  Math.round(enemy1.position_x*40-enemy1.position_y*40+(player.position_y*40-player.position_x*40)+windowHeight/2-40)-45);
		}
	}
	
	public void goToMainMenu(GameContainer gc){
		buttons = new ArrayList<Button>();
		buttons.add(new Button(gc.getWidth()/2-50, gc.getHeight()/3, 100, 20, "Start Game", "StartButton"));
		buttons.add(new Button(gc.getWidth()/2-50, gc.getHeight()/2, 100, 20, "Load Game", "LoadButton"));
		buttons.add(new Button(gc.getWidth()/2-50, gc.getHeight()/3*2, 100, 20, "Quit Game", "QuitButton"));
	}
	
	public void goToLoadMenu(GameContainer gc){
		buttons = new ArrayList<Button>();
		buttons.add(new Button(gc.getWidth()/3-50, gc.getHeight()/3, 100, 20, "Slot 1", "LoadSlot1"));
		buttons.add(new Button(gc.getWidth()/2-50, gc.getHeight()/3, 100, 20, "Slot 2", "LoadSlot2"));
		buttons.add(new Button(gc.getWidth()/3*2-50, gc.getHeight()/3, 100, 20, "Slot 3", "LoadSlot3"));
	}
	
	
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Simple Slick Game"));
			appgc.setDisplayMode(1024, 768, false);
			appgc.setAlwaysRender(true);
			appgc.start();
			
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void mousePressed(int button,int x,int y){
		
		//left click
		if(button == 0){
			//click a tile
			if(menuId == 2){
				float shifted_x = x-windowWidth/2+80;
				float shifted_y = y-windowHeight/2+40;
				System.out.println("player x:"+player.position_x+" player y:"+player.position_y);
				//playerpos_x = (int)((shifted_x/80+shifted_y/40-1)/2+playerpos_x);
				//playerpos_y = (int)((shifted_x/80-shifted_y/40+1)/2+playerpos_y);
				enemy1.moveTo(Math.round(enemy1.position_x),Math.round(enemy1.position_y),(int)((shifted_x/80+shifted_y/40-1)/2+player.position_x),(int)((shifted_x/80-shifted_y/40+1)/2+player.position_y));
				
				player.moveTo(Math.round(player.position_x),Math.round(player.position_y),(int)((shifted_x/80+shifted_y/40-1)/2+player.position_x),(int)((shifted_x/80-shifted_y/40+1)/2+player.position_y));
			}
			
			
			for(Button guibutton : buttons){
				if(menuId == 0 && guibutton.posX <= x && guibutton.posX + guibutton.width >= x && guibutton.posY <= y && guibutton.posY+guibutton.height >= y){
					buttonClicked(guibutton);
				}
			}
			
			
		}
	}
	
	public void buttonClicked(Button clickedbutton){
		System.out.println(clickedbutton.text + " clicked!");
		switch(clickedbutton.id){
		case "StartButton": menuId = 2;
			break;
		case "LoadButton": menuId = 1;
			break;
		case "QuitButton": //quit?
			break;
		}
	}
}
