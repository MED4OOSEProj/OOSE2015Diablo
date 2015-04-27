package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

public class Item extends GameObject {
	
	public Image sprite_thumbnail;
	public int attribute_value;
	public String attribute_description;
	public Sound sound_drop;

	public void generateItem(int posX, int posY){
		Random rnd = new Random();
		int n = rnd.nextInt(2);
		if (n == 0){
			// create armor
				//Armor.generateArmor();
		} else if (n == 1){
			// create potion
				//Potion.generatePotion();
		} else if (n == 2){
			// create weapon
			Weapon.generateWeapon();
		}
	}
}
