package edu.auburn.eng.sks0024.rfid_connector;
/**
 * ReaderLocation is an enumeration of the possible locations a reader can be. Valid locations are between the
 * store floor and the backroom, at the store entrance, and between the backroom and the warehouse. This enumeration is
 * used to determine the new location of an RFID tag. For instance, if a tag with current location STORE_FLOOR was read by
 * RFID reader at location FLOOR_BACKROOM then the location of the tag would be changed from STORE_FLOOR to BACKROOM to reflect
 * that the tag has moved from the store floor to the backroom of the store.
 * 
 * @since   1 (2-3-2015)
 * @version 1.1 (2-23-2015)
 * @author Sean Spurlin
 */
public enum ReaderLocation {
	FLOOR_BACKROOM, STORE_ENTRANCE, BACKROOM_WAREHOUSE, WAREHOUSE_LOADING;
	
	/**
	 * convertLocation takes in a String describing the an RFID antenna/reader location and returns the 
	 * corresponding ReaderLocation enumeration. This function should be used primarily by the ReaderLocation Combo
	 * GUI components described in the RFIDManagerWindow class.
	 * @param readerLocation The location of an antenna/reader as a String
	 * @return The location of the same antenna/reader as the corresponding ReaderLocation enum
	 */
	public static ReaderLocation convertLocation(String readerLocation) {
		switch (readerLocation) {
		case "Between Warehouse and Loading":
			return WAREHOUSE_LOADING;
		case "Between Warehouse and Backroom":
			return BACKROOM_WAREHOUSE;
		case "Between Backroom and Store floor":
			return FLOOR_BACKROOM;
		case "Between Store floor and Store entrance":
			return STORE_ENTRANCE;
		}
		return null;
		
	}
}
