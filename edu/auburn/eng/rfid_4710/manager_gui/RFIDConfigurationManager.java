package edu.auburn.eng.rfid_4710.manager_gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
//import org.eclipse.swt.layout.RowData;
//import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
//import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import edu.auburn.eng.sks0024.rfid_connector.JavaRFIDConnector;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.ScrolledComposite;
//import org.eclipse.core.databinding.DataBindingContext;
//import org.eclipse.core.databinding.observable.value.IObservableValue;
//import org.eclipse.jface.databinding.swt.WidgetProperties;
//import org.eclipse.core.databinding.beans.PojoProperties;
//import org.eclipse.core.databinding.observable.Realm;
//import org.eclipse.jface.databinding.swt.SWTObservables;
//import org.eclipse.swt.custom.StyledText;


public class RFIDConfigurationManager {
//	private DataBindingContext m_bindingContext;

	protected Shell shlRfidConfigurationManager;
	private Text text;
	private StyledText styledText;
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
		
		Label lblNewLabel = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblNewLabel.setBounds(20, 39, 137, 15);
		lblNewLabel.setText("Reader Hostname/IP");
		
		text = new Text(shlRfidConfigurationManager, SWT.BORDER);
		text.setToolTipText("");
		text.setBounds(163, 36, 137, 21);
		text.setMessage("Ex: 192.168.225.50");
		
		final Button ant1IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant1IsEnabled.setBounds(10, 99, 65, 16);
		ant1IsEnabled.setText("Enabled");
		
		Label lblAntenna1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna1.setBounds(85, 100, 55, 15);
		lblAntenna1.setText("Antenna 1");
		
		final Button ant1IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant1IsEntryPoint.setEnabled(ant1IsEnabled.getSelection());
		ant1IsEntryPoint.setBounds(156, 99, 93, 16);
		ant1IsEntryPoint.setText("Entry point?");
		
		Label lblBetwixt_1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_1.setBounds(255, 99, 55, 15);
		lblBetwixt_1.setText("Between");
		
		final Combo ant1StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant1StoreAreaOne.setEnabled(ant1IsEnabled.getSelection());
		ant1StoreAreaOne.setBounds(316, 97, 91, 23);
		ant1StoreAreaOne.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label lblAndpersand = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAndpersand.setAlignment(SWT.CENTER);
		lblAndpersand.setBounds(423, 100, 55, 15);
		lblAndpersand.setText("&&");
		
		final Combo ant1StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant1StoreAreaTwo.setEnabled(ant1IsEnabled.getSelection());
		ant1StoreAreaTwo.setBounds(494, 97, 91, 23);
		ant1StoreAreaTwo.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Button btnKillSelf = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnKillSelf.setToolTipText("Closes the application");
		btnKillSelf.setBounds(494, 492, 75, 25);
		btnKillSelf.setText("Quit");
		
		Button btnRunAway = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnRunAway.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnRunAway.setToolTipText("Will run Java Connector with current settings");
		btnRunAway.setBounds(624, 492, 75, 25);
		btnRunAway.setText("Execute");
		
		Button btnSaveStuff = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnSaveStuff.setToolTipText("Save the entered configuration for later use");
		btnSaveStuff.setBounds(20, 448, 120, 25);
		btnSaveStuff.setText("Save Configuration");
		
		Button btnLoadStuff = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnLoadStuff.setToolTipText("Load existing configuration");
		btnLoadStuff.setBounds(20, 492, 120, 25);
		btnLoadStuff.setText("Load Configuration");
		
		final Button ant2IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant2IsEnabled.setText("Enabled");
		ant2IsEnabled.setBounds(10, 128, 65, 16);
		
		Label lblAntenna2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna2.setText("Antenna 2");
		lblAntenna2.setBounds(85, 129, 55, 15);
		
		final Button ant2IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant2IsEntryPoint.setEnabled(ant2IsEnabled.getSelection());
		ant2IsEntryPoint.setText("Entry point?");
		ant2IsEntryPoint.setBounds(156, 128, 93, 16);
		
