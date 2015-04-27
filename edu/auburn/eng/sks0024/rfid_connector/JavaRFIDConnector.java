package edu.auburn.eng.sks0024.rfid_connector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;

import com.impinj.octanesdk.AntennaConfigGroup;
import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.ReaderMode;
import com.impinj.octanesdk.ReportConfig;
import com.impinj.octanesdk.ReportMode;
import com.impinj.octanesdk.Settings;

import edu.auburn.eng.rfid_4710.manager_gui.Antenna;
import edu.auburn.eng.rfid_4710.manager_gui.ServerInfo;

/**
 * JavaRFIDConnector is an implementation of the RFIDReader interface. This is the class that connects with the RFID Reader hardware
 * (currently Impinj Speedway Revolution). After making a connection with the reader, it begins reading RFID data from 
 * standard input which is handled by an onTagReportListener. In general, this class was drafted from the ReadTags.java file which is present in the
 * Impinj OctaneSDK samples folder. As of version 1.1 this class instantiates a TimerTask which will periodically update the
 * database with the latest tag information collected. In version 1.4 a dictionary was added associating RFID antennas with their designated location. 
 * NOTE: Currently this class does not support multiple readers, only a single impinj reader that is connected to up to 4 RFID antennas.
 * 
 * @since  	1.3 	(3-14-2015)
 * @version 1.4		(4-11-2015)
 * @author Sean Spurlin
 */
public class JavaRFIDConnector implements RFIDConnector {
	private String hostname, readerName;
	private AuburnReader reader;
	private static HashMap<StoreConfigurationKey, TagLocation> storeConfigurationMap = new HashMap<StoreConfigurationKey, TagLocation>();
			

	/**
	 * Default constructor which creates a new JavaRFIDConnector with fields set to null and a default AuburnReader.
	 */
	public JavaRFIDConnector() {
		this.hostname = null;
		this.readerName = null;
		this.setReader(new AuburnReader());
	}
	
	/**
	 * Constructor which creates a new JavaRFIDConnector with the input host name and a default AuburnReader.
	 * @param hostname IP address/host name of the physical RFID reader which we are connecting.
	 */
	public JavaRFIDConnector(String hostname) {
		this.hostname = hostname;
		this.readerName = null;
		this.setReader(new AuburnReader());
	}
	
	/**
	 * Constructor which creates a new JavaRFIDConnector with the input host name and reader and a default AuburnReader
	 * @param hostname IP address/host name of the physical RFID reader which we are connecting.
	 * @param readerName A user specified name for the reader (currently unused)
	 */
	public JavaRFIDConnector(String hostname, String readerName) {
		this.hostname = hostname;
		this.readerName = readerName;
		this.setReader(new AuburnReader());
	}
	
	/**
	 * Constructor which creates a new JavaRFIDConnector with the input host name and AuburnReader. 
	 * @param hostname the Host name/IP of the physical Impinj RFID Scanner 
	 * @param reader the virtual representation of the Impinj RFID Scanner which includes fields and functions which allow the user
	 * to know where RFID antennas are located.
	 */
	public JavaRFIDConnector(String hostname, AuburnReader reader) {
		this.hostname = hostname;
		this.reader = reader;
	}
	
