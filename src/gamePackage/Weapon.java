package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;

public class Weapon extends Item {
	
	public int attribute_attackdmg;
	public float attribute_attackspeed;
	public int attribute_durability_max;
	public int attribute_durability_current;
	public boolean attribute_isRanged;
	
	
	// Don't add more names to the arrays without changing the if-statements.
	// MUST have an EVEN number of names in the arrays.
	public static String[] prefix = new String[]{"Brunhilde's ","The Powerful ","Unyielding ","The Monsterbane ","Death's ","Skullsplitter ","Spinecracking ","The Seagold ","Cursed ","Holy "}; 
	
	// weapontype 0-4 are melee, 5-9 are ranged
	public static String[] weaponType = new String[]{"Axe","Sword","Mace","Pike","Spear","Javelin","Crossbow","Bow","Longbow","Throwing Knife"};
	
	// 0-2 are high dmg, 3-5 are fast weapons, 6-8 are durable, 10 is the most powerful EVeR! 
	
	public static String[] suffix = new String[]{" of Mightiness"," of Slaying"," of Great Strength"," of Swiftslaying"," of Quicksilver"," of Quickness"," of Unreliable Power"," of the Unbreakable Vow"," of Diamond Endurance"," of Godslaying"};
	
	// Arbejder på ikonerne lige nu 
	// public static Image[] weaponIcons = new Image[]{"Textures/tree1.png", "Textures/tree1.png"};
	
	public Weapon (){
	
	}
	
	
	public static void generateWeapon(){
		Weapon weapon = new Weapon();
		Random rnd = new Random();
		
		// how much damage per attack: (modifies the characters attackdamage)
		weapon.attribute_attackdmg = rnd.nextInt(10); 
		// How many attacks per second (modifies the characters attackspeed)
		weapon.attribute_attackspeed = rnd.nextFloat()*0.5f; 
		// How many hits you can make with the weapon before it breaks: 
		weapon.attribute_durability_max = rnd.nextInt(10)*2; 
		weapon.attribute_durability_current = weapon.attribute_durability_max; 
		weapon.attribute_isRanged = rnd.nextBoolean(); 
		
		// determine what name the weapon should have: 
		// BEWARE, here be magic numbers:
		String a = prefix[rnd.nextInt(prefix.length)];
		String b;
		if (weapon.attribute_isRanged == true)
			b = weaponType[rnd.nextInt(weaponType.length/2)+weaponType.length/2];
		else 
			b = weaponType[rnd.nextInt(weaponType.length/2)];
		
		String c;
		if ( weapon.attribute_attackdmg >= 9 && weapon.attribute_attackspeed >= 0.40f){
			//  Is the weapon the most powerful possible? Add " of Godslaying" 
			c = suffix[9];
		} else if ( weapon.attribute_attackdmg >= 6 ){
			//nextInt(3) because of three dmg related names on 0,1,2
			c = suffix[rnd.nextInt(3)];
		} else if ( weapon.attribute_attackspeed >= 0.25f){
			//nextInt(3)+3 because of three speed related names on 3,4,5
			c = suffix[rnd.nextInt(3)+3];
		} else if ( weapon.attribute_durability_max >= 12){
			//nextInt(3)+6 because of durability related names on 6,7,8
			c = suffix[rnd.nextInt(3)+6];
		} else {
			c = "";
		}
		
		weapon.attribute_description = a + b + c;
	}
}
