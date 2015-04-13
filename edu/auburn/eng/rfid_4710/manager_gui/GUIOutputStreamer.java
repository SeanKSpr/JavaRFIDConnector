package edu.auburn.eng.rfid_4710.manager_gui;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.swt.custom.StyledText;

public class GUIOutputStreamer extends OutputStream {
	private StyledText styledText;
	
	public GUIOutputStreamer(StyledText styledText) {
		this.styledText = styledText;
	}
    @Override
    public void write(final int b) throws IOException {
      styledText.append(String.valueOf((char) b));
    }
 
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
    	styledText.append(new String(b, off, len));
    }
 
    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }
}
