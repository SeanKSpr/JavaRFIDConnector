package edu.auburn.eng.sks0024.rfid_connector;

/**
 * TagLocation is a data class for keeping track of where an RFID tag can be located. 
 * @author Sean Spurlin
 * @version 1 (3-14-2015)
 * @since 1 (3-14-2015)
 */
public class TagLocation {
	private String name;
	
	/**
	 * Default constructor for TagLocation. Sets name of the location to null
	 */
	public TagLocation() {
		this.name = null;
	}
	
	/**
	 * Constructor for TagLocation. Sets the name of the location to the input name
	 * @param name Name of the location a Tag is located
	 */
	public TagLocation(String name) {
		this.name = name;
	}
	
	/**
	 * Overloaded equals to compare two TagLocation objects by their data. If the data is determined to be equal,
	 * then the two TagLocations are also equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null || (obj.getClass() != this.getClass()))) {
			return false;
		}
		TagLocation otherLocation = (TagLocation)obj;
		return this.name.equals(otherLocation.getName());
	}
	
	/**
	 * Overloaded hashCode to generate a hash code for the TagLocation object based on the data of the TagLocation.
	 */
	@Override
	public int hashCode() {
		int result = 0;
		result = 31 * result + (name != null ? this.name.hashCode() : 0);
		return result;
	}
	
	/**
	 * A getter function for getting the name field of the TagLocation
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * A setter function for setting the name field of the TagLocation
	 * @param name Name of the TagLocation
	 */
	public void setName(String name) {
		this.name = name;
	}
}
