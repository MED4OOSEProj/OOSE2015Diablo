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
	ArrayList<Button> mainMenu = new ArrayList<Button>();
	ArrayList<Button> loadMenu = new ArrayList<Button>();
	int menuId = 0;
	Font awtFont = new Font("Times New Roman", Font.TRUETYPE_FONT, 18);
	TrueTypeFont buttonFont;
	
	public Game(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//Called once, upon starting the program
		astar.surprise();
		
		buttonFont = new TrueTypeFont(awtFont, false);
		treeimg = new Image("Textures/tree1.png");
		mainMenu.add(new Button(gc.getWidth()/2-50, gc.getHeight()/3, 100, 20, "Start Game", "StartButton"));
		mainMenu.add(new Button(gc.getWidth()/2-50, gc.getHeight()/2, 100, 20, "Load Game", "LoadButton"));
		mainMenu.add(new Button(gc.getWidth()/2-50, gc.getHeight()/3*2, 100, 20, "Quit Game", "QuitButton"));
		loadMenu.add(new Button(gc.getWidth()/3-50, gc.getHeight()/3, 100, 20, "Slot 1", "LoadSlot1"));
		loadMenu.add(new Button(gc.getWidth()/2-50, gc.getHeight()/3, 100, 20, "Slot 2", "LoadSlot2"));
		loadMenu.add(new Button(gc.getWidth()/3*2-50, gc.getHeight()/3, 100, 20, "Slot 3", "LoadSlot3"));
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
					for(Button button : mainMenu){
						g.setColor(org.newdawn.slick.Color.darkGray);
						g.fillRect(button.posX, button.posY, button.width, button.height);
						g.setColor(org.newdawn.slick.Color.black);
						FontUtils.drawCenter(buttonFont, button.text, button.posX, button.posY, button.width);
					}
				} else if (menuId == 1){
					for(Button button : loadMenu){
						g.setColor(org.newdawn.slick.Color.darkGray);
						g.fillRect(button.posX, button.posY, button.width, button.height);
						g.setColor(org.newdawn.slick.Color.black);
						FontUtils.drawCenter(buttonFont, button.text, button.posX, button.posY, button.width);
					}
				} 
		
		//
		
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
}
