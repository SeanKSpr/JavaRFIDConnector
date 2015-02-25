package edu.auburn.eng.sks0024.rfid_connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.List;
import java.util.Properties;

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
	 * Open new connection to our PostgreSQL database
	 * 
	 * Precondition:	Connection to database hasn't been established
	 * Postcondition:	Connection to database is established
	 * @return The newly established Connection to the database
	 */
	public Connection open() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/rfiddb","postgres","password");
			//c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/rfidb","rfidweb", "rfidweb");
			
			//Added by Sean for testing purposes////////////////////////////////////////////
			Properties connectionProperties = c.getClientInfo();
			connectionProperties.setProperty("url", "jdbc:postgresql://localhost:5432/rfiddb");
			connectionProperties.setProperty("user", "postgres");
			c.setClientInfo(connectionProperties);
			////////////////////////////////////////////////////////////////////////////////
			
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
	 * Close the database connection
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
	 * This function will add new tags for existing UPCs in the database. This would be used
	 * when employees are moving new inventory into the store through whichever specified area.
	 * 
	 * Precondition:	tag hasn't doesn't exist in the database
	 * Postcondition:	tag exists in the database
	 * @param tag RFID Tag which hasn't been added to the database
	 * @param c The current Connection to the database
	 * @return True if the insertion was successful, False otherwise
	 */
	public boolean insertTag(TagWrapper tag, Connection c){

		try {
		    long upc = Long.parseLong(EPCConverter.getUPC(tag.getTag().getEpc().toWordList())); //get upc from this
		    long serialNum = (EPCConverter.getSerial(tag.getTag().getEpc().toWordList()));
			addTagToDatabase(upc, serialNum, c);
			return true;
		} catch (Exception e) {
			System.out.println("Error occurred while inserting new value into the database");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean addTagToDatabase(long tagUPC, long tagSerial, Connection c){
		try {
			if (findTagInDatabase(tagUPC, tagSerial, c)) {
				return false;
			}
			Statement stmt = c.createStatement();
			int id = getUPCId(tagUPC, c);
			String sql = "INSERT into products(upc_description_id, serial_num, location) values " //+ " (upc_id, serial, location)"
			+ "(" + id + ", " + tagSerial + ", '" + TagLocation.convertLocation(TagLocation.BACK_ROOM) + "')";
			stmt.executeUpdate(sql);
			
			stmt.close();
			c.commit();
			return true;
		} catch (Exception e) {
			System.out.println("Error occurred while inserting value into the database");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public int getUPCId(long upc, Connection c) {
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT id FROM upc_descriptions WHERE upc = " + upc; //+ " (upc_id, serial, location)"
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.next();
			return rs.getInt("id");
			
		} catch (Exception e) {
			System.out.println("Error occurred while getting UPC id from the database");
			return 0;
		}
	}
	
	/**
	 * Function:		updateTag
	 * 
	 * Changes the location of the read Tag to the other applicable location for the scanner which read the Tag
	 * 
	 * Precondition:	Tag exists in the database and the input tag has more up-to-date information
	 * Postcondition:	Tag entry in the database matches the information of the input tag
	 * @param tag RFID Tag which exists within the database but has more up-to-date information
	 * @param c The current Connection to the database
	 * @return True if the update was successful, False otherwise
	 */
	public boolean updateTag(TagWrapper tag, Connection c){
	    long upc = Long.parseLong(EPCConverter.getUPC(tag.getTag().getEpc().toWordList())); //get upc from this
	    long serialNum = (EPCConverter.getSerial(tag.getTag().getEpc().toWordList()));//get serial num from this
        return updateTagInDatabase(upc, serialNum, tag.getLocationScanned(), c);
	}
	
	public boolean updateTagInDatabase(long upc, long serial, ReaderLocation rl, Connection c) {
		try {
			Statement stmt = c.createStatement();

			int id = getTagID(upc, serial, c);
			String dbLocation = getTagLocation(id, c);
			
	        TagLocation currLoc = TagLocation.convertLocation(dbLocation);
			TagLocation newLoc = TagLocation.getNewLocation(currLoc, rl);
			String location = TagLocation.convertLocation(newLoc);
			
	        if (!dbLocation.equalsIgnoreCase(location) && !dbLocation.equalsIgnoreCase("out of store")) {
		        String sql = "UPDATE PRODUCTS set LOCATION = '" + location + "' where ID=" + id + ";";
		        System.out.println(sql);
				stmt.executeUpdate(sql);
				
				stmt.close();
				c.commit();
				return true;
	        }
			return false;
		} catch (Exception e) {
			System.out.println("Error occurred while updating tag");
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public int getTagID(long upc, long serial, Connection c) {

		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT products.id as productid FROM products JOIN upc_descriptions on upc_descriptions.id = products.upc_description_id where upc_descriptions.upc = " + upc +" and serial_num = "+ serial +";";
	        
	        ResultSet rs = stmt.executeQuery(sql);
	        rs.next();
	        int id = rs.getInt("productid");
			stmt.close();
			return id;
		} catch (Exception e) {
			System.out.println("Error occurred while obtaining Tag ID from database");
			return 0;
		}
	}
	
	public String getTagLocation(int id, Connection c) {

		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT id, location FROM products WHERE id = " + id + ";";
	        
	        ResultSet rs = stmt.executeQuery(sql);
	        rs.next();
	        String dbLocation = rs.getString("location");
			stmt.close();
			return dbLocation;
		} catch (Exception e) {
			System.out.println("Error occurred while obtaining Tag location from database");
			return null;
		}
	}
	
	/**
	 * Function:		findTag
	 * This determines if a given Tag has been seen before in our database; if the Tag has not been seen,
	 * let caller know so we can add the new Tag
	 * Precondition:	Tag matching the input Tag exists within the database 
	 * Postcondition:	Tag and associated information has been deleted from the database
	 * @param tag RFID Tag which exists within the database and is to be deleted along with any other information in the 
	 * database which belongs to that Tag
	 * @return True if the Tag exists, False otherwise
	 */
	public boolean findTag(TagWrapper tag, Connection c){
		
		try {
		    long upc = Long.parseLong(EPCConverter.getUPC(tag.getTag().getEpc().toWordList())); //get upc from this
		    long serialNum = (EPCConverter.getSerial(tag.getTag().getEpc().toWordList()));//get serial num from this
		    return findTagInDatabase(upc, serialNum, c);
		} catch (Exception e) {
			System.out.println("Error occurred while locating Tag in the database");
			return false;
		}
	}
	
	public boolean findTagInDatabase(long upcTag, long serialTag, Connection c) throws Exception {
		Statement stmt = c.createStatement();
		String sql = "SELECT * FROM products JOIN upc_descriptions ON upc_descriptions.id = products.upc_description_id WHERE upc = " + upcTag + " AND serial_num = " + serialTag + ";";
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.next()) { //condition passes if no valid rows in rs
			stmt.close();
			System.out.println("Did not find Tag");
			return false;
		}
		stmt.close();
		System.out.println("Found Tag");
		return true;
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
	 * If we need to grab actual information about a given Tag from the db, we will do it here;
	 * however, for now we only need to know if the Tag exists via findTag()
	 * We actually cannot create Tag objects, so this function is obsolete
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Tag associated with the input id has been retrieved from the database
	 * @param id Key to retrieve the associated Tag from the database; the key is the EPC
	 * @return Tag associated with the id
	 */
	public TagWrapper getTag(long id, Connection c){return null;}
	
	public static void main(String[] args) {
		//similar to EPCConverter class, run some tests locally as an instance of "running" the class
		
		PostgresConnector pc = new PostgresConnector();
		Connection c = pc.open();
		pc.getAllTags(c);
		long tagUPC = 2;
		long tagSerial = 100;
		//add Tag checks for if the Tag currently exists before adding
		pc.addTagToDatabase(tagUPC, tagSerial, c); //tests the SQL part of adding new tags
		//valid testing since we assume the TagWrapper is populated correctly in this class
		//and testing for populating TagWrapper as well as getting UPC and Serial is done elsewhere
		
		//test getAllTags w/ empty
		
		//test insertTag (findTag first)
		
		//test findTag
		
		//test getTagLocation
		
		//test updateTag w/ getTagLocation
		
		//test findTag
		
		//insert a bunch of Tags w/ assorted updates
		
		//test getAllTags with new defined state
		
		
		//similar testing theory to above, this tests updateTag
		//TagLocation readLocation = TagLocation.BACK_ROOM;
		//pc.testUpdateTag(tagUPC, tagSerial, c, readLocation);
		pc.close(c);
		
	}
}
