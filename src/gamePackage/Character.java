package gamePackage;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.Path;

public class Character extends GameObject{
	// variables
	public Animation[] anim_attacking = new Animation[4];
	public Animation anim_dying;
	public Animation[] anim_walking = new Animation[4];
	public Animation[] anim_idle = new Animation[4];
	public Sound sound_movement;
	public Sound sound_attacking;
	public Sound sound_dying;
	public boolean dead;
	public int attribute_health_max;
	public int attribute_health_current;
	public String attribute_name;
	private int direction = 1;
	private Action currentAction = Action.IDLE;
	public Character attackTarget;
	public Item pickupTarget;
	long lastAttackTime;
	long attackSpeed = 2000;
	
	Path path;
	int nextStep = 0;
	int nextStep_x = -1;
	int nextStep_y = -1;
	float movespeed = 0.002f;
	
	
	public enum Action{
		IDLE,ATTACKING,DYING,WALKING
	}
	
	// Methods
	public void kill(){
		
	}
	
	public void setAction(Action action){
		if(action != currentAction)
		currentAction = action;
	}
	
	public void setDirection(int direction){
		if(direction != this.direction)
		this.direction = direction;
	}
	
	public void move(int i){
		if(path != null){
			float deltamovespeed = movespeed*i;
			//System.out.println(Math.abs((playerpos_x - path.getStep(nextStep).getX())) < 0.05f && Math.abs((playerpos_y - path.getStep(nextStep).getY())) < 0.05f);

			if(path.getLength() > nextStep){
				nextStep_x = path.getStep(nextStep).getX();
				nextStep_y = path.getStep(nextStep).getY();
				if(Math.abs((position_x - path.getStep(nextStep).getX())) < deltamovespeed*2 && Math.abs((position_y - path.getStep(nextStep).getY())) < deltamovespeed*2){
					
					position_x = path.getStep(nextStep).getX();
					position_y = path.getStep(nextStep).getY();
					nextStep += 1;
				}
				else if(position_x < path.getStep(nextStep).getX()){
					setDirection(0);
					GameObject collisionObject = Game.gameLevels[Game.currentLevel].collidingObject(this, position_x+deltamovespeed, position_y);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_x += deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
					else setAction(Action.IDLE);
				}
				else if(position_x > path.getStep(nextStep).getX()){
					setDirection(2);
					GameObject collisionObject = Game.gameLevels[Game.currentLevel].collidingObject(this, position_x-deltamovespeed, position_y);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_x -= deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
					else setAction(Action.IDLE);
				}
				else if(position_y < path.getStep(nextStep).getY()){
					setDirection(3);
					GameObject collisionObject = Game.gameLevels[Game.currentLevel].collidingObject(this, position_x, position_y+deltamovespeed);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_y += deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
					else setAction(Action.IDLE);
					
				}
				else if(position_y > path.getStep(nextStep).getY()){
					setDirection(1);
					GameObject collisionObject = Game.gameLevels[Game.currentLevel].collidingObject(this, position_x, position_y-deltamovespeed);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_y -= deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
					else setAction(Action.IDLE);
					
				}
				
			}
			else {
				setAction(Action.IDLE);
				path = null;
			}
		}
		else setAction(Action.IDLE);
		
	}
	
	public void startAttack(){
		
		if(System.currentTimeMillis()-lastAttackTime > attackSpeed){
			System.out.println("attacktimer: "+System.currentTimeMillis());
			//ATTACK!!
			lastAttackTime = System.currentTimeMillis();
			setAction(Action.ATTACKING);
		}
	}
	
	public void attackMove(Character attackTarget){
		
		this.attackTarget = attackTarget;
		moveTo(Math.round(Game.player.position_x), Math.round(Game.player.position_y), Math.round(attackTarget.position_x),Math.round(attackTarget.position_y), true);
	}
	
	public void calculateScreenPos(){
		screenPosition_x = Math.round(position_x*80+position_y*80-(Game.player.position_y*80+Game.player.position_x*80)+Game.windowWidth/2-80)+pixelTranslation_x;
		screenPosition_y = Math.round(position_x*40-position_y*40+(Game.player.position_y*40-Game.player.position_x*40)+Game.windowHeight/2-40)+pixelTranslation_y;
		
	}
	
	public void moveTo(int start_x, int start_y, int end_x, int end_y, boolean attacking){
		if(!attacking) attackTarget = null;
		path = Game.gameLevels[Game.currentLevel].getPath(start_x,start_y,end_x,end_y);
		if(path!= null){
			//if one player position variable does not change, skip the first move
			if(position_y == path.getStep(1).getY() || position_x == path.getStep(1).getX())
				nextStep = 1;
			else nextStep = 0;
			
			System.out.println("Found path of length: " + path.getLength() + ".");
	
			for(int i = 0; i < path.getLength(); i++) {
				System.out.println("Move to: " + path.getX(i) + "," + path.getY(i) + ".");          
			}
			
		}
		else if(!Game.gameLevels[Game.currentLevel].blocked(null, end_x, end_y)){
			//if the player clicks the same tile as he is supposedly standing on, create a path of 1 length to it
				path = new Path();
				path.appendStep(start_x, start_y);
				nextStep = 0;
		}
		
	}
	
	@Override
	public Animation getCurrentAnimation(){
		switch(currentAction){
		case IDLE:
			return anim_idle[direction];
		case ATTACKING:
			return anim_attacking[direction];
		case DYING:
			return anim_dying;
		case WALKING:
			return anim_walking[direction];
		default:
			return anim_idle[direction];
		}
	}
}
