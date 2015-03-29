package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.ArrayList;
import java.util.List;

public class Antenna {
	private boolean enabled;
	private boolean isEntryPoint;
	private String storeAreaOne, storeAreaTwo;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public boolean isEntryPoint() {
		return isEntryPoint;
	}



	public void setEntryPoint(boolean isEntryPoint) {
		this.isEntryPoint = isEntryPoint;
	}



	public String getStoreAreaOne() {
		return storeAreaOne;
	}



	public void setStoreAreaOne(String storeAreaOne) {
		this.storeAreaOne = storeAreaOne;
	}



	public String getStoreAreaTwo() {
		return storeAreaTwo;
	}



	public void setStoreAreaTwo(String storeAreaTwo) {
		this.storeAreaTwo = storeAreaTwo;
	}

	public String toString() {
		return Boolean.toString(enabled) + ", " + Boolean.toString(isEntryPoint) + ", " + storeAreaOne + ", " + storeAreaTwo;
	}
	
	public String[] toStringArray() {
		String[] antennaProperties = new String[4];
		antennaProperties[0] = Boolean.toString(enabled);
		antennaProperties[1] = Boolean.toString(isEntryPoint);
		antennaProperties[2] = storeAreaOne;
		antennaProperties[3] = storeAreaTwo;
		return antennaProperties;
	}

	public static List<String[]> toListOfStringArrays(
			ArrayList<Antenna> antennaList) {
		ArrayList<String[]> antennas = new ArrayList<String[]>();
		for (Antenna antenna : antennaList) {
			antennas.add(antenna.toStringArray());
		}
		return antennas;
	}

}
