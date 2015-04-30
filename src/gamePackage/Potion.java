package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Potion extends Item{
	
	public int attribute_amount;
	Image[] potionIcons;
	
	public Potion (){
		try {
	         potionIcons = new Image[] {
	               new Image("Textures/potion_full_health_potion.png"),
	               new Image("Textures/potion_health_potion_minor.png"),
	               };
	      } catch (SlickException e) {
	    	  e.printStackTrace();
	      }
	}

	public static Potion generatePotion(){
		Potion potion = new Potion(); 
		Random rnd = new Random();
		
		boolean temp = rnd.nextBoolean(); 
		if (temp == true){
			// 
			potion.attribute_amount = Game.player.attribute_health_max; 
			potion.attribute_description = "Greater Potion of Healing";
		} else if (temp == false){
			potion.attribute_amount = Game.player.attribute_health_max/2;
			potion.attribute_description = "Medium Potion of Healing";
		} 
		
		return potion;
	}
}
