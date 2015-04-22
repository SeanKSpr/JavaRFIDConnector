package edu.auburn.eng.sks0024.rfid_connector_test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.auburn.eng.rfid_4710.manager_gui.ServerInfo;
import edu.auburn.eng.sks0024.rfid_connector.*;

public class DBAcceptanceTests {

	private long upc1;
	private long upc2;
	private int[] dupeSerials;
	private int[] firstSerials;
	private PostgresConnector conn;
	private Connection c;
	private String[] locations;
	private HashMap<StoreConfigurationKey, TagLocation> map;
	private JavaRFIDConnector connector;
	
	//Constructor for information needed for tests
	public DBAcceptanceTests(PostgresConnector pc) {
		upc1 = Long.parseLong("051071851756");
		upc2 = Long.parseLong("672787789760");
		dupeSerials = new int[]{2, 17, 38, 9, 12};
		firstSerials = new int[]{5, 13};
		conn = pc;
		ServerInfo server = new ServerInfo();
		server.setUrl("jdbc:postgresql://localhost:5432/rfiddb");
		server.setOwner("postgres");
		server.setPassword("password");
		conn.setServerInformation(server);
		c = pc.open();
		
		locations = new String[]{"warehouse","back room","on store floor","out of store"};
		map = generateStoreLayout(locations);
	}
	
	private HashMap<StoreConfigurationKey, TagLocation> generateStoreLayout(String[] locations) {
		connector = new edu.auburn.eng.sks0024.rfid_connector.JavaRFIDConnector();
		List<TagLocation> tagLocations = new ArrayList<TagLocation>();
		List<ReaderLocation> readerLocations = new ArrayList<ReaderLocation>();
				
		TagLocation warehouse = new TagLocation(locations[0]);
		TagLocation backroom = new TagLocation(locations[1]);
		TagLocation storefloor = new TagLocation(locations[2]);
		TagLocation outofstore = new TagLocation(locations[3]);
		tagLocations.add(warehouse);
		tagLocations.add(backroom);
		tagLocations.add(storefloor);
		tagLocations.add(outofstore);
		
		ReaderLocation warehouse_backroom = new ReaderLocation(locations[0], locations[1]);
		ReaderLocation backroom_storefloor = new ReaderLocation(locations[1], locations[2]);
		ReaderLocation storefloor_outofstore = new ReaderLocation(locations[2], locations[3]);
		readerLocations.add(warehouse_backroom);
		readerLocations.add(backroom_storefloor);
		readerLocations.add(storefloor_outofstore);
		
		return connector.generateStoreMap(tagLocations, readerLocations);
	}
	
	//Test to make sure the database contents are as expected; must be checked manually in the console.
	//    Test is to be run after each major grouping of acceptance tests to make sure database is behaving as expected.
	public void displayAllTest() {
		conn.getAllTags(c);
	}
	
	//Test inserting 2 new Tags to the database via PostgresConnector.
	public boolean insertTest() {
		boolean success;
		
		success = conn.addTagToDatabase(upc1, firstSerials[0], c, locations[1]);
		System.out.println("Added tag 1: " + success);
		success &= conn.addTagToDatabase(upc2, firstSerials[1], c, locations[1]);
		System.out.println("Added tag 2: " + success);
		
		if(!success) {
			System.out.println("Test Tags added incorrectly");
		}
		
		//Only considered to work if no errors during entry
		//   and (only) correct tags can be found in database.
		return success && findTest() && findNonexistentTest();
	}
	
	//Test finding the 2 Tags just inserted into the database.
	public boolean findTest() {
		boolean success;
		try {
			success = conn.findTagInDatabase(upc1, firstSerials[0], c);
			System.out.println("1: " + success);
			success &= conn.findTagInDatabase(upc2, firstSerials[1], c);
			System.out.println("2: " + success);
			if(!success) {
				System.out.println("Test Tags unable to be found");
			}
		} catch (Exception e) {
			System.out.println("Error occurred while finding test Tags");
			success = false;
		}
		
		return success;
	}
	
	//Make sure no errors occur when searching for Tags that don't exist, and make sure only
	//    the Tags specified in insertTest() were added.
	public boolean findNonexistentTest() {
		boolean success;
		try {
			success = !(conn.findTagInDatabase(upc1, firstSerials[1], c));
			System.out.println("2: " + success);
			success &= !(conn.findTagInDatabase(upc2, firstSerials[0], c));
			System.out.println("2: " + success);
		} catch (Exception e) {
			System.out.println("Error occurred while looking for test Tags not in database");
			success = false;
		}
		return success;
	}
	
