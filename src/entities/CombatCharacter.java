package entities;

import java.awt.Point;

/**
 * The CombatCharacter defines a Character that can engage in combat and therefore
 * has health and mana (more to come).
 * @author Emil
 *
 */
public abstract class CombatCharacter extends Character{
	
	private int base_health,health;
	private int base_mana, mana;
	// private Ability[] abilities;

	/**
	 * @param tileset The name of the TileSet to use from the Database.
	 * @param pos The starting position of the CombatCharacter.
	 * @param base_health The maximum health.
	 * @param base_mana  The maximum mana.
	 */
	public CombatCharacter(String tileset, Point pos, int base_health, int base_mana) {
		super(tileset, pos);
		
		this.base_health = base_health;
		health = base_health;
		this.base_mana = base_mana;
		mana = base_mana;
	}
	
	// Health stuff
	/**
	 * @return The maximum health.
	 */
	public int getBaseHealth(){
		return base_health;
	}
	
	/**
	 * @return The current health.
	 */
	public int getHealth(){
		return health;
	}
	
	/**
	 * Adjusts the current health by the given value. The CombatCharacter's current
	 * health can't exceed the base health or be less than zero.
	 * @param value Positive values will heal the CombatCharacter and negative values
	 * will damage it.
	 */
	public void adjustHealth(int value){
		health += value;
		
		if(health > base_health){
			health = base_health;
		}else if(health < 0){
			health = 0;
		}
	}
	
	/**
	 * @return True if the CombatCharacter has less than zero health.
	 */
	public boolean isDead(){
		return health <= 0;
	}
	
	// Mana stuff
	/**
	 * @return The maximum mana.
	 */
	public int getBaseMana(){
		return base_mana;
	}
	
	/**
	 * @return The current health.
	 */
	public int getMana(){
		return mana;
	}
	
	/**
	 * Adjusts the current mana by the given value. The CombatCharacter's current
	 * mana can't exceed the base mana or be less than zero.
	 * @param value Positive values will replenish the CombatCharacter and negative values
	 * will drain it.
	 */
	public void adjustMana(int value){
		mana += value;
		
		if(mana > base_mana){
			mana = base_mana;
		}else if(mana < 0){
			mana = 0;
		}
	}
	
	// TODO: Add methods for Abilities
}
