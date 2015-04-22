package edu.auburn.eng.sks0024.rfid_connector_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import org.junit.Test;

import edu.auburn.eng.sks0024.rfid_connector.JavaRFIDConnector;
import edu.auburn.eng.sks0024.rfid_connector.ReaderLocation;
import edu.auburn.eng.sks0024.rfid_connector.StoreConfigurationKey;
import edu.auburn.eng.sks0024.rfid_connector.TagLocation;

public class TagLocationTest {

	@Test
	public void TagUpdateLocationAcceptance() {
		Scanner scan = new Scanner(System.in);
		System.out.println("*********ENTER STORE LOCATIONS************");
		int numOfAntennasToSetup = 4;
		String storeAreaOne, storeAreaTwo;
		List<ReaderLocation> readerLocations = new ArrayList<ReaderLocation>();
		List<TagLocation> tagLocations = new ArrayList<TagLocation>();
		for (int i = 0; i  < numOfAntennasToSetup; i++) {
			System.out.print("Antenna " + (i + 1) + " store area one: ");
			storeAreaOne = scan.nextLine();
			System.out.print("Antenna " + (i + 1) + " store area two: ");
			storeAreaTwo = scan.nextLine();
			readerLocations.add(new ReaderLocation(storeAreaOne, storeAreaTwo));
			tagLocations.add(new TagLocation(storeAreaOne));
			tagLocations.add(new TagLocation(storeAreaTwo));
		}
		scan.close();
		JavaRFIDConnector rfidConnector = new JavaRFIDConnector();
		HashMap<StoreConfigurationKey, TagLocation> storeConfigMap = rfidConnector.generateStoreMap(tagLocations, readerLocations);
		
		int transitionNum = 1;
		for (Entry<StoreConfigurationKey, TagLocation> mapEntry : storeConfigMap.entrySet()) {
			System.out.println("Transition " + transitionNum + ": |Old Location: " + mapEntry.getKey().getTagLocation()
					+ " |Location Scanned: " + mapEntry.getKey().getReaderLocation()
					+ " |New Location: " + mapEntry.getValue());
			transitionNum++;
		}
	}

}
