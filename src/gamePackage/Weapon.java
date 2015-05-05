package gamePackage;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Weapon extends Item {
	
	public int attribute_attackdmg;
	public int attribute_attackspeed;
	public int attribute_durability_max;
	public int attribute_durability_current;
	public int attribute_location;
	
	// Don't add more names to the arrays without changing the if-statements.
	// MUST have an EVEN number of names in the arrays.
	public String[] prefix = new String[]{"Brunhilde's ","The Powerful ","Unyielding ","The Monsterbane ","Death's ","Skullsplitter ","Spinecracking ","The Seagold ","Cursed ","Holy "}; 
	
	// weapontype, all are melee
	public String[] weaponType = new String[]{"Axe","Sword","Mace","Club","War Staff","Great Axe","Flail","Warhammer","Falchion","Dagger"};
	
	// 0-2 are high dmg, 3-5 are fast weapons, 6-8 are durable, 10 is the most powerful EVeR! 
	public String[] suffix = new String[]{" of Mightiness"," of Slaying"," of Great Strength"," of Swiftslaying"," of Quicksilver"," of Quickness"," of Unreliable Power"," of the Unbreakable Vow"," of Diamond Endurance"," of Godslaying"};

	

	public Weapon () throws SlickException{
	         generateWeapon();
	}
	
	
	public void generateWeapon() throws SlickException{
		Random rnd = new Random();
		
		// Where should it be placed on the body?
		attribute_location = 3;
		// how much damage per attack: (modifies the character's attackdamage)
		attribute_attackdmg = rnd.nextInt(10); 
		// How many attacks per second (modifies the character's attackspeed)
		attribute_attackspeed = rnd.nextInt(1000)+400; 
		// How many hits you can make with the weapon before it breaks: 
		attribute_durability_max = rnd.nextInt(10)*2; 
		attribute_durability_current = attribute_durability_max; 
		
		// determine what name the weapon should have: 
		// BEWARE, here be magic numbers:
		String a = prefix[rnd.nextInt(prefix.length)];

		
		int temp = rnd.nextInt(weaponType.length);
		String b = weaponType[temp];
		
		
		switch (temp){
		case 0:
			thumbnail = new Image("Textures/weapon_Axe.png");
			break;
		case 1:
			thumbnail = new Image("Textures/weapon_Sword.png");
			break;
		case 2:
			thumbnail = new Image("Textures/weapon_Mace.png");
			break;
		case 3:
			thumbnail = new Image("Textures/weapon_Club.png");
			break;
		case 4:
			thumbnail = new Image("Textures/weapon_War_Staff.png");
			break;
		case 5:
			thumbnail = new Image("Textures/weapon_Dagger.png");
			break;
		case 6:
			thumbnail = new Image("Textures/weapon_Falchion.png");
			break;
		case 7:
			thumbnail = new Image("Textures/weapon_Flail.png");
			break;
		case 8:
			thumbnail = new Image("Textures/weapon_Great_Axe.png");
			break;
		case 9:
			thumbnail = new Image("Textures/weapon_War_Hammer.png");
			break;
		}
		
		String c;
		if (attribute_attackdmg >= 9 && attribute_attackspeed >= 0.40f){
			//  Is the weapon the most powerful possible? Add " of Godslaying" 
			c = suffix[9];
		} else if (attribute_attackdmg >= 6 ){
			//nextInt(3) because of three dmg related names on 0,1,2
			c = suffix[rnd.nextInt(3)];
		} else if (attribute_attackspeed >= 0.25f){
			//nextInt(3)+3 because of three speed related names on 3,4,5
			c = suffix[rnd.nextInt(3)+3];
		} else if (attribute_durability_max >= 12){
			//nextInt(3)+6 because of durability related names on 6,7,8
			c = suffix[rnd.nextInt(3)+6];
		} else {
			c = "";
		}
		
		attribute_description = a + b + c;
	}
}
