package edu.auburn.eng.rfid_4710.manager_gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.auburn.eng.sks0024.rfid_connector.JavaRFIDConnector;


public class RFIDConfigurationManager {

	private static final String[] COMMON_LOCATIONS = new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"};
	protected Shell shlRfidConfigurationManager;
	private Text hostnameText;
	private StyledText styledText;
	private Button ant1IsEnabled, ant2IsEnabled, ant3IsEnabled, ant4IsEnabled;
	private Button ant1IsEntryPoint, ant2IsEntryPoint, ant3IsEntryPoint, ant4IsEntryPoint;
	private Combo ant1StoreAreaOne, ant2StoreAreaOne, ant3StoreAreaOne, ant4StoreAreaOne;
	private Combo ant1StoreAreaTwo, ant2StoreAreaTwo, ant3StoreAreaTwo, ant4StoreAreaTwo;
	private Button btnKillSelf, btnRunAway;
	private Button btnSaveStuff, btnLoadStuff;
	private Text ant1InsertionLocation;
	private Text dbOwner;
	private Text dbPassword;
	private Text dbURL;
	private Text ant2InsertionLocation;
	private Text ant3InsertionLocation;
	private Text ant4InsertionLocation;
	
	
	/**
	 * Runs the SWT RFID Configuration Manager
	 * @param args Command line arguments (not used)
	 */
	public static void main(String[] args) {
		RFIDConfigurationManager window = new RFIDConfigurationManager();
		window.open();
		
	}

