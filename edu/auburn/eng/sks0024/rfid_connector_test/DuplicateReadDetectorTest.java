package edu.auburn.eng.sks0024.rfid_connector_test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.TagData;

import edu.auburn.eng.sks0024.rfid_connector.DuplicateReadDetector;
import edu.auburn.eng.sks0024.rfid_connector.TagWrapper;

public class DuplicateReadDetectorTest {
	@Test
	public void testEpcEqualDiffSize() {
		LinkedList<Integer> tagEPC1, tagEPC2;
		tagEPC1 = new LinkedList<Integer>();
		tagEPC2 = new LinkedList<Integer>();
		
		tagEPC1.add(0x0040);
		tagEPC1.add(0x1d00);
		tagEPC1.add(0x84f1);
		tagEPC1.add(0x32aa);
		tagEPC1.add(0x28bc);
		tagEPC1.add(0x59aa);
		
		tagEPC2.add(0x59bb);
		tagEPC2.add(0x0000);
		tagEPC2.add(0xffff);
		tagEPC2.add(0xaaaa);
		tagEPC2.add(0x0010);
		
		assertFalse(DuplicateReadDetector.epcEqual(tagEPC1, tagEPC2));
		
	}
	
	@Test
	public void testEPCEqualTrue() {
		LinkedList<Integer> tagEPC1;
		tagEPC1 = new LinkedList<Integer>();

		tagEPC1.add(0x0040);
		tagEPC1.add(0x1d00);
		tagEPC1.add(0x84f1);
		tagEPC1.add(0x32aa);
		tagEPC1.add(0x28bc);
		tagEPC1.add(0x59aa);
		
		assertTrue(DuplicateReadDetector.epcEqual(tagEPC1, tagEPC1));
		
	}
	
	@Test
	public void testEPCEqualFalse() {
		LinkedList<Integer> tagEPC1, tagEPC2;
		tagEPC1 = new LinkedList<Integer>();
		tagEPC2 = new LinkedList<Integer>();
		
		tagEPC1.add(0x0040);
		tagEPC1.add(0x1d00);
		tagEPC1.add(0x84f1);
		tagEPC1.add(0x32aa);
		tagEPC1.add(0x28bc);
		tagEPC1.add(0x59aa);
		
		tagEPC2.add(0x59bb);
		tagEPC2.add(0x0000);
		tagEPC2.add(0xffff);
		tagEPC2.add(0xaaaa);
		tagEPC2.add(0x0010);
		tagEPC2.add(0x59aa);
		
		assertTrue(tagEPC1.size() == tagEPC2.size());
		assertFalse(DuplicateReadDetector.epcEqual(tagEPC1, tagEPC2));	
	}
	
	@Test
	public void testfindDuplicateTagWithLatestReadTime() throws OctaneSdkException {
		MyTag tag;
		TagWrapper wrappedTag;
		TagWrapper recentlyReadTag = null;
		LinkedList<Integer> tagEPC;
		for (int i = 0; i < 10; i++) {
			tag = new MyTag();
			tagEPC = new LinkedList<Integer>();
			for (int j = 0; j < 6; j++) {
				tagEPC.add((6 * i) + j);
			}
			tag.assignEPC(TagData.fromWordList(tagEPC));
			wrappedTag = new TagWrapper(tag);
			if (i == 6) {
				recentlyReadTag = new TagWrapper(tag);
				recentlyReadTag.setTimeSeen((long)(System.currentTimeMillis() * (float)1.1));
			}
			DuplicateReadDetector.addWrappedTag(wrappedTag);
		}
		
		assert(recentlyReadTag != null);
		
		tagEPC = (LinkedList<Integer>) DuplicateReadDetector.findDuplicateTagWithLatestReadTime(recentlyReadTag).getTag().getEpc().toWordList();
		LinkedList<Integer> tagEPC2 = (LinkedList<Integer>) recentlyReadTag.getTag().getEpc().toWordList();
		
		for (int i = 0; i < tagEPC.size(); i++) {
			assertEquals(tagEPC.get(i), tagEPC2.get(i));
		}
		TagWrapper duplicateTag = DuplicateReadDetector.findDuplicateTagWithLatestReadTime(recentlyReadTag);
		assertNotEquals(duplicateTag.getTimeSeen(), recentlyReadTag.getTimeSeen());
		DuplicateReadDetector.emptyCollection(DuplicateReadDetector.getWrappedTags());
	}
	