		Label lblBetwixt_2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_2.setText("Between");
		lblBetwixt_2.setBounds(255, 128, 55, 15);
		
		final Combo ant2StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant2StoreAreaOne.setEnabled(ant2IsEnabled.getSelection());
		ant2StoreAreaOne.setBounds(316, 126, 91, 23);
		ant2StoreAreaOne.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label lblAnd = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd.setAlignment(SWT.CENTER);
		lblAnd.setText("&&");
		lblAnd.setBounds(423, 129, 55, 15);
		
		final Combo ant2StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant2StoreAreaTwo.setEnabled(ant2IsEnabled.getSelection());
		ant2StoreAreaTwo.setBounds(494, 126, 91, 23);
		ant2StoreAreaTwo.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		final Button ant3IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant3IsEnabled.setText("Enabled");
		ant3IsEnabled.setBounds(10, 157, 65, 16);
		
		Label lblAntenna3 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna3.setText("Antenna 3");
		lblAntenna3.setBounds(85, 158, 55, 15);
		
		final Button ant3IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant3IsEntryPoint.setEnabled(ant3IsEnabled.getSelection());
		ant3IsEntryPoint.setText("Entry point?");
		ant3IsEntryPoint.setBounds(156, 157, 93, 16);
		
		Label lblBetwixt_3 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_3.setText("Between");
		lblBetwixt_3.setBounds(255, 157, 55, 15);
		
		final Combo ant3StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant3StoreAreaOne.setEnabled(ant3IsEnabled.getSelection());
		ant3StoreAreaOne.setBounds(316, 155, 91, 23);
		ant3StoreAreaOne.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label label_5 = new Label(shlRfidConfigurationManager, SWT.NONE);
		label_5.setAlignment(SWT.CENTER);
		label_5.setText("&&");
		label_5.setBounds(423, 158, 55, 15);
		
		final Combo ant3StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant3StoreAreaTwo.setEnabled(ant3IsEnabled.getSelection());
		ant3StoreAreaTwo.setBounds(494, 155, 91, 23);
		ant3StoreAreaTwo.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		final Button ant4IsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant4IsEnabled.setText("Enabled");
		ant4IsEnabled.setBounds(10, 186, 65, 16);
		
		Label lblAntenna4 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna4.setText("Antenna 4");
		lblAntenna4.setBounds(85, 187, 55, 15);
		
		final Button ant4IsEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		ant4IsEntryPoint.setEnabled(ant4IsEnabled.getSelection());
		ant4IsEntryPoint.setText("Entry point?");
		ant4IsEntryPoint.setBounds(156, 186, 93, 16);
		
		Label lblBetwixt_4 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt_4.setText("Between");
		lblBetwixt_4.setBounds(255, 186, 55, 15);
		
		final Combo ant4StoreAreaOne = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant4StoreAreaOne.setEnabled(ant4IsEnabled.getSelection());
		ant4StoreAreaOne.setBounds(316, 184, 91, 23);
		ant4StoreAreaOne.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label lblAnd_1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd_1.setAlignment(SWT.CENTER);
		lblAnd_1.setText("&&");
		lblAnd_1.setBounds(423, 187, 55, 15);
		
