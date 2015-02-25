package edu.auburn.eng.sks0024.rfid_connector_test;

import com.impinj.octanesdk.Tag;
import com.impinj.octanesdk.TagData;

public class MyTag extends Tag {

	public void assignEPC(TagData epc) {
		super.setEpc(epc);
	}

}
