package entities;
import java.util.Iterator;

/**
 * Inventory is the class that will hold all the ItemStacks
 * that any character with an inventory use
 * @author Thediabloman
 */
public class Inventory implements Iterator<ItemStack> {
	private int size;
	private int numberOfItems;
	private ItemStack[] inventory;
	
	public Inventory(int size)
	{
		this.size = size;
		inventory = new ItemStack[size];
		numberOfItems = 0;
	}
	
	/**
	 * Adds an ItemStack to the inventory at slot 'slot'
	 * If there is already a item in that slot, the item will
	 * be returned
	 * @param item The item to be added to the inventory
	 * @param slot The slot to place the item
	 * @return The item that was in that slot already, or null if the space was free
	 */
	public ItemStack addItem(ItemStack item, int slot)
	{
		if(legalSlot(slot)){
			numberOfItems++;
			ItemStack temp = inventory[slot];
			inventory[slot] = item;
			return temp;
		} else {
			printIllegalSlotError(slot);
			return null;
		}
	}
	
	/**
	 * Adds an ItemStack to the inventory at the first open slot
	 * If there are no open slots the item will NOT be added
	 * @param item The item to be added
	 * @return true if the item was added, false if it was not
	 */
	public boolean addItem(ItemStack item)
	{
		if(isFull()) return false;
		
		for(int i = 0; i < size; i++){
			if(inventory[i] == null){
				inventory[i] = item;
				numberOfItems++;
				return true;
			}
		}
		
		return false;
		
		
		
	}
	
	/**
	 * See an item from the inventory, without removing it
	 * @param slot The slot in the inventory
	 * @return The item at that slot, null if it was empty
	 */
	public ItemStack seeItem(int slot)
	{
		if(legalSlot(slot)){
			return inventory[slot];
		} else {
			printIllegalSlotError(slot);
			return null;
		}
	}
	
	/**
	 * Take an item from the inventory and remove it
	 * @param slot The slot in the inventory
	 * @return The item at that slot, null if there was none
	 */
	public ItemStack takeItem(int slot)
	{
		if(legalSlot(slot)){
			ItemStack temp = inventory[slot];
			inventory[slot] = null;
			numberOfItems--;
			
			return temp;			
		} else {
			printIllegalSlotError(slot);
			return null;
		}
	}

public Iterator<ItemStack> getIterator()
{
//TODO
}
	
	/**
	 * @return true if the slot is within the range of the inventory
	 */
	private boolean legalSlot(int slot)
	{
		return slot < size && slot >= 0;
	}
	
	/**
	 * @return true if there is no more room in the inventory
	 */
	public boolean isFull()
	{
		return size == numberOfItems;
	}
	
	private void printIllegalSlotError(int slot)
	{
		System.out.println("Illegal SLOT: " + slot);
	}
}
