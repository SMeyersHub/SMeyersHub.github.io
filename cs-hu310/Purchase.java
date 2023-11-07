/**
 * This class sets up the basic structure for a purchase in the purchase
 * table. Consists of a quantity and item code to be inserted into the table.
 * @author Steven Meyers
 * @date 7/2/2020
 */
public class Purchase {
	private String itemCode;
	private int quantity;
	
	public Purchase(String itemCode, int quantity) {
		this.itemCode = itemCode;
		this.quantity = quantity;
	}
	
	/*
	 * Getters and setters of the Purchase's item code
	 */
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String newItemCode) {
		newItemCode = itemCode;
	}
	
	/*
	 * Getters and setters of quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int newQuantity) {
		quantity = newQuantity;
	}
	
	/*
	 * To string for an item.
	 */
	public String toString() {
		return String.format("(%s,%s)", itemCode, quantity);
	}
}


