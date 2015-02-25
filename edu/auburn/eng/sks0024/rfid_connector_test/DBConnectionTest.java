package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.TagData;

import edu.auburn.eng.sks0024.rfid_connector.PostgresConnector;
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
		assertEquals(connectionProperties.getProperty("url"), "jdbc:postgresql://localhost:5432/rfiddb");
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
		assertEquals(connectionProperties.getProperty("url"), "jdbc:postgresql://localhost:5432/rfiddb");
		
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
		boolean success = connector.insertTag(tWrapper, dbConnection);
		assertTrue(success);
	}
	
	@Test
	public void testDatabaseInsertDuplicateTag() {
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
		connector.insertTag(tWrapper, dbConnection);
		boolean failure = connector.insertTag(tWrapper, dbConnection);
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
		
		boolean failure = connector.insertTag(tWrapper, dbConnection);
		assertTrue(failure);
	}
	
	@Test
	public void testDatabaseInsertNullTag() {
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		TagWrapper tWrapper = null;
		boolean failure = connector.insertTag(tWrapper, dbConnection);
		assertTrue(failure);
	}
	
	@Test
	public void testDatabaseUpdateTagNewLocation() {
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
		tWrapper.setLocation(TagLocation.WAREHOUSE);
		boolean success = connector.insertTag(tWrapper, dbConnection);
		assertTrue(success);
		
		tWrapper.setLocation(TagLocation.BACK_ROOM);
		success = connector.updateTag(tWrapper, dbConnection);
		assertTrue(success);
		
		//really kind of needs to be getTag
		boolean found = connector.findTag(tWrapper, dbConnection);
		assertTrue(found);
	}
	
	@Test
	public void testDatabaseUpdateSameLocation() {
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
		tWrapper.setLocation(TagLocation.WAREHOUSE);
		boolean success = connector.insertTag(tWrapper, dbConnection);
		assertTrue(success);
		
		tWrapper.setLocation(TagLocation.WAREHOUSE);
		success = connector.updateTag(tWrapper, dbConnection);
		assertTrue(success);
		
		//really kind of needs to be getTag
		boolean found = connector.findTag(tWrapper, dbConnection);
		assertTrue(found);
	}
	
	@Test
	public void testDatabaseUpdateOUT_OF_STORE() {
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
		tWrapper.setLocation(TagLocation.OUT_OF_STORE);
		boolean success = connector.insertTag(tWrapper, dbConnection);
		assertTrue(success);
		
		tWrapper.setLocation(TagLocation.WAREHOUSE);
		success = connector.updateTag(tWrapper, dbConnection);
		assertTrue(success);
		
		//really kind of needs to be getTag
		boolean found = connector.findTag(tWrapper, dbConnection);
		assertTrue(found);
	}
	

	@Test
	public void testDatabaseUpdateNull() {
		PostgresConnector connector = new PostgresConnector();
		Connection dbConnection = connector.open();
		boolean failure = connector.updateTag(null, dbConnection);
		assertTrue(failure);
	
	}
	
	@Test
	public void testDatabaseUpdateTagConnectionLost() {
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
		tWrapper.setLocation(TagLocation.WAREHOUSE);
		boolean success = connector.insertTag(tWrapper, dbConnection);
		assertTrue(success);
		
		//Lost connection
		dbConnection = null;
		
		tWrapper.setLocation(TagLocation.BACK_ROOM);
		boolean failure = connector.updateTag(tWrapper, dbConnection);
		assertTrue(failure);
	}
	
}