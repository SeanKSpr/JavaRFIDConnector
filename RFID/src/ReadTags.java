import java.util.Collection;

import com.impinj.octanesdk.AntennaConfigGroup;
import com.impinj.octanesdk.ImpinjReader;
import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.ReaderMode;
import com.impinj.octanesdk.ReportConfig;
import com.impinj.octanesdk.ReportMode;
import com.impinj.octanesdk.Settings;


public final class ReadTags {
	private static Collection<TagWrapper> wrappedTags;
	//This time window indicates how long we ignore subsequent RFID reads of the SAME tag after the initial read. The time is in microseconds.
	static final int READ_DUPLICATE_TIME_WINDOW = 60000000;
    public static void main(String[] args) {

        try {
            String hostname = "192.168.225.50";                       

//            if (hostname == null) {
//                throw new Exception("Must specify the '" 
//                        + SampleProperties.hostname + "' property");
//            }
    
            ImpinjReader reader = new ImpinjReader();
            
            //System.out.println("Connecting");
            reader.connect(hostname);

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

            //System.out.println("Starting");
            reader.start();
            
            //System.out.println("Press Enter to exit.");
            //Scanner s = new Scanner(System.in);
            //s.nextLine();

            reader.stop();
            reader.disconnect();
        } catch (OctaneSdkException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.out);
        }
    }
	public static Collection<TagWrapper> getWrappedTags() {
		return wrappedTags;
	}
	
	public static void addWrappedTags(TagWrapper wrappedTag) {
		wrappedTags.add(wrappedTag);
	}
	
	public static void emptyCollection(Collection<TagWrapper> wrappedTags) {
		wrappedTags.clear();
	}
}
