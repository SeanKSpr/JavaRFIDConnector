package edu.auburn.eng.sks0024.rfid_connector;
import java.sql.Connection;
import java.util.Collection;
import java.util.TimerTask;

/**
 * DBUpdateTimer is a lightweight TimerTask implementation which updates the database with the latest tag information
 * stored in the tag batch list after a certain amount of time has passed.
 * @since   1 (2-4-2015)
 * @version 1 (2-4-2015)
 * @author Sean Spurlin
 *
 */
public class DBUpdateTimer extends TimerTask {
	public static long TIMER_DELAY = 10000;
	@Override
	/**
	 * This function is called whenever the DBUpdateTimer is awake. It simply updates the database with the latest batch 
	 * of read tags and then empties the batch. Upon completion of the run method, the DBUpdateTimer will sleep until a
	 * certain amount of time has passed and then wake up.
	 */
	public void run() { 
		updateDatabase();
		DuplicateReadDetector.emptyCollection(DuplicateReadDetector.getWrappedTags());
	}

	/**
	 * Takes the batch of read tags and connects with the DatabaseManager to update tag information in the database.
	 */
	public void updateDatabase() {
		RFIDDatabaseManager dbManager = new PostgresConnector();
		Connection dbConnection = dbManager.open();
		
		Collection<TagWrapper> tagBatch = DuplicateReadDetector.getBatchCopy();
		for (TagWrapper tag : tagBatch) {
			System.out.println("Updating Database Tag: " + tag.getTag().getEpc().toString() + " Time: " + tag.getTimeSeen());
			if(!dbManager.updateTag(tag, dbConnection)) {
				dbManager.insertTag(tag, dbConnection, "back room");
			};
		}
		//For testing reasons (not that it really matters if this is displayed on the console)
		if (tagBatch.isEmpty()) {
			System.out.println("Nothing in the tag batch");
		}
	}
	
	public int testUpdate() {
		Collection<TagWrapper> tagBatch = DuplicateReadDetector.getBatchCopy();
		for (TagWrapper tag : tagBatch) {
			System.out.println("Updating Database Tag: " + tag.getTag().getEpc().toString() + " Time: " + tag.getTimeSeen());
		}
		//For testing reasons (not that it really matters if this is displayed on the console)
		if (tagBatch.isEmpty()) {
			System.out.println("Nothing in the tag batch");
		}
		int numOfTagsUpdated = tagBatch.size();
		DuplicateReadDetector.emptyCollection(DuplicateReadDetector.getWrappedTags());
		return numOfTagsUpdated;
	}
}
