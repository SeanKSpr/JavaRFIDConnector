package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import edu.auburn.eng.sks0024.rfid_connector.AuburnReader;
import edu.auburn.eng.sks0024.rfid_connector.JavaRFIDConnector;
import edu.auburn.eng.sks0024.rfid_connector.ReaderLocation;

public class AntennaDictionaryTest {

	@Test
	public void dictionaryNominalTest() {
		AuburnReader reader = new AuburnReader();
		JavaRFIDConnector connector = new JavaRFIDConnector("192.168.225.50", reader);
		ReaderLocation storefloor_backroom, backroom_warehouse, storefloor_outOfStore, warehouse_outOfStore;
		storefloor_backroom = new ReaderLocation("storefloor", "backroom");
		backroom_warehouse = new ReaderLocation("backroom", "warehouse");
		storefloor_outOfStore = new ReaderLocation("storefloor", "out of store");
		warehouse_outOfStore = new ReaderLocation("warehouse", "out of store");
		reader.addAntenna(1, storefloor_backroom);
		reader.addAntenna(2, backroom_warehouse);
		reader.addAntenna(3, storefloor_outOfStore);
		reader.addAntenna(4, warehouse_outOfStore);
		
		connector.setReader(reader);
		Assert.assertEquals(reader.getLocation((short) 1).toString(), (storefloor_backroom.toString()));
		assertEquals(reader.getLocation((short) 2).toString(), backroom_warehouse.toString());
		assertEquals(reader.getLocation((short) 3).toString(), storefloor_outOfStore.toString());
		assertEquals(reader.getLocation((short) 4).toString(), warehouse_outOfStore.toString());
	}
	
	@Test
	public void dictionaryNominalTestFromConnector() {
		AuburnReader reader = new AuburnReader();
		JavaRFIDConnector connector = new JavaRFIDConnector("192.168.225.50", reader);
		ReaderLocation storefloor_backroom, backroom_warehouse, storefloor_outOfStore, warehouse_outOfStore;
		storefloor_backroom = new ReaderLocation("storefloor", "backroom");
		backroom_warehouse = new ReaderLocation("backroom", "warehouse");
		storefloor_outOfStore = new ReaderLocation("storefloor", "out of store");
		warehouse_outOfStore = new ReaderLocation("warehouse", "out of store");
		connector.addReader("backroom", "storefloor", 1);
		connector.addReader("backroom", "warehouse", 2);
		connector.addReader("storefloor", "out of store", 3);
		connector.addReader("warehouse", "out of store", 4);
		
		Assert.assertEquals(reader.getLocation((short) 1), storefloor_backroom);
		assertEquals(reader.getLocation((short) 2), backroom_warehouse);
		assertEquals(reader.getLocation((short) 3), storefloor_outOfStore);
		assertEquals(reader.getLocation((short) 4), warehouse_outOfStore);
	}
	
	@Test
	public void dictionaryAntenna1And4Test() {
		AuburnReader reader = new AuburnReader();
		JavaRFIDConnector connector = new JavaRFIDConnector("192.168.225.50", reader);
		ReaderLocation storefloor_backroom, warehouse_outOfStore;
		storefloor_backroom = new ReaderLocation("storefloor", "backroom");
		warehouse_outOfStore = new ReaderLocation("warehouse", "out of store");
		connector.addReader("backroom", "storefloor", 1);
		connector.addReader("warehouse", "out of store", 4);
		
		Assert.assertEquals(reader.getLocation((short) 1), storefloor_backroom);
		assertEquals(reader.getLocation((short) 2), null);
		assertEquals(reader.getLocation((short) 3), null);
		assertEquals(reader.getLocation((short) 4), warehouse_outOfStore);
	}
	
	@Test
	public void dictionaryInsertionPointTest() {
		AuburnReader reader = new AuburnReader();
		JavaRFIDConnector connector = new JavaRFIDConnector("192.168.225.50", reader);
		ReaderLocation storefloor_backroom, warehouse_outOfStore;
		storefloor_backroom = new ReaderLocation("storefloor", "backroom");
		warehouse_outOfStore = new ReaderLocation("warehouse", "out of store");
		connector.addEntryPointReader("backroom", "storefloor", 1, "storefloor");
		connector.addEntryPointReader("warehouse", "out of store", 4, "warehouse");
		
		Assert.assertEquals(reader.getLocation((short) 1), storefloor_backroom);
		assertEquals(reader.getLocation((short) 2), null);
		assertEquals(reader.getLocation((short) 3), null);
		assertEquals(reader.getLocation((short) 4), warehouse_outOfStore);
	}

}
