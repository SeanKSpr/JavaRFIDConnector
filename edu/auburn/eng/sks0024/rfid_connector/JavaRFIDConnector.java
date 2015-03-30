package edu.auburn.eng.sks0024.rfid_connector;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;

import com.impinj.octanesdk.AntennaConfigGroup;
import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.ReaderMode;
import com.impinj.octanesdk.ReportConfig;
import com.impinj.octanesdk.ReportMode;
import com.impinj.octanesdk.Settings;

/**
 * JavaRFIDConnector is an implementation of the RFIDReader interface. This is the class that connects with the RFID Reader hardware
 * (currently Impinj Speedway Revolution). After making a connection with the reader, it begins reading RFID data from 
 * standard input which is handled by an onTagReportListener. The RFID read settings are set to their default (see OctaneSDK
 * ImpinjReader.queryDefaultSettings). In general, this class was drafted from the ReadTags.java file which is present in the
 * Impinj OctaneSDK samples folder. As of version 1.1 this class instantiates a TimerTask which will periodically update the
 * database with the latest tag information collected. 
 * NOTE: Currently this class does not support multiple readers, only a single reader.
 * 
 * @since  	1 	(2-23-2015)
 * @version 1.3	(3-14-2015)
 * @author Sean Spurlin
 */
public class JavaRFIDConnector implements RFIDConnector {
	
	/**
	 * Driver method for the JavaConnector. It is currently hardcoded to interface with Speedway Revolution IP 192.168.225.50 which
	 * is assumed to be a reader located between the store floor and the backroom. After connecting to the reader, the reader starts up and
	 * begins receiving tag reports from the RFID reader.
	 * @param args Command line arguments (unsed)
	 */
	public static void main (String args[]) {
		JavaRFIDConnector reader = new JavaRFIDConnector();
		//reader.readerBootstrap("192.168.225.50", ReaderLocation.FLOOR_BACKROOM);
		reader.run();
		
		
	}
	
	private String hostname, readerName;
	private ArrayList<AuburnReader> readerList = new ArrayList<AuburnReader>();
	private static HashMap<StoreConfigurationKey, TagLocation> storeConfigurationMap = new HashMap<StoreConfigurationKey, TagLocation>();
			
	/**
	 * Default constructor which creates a new TagReader with fields set to null.
	 */
	public JavaRFIDConnector() {
		this.hostname = null;
		this.readerName = null;
	}
	
	/**
	 * Constructor which creates a new TagReader with the input host name and every other field null.
	 * @param hostname IP address/host name of the physical RFID reader which we are connecting.
	 */
	public JavaRFIDConnector(String hostname) {
		this.hostname = hostname;
		this.readerName = null;
	}
	
	/**
	 * Constructor which creates a new TagReader with the input host name and reader name and the reader location set to null.
	 * @param hostname IP address/host name of the physical RFID reader which we are connecting.
	 * @param readerName A user specified name for the reader (currently unused)
	 */
	public JavaRFIDConnector(String hostname, String readerName) {
		this.hostname = hostname;
		this.readerName = readerName;
	}
	
	/**
	 * Connects the Java AuburnReader to the physical RFID reader, does some general setup concerning how TagReports are handled,
	 * and other RFID reader related settings. It then creates a TagReporter which actually deals with the TagReports we receive from
	 * the RFID reader. These TagReports come in through standard input. This method runs until the system is turned off. Exceptions
	 * are thrown if the host name of the reader or its location haven't been set.
	 */
    public void run() {
        try {                      
            if (hostname == null) {
                throw new Exception("Must specify the hostname property of the reader");
            }
                     
            for (int readerID = 0; readerID < this.readerList.size(); readerID++) {
            	AuburnReader reader = readerList.get(readerID);
	            if (reader.getLocation() == null) {
	            	throw new Exception("Must specify the location of the reader");
	            }
	            configureReaderSettings(readerID, reader);
	            reader.start();
	            Scanner s = new Scanner(System.in);
	            s.nextLine();
	            reader.stop();
	            reader.disconnect();
	            s.close();
	        } 
            
        }catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
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
	
	/**
	 * Bootstrapping function which is required to set up the reader's host name and location.
	 */
	public void connectorBootstrap(String hostname) {
		Timer timer = new Timer();
		DBUpdateTimer updateTimer = new DBUpdateTimer();
		timer.scheduleAtFixedRate(updateTimer, DBUpdateTimer.TIMER_DELAY, DBUpdateTimer.TIMER_DELAY);
		this.hostname = hostname;
		
	}
	
	/**
	 * getReaderList returns the list of RFID readers/antennas which have been connected
	 * @return List of connected RFID readers
	 */
	public ArrayList<AuburnReader> getReaderList() {
		return readerList;
	}
	
	/**
	 * This function is used by the RFIDManagerWindow. It will pass the text stored in the ReaderLocation combo and this function
	 * will take that string, transform it into its equivalent ReaderLocation enum, set up the reader information, and then add 
	 * the reader to the list of RFID readers.
	 * @param storeAreaOne The location of the RFID reader as a String. This parameter comes from the RFIDManagerWindow class's 
	 * AntennaLocation Combo objects.
	 * @param storeAreaTwo TODO
	 * @param antennaID TODO
	 */
	public void addReader(String storeAreaOne, String storeAreaTwo, int antennaID) {
		AuburnReader reader = new AuburnReader();
		ReaderLocation location = new ReaderLocation(storeAreaOne, storeAreaTwo);
		//ReaderLocationEnum location = ReaderLocationEnum.convertLocation(storeAreaOne);
		reader.setLocation(location);
		
		this.readerList.add(reader);
	}

	private void configureReaderSettings(int antennaID, AuburnReader reader) {
		try {
		reader.connect(hostname);
        Settings settings = reader.queryDefaultSettings();

        ReportConfig report = settings.getReport();
        report.setIncludeAntennaPortNumber(true);
        report.setMode(ReportMode.Individual);

        settings.setReaderMode(ReaderMode.AutoSetDenseReader);

        // set some special settings for antenna 1
        AntennaConfigGroup antennas = settings.getAntennas();
        antennas.disableAll();
        antennas.enableById(new short[]{(short) antennaID});
        antennas.getAntenna((short) antennaID).setIsMaxRxSensitivity(false);
        antennas.getAntenna((short) antennaID).setIsMaxTxPower(false);
        antennas.getAntenna((short) antennaID).setTxPowerinDbm(20.0);
        antennas.getAntenna((short) antennaID).setRxSensitivityinDbm(-70);

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
	 * @param currentLocation
	 * @param locationScanned
	 * @return
	 */
	public static TagLocation getNewLocation(TagLocation currentLocation, ReaderLocation locationScanned) {
		return storeConfigurationMap.get(new StoreConfigurationKey(currentLocation, locationScanned));
	}
}
