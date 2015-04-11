package edu.auburn.eng.sks0024.rfid_connector;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * DuplicateReaderDetector is a static class which is used to detect duplicate reads of an RFID tag. It does this by maintaining
 * a list of previously seen RFID tags. DuplicateReadDetector will compare a newly read tag to the existing tags within the 
 * collection of read tags and compare the time of the newly read tag to the time that the tag was last read. If the difference
 * in read time is above a certain threshold, then the tag is added to the list of tags. Otherwise the tag is ignored.
 * This list of tags is uploaded to the base periodically and then emptied. All functions of DuplicateReadDetector are thread
 * safe as of version 1.1.
 * 
 * @version 1.1
 * @since 	1	(2-3-2015)
 * @author Sean Spurlin
 * 
 */
public final class DuplicateReadDetector {
	private static volatile Collection<TagWrapper> tagBatch = new LinkedList<TagWrapper>();
	//This time window indicates how long we ignore subsequent RFID reads of the SAME tag after the initial read. The time is in microseconds.
	static final int READ_DUPLICATE_TIME_WINDOW = 5000;
	
	/**
	 * Returns the collection of recently read tags
	 * @return Collection of recently read tags.
	 */
	public static synchronized Collection<TagWrapper> getWrappedTags() {
		return tagBatch;
	}
	
	/**
	 * Returns a copy of the batch of read RFID tags
	 * @return Copy of the collection of recently read tags.
	 */
	public static synchronized Collection<TagWrapper> getBatchCopy() {
		return new LinkedList<TagWrapper>(tagBatch);
	}
	/**
	 * Adds a tag to the collection of read tags
	 * @param wrappedTag An RFID tag wrapped up with its current location and last seen time
	 */
	public static synchronized void addWrappedTag(TagWrapper wrappedTag) {
		tagBatch.add(wrappedTag);
	}
	
	/**
	 * Returns the EPC and Time Seen of each TagWrapper in the list of read tags as a string
	 * @return String containing EPC and Time Seen information of every TagWrapper in the Collection
	 */
	public static synchronized String getTagBatchTimeInfo() {
		String timeList = "";
		for (TagWrapper tw : tagBatch) {
			timeList += "EPC: " + tw.getTag().getEpc().toString() +  " Time: " + tw.getTimeSeen() + "\n"
					+ "Antenna ID: " + tw.getLocationScanned().toString();
		}
		timeList += "**********END**********\n";
		return timeList;
	}
	
	/**
	 * Empties the collection of seen tags. This is to be used after uploading tag information to permanent storage
	 * @param wrappedTags Collection of read tags which is to be cleared out.
	 */
	public static synchronized void emptyCollection(Collection<TagWrapper> wrappedTags) {
		wrappedTags.clear();
	}
	
	/**
	 * Finds a tag in the Collection of read tags which shares the same EPC number as the input TagWrapper and has the most
	 * recent read time of any other matches in the Collection. In short it returns the entry for the previous time the
	 * input tag was read. If the input tag has never been read before then it returns null.
	 * @param recentlyReadTag - RFID tag which was just read by the RFID reader
	 * @return The entry for the previous time the input tag was read or null if there was no matching tag in the collection
	 */
	public static synchronized TagWrapper findDuplicateTagWithLatestReadTime(TagWrapper recentlyReadTag) {
		TagWrapper latestReadMatchingTag = null;
		for (TagWrapper tw : tagBatch) {
			List<Integer> EPC1 = recentlyReadTag.getTag().getEpc().toWordList();
			List<Integer> EPC2 = tw.getTag().getEpc().toWordList();
			if (epcEqual(EPC1, EPC2)) {
				if (latestReadMatchingTag == null) {
					latestReadMatchingTag = tw;
				}
				else if (tw.getTimeSeen() > latestReadMatchingTag.getTimeSeen())
					latestReadMatchingTag = tw;
				}
			}
		return latestReadMatchingTag;
	}
	
	/**
	 * Determines if the input tag is a duplicate read by searching through the collection of read tags and finding the
	 * time the tag was previously read. If the difference in time between the two reads is within a certain margin, then
	 * the tag is deemed to be a duplicate read.
	 * @param latestRead The most recent read of an RFID tag
	 * @return True if the latestRead is a duplicate; false otherwise
	 */
	public static synchronized boolean isDuplicateRead(TagWrapper latestRead) {
		TagWrapper previousRead = findDuplicateTagWithLatestReadTime(latestRead);
		if (previousRead == null) {
			return false;
		}
		long previousReadTime = previousRead.getTimeSeen();
		long latestReadTime = latestRead.getTimeSeen();
		
		//TESTING PURPOSES DELETE LATER
		//long result = latestReadTime - previousReadTime;

		if (latestReadTime - previousReadTime < READ_DUPLICATE_TIME_WINDOW) {
			return true;
		}
		return false;
	}
	
	/**
	 * Compares two tag EPCs to determine if two Tag objects are referring to the same physical RFID tag.
	 * @param tagEPC1 The first EPC which will be compared to the second
	 * @param tagEPC2 The second EPC which will be compared to the first
	 * @return True if the two Lists are equal; False otherwise
	 */
	public static boolean epcEqual(List<Integer> tagEPC1, List<Integer> tagEPC2) {
		if (tagEPC1.size() != tagEPC2.size()) {
			return false;
		}
		for (int i = 0; i < tagEPC1.size(); i++) {
			if (tagEPC1.get(i).intValue() != tagEPC2.get(i).intValue()) {
				return false;
			}
		}
		return true;
	}
	
	
}
