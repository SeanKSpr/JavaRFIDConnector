package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.LinkedList;
import java.util.List;

/**
 * Antenna is simply a data class for simplifying data transfer between the GUI and the underlying backend system.
 * This class contains all the information represented in the GUI and is all the information necessary to recreate an
 * RFID antenna/reader.
 * 
 * @since 1 (3-18-2015)
 * @version 1 (3-18-2015)
 * @author Sean Spurlin
 */
public class Antenna {
	private boolean isEnabled;
	private boolean isEntryPoint;
	private String storeAreaOne;
	private String storeAreaTwo;
	
	/**
	 * returns true if the antenna is enabled, false otherwise
	 * @return returns true if the antenna is enabled, false otherwise
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	
	/**
	 * returns true if the antenna is an entry point. An entry point is an antenna which can insert tags into the database.
	 * @return Returns true if the antenna is an entry point; false otherwise.
	 */
	public boolean isEntryPoint() {
		return isEntryPoint;
	}
	
	/**
	 * set method for isEntryPoint
	 * @param isEntryPoint a boolean value representing whether or not an antenna will be an entry point
	 */
	public void setEntryPoint(boolean isEntryPoint) {
		this.isEntryPoint = isEntryPoint;
	}
	
	/**
	 * returns the name of the first store area which the antenna is sitting between
	 * @return the name of the first store area which the antenna is sitting between
	 */
	public String getStoreAreaOne() {
		return storeAreaOne;
	}
	
	/**
	 * set method for storeAreaOne
	 * @param storeAreaOne the name of the first store area which the antenna is sitting between
	 */
	public void setStoreAreaOne(String storeAreaOne) {
		this.storeAreaOne = storeAreaOne;
	}
	
	/**
	 * returns the name of the second store area which the antenna is sitting between
	 * @return the name of the second store area which the antenna is sitting between
	 */
	public String getStoreAreaTwo() {
		return storeAreaTwo;
	}
	
	/**
	 * set method for storeAreaTwo
	 * @param storeAreaTwo the name of the first store area which the antenna is sitting between
	 */
	public void setStoreAreaTwo(String storeAreaTwo) {
		this.storeAreaTwo = storeAreaTwo;
	}
	
	/**
	 * set method for isEnabled
	 * @param isEnabled boolean value representing whether or not an antenna is enabled
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	/**
	 * toStringArray is a helper method for converting an Antenna object into an array of Strings
	 * [0] is the isEnabled boolean
	 * [1] is the isEntryPoint boolean
	 * [2] is the name of storeAreaOne
	 * [3] is the name of storeAreaTwo
	 * @return an array of Strings representing the data stored in the Antenna
	 */
	public String[] toStringArray() {
		String[] antennaProperties = new String[4];
		antennaProperties[0] = Boolean.toString(isEnabled);
		antennaProperties[1] = Boolean.toString(isEntryPoint);
		antennaProperties[2] = storeAreaOne;
		antennaProperties[3] = storeAreaTwo;
		return antennaProperties;
	}
	
	/**
	 *Another helper method which simply takes a list of Antenna objects, converts them into String arrays, and adds them
	 *to a list of String arrays. This is a static function.
	 * @param antennaList A List of Antenna objects
	 * @return A List of String arrays correlating to the Antennas
	 */
	public static List<String[]> toListOfStringArrays(List<Antenna> antennaList) {
		LinkedList<String[]> list = new LinkedList<String[]>();
		for (Antenna antenna : antennaList) {
			list.add(antenna.toStringArray());
		}
		return list;
	}
}