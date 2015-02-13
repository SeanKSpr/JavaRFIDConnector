package edu.auburn.eng.sks0024.rfid_connector;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
//import java.util.List;

/**
 * PostgresConnector is the implementation of RFIDDatabaseManager used to write to our database. Currently based
 * on Sean's interface and its desired functionality.
 * 
 * @since February 5, 2015
 * @version 1
 * @author Jared Watkins
 * 
 */

public class PostgresConnector implements RFIDDatabaseManager {

	/**
	 * Function:		open
	 * 
	 * Precondition:	Connection to database hasn't been established
	 * Postcondition:	Connection to database is established
	 * @return The newly established Connection to the database
	 */
	public Connection open() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/rfidb","rfidweb", "rfidweb");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.out.println("Exception occurred while opening db connection");
		}
		return c;
	}
	
	/**
	 * Function:		close
	 * 
	 * Precondition:	Connection to database is currently established
	 * Postcondition:	Connection to database is severed
	 * @param c The current Connection to the database
	 */
	public void close(Connection c) {
		try {
			c.close();
		} catch (Exception e) {
			System.out.println("Exception occurred when closing db connection");
		}
	}
	
	/**
	 * Function:		insertTag
	 * 
	 * Precondition:	tag hasn't doesn't exist in the database
	 * Postcondition:	tag exists in the database
	 * @param tag RFID Tag which hasn't been added to the database
	 * @param c The current Connection to the database
	 * @return True if the insertion was successful, False otherwise
	 */
	public boolean insertTag(TagWrapper tag, Connection c){

		try {
//			Statement stmt = c.createStatement();
//			String sql = "INSERT into upc_descriptions values" +
//					"(3, 151071851756, 'Wrangler', '50/30', 'strech', 'Men', 'blue')," +
//					"(4, 772787789760, 'Rustler', '33/30', 'classic', 'Men', 'blue');" +
//					"INSERT into products values(3, 1, 2, 'in_store')," +
//					"(4, 2, 2, 'warehouse'), (5, 2, 3, 'warehouse');";
//	        System.out.println(sql);
//			stmt.executeUpdate(sql);
//			
//			stmt.close();
//			c.commit();
			return true;
		} catch (Exception e) {
			System.out.println("Error occurred while inserting default values into the database");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Function:		updateTag
	 * 
	 * Precondition:	Tag exists in the database and the input tag has more up-to-date information
	 * Postcondition:	Tag entry in the database matches the information of the input tag
	 * @param tag RFID Tag which exists within the database but has more up-to-date information
	 * @param c The current Connection to the database
	 * @return True if the update was successful, False otherwise
	 */
	public boolean updateTag(TagWrapper tag, Connection c){
		try {
		    long upc = Long.parseLong(EPCConverter.getUPC(tag.getTag().getEpc().toWordList())); //get upc from this
		    long serialNum = (EPCConverter.getSerial(tag.getTag().getEpc().toWordList()));
		    System.out.println("UPC: " + upc);
		    System.out.println("Serial: " + serialNum);//get serial num from this
			Statement stmt = c.createStatement();
	        String sql = "SELECT products.id as productid,upc_descriptions.id as upcid, upc, upc_description_id, serial_num, location, vendor, fit, style FROM products JOIN upc_descriptions on upc_descriptions.id = products.upc_description_id where upc_descriptions.upc = " + upc +" and serial_num = "+ serialNum +";";
	        
	        ResultSet rs = stmt.executeQuery(sql);
	        rs.next();
	        int id = rs.getInt("productid");
	        String dbLocation = rs.getString("location");
	        TagLocation currLoc = convertLocation(dbLocation);
	        TagLocation tl = tag.getLocation().getNewLocation(currLoc, tag.getLocationScanned());
	        System.out.println("Scanner location: " + tag.getLocationScanned());
	        System.out.println("Current tag location: " + currLoc);
	        System.out.println("New tag location: " + tl);
			String location = convertLocation(tl);
			System.out.println("Updated item location: " + location);
	        
	        if (!dbLocation.equalsIgnoreCase(location)) {
		        sql = "UPDATE PRODUCTS set LOCATION = '" + location + "' where ID=" + id + ";";
		        System.out.println(sql);
				stmt.executeUpdate(sql);
				
				stmt.close();
				c.commit();
	        }
			return true;
		} catch (Exception e) {
			System.out.println("Error occurred while updating tag");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	/**
	 * Function:		convertLocation
	 * 
	 * Precondition:	TagLocation has been recieved from the given TagWrapper object
	 * Postcondition:	TagLocation value has been converted to its String representation
	 * @param loc The TagLocation enum value we need to convert to the String value for the database
	 * @return The String location to enter in the database
	 */
	public String convertLocation(TagLocation loc) {
		switch(loc) {
		case BACK_ROOM:
			return "back room";
		case STORE_FLOOR:
			return "on store floor";
		case WAREHOUSE:
			return "warehouse";
		case OUT_OF_STORE:
			return "out of store";
		default:
			return "";
		}
	}
	
	/**
	 * Function:		convertLocation
	 * 
	 * Precondition:	TagLocation has been recieved from the given TagWrapper object
	 * Postcondition:	TagLocation value has been converted to its String representation
	 * @param loc The String value we need to convert to the TagLocation enum value for the database
	 * @return The TagLocation from the database to compare to the new location from the reader
	 */
	public TagLocation convertLocation(String loc) {
		switch(loc) {
		case "back room":
			return TagLocation.BACK_ROOM;
		case "on store floor":
			return TagLocation.STORE_FLOOR;
		case "warehouse":
			return TagLocation.WAREHOUSE;
		case "out of store":
			return TagLocation.OUT_OF_STORE;
		default:
			return null;
		}
	}
	
	/**
	 * Function:		deleteTag
	 * I can add this later, but it doesn't seem like we need to delete tags via Java database access
	 * Precondition:	Tag matching the input Tag exists within the database 
	 * Postcondition:	Tag and associated information has been deleted from the database
	 * @param tag RFID Tag which exists within the database and is to be deleted along with any other information in the 
	 * database which belongs to that Tag
	 * @return True if the deletion was successful, False otherwise
	 */
	public boolean deleteTag(TagWrapper tag, Connection c){return false;}
	
	/**
	 * Function:		getAllTags
	 * Not really sure if we need this right now either
	 * Precondition:	Connection to database has been established
	 * Postcondition:	All tags have been retrieved from the database 
	 * @param c Current Connection object corresponding to the database
	 */
	public void /*List<TagWrapper>*/ getAllTags(Connection c){
		try {
			Statement stmt = c.createStatement();
	        String sql = "SELECT products.id as productid, upc_descriptions.id as upcid, upc, upc_description_id, serial_num, location, vendor, fit, style FROM products JOIN upc_descriptions ON upc_descriptions.id = products.upc_description_id";
	        //sql = "SELECT all FROM products";
	        ResultSet rs = stmt.executeQuery(sql);
	        System.out.println("Grabbed db rows");
	        while(rs.next()) {
	        	int prodId = rs.getInt("productid");
	        	int upcId = rs.getInt("upcid");
	        	long upc = rs.getLong("upc");
	        	int upcDescId = rs.getInt("upc_description_id");
	        	long serialNum = rs.getLong("serial_num");
	        	String location = rs.getString("location");
	        	String vendor = rs.getString("vendor");
	        	String fit = rs.getString("fit");
	        	String style = rs.getString("style");
	        	System.out.println(prodId + " | " + upcId + " | " + upc + " | " + upcDescId + 
	        			" | " + serialNum + " | " + location + " | " + vendor + " | " + fit + 
	        			" | " + style);
	        }/**/
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error occurred while fetching all tags from the db");
		}
	}
	
	/**
	 * Function:		getTag
	 * I don't see why the database will need to fetch data from the db and generate tags from it
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Tag associated with the input id has been retrieved from the database
	 * @param id Key to retrieve the associated Tag from the database; the key is the EPC
	 * @return Tag associated with the id
	 */
	public TagWrapper getTag(long id, Connection c){return null;}
	
	/**
	 * Function:		dropTable
	 * I do not believe we need to initialize or drop our tables via the Java Database Manager, since
	 * the schema should already be constructed and all of the tags should be in the table as well.
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Table which shares the same as the input is dropped from the database along with any data belonging
	 * to entries in the dropped table
	 * @param tableName Name of the table in the database to be dropped
	 */
	public void dropTable(String tableName, Connection c){}
	
	/**
	 * Function:		initializeTable
	 * I do not believe we need to initialize or drop our tables via the Java Database Manager, since
	 * the schema should already be constructed and all of the tags should be in the table as well.
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Table with the same name as the input String is initialized with default information
	 * @param tableName Name of the table in the database to be filled with default values
	 */
	public void initializeTable(String tableName, Connection c){}
}
