package gamePackage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.paint.Color;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame
{
	Image treeimg;
	String str;
	GameLevel gamelevel;
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	public Game(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		//Called once, upon starting the program
		astar.surprise();
		
		treeimg = new Image("Textures/tree1.png");
		buttons.add(new Button(gc.getWidth()/2, gc.getHeight()/3, 100, 20, "Start Game", "StartButton"));
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
		for(Button button : buttons){
			g.setColor(org.newdawn.slick.Color.red);
			g.fillRect(button.posX, button.posY, button.width, button.height);
			g.setColor(org.newdawn.slick.Color.blue);
			g.drawString(button.text, button.posX, button.posY);
		}
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
