package edu.auburn.eng.sks0024.rfid_connector;
/**
 * TagLocation is an enumeration of the possible locations an RFID tag can be located. The possible locations for a tag are:
 * on the store floor, in the back room, in the warehouse, or out of the store (sold). This enumeration is used in conjunction with
 * the ReaderLocation enumeration to determine new RFID tag locations after a tag moves past a particular RFID reader. For instance, if a tag with current location STORE_FLOOR was read by
 * RFID reader at location FLOOR_BACKROOM then the location of the tag would be changed from STORE_FLOOR to BACKROOM to reflect
 * that the tag has moved from the store floor to the backroom of the store.
 * 
 * @since 	1 (2-3-2015)
 * @version 1
 * @author Sean Spurlin
 * 
 */
public enum TagLocation {
	STORE_FLOOR, BACK_ROOM, WAREHOUSE, OUT_OF_STORE;
	
	/**
	 * This function takes the current location of a tag and a location of an RFID reader and returns the new tag location. This
	 * simulates, for instance, an RFID tag moving from the backroom of a store, past a scanner between the backroom and the
	 * store floor, and onto the store floor.
	 * @param currentLocation The current location of the RFID tag
	 * @param readerLoc The location of the RFID reader that read the RFID tag
	 * @return The new location of the RFID tag
	 */
	public static TagLocation getNewLocation(TagLocation currentLocation, ReaderLocation readerLoc) {
		TagLocation newLocation = null;
		switch(readerLoc) {
		case FLOOR_BACKROOM:
			if (currentLocation == STORE_FLOOR) {
				newLocation = BACK_ROOM;
			}
			else if (currentLocation == BACK_ROOM) {
				newLocation = STORE_FLOOR;
			}
			break;
		case STORE_ENTRANCE:
			if (currentLocation == STORE_FLOOR) {
				newLocation = OUT_OF_STORE;
			}
			break;
		case BACKROOM_WAREHOUSE:
			if (currentLocation == BACK_ROOM) {
				newLocation = WAREHOUSE;
			}
			else if (currentLocation == WAREHOUSE) {
				newLocation = BACK_ROOM;
			}
			break;
		}
		return newLocation;
	}
	
	/**
	 * Function:		convertLocation
	 * 
	 * Precondition:	TagLocation has been recieved from the given TagWrapper object
	 * Postcondition:	TagLocation value has been converted to its String representation
	 * @param loc The String value we need to convert to the TagLocation enum value for the database
	 * @return The TagLocation from the database to compare to the new location from the reader
	 */
	public static TagLocation convertLocation(String loc) {
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
}
