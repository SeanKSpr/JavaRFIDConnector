package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import edu.auburn.eng.sks0024.rfid_connector.ReaderLocation;
import edu.auburn.eng.sks0024.rfid_connector.StoreConfigurationKey;
import edu.auburn.eng.sks0024.rfid_connector.TagLocation;

public class StoreConfigurationKeyTest {
	
	@Test
	public void tagLocationEqualTest() {
		TagLocation locA = new TagLocation("a");
		TagLocation same = new TagLocation("a");
		TagLocation locAWithSpace = new TagLocation("a ");
		TagLocation locAAsCapital = new TagLocation("A");
		TagLocation different = new TagLocation("completely different");
		TagLocation noName = new TagLocation();
		Object notATagLocation = new Object();
		
		assertTrue(locA.equals(locA));
		assertTrue(locA.equals(same));
		assertFalse(locA.equals(locAWithSpace));
		assertFalse(locA.equals(locAAsCapital));
		assertFalse(locA.equals(different));
		assertFalse(locA.equals(noName));
		assertFalse(locA.equals(notATagLocation));
	}
	
	@Test
	public void readerLocationEqualTest() {
		ReaderLocation locAB = new ReaderLocation("a", "b");
		ReaderLocation same = new ReaderLocation("a", "b");
		ReaderLocation locBA = new ReaderLocation("b", "a");
		ReaderLocation locABWithSpaces = new ReaderLocation("a ", " b");
		ReaderLocation locABCapital = new ReaderLocation("A", "b");
		ReaderLocation different = new ReaderLocation("c", "d");
		ReaderLocation oneLocIsSame = new ReaderLocation("a", "c");
		ReaderLocation noName = new ReaderLocation("", null);
		Object notAReaderLocation = new Object();
		
		assertTrue(locAB.equals(locAB));
		assertTrue(locAB.equals(same));
		assertTrue(locAB.equals(locBA));
		assertFalse(locAB.equals(locABWithSpaces));
		assertFalse(locAB.equals(locABCapital));
		assertFalse(locAB.equals(different));
		assertFalse(locAB.equals(oneLocIsSame));
		assertFalse(locAB.equals(noName));
		assertFalse(locAB.equals(notAReaderLocation));
	}
	
	@Test
	public void tagLocationHashTest() {
		TagLocation locA = new TagLocation("a");
		TagLocation same = new TagLocation("a");
		TagLocation locAWithSpace = new TagLocation("a ");
		TagLocation locAAsCapital = new TagLocation("A");
		TagLocation different = new TagLocation("completely different");
		TagLocation noName = new TagLocation();
		Object notATagLocation = new Object();
		
		assertEquals(locA.hashCode(), locA.hashCode());
		assertEquals(locA.hashCode(), same.hashCode());
		assertNotEquals(locA.hashCode(), locAWithSpace.hashCode());
		assertNotEquals(locA.hashCode(), locAAsCapital.hashCode());
		assertNotEquals(locA.hashCode(), different.hashCode());
		assertNotEquals(locA.hashCode(), noName.hashCode());
		assertNotEquals(locA.hashCode(), notATagLocation.hashCode());
	}
	
	@Test
	public void readerLocationHashTest() {
		ReaderLocation locAB = new ReaderLocation("a", "b");
		ReaderLocation same = new ReaderLocation("a", "b");
		ReaderLocation locBA = new ReaderLocation("b", "a");
		ReaderLocation locABWithSpaces = new ReaderLocation("a ", " b");
		ReaderLocation locABCapital = new ReaderLocation("A", "b");
		ReaderLocation different = new ReaderLocation("c", "d");
		ReaderLocation oneLocIsSame = new ReaderLocation("a", "c");
		ReaderLocation noName = new ReaderLocation("", null);
		Object notAReaderLocation = new Object();
		
		int locABHash = locAB.hashCode();
		int locBAHash = locBA.hashCode();
		System.out.println("locAB Hash: " + locABHash + " should equal locBA Hash: " + locBAHash);
		assertEquals(locAB.hashCode(), locAB.hashCode());
		assertEquals(locAB.hashCode(), same.hashCode());
		assertEquals(locAB.hashCode(), locBA.hashCode());
		assertNotEquals(locAB.hashCode(), locABWithSpaces.hashCode());
		assertNotEquals(locAB.hashCode(), locABCapital.hashCode());
		assertNotEquals(locAB.hashCode(), different.hashCode());
		assertNotEquals(locAB.hashCode(), oneLocIsSame.hashCode());
		assertNotEquals(locAB.hashCode(), noName.hashCode());
		assertNotEquals(locAB.hashCode(), notAReaderLocation.hashCode());
	}
	
