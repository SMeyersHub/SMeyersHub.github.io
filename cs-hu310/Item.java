/**
 * The basic infrastructure for an item in the Item table.
 * @author Steven Meyers
 * @Date 7/2/2020
 */
public class Item {
	private String itemCode;
	private String itemDescription;
	private double price;
	
	public Item(String itemCode, String itemDescription, double price) {
		this.itemCode= itemCode;
		this.itemDescription = itemDescription;
		this.price = price;
	}
	/*
	 * Getters and setters for the Item Code.
	 */
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String newCode) {
		itemCode = newCode;
	}
	
	/*
	 * Getters and setters for the Item's description
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	
	public void setItemDescription(String newDescription) {
		itemDescription = newDescription;
	}
	/*
	 * Getters and setters for the Item's price
	 */
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double newPrice) {
		price = newPrice;
	}
	
	/**
	 * To string for the item.
	 */
	public String toString() {
		return String.format("(%s,%s,%s)", itemCode, itemDescription, price);
	}
}
