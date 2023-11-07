import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Program that manages an sql database through a java interface. Includes operations such as adding, deleting, and getting items, purchases,
 * and shipments.
 * @author Steven Meyers
 * @date 7/2/2020
 */
public class Project {
	
	/**
	 * Adds a new item to the Item table on the database.
	 * @param itemCode - Code of item being created.
	 * @param itemDescription -The description of the item being created.
	 * @param price - The price of the item being created.
	 * @return item - An item with the provided itemCode, itemDesc
	 * @throws SQLException
	 */
	public static Item createItem(String itemCode, String itemDescription, double price) throws SQLException {
		Connection connection = null;

		Item item = new Item(itemCode, itemDescription, price);
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sqlOutput = String.format("INSERT INTO Item(ItemCode, ItemDescription, Price) VALUES ('%s', '%s', %s);",
				item.getItemCode(),
				item.getItemDescription(),
				item.getPrice());
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();

		return item;
	}
	
	/**
	 * Attempts to create a new item using a stored SQL procedure.
	 * @param itemCode - code of item being created
	 * @param itemDescription - description of item being created
	 * @param price - price of item being created
	 * @return item - item being created toString
	 * @throws SQLException
	 */
	public static Item createItemUsingStoredProcedure(String itemCode, String itemDescription, double price) throws SQLException {
		Connection connection = null;
		Item item = new Item(itemCode, itemDescription, price);
		
		connection = MySqlDatabase.getDatabaseConnection();
		String sql = "CALL create_item(?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1, itemCode);
		preparedStatement.setString(2, itemCode);
		preparedStatement.setDouble(3, price);
		
		preparedStatement.execute();
		connection.close();
		
		return item;
	}
	
	/**
	 * Adds an item to the Purchase table
	 * @param itemCode - Code of the item being purchased
	 * @param quantity - Amount of items being purchased
	 * @return purchase - Purchase with the itemcode and quantity input.
	 * @throws SQLException
	 */
	public static Purchase createPurchase(String itemCode, int quantity) throws SQLException {
		Connection connection = null;
		Purchase purchase = new Purchase(itemCode, quantity);
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement= connection.createStatement();
		
		String sqlOutput = String.format(
				"Insert into Purchase(Quantity, ItemID) SELECT %s, ID FROM Item WHERE ItemCode LIKE '%s';", 
				purchase.getQuantity(),
				purchase.getItemCode());
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
		
		return purchase;
	}
	
	/**
	 * Creates and adds a shipment to the Shipment table
	 * @param itemCode - Code of the item being shipped
	 * @param quantity - Quantity of items being shipped
	 * @param shipmentDate - Date (in a string format) of the shipment
	 * @return shipment - shipment with the itemCode, quantity, and shipmentDate input.
	 * @throws SQLException
	 */
	public static Shipment createShipment(String itemCode, int quantity, String shipmentDate) throws SQLException {
		Connection connection = null;
		Shipment shipment = new Shipment(itemCode, quantity, shipmentDate);
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sqlOutput = String.format("Insert into Shipment(Quantity, ShipmentDate, ItemID) SELECT %s, '%s', ID FROM Item WHERE ItemCode LIKE '%s';", 
				shipment.getQuantity(),
				shipment.getDate(),
				shipment.getItemCode());
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
		return shipment;
	}
	