	@Test
	public void testfindDuplicateTagWithLatestReadTimeAllSameEPC() throws OctaneSdkException {
		MyTag tag;
		TagWrapper wrappedTag;
		TagWrapper recentlyReadTag = null;
		LinkedList<Integer> tagEPC;
		for (int i = 0; i < 10; i++) {
			tag = new MyTag();
			tagEPC = new LinkedList<Integer>();
			for (int j = 0; j < 6; j++) {
				tagEPC.add(i);
			}
			tag.assignEPC(TagData.fromWordList(tagEPC));
			wrappedTag = new TagWrapper(tag);
			if (i == 6) {
				recentlyReadTag = new TagWrapper(tag);
				recentlyReadTag.setTimeSeen((long)(System.currentTimeMillis() * (float)1.1));
			}
			DuplicateReadDetector.addWrappedTag(wrappedTag);
		}
		
		assert(recentlyReadTag != null);
		
		tagEPC = (LinkedList<Integer>) DuplicateReadDetector.findDuplicateTagWithLatestReadTime(recentlyReadTag).getTag().getEpc().toWordList();
		LinkedList<Integer> tagEPC2 = (LinkedList<Integer>) recentlyReadTag.getTag().getEpc().toWordList();
		
		for (int i = 0; i < tagEPC.size(); i++) {
			assertEquals(tagEPC.get(i), tagEPC2.get(i));
		}
		TagWrapper duplicateTag = DuplicateReadDetector.findDuplicateTagWithLatestReadTime(recentlyReadTag);
		assertNotEquals(duplicateTag.getTimeSeen(), recentlyReadTag.getTimeSeen());
		
		for (TagWrapper tw : DuplicateReadDetector.getWrappedTags()) {
			assertTrue(tw.getTimeSeen() <= duplicateTag.getTimeSeen());
		}
		DuplicateReadDetector.emptyCollection(DuplicateReadDetector.getWrappedTags());
	}
	
	@Test
	public void testIsDuplicateRead() throws OctaneSdkException {
		LinkedList<Integer> tagEPC1;
		tagEPC1 = new LinkedList<Integer>();
		
		tagEPC1.add(0x0040);
		tagEPC1.add(0x1d00);
		tagEPC1.add(0x84f1);
		tagEPC1.add(0x32aa);
		tagEPC1.add(0x28bc);
		tagEPC1.add(0x59aa);
		
		MyTag tag = new MyTag();
		MyTag tag2 = new MyTag();
		
		tag.assignEPC(TagData.fromWordList(tagEPC1));
		tag2.assignEPC(TagData.fromWordList(tagEPC1));
		
		TagWrapper tw1 = new TagWrapper(tag);
		TagWrapper tw2 = new TagWrapper(tag2);
		
		tw1.setTimeSeen(System.currentTimeMillis());
		tw2.setTimeSeen(System.currentTimeMillis());
		
		DuplicateReadDetector.addWrappedTag(tw1);
		assertTrue(DuplicateReadDetector.isDuplicateRead(tw2));
	}
	
	@Test
	public void testIsDuplicateReadFalse() throws OctaneSdkException {
		LinkedList<Integer> tagEPC1;
		tagEPC1 = new LinkedList<Integer>();
		
		tagEPC1.add(0x0040);
		tagEPC1.add(0x1d00);
		tagEPC1.add(0x84f1);
		tagEPC1.add(0x32aa);
		tagEPC1.add(0x28bc);
		tagEPC1.add(0x59aa);
		
		MyTag tag = new MyTag();
		MyTag tag2 = new MyTag();
		
		tag.assignEPC(TagData.fromWordList(tagEPC1));
		tag2.assignEPC(TagData.fromWordList(tagEPC1));
		
		TagWrapper tw1 = new TagWrapper(tag);
		TagWrapper tw2 = new TagWrapper(tag2);
		
		tw1.setTimeSeen(System.currentTimeMillis());
		tw2.setTimeSeen(System.currentTimeMillis() + 6000);
		
		DuplicateReadDetector.addWrappedTag(tw1);
		assertFalse(DuplicateReadDetector.isDuplicateRead(tw2));
	}
}
