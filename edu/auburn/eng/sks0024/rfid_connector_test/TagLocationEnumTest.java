package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.auburn.eng.sks0024.rfid_connector.ReaderLocationEnum;
import edu.auburn.eng.sks0024.rfid_connector.TagLocationEnum;

public class TagLocationEnumTest {
	
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
		TagLocationEnum location = TagLocationEnum.BACK_ROOM;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.BACKROOM_WAREHOUSE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.WAREHOUSE, newLocation);
	}
	
	//All these tests need to be renamed because the second part of the SF_BR the BR should be a ReaderLoc abbreviation.
	@Test
	public void testNewLocationSF_SE() {
		TagLocationEnum location = TagLocationEnum.STORE_FLOOR;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.STORE_ENTRANCE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationSF_SB() {
		TagLocationEnum location = TagLocationEnum.STORE_FLOOR;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.FLOOR_BACKROOM;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.BACK_ROOM, newLocation);
	}
	
	@Test
	public void testNewLocationSF_BW() {
		TagLocationEnum location = TagLocationEnum.STORE_FLOOR;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.BACKROOM_WAREHOUSE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.STORE_FLOOR, newLocation);
	}
	
	@Test
	public void testNewLocationSF_WO() {
		TagLocationEnum location = TagLocationEnum.STORE_FLOOR;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.WAREHOUSE_LOADING;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationBR_SE() {
		TagLocationEnum location = TagLocationEnum.BACK_ROOM;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.STORE_ENTRANCE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.BACK_ROOM, newLocation);
	}
	
	@Test
	public void testNewLocationBR_SB() {
		TagLocationEnum location = TagLocationEnum.BACK_ROOM;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.FLOOR_BACKROOM;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.STORE_FLOOR, newLocation);
	}
	
	@Test
	public void testNewLocationBR_BW() {
		TagLocationEnum location = TagLocationEnum.BACK_ROOM;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.BACKROOM_WAREHOUSE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationBR_WO() {
		TagLocationEnum location = TagLocationEnum.BACK_ROOM;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.WAREHOUSE_LOADING;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_SE() {
		TagLocationEnum location = TagLocationEnum.WAREHOUSE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.STORE_ENTRANCE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_SB() {
		TagLocationEnum location = TagLocationEnum.WAREHOUSE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.FLOOR_BACKROOM;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.WAREHOUSE, newLocation);
	}
	
	@Test
	public void testNewLocationWH_BW() {
		TagLocationEnum location = TagLocationEnum.WAREHOUSE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.BACKROOM_WAREHOUSE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.BACK_ROOM, newLocation);
	}
	
	@Test
	public void testNewLocationWH_WO() {
		TagLocationEnum location = TagLocationEnum.WAREHOUSE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.WAREHOUSE_LOADING;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_SE() {
		TagLocationEnum location = TagLocationEnum.OUT_OF_STORE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.STORE_ENTRANCE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_SB() {
		TagLocationEnum location = TagLocationEnum.OUT_OF_STORE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.FLOOR_BACKROOM;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_BW() {
		TagLocationEnum location = TagLocationEnum.OUT_OF_STORE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.BACKROOM_WAREHOUSE;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.OUT_OF_STORE, newLocation);
	}
	
	@Test
	public void testNewLocationOOS_WO() {
		TagLocationEnum location = TagLocationEnum.OUT_OF_STORE;
		ReaderLocationEnum readerLoc = ReaderLocationEnum.WAREHOUSE_LOADING;
		TagLocationEnum newLocation = TagLocationEnum.getNewLocation(location, readerLoc);
		
		assertEquals(TagLocationEnum.WAREHOUSE, newLocation);
	}
	
}
	