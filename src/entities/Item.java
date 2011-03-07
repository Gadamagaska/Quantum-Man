package entities;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import enums.BaseSkill;
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
	private Map<BaseSkill, Integer> powers;
	
	// Maybe instead of having a lot of parameters, some sort of static
	// class/method could load all the data from a single ItemID?
	
	public Item(int ItemID, String name, ItemSlot type, int value, boolean canBeSold, boolean stackable, int maxInStack, Map<BaseSkill, Integer> powers)
	{
		this.ItemID = ItemID;
		this.name = name;
		this.type = type;
		this.value = value;
		this.canBeSold = canBeSold;
		this.stackable = stackable;
		this.maxInStack = maxInStack;
		this.powers = powers;
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
	
	public int getPowerValue(BaseSkill skill)
	{
		if(powers.containsKey(skill)){
			return powers.get(skill);
		} else {
			return 0;
		}
	}
	
//	private class power
//	{
//		private final BaseSkill skill;
//		private final int value;
//		
//		public power(BaseSkill skill, int value)
//		{
//			this.skill = skill;
//			this.value = value;
//		}
//		
//		public BaseSkill getSkill()
//		{
//			return skill;
//		}
//		
//		public int getValue()
//		{
//			return value;
//		}
//	}
	
	// Item Factory
	private static Map<Integer, Item> createdItems = new HashMap<Integer, Item>();

	private static int staticItemID;
	private static String staticName;
	private static ItemSlot statictype;
	private static int staticvalue;
	private static boolean staticcanBeSold;
	private static boolean staticstackable;
	private static int staticmaxInStack;
	private static Map<BaseSkill, Integer> staticpowers;
	
	public static void addItemID(int itemID)
	{
		staticItemID = itemID;
	}
	
	public static void addName(String name)
	{
		staticName = name;
	}
	
	public static void addType(ItemSlot itemSlot)
	{
		statictype = itemSlot;
	}
	
	public static void addSellAble(boolean canBeSold, int value)
	{
		staticcanBeSold = canBeSold;
		staticvalue = value;
	}
	
	public static void addStackAble(boolean stackable, int maxInStack)
	{
		staticstackable = stackable;
		staticmaxInStack = maxInStack;
	}
	
	public static void addPower(BaseSkill skill, int value)
	{
		staticpowers.put(skill, value);
	}
	
	public static Item createItem()
	{
		if(createdItems.containsKey(staticItemID)){ 
			return createdItems.get(staticItemID);
		} else {
			Item newItem = new Item(staticItemID, staticName, statictype, staticvalue, staticcanBeSold, staticstackable, staticmaxInStack, staticpowers);
			createdItems.put(staticItemID, newItem);
			return newItem;
		}
	}
}
