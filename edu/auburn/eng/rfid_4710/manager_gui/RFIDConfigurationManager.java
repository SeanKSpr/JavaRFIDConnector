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
import org.eclipse.swt.events.SelectionAdapter;
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
	private Text insertionLocation;
	
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		RFIDConfigurationManager window = new RFIDConfigurationManager();
		window.open();
		
	}

	/**
	 * Open the window.
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
	 * Create contents of the window.
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
	}

	private void createHostnameUI() {
		Label lblHostname = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblHostname.setBounds(20, 39, 137, 15);
		lblHostname.setText("Reader Hostname/IP");
		
		hostnameText = new Text(shlRfidConfigurationManager, SWT.BORDER);
		hostnameText.setToolTipText("");
		hostnameText.setBounds(163, 36, 137, 21);
		hostnameText.setMessage("Ex: 192.168.225.50");
	}

	private void createConsoleUI() {
		styledText = new StyledText(shlRfidConfigurationManager, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		styledText.setBounds(20, 237, 656, 188);
		
		Label lblConsole = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblConsole.setBounds(20, 216, 55, 15);
		lblConsole.setText("Console");
	}

	private void createAntennaFourUI() {
		ant4IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant4IsEnabled.setText("Enabled");
		ant4IsEnabled.setBounds(10, 186, 65, 16);
		
		Label lblAntenna4 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna4.setText("Antenna 4");
		lblAntenna4.setBounds(85, 187, 55, 15);
		
		ant4IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant4IsEntryPoint.setEnabled(ant4IsEnabled.getSelection());
		ant4IsEntryPoint.setText("Entry point?");
		ant4IsEntryPoint.setBounds(156, 186, 93, 16);
		
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
		
		ant4IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				toggleAntenna4Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
	}

	private void createAntennaThreeUI() {
		ant3IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant3IsEnabled.setText("Enabled");
		ant3IsEnabled.setBounds(10, 157, 65, 16);
		
		Label lblAntenna3 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna3.setText("Antenna 3");
		lblAntenna3.setBounds(85, 158, 55, 15);
		
		ant3IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant3IsEntryPoint.setEnabled(ant3IsEnabled.getSelection());
		ant3IsEntryPoint.setText("Entry point?");
		ant3IsEntryPoint.setBounds(156, 157, 93, 16);
		
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
		
		ant3IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				toggleAntenna3Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
	}

	private void createAntennaTwoUI() {
		ant2IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant2IsEnabled.setText("Enabled");
		ant2IsEnabled.setBounds(10, 128, 65, 16);
		
		Label lblAntenna2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna2.setText("Antenna 2");
		lblAntenna2.setBounds(85, 129, 55, 15);
		
		ant2IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant2IsEntryPoint.setEnabled(ant2IsEnabled.getSelection());
		ant2IsEntryPoint.setText("Entry point?");
		ant2IsEntryPoint.setBounds(156, 128, 93, 16);
		
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
		
		ant2IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				toggleAntenna2Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
	}

	private void createAntennaOneUI() {
		ant1IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant1IsEnabled.setBounds(10, 99, 65, 16);
		ant1IsEnabled.setText("Enabled");
		
		Label lblAntenna1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna1.setBounds(85, 100, 55, 15);
		lblAntenna1.setText("Antenna 1");
		
		ant1IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant1IsEntryPoint.setEnabled(ant1IsEnabled.getSelection());
		ant1IsEntryPoint.setBounds(156, 99, 93, 16);
		ant1IsEntryPoint.setText("Entry point?");
		
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
		
		ant1IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				toggleAntenna1Fields();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		
		insertionLocation = new Text(shlRfidConfigurationManager, SWT.BORDER);
		insertionLocation.setBounds(614, 99, 73, 21);
	}

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
	
	private void updateConsole(final String text) {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		        styledText.append(text);
		    }
		});
	}
	
	private ArrayList<Antenna> getAntennaListFromFields() {
		ArrayList<Antenna> antennaList = new ArrayList<Antenna>();
		Antenna antenna1 = new Antenna();
		antenna1.setStoreAreaOne(ant1StoreAreaOne.getText());
		antenna1.setStoreAreaTwo(ant1StoreAreaTwo.getText());
		antenna1.setEnabled(ant1IsEnabled.getSelection());
		antenna1.setEntryPoint(ant1IsEntryPoint.getSelection());
		antennaList.add(antenna1);
		
		Antenna antenna2 = new Antenna();
		antenna2.setStoreAreaOne(ant2StoreAreaOne.getText());
		antenna2.setStoreAreaTwo(ant2StoreAreaTwo.getText());
		antenna2.setEnabled(ant2IsEnabled.getSelection());
		antenna2.setEntryPoint(ant2IsEntryPoint.getSelection());
		antennaList.add(antenna2);
		
		Antenna antenna3 = new Antenna();
		antenna3.setStoreAreaOne(ant3StoreAreaOne.getText());
		antenna3.setStoreAreaTwo(ant3StoreAreaTwo.getText());
		antenna3.setEnabled(ant3IsEnabled.getSelection());
		antenna3.setEntryPoint(ant3IsEntryPoint.getSelection());
		antennaList.add(antenna3);
		
		Antenna antenna4 = new Antenna();
		antenna4.setStoreAreaOne(ant4StoreAreaOne.getText());
		antenna4.setStoreAreaTwo(ant4StoreAreaTwo.getText());
		antenna4.setEnabled(ant4IsEnabled.getSelection());
		antenna4.setEntryPoint(ant4IsEntryPoint.getSelection());
		antennaList.add(antenna4);	
		return antennaList;
	}

	private void loadAntennaOneProperties(Antenna antennaOne) {
		ant1StoreAreaOne.setText(antennaOne.getStoreAreaOne());
		ant1StoreAreaTwo.setText(antennaOne.getStoreAreaTwo());
		ant1IsEnabled.setSelection(antennaOne.isEnabled());
		ant1IsEntryPoint.setSelection(antennaOne.isEntryPoint());
		ant1StoreAreaOne.setEnabled(ant1IsEnabled.getSelection());
		ant1StoreAreaTwo.setEnabled(ant1IsEnabled.getSelection());
		ant1IsEntryPoint.setEnabled(ant1IsEnabled.getSelection());
		insertionLocation.setText(antennaOne.getInsertionLocation());
	}

	private void loadAntennaTwoProperties(Antenna antennaTwo) {
		ant2StoreAreaOne.setText(antennaTwo.getStoreAreaOne());
		ant2StoreAreaTwo.setText(antennaTwo.getStoreAreaTwo());
		ant2IsEnabled.setSelection(antennaTwo.isEnabled());
		ant2IsEntryPoint.setSelection(antennaTwo.isEntryPoint());
		ant2StoreAreaOne.setEnabled(ant2IsEnabled.getSelection());
		ant2StoreAreaTwo.setEnabled(ant2IsEnabled.getSelection());
		ant2IsEntryPoint.setEnabled(ant2IsEnabled.getSelection());
	}

	private void loadAntennaThreeProperties(Antenna antennaThree) {
		ant3StoreAreaOne.setText(antennaThree.getStoreAreaOne());
		ant3StoreAreaTwo.setText(antennaThree.getStoreAreaTwo());
		ant3IsEnabled.setSelection(antennaThree.isEnabled());
		ant3IsEntryPoint.setSelection(antennaThree.isEntryPoint());
		ant3StoreAreaOne.setEnabled(ant3IsEnabled.getSelection());
		ant3StoreAreaTwo.setEnabled(ant3IsEnabled.getSelection());
		ant3IsEntryPoint.setEnabled(ant3IsEnabled.getSelection());
	}

	private void loadAntennaFourProperties(Antenna antennaFour) {
		ant4StoreAreaOne.setText(antennaFour.getStoreAreaOne());
		ant4StoreAreaTwo.setText(antennaFour.getStoreAreaTwo());
		ant4IsEnabled.setSelection(antennaFour.isEnabled());
		ant4IsEntryPoint.setSelection(antennaFour.isEntryPoint());
		ant4StoreAreaOne.setEnabled(ant4IsEnabled.getSelection());
		ant4StoreAreaTwo.setEnabled(ant4IsEnabled.getSelection());
		ant4IsEntryPoint.setEnabled(ant4IsEnabled.getSelection());
	}

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
		// TODO Auto-generated method stub
		
	}

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
	
	private ServerInfo getServerInfoFromFields() {
		ServerInfo si = new ServerInfo();
		si.setOwner("*********");
		si.setPassword("*********");
		si.setUrl("http://aurfid.herokuapp.com/");
		return si;
	}

	private void toggleAntenna4Fields() {
		//^ is XOR
		ant4StoreAreaOne.setEnabled(ant4StoreAreaOne.getEnabled() ^ true);
		ant4StoreAreaTwo.setEnabled(ant4StoreAreaTwo.getEnabled() ^ true);
		ant4IsEntryPoint.setEnabled(ant4IsEntryPoint.getEnabled() ^ true);
	}

	private void toggleAntenna3Fields() {
		//^ is XOR
		ant3StoreAreaOne.setEnabled(ant3StoreAreaOne.getEnabled() ^ true);
		ant3StoreAreaTwo.setEnabled(ant3StoreAreaTwo.getEnabled() ^ true);
		ant3IsEntryPoint.setEnabled(ant3IsEntryPoint.getEnabled() ^ true);
	}

	private void toggleAntenna2Fields() {
		//^ is XOR
		ant2StoreAreaOne.setEnabled(ant2StoreAreaOne.getEnabled() ^ true);
		ant2StoreAreaTwo.setEnabled(ant2StoreAreaTwo.getEnabled() ^ true);
		ant2IsEntryPoint.setEnabled(ant2IsEntryPoint.getEnabled() ^ true);
	}

	private void toggleAntenna1Fields() {
		//^ is XOR
		ant1StoreAreaOne.setEnabled(ant1StoreAreaOne.getEnabled() ^ true);
		ant1StoreAreaTwo.setEnabled(ant1StoreAreaTwo.getEnabled() ^ true);
		ant1IsEntryPoint.setEnabled(ant1IsEntryPoint.getEnabled() ^ true);
	}
}
