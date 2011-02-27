package entities;

import enums.ItemSlot;

/**
 * A ItemQuest is a item that a player needs to complete a quest
 * @author Thediabloman
 *
 */
public class ItemQuest extends Item {
	private final int connectedQuestID;
	
	/**
	 * The same parameters as a normal Item, except the questID is added,
	 * and the item is not sell-able and has a value of 0 by standard.
	 * @param questID The ID of the quest connected to this item
	 * @param itemID The ID of the actual Item
	 * @param name The name of the Item
	 * @param type The type of the Item // could be UNEQUIPABLE by standard
	 * @param stackable If it can be stacked
	 * @param maxInStack How much it can be stacked
	 */
	public ItemQuest(int questID, int itemID, String name, ItemSlot type, boolean stackable, int maxInStack)
	{
		super(itemID, name, type, 0, false, stackable, maxInStack);
		connectedQuestID = questID;
	}
	
	public int getQuestID()
	{
		return connectedQuestID;
	}
}
