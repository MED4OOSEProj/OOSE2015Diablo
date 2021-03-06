package gamePackage;

import gamePackage.Character.Action;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame
{
	private GameObject currentHoveredObject;
	static GameLevel gameLevel;
	static TerrainType[] terrainTypes = new TerrainType[6];
	static Player player;
	private int currentLevel = 1;
	static int windowWidth;
	static int windowHeight;
	int menuId = 0;
	int mouse_position_x;
	int mouse_position_y;
	int tilewidth;
	int tileheight;
	int roamtimer = 1000;
	int enemyCount = 0;
	int numberOfLevels = 5;
	float health_percentage;
	Image menu2_overlay_left;
	Image menu2_overlay_right;
	Image menu2_overlay_extender;
	Image menu2_overlay_life;
	Image menu2_overlay_underlife;
	Image image_inventory;
	Image image_charsheet;
	Image image_mainmenu;
	boolean menu_characterSheet = false;
	boolean menu_inventory =false;
	boolean gameWon = false;
	boolean gameLost = false;
	boolean roamLock = false;
	boolean hoverObjectLock = false;
	boolean newGamePressed = false;
	boolean completedLevel = false;
	ArrayList<Button> buttons = new ArrayList<Button>();
	Button nextLevelButton;
	Button restartButton;
	Button charsheetButton;
	Button inventoryButton;
	Button mapButton;
	Button menuButton;
	
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Diablo"));
			//appgc.setDisplayMode(appgc.getScreenWidth(), appgc.getScreenHeight(), true);
			appgc.setDisplayMode(800, 600, false);
			appgc.setAlwaysRender(true);
			appgc.start();
			
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
	Game(String gamename)
	{
		super(gamename);
	}

	
	@Override
	public void init(GameContainer gc) throws SlickException {
		//Called once, upon starting the program
		
		
		//Saves the window sizes, so that the methods that need the screen size do not need the gamecontainer
		windowWidth = gc.getWidth();
		windowHeight = gc.getHeight();
		
		
		gc.setMouseCursor(new Image("Textures/cursor.png"), 0, 0);
		player = new Player();
		
		
		//Creates different types of terrains
		terrainTypes[0] = new TerrainType("Wood floorboards",1,"The boards creak a little", new Image("Textures/tile_ground.png"),false);
		terrainTypes[1] = new TerrainType("Stone Wall",1,"The wall blocks your path", new Image("Textures/tile_wall.png"),true);
		
		
		tilewidth = terrainTypes[0].terrainImage.getWidth();
		tileheight = terrainTypes[0].terrainImage.getHeight();
		
		
		menu2_overlay_left = new Image("Textures/menu2_overlay_left.png");
		menu2_overlay_right = new Image("Textures/menu2_overlay_right.png");
		menu2_overlay_extender = new Image("Textures/menu2_overlay_extender.png");
		menu2_overlay_life = new Image("Textures/menu2_overlay_life.png");
		menu2_overlay_underlife = new Image("Textures/menu2_overlay_underlife.png");
		image_inventory = new Image("Textures/inventory.png");
		image_charsheet = new Image("Textures/charsheet.png");
		image_mainmenu = new Image("Textures/mainmenu.jpg");
		
		
		//Creates game levels
		gameLevel = new GameLevel();
		gameLevel.objectsInLevel.add(player);
		gameLevel.createEnemies(); 
		
		
		nextLevelButton = new Button(windowWidth-75, windowHeight-menu2_overlay_left.getHeight()-9, 74, 20, "Continue", "nextlevel");
		restartButton = new Button(windowWidth/2-37, windowHeight-menu2_overlay_left.getHeight()-9, 74, 20, "Restart", "restart");
		charsheetButton = new Button(8, windowHeight-menu2_overlay_left.getHeight()+22, 74, 20, "", "charsheet");
		inventoryButton = new Button(8, windowHeight-menu2_overlay_left.getHeight()+48, 74, 20, "", "inventory");
		mapButton = new Button(8, windowHeight-menu2_overlay_left.getHeight()+88, 74, 20, "", "map");
		menuButton = new Button(8, windowHeight-menu2_overlay_left.getHeight()+114, 74, 20, "", "menu");
		
		
		goToMainMenu();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		//Makes changes to models, is called once per i milliseconds
		//updates stuff
		
		
		//If the game is not supposed to be paused
		if(menuId == 2 && !gameWon && !gameLost){
			enemyCount = 0;
			
		
			//run through each object in the level
			for(GameObject gameobj : gameLevel.objectsInLevel){
				if(gameobj instanceof Character){
					//if the object is a character
					if(!((Character)gameobj).dead && !((Character)gameobj).dying){
						//and is not dead
						if(gameobj instanceof Enemy){
							//make enemies roam every roamtimer milliseconds
							if(!roamLock && System.currentTimeMillis()%roamtimer < 50){
								((Enemy) gameobj).roam();
							}
							
			
							enemyCount++;
						}
						//call the character's move method to move it along its path, possibly colliding and attacking others
						((Character) gameobj).move(i);
						//make sure the animation is synchronized correctly, using time between each update (i)
						gameobj.getCurrentAnimation().update(i);
					}
					else if(((Character)gameobj).dying){
						//if the character is dying, make sure the animation only runs till its end
						gameobj.getCurrentAnimation().update(i);
						gameobj.getCurrentAnimation().setLooping(false);
						if(gameobj.getCurrentAnimation().getFrame() == gameobj.getCurrentAnimation().getFrameCount()-1){
							//and when the animation is over, tag the character as dead
							((Character) gameobj).dying = false;
							((Character) gameobj).dead = true;
							if(gameobj instanceof Player){
								//if the dead character is the player, lose the game
								loseGame();
							}
						}
					}
					//after moving, calculate the new position in pixel coordinates for drawing later
					((Character) gameobj).calculateScreenPos();
				}
				
			}
			//after running through each object, the number of living enemies have been counted
			if(enemyCount == 0 && !completedLevel){
				//and the level can be completed, giving access to the next
					completeLevel();
			}
			//roamtimer mechanics. Turn off the lock at the end of the timer, and turn it on in the beginning of the timer.
			if(System.currentTimeMillis()%roamtimer > roamtimer-50){
				if(roamLock){
					roamLock = false;
				}
			}
			else roamLock = true;
		}
		
	}

	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		//Is called every time a render has completed, so as fast as the hardware can do it.

		
		// menuId == 0 gives mainMenu, menuId == 1 gives loadMenu (to be implemented in the future), menuId == 2 shows the game
		if (menuId == 0){
			image_mainmenu.drawCentered(windowWidth/2, windowHeight/2);
		} 
		else if (menuId == 2){
		
			
			int xpos;
			int ypos;
			//draws the tiles
			for(int x = 0; x < gameLevel.getWidthInTiles(); x++){
				for(int y = 0; y < gameLevel.getHeightInTiles(); y++){
					//the position of each tile is calculated by the x and y tile id equally affecting its position: (x*(tilewidth/2)+y*(tilewidth/2))
					//then translated according to the position of the player, so that the tiles move when the player is supposed to be moving, creating the illusion of the player moving: -(player.position_y*(tilewidth/2)+player.position_x*(tilewidth/2))
					//and finally the tiles are translated to the center of the screen: +windowWidth/2-tilewidth/2
					xpos = Math.round(x*(tilewidth/2)+y*(tilewidth/2)-(player.position_y*(tilewidth/2)+player.position_x*(tilewidth/2))+windowWidth/2-tilewidth/2);
					ypos = Math.round(y*(tileheight/2)-x*(tileheight/2)+(player.position_y*(tileheight/2)-player.position_x*(tileheight/2))+windowHeight/2-tileheight/2);
					//if the tile is within the visible area, draw it
					if(xpos >= -tilewidth && xpos < windowWidth && ypos > -tileheight && ypos < windowHeight-menu2_overlay_right.getHeight()+11)
					terrainTypes[gameLevel.grid_terrainIDs[x][y]].terrainImage.draw(xpos,ypos);
				}
			}
			//sorts the objects by their height position on the screen, so that the objects in front are drawn last
			Collections.sort(gameLevel.objectsInLevel);
			hoverObjectLock = false;
			currentHoveredObject = null;
			//run through all objects in the level
			for(GameObject gameobj : gameLevel.objectsInLevel){
				//draw the player
				if(gameobj instanceof Player){
					int translate_x = 0;
					//fixing attack frames being 128 pixels wide rather than 96 pixels.
					if(((Player) gameobj).currentAction == Action.ATTACKING || ((Player) gameobj).currentAction == Action.DYING)
						translate_x = ((Player) gameobj).screenPosTranslationWhenAttacking_x;
					gameobj.getCurrentAnimation().draw(windowWidth/2-45 + translate_x, windowHeight/2-86);
				}
				//draw enemies
				if(gameobj instanceof Enemy){
					//only draw if visible
					if(gameobj.screenPosition_x >= -gameobj.getCurrentAnimation().getCurrentFrame().getWidth() && gameobj.screenPosition_x < windowWidth && gameobj.screenPosition_y > -gameobj.getCurrentAnimation().getCurrentFrame().getHeight() && gameobj.screenPosition_y < windowHeight-menu2_overlay_right.getHeight()+11){
						//draw red edge when living enemy is hovered
						if(!((Enemy)gameobj).dead && !((Enemy)gameobj).dying)
							if(!hoverObjectLock && isObjectHovered(gameobj)){
								gameobj.getCurrentAnimation().drawFlash(gameobj.screenPosition_x,gameobj.screenPosition_y,gameobj.getCurrentAnimation().getWidth()*1.05f, gameobj.getCurrentAnimation().getHeight()*1.04f, Color.red);
								//setting the object which is hovered here in the render method is not pretty, but makes sure that the item hovered is synchronized to what the player is seeing
								currentHoveredObject = gameobj;
								hoverObjectLock = true;
							}
			
						
						//draw current enemy's animation frame
						gameobj.getCurrentAnimation().draw(gameobj.screenPosition_x+3,gameobj.screenPosition_y+3);
					}
				}
				
				
			}
			
			
			//Draws the GUI elements on top
			g.drawString("Level: "+currentLevel, windowWidth-90, 10);
			if(completedLevel){
				g.fillRect(nextLevelButton.posX, nextLevelButton.posY, nextLevelButton.width, nextLevelButton.height);
				g.setColor(Color.black);
				g.drawString(nextLevelButton.text, nextLevelButton.posX+4, nextLevelButton.posY+1);
				g.setColor(Color.white);
			}
			else g.drawString("Monsters remaining: "+enemyCount, windowWidth-210, windowHeight-menu2_overlay_right.getHeight()-9);
			
			
			if(menu_inventory){
				image_inventory.draw(windowWidth-image_inventory.getWidth(),windowHeight-menu2_overlay_right.getHeight()-image_inventory.getHeight()+11);
			}
			if(menu_characterSheet){
				image_charsheet.draw(0,windowHeight-menu2_overlay_right.getHeight()-image_charsheet.getHeight()+11);
			}
			
			
			health_percentage = 1-((float)(player.attribute_health_current)/(float)(player.attribute_health_max));
			menu2_overlay_underlife.draw(93,windowHeight-menu2_overlay_left.getHeight());
			menu2_overlay_life.draw(93,windowHeight-menu2_overlay_left.getHeight()+menu2_overlay_life.getHeight()*health_percentage,
									93+menu2_overlay_life.getWidth(),windowHeight-menu2_overlay_left.getHeight()+menu2_overlay_life.getHeight(),
									0,menu2_overlay_life.getHeight()*health_percentage,
									menu2_overlay_life.getWidth(),menu2_overlay_life.getHeight());
			menu2_overlay_left.draw(0,windowHeight-menu2_overlay_left.getHeight());
			for(int x = 0; x <= ((windowWidth-menu2_overlay_right.getWidth()-menu2_overlay_left.getWidth())/menu2_overlay_extender.getWidth()); x++){
				menu2_overlay_extender.draw(menu2_overlay_left.getWidth()+x*menu2_overlay_extender.getWidth(),windowHeight-menu2_overlay_left.getHeight());
			}
			menu2_overlay_right.draw(windowWidth-menu2_overlay_right.getWidth(),windowHeight-menu2_overlay_right.getHeight());
			
			
			if(gameWon || gameLost){
				g.setColor(new Color(0,0,0,0.5f));
				g.fillRect(20, 20, windowWidth-40, windowHeight-40);
				g.setColor(Color.white);
				g.fillRect(restartButton.posX, restartButton.posY, restartButton.width, restartButton.height);
				g.setColor(Color.black);
				g.drawString(restartButton.text, restartButton.posX+4, restartButton.posY+1);
				g.setColor(Color.white);
			}
			if(gameWon){
				g.drawString("You won the game!", windowWidth/2-40, windowHeight/2);
			}
			if(gameLost){
				g.drawString("You died!", windowWidth/2-40, windowHeight/2);
			}
			
			
		}
	}
	
	/**
	 * Checks if an object has the cursor positioned on it
	 * @param obj The object which is being checked
	 * @return True if the cursor is within the object's hitbox defined by its pixelWidth and pixelHeight
	 */
	public boolean isObjectHovered(GameObject obj){
		if(Math.abs(obj.screenPosition_x - 5 + obj.getCurrentAnimation().getCurrentFrame().getWidth()/2-mouse_position_x) < obj.pixelWidth &&
		   Math.abs(obj.screenPosition_y + obj.getCurrentAnimation().getCurrentFrame().getHeight()/2-mouse_position_y) < obj.pixelHeight){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Sets the menuId to 0 and enables the buttons start and quit. The game is paused here.
	 */
	public void goToMainMenu(){
		menuId = 0;
		buttons = new ArrayList<Button>();
		buttons.add(new Button(windowWidth/2-image_mainmenu.getWidth()/2+165, windowHeight/2-image_mainmenu.getHeight()/2+254, 310, 45, "", "StartButton"));
		buttons.add(new Button(windowWidth/2-image_mainmenu.getWidth()/2+165, windowHeight/2-image_mainmenu.getHeight()/2+310, 310, 45, "", "QuitButton"));
	}
	
	
	/**
	 * Sets the menuId to 2 and enables the ingame buttons
	 */
	public void goToGame(){
		menuId = 2;
		buttons = new ArrayList<Button>();
		buttons.add(charsheetButton);
		buttons.add(inventoryButton);
		buttons.add(mapButton);
		buttons.add(menuButton);
		
		
		if(completedLevel){
			buttons.add(nextLevelButton);
		}
	}
	
	
	/**
	 * Enables the button for continuing to the next level
	 */
	public void completeLevel(){
		completedLevel = true;
		buttons = new ArrayList<Button>();
		buttons.add(charsheetButton);
		buttons.add(inventoryButton);
		buttons.add(mapButton);
		buttons.add(menuButton);
		buttons.add(nextLevelButton);
	}
	
	
	/**
	 * Goes to the next level
	 */
	public void nextLevel() throws SlickException{
		if(currentLevel < numberOfLevels){
		player.path = null;
		currentLevel++;
		completedLevel = false;
		buttons = new ArrayList<Button>();
		buttons.add(charsheetButton);
		buttons.add(inventoryButton);
		buttons.add(mapButton);
		buttons.add(menuButton);
		gameLevel = new GameLevel();
		gameLevel.objectsInLevel.add(player);
		// Change player's position in the new map
		player.position_x = (int)(gameLevel.levelWidth/2);
		player.position_y = (int)(gameLevel.levelHeight/2);
		gameLevel.createEnemies();
		}
		else winGame();
	}
	
	
	/**
	 * Ends the game by winning, displays a reset button
	 */
	public void winGame(){
		buttons = new ArrayList<Button>();
		buttons.add(restartButton);
		completedLevel = false;
		gameWon = true;
	}
	
	
	/**
	 * Ends the game by losing, displays a reset button
	 */
	public void loseGame(){
		buttons = new ArrayList<Button>();
		buttons.add(restartButton);
		gameLost = true;
	}
	
	
	/**
	 * Resets everything to start values and goes to the main menu
	 */
	public void restart() throws SlickException{
		player = new Player();
		gameLevel = new GameLevel();
		gameLevel.objectsInLevel.add(player);
		gameLevel.createEnemies(); 
		completedLevel = false;
		menu_characterSheet = false;
		menu_inventory =false;
		roamLock = false;
		hoverObjectLock = false;
		newGamePressed = false;
		completedLevel = false;
		enemyCount = 0;
		gameWon = false;
		gameLost = false;
		currentLevel = 1;
		goToMainMenu();
	}
	
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy){
		mouse_position_x = newx;
		mouse_position_y = newy;
	}
	
	
	public void mousePressed(int button,int x,int y){
	
		
		//left click
		if(button == 0){
		
			
			if(menuId == 2 && menu_inventory && x > windowWidth-image_inventory.getWidth() && y >windowHeight-menu2_overlay_right.getHeight()-image_inventory.getHeight()+11){
				//click inside inventory
			}
			else if (menuId == 2 && menu_characterSheet && x < image_charsheet.getWidth() && y >windowHeight - menu2_overlay_left.getHeight()-image_charsheet.getHeight()+11){
				//click inside charsheet
			}
			else if (menuId == 2 && y >windowHeight - menu2_overlay_left.getHeight()+11){
				//click inside lower interface
				
			}
			else if(menuId ==2){
				float shifted_x = x-windowWidth/2+tilewidth/2;
				float shifted_y = y-windowHeight/2+tileheight/2;
				if(currentHoveredObject != null){
					if(currentHoveredObject instanceof Enemy){
						//click an enemy
						player.attackMove((Character)currentHoveredObject);
					}
					if(currentHoveredObject instanceof Item){
						//click an item
						player.moveAndPickUp((Item)currentHoveredObject);
					}
				}
				//click a tile (reverse engineered draw tile loop in render method)
				else player.moveTo(Math.round(player.position_x),Math.round(player.position_y),(int)((shifted_x/(tilewidth/2)+shifted_y/(tileheight/2)-1)/2+player.position_x),(int)((shifted_x/(tilewidth/2)-shifted_y/(tileheight/2)+1)/2+player.position_y), false);
			
				
			}
		}
	}
	
	
	public void mouseClicked(int button, int x, int y, int clickCount){
		if(button == 0){
			for(Button guibutton : buttons){
				if(guibutton.posX <= x && guibutton.posX + guibutton.width >= x && guibutton.posY <= y && guibutton.posY+guibutton.height >= y){
					try {
						buttonClicked(guibutton);
					} catch (SlickException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	/**
	 * Called when a button is clicked
	 * @param clickedbutton The button being clicked
	 */
	public void buttonClicked(Button clickedbutton) throws SlickException{
		System.out.println(clickedbutton.text + " clicked!");
		switch(clickedbutton.id){
		case "StartButton": 
			newGamePressed = true;
			goToGame();
			break;
		case "charsheet": 
			menu_characterSheet = !menu_characterSheet;
			break;
		case "inventory": 
			menu_inventory = !menu_inventory;
			break;
		case "map": 
			//display map
			break;
		case "menu": 
			goToMainMenu();
			break;
		case "QuitButton":
			System.exit(0);
			break;
		case "nextlevel":
			nextLevel();
			break;
		case "restart":
			restart();
			break;
		}
	}
	
	
	public void keyPressed(int key, char c){
		System.out.println("key pressed:"+key);
		if(menuId == 2 && !gameWon && !gameLost){
			switch(key){
				case 1:  goToMainMenu(); //esc for main menu
				break;
				case 2:  player.drinkPotion(0); //keys 1-8 to drink potions (not implemented yet)
				break;
				case 3:  player.drinkPotion(1);
				break;
				case 4:  player.drinkPotion(2);
				break;
				case 5:  player.drinkPotion(3);
				break;
				case 6:  player.drinkPotion(4);
				break;
				case 7:  player.drinkPotion(5);
				break;
				case 8:  player.drinkPotion(6);
				break;
				case 9:  player.drinkPotion(7);
				break;
				case 15:  //display map using tab
				break;
				case 23:  menu_inventory = !menu_inventory; // i for inventory
				break;
				case 46:  menu_characterSheet = !menu_characterSheet; //c for character sheet
				break;
			}
		}
		else if(key == 1 && !gameWon && !gameLost){
			//escape pressed during menu: go back to the game if it was once started
			if(newGamePressed)
				goToGame();
		}
	}
}

