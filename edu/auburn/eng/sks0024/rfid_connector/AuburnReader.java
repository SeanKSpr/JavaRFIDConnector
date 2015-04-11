package edu.auburn.eng.sks0024.rfid_connector;
import com.impinj.octanesdk.ImpinjReader;

/**
 * AuburnReader is a child of the ImpinjReader documented in Impinj's OctaneSDK for Java. This class adds functionality
 * to an RFID reader that allows the retrieval of the reader's location.
 * @version 1.1 (4-11-2015)
 * @since	1 (2-3-2015)  
 * @author Sean Spurlin
 *
 */
public class AuburnReader extends ImpinjReader {
	private ReaderLocation location;
	private int antennaID;
	
	/**
	 * Precondition:		AuburnReader hasn't been instantiated. 
	 * Postcondition:		AuburnReader is instantiated with its reader location set to the input location
	 * @param location the location of the RFID reader; see ReaderLocation enumeration for documentation.
	 */
	public AuburnReader(ReaderLocation location) {
		super();
		this.location = location;
	}
	
	/**
	 * Constructor which generates a new AuburnReader which its fields set to null
	 */
	public AuburnReader() {
	}
	
	/**
	 * Constructor for the AuburnReader which takes in the location the reader can be found as well
	 * as an integer value associating this particular object with a particular RFID antenna by an antenna ID.
	 * @param location
	 * @param antennaID
	 */
	public AuburnReader(ReaderLocation location, int antennaID) {
		super();
		this.location = location;
		this.antennaID = antennaID;
	}
	/**
	 * Precondition:	AuburnReader has been instantiated
	 * Postcondition:	The ReaderLocation of the RFID reader will be returned; see ReaderLocation enumeration documentation
	 * for details
	 * 
	 * @return location of the RFID reader
	 */
	public ReaderLocation getLocation() {
		return location;
	}
	
	/**
	 * Precondition:	AuburnReader has been instantiated
	 * Postcondition:	The location of the RFID reader will be changed to the input location; see ReaderLocation enumeration documentation for valid inputs.
	 * @param location New location of the RFID reader
	 */
	public void setLocation(ReaderLocation location) {
		this.location = location;
	}

	public int getAntennaID() {
		return antennaID;
	}

	public void setAntennaID(int antennaID) {
		this.antennaID = antennaID;
	}
	
}
