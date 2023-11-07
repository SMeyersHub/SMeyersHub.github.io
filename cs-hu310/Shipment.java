/**
 * Creates the basic structure for a shipment to be added to the Shipment table.
 * @author Steven Meyers
 * @date 7/2/2020
 */
public class Shipment {
	private String itemCode;
	private int quantity;
	private String shipmentDate;
	
	public Shipment(String itemCode, int quantity, String shipmentDate) {
		this.itemCode = itemCode;
		this.quantity = quantity;
		this.shipmentDate = shipmentDate;
	}
	
	/*
	 * Getters and setters of Shipment's item code
	 */
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String newItemCode) {
		this.itemCode = newItemCode;
	}
	
	/*
	 * Getters and setters of quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int newQuantity) {
		this.quantity = newQuantity;
	}
	
	/*
	 * Getters and setters of date
	 */
	public String getDate() {
		return shipmentDate;
	}
	
	public void setDate(String newDate) {
		this.shipmentDate = newDate;
	}
	
	/*
	 * To string for the shipment.
	 */
	public String toString() {
		return String.format("(%s,%s, %s)", itemCode, quantity, shipmentDate);
	}
}
