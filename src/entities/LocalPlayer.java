package entities;

import java.awt.Dimension;
import java.awt.Point;

import database.Database;
import enums.Direction;

/**
 * This class contains methods for moving and managing the Player controlled locally.
 * It has a reference to the normal Player object with all the stuff for the player.
 * 
 * @author Emil
 *
 */
public class LocalPlayer{

	private Player me;
	private Entity ghost;
	
	public LocalPlayer(Player player) {
		me = player;
		
		// making an Entity, just this once...
		ghost = new Entity(player.getPos().getLocation()){
			@Override
			public void activate() {}
			@Override
			public void move() {}
		};
	}

	public Player getPlayer(){
		return me;
	}
	
	public Point getGhostPos(){
		return ghost.getPos();
	}
	
	public int getGhostX(){
		return ghost.getX();
	}
	
	public int getGhostY(){
		return ghost.getY();
	}
	
	public synchronized void move(Direction direction){
		Database db = Database.getInstance();
		Dimension map = db.getLevelDimensions(0);

		me.turn(direction);
		
		switch(direction) {	
		case EAST:{
			if(db.isWalkable(0, me.getX()+1, me.getY())){
				if((me.getX()-getGhostX() > 2) && (getGhostX() < map.width-6)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		}
		case WEST:{
			if(db.isWalkable(0, me.getX()-1, me.getY())){
				if((me.getX()-getGhostX() < -1) && (getGhostX() > 4)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		}
		case NORTH:
			if(db.isWalkable(0, me.getX(), me.getY()-1)){
				if((me.getY()-getGhostY() < -1) && (getGhostY() > 4)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		case SOUTH:
			if(db.isWalkable(0, me.getX(), me.getY()+1)){
				if((me.getY()-getGhostY() > 2) && (getGhostY() < map.height-6)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		}
	}
}
