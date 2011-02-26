package entities;

import java.awt.Dimension;

import database.Database;
import enums.Direction;

/**
 * The local player holds a reference to the Player object that
 * is controlled locally, it also has the methods for moving that
 * player around the game-world without conflicting with other
 * Entities and world tiles.
 * @author Emil
 *
 */
public class LocalPlayer{

	private Player me;
	private Entity ghost;
	
	public LocalPlayer(Player player) {
		me = player;
		ghost = new Entity(player.getPos().getLocation());
	}

	public Player getPlayer(){
		return me;
	}
	
	public int getGhostX(){
		return ghost.getX();
	}
	
	public int getGhostY(){
		return ghost.getY();
	}
	
	public void move(Direction direction){
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
