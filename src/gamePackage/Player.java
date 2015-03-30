package gamePackage;

public class Player extends Character{
	public Item[][] inventory;
	public Item[] equipment;
	public int gold;
	public int attribute_level;
	public int attribute_experience;
	public int attribute_strength;
	public int attribute_dexterity;
	public int attribute_vitality;
	public int attributepoints;
	public GameLevel level;

	public Player(){
		
	}
	
	public void moveAndPickUp(Item target){
		//Player.equipment[EquipmentType.FEET] = 
	}
	
	public void moveAndAttack(Enemy target){
		
	}
	
	public void move(int x, int y){
		
	}
	
	public void dropItem(Item item){
		
	}
	
	public void kill(){
		
	}
}
