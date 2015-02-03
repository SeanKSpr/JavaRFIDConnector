import com.impinj.octanesdk.ImpinjReader;
import com.impinj.octanesdk.Tag;
import com.impinj.octanesdk.TagReport;
import com.impinj.octanesdk.TagReportListener;

/**
 * TagReporter is an implementation of the TagReportListener interface. It only has one function which is 
 * called each time a TagReport comes in standard input from the physical RFID reader. This class simply 
 * listens for these reports and adds any and all tags which have been read to a Collection of read tags
 * as long as the tags aren't deemed to be duplicate readings.
 * 
 * @since 2-3-2015
 * @version 1
 * @author Sean Spurlin
 */
public class TagReporter implements TagReportListener {
	
	@Override
	/**
	 * onTagReported is called whenever a TagReport comes in standard input from the physical RFID reader. These TagReports 
	 * contain one or more RFID tags which have been read. This function takes those tags from the reports, wraps them up to add
	 * RFID location and Time Seen functionality, timestamps them with the current time (Epoch time), and adds them to a collection
	 * of read tags as long as the tag is deemed to not have come from a duplicate read.
	 */
	public void onTagReported(ImpinjReader reader, TagReport report) {
		TagWrapper wrappedTag;
		for (Tag t : report.getTags()) {
			wrappedTag = new TagWrapper(t);
			
			if (!DuplicateReadDetector.isDuplicateRead(wrappedTag)) {
				DuplicateReadDetector.addWrappedTag(wrappedTag);
				System.out.println(DuplicateReadDetector.getTagBatchTimeInfo());
			}
			else {//TESTING PURPOSES ONLY
				//System.out.println("\n\n **************DUPLICATE FOUND***************\n");
			}
		}

	}
}
