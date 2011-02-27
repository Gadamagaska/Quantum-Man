package entities;

import interfaces.Drawable;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

import core.Animation;
import database.Database;
import enums.Direction;

/**
 * The Character is an Entity that has Animation specified by a TileSet for all four Directions and
 * is pointing toward a specific direction. It implements Drawable so it is visible in the game-world.
 * @author Emil
 *
 */
public abstract class Character extends Entity implements Drawable{
	
	private Animation curAnimation;
	private HashMap<Direction,Animation> dirAnimations;
	private Direction dir;

	/**
	 * @param tileset The TileSet of which the Character's Animations should be made.
	 * @param pos The starting position of the Character.
	 */
	public Character(String tileset, Point pos) {
		super(pos);
		createAnimations(tileset);
		dir = Direction.SOUTH;
		turn(dir);
	}

	/**
	 * Creates the four Animations for the Character
	 * @param tileset The TileSet to be used.
	 */
	private void createAnimations(String tileset) {
		Database db = Database.getInstance();
		dirAnimations = new HashMap<Direction, Animation>();
		
		dirAnimations.put(Direction.SOUTH, db.getTileAnimation(tileset, new int[]{0,4,8}, 200));
		dirAnimations.put(Direction.WEST, db.getTileAnimation(tileset, new int[]{1,5,9}, 200));
		dirAnimations.put(Direction.NORTH, db.getTileAnimation(tileset, new int[]{2,6,10}, 200));
		dirAnimations.put(Direction.EAST, db.getTileAnimation(tileset, new int[]{3,7,11}, 200));
	}
	
	/**
	 * Updates the current Animation due to the time passed.
	 * @param timePassed The time since last update (in milliseconds).
	 */
	public void update(long timePassed){
		curAnimation.update(timePassed);
	}
	
	/**
	 * Turns the Character towards the given Direction.
	 * @param dir The new Direction of the Character.
	 */
	public void turn(Direction dir){
		this.dir = dir;
		curAnimation = dirAnimations.get(dir);
	}
	
	/**
	 * @return The Direction that the Character is facing.
	 */
	public Direction getDirection(){
		return dir;
	}
	
	@Override
	public Image getImage() {
		return curAnimation.getImage();
	}
}
