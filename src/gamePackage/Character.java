package gamePackage;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

//the class implements mover because this is used as a parameter in the pathfinding library, in order to identify which object is moving
public class Character extends GameObject implements Mover{
	Animation[] anim_attacking = new Animation[4];
	Animation[] anim_dying = new Animation[4];;
	Animation[] anim_walking = new Animation[4];
	Animation[] anim_idle = new Animation[4];
	Sound sound_movement;
	Sound sound_attacking;
	Sound sound_dying;
	boolean dying = false;
	boolean dead = false;
	boolean attackRequested = false;
	boolean damageDealt = false;
	String attribute_name;
	int attribute_health_max =5;
	int attribute_health_current;
	int attribute_damage;
	int attribute_attackSpeed = 1000;
	int screenPosTranslationWhenAttacking_x;
	int nextStep = 0;
	int nextStep_x = -1;
	int nextStep_y = -1;
	private int direction = 1;
	Action currentAction = Action.IDLE;
	Character attackTarget;
	long lastAttackTime;
	Path path;
	float movespeed = 0.002f;
	
	
	public enum Action{
		IDLE,ATTACKING,DYING,WALKING
	}
	
	
	/**
	 * Starts the dying animation and sets health to zero
	 */
	public void kill(){
		attribute_health_current = 0;
		setAction(Action.DYING);
		dying = true;
	}
	
	
	/**
	 * Sets the current action of the character to something else
	 * @param action The new action which the character should do
	 */
	public void setAction(Action action){
		if(action != currentAction){
			currentAction = action;
		}
	}
	
	
	/**
	 * Sets the current direction of the character to something else
	 * @param direction The new direction which the character should face, 0 = southeast, 1 = southwest, 2 = northwest, 3 = northeast
	 */
	public void setDirection(int direction){
		if(direction != this.direction)
		this.direction = direction;
	}
	
	
	/**
	 * Makes the character move one movespeed along its path, per millisecond. If it collides with something, attack it if it is the attacktarget
	 * @param i Time in milliseconds since last update
	 */
	public void move(int i){
		if(path != null && currentAction != Action.ATTACKING){
			//if there is a path defined, and the character is not attacking
			float deltamovespeed = movespeed*i;
			setAction(Action.IDLE);
			if(path.getLength() > nextStep){
				//if the character has not reached the end of the path yet, get x and y tile positions of the target step in the path
				nextStep_x = path.getStep(nextStep).getX();
				nextStep_y = path.getStep(nextStep).getY();
				if(Math.abs((position_x - path.getStep(nextStep).getX())) < deltamovespeed*2 && Math.abs((position_y - path.getStep(nextStep).getY())) < deltamovespeed*2){
					//if the character is within 2 deltamovespeeds of the destination tile, set the character position to this tile, and define the next destination as the next step in the path
					position_x = path.getStep(nextStep).getX();
					position_y = path.getStep(nextStep).getY();
					nextStep += 1;
				}
				else if(position_x < path.getStep(nextStep).getX()){
					//if the character's x position is lower than the destination tile's x position, check if there is a collisionobject blocking our
					//path, or otherwise move one deltamovespeed closer to it. The direction of the character is also updated accordingly.
					//This moves the character southeast
					setDirection(0);
					GameObject collisionObject = Game.gameLevel.collidingObject(this, position_x+deltamovespeed, position_y);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_x += deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
				}
				else if(position_x > path.getStep(nextStep).getX()){
					//moving northwest
					setDirection(2);
					GameObject collisionObject = Game.gameLevel.collidingObject(this, position_x-deltamovespeed, position_y);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_x -= deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
				}
				else if(position_y < path.getStep(nextStep).getY()){
					//moving northeast
					setDirection(3);
					GameObject collisionObject = Game.gameLevel.collidingObject(this, position_x, position_y+deltamovespeed);
					if(collisionObject == null){
						setAction(Action.WALKING);
						position_y += deltamovespeed;
					}
					else if(collisionObject == attackTarget) startAttack();
					
	
				}
				else if(position_y > path.getStep(nextStep).getY()){
					//moving southwest
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
				//having reached the last step of the path, stop moving
				path = null;
			}
		}
		else if(currentAction == Action.ATTACKING && System.currentTimeMillis()-lastAttackTime <= attribute_attackSpeed){
			//attack NOT finished
			if(anim_attacking[direction].getFrame() >= 8 && !damageDealt){
				//attack animation has reached frame 8 (0 indexed), which is when the character's weapon is furthest away
				if (attackTarget != null){
					//deal damage to the target
				System.out.println(attribute_name + " attacking " + attackTarget.attribute_name + " for "+ attribute_damage +" dmg");
				attackTarget.attribute_health_current = attackTarget.attribute_health_current - attribute_damage;
				if(attackTarget.attribute_health_current <= 0) attackTarget.kill();
				}
				//enable a lock so that damage is only dealt once per attack
				damageDealt = true;
			}
		}
		else if(currentAction == Action.ATTACKING && System.currentTimeMillis()-lastAttackTime > attribute_attackSpeed){
			//attack finished
			setAction(Action.IDLE);
			path = null;
		}
		else if(this instanceof Enemy && 
				Math.abs(Game.player.position_x - position_x) < 0.9f &&
				Math.abs(Game.player.position_y - position_y) < 0.9f){
			//fixes an enemy blocking another enemy when it's attacking, allowing it to attack when the player is near
			startAttack();
		}
		else {
			//if there is no path defined, and not attacking
			setAction(Action.IDLE);
		}
		
		
		
	}
	
	
	/**
	 * The character performs an attack towards its attackTarget
	 */
	public void startAttack(){
		if(System.currentTimeMillis()-lastAttackTime > attribute_attackSpeed && (attackRequested || this instanceof Enemy)){
			//only attack if the attribute_attackSpeed milliseconds have passed since the last attack. And also check if the attack was requested by a mouse click for the player, the enemy ignores this condition.
			//Set the animation speed to match the attack speed. It seems animation durations occur twice as fast as their frameduration, meaning it has to be doubled.
			int frameduration = (int)(attribute_attackSpeed/anim_attacking[direction].getFrameCount());
			for(int frame = 0; frame < anim_attacking[direction].getFrameCount(); frame++)
				anim_attacking[direction].setDuration(frame, frameduration*2);
			attackRequested = false;
			damageDealt = false;
			lastAttackTime = System.currentTimeMillis();
			anim_attacking[direction].restart();
			setAction(Action.ATTACKING);
		}

		
	}
	
	
	/**
	 * Request the character to start moving towards a target, and attack it if it collides
	 * @param attackTarget The character which this character should attack if it collides with it
	 */
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
	
	
	/** 
	 * Request the character to start moving towards a destination. This creates a path for the character, but does not make it move.
	 * @param start_x Current X tile position of the character which should move
	 * @param start_y Current Y tile position of the character which should move
	 * @param end_x X tile position of the destination the character should move to
	 * @param end_y Y tile position of the destination the character should move to
	 * @param attacking True if the character should attack its attackTarget when colliding with it, false to clear the attackTarget
	 */
	public void moveTo(int start_x, int start_y, int end_x, int end_y, boolean attacking){
		if(currentAction != Action.ATTACKING){
			if(!attacking) attackTarget = null;
			path = Game.gameLevel.getPath(this, start_x,start_y,end_x,end_y);
			if(path!= null){
				//if one player position variable does not change, skip the first move
				if(position_y == path.getStep(1).getY() || position_x == path.getStep(1).getX())
					nextStep = 1;
				else nextStep = 0;
				
			}
			else if(!Game.gameLevel.blockedChar(this, end_x, end_y)){
				//if the character is moving to the same tile as he is supposedly standing on, create a path of 1 length to it
					path = new Path();
					path.appendStep(start_x, start_y);
					nextStep = 0;
			}
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
			return anim_dying[direction];
		case WALKING:
			return anim_walking[direction];
		default:
			return anim_idle[direction];
		}
	}
}


