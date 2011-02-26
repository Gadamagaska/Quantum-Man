package entities;

import java.awt.Point;

import enums.Direction;

/**
 * The Entity is a game-object that has some function within the game, that
 * could be the ability to move or animate (if it doesn't have any of this it
 * should just be a part of the level-layer). This is the simplest game-object
 * there is and only contains a postion and a name.
 * @author Emil
 *
 */
public class Entity {
	
	private Point pos;
	private String name;
	private boolean flying;
	
	/**
	 * The constructor takes a position that will be the entity's starting
	 * postions.
	 * @param position
	 */
	public Entity(Point position){
		pos = position;
		name = "";
		flying = false;
	}
	
	/**
	 * @return The current position of the Entity.
	 */
	public Point getPos(){
		return pos;
	}
	
	/**
	 * @return The current x-coordinate of the Entity.
	 */
	public int getX(){
		return pos.x;
	}
	
	/**
	 * @return The current y-coordinate of the Entity.
	 */
	public int getY(){
		return pos.y;
	}
	
	/**
	 * Moves the Entity to the given coordinates.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 */
	public void move(int x, int y){
		pos.x = x;
		pos.y = y;
	}
	
	/**
	 * Moves the Entity in the given Direction.
	 * @param dir The target Direction.
	 */
	public void move(Direction dir){
		switch(dir){
		
		case NORTH:{
			pos.y -= 1;
			break;
		}
		case SOUTH:{
			pos.y += 1;
			break;
		}
		case WEST:{
			pos.x -= 1;
			break;
		}
		case EAST:{
			pos.x += 1;
			break;
		}
		}
	}
	
	/**
	 * Sets the name of the Entity. This name is displayed when a player
	 * mouse-overs it for a longer time.
	 * @param name The wanted name.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @return The current name of the Entity.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets whether the Entity is flying (placed on layer 2). If true, other
	 * entities will be painted under it.
	 * @param flying The wanted boolean value
	 */
	public void setFlying(boolean flying) {
		this.flying = flying;
	}
	
	/**
	 * 
	 * @return The boolean value of whether the Entity is flying.
	 */
	public boolean getFlying() {
		return flying;
	}
}
