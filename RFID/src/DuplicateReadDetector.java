import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class DuplicateReadDetector {
	private static Collection<TagWrapper> tagBatch = new LinkedList<TagWrapper>();
	//This time window indicates how long we ignore subsequent RFID reads of the SAME tag after the initial read. The time is in microseconds.
	static final int READ_DUPLICATE_TIME_WINDOW = 5000;
	
	public static Collection<TagWrapper> getWrappedTags() {
		return tagBatch;
	}
	
	public static void addWrappedTag(TagWrapper wrappedTag) {
		tagBatch.add(wrappedTag);
	}
	
	public static String getTagBatchTimeInfo() {
		String timeList = "";
		for (TagWrapper tw : tagBatch) {
			timeList += "EPC: " + tw.getTag().getEpc().toString() +  " Time: " + tw.getTimeSeen() + " ";
		}
		return timeList;
	}

	public static void emptyCollection(Collection<TagWrapper> wrappedTags) {
		wrappedTags.clear();
	}
	
	public static TagWrapper findDuplicateTagWithLatestReadTime(TagWrapper recentlyReadTag) {
		TagWrapper latestReadMatchingTag = new TagWrapper();
		for (TagWrapper tw : tagBatch) {
			List<Integer> EPC1 = recentlyReadTag.getTag().getEpc().toWordList();
			List<Integer> EPC2 = tw.getTag().getEpc().toWordList();
			if (epcEqual(EPC1, EPC2)) {
				if (latestReadMatchingTag.getTag() == null) {
					latestReadMatchingTag = tw;
				}
				else if (tw.getTimeSeen() > latestReadMatchingTag.getTimeSeen())
					latestReadMatchingTag = tw;
				}
			}
		return latestReadMatchingTag;
	}
	
	public static boolean isDuplicateRead(TagWrapper latestRead) {
		TagWrapper previousRead = findDuplicateTagWithLatestReadTime(latestRead);
		if (previousRead.getTag() == null) {
			return false;
		}
		long previousReadTime = previousRead.getTimeSeen();
		long latestReadTime = latestRead.getTimeSeen();
		
		//TESTING PURPOSES DELETE LATER
		long result = latestReadTime - previousReadTime;

		if (latestReadTime - previousReadTime < READ_DUPLICATE_TIME_WINDOW) {
			return true;
		}
		return false;
	}
	
	public static boolean epcEqual(List<Integer> tagEPC1, List<Integer> tagEPC2) {
		if (tagEPC1.size() != tagEPC2.size()) {
			return false;
		}
		for (int i = 0; i < tagEPC1.size(); i++) {
			if (tagEPC1.get(i).intValue() != tagEPC2.get(i).intValue()) {
				return false;
			}
		}
		return true;
	}
	
	
}
