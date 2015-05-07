package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

class Jewelry extends Item {
	int attribute_strength; 
	int attribute_dexterity; 
	int attribute_vitality; 
	int attribute_damage_reduction; 
	int attribute_location;
	
	// Don't add more names to the arrays without changing the if-statements.
	// MUST have an EVEN number of names in the arrays.
	// 0-1 = good, 2-3 = bad, 4-9 = generic
	static String[] prefix = new String[]{"Blessed ","Holy ","Cursed ","Defect ","Enchanted ","Beautiful ","Shining ","Glittering ","Burning ","Magical "}; 
	
	// jewelryType
	static String[] jewelryType = new String[]{"Ring","Amulet"};
	
	// 0-1 are high strength, 2-3 are high dexterity, 4-5 are vitality, 6-7 is high damage reduction, 8 is the worst, 9 is the BEST 
	static String[] suffix = new String[]{" of Strength"," of Might"," of Speed"," of Dexterity"," of Vitality"," of Fortitude"," of Steel Flesh"," of Shielding"," of Crap"," of Godblood"};
		
	Jewelry() throws SlickException{
		// generate a jewelry object on creation
		generateJewelry();
	}


	void generateJewelry() throws SlickException{
		Random rnd = new Random(); 
		// Determine what stats the jewelry should change: 
		boolean strength = rnd.nextBoolean(); 
		boolean dexterity = rnd.nextBoolean();
		boolean vitality = rnd.nextBoolean();
		boolean damage_Reduction = rnd.nextBoolean();
		String a; 
		String b =""; 
		String c; 
		
		if (strength == true){
			attribute_strength = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			attribute_strength = 0; 
		
		if (dexterity == true){
			attribute_dexterity = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			attribute_dexterity = 0; 
		
		if (vitality == true){
			attribute_vitality = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			attribute_vitality = 0; 
		
		if (damage_Reduction == true){
			attribute_damage_reduction = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			attribute_damage_reduction = 0; 
		
		
		// Does the good stats outweigh the bad?
		if (attribute_strength+attribute_dexterity+attribute_vitality+attribute_damage_reduction > 0)
			a = prefix[rnd.nextInt(2)];
		// Or does the bad stats outweigh the good?
		else if (attribute_strength+attribute_dexterity+attribute_vitality+attribute_damage_reduction < 0)
			a = prefix[rnd.nextInt(2)+2];
		else
		// If the item's stats added up are 0 exactly, it gets a generic name:
			a = prefix[rnd.nextInt(6)+4];
		
		// If the item the most effective possible? 
		if (attribute_strength >= 4 && attribute_dexterity >= 4 && attribute_vitality >= 4 && attribute_damage_reduction >= 4) 
			c = suffix[9];
		// Is the item the worst item possible?
		else if (attribute_strength <= -4 && attribute_dexterity <= -4 && attribute_vitality <= -4 && attribute_damage_reduction <= -4) 
			c = suffix[8];
		// Does the item give a lot of strength? OR 
		else if (attribute_strength >= 4)
			c = suffix[rnd.nextInt(2)];
		// Does the item give a lot of dexterity? OR
		else if (attribute_dexterity >= 4)
			c = suffix[rnd.nextInt(2)+2];
		// Does the item give a lot of vitality? OR
		else if (attribute_vitality >= 4)
			c = suffix[rnd.nextInt(2)+4];
		// Does the item give a lot of damage reduction?
		else if (attribute_damage_reduction >= 4)
			c = suffix[rnd.nextInt(2)+6];
		else
		// Or is the item generic and bland?
			c = "";
		
		// A temporary variable so the item kan be matched with the right icon. 
		int temptype = rnd.nextInt(4);
		
		switch(temptype){
		case 0:
			attribute_location = 2;	
			b = jewelryType[0];
			thumbnail = new Image("Textures/jewelry_ring_bleeder.png");
			break;
		case 1:
			attribute_location = 2;	
			b = jewelryType[0];
			thumbnail = new Image("Textures/jewelry_ring_regha.png");
			break;
		case 2:
			attribute_location = 1;	
			b = jewelryType[1];
			thumbnail = new Image("Textures/jewelry_acolytes_amulet.png");
			break;
		case 3:
			attribute_location = 1;	
			b = jewelryType[1];
			thumbnail = new Image("Textures/jewelry_optic_amulet.png");
			break;
		}
		attribute_description = a + b + c;
	}
	
}
