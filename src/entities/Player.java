package entities;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

import core.Animation;
import core.Sprite;
import database.Database;
import enums.Direction;


public class Player {

	private Sprite player;
	private Direction direction;
	private Animation animation;
	private HashMap<Direction,Animation> anim_dirs;
	private Point position;
	private Image image;
	private Point ghostPoint;

	public Player(String tileset, Direction direction, Point position) {
		setDirection(direction);
		setPosition(position);

		// create the 4 animations from the tileSet
		anim_dirs = new HashMap<Direction,Animation>();

		Database db = Database.getInstance();
		anim_dirs.put(Direction.NORTH, db.getTileAnimation(tileset, new int[]{2,6,10}, 200));
		anim_dirs.put(Direction.SOUTH, db.getTileAnimation(tileset, new int[]{0,4,8}, 200));
		anim_dirs.put(Direction.WEST, db.getTileAnimation(tileset, new int[]{1,5,9}, 200));
		anim_dirs.put(Direction.EAST, db.getTileAnimation(tileset, new int[]{3,7,11}, 200));
		animation = anim_dirs.get(direction);

		player = new Sprite(animation);
		this.image = animation.getImage();
		ghostPoint = position.getLocation();
	}

	public Sprite getSprite() {
		return player;
	}

	public void update(long timePassed){
		animation.update(timePassed);
	}

	public void setDirection(Direction direction){
		this.direction = direction;
	}

	public void setPosition(Point position) {
		this.position = position;
	}	

	public Direction getDirection() {
		return direction;
	}

	public Point getPosition() {
		return position;
	}
	public int getX() {
		return position.x;
	}
	public void setX(int x){
		position.x = x;
	}

	public int getY() {
		return position.y;
	}
	public void setY(int y){
		position.y = y;
	}

	public Animation getAnimation() {
		return animation;
	}	

	public Image getImage() {
		image = animation.getImage();
		return image;
	}

	public int getGhostX(){
		return ghostPoint.x;
	}
	public void setGhostX(int x){
		ghostPoint.x = x;
	}

	public int getGhostY(){
		return ghostPoint.y;
	}
	public void setGhostY(int y){
		ghostPoint.y = y;
	}

	public synchronized void movePlayer(Direction direction){
		Database db = Database.getInstance();
		Dimension map = db.getLevelDimensions(0);

		switch(direction) {	
		case EAST:{
			animation = anim_dirs.get(direction);
			if(db.isWalkable(0, getX()+1, getY())){
				if((getX()-getGhostX() > 2) && (getGhostX() < map.width-6)){
					setGhostX(getGhostX()+1);
				}
				setX(getX()+1);
			}
			break;
		}
		case WEST:{
			animation = anim_dirs.get(direction);
			if(db.isWalkable(0, getX()-1, getY())){
				if((getX()-getGhostX() < -1) && (getGhostX() > 4)){
					setGhostX(getGhostX()-1);
				}
				setX(getX()-1);
			}
			break;
		}
		case NORTH:
			animation = anim_dirs.get(direction);
			if(db.isWalkable(0, getX(), getY()-1)){
				System.out.println(getGhostX()-getX());
				System.out.println(getGhostX() > 5);
				if((getY()-getGhostY() < -1) && (getGhostY() > 4)){
					setGhostY(getGhostY()-1);
				}
				setY(getY()-1);
			}
			break;
		case SOUTH:
			animation = anim_dirs.get(direction);
			if(db.isWalkable(0, getX(), getY()+1)){
				if((getY()-getGhostY() > 2) && (getGhostY() < map.height-6)){
					setGhostY(getGhostY()+1);
				}
				setY(getY()+1);
			}
			break;
		}
	}
}
