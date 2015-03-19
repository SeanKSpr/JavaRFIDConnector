package edu.auburn.eng.sks0024.rfid_connector;

/**
 * StoreConfigurationKey is a custom key class for the store configuration hash map stored in the JavaRFIDConnector class.
 * The purpose of this class is to facilitate a way of naturally obtaining where a tag transitions given the current tag location
 * and the reader which scanned the tag. 
 * 
 * Ex. Assume we have locations (a, b, c, d, e) and reader locations {(a,b), (b, c), (c, d) (d, e)}
 * Our keys then are all valid Location_ReaderLocation transitional pairs
 * [(a), (a, b)], [(b), (a, b)], [(b), (b, c)], [(c), (b, c)], [(c), (c, d)], [(d), (c, d)], [(d), (d, e)], [(e), (d, e)]
 * And the value stored with these valid Location_ReaderLocation transitional pairs are the transitions themselves
 * ex. HashMap.get([(a), (a, b)]) would return b as the new location
 * @since 1 (3-14-2015)
 * @version 1 (3-14-2015)
 * @author Sean Spurlin
 */
public class StoreConfigurationKey {
	private TagLocation tagLocation;
	private ReaderLocation readerLocation;
	
	/**
	 * Constructor for StoreConfigurationKey which takes in the two components of the key: the TagLocation and ReaderLocation and saves them as variables
	 * @param tagLocation A tag location
	 * @param readerLocation A reader location 
	 */
	public StoreConfigurationKey(TagLocation tagLocation,
			ReaderLocation readerLocation) {
		super();
		this.tagLocation = tagLocation;
		this.readerLocation = readerLocation;
	}
	
	/**
	 * Overloaded equals to compare two StoreConfigurationKey objects by their data; the two TagLocation objects are compared, then the two ReaderLocations
	 * are compared. If both of these comparisons return true, then the two keys are equal otherwise the two keys are not equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null || (obj.getClass() != this.getClass()))) {
			return false;
		}
		StoreConfigurationKey otherKey = (StoreConfigurationKey)obj;
		
		if (this.tagLocation.equals(otherKey.getTagLocation())) {
			if (this.readerLocation.equals(otherKey.getReaderLocation())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Overloaded hashCode to generate a hash code for the StoreConfigurationKey based on the TagLocation and ReaderLocation.
	 */
	@Override
	public int hashCode() {
		int result = 0;
		result = 31 * result + (tagLocation != null ? this.tagLocation.hashCode() : 0);
		result = 31 * result + (readerLocation != null ? this.readerLocation.hashCode() : 0);
		return result;
	}
	
	/**
	 * A getter function for returning the TagLocation component of the StoreConfigurationKey
	 * @return TagLocation of the key
	 */
	public TagLocation getTagLocation() {
		return tagLocation;
	}
	
	/**
	 * A setter function for setting the TagLocation component of the StoreConfigurationKey
	 * @param tagLocation The location of the tag 
	 */
	public void setTagLocation(TagLocation tagLocation) {
		this.tagLocation = tagLocation;
	}
	
	/**
	 * A getter function for returning the ReaderLocation component of the StoreConfigurationKey
	 * @return ReaderLocation of the key
	 */
	public ReaderLocation getReaderLocation() {
		return readerLocation;
	}
	
	/**
	 * A setter function for setting the ReaderLocation of the StoreConfigurationKey
	 * @param readerLocation The location of the RFID reader
	 */
	public void setReaderLocation(ReaderLocation readerLocation) {
		this.readerLocation = readerLocation;
	}
}
