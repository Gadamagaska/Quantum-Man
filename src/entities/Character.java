package entities;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

import core.Animation;
import database.Database;
import enums.Direction;

public abstract class Character extends Entity {
	
	private Animation curAnimation;
	private HashMap<Direction,Animation> dirAnimations;
	private Direction dir;

	public Character(String tileset, Point pos) {
		super(pos);
		createAnimations(tileset);
		dir = Direction.SOUTH;
		turn(dir);
	}

	private void createAnimations(String tileset) {
		Database db = Database.getInstance();
		dirAnimations = new HashMap<Direction, Animation>();
		
		dirAnimations.put(Direction.SOUTH, db.getTileAnimation(tileset, new int[]{0,4,8}, 200));
		dirAnimations.put(Direction.WEST, db.getTileAnimation(tileset, new int[]{1,5,9}, 200));
		dirAnimations.put(Direction.NORTH, db.getTileAnimation(tileset, new int[]{2,6,10}, 200));
		dirAnimations.put(Direction.EAST, db.getTileAnimation(tileset, new int[]{3,7,11}, 200));
	}
	
	public void update(long timePassed){
		curAnimation.update(timePassed);
	}
	
	public void turn(Direction dir){
		this.dir = dir;
		curAnimation = dirAnimations.get(dir);
	}
	
	public Direction getDirection(){
		return dir;
	}
	
	public Image getImage() {
		return curAnimation.getImage();
	}
}
