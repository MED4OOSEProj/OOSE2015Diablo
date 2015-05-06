package gamePackage;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

public class Character extends GameObject implements Mover{
	// variables
	Animation[] anim_attacking = new Animation[4];
	Animation[] anim_dying = new Animation[4];;
	Animation[] anim_walking = new Animation[4];
	Animation[] anim_idle = new Animation[4];
	Sound sound_movement;
	Sound sound_attacking;
	Sound sound_dying;
	boolean dying = false;
	boolean dead = false;
	int attribute_health_max =5;
	int attribute_health_current;
	int attribute_damage;
	int attribute_attackSpeed = 1000;
	String attribute_name;
	private int direction = 1;
	Action currentAction = Action.IDLE;
	Character attackTarget;
	
	long lastAttackTime;
	
	boolean attackRequested = false;
	boolean damageDealt = false;
	public int screenPosTranslationWhenAttacking_x;
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
		attribute_health_current = 0;
		setAction(Action.DYING);
		dying = true;
		System.out.println("someone is being killed");
	}
	
	public void setAction(Action action){
		if(action != currentAction){
			currentAction = action;
		}
		
	}
	
	public void setDirection(int direction){
		if(direction != this.direction)
		this.direction = direction;
	}
	
	public void move(int i){
		if(path != null && currentAction != Action.ATTACKING){
			float deltamovespeed = movespeed*i;
			//System.out.println(Math.abs((playerpos_x - path.getStep(nextStep).getX())) < 0.05f && Math.abs((playerpos_y - path.getStep(nextStep).getY())) < 0.05f);
			setAction(Action.IDLE);
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
					GameObject collisionObject = Game.gameLevel.collidingObject(this, position_x+deltamovespeed, position_y);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_x += deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
				}
				else if(position_x > path.getStep(nextStep).getX()){
					setDirection(2);
					GameObject collisionObject = Game.gameLevel.collidingObject(this, position_x-deltamovespeed, position_y);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_x -= deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
				}
				else if(position_y < path.getStep(nextStep).getY()){
					setDirection(3);
					GameObject collisionObject = Game.gameLevel.collidingObject(this, position_x, position_y+deltamovespeed);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_y += deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
					
				}
				else if(position_y > path.getStep(nextStep).getY()){
					setDirection(1);
					GameObject collisionObject = Game.gameLevel.collidingObject(this, position_x, position_y-deltamovespeed);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_y -= deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
				}
			}
			else {
				path = null;
			}
		}
		else if(currentAction == Action.ATTACKING && System.currentTimeMillis()-lastAttackTime <= attribute_attackSpeed){
			//attack NOT finished
			if(anim_attacking[direction].getFrame() >= 8 && !damageDealt){
				//attack animation has reached frame 8 (0 indexed), which is when the character's weapon is furthest away
				if (attackTarget != null){
				System.out.println(attribute_name + " attacking " + attackTarget.attribute_name + " for "+ attribute_damage +" dmg");
				attackTarget.attribute_health_current = attackTarget.attribute_health_current - attribute_damage;
				if(attackTarget.attribute_health_current <= 0) attackTarget.kill();
				}
				damageDealt = true;
			}
		}
		else if(currentAction == Action.ATTACKING && System.currentTimeMillis()-lastAttackTime > attribute_attackSpeed){
			//attack finished
			setAction(Action.IDLE);
			path = null;
			//System.out.println("stopping attack after "+(System.currentTimeMillis()-lastAttackTime + " ms"));
		}
		else if(this instanceof Enemy && 
				Math.abs(Game.player.position_x - position_x) < 1 &&
				Math.abs(Game.player.position_y - position_y) < 1){
			//fixes an enemy blocking another enemy when it's attacking, allowing it to attack when the player is near
			startAttack();
		}
		else {
			setAction(Action.IDLE);
		}
		
		
		
	}
	
	public void startAttack(){
		
		if(System.currentTimeMillis()-lastAttackTime > attribute_attackSpeed && (attackRequested || this instanceof Enemy)){
			//System.out.println("attacktimer: "+System.currentTimeMillis());
			//ATTACK!!
			int frameduration = (int)(attribute_attackSpeed/anim_attacking[direction].getFrameCount());
			//System.out.println("frameduration: "+frameduration);
			
			for(int frame = 0; frame < anim_attacking[direction].getFrameCount(); frame++)
				anim_attacking[direction].setDuration(frame, frameduration*2);
			attackRequested = false;
			damageDealt = false;
			lastAttackTime = System.currentTimeMillis();
			anim_attacking[direction].restart();
			setAction(Action.ATTACKING);
		}

	}
	
	public void attackMove(Character attackTarget){
		if(currentAction != Action.ATTACKING){
			attackRequested = true;
			this.attackTarget = attackTarget;
			moveTo(Math.round(position_x), Math.round(position_y), Math.round(attackTarget.position_x),Math.round(attackTarget.position_y), true);
		}
	}
	
	public void calculateScreenPos(){
		screenPosition_x = Math.round(position_x*80+position_y*80-(Game.player.position_y*80+Game.player.position_x*80)+Game.windowWidth/2-80)+pixelTranslation_x;
		//fixing attack frames being 128 pixels wide rather than 96 pixels.
		if(currentAction == Action.ATTACKING) screenPosition_x += screenPosTranslationWhenAttacking_x;
		screenPosition_y = Math.round(position_x*40-position_y*40+(Game.player.position_y*40-Game.player.position_x*40)+Game.windowHeight/2-40)+pixelTranslation_y;
		
	}
	
	public void moveTo(int start_x, int start_y, int end_x, int end_y, boolean attacking){
		if(currentAction != Action.ATTACKING){
			if(!attacking) attackTarget = null;
			path = Game.gameLevel.getPath(this, start_x,start_y,end_x,end_y);
			if(path!= null){
				//if one player position variable does not change, skip the first move
				if(position_y == path.getStep(1).getY() || position_x == path.getStep(1).getX())
					nextStep = 1;
				else nextStep = 0;
				/*
				System.out.println("Found path of length: " + path.getLength() + ".");
		
				for(int i = 0; i < path.getLength(); i++) {
					System.out.println("Move to: " + path.getX(i) + "," + path.getY(i) + ".");          
				}
				*/
				
			}
			else if(!Game.gameLevel.blockedChar(this, end_x, end_y)){
				//if the character is moving to the same tile as he is supposedly standing on, create a path of 1 length to it
					path = new Path();
					path.appendStep(start_x, start_y);
					nextStep = 0;
			}
		}
	}
	
	public boolean isThereAPathTo(int start_x, int start_y, int end_x, int end_y){
		Path temppath = Game.gameLevel.getPath(this, start_x,start_y,end_x,end_y);
		if(temppath!= null){
			return true;
		}
		return false;
	}

	
	@Override
	public Animation getCurrentAnimation(){
		switch(currentAction){
		case IDLE:
			return anim_idle[direction];
		case ATTACKING:
			return anim_attacking[direction];
		case DYING:
			return anim_dying[direction];
		case WALKING:
			return anim_walking[direction];
		default:
			return anim_idle[direction];
		}
	}
}
