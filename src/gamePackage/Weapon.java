package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Weapon extends Item {
	
	public int attribute_attackdmg;
	public float attribute_attackspeed;
	public int attribute_durability_max;
	public int attribute_durability_current;
	public int attribute_location;
	public static Image[] weaponIcons;
	
	// Don't add more names to the arrays without changing the if-statements.
	// MUST have an EVEN number of names in the arrays.
	public static String[] prefix = new String[]{"Brunhilde's ","The Powerful ","Unyielding ","The Monsterbane ","Death's ","Skullsplitter ","Spinecracking ","The Seagold ","Cursed ","Holy "}; 
	
	// weapontype, all are melee
	public static String[] weaponType = new String[]{"Axe","Sword","Mace","Club","War Staff","Great Axe","Flail","Warhammer","Falchion","Dagger"};
	
	// 0-2 are high dmg, 3-5 are fast weapons, 6-8 are durable, 10 is the most powerful EVeR! 
	public static String[] suffix = new String[]{" of Mightiness"," of Slaying"," of Great Strength"," of Swiftslaying"," of Quicksilver"," of Quickness"," of Unreliable Power"," of the Unbreakable Vow"," of Diamond Endurance"," of Godslaying"};

	

	public Weapon (){
		try {
	         weaponIcons = new Image[] {
	               new Image("Textures/weapon_Axe.png"),
	               new Image("Textures/weapon_Sword.png"),
	               new Image("Textures/weapon_Mace.png"),
	               new Image("Textures/weapon_Club.png"),
	               new Image("Textures/weapon_War_Staff.png"),
	               new Image("Textures/weapon_Dagger.png"),
	               new Image("Textures/weapon_Falchion.png"),
	               new Image("Textures/weapon_Flail.png"),
	               new Image("Textures/weapon_Great_Axe.png"),
	               new Image("Textures/weapon_War_Hammer.png"),
	               };
	      } catch (SlickException e) {
	    	  e.printStackTrace();
	      }
	}
	
	
	public static Weapon generateWeapon(){
		Weapon weapon = new Weapon();
		Random rnd = new Random();
		
		// Where should it be placed on the body?
		weapon.attribute_location = 5;
		// how much damage per attack: (modifies the character's attackdamage)
		weapon.attribute_attackdmg = rnd.nextInt(10); 
		// How many attacks per second (modifies the character's attackspeed)
		weapon.attribute_attackspeed = rnd.nextFloat()*0.5f; 
		// How many hits you can make with the weapon before it breaks: 
		weapon.attribute_durability_max = rnd.nextInt(10)*2; 
		weapon.attribute_durability_current = weapon.attribute_durability_max; 
		
		// determine what name the weapon should have: 
		// BEWARE, here be magic numbers:
		String a = prefix[rnd.nextInt(prefix.length)];

		
		int temp = rnd.nextInt(weaponType.length);
		String b = weaponType[temp];
		weapon.sprite_thumbnail = weaponIcons[temp];
		
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
		return weapon;
	}
}
