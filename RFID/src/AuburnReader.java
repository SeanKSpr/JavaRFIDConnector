import com.impinj.octanesdk.ImpinjReader;

/**
 * AuburnReader is a child of the ImpinjReader documented in Impinj's OctaneSDK for Java. This class adds functionality
 * to an RFID reader that allows the retrieval of the reader's location.
 * @version 1
 * @since 2-3-2015
 * @author Sean Spurlin
 *
 */
public class AuburnReader extends ImpinjReader {
	private ReaderLocation location;
	
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
	
}
