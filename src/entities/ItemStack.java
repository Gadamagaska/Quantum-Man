package entities;

public class ItemStack {
	private Item item;
	private int amount;
	private int max;
	private boolean canBeStacked;
	
	/**
	 * Creates a new ItemStack for an item.
	 * An ItemStack can and should be instanciated even
	 * when there is only one item in the stack initially.
	 * This constructor will initialize a stackable stack, up to max.
	 * @param item The item to add
	 * @param amount The number of items in the stack initially
	 * @param max The max number of items that can be in the stack
	 */
	public ItemStack(Item item, int amount, int max)
	{
		this.item = item;
		this.amount = amount;
		this.max = max; 
		// I could check here and see if max is lower than amount
		// and then throw a exception?
		canBeStacked = true;
	}
	
	/**
	 * Creates a new ItemSTack for an item, with the
	 * standard values 1 and max 1. Use this constructor
	 * for weapons and such.
	 * This constructor will not instantiate a stackable stack
	 * and can only have a stack of 1 item.
	 * @param item
	 */
	public ItemStack(Item item)
	{
		this.item = item;
		amount = 1;
		max = 1;
		canBeStacked = false;
	}
	
	/**
	 * Adds more items to the stack of items, up to the max value.
	 * If the initial stack, plus the additional number, is more than
	 * the allowed max value, then the leftover is returned.
	 * If the stack cannot be stacked, the moreItems value is returned.
	 * @param moreItems The number of items to add
	 * @return The left over number of items
	 */
	public int addItems(int moreItems)
	{
		if(canBeStacked){
			if(amount + moreItems > max){
				int leftOverItems = (amount + moreItems) - max;
				amount = max;
				return leftOverItems;
			} else {
				amount += moreItems;
				return 0;
			}
		} else {
			return moreItems;
		}
	}
	
	/**
	 * Removes items from the stack
	 * @param lessItems The number of items to remove
	 * @return The item that is in the stack
	 */
	public Item removeItems(int lessItems)
	{
		if(amount > lessItems){
			amount -= lessItems;
			return item;
		}
		return null;
		
		// if there are 0 items left in the stack, 
		// how do we deallocate the memory?
	}
	
	public int getNumberOfItemsInStack()
	{
		return amount;
	}
	
	public Item getItemInStack()
	{
		return item;
	}
}
