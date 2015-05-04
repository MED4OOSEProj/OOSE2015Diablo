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
	
	// Don't add more names to the arrays without changing the if-statements.
	// MUST have an EVEN number of names in the arrays.
	// 0-1 = good, 2-3 = bad, 4-9 = generic
	public static String[] prefix = new String[]{"Blessed ","Holy ","Cursed ","Defect ","Enchanted ","Beautiful ","Shining ","Glittering ","Burning ","Magical "}; 
	
	// jewelryType
	public static String[] jewelryType = new String[]{"Ring","Amulet"};
	
	// 0-1 are high strength, 2-3 are high dexterity, 4-5 are vitality, 6-7 is high damage reduction, 8 is the worst, 9 is the BEST 
	public static String[] suffix = new String[]{" of Strength"," of Might"," of Speed"," of Dexterity"," of Vitality"," of Fortitude"," of Steel Flesh"," of Shielding"," of Crap"," of Godblood"};
		
	public Jewelry() throws SlickException{
		generateJewelry();
	}


	public void generateJewelry() throws SlickException{
		Random rnd = new Random(); 
		boolean isRing = rnd.nextBoolean();
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
		
		if (attribute_strength+attribute_dexterity+attribute_vitality+attribute_damage_reduction > 0)
			a = prefix[rnd.nextInt(2)];
		else if (attribute_strength+attribute_dexterity+attribute_vitality+attribute_damage_reduction < 0)
			a = prefix[rnd.nextInt(2)+2];
		else
			a = prefix[rnd.nextInt(6)+4];
		
		if (attribute_strength >= 4 && attribute_dexterity >= 4 && attribute_vitality >= 4 && attribute_damage_reduction >= 4) 
			c = suffix[9];
		else if (attribute_strength <= -4 && attribute_dexterity <= -4 && attribute_vitality <= -4 && attribute_damage_reduction <= -4) 
			c = suffix[8];
		else if (attribute_strength >= 4)
			c = suffix[rnd.nextInt(2)];
		else if (attribute_dexterity >= 4)
			c = suffix[rnd.nextInt(2)+2];
		else if (attribute_vitality >= 4)
			c = suffix[rnd.nextInt(2)+4];
		else if (attribute_damage_reduction >= 4)
			c = suffix[rnd.nextInt(2)+6];
		else
			c = "";
		
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
