package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Item extends GameObject {
	
	public Image thumbnail;
	public int attribute_value;
	public String attribute_description;
	public Sound sound_drop;

	// consider adding a variable to define what type of item to generate and spawn
	public Item generateItem() throws SlickException{
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
