package gamePackage;

import java.util.Random;

public class Weapon extends Item {
	
	public int attribute_attackdmg;
	public float attribute_attackspeed;
	public int attribute_durability_max;
	public int attribute_durability_current;
	public boolean attribute_isRanged;
	
	public static String[] prefix = new String[]{"Brunhilde's ","The Powerful ","Unyielding ","The Monsterbane ","Death's ","Skullsplitter ","Spinecracking ","The Seagold ","Cursed ","Holy "}; 
	
	// weapontype 0-4 are melee, 5-9 are ranged
	public static String[] weaponType = new String[]{"Axe","Sword","Mace","Pike","Spear","Javelin","Crossbow","Bow","Longbow","Throwing Knife"};
	
	// 0-2 are high dmg, 3-5 are fast weapons, 6-8 are durable, 10 is the most powerful EVeR! 
	
	public static String[] suffix = new String[]{" of Mightiness"," of Slaying"," of the Knights Templar"," of Swiftslaying"," of Quicksilver"," of Quickness"," of Unreliable Power"," of the Unbreakable Vow"," of Diamond Endurance"," of Godslaying"};
	
	
	public Weapon (){
	
	}
	
	
	public static void generateWeapon(){
		Weapon weapon = new Weapon();
		Random rnd = new Random();
		
		weapon.attribute_attackdmg = rnd.nextInt(10); 
		weapon.attribute_attackspeed = rnd.nextFloat()*0.5f; 
		weapon.attribute_durability_current = weapon.attribute_durability_max; 
		weapon.attribute_durability_max = rnd.nextInt(10)*2; 
		weapon.attribute_isRanged = rnd.nextBoolean(); 
		weapon.attribute_description = prefix[rnd.nextInt(10)] + weaponType[rnd.nextInt(10)] + suffix[rnd.nextInt(10)];
	}
}
