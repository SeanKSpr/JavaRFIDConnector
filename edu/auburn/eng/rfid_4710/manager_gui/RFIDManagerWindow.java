package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.auburn.eng.sks0024.rfid_connector.JavaRFIDConnector;

/**
 * RFIDManagerWindow is a user interface for a manager or technology operator who needs to set up the RFID scanners for them to function as an
 * automated inventory management system. The information the manager inputs includes: the IP for the Impinj reader hub, 
 * whether a particular reader will be used, where that reader is located, and whether or not that reader will be used to record newly
 * added shipments to a database of inventory. The manager can also save configurations to a file and load configurations from configuration files
 * to populate the UI.
 *  
 * @author Sean Spurlin & Jared Watkins
 * @version 1 (2-23-2015)
 * @since 	1 (2-23-2015)
 *
 */
public class RFIDManagerWindow {

	protected Shell shell;
	private Text ReaderIPText;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RFIDManagerWindow window = new RFIDManagerWindow();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		//Create the window to house all the GUI elements
		shell = createShell();
		
		//Adds a label to hint to the user that the text field is for inputting the IP of the Impinj Reader
		Label lblReaderHostnameip = new Label(shell, SWT.NONE);
		lblReaderHostnameip.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblReaderHostnameip.setText("Reader Hostname/IP");
		
		//Setup the text field for inputting the reader IP
		ReaderIPText = createReaderIPText();
		
		//Create an ArrayList to store the Combo GUI elements. This is used when we are determining ReaderLocation for Antennas
		ArrayList<Object> readerComboList = new ArrayList<Object>();
		//Add the ArrayList as the "Data" of the shell (Data field is solely for the programmer to use)
		shell.setData(readerComboList);
		
		//Add some spacers for visual affect
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		//Creates the checkbox for determining whether Antenna 1 is enabled or not
		createBtnAntenna1Enabled();
		
		//Creates a Label which describes the Combo as a dropdown list of "Antenna Location"s
		Label lblAntenna1Location = new Label(shell, SWT.NONE);
		lblAntenna1Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAntenna1Location.setText("Antenna Location");
		
		//Creates the combo which contains possible reader locations for Antenna 1
		Combo comboAntenna1Location = createComboAntenna1Location();
		readerComboList.add(comboAntenna1Location);
		
		//Creates a checkbox for Antenna 1 for determining if the scanner will be used to insert tags
		Button chkboxAntenna1InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna1InputScanner.setText("Antenna is used to record new items");
		
		//Creates the checkbox for determining whether Antenna 2 is enabled or not
		createBtnAntenna2Enabled();
		
		//Creates a Label which describes the Combo as a dropdown list of "Antenna Location"s
		Label lblAntenna2Location = new Label(shell, SWT.NONE);
		lblAntenna2Location.setText("Antenna Location");
		lblAntenna2Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		//Creates the combo which contains possible reader locations for Antenna 2
		Combo comboAntenna2Location = createComboAntenna2Location();
		readerComboList.add(comboAntenna2Location);
		
		//Creates a checkbox for Antenna 2 for determining if the scanner will be used to insert tags
		Button chkboxAntenna2InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna2InputScanner.setText("Antenna is used to record new items");
		
		//Creates the checkbox for determining whether Antenna 3 is enabled or not
		createBtnAntenna3Enabled();
		
		//Creates a Label which describes the Combo as a dropdown list of "Antenna Location"s
		Label lblAntenna3Location = new Label(shell, SWT.NONE);
		lblAntenna3Location.setText("Antenna Location");
		lblAntenna3Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		//Creates the combo which contains possible reader locations for Antenna 4
		Combo comboAntenna3Location = createComboAntenna3Location();
		readerComboList.add(comboAntenna3Location);
		
		//Creates a checkbox for Antenna 3 for determining if the scanner will be used to insert tags
		Button chkboxAntenna3InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna3InputScanner.setText("Antenna is used to record new items");
		
		//Creates the checkbox for determining whether Antenna 4 is enabled or not
		createBtnAntenna4Enabled();
		
		//Creates a Label which describes the Combo as a dropdown list of "Antenna Location"s
		Label lblAntenna4Location = new Label(shell, SWT.NONE);
		lblAntenna4Location.setText("Antenna Location");
		lblAntenna4Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		//Creates the combo which contains possible reader locations for Antenna 4
		Combo comboAntenna4Location = createComboAntennaLocation();
		readerComboList.add(comboAntenna4Location);
		
