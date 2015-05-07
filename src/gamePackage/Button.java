package gamePackage;

public class Button {
	public int posX;
	public int posY;
	public int width = 60;
	public int height = 30;
	public String text = "Buttontext";
	public String id = "b_default";
	
	
	public Button(int posX, int posY, int width, int height, String text, String id){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.text = text;
		this.id = id; 
	}
	
	
}