	/**
	 * Redirects console output from standard out to a console in the GUI. Also creates all the GUI elements which
	 * constitute the configuration manager. Afterwards, while the GUI isn't closed it will continually refresh the page.
	 */
	public void open() {
		Display display = Display.getDefault();
		redirectSystemStreams();
		createContents();
		shlRfidConfigurationManager.open();
		shlRfidConfigurationManager.layout();
		while (!shlRfidConfigurationManager.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Creates all the GUI elements of the configuration manager
	 */
	protected void createContents() {
		setupConfigManagerShell();
		createHostnameUI();
		createAntennaOneUI();
		createQuitButton();
		createExecuteButton();
		createSaveButton();
		createLoadButton();
		createAntennaTwoUI();
		createAntennaThreeUI();
		createAntennaFourUI();	
		createConsoleUI();
		createDatabaseUI();
	}
	
	/**
	 * Creates the GUI elements related to the hostname text field.
	 */
	private void createHostnameUI() {
		Label lblHostname = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblHostname.setBounds(10, 10, 137, 15);
		lblHostname.setText("Reader Hostname/IP");
		
		hostnameText = new Text(shlRfidConfigurationManager, SWT.BORDER);
		hostnameText.setToolTipText("");
		hostnameText.setBounds(10, 31, 137, 21);
		hostnameText.setMessage("Ex: 192.168.225.50");
	}
	
	/**
	 * Creates the GUI elements related to the console view. This view displays everything which is sent to 
	 * standard out by the JavaRFIDConnector
	 */
	private void createConsoleUI() {
		styledText = new StyledText(shlRfidConfigurationManager, SWT.BORDER | SWT.WRAP);
		styledText.setBounds(20, 237, 656, 188);
		
		Label lblConsole = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblConsole.setBounds(20, 216, 55, 15);
		lblConsole.setText("Console");
		
		Label lblDatabaseInformation = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblDatabaseInformation.setBounds(177, 10, 137, 15);
		lblDatabaseInformation.setText("Database Information");	
	}

	private void createDatabaseUI() {
		dbOwner = new Text(shlRfidConfigurationManager, SWT.BORDER);
		dbOwner.setMessage("owner/username");
		dbOwner.setBounds(177, 31, 111, 21);
		
		dbPassword = new Text(shlRfidConfigurationManager, SWT.BORDER | SWT.PASSWORD);
		dbPassword.setMessage("password");
		dbPassword.setBounds(316, 31, 104, 21);
		
		dbURL = new Text(shlRfidConfigurationManager, SWT.BORDER);
		dbURL.setMessage("URL");
		dbURL.setBounds(449, 31, 161, 21);
	}
	
	/**
	 * Creates all the GUI elements related to setting up RFID antenna number 4.
	 */
	private void createAntennaFourUI() {
		ant4IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant4IsEnabled.setText("Enabled");
		ant4IsEnabled.setBounds(10, 186, 65, 16);
		ant4IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				toggleAntenna4Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		
		Label lblAntenna4 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna4.setText("Antenna 4");
		lblAntenna4.setBounds(85, 187, 55, 15);
		
		ant4IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant4IsEntryPoint.setEnabled(ant4IsEnabled.getSelection());
		ant4IsEntryPoint.setText("Entry point?");
		ant4IsEntryPoint.setBounds(156, 186, 93, 16);
		ant4IsEntryPoint.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toggleAntenna4InsertionField();			
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Label lblBetwixt_4 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_4.setText("Between");
		lblBetwixt_4.setBounds(255, 186, 55, 15);
		
		ant4StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant4StoreAreaOne.setEnabled(ant4IsEnabled.getSelection());
		ant4StoreAreaOne.setBounds(316, 184, 91, 23);
		ant4StoreAreaOne.setItems(COMMON_LOCATIONS);
		
		Label lblAnd4 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd4.setAlignment(SWT.CENTER);
		lblAnd4.setText("&&");
		lblAnd4.setBounds(423, 187, 55, 15);
		
		ant4StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant4StoreAreaTwo.setEnabled(ant4IsEnabled.getSelection());
		ant4StoreAreaTwo.setBounds(494, 184, 91, 23);
		ant4StoreAreaTwo.setItems(COMMON_LOCATIONS);
	
		ant4InsertionLocation = new Text(shlRfidConfigurationManager, SWT.BORDER);
		ant4InsertionLocation.setBounds(611, 186, 76, 21);
		ant4InsertionLocation.setEnabled(false);
	}
	
	/**
	 * Creates all the GUI elements related to setting up RFID antenna number 3.
	 */
	private void createAntennaThreeUI() {
		ant3IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant3IsEnabled.setText("Enabled");
		ant3IsEnabled.setBounds(10, 157, 65, 16);
		ant3IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				toggleAntenna3Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		
		Label lblAntenna3 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna3.setText("Antenna 3");
		lblAntenna3.setBounds(85, 158, 55, 15);
		
		ant3IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant3IsEntryPoint.setEnabled(ant3IsEnabled.getSelection());
		ant3IsEntryPoint.setText("Entry point?");
		ant3IsEntryPoint.setBounds(156, 157, 93, 16);
		ant3IsEntryPoint.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toggleAntenna3InsertionField();	
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Label lblBetwixt_3 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_3.setText("Between");
		lblBetwixt_3.setBounds(255, 157, 55, 15);
		
		ant3StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant3StoreAreaOne.setEnabled(ant3IsEnabled.getSelection());
		ant3StoreAreaOne.setBounds(316, 155, 91, 23);
		ant3StoreAreaOne.setItems(COMMON_LOCATIONS);
		
		Label lblAnd3 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd3.setAlignment(SWT.CENTER);
		lblAnd3.setText("&&");
		lblAnd3.setBounds(423, 158, 55, 15);
		
		ant3StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant3StoreAreaTwo.setEnabled(ant3IsEnabled.getSelection());
		ant3StoreAreaTwo.setBounds(494, 155, 91, 23);
		ant3StoreAreaTwo.setItems(COMMON_LOCATIONS);
		
		ant3InsertionLocation = new Text(shlRfidConfigurationManager, SWT.BORDER);
		ant3InsertionLocation.setBounds(611, 157, 76, 21);
		ant3InsertionLocation.setEnabled(false);
	}
	
	/**
	 * Creates all the GUI elements related to setting up RFID antenna number 2.
	 */
	private void createAntennaTwoUI() {
		ant2IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant2IsEnabled.setText("Enabled");
		ant2IsEnabled.setBounds(10, 128, 65, 16);
		ant2IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				toggleAntenna2Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		
		Label lblAntenna2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna2.setText("Antenna 2");
		lblAntenna2.setBounds(85, 129, 55, 15);
		
		ant2IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant2IsEntryPoint.setEnabled(ant2IsEnabled.getSelection());
		ant2IsEntryPoint.setText("Entry point?");
		ant2IsEntryPoint.setBounds(156, 128, 93, 16);
		ant2IsEntryPoint.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toggleAntenna2InsertionField();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Label lblBetwixt_2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_2.setText("Between");
		lblBetwixt_2.setBounds(255, 128, 55, 15);
		
		ant2StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant2StoreAreaOne.setEnabled(ant2IsEnabled.getSelection());
		ant2StoreAreaOne.setBounds(316, 126, 91, 23);
		ant2StoreAreaOne.setItems(COMMON_LOCATIONS);
		
		Label lblAnd2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd2.setAlignment(SWT.CENTER);
		lblAnd2.setText("&&");
		lblAnd2.setBounds(423, 129, 55, 15);
		
		ant2StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant2StoreAreaTwo.setEnabled(ant2IsEnabled.getSelection());
		ant2StoreAreaTwo.setBounds(494, 126, 91, 23);
		ant2StoreAreaTwo.setItems(COMMON_LOCATIONS);
		
		ant2InsertionLocation = new Text(shlRfidConfigurationManager, SWT.BORDER);
		ant2InsertionLocation.setBounds(611, 128, 76, 21);
		ant2InsertionLocation.setEnabled(false);
	}
	
	/**
	 * Creates all the GUI elements related to setting up RFID antenna number 1.
	 */
	private void createAntennaOneUI() {
		ant1IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant1IsEnabled.setBounds(10, 99, 65, 16);
		ant1IsEnabled.setText("Enabled");
		ant1IsEnabled.addSelectionListener(new SelectionListener() {	
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toggleAntenna1Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Label lblAntenna1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna1.setBounds(85, 100, 55, 15);
		lblAntenna1.setText("Antenna 1");
		
		ant1IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant1IsEntryPoint.setEnabled(ant1IsEnabled.getSelection());
		ant1IsEntryPoint.setBounds(156, 99, 93, 16);
		ant1IsEntryPoint.setText("Entry point?");
		ant1IsEntryPoint.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				toggleAntenna1InsertionField();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub	
			}
		});
		
		Label lblBetwixt_1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_1.setBounds(255, 99, 55, 15);
		lblBetwixt_1.setText("Between");
		
		ant1StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant1StoreAreaOne.setEnabled(ant1IsEnabled.getSelection());
		ant1StoreAreaOne.setBounds(316, 97, 91, 23);
		ant1StoreAreaOne.setItems(COMMON_LOCATIONS);
		
		Label lblAnd1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd1.setAlignment(SWT.CENTER);
		lblAnd1.setBounds(423, 100, 55, 15);
		lblAnd1.setText("&&");
		
		ant1StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant1StoreAreaTwo.setEnabled(ant1IsEnabled.getSelection());
		ant1StoreAreaTwo.setBounds(494, 97, 91, 23);
		ant1StoreAreaTwo.setItems(COMMON_LOCATIONS);
			
		ant1InsertionLocation = new Text(shlRfidConfigurationManager, SWT.BORDER);
		ant1InsertionLocation.setBounds(611, 97, 75, 21);
		ant1InsertionLocation.setEnabled(false);
	}

	/**
	 * Creates the load button and sets its  mouseDown controller to load an RFID configuration file
	 * to populate the GUIs and setup the antenna information.
	 */
	private void createLoadButton() {
		btnLoadStuff = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnLoadStuff.setToolTipText("Load existing configuration");
		btnLoadStuff.setBounds(20, 492, 120, 25);
		btnLoadStuff.setText("Load Configuration");
		btnLoadStuff.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				populateManagerFromConfigFile();
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
	}
	
	/**
	 * Creates the save button and sets its mouseDown controller to save the antenna
	 * GUI fields as a configuration file
	 */
	private void createSaveButton() {
		btnSaveStuff = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnSaveStuff.setToolTipText("Save the entered configuration for later use");
		btnSaveStuff.setBounds(20, 448, 120, 25);
		btnSaveStuff.setText("Save Configuration");
		btnSaveStuff.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				ArrayList<Antenna> antennaList = (ArrayList<Antenna>) getAntennaListFromFields();				
				JSONConfigurationFile js = new JSONConfigurationFile();
				ServerInfo si = getServerInfoFromFields();
				js.saveConfiguration(hostnameText.getText(), antennaList, si); 
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
	}
	
	/**
	 * Creates the execute button and sets its mouseDown controller to begin executing the JavaRFIDConnector
	 */
	private void createExecuteButton() {
		btnRunAway = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnRunAway.setToolTipText("Will run Java Connector with current settings");
		btnRunAway.setBounds(624, 492, 75, 25);
		btnRunAway.setText("Execute");
		btnRunAway.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent arg0) {
				launchJavaRFIDConnector();
			}
			public void mouseDoubleClick(MouseEvent arg0) {	}
			public void mouseUp(MouseEvent arg0) { }
		});
	}
	
	/**
	 * Creates the quit button and sets its mouseDown controller to exit the program
	 */
	private void createQuitButton() {
		btnKillSelf = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnKillSelf.setToolTipText("Closes the application");
		btnKillSelf.setBounds(494, 492, 75, 25);
		btnKillSelf.setText("Quit");
		btnKillSelf.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				System.exit(0);
			}
			public void mouseDoubleClick(MouseEvent arg0) {	}
			public void mouseUp(MouseEvent arg0) { }
		});
	}
	
	/**
	 * Creates the SWT shell that the configuration manager runs in and sets a mouseDown controller so that
	 * whenever the user clicks the shell, it forces focus on the shell.
	 */
	private void setupConfigManagerShell() {
		shlRfidConfigurationManager = new Shell();
		shlRfidConfigurationManager.setImage(SWTResourceManager.getImage("C:\\Users\\Berter\\Documents\\219442-p-4x.jpg"));
		shlRfidConfigurationManager.setToolTipText("You are reading the tooltip text");
		shlRfidConfigurationManager.setSize(741, 599);
		shlRfidConfigurationManager.setText("RFID Configuration Manager");
		shlRfidConfigurationManager.setLayout(null);
		shlRfidConfigurationManager.setFocus();
		shlRfidConfigurationManager.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				shlRfidConfigurationManager.forceFocus();
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
	}
	
	/**
	 * Redirects the standard output stream so that instead of displaying to the Java console, it
	 * will display the information in the GUI console for the user to see.
	 */
	private void redirectSystemStreams() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(final int b) throws IOException {
		      updateConsole(String.valueOf((char) b));
		    }

			@Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateConsole(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
		}
	
	/**
	 * Updates the GUI console with the latest information which is being sent to the standard output stream.
	 * @param text The text to be added to the GUI console.
	 */
	private void updateConsole(final String text) {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		        styledText.append(text);
		    }
		});
	}
	
	/**
	 * Extracts all the information from the Antenna fields in order to generate a list of
	 * Antenna objects.
	 * @return an ArrayList of Antenna objects which are created from the Antenna GUI contents.
	 */
	private ArrayList<Antenna> getAntennaListFromFields() {
		ArrayList<Antenna> antennaList = new ArrayList<Antenna>();
		Antenna antenna1 = new Antenna();
		antenna1.setStoreAreaOne(ant1StoreAreaOne.getText());
		antenna1.setStoreAreaTwo(ant1StoreAreaTwo.getText());
		antenna1.setEnabled(ant1IsEnabled.getSelection());
		antenna1.setEntryPoint(ant1IsEntryPoint.getSelection());
		antenna1.setAntennaID(1);
		antenna1.setInsertionLocation(ant1InsertionLocation.getText());
		antennaList.add(antenna1);
		
		Antenna antenna2 = new Antenna();
		antenna2.setStoreAreaOne(ant2StoreAreaOne.getText());
		antenna2.setStoreAreaTwo(ant2StoreAreaTwo.getText());
		antenna2.setEnabled(ant2IsEnabled.getSelection());
		antenna2.setEntryPoint(ant2IsEntryPoint.getSelection());
		antenna2.setAntennaID(2);
		antennaList.add(antenna2);
		
		Antenna antenna3 = new Antenna();
		antenna3.setStoreAreaOne(ant3StoreAreaOne.getText());
		antenna3.setStoreAreaTwo(ant3StoreAreaTwo.getText());
		antenna3.setEnabled(ant3IsEnabled.getSelection());
		antenna3.setEntryPoint(ant3IsEntryPoint.getSelection());
		antenna3.setAntennaID(3);
		antennaList.add(antenna3);
		
		Antenna antenna4 = new Antenna();
		antenna4.setStoreAreaOne(ant4StoreAreaOne.getText());
		antenna4.setStoreAreaTwo(ant4StoreAreaTwo.getText());
		antenna4.setEnabled(ant4IsEnabled.getSelection());
		antenna4.setEntryPoint(ant4IsEntryPoint.getSelection());
		antenna4.setAntennaID(4);
		antennaList.add(antenna4);	
		return antennaList;
	}
	
	/**
	 * Loads an Antenna object's fields to its related GUI fields in the configuration manager
	 * @param antennaOne an Antenna which relates to the Antenna 1 GUI fields
	 */
	private void loadAntennaOneProperties(Antenna antennaOne) {
		ant1StoreAreaOne.setText(antennaOne.getStoreAreaOne());
		ant1StoreAreaTwo.setText(antennaOne.getStoreAreaTwo());
		ant1IsEnabled.setSelection(antennaOne.isEnabled());
		ant1IsEntryPoint.setSelection(antennaOne.isEntryPoint());
		ant1StoreAreaOne.setEnabled(ant1IsEnabled.getSelection());
		ant1StoreAreaTwo.setEnabled(ant1IsEnabled.getSelection());
		ant1IsEntryPoint.setEnabled(ant1IsEnabled.getSelection());
		ant1InsertionLocation.setText(antennaOne.getInsertionLocation());
	}
	
	/**
	 * Loads an Antenna object's fields to its related GUI fields in the configuration manager
	 * @param antennaOne an Antenna which relates to the Antenna 2 GUI fields
	 */
	private void loadAntennaTwoProperties(Antenna antennaTwo) {
		ant2StoreAreaOne.setText(antennaTwo.getStoreAreaOne());
		ant2StoreAreaTwo.setText(antennaTwo.getStoreAreaTwo());
		ant2IsEnabled.setSelection(antennaTwo.isEnabled());
		ant2IsEntryPoint.setSelection(antennaTwo.isEntryPoint());
		ant2StoreAreaOne.setEnabled(ant2IsEnabled.getSelection());
		ant2StoreAreaTwo.setEnabled(ant2IsEnabled.getSelection());
		ant2IsEntryPoint.setEnabled(ant2IsEnabled.getSelection());
	}
	
	/**
	 * Loads an Antenna object's fields to its related GUI fields in the configuration manager
	 * @param antennaOne an Antenna which relates to the Antenna 3 GUI fields
	 */
	private void loadAntennaThreeProperties(Antenna antennaThree) {
		ant3StoreAreaOne.setText(antennaThree.getStoreAreaOne());
		ant3StoreAreaTwo.setText(antennaThree.getStoreAreaTwo());
		ant3IsEnabled.setSelection(antennaThree.isEnabled());
		ant3IsEntryPoint.setSelection(antennaThree.isEntryPoint());
		ant3StoreAreaOne.setEnabled(ant3IsEnabled.getSelection());
		ant3StoreAreaTwo.setEnabled(ant3IsEnabled.getSelection());
		ant3IsEntryPoint.setEnabled(ant3IsEnabled.getSelection());
	}
	
	/**
	 * Loads an Antenna object's fields to its related GUI fields in the configuration manager
	 * @param antennaOne an Antenna which relates to the Antenna 4 GUI fields
	 */
	private void loadAntennaFourProperties(Antenna antennaFour) {
		ant4StoreAreaOne.setText(antennaFour.getStoreAreaOne());
		ant4StoreAreaTwo.setText(antennaFour.getStoreAreaTwo());
		ant4IsEnabled.setSelection(antennaFour.isEnabled());
		ant4IsEntryPoint.setSelection(antennaFour.isEntryPoint());
		ant4StoreAreaOne.setEnabled(ant4IsEnabled.getSelection());
		ant4StoreAreaTwo.setEnabled(ant4IsEnabled.getSelection());
		ant4IsEntryPoint.setEnabled(ant4IsEnabled.getSelection());
	}
	
	/**
	 * Loads a configuration file and uses it to populate the Antenna and Server GUI fields
	 */
	private void populateManagerFromConfigFile() {
		JSONConfigurationFile js = new JSONConfigurationFile();
		try {js.loadConfiguration();} catch (LoadCancelledException ex) { System.out.println(ex); return; }
		hostnameText.setText(js.getHostname());
		ServerInfo serverInfo = js.getServerInfo();
		ArrayList<Antenna> antennaList = js.getAntennaList();
		loadAntennaProperties(antennaList);
		loadServerInfoProperties(serverInfo);
	}
	
	private void loadServerInfoProperties(ServerInfo serverInfo) {
		String owner = serverInfo.getOwner();
		String password = serverInfo.getPassword();
		String url = serverInfo.getUrl();
		dbOwner.setText(owner);
		dbPassword.setText(password);
		dbURL.setText(url);
		
	}
	
	/**
	 * Takes a list of Antenna objects and loads each of them into their related GUI fields.
	 * @param antennaList an ArrayList of Antenna objects which are to be loaded to the Antenna fields
	 */
	private void loadAntennaProperties(ArrayList<Antenna> antennaList) {
		Antenna antennaOne = antennaList.get(0);
		loadAntennaOneProperties(antennaOne);
		
		Antenna antennaTwo = antennaList.get(1);
		loadAntennaTwoProperties(antennaTwo);
		
		Antenna antennaThree = antennaList.get(2);
		loadAntennaThreeProperties(antennaThree);
		
		Antenna antennaFour = antennaList.get(3);
		loadAntennaFourProperties(antennaFour);
	}
	
	/**
	 * Pulls information from the hostname, antenna, and server information GUI fields 
	 * in order to set up the JavaRFIDConnector and launch it.
	 */
	private void launchJavaRFIDConnector() {
		JavaRFIDConnector jrc = new JavaRFIDConnector();
		List<Antenna> antennaList = getAntennaListFromFields();			
		jrc.setHostname("192.168.225.50");
		for (Antenna antenna : antennaList) {
			if (antenna.isEnabled()) {
				if (antenna.isEntryPoint()) {
					jrc.addEntryPointReader(antenna.getStoreAreaOne(), antenna.getStoreAreaTwo(), antenna.getAntennaID(), antenna.getInsertionLocation());
				}
				else {
					jrc.addReader(antenna.getStoreAreaOne(), antenna.getStoreAreaTwo(), antenna.getAntennaID());
				}
			}
		}
		jrc.addReader("Store_Floor", "Warehouse", 1);
		jrc.addReader("Warehouse", "Out_of_store", 4);
		Thread thread = new Thread(jrc);
		thread.start();
	}
	
	/**
	 * Pulls the fields from the server information GUI fields in order to create a ServerInfo object
	 * which is then returned.
	 * @return a ServerInfo object containing all the server information data stored in the configuration
	 * manager.
	 */
	private ServerInfo getServerInfoFromFields() {
		ServerInfo si = new ServerInfo();
		String owner = dbOwner.getText();
		String password = dbPassword.getText();
		String url = dbURL.getText();
		si.setOwner(owner);
		si.setPassword(password);
		//"http://aurfid.herokuapp.com/"
		si.setUrl(url);
		return si;
	}
	
	/**
	 * Toggles the GUI elements related to Antenna 4 from enabled to disabled an disabled to enabled.
	 */
	private void toggleAntenna4Fields() {
		//^ is XOR
		ant4StoreAreaOne.setEnabled(ant4StoreAreaOne.getEnabled() ^ true);
		ant4StoreAreaTwo.setEnabled(ant4StoreAreaTwo.getEnabled() ^ true);
		ant4IsEntryPoint.setEnabled(ant4IsEntryPoint.getEnabled() ^ true);
	}
	
	/**
	 * Toggles the GUI elements related to Antenna 3 from enabled to disabled an disabled to enabled.
	 */
	private void toggleAntenna3Fields() {
		//^ is XOR
		ant3StoreAreaOne.setEnabled(ant3StoreAreaOne.getEnabled() ^ true);
		ant3StoreAreaTwo.setEnabled(ant3StoreAreaTwo.getEnabled() ^ true);
		ant3IsEntryPoint.setEnabled(ant3IsEntryPoint.getEnabled() ^ true);
	}
	
	/**
	 * Toggles the GUI elements related to Antenna 2 from enabled to disabled an disabled to enabled.
	 */
	private void toggleAntenna2Fields() {
		//^ is XOR
		ant2StoreAreaOne.setEnabled(ant2StoreAreaOne.getEnabled() ^ true);
		ant2StoreAreaTwo.setEnabled(ant2StoreAreaTwo.getEnabled() ^ true);
		ant2IsEntryPoint.setEnabled(ant2IsEntryPoint.getEnabled() ^ true);
	}
	
	/**
	 * Toggles the GUI elements related to Antenna 1 from enabled to disabled an disabled to enabled.
	 */
	private void toggleAntenna1Fields() {
		//^ is XOR
		ant1StoreAreaOne.setEnabled(ant1StoreAreaOne.getEnabled() ^ true);
		ant1StoreAreaTwo.setEnabled(ant1StoreAreaTwo.getEnabled() ^ true);
		ant1IsEntryPoint.setEnabled(ant1IsEntryPoint.getEnabled() ^ true);
	}
	
	private void toggleAntenna1InsertionField() {
		ant1InsertionLocation.setEnabled(ant1InsertionLocation.getEnabled() ^ true);
	}
	
	private void toggleAntenna2InsertionField() {
		ant2InsertionLocation.setEnabled(ant2InsertionLocation.getEnabled() ^ true);
	}
	
	private void toggleAntenna3InsertionField() {
		ant3InsertionLocation.setEnabled(ant3InsertionLocation.getEnabled() ^ true);
	}
	
	private void toggleAntenna4InsertionField() {
		ant4InsertionLocation.setEnabled(ant4InsertionLocation.getEnabled() ^ true);
	}
}
