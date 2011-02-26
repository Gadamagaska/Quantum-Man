package entities;

import interfaces.Active;

import java.awt.Point;

/**
 * This class extends CombatCharacter and is used for actual Players. This
 * can (and probably is) be the Player controlled locally, but might later
 * also be another player on the server/network or another save-game.
 * @author Emil
 *
 */
public class Player extends CombatCharacter implements Active{
	
	// private Inventory inventory;
	// private Quest[] quests;
	// private HashSet<EquipmentSlot> equipment;

	/**
	 * Constructor for the Player class, they automatically
	 * start with Base Health and Mana at 100.
	 * 
	 * @param tileset The String of the TileSet in the Database
	 * @param pos The starting position of the Player
	 */
	public Player(String name,String tileset, Point pos) {
		super(tileset, pos, 100, 100);
		setName(name);
		// TODO Instantiere de tre private felter ovenfor...
	}

	@Override
	public void activate() {
		// TODO Inspect or something
	}
}
