package entities;

import java.awt.Point;

public abstract class CombatCharacter extends Character {
	
	private int base_health,health;
	private int base_mana, mana;
	// private Ability[] abilities;

	public CombatCharacter(String tileset, Point pos, int base_health, int base_mana) {
		super(tileset, pos);
		
		this.base_health = base_health;
		health = base_health;
		this.base_mana = base_mana;
		mana = base_mana;
	}
	
	// Health stuff
	public int getBaseHealth(){
		return base_health;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void adjustHealth(int value){
		health += value;
	}
	
	public boolean isDead(){
		return health <= 0;
	}
	
	// Mana stuff
	public int getBaseMana(){
		return base_mana;
	}
	
	public int getMana(){
		return mana;
	}
	
	public void adjustMana(int value){
		mana += value;
	}
	
	// TODO: Add methods for Abilities
}
