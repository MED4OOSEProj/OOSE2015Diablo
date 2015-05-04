package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Armor extends Item {
	
	public int attribute_damage_reduction;
	public int attribute_durability_max;
	public int attribute_durability_current;
	public int attribute_location;
	
	// Don't add more names to the arrays without changing the if-statements.
	// MUST have an EVEN number of names in the arrays.
	public String[] prefix = new String[]{"Brunhilde's ","Unbreakable ","Used ","Tattered ","Dirty ","Mighty ","Shining ","Glittering ","Cursed ","Holy "}; 
	
	// armortype
	public String[] armorType = new String[]{"Breast Plate","Cape","Chain Mail","Cloak","Full Plate Mail","Gothic Plate","Rags","Ring Mail","Robe","Studded Leather Armor"};
	
	// 0-5 are high damage reduction, 6-8 are high durability, 9 is the supposed to be the best 
	public String[] suffix = new String[]{" of Infinite Durability"," of Protection"," of Fortitude"," of The Lady's Blessing"," of The Everliving"," of Eternity"," of Unparralled Fortitude"," of the Unbreakable Vow"," of Diamond Endurance"," of Godskin"};

	
	public Armor() throws SlickException{
		generateArmor();
	}

	public void generateArmor() throws SlickException{
		Random rnd = new Random();
		
		// How much does the armor reduce incoming damage?
		attribute_damage_reduction = rnd.nextInt(5)+2;
		// Where should it be placed on the body?
		attribute_location = 0;
		// How many hits can the armor take before it breaks?
		attribute_durability_max = rnd.nextInt(10)*2;
		attribute_durability_current = attribute_durability_max;
		
		// Where is it placed in the inventory?
		// NOTICE the attribute location system is not IMPLEMENTED
		// Diablo 1 only has armor and helmet .. we can implement this if we want to. 
		// armor.attribute_location = 5;
		
		// determine what name the armor should have: 
		// BEWARE, here be magic numbers:
		String a = prefix[rnd.nextInt(prefix.length)];
		
		// Match icon to armor: 
		int temp = rnd.nextInt(armorType.length);
		String b = armorType[temp];
		
		switch (temp){
		case 0:
			thumbnail = new Image("Textures/armor_Breast_Plate.png");
			break;
		case 1:
			thumbnail = new Image("Textures/armor_Cape.png");
			break;
		case 2:
			thumbnail = new Image("Textures/armor_Chain_Mail.png");
			break;
		case 3:
			thumbnail = new Image("Textures/armor_Cloak.png");
			break;
		case 4:
			thumbnail = new Image("Textures/armor_Full_Plate_Mail.png");
			break;
		case 5:
			thumbnail = new Image("Textures/armor_Gothic_plate.png");
			break;
		case 6:
			thumbnail = new Image("Textures/armor_Rags.png");
			break;
		case 7:
			thumbnail = new Image("Textures/armor_Ring_Mail.png");
			break;
		case 8:
			thumbnail = new Image("Textures/armor_Robe.png");
			break;
		case 9:
			thumbnail = new Image("Textures/armor_Studded_Leather_Armor.png");
			break;
		}
			
		String c;
		if ( attribute_damage_reduction >= 6 && attribute_durability_max >= 18){
			//  Is the armor the most powerful possible? Add " of Godskin" 
			c = suffix[9];
		} else if ( attribute_damage_reduction >= 4 ){
			//nextInt(4) because of four damage reduction related names on 0,1,2,3
			c = suffix[rnd.nextInt(4)];
		} else if ( attribute_durability_max >= 10){
			//nextInt(5)+4 because of five durability related names on 4,5,6,7,8
			c = suffix[rnd.nextInt(5)+4];
		} else {
			c = "";
		}
		
		// Combine strings to make the name: 
		attribute_description = a + b + c;
	}
}
