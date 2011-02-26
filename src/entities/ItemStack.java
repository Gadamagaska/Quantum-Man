package entities;

public class ItemStack {
	private Item item;
	private int amount;
	
	public ItemStack(Item item, int amount)
	{
		this.item = item;
		this.amount = amount;
	}
	
	public ItemStack(Item item)
	{
		this.item = item;
		amount = 1;
	}
}
