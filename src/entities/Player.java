package entities;

import java.awt.Point;

public class Player extends CombatCharacter {
	
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
		// TODO Inspection af Player?
	}

	/**
	 * Since a Player is controlled by a human this method
	 * shouldn't do anything.
	 */
	@Override
	public void move() {}
}
