package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

class Item extends GameObject {
	
	Image thumbnail;
	int attribute_value;
	String attribute_description;
	Sound sound_drop;

	// generate a random item of the types armor, potion, weapon or jewelry.
	Item generateItem() throws SlickException{
		Item returnitem = null;
		Random rnd = new Random();
		int n = rnd.nextInt(4);
		if (n == 0){
			// create armor
			returnitem = new Armor();
		} 
		else if (n == 1){
			// create potion
			returnitem = new Potion();
		} 
		else if (n == 2){
			// create weapon
			returnitem = new Weapon();
		}
		else if (n == 3){
			// create jewelry
			returnitem = new Jewelry();
		}
		return returnitem;
	}
}
