package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.junit.Test;

import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.TagData;

import edu.auburn.eng.sks0024.rfid_connector.JavaRFIDConnector;
import edu.auburn.eng.sks0024.rfid_connector.PostgresConnector;
import edu.auburn.eng.sks0024.rfid_connector.ReaderLocation;
import edu.auburn.eng.sks0024.rfid_connector.StoreConfigurationKey;
import edu.auburn.eng.sks0024.rfid_connector.TagLocation;
import edu.auburn.eng.sks0024.rfid_connector.TagWrapper;

public class DBConnectionTest {
	@Test
	public void testNominalConnection() {
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		Properties connectionProperties = null;
		try {
			connectionProperties = dbConnection.getClientInfo();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(connectionProperties.getProperty("user"), "postgres");
		assertEquals(connectionProperties.getProperty("url"), "jdbc:postgresql://localhost:5432/postgres");
	}
	
	@Test
	public void EstablishConnectionAccept() {
		PostgresConnector connector = new PostgresConnector();
		String user, url, password;
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter the user name for the database owner: ");
		user = scan.nextLine();
		System.out.print("Enter the url to the database: ");
		url = scan.nextLine();
		System.out.print("Enter the password to the database: ");
		password = scan.nextLine();
		scan.close();
		
		Connection dbConnection = connector.openTest(user, url, password);
		Properties connectionProperties = null;
		try {
			connectionProperties = dbConnection.getClientInfo();
		} catch (SQLException e) {
			fail();
			e.printStackTrace();
		}
		assertEquals(connectionProperties.getProperty("user"), user);
		assertEquals(connectionProperties.getProperty("url"), url);
		assertEquals(connectionProperties.getProperty("password"), password);
		System.out.println("Connection to " + url + " established successfully");
		System.out.println("Database owner: " + user + "\nPassword: " + password);
		
		closeConnectionAccept(connector, user, url, password, dbConnection);
		
	}

	private void closeConnectionAccept(PostgresConnector connector,
			String user, String url, String password, Connection dbConnection) {
		connector.close(dbConnection);
		try {
			dbConnection.getClientInfo();
		} catch (SQLException e) {
			System.out.println("Connection to " + url + " closed successfully");
			System.out.println("Database owner: " + user + "\nPassword: " + password);
			assertTrue(true);
		}
	}
	@Test
	public void testCloseConnection() {
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		Properties connectionProperties = null;
		try {
			connectionProperties = dbConnection.getClientInfo();
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
		assertEquals(connectionProperties.getProperty("user"), "postgres");
		assertEquals(connectionProperties.getProperty("url"), "jdbc:postgresql://localhost:5432/postgres");
		
		connector.close(dbConnection);
		try {
			dbConnection.getClientInfo();
		} catch (SQLException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testDatabaseInsertNewTag() {
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		MyTag tag = new MyTag();
		TagData epc = tag.getEpc();
		List<Integer> epcList = new ArrayList<Integer>();
		epcList.add(0x3034);
		epcList.add(0x2910);
		epcList.add(0x4c4d);
		epcList.add(0x2000);
		epcList.add(0x0000);
		epcList.add(0x0001);
		
		try {
			epc = TagData.fromWordList(epcList);
		} catch (OctaneSdkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((MyTag) tag).assignEPC(epc);
		TagWrapper tWrapper = new TagWrapper(tag);
		boolean success = connector.insertTag(tWrapper, dbConnection, "back room");
		assertTrue(success);
	}
	
	@Test
	public void testDatabaseInsertDuplicateTag() {
		TagLocation backroom = new TagLocation("back room");
		TagLocation warehouse = new TagLocation("warehouse");
		ReaderLocation backroom_warehouse = new ReaderLocation(backroom.getName(), warehouse.getName());
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		MyTag tag = new MyTag();
		TagData epc = tag.getEpc();
		List<Integer> epcList = new ArrayList<Integer>();
		epcList.add(0x3034);
		epcList.add(0x2910);
		epcList.add(0x4c4d);
		epcList.add(0x2000);
		epcList.add(0x0000);
		epcList.add(0x0001);
		
		try {
			epc = TagData.fromWordList(epcList);
		} catch (OctaneSdkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((MyTag) tag).assignEPC(epc);
		TagWrapper tWrapper = new TagWrapper(tag);
		connector.insertTag(tWrapper, dbConnection, "back room");
		boolean failure = connector.insertTag(tWrapper, dbConnection, "back room");
		assertTrue(failure);
	}
	
	@Test
	public void testDatabaseInsertConnectionLost() {
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		MyTag tag = new MyTag();
		TagData epc = tag.getEpc();
		List<Integer> epcList = new ArrayList<Integer>();
		epcList.add(0x3034);
		epcList.add(0x2910);
		epcList.add(0x4c4d);
		epcList.add(0x2000);
		epcList.add(0x0000);
		epcList.add(0x0001);
		
		try {
			epc = TagData.fromWordList(epcList);
		} catch (OctaneSdkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((MyTag) tag).assignEPC(epc);
		TagWrapper tWrapper = new TagWrapper(tag);
		
		dbConnection = null;
		
		boolean failure = connector.insertTag(tWrapper, dbConnection, "back room");
		assertTrue(failure);
	}
	
	@Test
	public void testDatabaseUpdateTagNewLocation() {
		TagLocation backroom = new TagLocation("back room");
		TagLocation warehouse = new TagLocation("warehouse");
		ReaderLocation backroom_warehouse = new ReaderLocation(backroom.getName(), warehouse.getName());
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		MyTag tag = new MyTag();
		TagData epc = tag.getEpc();
		List<Integer> epcList = new ArrayList<Integer>();
		epcList.add(0x3034);
		epcList.add(0x2910);
		epcList.add(0x4c4d);
		epcList.add(0x2000);
		epcList.add(0x0000);
		epcList.add(0x0001);
		
		try {
			epc = TagData.fromWordList(epcList);
		} catch (OctaneSdkException e) {
			fail();
			e.printStackTrace();
		}
		((MyTag) tag).assignEPC(epc);
		TagWrapper tWrapper = new TagWrapper(tag);
		tWrapper.setLocation(warehouse);
		boolean success = connector.insertTag(tWrapper, dbConnection, "back room");
		assertTrue(success);
		
		tWrapper.setLocation(backroom);
		tWrapper.setLocationScanned(backroom_warehouse);
		success = connector.updateTag(tWrapper, dbConnection);
		assertTrue(success);
		
		//really kind of needs to be getTag
		boolean found = connector.findTag(tWrapper, dbConnection);
		assertTrue(found);
	}
	
	@Test
	public void testDatabaseUpdateSameLocation() {
		HashMap<StoreConfigurationKey, TagLocation> map = setupStoreLayoutMap();
		TagLocation backroom = new TagLocation("back room");
		TagLocation warehouse = new TagLocation("warehouse");
		ReaderLocation backroom_warehouse = new ReaderLocation(backroom.getName(), warehouse.getName());
		JavaRFIDConnector javaConnector = new JavaRFIDConnector();
		JavaRFIDConnector.setStoreConfigurationMap(map);
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		MyTag tag = new MyTag();
		TagData epc = tag.getEpc();
		List<Integer> epcList = new ArrayList<Integer>();
		epcList.add(0x3034);
		epcList.add(0x2910);
		epcList.add(0x4c4d);
		epcList.add(0x2000);
		epcList.add(0x0000);
		epcList.add(0x0001);
		
		try {
			epc = TagData.fromWordList(epcList);
		} catch (OctaneSdkException e) {
			fail();
			e.printStackTrace();
		}
		((MyTag) tag).assignEPC(epc);
		TagWrapper tWrapper = new TagWrapper(tag);
		boolean success = connector.insertTag(tWrapper, dbConnection, "back room");
		tWrapper.setLocation(backroom);
		
		tWrapper.setLocationScanned(backroom_warehouse);
		success = connector.updateTag(tWrapper, dbConnection);
		assertTrue(success);
		
		//really kind of needs to be getTag
		boolean found = connector.findTag(tWrapper, dbConnection);
		assertTrue(found);
	}
	
	@Test
	public void testDatabaseUpdateOUT_OF_STORE() {
		TagLocation out_of_store = new TagLocation("out of store");
		TagLocation warehouse = new TagLocation("warehouse");
		TagLocation backroom = new TagLocation("back room");
		ReaderLocation outOfStore_warehouse = new ReaderLocation(out_of_store.getName(), warehouse.getName());
		ReaderLocation backroom_warehouse = new ReaderLocation(backroom.getName(), warehouse.getName());
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		MyTag tag = new MyTag();
		TagData epc = tag.getEpc();
		List<Integer> epcList = new ArrayList<Integer>();
		epcList.add(0x3034);
		epcList.add(0x2910);
		epcList.add(0x4c4d);
		epcList.add(0x2000);
		epcList.add(0x0000);
		epcList.add(0x0001);
		
		try {
			epc = TagData.fromWordList(epcList);
		} catch (OctaneSdkException e) {
			fail();
			e.printStackTrace();
		}
		((MyTag) tag).assignEPC(epc);
		TagWrapper tWrapper = new TagWrapper(tag);
		tWrapper.setLocation(out_of_store);
		boolean success = connector.insertTag(tWrapper, dbConnection, out_of_store.getName());
		assertTrue(success);
		
		tWrapper.setLocation(warehouse);
		tWrapper.setLocationScanned(backroom_warehouse);
		success = connector.updateTag(tWrapper, dbConnection);
		assertTrue(success);
		
		//really kind of needs to be getTag
		boolean found = connector.findTag(tWrapper, dbConnection);
		assertTrue(found);
	}
	
	
	@Test
	public void testDatabaseUpdateTagConnectionLost() {
		TagLocation warehouse = new TagLocation("warehouse");
		TagLocation backroom = new TagLocation("backroom");
		ReaderLocation backroom_warehouse = new ReaderLocation(warehouse.getName(), backroom.getName());
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		MyTag tag = new MyTag();
		TagData epc = tag.getEpc();
		List<Integer> epcList = new ArrayList<Integer>();
		epcList.add(0x3034);
		epcList.add(0x2910);
		epcList.add(0x4c4d);
		epcList.add(0x2000);
		epcList.add(0x0000);
		epcList.add(0x0001);
		
		try {
			epc = TagData.fromWordList(epcList);
		} catch (OctaneSdkException e) {
			fail();
			e.printStackTrace();
		}
		((MyTag) tag).assignEPC(epc);
		TagWrapper tWrapper = new TagWrapper(tag);
		tWrapper.setLocation(warehouse);
		boolean success = connector.insertTag(tWrapper, dbConnection, "back room");
		assertTrue(success);
		
		//Lost connection
		dbConnection = null;
		
		tWrapper.setLocation(backroom);
		tWrapper.setLocationScanned(backroom_warehouse);
		boolean failure = !connector.updateTag(tWrapper, dbConnection);
		assertTrue(failure);
	}
	
	private HashMap<StoreConfigurationKey, TagLocation> setupStoreLayoutMap() {
		HashMap<StoreConfigurationKey, TagLocation> storeMap = new HashMap<StoreConfigurationKey, TagLocation>();
		TagLocation backroom = new TagLocation("back room");
		TagLocation warehouse = new TagLocation("warehouse");
		TagLocation out_of_store = new TagLocation("out of store");
		TagLocation storefloor = new TagLocation("store floor");
		
		ReaderLocation backroom_warehouse = new ReaderLocation(backroom.getName(), warehouse.getName());
		ReaderLocation warehouse_outOfStore = new ReaderLocation(warehouse.getName(), out_of_store.getName());
		ReaderLocation storefloor_backroom = new ReaderLocation(storefloor.getName(), backroom.getName());
		ReaderLocation storefloor_outOfStore = new ReaderLocation(storefloor.getName(), out_of_store.getName());
		
		StoreConfigurationKey key1, key2, key3, key4, key5, key6, key7, key8;
		key1 =  new StoreConfigurationKey(storefloor, storefloor_outOfStore);
		key2 = new StoreConfigurationKey(storefloor, storefloor_backroom);
		key3 = new StoreConfigurationKey(out_of_store, storefloor_outOfStore);
		key4 = new StoreConfigurationKey(out_of_store, warehouse_outOfStore);
		key5 = new StoreConfigurationKey(warehouse, warehouse_outOfStore);
		key6 = new StoreConfigurationKey(warehouse, backroom_warehouse);
		key7 = new StoreConfigurationKey(backroom, storefloor_backroom);
		key8 = new StoreConfigurationKey(backroom, backroom_warehouse);
		
		storeMap.put(key1, out_of_store);
		storeMap.put(key2, backroom);
		storeMap.put(key3, storefloor);
		storeMap.put(key4, warehouse);
		storeMap.put(key5,  out_of_store);
		storeMap.put(key6, backroom);
		storeMap.put(key7, storefloor);
		storeMap.put(key8, warehouse);
		return storeMap;
		}
}