	/**
	 * Connects the Java AuburnReader to the physical RFID reader, does some general setup concerning how TagReports are handled,
	 * and other RFID reader related settings. It then creates a TagReporter which actually deals with the TagReports we receive from
	 * the RFID reader. These TagReports come in through standard input. This method runs until the system is turned off. Exceptions
	 * are thrown if the host name of the reader or its location haven't been set.
	 */
    public void run() {
        try {
            short[] antennaListForConfiguring = getAntennaListForConfiguring();
	        configureReaderSettings(antennaListForConfiguring, reader);
            reader.start();
            Scanner s = new Scanner(System.in);
            s.nextLine();
            s.close();
            reader.stop();
            reader.disconnect();
            
        }catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
    
    /**
     * Obtains the key set from the AuburnReader's dictionary. This key set contains all the RFID antennas IDs which have been plugged in to the
     * Impinj RFID Scanner. We take this set and convert it into a short array and return it.
     * @return a short array containing the antenna IDs of those RFID antennas which are connected.
     */
	private short[] getAntennaListForConfiguring() {
		Set<Short> antennaKeySet = reader.getAntennaDictionary().keySet();
		Short[] antennaIDs = new Short[antennaKeySet.size()];
		antennaIDs = antennaKeySet.toArray(antennaIDs);
		short[] antennaListForConfiguring = new short[antennaIDs.length];
		for (int i = 0; i < antennaListForConfiguring.length; i++) {
			antennaListForConfiguring[i] = antennaIDs[i];
		}
		return antennaListForConfiguring;
	}
    
    /**
     * Gets the IP/Host name of the reader
     * @return IP/Host name
     */
	public String getHostname() {
		return hostname;
	}
	
	/**
	 * Sets the IP/host name of the reader to the input
	 * @param hostname the IP/host name of the physical RFID reader
	 */
	public void setHostname(String hostname) {
		System.out.println("Setting Hostname to " + hostname);
		this.hostname = hostname;
	}
	
	/**
	 * Gets the name of the RFID reader
	 * @return name of the RFID reader
	 */
	public String getReaderName() {
		return readerName;
	}
	
	/**
	 * Sets the name of the RFID reader to the input
	 * @param readerName the new name of the RFID reader
	 */
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}
	
	public void connectorBootstrap(String hostname, ServerInfo serverInformation, List<Antenna> antennaList) throws Exception {
		if (hostname == null || hostname.equals("")) {
            throw new Exception("Must specify the hostname property of the reader");
        }
		if (serverInformation != null ) {
			if (serverInformation.getUrl() == null || serverInformation.getUrl().equals("")) {
				throw new Exception("Must specify the URL property of the server information");
			}
			if (serverInformation.getOwner() == null || serverInformation.getOwner().equals("")) {
				throw new Exception("Must specify the owner/username property of the server information");
			}
			if (serverInformation.getPassword() == null || serverInformation.getPassword().equals("")) {
				throw new Exception("Must specify the password property of the server information");
			}
		}
		else {
			throw new Exception("Must specify database information");
		}
		if (antennaList.isEmpty()) {
			throw new Exception("Must specify at least one Antenna");
		}
		else {
			if (!containsConfigredAntenna(antennaList)){
				throw new Exception("Must have at least one Antenna properly configured");
			}
		}
		setupDatabaseUpdater();
		this.hostname = hostname;
		setupDatabaseInformation(serverInformation);
		setupAntennas(antennaList);
		setupStoreConfigMap();
	}
	
