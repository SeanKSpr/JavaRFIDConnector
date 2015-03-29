package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.LinkedList;
import java.util.List;

public class Antenna {
	private boolean isEnabled;
	private boolean isEntryPoint;
	private String storeAreaOne;
	private String storeAreaTwo;
	
	public boolean isEnabled() {
		return isEnabled;
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
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public String[] toStringArray() {
		String[] antennaProperties = new String[4];
		antennaProperties[0] = Boolean.toString(isEnabled);
		antennaProperties[1] = Boolean.toString(isEntryPoint);
		antennaProperties[2] = storeAreaOne;
		antennaProperties[3] = storeAreaTwo;
		return antennaProperties;
	}
	public static List<String[]> toListOfStringArrays(List<Antenna> antennaList) {
		LinkedList<String[]> list = new LinkedList<String[]>();
		for (Antenna antenna : antennaList) {
			list.add(antenna.toStringArray());
		}
		return list;
	}
}