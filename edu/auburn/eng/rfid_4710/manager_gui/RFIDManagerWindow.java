package edu.auburn.eng.rfid_4710.manager_gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

import edu.auburn.eng.sks0024.rfid_connector.ReaderLocation;
import edu.auburn.eng.sks0024.rfid_connector.TagReader;

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
		shell = new Shell();
		shell.setSize(643, 446);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(4, false));
		
		Label lblReaderHostnameip = new Label(shell, SWT.NONE);
		lblReaderHostnameip.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblReaderHostnameip.setText("Reader Hostname/IP");
		
		ReaderIPText = new Text(shell, SWT.BORDER);
		ReaderIPText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button btnAntennaEnabled_1 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAntennaEnabled_1.setText("Antenna 1 Enabled");
		
		Label lblAntenna1Location = new Label(shell, SWT.NONE);
		lblAntenna1Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAntenna1Location.setText("Antenna Location");
		
		Combo comboAntenna1Location = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		comboAntenna1Location.setItems(new String[] {"Between Warehouse and Loading", "Between Warehouse and Backroom", "Between Backroom and Store floor", "Between Store floor and Store entrance"});
		comboAntenna1Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button chkboxAntenna1InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna1InputScanner.setText("Antenna is used to record new items");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button btnAntennaEnabled_2 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_2.setText("Antenna 2 Enabled");
		
		Label lblAntenna2Location = new Label(shell, SWT.NONE);
		lblAntenna2Location.setText("Antenna Location");
		lblAntenna2Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Combo comboAntenna2Location = new Combo(shell, SWT.READ_ONLY);
		comboAntenna2Location.setItems(new String[] {"Between Warehouse and Loading", "Between Warehouse and Backroom", "Between Backroom and Store floor", "Between Store floor and Store entrance"});
		comboAntenna2Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboAntenna2Location.setText("Location");
		
		Button chkboxAntenna2InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna2InputScanner.setText("Antenna is used to record new items");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button btnAntennaEnabled_3 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAntennaEnabled_3.setText("Antenna 3 Enabled");
		
		Label lblAntenna3Location = new Label(shell, SWT.NONE);
		lblAntenna3Location.setText("Antenna Location");
		lblAntenna3Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Combo comboAntenna3Location = new Combo(shell, SWT.READ_ONLY);
		comboAntenna3Location.setItems(new String[] {"Between Warehouse and Loading", "Between Warehouse and Backroom", "Between Backroom and Store floor", "Between Store floor and Store entrance"});
		comboAntenna3Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboAntenna3Location.setText("Location");
		
		Button chkboxAntenna3InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna3InputScanner.setText("Antenna is used to record new items");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button btnAntennaEnabled_4 = new Button(shell, SWT.CHECK);
		btnAntennaEnabled_4.setText("Antenna 4 Enabled");
		
		Label lblAntenna4Location = new Label(shell, SWT.NONE);
		lblAntenna4Location.setText("Antenna Location");
		lblAntenna4Location.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Combo comboAntenna4Location = new Combo(shell, SWT.READ_ONLY);
		comboAntenna4Location.setItems(new String[] {"Between Warehouse and Loading", "Between Warehouse and Backroom", "Between Backroom and Store floor", "Between Store floor and Store entrance"});
		comboAntenna4Location.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboAntenna4Location.setText("Location");
		
		Button chkboxAntenna4InputScanner = new Button(shell, SWT.CHECK);
		chkboxAntenna4InputScanner.setText("Antenna is used to record new items");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Button btnLaunch = new Button(shell, SWT.NONE);
		btnLaunch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				TagReader reader = new TagReader();
				reader.readerBootstrap("192.168.225.50", ReaderLocation.FLOOR_BACKROOM);
				reader.startReader();
			}
		});
		btnLaunch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnLaunch.setText("Launch");
		
		Button btnSaveConfiguration = new Button(shell, SWT.NONE);
		btnSaveConfiguration.setText("Save Configuration");
		
		Button btnLoadConfiguration = new Button(shell, SWT.NONE);
		btnLoadConfiguration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnLoadConfiguration.setText("Load Configuration");
		new Label(shell, SWT.NONE);

	}

}
