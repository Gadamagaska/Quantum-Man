package entities;
import enums.ItemSlot;
/**
 * An Item is a single item of some type.
 * This class will be the 'main' class for items
 * @author Thediabloman
 */
public class Item {
	private final int ItemID;
	private final String name;
	private final ItemSlot type;
	private final int value;
	private final boolean canBeSold;
	private final boolean stackable;
	private final int maxInStack;
	
	// Maybe instead of having a lot of parameters, some sort of static
	// class/method could load all the data from a single ItemID?
	
	public Item(int ItemID, String name, ItemSlot type, int value, boolean canBeSold, boolean stackable, int maxInStack)
	{
		this.ItemID = ItemID;
		this.name = name;
		this.type = type;
		this.value = value;
		this.canBeSold = canBeSold;
		this.stackable = stackable;
		this.maxInStack = maxInStack;
	}
	
	/**
	 * @return The ID of the item
	 */
	public int getItemID()
	{
		return ItemID;
	}
	
	/**
	 * @return The name of the Item
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Can check if an item can be equipped
	 * @return true if the item can be equipped, 
	 * 			either weapon or armor 
	 */
	public boolean canEquip()
	{
		return !(type == ItemSlot.UNEQUIPPABLE);
	}
	
	/**
	 * Check if item is a weapon
	 * @return true if weapon, false otherwise
	 */
	public boolean isWeapon()
	{
		return type == ItemSlot.WEAPON;
	}
	
	/**
	 * @return The value of the item
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * @return true if the item can be sold
	 */
	public boolean canBeSold()
	{
		return canBeSold;
	}
	
	public boolean canBeStacked()
	{
		return stackable;
	}
	
	public int getMaxStack()
	{
		return maxInStack;
	}
}
