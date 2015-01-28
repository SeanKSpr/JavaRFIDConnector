public enum TagLocation {
	STORE_FLOOR, BACK_ROOM, WAREHOUSE, OUT_OF_STORE;
	
	public TagLocation getNewLocation(TagLocation currentLocation, ReaderLocation readerLoc) {
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
}