	/**
	 * 
	 * @param antennaList List of antennas which are connected to the Impinj RFID Scanner and have been enabled.
	 * @return True if the antennaList contains at least one antenna with its store area one and two set; false otherwise
	 */
	private boolean containsConfigredAntenna(List<Antenna> antennaList) {
 		for (Antenna antenna : antennaList) {
			if (antenna.getStoreAreaOne() != null && !antenna.getStoreAreaOne().equals("")) {
				if (antenna.getStoreAreaTwo() != null && !antenna.getStoreAreaTwo().equals("")) {
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * Sets up the store configuration map. This function is to be called by the connectorBootstrap and is how the store configuration map
	 * will be created during actual client usage. This function uses the AuburnReader's dictionary (which is setup in the connectorBootstrap)
	 * in order to get a list of ReaderLocations and TagLocations. These lists are then passed to generateStoreMap in order to actually construct
	 * the store configuration map.
	 */
	private void setupStoreConfigMap() {
		ArrayList<ReaderLocation> readerLocations = new ArrayList<ReaderLocation>();
		ArrayList<TagLocation> tagLocations = new ArrayList<TagLocation>();
		readerLocations.addAll(this.reader.getAntennaDictionary().values());
		for (ReaderLocation readerLocation : readerLocations) {
			tagLocations.add(new TagLocation(readerLocation.getStoreAreaOne()));
			tagLocations.add(new TagLocation(readerLocation.getStoreAreaTwo()));
		}
		generateStoreMap(tagLocations, readerLocations);
	}
	
	/**
	 * Takes a list of Antenna objects and adds entries to the AuburnReader based on the information contained within the objects.
	 * This sets up the AuburnReader's dictionary.
	 * @param antennaList list of Antennas to be added to the AuburnReader
	 */
	private void setupAntennas(List<Antenna> antennaList) {
		for (Antenna antenna : antennaList) {
			if (antenna.isEnabled()) {
				if (antenna.isEntryPoint()) {
					addEntryPointReader(antenna.getStoreAreaOne(), antenna.getStoreAreaTwo(), antenna.getAntennaID(), antenna.getInsertionLocation());
				}
				else {
					addReader(antenna.getStoreAreaOne(), antenna.getStoreAreaTwo(), antenna.getAntennaID());
				}
			}
		}
	}
	
	/**
	 * Simply takes the ServerInfo object and uses it to bootstrap the PostgresConnector.
	 * @param serverInformation information about the database to be connected. Information includes database name, password, and url.
	 */
	private void setupDatabaseInformation(ServerInfo serverInformation) {
		PostgresConnector connector = new PostgresConnector();
		connector.setServerInformation(serverInformation);
	}
	
	/**
	 * Sets up the DBUpdateTimer which will periodically send read Tags to the PostgresConnector in order to be inserted or updated in the
	 * database.
	 */
	private void setupDatabaseUpdater() {
		Timer timer = new Timer();
		DBUpdateTimer updateTimer = new DBUpdateTimer();
		timer.scheduleAtFixedRate(updateTimer, DBUpdateTimer.TIMER_DELAY, DBUpdateTimer.TIMER_DELAY);
	}
	
	/**
	 * Adds a new antenna and associated ReaderLocation to the dictionary of the AuburnReader being
	 * maintained by the JavaRFIDConnector. 
	 * @param storeAreaOne One of the locations the rfid reader sits between
	 * @param storeAreaTwo  The other location the rfid reader sits between
	 * @param antennaID The identifier for the rfid antenna this location information is to be associated with
	 */
	public void addReader(String storeAreaOne, String storeAreaTwo, int antennaID) {
		ReaderLocation location = new ReaderLocation(storeAreaOne, storeAreaTwo);
		this.reader.addAntenna(antennaID, location);
	}
	
	/**
	 * Adds a new entry point antenna and associated ReaderLocation to the dictionary of the AuburnReader being maintained
	 * by the JavaRFIDConnector.
	 * @param storeAreaOne One of the locations the rfid reader sits between
	 * @param storeAreaTwo The other location the rfid reader sits between
	 * @param antennaID The identifier for the rfid antenna this location information is to be associated with
	 * @param insertionLocation The location where tags should be inserted if they are scanned by this antenna
	 */
	public void addEntryPointReader(String storeAreaOne, String storeAreaTwo, int antennaID, String insertionLocation) {
		ReaderLocation location = new ReaderLocation(storeAreaOne, storeAreaTwo);
		location.setEntryPoint(true);
		location.setInsertionPoint(new TagLocation(insertionLocation));
		this.reader.addAntenna(antennaID, location);
	}
	
	/**
	 * Method which takes in a list of TagLocations and a list of ReaderLocations and associates the two lists together in order to generate a HashMap of
	 * TagLocation transitions aka the store layout map. 
	 * @param tagLocations List of possible locations tags can be located in the store
	 * @param readerLocations List of possible locations readers can be located in the store
	 * @return a map which contains all the valid tag location transitions through the store.
	 */
	public HashMap<StoreConfigurationKey, TagLocation> generateStoreMap(List<TagLocation> tagLocations, List<ReaderLocation> readerLocations) {
		for (TagLocation tagLocation : tagLocations) {
			for (ReaderLocation readerLocation : readerLocations) {
				if (tagLocation.getName().equals(readerLocation.getStoreAreaOne())) {
					StoreConfigurationKey key = new StoreConfigurationKey(tagLocation, readerLocation);
					storeConfigurationMap.put(key, new TagLocation(readerLocation.getStoreAreaTwo()));
				}
				else if (tagLocation.getName().equals(readerLocation.getStoreAreaTwo())) {
					StoreConfigurationKey key = new StoreConfigurationKey(tagLocation, readerLocation);
					storeConfigurationMap.put(key, new TagLocation(readerLocation.getStoreAreaOne()));
				}
			}
		}
		return storeConfigurationMap;
	}
	
	/**
	 * Function which takes in an AuburnReader along with an antennaID so that it can associated the reader
	 * object with the physical RFID antenna and set up some antenna properties such as power and sensitivity
	 * and also configure the TagReports that the AuburnReader will be receiving.
	 * @param antennaIDs an array containing IDs to those RFID antennas which are plugged into the Impinj RFID
	 * scanner and are to be set up.
	 * @param reader This is the RFID Reader object which will be receiving messages from the physical Impinj Reader
	 * in order to get rfid tag data.
	 */
	private void configureReaderSettings(short[] antennaIDs, AuburnReader reader) {
		try {
		
		reader.connect(hostname);
		
        Settings settings = reader.queryDefaultSettings();

        ReportConfig report = settings.getReport();
        report.setIncludeAntennaPortNumber(true);
        report.setMode(ReportMode.Individual);

        settings.setReaderMode(ReaderMode.AutoSetDenseReader);
        AntennaConfigGroup antennas = settings.getAntennas();
        antennas.enableById(antennaIDs);
        for (short antennaID : antennaIDs) {
	        antennas.getAntenna(antennaID).setIsMaxRxSensitivity(false);
	        antennas.getAntenna(antennaID).setIsMaxTxPower(false);
	        antennas.getAntenna(antennaID).setTxPowerinDbm(20.0);
	        antennas.getAntenna(antennaID).setRxSensitivityinDbm(-70);
        }
        reader.setTagReportListener(new TagReporter());

        reader.applySettings(settings);
		} catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
	}
	
	/**
	 * Adds a new entry into the StoreConfiguration hash map. 
	 * This function was introduced in version 1.3 to facilitate user defined tag location areas and transitions
	 * @param key - Key to the HashMap entry
	 * @param value - Value associated with the key
	 */
	public void addStoreConfigKeyValue(StoreConfigurationKey key, TagLocation value) {
		storeConfigurationMap.put(key, value);
	}
	
	/**
	 * Returns a new tag location based on the input current tag location and where the tag was scanned.
	 * If the transition isn't valid then this function returns null.
	 * This function was introduced in version 1.3 to facilitate user defined tag location areas and transitions.
	 * @param currentLocation the current location of the RFID tag
	 * @param locationScanned the location where the RFID tag was scanned
	 * @return the new location the RFID tag can be located
	 */
	public static TagLocation getNewLocation(TagLocation currentLocation, ReaderLocation locationScanned) {
		return storeConfigurationMap.get(new StoreConfigurationKey(currentLocation, locationScanned));
	}
	
	/**
	 * Getter function for retrieving the AuburnReader being maintained by this JavaRFIDConnector
	 * @return the AuburnReader of this class
	 */
	public AuburnReader getReader() {
		return reader;
	}
	
	/**
	 * Setter function for assigning the AuburnReader of this class to a new AuburnReader
	 * @param reader a new AuburnReader
	 */
	public void setReader(AuburnReader reader) {
		this.reader = reader;
	}
	
	/**
	 * Returns the store configuration map containing keys of <TagLocation, ReaderLocation> and values of <TagLocation> such that
	 * if a Tag is scanned at a particular ReaderLocation the Tag will transition to a new location.
	 * @return the store configuration map
	 */
	public static HashMap<StoreConfigurationKey, TagLocation> getStoreConfigurationMap() {
		return storeConfigurationMap;
	}
	
	/**
	 * Setter method for modifying the store configuration map
	 * @param storeConfigurationMap a new store configuration map to overwrite the preexisting one within this object.
	 */
	public static void setStoreConfigurationMap(
			HashMap<StoreConfigurationKey, TagLocation> storeConfigurationMap) {
		JavaRFIDConnector.storeConfigurationMap = storeConfigurationMap;
	}
}
