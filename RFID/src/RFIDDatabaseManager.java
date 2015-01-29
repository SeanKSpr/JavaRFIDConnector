import java.util.List;
/**
 * @version January 28, 2015
 * @author Sean Spurlin
 * 
 * RFIDDatabaseManager is a generic interface for interacting with a database. I based it off a database helper that
 * I used for an android project so it might not be entirely correct for this application. Feel free to correct it.
 */

public interface RFIDDatabaseManager {
	/**
	 * Function:		open
	 * 
	 * Precondition:	Connection to database hasn't been established
	 * Postcondition:	Connection to database is established
	 */
	public void open();
	
	/**
	 * Function:		close
	 * 
	 * Precondition:	Connection to database is currently established
	 * Postcondition:	Connection to database is severed
	 */
	public void close();
	
	/**
	 * Function:		insertTag
	 * 
	 * Precondition:	tag hasn't doesn't exist in the database
	 * Postcondition:	tag exists in the database
	 * @param tag RFID Tag which hasn't been added to the database
	 * @return True if the insertion was successful, False otherwise
	 */
	public boolean insertTag(TagWrapper tag);
	
	/**
	 * Function:		updateTag
	 * 
	 * Precondition:	Tag exists in the database and the input tag has more up-to-date information
	 * Postcondition:	Tag entry in the database matches the information of the input tag
	 * @param tag RFID Tag which exists within the database but has more up-to-date information
	 * @return True if the update was successful, False otherwise
	 */
	public boolean updateTag(TagWrapper tag);
	
	/**
	 * Function:		deleteTag
	 * 
	 * Precondition:	Tag matching the input Tag exists within the database 
	 * Postcondition:	Tag and associated information has been deleted from the database
	 * @param tag RFID Tag which exists within the database and is to be deleted along with any other information in the 
	 * database which belongs to that Tag
	 * @return True if the deletion was successful, False otherwise
	 */
	public boolean deleteTag(TagWrapper tag);
	
	/**
	 * Function:		getAllTags
	 * 
	 * Precondition:	Connection to database has been established
	 * Postcondition:	All tags have been retrieved from the database 
	 * @return A list of all the tags in the database
	 */
	public List<TagWrapper> getAllTags();
	
	/**
	 * Function:		getTag
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Tag associated with the input id has been retrieved from the database
	 * @param id Key to retrieve the associated Tag from the database; the key is the EPC
	 * @return Tag associated with the id
	 */
	public TagWrapper getTag(long id);
	
	/**
	 * Function:		dropTable
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Table which shares the same as the input is dropped from the database along with any data belonging
	 * to entries in the dropped table
	 * @param tableName Name of the table in the database to be dropped
	 */
	public void dropTable(String tableName);
	
	/**
	 * Function:		initializeTable
	 * Precondition:	Connection to database has been established
	 * Postcondition:	Table with the same name as the input String is initialized with default information
	 * @param tableName Name of the table in the database to be filled with default values
	 */
	public void initializeTable(String tableName);	
}