		//Creates a checkbox for Antenna 4 for determining if the scanner will be used to insert tags
		Button chkboxAntenna4InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna4InputScanner.setText("Antenna is used to record new items");
		
		//Some spacers for formatting
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		//Creates the launch button
		createLaunchButton();
		
		//Creates the save configuration button
		createSaveConfigButton();
		
		//Creates the load configuration button
		createLoadConfigButton();
		
	}

	private void createLoadConfigButton() {
		Button btnLoadConfiguration = new Button(shell, SWT.NONE);
		btnLoadConfiguration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnLoadConfiguration.setText("Load Configuration");
	}

	private void createSaveConfigButton() {
		Button btnSaveConfiguration = new Button(shell, SWT.NONE);
		btnSaveConfiguration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				
			}
		});
		btnSaveConfiguration.setText("Save Configuration");
	}

	private void createBtnAntenna4Enabled() {
		Button btnAntennaEnabled_4 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAntennaEnabled_4.setText("Antenna 4 Enabled");
	}

	private void createBtnAntenna2Enabled() {
		Button btnAntennaEnabled_2 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAntennaEnabled_2.setText("Antenna 3 Enabled");	
	}

	private void createBtnAntenna3Enabled() {
		Button btnAntennaEnabled_3 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAntennaEnabled_3.setText("Antenna 3 Enabled");
	}

	private Button createLaunchButton() {
		Button btnLaunch = new Button(shell, SWT.NONE);
		btnLaunch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				JavaRFIDConnector connector = new JavaRFIDConnector();
				String ipaddress = ReaderIPText.getText();
				connector.connectorBootstrap(ipaddress);
				
				if (shell.getData() instanceof ArrayList) {
					for (Object obj : ((ArrayList<?>) shell.getData())) {
						if (obj instanceof Combo) {
							Combo comboAntennaLocation = (Combo)obj;
							String readerLocation = comboAntennaLocation.getText();
							if (readerLocation != null && readerLocation != "") {
								connector.addReader(readerLocation);
							}
						}
				}
					Thread thread = new Thread(connector);
					thread.start();
				}
				
			}
		});
		btnLaunch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnLaunch.setText("Launch");
		return btnLaunch;
	}

	private Combo createComboAntennaLocation() {
		Combo comboAntenna4Location = new Combo(shell, SWT.READ_ONLY);
		comboAntenna4Location.setItems(new String[] {"Between Warehouse and Loading", 
				"Between Warehouse and Backroom", 
				"Between Backroom and Store floor", 
				"Between Store floor and Store entrance"});
		comboAntenna4Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboAntenna4Location.setText("Location");
		return comboAntenna4Location;
	}

	private Combo createComboAntenna3Location() {
		Combo comboAntenna3Location = new Combo(shell, SWT.READ_ONLY);
		comboAntenna3Location.setItems(new String[] {"Between Warehouse and Loading", 
				"Between Warehouse and Backroom", 
				"Between Backroom and Store floor", 
				"Between Store floor and Store entrance"});
		comboAntenna3Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboAntenna3Location.setText("Location");
		return comboAntenna3Location;
	}

	private Combo createComboAntenna2Location() {
		Combo comboAntenna2Location = new Combo(shell, SWT.READ_ONLY);
		comboAntenna2Location.setItems(new String[] {"Between Warehouse and Loading", 
				"Between Warehouse and Backroom", 
				"Between Backroom and Store floor", 
				"Between Store floor and Store entrance"});
		comboAntenna2Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboAntenna2Location.setText("Location");
		return comboAntenna2Location;
	}

	private Combo createComboAntenna1Location() {
		Combo comboAntenna1Location = new Combo(shell, SWT.READ_ONLY);
		comboAntenna1Location.setItems(new String[] {"Between Warehouse and Loading", 
				"Between Warehouse and Backroom", 
				"Between Backroom and Store floor", 
				"Between Store floor and Store entrance"});
		comboAntenna1Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return comboAntenna1Location;
	}

	private Button createBtnAntenna1Enabled() {
		Button btnAntennaEnabled_1 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAntennaEnabled_1.setText("Antenna 1 Enabled");
		return btnAntennaEnabled_1;
	}

	private Text createReaderIPText() {
		Text ipText = new Text(shell, SWT.BORDER);
		ipText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		return ipText;
	}

	private Shell createShell() {
		shell = new Shell();
		shell.setSize(800, 640);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(4, false));
		return shell;
	}

}
