import java.util.Scanner;

import com.impinj.octanesdk.AntennaConfigGroup;
import com.impinj.octanesdk.ImpinjReader;
import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.ReaderMode;
import com.impinj.octanesdk.ReportConfig;
import com.impinj.octanesdk.ReportMode;
import com.impinj.octanesdk.Settings;


public class TagReader implements RFIDReader {
	
	public static void main (String args[]) {
		TagReader reader = new TagReader();
		reader.readerBootstrap("192.168.225.50", ReaderLocation.FLOOR_BACKROOM);
		reader.startReader();
		
		
	}
	//Should probably just create a child class of the ImpinjReader that has ReaderLocation as a private field
	private String hostname, readerName;
	ReaderLocation location;
	public TagReader() {
		this.hostname = null;
		this.readerName = null;
	}
	
	public TagReader(String hostname) {
		this.hostname = hostname;
		this.readerName = null;
	}
	
	public TagReader(String hostname, String readerName) {
		this.hostname = hostname;
		this.readerName = readerName;
	}
	
	public TagReader(String hostname, ReaderLocation location) {
		this.hostname = hostname;
		this.location = location;
	}
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
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getReaderName() {
		return readerName;
	}
	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public void readerBootstrap(String hostname, ReaderLocation location) {
		this.hostname = hostname;
		this.location = location;
		
	}
}
