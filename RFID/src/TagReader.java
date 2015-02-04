import java.util.Scanner;
import java.util.Timer;

import com.impinj.octanesdk.AntennaConfigGroup;
import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.ReaderMode;
import com.impinj.octanesdk.ReportConfig;
import com.impinj.octanesdk.ReportMode;
import com.impinj.octanesdk.Settings;

/**
 * TagReader is an implementation of the RFIDReader interface. This is the class that connects with the RFID Reader hardware
 * (currently Impinj Speedway Revolution). After making a connection with the reader, it begins reading RFID data from 
 * standard input which is handled by an onTagReportListener. The RFID read settings are set to their default (see OctaneSDK
 * ImpinjReader.queryDefaultSettings). In general, this class was drafted from the ReadTags.java file which is present in the
 * Impinj OctaneSDK samples folder. As of version 1.1 this class instantiates a TimerTask which will periodically update the
 * database with the latest tag information collected. 
 * NOTE: Currently this class does not support multiple readers, only a single reader.
 * 
 * @since  	1 	(2-3-2015)
 * @version 1.1	(2-4-2015)
 * @author Sean Spurlin
 */
public class TagReader implements RFIDReader {
	
	/**
	 * Driver method for the JavaConnector. It is currently hardcoded to interface with Speedway Revolution IP 192.168.225.50 which
	 * is assumed to be a reader located between the store floor and the backroom. After connecting to the reader, the reader starts up and
	 * begins receiving tag reports from the RFID reader.
	 * @param args Command line arguments (unsed)
	 */
	public static void main (String args[]) {
		TagReader reader = new TagReader();
		reader.readerBootstrap("192.168.225.50", ReaderLocation.FLOOR_BACKROOM);
		reader.startReader();
		
		
	}
	
	private String hostname, readerName;
	private ReaderLocation location;
	
	/**
	 * Default constructor which creates a new TagReader with fields set to null.
	 */
	public TagReader() {
		this.hostname = null;
		this.readerName = null;
		this.location = null;
	}
	
	/**
	 * Constructor which creates a new TagReader with the input host name and every other field null.
	 * @param hostname IP address/host name of the physical RFID reader which we are connecting.
	 */
	public TagReader(String hostname) {
		this.hostname = hostname;
		this.readerName = null;
		this.location = null;
	}
	
	/**
	 * Constructor which creates a new TagReader with the input host name and reader name and the reader location set to null.
	 * @param hostname IP address/host name of the physical RFID reader which we are connecting.
	 * @param readerName A user specified name for the reader (currently unused)
	 */
	public TagReader(String hostname, String readerName) {
		this.hostname = hostname;
		this.readerName = readerName;
	}
	
	/**
	 * Constructor which creates a new TagReader with the input host name and reader location. Reader name is set to null (currently unused)
	 * @param hostname IP address/host name of the physical RFID reader which we are connecting.
	 * @param location The location of the reader (see ReaderLocation enumeration for more information)
	 */
	public TagReader(String hostname, ReaderLocation location) {
		this.hostname = hostname;
		this.location = location;
	}
	/**
	 * Connects the Java AuburnReader to the physical RFID reader, does some general setup concerning how TagReports are handled,
	 * and other RFID reader related settings. It then creates a TagReporter which actually deals with the TagReports we receive from
	 * the RFID reader. These TagReports come in through standard input. This method runs until the system is turned off. Exceptions
	 * are thrown if the host name of the reader or its location haven't been set.
	 */
    public void startReader() {
        try {
            //String hostname = "192.168.225.50";                       

            if (hostname == null) {
                throw new Exception("Must specify the hostname property of the reader");
            }
            
            if (location == null) {
            	throw new Exception("Must specify the location of the reader");
            }
    
            AuburnReader reader = new AuburnReader();
            
            //System.out.println("Connecting");
            reader.connect(hostname);
            reader.setLocation(this.location);
            Settings settings = reader.queryDefaultSettings();

            ReportConfig report = settings.getReport();
            report.setIncludeAntennaPortNumber(true);
            report.setMode(ReportMode.Individual);

            settings.setReaderMode(ReaderMode.AutoSetDenseReader);

            // set some special settings for antenna 1
            AntennaConfigGroup antennas = settings.getAntennas();
            antennas.disableAll();
            antennas.enableById(new short[]{1});
            antennas.getAntenna((short) 1).setIsMaxRxSensitivity(false);
            antennas.getAntenna((short) 1).setIsMaxTxPower(false);
            antennas.getAntenna((short) 1).setTxPowerinDbm(20.0);
            antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70);

            reader.setTagReportListener(new TagReporter());

            //System.out.println("Applying Settings");
            reader.applySettings(settings);

            System.out.println("Starting");
            reader.start();
            
            //System.out.println("Press Enter to exit.");
            Scanner s = new Scanner(System.in);
            s.nextLine();

            reader.stop();
            reader.disconnect();
            s.close();
        } catch (OctaneSdkException ex) {
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
	public void readerBootstrap(String hostname, ReaderLocation location) {
		Timer timer = new Timer();
		DBUpdateTimer updateTimer = new DBUpdateTimer();
		timer.scheduleAtFixedRate(updateTimer, DBUpdateTimer.TIMER_DELAY, DBUpdateTimer.TIMER_DELAY);
		this.hostname = hostname;
		this.location = location;
		
	}
}
