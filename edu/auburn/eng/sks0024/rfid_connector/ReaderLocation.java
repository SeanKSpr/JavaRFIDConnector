package edu.auburn.eng.sks0024.rfid_connector;

/**
 * ReaderLocation is a data class which keeps track of the two locations that an RFID reader sits between.
 * This class is used to determine how RFID tags transition throughout the store.
 * @version 1 (3-14-2015)
 * @since 1 (3-14-2015)
 * @author Sean Spurlin
 */
public class ReaderLocation {
	private String storeAreaOne, storeAreaTwo;
	
	/**
	 * Constructor which takes in the two locations that the RFID reader sits between and assigns them to the 
	 * location fields of the ReaderLocation object.
	 * @param storeAreaOne A store area which is to one side of the RFID reader
	 * @param storeAreaTwo The other store area which is on the side opposite to storeAreaOne
	 */
	public ReaderLocation(String storeAreaOne, String storeAreaTwo) {
		this.storeAreaOne = storeAreaOne;
		this.storeAreaTwo = storeAreaTwo;
	}
	
	/**
	 * Overloaded equals to compare two ReaderLocations by their storeAreaOne and storeAreaTwo. If these two fields
	 * match, then the ReaderLocations are considered to be the same.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null || (obj.getClass() != this.getClass()))) {
			return false;
		}
		
		ReaderLocation otherLocation = (ReaderLocation)obj;
		if (this.storeAreaOne.equals(otherLocation.getStoreAreaOne())) {
			if (this.storeAreaTwo.equals(otherLocation.getStoreAreaTwo())) {
				return true;
			}
		}
		else if (this.storeAreaOne.equals(otherLocation.getStoreAreaTwo())){
			if (this.storeAreaTwo.equals(otherLocation.getStoreAreaOne())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Overloaded hashCode to generate a hash code based on the two Strings storeAreaOne and storeAreaTwo.
	 * This is to facilitate the correctness and viability of the store configuration hash map.
	 */
	@Override
	public int hashCode() {
		int result = 0;
		String firstDataItemToBeUsed, secondDataItemToBeUsed;
		
		if (storeAreaOne == null || storeAreaTwo == null) {
			return super.hashCode();
		}
		
		if (storeAreaOne.compareTo(storeAreaTwo) <= 0) {
			firstDataItemToBeUsed = storeAreaOne;
			secondDataItemToBeUsed = storeAreaTwo;
		}
		else {
			firstDataItemToBeUsed = storeAreaTwo;
			secondDataItemToBeUsed = storeAreaOne;
		}
		result = 31 * result + (firstDataItemToBeUsed != null ? firstDataItemToBeUsed.hashCode() : 0);
		result = 31 * result + (secondDataItemToBeUsed != null ? secondDataItemToBeUsed.hashCode() : 0);
		return result;
	}
	
	/**
	 * A getter function for obtaining storeAreaOne
	 * @return storeAreaOne - One of the store locations the RFID reader sits between
	 */
	public String getStoreAreaOne() {
		return storeAreaOne;
	}
	
	/**
	 * A setter function for setting storeAreaOne
	 * @param storeAreaOne A store location the RFID reader sits between
	 */
	public void setStoreAreaOne(String storeAreaOne) {
		this.storeAreaOne = storeAreaOne;
	}
	
	/**
	 * A getter function for obtaining storeAreaTwo
	 * @return storeAreaTwo - One of the store locations the RFID reader sits between
	 */
	public String getStoreAreaTwo() {
		return storeAreaTwo;
	}
	
	/**
	 * A setter function for setting storeAreaTwo
	 * @param storeAreaTwo A store location the RFID reader sits between
	 */
	public void setStoreAreaTwo(String storeAreaTwo) {
		this.storeAreaTwo = storeAreaTwo;
	}
}