	//Test to make sure location of new items is as specified within the test that added them.
	public boolean getNewLocationTest() {
		int id1 = conn.getTagID(upc1, firstSerials[0], c);
		String loc1 = conn.getTagLocation(id1, c);
		int id2 = conn.getTagID(upc2, firstSerials[1], c);
		String loc2 = conn.getTagLocation(id2, c);
		
		return loc1.equalsIgnoreCase("back room") && loc2.equalsIgnoreCase("back room");
	}
	
	//Test to make sure no errors occur while looking for the location of Tags not in the database.
	public boolean getNonexistentLocationTest() {		
		int id1 = conn.getTagID(upc1, firstSerials[1], c);
		String loc1 = conn.getTagLocation(id1, c);
		int id2 = conn.getTagID(upc2, firstSerials[0], c);
		String loc2 = conn.getTagLocation(id2, c);
		
		//getID returns 0 if no Tag exists with given upc and serial combination.
		//getTagLocation returns an empty String if no Tag with given ID exists.
		return loc1.equalsIgnoreCase("") && loc2.equalsIgnoreCase("");
	}
	
	//DEPRECATED
	//Test to make sure we can change location for new Tags to the store floor.
	public boolean moveNewLocationTest() {
		boolean success;
		//I have no idea how the HashMap works with this; is there a function that can return the String for new location given current location?
		success = conn.updateTagInDatabase(upc1, firstSerials[0], new ReaderLocation(locations[1], locations[2]), c);
		success &= conn.updateTagInDatabase(upc2, firstSerials[1], new ReaderLocation(locations[1], locations[2]), c);
		
		return success && getUpdatedLocationsTest();
	}
	
	//DEPRECATED
	//Test that Tag locations updated appropriately.
	public boolean getUpdatedLocationsTest() {
		int id1 = conn.getTagID(upc1, firstSerials[0], c);
		String loc1 = conn.getTagLocation(id1, c);
		int id2 = conn.getTagID(upc2, firstSerials[1], c);
		String loc2 = conn.getTagLocation(id2, c);
		
		return loc1.equalsIgnoreCase("on store floor") && loc2.equalsIgnoreCase("on store floor");
	}
	
	//DEPRECATED
	//Test that we cannot update Tags using an invalid ReaderLocation (compared to current location).
	public boolean updateInvalidLocationTest() {
		boolean failure;
		//Both Tags should be on the store floor, so the reader location listed is invalid for those Tags.
		failure = conn.updateTagInDatabase(upc1, firstSerials[0], new ReaderLocation(locations[0], locations[1]), c);
		failure &= conn.updateTagInDatabase(upc2, firstSerials[1], new ReaderLocation(locations[0], locations[1]), c);
		
		//Need to make sure the location of neither Tag was somehow changed; both should still be on the store floor.
		return !failure && getUpdatedLocationsTest();		
	}
	
	//DEPRECATED
	//Test that no errors occur when attempting to update Tags not in the database.
	public boolean updateNonExistentTagsTest() {
		boolean failure;
		//Location of reader should not matter, since neither Tag exists in the database.
		failure = conn.updateTagInDatabase(upc1, firstSerials[1], new ReaderLocation(locations[1], locations[2]), c);
		failure &= conn.updateTagInDatabase(upc2, firstSerials[0], new ReaderLocation(locations[1], locations[2]), c);
		
		return !failure;		
	}
	
