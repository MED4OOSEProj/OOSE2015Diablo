package gamePackage;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.FontUtils;

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
	TerrainType[] terrainTypes = new TerrainType[6];
	GameLevel[] gameLevels = new GameLevel[3];
	
	public Game(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//Called once, upon starting the program
		
		//Creates game levels
		gameLevels[0] = new GameLevel();
		
		//Creates different types of terrains
		terrainTypes[0] = new TerrainType("Wall",1,"The wall blocks your path", new Image("Textures/tile_ground.png"),true);
		terrainTypes[1] = new TerrainType("Wood floorboards",1,"The boards creak a little", new Image("Textures/tile_ground.png"),false);
		
		
		buttonFont = new TrueTypeFont(awtFont, false);
		treeimg = new Image("Textures/tree1.png");
		goToMainMenu(gc);
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		//Makes changes to models, is called once per something milliseconds
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//Is called every time a render has completed, so as fast as the hardware can do it.
		//g.drawString("Hello World!", 250+(System.currentTimeMillis()/10)%50, 200);
		treeimg.draw(250+(System.currentTimeMillis()/10)%50, 200);
		
		// change loadMenu to mainMenu to see the mainMenu buttons. Next step?> If statement
		// menuId == 0 gives mainMenu, menuId == 1, gives loadMenu.
		if (menuId == 0){
			for(Button button : buttons){
				g.setColor(org.newdawn.slick.Color.darkGray);
				g.fillRect(button.posX, button.posY, button.width, button.height);
				g.setColor(org.newdawn.slick.Color.black);
				FontUtils.drawCenter(buttonFont, button.text, button.posX, button.posY, button.width);
			}
		} else if (menuId == 1){
			for(Button button : buttons){
				g.setColor(org.newdawn.slick.Color.darkGray);
				g.fillRect(button.posX, button.posY, button.width, button.height);
				g.setColor(org.newdawn.slick.Color.black);
				FontUtils.drawCenter(buttonFont, button.text, button.posX, button.posY, button.width);
			}
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
			appgc.setDisplayMode(640, 480, false);
			appgc.setAlwaysRender(true);
			appgc.start();
			
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void mousePressed(int button,int x,int y){
		
		if(button == 0){
			for(Button guibutton : buttons){
				if(guibutton.posX <= x && guibutton.posX + guibutton.width >= x && guibutton.posY <= y && guibutton.posY+guibutton.height >= y){
					buttonClicked(guibutton);
				}
			}
		}
	}
	
	public void buttonClicked(Button clickedbutton){
		System.out.println(clickedbutton.text + " clicked!");
	}
}
