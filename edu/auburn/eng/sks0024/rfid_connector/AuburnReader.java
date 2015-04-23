package edu.auburn.eng.sks0024.rfid_connector;
import java.util.HashMap;

import com.impinj.octanesdk.ImpinjReader;

/**
 * AuburnReader is a child of the ImpinjReader documented in Impinj's OctaneSDK for Java. This class adds functionality
 * to an RFID reader that allows the retrieval of the reader's location through use of a dictionary which relates RFID
 * antenna identifiers to a particular location.
 * @version 1.2 (4-13-2015)
 * @since	1.1 (4-11-2015)  
 * @author Sean Spurlin
 *
 */
public class AuburnReader extends ImpinjReader {
	private HashMap<Short, ReaderLocation> antennaDictionary;
	
	/**
	 * Constructor which generates a new AuburnReader with a new dictionary
	 */
	public AuburnReader() {
		antennaDictionary = new HashMap<Short, ReaderLocation>();
	}
	
	/**
	 * Constructor for the AuburnReader which takes in the location the reader can be found as well
	 * as an integer value associating this particular object with a particular RFID antenna by an antenna ID.
	 * @param location the location to be associated with an RFID antenna
	 * @param antennaID the identifier for an RFID antenna
	 */
	public AuburnReader(ReaderLocation location, int antennaID) {
		super();
		antennaDictionary = new HashMap<Short, ReaderLocation>();
		antennaDictionary.put((short)antennaID, location);
	}
	/**
	 * Returns the ReaderLocation associated with the passed antennaID
	 * @param  antennaID the identifier for an RFID antenna
	 * @return location of the RFID reader
	 */
	public ReaderLocation getLocation(Short antennaID) {
		return antennaDictionary.get(antennaID);
	}
	
	/**
	 * Adds a new entry into the antenna dictionary. The key is the antennaID and the value associated with the key
	 * is the related ReaderLocation.
	 * @param antennaID the identifier for an RFID antenna which is used as a key
	 * @param location the location of that RFID antenna
	 */
	public void addAntenna(int antennaID, ReaderLocation location) {
		antennaDictionary.put((short)antennaID, location);
	}
	
	/**
	 * Getter function for obtaining the antenna dictionary
	 * @return the dictionary of antennas and their associated locations
	 */
	public HashMap<Short, ReaderLocation> getAntennaDictionary() {
		return antennaDictionary;
	}
	
	/**
	 * Setter function for setting the antenna dictionary to a new dictionary
	 * @param antennaDictionary a dictionary containing antennaIDs as keys which are each associated with a ReaderLocation
	 */
	public void setAntennaDictionary(HashMap<Short, ReaderLocation> antennaDictionary) {
		this.antennaDictionary = antennaDictionary;
	}
	
}