	//DEPRECATED UNTIL ABOVE TESTS FIXED
	//Test the overall system in a more strenuous setting. Run displayAllTest() after this to make sure
	//    final configuration of database is as expected.
	public boolean overallSystemTest() {
		boolean success;
		
		//Create virtual readers
		ReaderLocation warehouse_backroom = new ReaderLocation(locations[0], locations[1]);
		ReaderLocation backroom_storefloor = new ReaderLocation(locations[1], locations[2]);
		ReaderLocation storefloor_outofstore = new ReaderLocation(locations[2], locations[3]);
		
		//Extensively test adding Tags
		success = conn.addTagToDatabase(upc1, dupeSerials[0], c, locations[1]);
		success &= conn.addTagToDatabase(upc1, dupeSerials[1], c, locations[1]);
		success &= conn.addTagToDatabase(upc1, dupeSerials[2], c, locations[1]);
		success &= conn.addTagToDatabase(upc1, dupeSerials[3], c, locations[1]);
		success &= conn.addTagToDatabase(upc1, dupeSerials[4], c, locations[1]);
		success &= conn.addTagToDatabase(upc2, dupeSerials[0], c, locations[1]);
		success &= conn.addTagToDatabase(upc2, dupeSerials[1], c, locations[1]);
		success &= conn.addTagToDatabase(upc2, dupeSerials[2], c, locations[1]);
		success &= conn.addTagToDatabase(upc2, dupeSerials[3], c, locations[1]);
		success &= conn.addTagToDatabase(upc2, dupeSerials[4], c, locations[1]);
		
		//Extensively test finding Tags that now exist.
		try {
			success &= conn.findTagInDatabase(upc1, dupeSerials[0], c);
			success &= conn.findTagInDatabase(upc1, dupeSerials[1], c);
			success &= conn.findTagInDatabase(upc1, dupeSerials[2], c);
			success &= conn.findTagInDatabase(upc1, dupeSerials[3], c);
			success &= conn.findTagInDatabase(upc1, dupeSerials[4], c);
			success &= conn.findTagInDatabase(upc2, dupeSerials[0], c);
			success &= conn.findTagInDatabase(upc2, dupeSerials[1], c);
			success &= conn.findTagInDatabase(upc2, dupeSerials[2], c);
			success &= conn.findTagInDatabase(upc2, dupeSerials[3], c);
			success &= conn.findTagInDatabase(upc2, dupeSerials[4], c);
		} catch (Exception e) {
			success &= false;
		}

		//Extensively test updating Tag locations.
				//move these Tags onto the Store floor
		success &= conn.updateTagInDatabase(upc1, dupeSerials[0], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc2, dupeSerials[0], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc1, dupeSerials[1], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc2, dupeSerials[1], backroom_storefloor, c);
				//move these Tags to the warehouse
		success &= conn.updateTagInDatabase(upc1, dupeSerials[4], warehouse_backroom, c);
		success &= conn.updateTagInDatabase(upc2, dupeSerials[4], warehouse_backroom, c);
				//move these Tags onto floor and then into back room again
		success &= conn.updateTagInDatabase(upc1, dupeSerials[2], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc2, dupeSerials[2], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc1, dupeSerials[2], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc2, dupeSerials[2], backroom_storefloor, c);
				//move these Tags completely out of the store
		success &= conn.updateTagInDatabase(upc1, dupeSerials[3], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc2, dupeSerials[3], backroom_storefloor, c);
		success &= conn.updateTagInDatabase(upc1, dupeSerials[3], storefloor_outofstore, c);
		success &= conn.updateTagInDatabase(upc2, dupeSerials[3], storefloor_outofstore, c);
		
		//Extensively test that Tags are in expected locations
		int id1 = conn.getTagID(upc1, dupeSerials[0], c);
		String loc1 = conn.getTagLocation(id1, c);
		int id2 = conn.getTagID(upc2, dupeSerials[0], c);
		String loc2 = conn.getTagLocation(id2, c);
		int id3 = conn.getTagID(upc1, dupeSerials[1], c);
		String loc3 = conn.getTagLocation(id3, c);
		int id4 = conn.getTagID(upc2, dupeSerials[1], c);
		String loc4 = conn.getTagLocation(id4, c);
		int id5 = conn.getTagID(upc1, dupeSerials[4], c);
		String loc5 = conn.getTagLocation(id5, c);
		int id6 = conn.getTagID(upc2, dupeSerials[4], c);
		String loc6 = conn.getTagLocation(id6, c);
		int id7 = conn.getTagID(upc1, dupeSerials[2], c);
		String loc7 = conn.getTagLocation(id7, c);
		int id8 = conn.getTagID(upc2, dupeSerials[2], c);
		String loc8 = conn.getTagLocation(id8, c);
		int id9 = conn.getTagID(upc1, dupeSerials[3], c);
		String loc9 = conn.getTagLocation(id9, c);
		int id10 = conn.getTagID(upc2, dupeSerials[3], c);
		String loc10 = conn.getTagLocation(id10, c);
		
		success &= (loc1.equalsIgnoreCase("on store floor") && loc2.equalsIgnoreCase("on store floor")
				 && loc3.equalsIgnoreCase("on store floor") && loc4.equalsIgnoreCase("on store floor")
				 && loc5.equalsIgnoreCase("warehouse") 		&& loc6.equalsIgnoreCase("warehouse")
				 && loc7.equalsIgnoreCase("back room")      && loc8.equalsIgnoreCase("back room")
				 && loc9.equalsIgnoreCase("out of store")   && loc10.equalsIgnoreCase("out of store"));
		
		//If desired can later add further testing for finding, updating, and getting locations for Tags that do not exist.
		
		return success;
	}
}
