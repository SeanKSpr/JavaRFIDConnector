/**
 * ReaderLocation is an enumeration of the possible locations a reader can be. Valid locations are between the
 * store floor and the backroom, at the store entrance, and between the backroom and the warehouse. This enumeration is
 * used to determine the new location of an RFID tag. For instance, if a tag with current location STORE_FLOOR was read by
 * RFID reader at location FLOOR_BACKROOM then the location of the tag would be changed from STORE_FLOOR to BACKROOM to reflect
 * that the tag has moved from the store floor to the backroom of the store.
 * 
 * @since   1 (2-3-2015)
 * @version 1
 * @author Sean Spurlin
 */
public enum ReaderLocation {
	FLOOR_BACKROOM, STORE_ENTRANCE, BACKROOM_WAREHOUSE;
}