	/**
	 *  Gets items with the fitting itemcode, or all items using %.
	 * @param itemCode - Code of item being retrieved from table or % for all.
	 * @return items - Array list of items fitting the limit
	 * @throws SQLException
	 */
	public static List<Item> getItems(String itemCode) throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		if(itemCode.equals("%")) {
			String sqlOutput = "SELECT * FROM Item";
			ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
			List<Item> items = new ArrayList<Item>();
			
			while (resultSet.next()) {
				String resultItemCode = resultSet.getString(2);
				String resultItemDesc = resultSet.getString(3);
				double resultPrice = resultSet.getDouble(4);
				
				Item item = new Item(resultItemCode, resultItemDesc, resultPrice);
				items.add(item);
			}
			resultSet.close();
			connection.close();
			return items;
		} else {
			String sqlOutput = String.format("SELECT * FROM Item WHERE ItemCode LIKE '%s';", itemCode);
			ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
			List<Item> items = new ArrayList<Item>();
			
			while (resultSet.next()) {
				String resultItemCode = resultSet.getString(2);
				String resultItemDesc = resultSet.getString(3);
				double resultPrice = resultSet.getDouble(4);
				
				Item item = new Item(resultItemCode, resultItemDesc, resultPrice);
				items.add(item);
			}
			resultSet.close();
			connection.close();
			return items;
		}
	}
	
	/**
	 * Gets the purchases with the item code, or % for all.
	 * @param itemCode - Code of the items in the purchases being retrieved.
	 * @return purchases - ArrayList of purchases fitting the limiter of the itemCode.
	 * @throws SQLException
	 */
	public static List<Purchase> getPurchases(String itemCode) throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		if(itemCode.equals("%")) {
			String sqlOutput = "SELECT * FROM Purchase";
			ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
			List<Purchase> purchases = new ArrayList<Purchase>();
			
			while (resultSet.next()) {
				String itemID = resultSet.getString(2);
				int quantity = resultSet.getInt(3);
				
				Purchase purchase = new Purchase(itemID, quantity);
				purchases.add(purchase);
			}
			resultSet.close();
			connection.close();
			return purchases;
		} else {
			String sqlOutput = String.format("SELECT * FROM Purchase WHERE ItemID LIKE (SELECT ID FROM Item WHERE ItemCode LIKE '%s');", itemCode);
			ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
			List<Purchase> purchases = new ArrayList<Purchase>();
			
			while (resultSet.next()) {
				String itemID = resultSet.getString(2);
				int quantity = resultSet.getInt(3);
				
				Purchase purchase = new Purchase(itemID, quantity);
				purchases.add(purchase);
			}
			resultSet.close();
			connection.close();
			return purchases;
		}
	}
	
	/**
	 * Gets shipments with the associated itemCode from the shipments table.
	 * @param itemCode - code of item limiting the return from shipments.
	 * @return shipments - ArrayList of shipments fitting the limiter itemCode or % for all.
	 * @throws SQLException
	 */
	public static List<Shipment> getShipments(String itemCode) throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		if(itemCode.equals("%")) {
			String sqlOutput = "SELECT * FROM Shipment";
			ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
			List<Shipment> shipments = new ArrayList<Shipment>();
			
			while (resultSet.next()) {
				String itemID = resultSet.getString(2);
				int quantity = resultSet.getInt(3);
				String shipmentDate = resultSet.getString(4);
				
				Shipment shipment = new Shipment(itemID, quantity, shipmentDate);
				shipments.add(shipment);
			}
			resultSet.close();
			connection.close();
			return shipments;
		} else {
			String sqlOutput = String.format("SELECT * FROM Shipment WHERE ItemID LIKE (SELECT ID FROM Item WHERE ItemCode LIKE '%s');", itemCode);
			ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
			List<Shipment> shipments = new ArrayList<Shipment>();
			
			while (resultSet.next()) {
				String itemID = resultSet.getString(2);
				int quantity = resultSet.getInt(3);
				String shipmentDate = resultSet.getString(4);
				
				Shipment shipment = new Shipment(itemID, quantity, shipmentDate);
				shipments.add(shipment);
			}
			resultSet.close();
			connection.close();
			return shipments;
		}
	}
	
	/**
	 * Returns 3 rows: itemCode, itemDescription, and a calculation called itemsAvailable
	 * @param itemCode - code of item that user wants to see items available of, % for all.
	 * @return results - a string list that has all the result statements from the loops.
	 * @throws SQLException
	 */
	public static List<String> itemsAvailable(String itemCode) throws SQLException {
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		if(itemCode.equals("%")) {
			ResultSet resultSet = null;
			List<String> results = new ArrayList<String>();
			for(int i = 0; i <= getItems("%").size(); i++) {
				String sqlOutput =  String.format("SELECT ItemCode, ItemDescription, (SELECT IFNULL(SUM(Quantity), 0) FROM Shipment WHERE ItemID = '%s') - (SELECT IFNULL(SUM(Quantity), 0) FROM Purchase WHERE ItemID = '%s') AS 'Items Remaining' FROM Item WHERE ID = '%s';", i, i, i);
				resultSet = sqlStatement.executeQuery(sqlOutput);
				
				while (resultSet.next()) {
					String itemID = resultSet.getString(1);
					String itemDescription = resultSet.getString(2);
					String itemsAvailable = resultSet.getString(3);
					
					String result = String.format("('%s','%s','%s')", itemID, itemDescription, itemsAvailable);
					results.add(result);
				}
			}
			resultSet.close();
			connection.close();
			return results;
		} else {
			String sqlOutput = String.format("SELECT ItemCode, ItemDescription, (SELECT IFNULL(SUM(Quantity), 0) FROM Shipment WHERE ItemID = (SELECT ID FROM Item WHERE ItemCode = '%s')) - (SELECT IFNULL(SUM(Quantity), 0) FROM Purchase WHERE ItemID = (SELECT ID FROM Item WHERE ItemCode = '%s')) AS 'Items Remaining' FROM Item WHERE ItemCode = '%s';", itemCode, itemCode, itemCode);
			ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
			List<String> results = new ArrayList<String>();
			
			while (resultSet.next()) {
				String itemID = resultSet.getString(1);
				String itemDescription = resultSet.getString(2);
				String itemsAvailable = resultSet.getString(3);
				
				String result = String.format("('%s','%s','%s')", itemID, itemDescription, itemsAvailable);
				results.add(result);
			}
			resultSet.close();
			connection.close();
			return results;
		}
	}
	/**
	 * Updates the item with itemCode to have a new price.
	 * @param itemCode - Code of item having price updated.
	 * @param newPrice - New price of item being updated.
	 * @throws SQLException
	 */
	public static void updateItem(String itemCode, double newPrice) throws SQLException {
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sqlOutput = String.format("UPDATE Item SET price = %s WHERE ItemCode LIKE '%s'",
				newPrice,
				itemCode);
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
	}
	
	/**
	 * Deletes the item with the item code.
	 * @param itemCode - Code of item being deleted.
	 * @throws SQLException
	 */
	public static void deleteItem(String itemCode) throws SQLException {
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sqlOutput = String.format("DELETE FROM Item WHERE ItemCode='%s';" , 
				itemCode);
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
	}
	
	/**
	 * Deletes the most recent purchase of the item with the itemCode.
	 * @param itemCode - Code of item in purchase being deleted.
	 * @throws SQLException
	 */
	public static void deletePurchase(String itemCode) throws SQLException{
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sqlOutput = String.format("DELETE FROM Purchase WHERE ItemID LIKE (SELECT ID FROM Item WHERE ItemCode LIKE '%s') ORDER BY PurchaseDate DESC LIMIT 1;", 
				itemCode);
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
	}
	
	/**
	 *  Deletes the most recent shipment of the item with the itemCode.
	 * @param itemCode - Code of item in shipment being deleted.
	 * @throws SQLException
	 */
	public static void deleteShipment(String itemCode) throws SQLException{
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sqlOutput = String.format("DELETE FROM Shipment WHERE ItemID LIKE (SELECT ID FROM Item WHERE ItemCode LIKE '%s') ORDER BY ShipmentDate DESC LIMIT 1;", 
				itemCode);
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
	}
	
	/**
	 * Attempts to create an item using createItem and prints errors if they occur.
	 * @param itemCode - code of the item being created.
	 * @param itemDescription - description of item being created.
	 * @param price - price of item being created.
	 */
	public static void attemptToCreateItem(String itemCode, String itemDescription, double price) {
		try {
			Item item = createItem(itemCode, itemDescription, price);
			System.out.println(item.toString());
		} catch (SQLException sqlException) {
			System.out.println("Item creation failed.");
			System.out.println(sqlException.getMessage());
		}
		
	}
	
	public static void attemptToCreateItemUsingStoredProcedure(String itemCode, String itemDescription, double price) {
		try {
			Item item = createItemUsingStoredProcedure(itemCode, itemDescription, price);
			System.out.println(item.toString());
		} catch (SQLException sqlException) {
			System.out.println("Stored procedure item creation failed.");
			System.out.println(sqlException.getMessage());
		}
	}
	
	/**
	 * Attempts to create a purchase using createPurchase and prints errors.
	 * @param itemCode - code of item in purchase being created.
	 * @param quantity - quantity of item being purchased.
	 */
	public static void attemptToCreatePurchase(String itemCode, int quantity) {
		try {
			Purchase purchase = createPurchase(itemCode, quantity);
			System.out.println(purchase.toString());
		} catch(SQLException sqlException) {
			System.out.println("Purchase creation failed.");
			System.out.println(sqlException.getMessage());
		}
	}

	/**
	 * Attempts to create a shipment using createShipment and prints errors.
	 * @param itemCode - code of item in shipment being created.
	 * @param quantity - quantity of shipment items.
	 * @param shipmentDate - date of shipment.
	 */
	public static void attemptToCreateShipment(String itemCode, int quantity, String shipmentDate) {
		try {
			Shipment shipment = createShipment(itemCode, quantity, shipmentDate);
			System.out.println(shipment.toString());
		} catch(SQLException sqlException) {
			System.out.println("Shipment creation failed.");
			System.out.println(sqlException.getMessage());
		}
	}
	
	
	/**
	 * Attempts to get items using getItems and prints errors.
	 * @param itemCode - the code of the items attempting to get.
	 */
	public static void attemptToGetItems(String itemCode) {
		try {
			List<Item> items = getItems(itemCode);
			for (Item item : items) {
				System.out.println(item.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get items");
			System.out.println(sqlException.getMessage());
		}
		
	}
	
	/**
	 * Attempts to get purchases using getPurchases and prints errors.
	 * @param itemCode - the code of the items in purchases attempting to get.
	 */
	public static void attemptToGetPurchases(String itemCode) {
		try {
			List<Purchase> purchases = getPurchases(itemCode);
			for (Purchase purchase : purchases) {
				System.out.println(purchase.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get purchases.");
			System.out.println(sqlException.getMessage());
		}
		
	}
	
	/**
	 * Attempts to get shipments using getShipments and prints errors.
	 * @param itemCode - code of item in shipment.
	 */
	public static void attemptToGetShipments(String itemCode) {
		try {
			List<Shipment> shipments = getShipments(itemCode);
			for (Shipment shipment : shipments) {
				System.out.println(shipment.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get shipments");
			System.out.println(sqlException.getMessage());
		}
		
	}
	
	public static void attemptToItemsAvailable(String itemCode) {
		try {
			List<String> results = itemsAvailable(itemCode);
			for (String result : results) {
				System.out.println(result);
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get available items");
			System.out.println(sqlException.getMessage());
		}
		
	}
	/**
	 * Attempts to delete the item with the item code and prints errors if it doesn't work.
	 * @param itemCode - code of item attempting to be deleted.
	 */
	public static void attemptToDeleteItem(String itemCode) {
		try {
			deleteItem(itemCode);
			System.out.println("Item has been deleted!");
		} catch (SQLException sqlException) {
			System.out.println("Failed to remove item.");
			System.out.println(sqlException.getMessage());
		}
	}
	
	/**
	 * Attempts to delete the most recent purchase with the itemCode.
	 * @param itemCode - itemCode in purchase attempting to be deleted.
	 */
	public static void attemptToDeletePurchase(String itemCode) {
		try {
			deletePurchase(itemCode);
			System.out.println("Purchase has been deleted!");
		} catch (SQLException sqlException) {
			System.out.println("Failed to delete purchase.");
			System.out.println(sqlException.getMessage());
		}
	}
	
	/**
	 * Attempts to delete a shipment with the item with the itemCode and prints errors if it doesn't work.
	 * @param itemCode - itemCode in shipment attempting to be deleted.
	 */
	public static void attemptToDeleteShipment(String itemCode) {
		try {
			deleteShipment(itemCode);
			System.out.println("Shipment has been deleted!");
		} catch (SQLException sqlException) {
			System.out.println("Failed to delete shipment.");
			System.out.println(sqlException.getMessage());
		}
	}
	
	/**
	 * Attempts to update the item's price with the item code and prints errors if not.
	 * @param itemCode - itemCode of item being updated.
	 * @param newPrice - new price being updated in item.
	 */
	public static void attemptToUpdateItem(String itemCode, double newPrice) {
		try {
			updateItem(itemCode, newPrice);
			System.out.println("Item's price has been updated!");
		} catch (SQLException sqlException) {
			System.out.println("Failed to update item");
			System.out.println(sqlException.getMessage());
		}
	}
	
	/**
	 * Main method that runs the program and links to all the other methods.
	 * @param args args corresponding with the correct method and variables.
	 */
	public static void main(String[] args) {
		try {
		if(args[0].equals("CreateItem")) {
			String itemCode = args[1];
			String itemDescription = args[2];
			double price = Double.parseDouble(args[3]);
			attemptToCreateItem(itemCode, itemDescription, price);
			
		} else if (args[0].contentEquals("CreatePurchase")) {
			String itemCode = args[1];
			int quantity = Integer.parseInt(args[2]);
			attemptToCreatePurchase(itemCode, quantity);
			
		} else if (args[0].contentEquals("CreateShipment")) {
			String itemCode = args[1];
			int quantity = Integer.parseInt(args[2]);
			String shipmentDate = args[3];
			attemptToCreateShipment(itemCode, quantity, shipmentDate);
		
		} else if(args[0].contentEquals("GetItems")) {
			String itemCode = args[1];
			attemptToGetItems(itemCode);
			
		} else if(args[0].contentEquals("GetShipments")) {
			String itemCode = args[1];
			attemptToGetShipments(itemCode);
			
		} else if(args[0].contentEquals("GetPurchases")) {
			String itemCode = args[1];
			attemptToGetPurchases(itemCode);
			
		} else if(args[0].contentEquals("UpdateItem")) {
			String itemCode = args[1];
			double newPrice = Double.parseDouble(args[2]);
			attemptToUpdateItem(itemCode, newPrice);
			
		} else if(args[0].contentEquals("DeleteItem")) {
			String itemCode = args[1];
			attemptToDeleteItem(itemCode);
			
		} else if(args[0].contentEquals("DeleteShipment")) {
			String itemCode = args[1];
			attemptToDeleteShipment(itemCode);
			
		} else if(args[0].contentEquals("DeletePurchase")) {
			String itemCode = args[1];
			attemptToDeletePurchase(itemCode);
			
		} else if(args[0].contentEquals("ItemsAvailable")) {
			String itemCode = args[1];
			attemptToItemsAvailable(itemCode);
			
		} else if(args[0].contentEquals("CreateItemUsingStoredProcedure")) {
			String itemCode = args[1];
			String itemDescription = args[2];
			double price = Double.parseDouble(args[3]);
			attemptToCreateItemUsingStoredProcedure(itemCode, itemDescription, price);
		} 
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Please use one of the following commands: ");
			System.out.println("CreateItem [Item Code] [Item Description] [Item Price]");
			System.out.println("CreateItemUsingStoredProcedure [Item Code] [Item Description] [Price]");
			System.out.println("CreatePurchase [Item Code] [Quantity of Purchase]");
			System.out.println("CreateShipment [Item Code] [Quantity of Shipment] [Shipment Date (YYYY-MM-DD)]");
			System.out.println("GetItems [Item Code (use % for all items)]");
			System.out.println("GetPurchases [Item Code (use % for all purchases)]");
			System.out.println("GetShipments [Item Code (use % for all shipments)]");
			System.out.println("ItemsAvailable [Item Code (use % for all items)]");
			System.out.println("UpdateItem [Item Code] [New Price for Item]");
			System.out.println("DeleteItem [Item Code]");
			System.out.println("DeletePurchase [Item Code]");
			System.out.println("DeleteShipment [Item Code]");
		}
	}

}
