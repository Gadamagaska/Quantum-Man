package entities;

import java.awt.Dimension;

import database.Database;
import enums.Direction;

/**
 * The local player holds a reference to the Player object that
 * is controlled locally, it also has the methods for moving that
 * player around the game-world without conflicting with other
 * Entities and world tiles. To make the view only move when the
 * Player is close to the edge of the view we use a "Ghost" that
 * follows the player only if he is too far away, and it is actually
 * the ghost's view that is seen.
 * @author Emil
 *
 */
public class LocalPlayer{

	private Player me;
	private Entity ghost;
	
	/**
	 * The constructor only takes the reference to the player that
	 * can be controlled locally.
	 * @param player
	 */
	public LocalPlayer(Player player) {
		me = player;
		ghost = new Entity(player.getPos().getLocation());
	}

	/**
	 * Returns the reference to the Player that can be controlled
	 * locally.
	 * @return
	 */
	public Player getPlayer(){
		return me;
	}
	
	/**
	 * @return The x-coordinate for the Ghost.
	 */
	public int getGhostX(){
		return ghost.getX();
	}
	
	/**
	 * @return The y-coordinate for the Ghost.
	 */
	public int getGhostY(){
		return ghost.getY();
	}
	
	/**
	 * Moves the referenced Player in the direction specified. This is
	 * done with concerns towards the walkability of the tiles in the
	 * current level.
	 * @param direction
	 */
	// TODO: This should also take into account if there is another Entity on
	// the target position.
	public void move(Direction direction){
		Database db = Database.getInstance();
		Dimension map = db.getLevelDimensions(1);

		me.turn(direction);
		
		switch(direction) {	
		case EAST:{
			if(db.isWalkable(1, me.getX()+1, me.getY())){
				if((me.getX()-getGhostX() > 2) && (getGhostX() < map.width-6)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		}
		case WEST:{
			if(db.isWalkable(1, me.getX()-1, me.getY())){
				if((me.getX()-getGhostX() < -1) && (getGhostX() > 4)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		}
		case NORTH:
			if(db.isWalkable(1, me.getX(), me.getY()-1)){
				if((me.getY()-getGhostY() < -1) && (getGhostY() > 4)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		case SOUTH:
			if(db.isWalkable(1, me.getX(), me.getY()+1)){
				if((me.getY()-getGhostY() > 2) && (getGhostY() < map.height-6)){
					ghost.move(direction);
				}
				me.move(direction);
			}
			break;
		}
	}
}
