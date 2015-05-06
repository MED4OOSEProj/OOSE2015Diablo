package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Potion extends Item{
	
	int attribute_amount;
	Image[] potionIcons;
	
	Potion () throws SlickException{
		generatePotion();
	}

	void generatePotion() throws SlickException{
		Random rnd = new Random();
		
		// Is the potion of medium healing or greater healing. 
		boolean temp = rnd.nextBoolean(); 
		if (temp == true){
			// Should set the player's health to max: Game.player.attribute_health_current = the line below. 
			attribute_amount = Game.player.attribute_health_max; 
			attribute_description = "Greater Potion of Healing";
			thumbnail = new Image("Textures/potion_full_health_potion.png");
		} else if (temp == false){
			// Should raise the player's health by half the total value. 
			// NOTE not allowed to exceed player_health_max
			attribute_amount = Game.player.attribute_health_max/2;
			attribute_description = "Medium Potion of Healing";
			thumbnail = new Image("Textures/potion_health_potion_minor.png");
		} 

	}
}