		final Combo ant4StoreAreaTwo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		ant4StoreAreaTwo.setEnabled(ant4IsEnabled.getSelection());
		ant4StoreAreaTwo.setBounds(494, 184, 91, 23);
		ant4StoreAreaTwo.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		styledText = new StyledText(shlRfidConfigurationManager, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		styledText.setBounds(20, 237, 656, 188);
		PrintStream printStream = new PrintStream(new GUIOutputStreamer(styledText));
		System.setOut(printStream);
		System.setErr(printStream);
		
		Label lblConsole = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblConsole.setBounds(20, 216, 55, 15);
		lblConsole.setText("Console");

//		m_bindingContext = initDataBindings();

		
		ant1IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				ant1StoreAreaOne.setEnabled(ant1StoreAreaOne.getEnabled() ^ true);
				ant1StoreAreaTwo.setEnabled(ant1StoreAreaTwo.getEnabled() ^ true);
				ant1IsEntryPoint.setEnabled(ant1IsEntryPoint.getEnabled() ^ true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		ant2IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				ant2StoreAreaOne.setEnabled(ant2StoreAreaOne.getEnabled() ^ true);
				ant2StoreAreaTwo.setEnabled(ant2StoreAreaTwo.getEnabled() ^ true);
				ant2IsEntryPoint.setEnabled(ant2IsEntryPoint.getEnabled() ^ true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		ant3IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				ant3StoreAreaOne.setEnabled(ant3StoreAreaOne.getEnabled() ^ true);
				ant3StoreAreaTwo.setEnabled(ant3StoreAreaTwo.getEnabled() ^ true);
				ant3IsEntryPoint.setEnabled(ant3IsEntryPoint.getEnabled() ^ true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		ant4IsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				ant4StoreAreaOne.setEnabled(ant4StoreAreaOne.getEnabled() ^ true);
				ant4StoreAreaTwo.setEnabled(ant4StoreAreaTwo.getEnabled() ^ true);
				ant4IsEntryPoint.setEnabled(ant4IsEntryPoint.getEnabled() ^ true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		
		btnKillSelf.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				System.exit(0);
			}
			public void mouseDoubleClick(MouseEvent arg0) {	}
			public void mouseUp(MouseEvent arg0) { }
		});

		btnSaveStuff.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				ArrayList<Antenna> antennaList = new ArrayList<Antenna>();
				Antenna antenna1 = new Antenna();
				antenna1.setStoreAreaOne(ant1StoreAreaOne.getText());
				antenna1.setStoreAreaTwo(ant1StoreAreaTwo.getText());
				antenna1.setEnabled(ant1IsEnabled.getSelection());
				antenna1.setEntryPoint(ant1IsEntryPoint.getSelection());
				//System.out.println("Antenna 1: " + antenna1.toString());
				antennaList.add(antenna1);
				Antenna antenna2 = new Antenna();
				antenna2.setStoreAreaOne(ant2StoreAreaOne.getText());
				antenna2.setStoreAreaTwo(ant2StoreAreaTwo.getText());
				antenna2.setEnabled(ant2IsEnabled.getSelection());
				antenna2.setEntryPoint(ant2IsEntryPoint.getSelection());
				//System.out.println("Antenna 2: " + antenna2.toString());
				antennaList.add(antenna2);
				Antenna antenna3 = new Antenna();
				antenna3.setStoreAreaOne(ant3StoreAreaOne.getText());
				antenna3.setStoreAreaTwo(ant3StoreAreaTwo.getText());
				antenna3.setEnabled(ant3IsEnabled.getSelection());
				antenna3.setEntryPoint(ant3IsEntryPoint.getSelection());
				//System.out.println("Antenna 3: " + antenna3.toString());
				antennaList.add(antenna3);
				Antenna antenna4 = new Antenna();
				antenna4.setStoreAreaOne(ant4StoreAreaOne.getText());
				antenna4.setStoreAreaTwo(ant4StoreAreaTwo.getText());
				antenna4.setEnabled(ant4IsEnabled.getSelection());
				antenna4.setEntryPoint(ant4IsEntryPoint.getSelection());
				//System.out.println("Antenna 4: " + antenna4.toString());
				antennaList.add(antenna4);				
				
				JSONConfigurationFile js = new JSONConfigurationFile();
				ArrayList<String> serverInfo = new ArrayList<String>();
				ServerInfo si = new ServerInfo();
				si.setOwner("*********");
				si.setPassword("*********");
				si.setUrl("http://aurfid.herokuapp.com/");
				js.saveConfiguration(text.getText(), antennaList, si); //catch (Exception ex) { System.out.println(ex); return;}
				/*System.out.println(text.getText() + "\n\n" + antennaList.size() + "\n\n" + antennaList.get(0).toString() + "\n\n"
						+ antennaList.get(1).toString() + "\n\n" + antennaList.get(2).toString() + "\n\n"
						+ antennaList.get(3).toString() + "\n\n" + serverInfo.toString());*/
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
		
		btnLoadStuff.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				JSONConfigurationFile js = new JSONConfigurationFile();
				try {js.loadConfiguration();} catch (LoadCancelledException ex) { System.out.println(ex); return; }
				text.setText(js.getHostname());
				//How are we using the server info from the config file?
				ServerInfo serverInfo = js.getServerInfo();
				ArrayList<Antenna> antennaList = js.getAntennaList();
				ant1StoreAreaOne.setText(antennaList.get(0).getStoreAreaOne());
				ant1StoreAreaTwo.setText(antennaList.get(0).getStoreAreaTwo());
				ant1IsEnabled.setSelection(antennaList.get(0).isEnabled());
				ant1IsEntryPoint.setSelection(antennaList.get(0).isEntryPoint());
				ant2StoreAreaOne.setText(antennaList.get(1).getStoreAreaOne());
				ant2StoreAreaTwo.setText(antennaList.get(1).getStoreAreaTwo());
				ant2IsEnabled.setSelection(antennaList.get(1).isEnabled());
				ant2IsEntryPoint.setSelection(antennaList.get(1).isEntryPoint());
				ant3StoreAreaOne.setText(antennaList.get(2).getStoreAreaOne());
				ant3StoreAreaTwo.setText(antennaList.get(2).getStoreAreaTwo());
				ant3IsEnabled.setSelection(antennaList.get(2).isEnabled());
				ant3IsEntryPoint.setSelection(antennaList.get(2).isEntryPoint());
				ant4StoreAreaOne.setText(antennaList.get(3).getStoreAreaOne());
				ant4StoreAreaTwo.setText(antennaList.get(3).getStoreAreaTwo());
				ant4IsEnabled.setSelection(antennaList.get(3).isEnabled());
				ant4IsEntryPoint.setSelection(antennaList.get(3).isEntryPoint());

				ant1StoreAreaOne.setEnabled(ant1IsEnabled.getSelection());
				ant1StoreAreaTwo.setEnabled(ant1IsEnabled.getSelection());
				ant1IsEntryPoint.setEnabled(ant1IsEnabled.getSelection());
				ant2StoreAreaOne.setEnabled(ant2IsEnabled.getSelection());
				ant2StoreAreaTwo.setEnabled(ant2IsEnabled.getSelection());
				ant2IsEntryPoint.setEnabled(ant2IsEnabled.getSelection());
				ant3StoreAreaOne.setEnabled(ant3IsEnabled.getSelection());
				ant3StoreAreaTwo.setEnabled(ant3IsEnabled.getSelection());
				ant3IsEntryPoint.setEnabled(ant3IsEnabled.getSelection());
				ant4StoreAreaOne.setEnabled(ant4IsEnabled.getSelection());
				ant4StoreAreaTwo.setEnabled(ant4IsEnabled.getSelection());
				ant4IsEntryPoint.setEnabled(ant4IsEnabled.getSelection());
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
		
		//LAUNCH PROGRAM
		btnRunAway.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent arg0) {
				JavaRFIDConnector jrc = new JavaRFIDConnector();
				//AuburnReader ar = new AuburnReader();
				Antenna antenna = new Antenna();
				
				jrc.setHostname("192.168.225.50");
				jrc.addReader("Store_Floor", "Warehouse", 1);
				jrc.addReader("Warehouse", "Out_of_store", 4);
				Thread thread = new Thread(jrc);
				thread.start();
			}
			public void mouseDoubleClick(MouseEvent arg0) {	}
			public void mouseUp(MouseEvent arg0) { }
			
		});
//		m_bindingContext = initDataBindings();


	}
}
