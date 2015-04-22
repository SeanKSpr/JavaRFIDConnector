package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class StoreLayoutWindow {

	protected Shell shlCreateStoreLayout;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			StoreLayoutWindow window = new StoreLayoutWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlCreateStoreLayout.open();
		shlCreateStoreLayout.layout();
		while (!shlCreateStoreLayout.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCreateStoreLayout = new Shell();
		shlCreateStoreLayout.setSize(558, 322);
		shlCreateStoreLayout.setText("Create Store Layout");
		
		text = new Text(shlCreateStoreLayout, SWT.BORDER);
		text.setBounds(139, 27, 156, 21);
		
		text_1 = new Text(shlCreateStoreLayout, SWT.BORDER);
		text_1.setBounds(139, 54, 156, 21);
		
		Label lblLocation = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation.setBounds(35, 57, 85, 15);
		lblLocation.setText("Location #2:");
		
		Label lblLocation_1 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_1.setText("Location #1:");
		lblLocation_1.setBounds(35, 30, 85, 15);
		
		Label lblLocation_2 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_2.setText("Location #3:");
		lblLocation_2.setBounds(35, 84, 85, 15);
		
		Label lblLocation_3 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_3.setText("Location #4:");
		lblLocation_3.setBounds(35, 111, 85, 15);
		
		Label lblLocation_4 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_4.setText("Location #5:");
		lblLocation_4.setBounds(35, 138, 85, 15);
		
		Label lblLocation_5 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_5.setText("Location #6:");
		lblLocation_5.setBounds(35, 165, 85, 15);
		
		text_2 = new Text(shlCreateStoreLayout, SWT.BORDER);
		text_2.setBounds(139, 81, 156, 21);
		
		text_3 = new Text(shlCreateStoreLayout, SWT.BORDER);
		text_3.setBounds(139, 108, 156, 21);
		
		text_4 = new Text(shlCreateStoreLayout, SWT.BORDER);
		text_4.setBounds(139, 135, 156, 21);
		
		text_5 = new Text(shlCreateStoreLayout, SWT.BORDER);
		text_5.setBounds(139, 162, 156, 21);
		
		text_6 = new Text(shlCreateStoreLayout, SWT.BORDER);
		text_6.setBounds(139, 189, 156, 21);
		
		text_7 = new Text(shlCreateStoreLayout, SWT.BORDER);
		text_7.setBounds(139, 216, 156, 21);
		
		Label lblLocation_6 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_6.setText("Location #7:");
		lblLocation_6.setBounds(35, 192, 85, 15);
		
		Label lblLocation_7 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_7.setText("Location #8:");
		lblLocation_7.setBounds(35, 219, 85, 15);
		
		Label lblNewLabel = new Label(shlCreateStoreLayout, SWT.WRAP);
		lblNewLabel.setBounds(357, 84, 139, 109);
		lblNewLabel.setText("Please enter names for the locations within your store. You may enter a maximum of 8 locations to be supported by this scanner.");
		
		Button btnSaveLayout = new Button(shlCreateStoreLayout, SWT.NONE);
		btnSaveLayout.setBounds(357, 216, 75, 25);
		btnSaveLayout.setText("Save Layout");
		
		Button btnCancel = new Button(shlCreateStoreLayout, SWT.NONE);
		btnCancel.setBounds(442, 216, 75, 25);
		btnCancel.setText("Cancel");

		List<String> currentContents = RFIDConfigurationManager.readStoreLocations();
		
	}
}
