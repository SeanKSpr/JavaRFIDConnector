package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.auburn.eng.sks0024.rfid_connector.ReaderLocation;
import edu.auburn.eng.sks0024.rfid_connector.TagLocation;

public class TagLocationTest {

	@Test
	public void testInstance01NewLocation() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}

}