	@Test
	public void nominalGetLocation() {
		TagLocation locA = new TagLocation("a");
		TagLocation locB = new TagLocation("b");
		TagLocation locC = new TagLocation("c");
		TagLocation locD = new TagLocation("d");
		TagLocation locE = new TagLocation("e");
		
		ReaderLocation readAB = new ReaderLocation("a", "b");
		ReaderLocation readBC = new ReaderLocation("b", "c");
		ReaderLocation readCD = new ReaderLocation("c", "d");
		ReaderLocation readDE = new ReaderLocation("d", "e");
		
		StoreConfigurationKey key1 = new StoreConfigurationKey(locA, readAB);
		StoreConfigurationKey key2 = new StoreConfigurationKey(locB, readAB);
		StoreConfigurationKey key3 = new StoreConfigurationKey(locB, readBC);
		StoreConfigurationKey key4 = new StoreConfigurationKey(locC, readBC);
		StoreConfigurationKey key5 = new StoreConfigurationKey(locC, readCD);
		StoreConfigurationKey key6 = new StoreConfigurationKey(locD, readCD);
		StoreConfigurationKey key7 = new StoreConfigurationKey(locD, readDE);
		StoreConfigurationKey key8 = new StoreConfigurationKey(locE, readDE);
		HashMap<StoreConfigurationKey, TagLocation> storeLayout = setUpStoreConfigurationHashMap();
		
		assertTrue(storeLayout.get(key1).getName().equals("b"));
		assertTrue(storeLayout.get(key2).getName().equals("a"));
		assertTrue(storeLayout.get(key3).getName().equals("c"));
		assertTrue(storeLayout.get(key4).getName().equals("b"));
		assertTrue(storeLayout.get(key5).getName().equals("d"));
		assertTrue(storeLayout.get(key6).getName().equals("c"));
		assertTrue(storeLayout.get(key7).getName().equals("e"));
		assertTrue(storeLayout.get(key8).getName().equals("d"));
		
	}
	
	@Test
	public void multipleSameLocations() {
		TagLocation locA = new TagLocation("a");
		TagLocation locADup = new TagLocation("a");
		TagLocation locC = new TagLocation("c");
		TagLocation locD = new TagLocation("d");
		TagLocation locE = new TagLocation("e");
		
		ReaderLocation readAC = new ReaderLocation("a", "c");
		ReaderLocation readCA = new ReaderLocation("a", "c");
		ReaderLocation readCD = new ReaderLocation("c", "d");
		ReaderLocation readDE = new ReaderLocation("d", "e");
		
		StoreConfigurationKey key1 = new StoreConfigurationKey(locA, readAC);
		StoreConfigurationKey key2 = new StoreConfigurationKey(locA, readCA);
		StoreConfigurationKey key3 = new StoreConfigurationKey(locADup, readAC);
		StoreConfigurationKey key4 = new StoreConfigurationKey(locADup, readCA);
		StoreConfigurationKey key5 = new StoreConfigurationKey(locC, readAC);
		StoreConfigurationKey key6 = new StoreConfigurationKey(locC, readCA);
		StoreConfigurationKey key7 = new StoreConfigurationKey(locC, readCD);
		StoreConfigurationKey key8 = new StoreConfigurationKey(locD, readCD);
		StoreConfigurationKey key9 = new StoreConfigurationKey(locD, readDE);
		StoreConfigurationKey key10 = new StoreConfigurationKey(locE, readDE);
		
		HashMap<StoreConfigurationKey, TagLocation> storeLayout = new HashMap<StoreConfigurationKey, TagLocation>();
		
		storeLayout.put(key1, locC);
		storeLayout.put(key2, locC);
		storeLayout.put(key3, locC);
		storeLayout.put(key4, locC);
		storeLayout.put(key5, locA);
		storeLayout.put(key6, locA);
		storeLayout.put(key5, locADup);
		storeLayout.put(key6, locADup);
		storeLayout.put(key7, locD);
		storeLayout.put(key8, locC);
		storeLayout.put(key9, locE);
		storeLayout.put(key10, locD);
		
		assertTrue(storeLayout.get(key1).getName().equals("c"));
		assertTrue(storeLayout.get(key2).getName().equals("c"));
		assertTrue(storeLayout.get(key3).getName().equals("c"));
		assertTrue(storeLayout.get(key4).getName().equals("c"));
		assertTrue(storeLayout.get(key5).getName().equals("a"));
		assertTrue(storeLayout.get(key6).getName().equals("a"));
		assertTrue(storeLayout.get(key7).getName().equals("d"));
		assertTrue(storeLayout.get(key8).getName().equals("c"));
		assertTrue(storeLayout.get(key9).getName().equals("e"));
		assertTrue(storeLayout.get(key10).getName().equals("d"));
	}
	
	private HashMap<StoreConfigurationKey, TagLocation> setUpStoreConfigurationHashMap() {
		TagLocation locA = new TagLocation("a");
		TagLocation locB = new TagLocation("b");
		TagLocation locC = new TagLocation("c");
		TagLocation locD = new TagLocation("d");
		TagLocation locE = new TagLocation("e");
		
		ReaderLocation readAB = new ReaderLocation("a", "b");
		ReaderLocation readBC = new ReaderLocation("b", "c");
		ReaderLocation readCD = new ReaderLocation("c", "d");
		ReaderLocation readDE = new ReaderLocation("d", "e");
		
		StoreConfigurationKey key1 = new StoreConfigurationKey(locA, readAB);
		StoreConfigurationKey key2 = new StoreConfigurationKey(locB, readAB);
		StoreConfigurationKey key3 = new StoreConfigurationKey(locB, readBC);
		StoreConfigurationKey key4 = new StoreConfigurationKey(locC, readBC);
		StoreConfigurationKey key5 = new StoreConfigurationKey(locC, readCD);
		StoreConfigurationKey key6 = new StoreConfigurationKey(locD, readCD);
		StoreConfigurationKey key7 = new StoreConfigurationKey(locD, readDE);
		StoreConfigurationKey key8 = new StoreConfigurationKey(locE, readDE);
		
		HashMap<StoreConfigurationKey, TagLocation> storeLayout = new HashMap<StoreConfigurationKey, TagLocation>();
		
		storeLayout.put(key1, locB);
		storeLayout.put(key2, locA);
		storeLayout.put(key3, locC);
		storeLayout.put(key4, locB);
		storeLayout.put(key5, locD);
		storeLayout.put(key6, locC);
		storeLayout.put(key7, locE);
		storeLayout.put(key8, locD);
		
		return storeLayout;
	}
}
