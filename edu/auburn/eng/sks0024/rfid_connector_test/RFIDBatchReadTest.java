package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.TagData;

import edu.auburn.eng.sks0024.rfid_connector.DBUpdateTimer;
import edu.auburn.eng.sks0024.rfid_connector.DuplicateReadDetector;
import edu.auburn.eng.sks0024.rfid_connector.TagWrapper;

public class RFIDBatchReadTest {

	@Test
	public void testRFIDBatchReadAccept() throws OctaneSdkException {
		MyTag tag;
		TagWrapper wrappedTag;
		LinkedList<Integer> tagEPC;
		for (int i = 0; i < 10; i++) {
			tag = new MyTag();
			tagEPC = new LinkedList<Integer>();
			for (int j = 0; j < 6; j++) {
				tagEPC.add((6 * i) + j);
			}
			tag.assignEPC(TagData.fromWordList(tagEPC));
			wrappedTag = new TagWrapper(tag);
			wrappedTag.setTimeSeen(System.currentTimeMillis() + i * 100);
			DuplicateReadDetector.addWrappedTag(wrappedTag);
		}
		
		DBUpdateTimer timer = new DBUpdateTimer();
		int tagsUpdated = timer.testUpdate();
		
		assertEquals(tagsUpdated, 10);
		
	}

}
