package gamePackage;

import gamePackage.Character.Action;

import java.awt.Font;
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
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.FontUtils;

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
	static int windowWidth;
	static int windowHeight;
	static Player player;
	int mouse_position_x;
	int mouse_position_y;
	GameObject currentHoveredObject;
	Image menu2_overlay_left;
	Image menu2_overlay_right;
	Image menu2_overlay_extender;
	Image menu2_overlay_life;
	Image menu2_overlay_underlife;
	Image image_inventory;
	Image image_charsheet;
	Image image_mainmenu;
	float yscale = 0.1f;
	boolean menu_characterSheet = false;
	boolean menu_inventory =false;
	int tilewidth;
	int tileheight;
	
	
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
		gameLevels[0] = new GameLevel();
		
		gameLevels[0].objectsInLevel.add(player);
		gameLevels[0].objectsInLevel.add(new Enemy(3,1));
		gameLevels[0].objectsInLevel.add(new Enemy(2,1));
		
		buttonFont = new TrueTypeFont(awtFont, false);
		treeimg = new Image("Textures/tree1.png");
		
		/*
		// Testing generation of weapon:
		Weapon wep1 = Weapon.generateWeapon();
		System.out.println(wep1.attribute_description);
		System.out.println(wep1.attribute_attackdmg);
		System.out.println(wep1.attribute_attackspeed);
		System.out.println(wep1.attribute_durability_max);
		
		// Testing the generateArmor: 
		Armor arm1 = Armor.generateArmor();	
		System.out.println(arm1.attribute_description);	
		System.out.println(arm1.attribute_damage_reduction);
		System.out.println(arm1.attribute_durability_max);	
		
		// Testing the generateArmor: 
		Jewelry jew1 = Jewelry.generateJewelry();
		System.out.println(jew1.attribute_description);	
		System.out.println(jew1.attribute_strength);	
		System.out.println(jew1.attribute_dexterity);	
		System.out.println(jew1.attribute_vitality);	
		System.out.println(jew1.attribute_damage_reduction);	
		
		// Testing the generatePotion: 
		// PROBLEM WITH POTION. attribute_amount = 0
		Potion pot1 = Potion.generatePotion();
		System.out.println(pot1.attribute_description);	
		System.out.println(pot1.attribute_amount);*/
		goToMainMenu();
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		//Makes changes to models, is called once per i milliseconds
		//updates stuff
		
		//System.out.println("delta: "+i);
		
		if(menuId == 2){
			//update objects
			for(GameObject gameobj : gameLevels[currentLevel].objectsInLevel){
				if(gameobj instanceof Character){
					if(!((Character)gameobj).dead && !((Character)gameobj).dying){
						if(gameobj instanceof Enemy){
							((Enemy) gameobj).roam();
						}
						((Character) gameobj).move(i);
						gameobj.getCurrentAnimation().update(i);
					}
					else if(((Character)gameobj).dying){
						gameobj.getCurrentAnimation().update(i);
						gameobj.getCurrentAnimation().setLooping(false);
						if(gameobj.getCurrentAnimation().getFrame() == gameobj.getCurrentAnimation().getFrameCount()-1){
							
							((Character) gameobj).dying = false;
							((Character) gameobj).dead = true;
						}
					}
					((Character) gameobj).calculateScreenPos();
				}
				
			}
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
			image_mainmenu.drawCentered(windowWidth/2, windowHeight/2);
			/*  buttontesting
			for(Button button : buttons){
				g.setColor(org.newdawn.slick.Color.darkGray);
				g.fillRect(button.posX, button.posY, button.width, button.height);
				g.setColor(org.newdawn.slick.Color.black);
				FontUtils.drawCenter(buttonFont, button.text, button.posX, button.posY, button.width);
			}
			*/
		} 
		else if (menuId == 2){
			
			int xpos;
			int ypos;
			//tiles
			for(int x = 0; x < gameLevels[currentLevel].getWidthInTiles(); x++){
				for(int y = 0; y < gameLevels[currentLevel].getHeightInTiles(); y++){
					xpos = Math.round(x*80+y*80-(player.position_y*80+player.position_x*80)+windowWidth/2-80);
					ypos = Math.round(y*40-x*40+(player.position_y*40-player.position_x*40)+windowHeight/2-40);
					if(xpos >= -tilewidth && xpos < windowWidth && ypos > -tileheight && ypos < windowHeight-menu2_overlay_right.getHeight()+11)
					terrainTypes[gameLevels[currentLevel].grid_terrainIDs[x][y]].terrainImage.draw(xpos,ypos);
				}
			}
			//sorts the objects by their height position on the screen, so that the objects in front are drawn last
			Collections.sort(gameLevels[currentLevel].objectsInLevel);
			GameObject tempHoveredObject = null;
			for(GameObject gameobj : gameLevels[currentLevel].objectsInLevel){
				
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
					if(gameobj.screenPosition_x >= -tilewidth && gameobj.screenPosition_x < windowWidth && gameobj.screenPosition_y > -tileheight && gameobj.screenPosition_y < windowHeight-menu2_overlay_right.getHeight()+11){
						//draw red edge when hovered
						if(!((Enemy)gameobj).dead && !((Enemy)gameobj).dying)
							if(isObjectHovered(gameobj)){
								gameobj.getCurrentAnimation().drawFlash(gameobj.screenPosition_x,gameobj.screenPosition_y,gameobj.getCurrentAnimation().getWidth()*1.05f, gameobj.getCurrentAnimation().getHeight()*1.04f, Color.red);
								tempHoveredObject = gameobj;
							}
									  
						//draw
						gameobj.getCurrentAnimation().draw(gameobj.screenPosition_x+3,gameobj.screenPosition_y+3);
					}
				}
				
				
			}
			currentHoveredObject = tempHoveredObject;
			
			//interface
			if(menu_inventory){
				image_inventory.draw(windowWidth-image_inventory.getWidth(),windowHeight-menu2_overlay_right.getHeight()-image_inventory.getHeight()+11);
			}
			if(menu_characterSheet){
				image_charsheet.draw(0,windowHeight-menu2_overlay_right.getHeight()-image_charsheet.getHeight()+11);
			}
			
			yscale = 1-((float)(player.attribute_health_current)/(float)(player.attribute_health_max));
			menu2_overlay_underlife.draw(93,windowHeight-menu2_overlay_left.getHeight());
			menu2_overlay_life.draw(93,windowHeight-menu2_overlay_left.getHeight()+menu2_overlay_life.getHeight()*yscale,
									93+menu2_overlay_life.getWidth(),windowHeight-menu2_overlay_left.getHeight()+menu2_overlay_life.getHeight(),
									0,menu2_overlay_life.getHeight()*yscale,
									menu2_overlay_life.getWidth(),menu2_overlay_life.getHeight());
			menu2_overlay_left.draw(0,windowHeight-menu2_overlay_left.getHeight());
			for(int x = 0; x <= ((windowWidth-menu2_overlay_right.getWidth()-menu2_overlay_left.getWidth())/menu2_overlay_extender.getWidth()); x++){
				menu2_overlay_extender.draw(menu2_overlay_left.getWidth()+x*menu2_overlay_extender.getWidth(),windowHeight-menu2_overlay_left.getHeight());
			}
			menu2_overlay_right.draw(windowWidth-menu2_overlay_right.getWidth(),windowHeight-menu2_overlay_right.getHeight());
			
			
			
		}
	}
	
	public boolean isObjectHovered(GameObject obj){
		if(Math.abs(obj.screenPosition_x - 5 + obj.getCurrentAnimation().getCurrentFrame().getWidth()/2-mouse_position_x) < obj.pixelWidth &&
		   Math.abs(obj.screenPosition_y + obj.getCurrentAnimation().getCurrentFrame().getHeight()/2-mouse_position_y) < obj.pixelHeight){
			return true;
		}
		return false;
	}
	
	public void goToMainMenu(){
		menuId = 0;
		buttons = new ArrayList<Button>();
		buttons.add(new Button(windowWidth/2-image_mainmenu.getWidth()/2+165, windowHeight/2-image_mainmenu.getHeight()/2+254, 310, 45, "", "StartButton"));
		buttons.add(new Button(windowWidth/2-image_mainmenu.getWidth()/2+165, windowHeight/2-image_mainmenu.getHeight()/2+310, 310, 45, "", "QuitButton"));
	}
	
	public void goToGame(){
		menuId = 2;
		buttons = new ArrayList<Button>();
		buttons.add(new Button(8, windowHeight-menu2_overlay_left.getHeight()+22, 74, 20, "", "charsheet"));
		buttons.add(new Button(8, windowHeight-menu2_overlay_left.getHeight()+48, 74, 20, "", "inventory"));
		buttons.add(new Button(8, windowHeight-menu2_overlay_left.getHeight()+88, 74, 20, "", "map"));
		buttons.add(new Button(8, windowHeight-menu2_overlay_left.getHeight()+114, 74, 20, "", "menu"));
	}
	
	
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Diablo"));
			appgc.setDisplayMode(1024, 768, false);
			appgc.setAlwaysRender(true);
			appgc.start();
			
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy){
		mouse_position_x = newx;
		mouse_position_y = newy;
	}
	
	public void mousePressed(int button,int x,int y){
		
		//left click
		if(button == 0){
			//click a tile
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
				float shifted_x = x-windowWidth/2+80;
				float shifted_y = y-windowHeight/2+40;
				System.out.println("player x:"+player.position_x+" player y:"+player.position_y);
				//playerpos_x = (int)((shifted_x/80+shifted_y/40-1)/2+playerpos_x);
				//playerpos_y = (int)((shifted_x/80-shifted_y/40+1)/2+playerpos_y);
				//enemy1.moveTo(Math.round(enemy1.position_x),Math.round(enemy1.position_y),(int)((shifted_x/80+shifted_y/40-1)/2+player.position_x),(int)((shifted_x/80-shifted_y/40+1)/2+player.position_y));
				if(currentHoveredObject != null){
					if(currentHoveredObject instanceof Enemy){
						System.out.println("calling attack move");
						player.attackMove((Character)currentHoveredObject);
					}
					if(currentHoveredObject instanceof Item){
						
					}
				}
				else player.moveTo(Math.round(player.position_x),Math.round(player.position_y),(int)((shifted_x/80+shifted_y/40-1)/2+player.position_x),(int)((shifted_x/80-shifted_y/40+1)/2+player.position_y), false);
			
				
				
			}
			
			for(Button guibutton : buttons){
				if(guibutton.posX <= x && guibutton.posX + guibutton.width >= x && guibutton.posY <= y && guibutton.posY+guibutton.height >= y){
					buttonClicked(guibutton);
				}
			}
			
			
			
			
			
		}
	}
	
	public void buttonClicked(Button clickedbutton){
		System.out.println(clickedbutton.text + " clicked!");
		switch(clickedbutton.id){
		case "StartButton": 
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
		case "QuitButton": //quit?
			System.exit(0);
			break;
		}
	}
	
	public void keyPressed(int key, char c){
		System.out.println("key pressed:"+key);
		if(menuId == 2){
			switch(key){
				case 1:  goToMainMenu();
				break;
				case 2:  player.drinkPotion(0);
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
				case 15:  //display map
				break;
				case 23:  menu_inventory = !menu_inventory;
				break;
				case 46:  menu_characterSheet = !menu_characterSheet;
				break;
			}
		}
		else if(key == 1){
			//escape pressed during menu
		}
	}
	
	
}
