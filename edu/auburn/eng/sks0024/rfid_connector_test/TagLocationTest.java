package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.auburn.eng.sks0024.rfid_connector.ReaderLocation;
import edu.auburn.eng.sks0024.rfid_connector.TagLocation;

public class TagLocationTest {
	
	////Jargon for these tests/////////////////////////
	//
	///////Tag Locations
	// SF = Store Floor
	// BR = Back Room
	// WH = Warehouse
	// OOS = Out of store
	//
	///////Reader locations 
	// SE = Store Entrance
	// SB = Store Backroom
	// BW = Backroom Warehouse
	// WO = Warehouse outside
	//
	///////////////////////////////////////////////////
	@Test
	public void testInstance01NewLocation() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	//All these tests need to be renamed because the second part of the SF_BR the BR should be a ReaderLoc abbreviation.
	@Test
	public void testNewLocationSF_SE() {
		TagLocation location = TagLocation.STORE_FLOOR;
		ReaderLocation readerLoc = ReaderLocation.STORE_ENTRANCE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationSF_SB() {
		TagLocation location = TagLocation.STORE_FLOOR;
		ReaderLocation readerLoc = ReaderLocation.FLOOR_BACKROOM;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.BACK_ROOM, newLocation);
	}
	
	@Test
	public void testNewLocationSF_BW() {
		TagLocation location = TagLocation.STORE_FLOOR;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.STORE_FLOOR, newLocation);
	}
	
	@Test
	public void testNewLocationSF_WO() {
		TagLocation location = TagLocation.STORE_FLOOR;
		ReaderLocation readerLoc = ReaderLocation.WAREHOUSE_LOADING;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.STORE_FLOOR, newLocation);
	}
	
	@Test
	public void testNewLocationBR_SE() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.STORE_ENTRANCE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.BACK_ROOM, newLocation);
	}
	
	@Test
	public void testNewLocationBR_SB() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.FLOOR_BACKROOM;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.STORE_FLOOR, newLocation);
	}
	
	@Test
	public void testNewLocationBR_BW() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationBR_WO() {
		TagLocation location = TagLocation.BACK_ROOM;
		ReaderLocation readerLoc = ReaderLocation.WAREHOUSE_LOADING;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.BACK_ROOM, newLocation);
	}
	
	@Test
	public void testNewLocationWH_SE() {
		TagLocation location = TagLocation.WAREHOUSE;
		ReaderLocation readerLoc = ReaderLocation.STORE_ENTRANCE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_SB() {
		TagLocation location = TagLocation.WAREHOUSE;
		ReaderLocation readerLoc = ReaderLocation.FLOOR_BACKROOM;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_BW() {
		TagLocation location = TagLocation.WAREHOUSE;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.BACK_ROOM, newLocation);
	}
	
	@Test
	public void testNewLocationWH_WO() {
		TagLocation location = TagLocation.WAREHOUSE;
		ReaderLocation readerLoc = ReaderLocation.WAREHOUSE_LOADING;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_SE() {
		TagLocation location = TagLocation.OUT_OF_STORE;
		ReaderLocation readerLoc = ReaderLocation.STORE_ENTRANCE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_SB() {
		TagLocation location = TagLocation.OUT_OF_STORE;
		ReaderLocation readerLoc = ReaderLocation.FLOOR_BACKROOM;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_BW() {
		TagLocation location = TagLocation.OUT_OF_STORE;
		ReaderLocation readerLoc = ReaderLocation.BACKROOM_WAREHOUSE;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_WO() {
		TagLocation location = TagLocation.OUT_OF_STORE;
		ReaderLocation readerLoc = ReaderLocation.WAREHOUSE_LOADING;
		TagLocation newLocation = TagLocation.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocation.WAREHOUSE, newLocation);
	}
	
}
	