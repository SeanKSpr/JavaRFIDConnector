package edu.auburn.eng.sks0024.rfid_connector_test;

import java.util.ArrayList;

import edu.auburn.eng.sks0024.rfid_connector.EPCConverter;
import static org.junit.Assert.*;

import org.junit.Test;

public class EPCConverterTest {

	@Test
	public void testInstance01GetUPC() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0x3034);
		testList.add(0x031d);
		testList.add(0xfc53);
		testList.add(0x2dc0);
		testList.add(0x0000);
		testList.add(0x0001);
		
		String UPC = EPCConverter.getUPC(testList);
		assertEquals(UPC, "051071851756");
	}
	
	@Test
	public void testInstance02GetUPC() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0x3034);
		testList.add(0x031d);
		testList.add(0xfc53);
		testList.add(0x2dc0);
		testList.add(0x0000);
		testList.add(0x0002);
		
		String UPC = EPCConverter.getUPC(testList);
		assertEquals(UPC, "051071851756");
	}
	
	//Row 82 of excel file EPC - 3034 2910 4C4D 2000 0000 0001
	@Test
	public void testInstance03GetUPC() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0x3034);
		testList.add(0x2910);
		testList.add(0x4c4d);
		testList.add(0x2000);
		testList.add(0x0000);
		testList.add(0x0001);
		
		String UPC = EPCConverter.getUPC(testList);
		assertEquals(UPC, "672787789760");
	}
	
	//Row 93 of excel file EPC - 3034 0266 2C4E D800 0000 0001
	@Test
	public void testInstance04GetUPC() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0x3034);
		testList.add(0x0266);
		testList.add(0x2c4e);
		testList.add(0xd800);
		testList.add(0x0000);
		testList.add(0x0001);
		
		String UPC = EPCConverter.getUPC(testList);
		assertEquals(UPC, "039307807364");
	}
	
	@Test
	public void testInstance04GetSerial() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0x3034);
		testList.add(0x0266);
		testList.add(0x2c4e);
		testList.add(0xd800);
		testList.add(0x0000);
		testList.add(0x0001);
		
		long serial = EPCConverter.getSerial(testList);
		assertEquals(serial, 1);
	}
	
	@Test
	public void testInstance05GetSerial() {
		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList.add(0x3034);
		testList.add(0x0521);
		testList.add(0xd042);
		testList.add(0xc42d);
		testList.add(0x0000);
		testList.add(0x0015); 
		
		long serial = EPCConverter.getSerial(testList);
		long actualSerial = Long.parseLong("193273528341");
		assertEquals(serial, actualSerial);
		
		
		
	}
}
