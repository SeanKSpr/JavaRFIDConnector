package edu.auburn.eng.sks0024.rfid_connector;
import java.sql.*;
import java.util.List;
/**
 * RFIDDatabaseManager is a generic interface for interacting with a database. I based it off a database helper that
 * I used for an android project so it might not be entirely correct for this application. Feel free to correct it.
 * 
 * @since January 28, 2015
 * @version 1
 * @author Sean Spurlin
 * @modified Jared Watkins
 * 
 */

public interface RFIDDatabaseManager {
	/**
	 * Function:		open
	 * 
	 * Precondition:	Connection to database hasn't been established
	 * Postcondition:	Connection to database is established
	 * @return The newly opened Connection to the database
	 */
	public Connection open();
	
	/**
	 * Function:		close
	 * 
	 * Precondition:	Connection to database is currently established
	 * Postcondition:	Connection to database is severed
	 * @param c Current connection to our database
	 */
	public void close(Connection c);
	
	/**
	 * Function:		insertTag
	 * 
	 * Precondition:	tag hasn't doesn't exist in the database
	 * Postcondition:	tag exists in the database
	 * @param tag RFID Tag which hasn't been added to the database
	 * @param c Current connection to our database
	 * @return True if the insertion was successful, False otherwise
	 */
	public boolean insertTag(TagWrapper tag, Connection c);
	
	/**
	 * Function:		updateTag
	 * 
	 * Precondition:	Tag exists in the database and the input tag has more up-to-date information
	 * Postcondition:	Tag entry in the database matches the information of the input tag
	 * @param tag RFID Tag which exists within the database but has more up-to-date information
	 * @param c Current connection to our database
	 * @return True if the update was successful, False otherwise
	 */
	public boolean updateTag(TagWrapper tag, Connection c);
	
	/**
	 * Function:		deleteTag
	 * 
	 * Precondition:	Tag matching the input Tag exists within the database 
	 * Postcondition:	Tag and associated information has been deleted from the database
	 * @param tag RFID Tag which exists within the database and is to be deleted along with any other information in the 
	 * database which belongs to that Tag
	 * @param c Current connection to our database
	 * @return True if the deletion was successful, False otherwise
	 */
	public boolean deleteTag(TagWrapper tag, Connection c);
	
	/**
	 * Function:		getAllTags
	 * 
	 * Precondition:	Connection to database has been established
	 * Postcondition:	All tags have been retrieved from the database 
	 * @param c Current connection to our database
	 * @return A list of all the tags in the database
	 */
	public void getAllTags(Connection c);
	
	/**
	 * Function:		getTag
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Tag associated with the input id has been retrieved from the database
	 * @param id Key to retrieve the associated Tag from the database; the key is the EPC
	 * @param c Current connection to our database
	 * @return Tag associated with the id
	 */
	public TagWrapper getTag(long id, Connection c);
	
}
