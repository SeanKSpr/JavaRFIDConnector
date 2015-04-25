package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class StoreLayoutWindow {

	protected Shell shlCreateStoreLayout;
	private Text loc1Text;
	private Text loc2Text;
	private Text loc3Text;
	private Text loc4Text;
	private Text loc5Text;
	private Text loc6Text;
	private Text loc7Text;
	private Text loc8Text;
	private ArrayList<String> storeLayout;

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
	
	public ArrayList<String> getCustomLayout() {
		storeLayout = RFIDConfigurationManager.readStoreLocations();
		try {
			StoreLayoutWindow window = new StoreLayoutWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storeLayout;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCreateStoreLayout = new Shell();
		shlCreateStoreLayout.setSize(558, 322);
		shlCreateStoreLayout.setText("Create Store Layout");
		createLocationUI();
		createLayoutLabel();
		createSaveButton();
		createLoadButton();
	}

	private void createLayoutLabel() {
		Label layoutLabel = new Label(shlCreateStoreLayout, SWT.WRAP);
		layoutLabel.setBounds(357, 84, 139, 109);
		layoutLabel.setText("Please enter names for the locations within your store. You may enter a maximum of 8 locations to be supported by this scanner.");
	}

	private void createLoadButton() {
		Button btnCancel = new Button(shlCreateStoreLayout, SWT.NONE);
		btnCancel.setBounds(442, 216, 75, 25);
		btnCancel.setText("Cancel");
		btnCancel.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				shlCreateStoreLayout.dispose();
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
	}

	private void createSaveButton() {
		Button btnSaveLayout = new Button(shlCreateStoreLayout, SWT.NONE);
		btnSaveLayout.setBounds(357, 216, 75, 25);
		btnSaveLayout.setText("Save Layout");
		btnSaveLayout.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				sendStoreLayoutToConfigManager();
				shlCreateStoreLayout.dispose();
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
	}

	private void createLocationUI() {
		createLocation1UI();
		createLocation2UI();
		createLocation3UI();
		createLocation4UI();
		createLocation5UI();
		createLocation6UI();
		createLocation7UI();
		createLocation8UI();
	}

	private void createLocation8UI() {
		loc8Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc8Text.setBounds(139, 216, 156, 21);
		
		Label lblLocation_8 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_8.setText("Location #8:");
		lblLocation_8.setBounds(35, 219, 85, 15);
	}

	private void createLocation7UI() {
		loc7Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc7Text.setBounds(139, 189, 156, 21);
		
		Label lblLocation_7 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_7.setText("Location #7:");
		lblLocation_7.setBounds(35, 192, 85, 15);
	}

	private void createLocation6UI() {
		loc6Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc6Text.setBounds(139, 162, 156, 21);
		
		Label lblLocation_6 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_6.setText("Location #6:");
		lblLocation_6.setBounds(35, 165, 85, 15);
	}

	private void createLocation5UI() {
		loc5Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc5Text.setBounds(139, 135, 156, 21);
		
		Label lblLocation_5 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_5.setText("Location #5:");
		lblLocation_5.setBounds(35, 138, 85, 15);
	}

	private void createLocation4UI() {
		loc4Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc4Text.setBounds(139, 108, 156, 21);
		
		Label lblLocation_4 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_4.setText("Location #4:");
		lblLocation_4.setBounds(35, 111, 85, 15);
	}

	private void createLocation3UI() {
		loc3Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc3Text.setBounds(139, 81, 156, 21);
		
		Label lblLocation_3 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_3.setText("Location #3:");
		lblLocation_3.setBounds(35, 84, 85, 15);
	}

	private void createLocation2UI() {
		loc2Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc2Text.setBounds(139, 54, 156, 21);
		
		Label lblLocation_2 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_2.setBounds(35, 57, 85, 15);
		lblLocation_2.setText("Location #2:");
	}

	private void createLocation1UI() {
		loc1Text = new Text(shlCreateStoreLayout, SWT.BORDER);
		loc1Text.setBounds(139, 27, 156, 21);
		
		Label lblLocation_1 = new Label(shlCreateStoreLayout, SWT.NONE);
		lblLocation_1.setText("Location #1:");
		lblLocation_1.setBounds(35, 30, 85, 15);
	}

	private ArrayList<String> getStoreLocations() {
		Set<String> locations = new HashSet<String>();
		HashMap<Integer, String> locationTextFieldStrings = getLocationsFromFields();
		for (int i = 0; i < locationTextFieldStrings.size(); i++) {
			String location = locationTextFieldStrings.get(new Integer(i + 1));
			if (notNullOrEmpty(location)) {
				locations.add(location);
			}
		}
		return new ArrayList<String>(locations);
	}

	private boolean notNullOrEmpty(String location) {
		return location != null && !location.equals("");
	}

	private void sendStoreLayoutToConfigManager() {
		storeLayout = getStoreLocations();
		RFIDConfigurationManager.createStoreLocations(storeLayout);
	}
	
	private HashMap<Integer, String> getLocationsFromFields() {
		HashMap<Integer, String> locations = new HashMap<Integer, String>();
		locations.put(1, loc1Text.getText().trim());
		locations.put(2, loc2Text.getText().trim());
		locations.put(3, loc3Text.getText().trim());
		locations.put(4, loc4Text.getText().trim());
		locations.put(5, loc5Text.getText().trim());
		locations.put(6, loc6Text.getText().trim());
		locations.put(7, loc7Text.getText().trim());
		locations.put(8, loc8Text.getText().trim());
		return locations;
	}
}
