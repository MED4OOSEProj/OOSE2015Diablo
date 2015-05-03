package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Jewelry extends Item {
	
	public int attribute_strength; 
	public int attribute_dexterity; 
	public int attribute_vitality; 
	public int attribute_damage_reduction; 
	public int attribute_location;
	public static Image[] jewelryIcons;
	
	// Don't add more names to the arrays without changing the if-statements.
	// MUST have an EVEN number of names in the arrays.
	// 0-1 = good, 2-3 = bad, 4-9 = generic
	public static String[] prefix = new String[]{"Blessed ","Holy ","Cursed ","Defect ","Enchanted ","Beautiful ","Shining ","Glittering ","Burning ","Magical "}; 
	
	// jewelryType
	public static String[] jewelryType = new String[]{"Ring","Amulet"};
	
	// 0-1 are high strength, 2-3 are high dexterity, 4-5 are vitality, 6-7 is high damage reduction, 8 is the worst, 9 is the BEST 
	public static String[] suffix = new String[]{" of Strength"," of Might"," of Speed"," of Dexterity"," of Vitality"," of Fortitude"," of Steel Flesh"," of Shielding"," of Crap"," of Godblood"};
		
	public Jewelry(){
		try {
	         jewelryIcons = new Image[] {
	        		 // 4 different, 0-1 are rings, 2-3 are amulets. 
	               new Image("Textures/jewelry_ring_bleeder.png"),
	               new Image("Textures/jewelry_ring_regha.png"),
	               new Image("Textures/jewelry_acolytes_amulet.png"),
	               new Image("Textures/jewelry_optic_amulet.png"),
	               };
	      } catch (SlickException e) {
	    	  e.printStackTrace();
	      }
	}


	public static Jewelry generateJewelry(){
		Jewelry jewelry = new Jewelry();
		Random rnd = new Random(); 
		boolean isRing = rnd.nextBoolean();
		boolean strength = rnd.nextBoolean(); 
		boolean dexterity = rnd.nextBoolean();
		boolean vitality = rnd.nextBoolean();
		boolean damage_Reduction = rnd.nextBoolean();
		String a; 
		String b; 
		String c; 
		
		if (strength == true){
			jewelry.attribute_strength = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			jewelry.attribute_strength = 0; 
		
		if (dexterity == true){
			jewelry.attribute_dexterity = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			jewelry.attribute_dexterity = 0; 
		
		if (vitality == true){
			jewelry.attribute_vitality = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			jewelry.attribute_vitality = 0; 
		
		if (damage_Reduction == true){
			jewelry.attribute_damage_reduction = rnd.nextInt(10)-5; // 10 is a PLACEHOLDER, can be negative!
		} else 
			jewelry.attribute_damage_reduction = 0; 
		
		if (jewelry.attribute_strength+jewelry.attribute_dexterity+jewelry.attribute_vitality+jewelry.attribute_damage_reduction > 0)
			a = prefix[rnd.nextInt(2)];
		else if (jewelry.attribute_strength+jewelry.attribute_dexterity+jewelry.attribute_vitality+jewelry.attribute_damage_reduction < 0)
			a = prefix[rnd.nextInt(2)+2];
		else
			a = prefix[rnd.nextInt(6)+4];
		
		if (jewelry.attribute_strength >= 4 && jewelry.attribute_dexterity >= 4 && jewelry.attribute_vitality >= 4 && jewelry.attribute_damage_reduction >= 4) 
			c = suffix[9];
		else if (jewelry.attribute_strength <= -4 && jewelry.attribute_dexterity <= -4 && jewelry.attribute_vitality <= -4 && jewelry.attribute_damage_reduction <= -4) 
			c = suffix[8];
		else if (jewelry.attribute_strength >= 4)
			c = suffix[rnd.nextInt(2)];
		else if (jewelry.attribute_dexterity >= 4)
			c = suffix[rnd.nextInt(2)+2];
		else if (jewelry.attribute_vitality >= 4)
			c = suffix[rnd.nextInt(2)+4];
		else if (jewelry.attribute_damage_reduction >= 4)
			c = suffix[rnd.nextInt(2)+6];
		else
			c = "";
		
		if (isRing == true ){
			// Generate ring
			// Where should it be placed on the body?
			jewelry.attribute_location = 3;		
			// Make it a ring
			b = jewelryType[0]; 
			// Choose an icon from the 4 first
			jewelry.sprite_thumbnail = jewelryIcons[rnd.nextInt(jewelryIcons.length/2)];
		} else {
			// Where should it be placed on the body?
			jewelry.attribute_location = 2;
			// Make it an amulet. 
			b = jewelryType[1];
			// choose an icon from the 4 last
			jewelry.sprite_thumbnail = jewelryIcons[rnd.nextInt((jewelryIcons.length/2))+2];
		}

		
		
		jewelry.attribute_description = a + b + c;
		return jewelry; 
	}
}
