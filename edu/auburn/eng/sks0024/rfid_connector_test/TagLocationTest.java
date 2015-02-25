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
	
	//All these tests need to be renamed because the second part of the SF_BR the BR should be a ReaderLoc abbreviation.
	@Test
	public void testNewLocationSF_BR() {
		TagLocation location = TagLocation.STORE_FLOOR;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationSF_WH() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationSF_OOS() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationBR_SF() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationBR_WH() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationBR_OOS() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_SF() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_BR() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_OOS() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_SF() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_BR() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_WH() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationNULL() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
}
