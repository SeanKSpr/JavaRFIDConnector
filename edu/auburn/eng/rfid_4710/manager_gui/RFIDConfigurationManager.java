package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
//import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
//import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Text;
//import org.eclipse.swt.layout.RowData;
//import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
//		Display display = Display.getDefault();
//		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
//			public void run() {
				try {
					RFIDConfigurationManager window = new RFIDConfigurationManager();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//		});
//	}

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
		shlRfidConfigurationManager.setSize(640, 480);
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
		
		final Button btnIsEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnIsEnabled.setBounds(10, 99, 65, 16);
		btnIsEnabled.setText("Enabled");
		
		Label lblAntenna = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna.setBounds(85, 100, 55, 15);
		lblAntenna.setText("Antenna 1");
		
		final Button btnIsDoesNew = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnIsDoesNew.setEnabled(btnIsEnabled.getSelection());
		btnIsDoesNew.setBounds(156, 99, 93, 16);
		btnIsDoesNew.setText("Entry point?");
		
		Label lblBetwixt = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetwixt.setBounds(255, 99, 55, 15);
		lblBetwixt.setText("Between");
		
		final Combo combo = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo.setEnabled(btnIsEnabled.getSelection());
		combo.setBounds(316, 97, 91, 23);
		combo.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label lblAndpersand = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAndpersand.setAlignment(SWT.CENTER);
		lblAndpersand.setBounds(423, 100, 55, 15);
		lblAndpersand.setText("&&");
		
		final Combo combo_1 = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo_1.setEnabled(btnIsEnabled.getSelection());
		combo_1.setBounds(494, 97, 91, 23);
		combo_1.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		ProgressBar progressBar = new ProgressBar(shlRfidConfigurationManager, SWT.NONE);
		progressBar.setBounds(163, 225, 432, 152);
		
		Button btnKillSelf = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnKillSelf.setToolTipText("Closes the application");
		btnKillSelf.setBounds(411, 391, 75, 25);
		btnKillSelf.setText("Quit");
		
		Button btnRunAway = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnRunAway.setToolTipText("Will run Java Connector with current settings");
		btnRunAway.setBounds(520, 391, 75, 25);
		btnRunAway.setText("Execute");
		
		Button btnSaveStuff = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnSaveStuff.setToolTipText("Save the entered configuration for later use");
		btnSaveStuff.setBounds(20, 352, 120, 25);
		btnSaveStuff.setText("Save Configuration");
		
		Button btnLoadStuff = new Button(shlRfidConfigurationManager, SWT.NONE);
		btnLoadStuff.setToolTipText("Load existing configuration");
		btnLoadStuff.setBounds(20, 391, 120, 25);
		btnLoadStuff.setText("Load Configuration");
		
		final Button btnEnabled = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnEnabled.setText("Enabled");
		btnEnabled.setBounds(10, 128, 65, 16);
		
		Label lblAntenna_1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna_1.setText("Antenna 2");
		lblAntenna_1.setBounds(85, 129, 55, 15);
		
		final Button btnEntryPoint = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnEntryPoint.setEnabled(btnEnabled.getSelection());
		btnEntryPoint.setText("Entry point?");
		btnEntryPoint.setBounds(156, 128, 93, 16);
		
		Label lblBetween = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetween.setText("Between");
		lblBetween.setBounds(255, 128, 55, 15);
		
		final Combo combo_2 = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo_2.setEnabled(btnEnabled.getSelection());
		combo_2.setBounds(316, 126, 91, 23);
		combo_2.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label lblAnd = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd.setAlignment(SWT.CENTER);
		lblAnd.setText("&&");
		lblAnd.setBounds(423, 129, 55, 15);
		
		final Combo combo_3 = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo_3.setEnabled(btnEnabled.getSelection());
		combo_3.setBounds(494, 126, 91, 23);
		combo_3.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		final Button btnEnabled_1 = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnEnabled_1.setText("Enabled");
		btnEnabled_1.setBounds(10, 157, 65, 16);
		
		Label lblAntenna_2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna_2.setText("Antenna 3");
		lblAntenna_2.setBounds(85, 158, 55, 15);
		
		final Button btnEntryPoint_1 = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnEntryPoint_1.setEnabled(btnEnabled_1.getSelection());
		btnEntryPoint_1.setText("Entry point?");
		btnEntryPoint_1.setBounds(156, 157, 93, 16);
		
		Label lblBetween_1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetween_1.setText("Between");
		lblBetween_1.setBounds(255, 157, 55, 15);
		
		final Combo combo_4 = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo_4.setEnabled(btnEnabled_1.getSelection());
		combo_4.setBounds(316, 155, 91, 23);
		combo_4.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label label_5 = new Label(shlRfidConfigurationManager, SWT.NONE);
		label_5.setAlignment(SWT.CENTER);
		label_5.setText("&&");
		label_5.setBounds(423, 158, 55, 15);
		
		final Combo combo_5 = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo_5.setEnabled(btnEnabled_1.getSelection());
		combo_5.setBounds(494, 155, 91, 23);
		combo_5.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		final Button btnEnabled_2 = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnEnabled_2.setText("Enabled");
		btnEnabled_2.setBounds(10, 186, 65, 16);
		
		Label lblAntenna_3 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAntenna_3.setText("Antenna 4");
		lblAntenna_3.setBounds(85, 187, 55, 15);
		
		final Button btnEntryPoint_2 = new Button(shlRfidConfigurationManager, SWT.CHECK);
		btnEntryPoint_2.setEnabled(btnEnabled_2.getSelection());
		btnEntryPoint_2.setText("Entry point?");
		btnEntryPoint_2.setBounds(156, 186, 93, 16);
		
		Label lblBetween_2 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblBetween_2.setText("Between");
		lblBetween_2.setBounds(255, 186, 55, 15);
		
		final Combo combo_6 = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo_6.setEnabled(btnEnabled_2.getSelection());
		combo_6.setBounds(316, 184, 91, 23);
		combo_6.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		Label lblAnd_1 = new Label(shlRfidConfigurationManager, SWT.NONE);
		lblAnd_1.setAlignment(SWT.CENTER);
		lblAnd_1.setText("&&");
		lblAnd_1.setBounds(423, 187, 55, 15);
		
		final Combo combo_7 = new Combo(shlRfidConfigurationManager, SWT.NONE);
		combo_7.setEnabled(btnEnabled_2.getSelection());
		combo_7.setBounds(494, 184, 91, 23);
		combo_7.setItems(new String[] {"Warehouse", "Loading Area", "Store Floor", "Back Room", "Exit"});
		
		btnIsEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				combo.setEnabled(combo.getEnabled() ^ true);
				combo_1.setEnabled(combo_1.getEnabled() ^ true);
				btnIsDoesNew.setEnabled(btnIsDoesNew.getEnabled() ^ true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		btnEnabled.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				combo_2.setEnabled(combo_2.getEnabled() ^ true);
				combo_3.setEnabled(combo_3.getEnabled() ^ true);
				btnEntryPoint.setEnabled(btnEntryPoint.getEnabled() ^ true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		btnEnabled_1.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				combo_4.setEnabled(combo_4.getEnabled() ^ true);
				combo_5.setEnabled(combo_5.getEnabled() ^ true);
				btnEntryPoint_1.setEnabled(btnEntryPoint_1.getEnabled() ^ true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) { }
		});
		btnEnabled_2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				//^ is XOR
				combo_6.setEnabled(combo_6.getEnabled() ^ true);
				combo_7.setEnabled(combo_7.getEnabled() ^ true);
				btnEntryPoint_2.setEnabled(btnEntryPoint_2.getEnabled() ^ true);
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
				antenna1.setStoreAreaOne(combo.getText());
				antenna1.setStoreAreaTwo(combo_1.getText());
				antenna1.setEnabled(btnIsEnabled.getSelection());
				antenna1.setEntryPoint(btnIsDoesNew.getSelection());
				//System.out.println("Antenna 1: " + antenna1.toString());
				antennaList.add(antenna1);
				Antenna antenna2 = new Antenna();
				antenna2.setStoreAreaOne(combo_2.getText());
				antenna2.setStoreAreaTwo(combo_3.getText());
				antenna2.setEnabled(btnEnabled.getSelection());
				antenna2.setEntryPoint(btnEntryPoint.getSelection());
				//System.out.println("Antenna 2: " + antenna2.toString());
				antennaList.add(antenna2);
				Antenna antenna3 = new Antenna();
				antenna3.setStoreAreaOne(combo_4.getText());
				antenna3.setStoreAreaTwo(combo_5.getText());
				antenna3.setEnabled(btnEnabled_1.getSelection());
				antenna3.setEntryPoint(btnEntryPoint_1.getSelection());
				//System.out.println("Antenna 3: " + antenna3.toString());
				antennaList.add(antenna3);
				Antenna antenna4 = new Antenna();
				antenna4.setStoreAreaOne(combo_6.getText());
				antenna4.setStoreAreaTwo(combo_7.getText());
				antenna4.setEnabled(btnEnabled_2.getSelection());
				antenna4.setEntryPoint(btnEntryPoint_2.getSelection());
				//System.out.println("Antenna 4: " + antenna4.toString());
				antennaList.add(antenna4);				
				
				JSONConfigurationFile js = new JSONConfigurationFile();
				ArrayList<String> serverInfo = new ArrayList<String>();
				ServerInfo si = new ServerInfo();
				si.setOwner("rfidb");
				si.setPassword("rfidb");
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
				combo.setText(antennaList.get(0).getStoreAreaOne());
				combo_1.setText(antennaList.get(0).getStoreAreaTwo());
				btnIsEnabled.setSelection(antennaList.get(0).isEnabled());
				btnIsDoesNew.setSelection(antennaList.get(0).isEntryPoint());
				combo_2.setText(antennaList.get(1).getStoreAreaOne());
				combo_3.setText(antennaList.get(1).getStoreAreaTwo());
				btnEnabled.setSelection(antennaList.get(1).isEnabled());
				btnEntryPoint.setSelection(antennaList.get(1).isEntryPoint());
				combo_4.setText(antennaList.get(2).getStoreAreaOne());
				combo_5.setText(antennaList.get(2).getStoreAreaTwo());
				btnEnabled_1.setSelection(antennaList.get(2).isEnabled());
				btnEntryPoint_1.setSelection(antennaList.get(2).isEntryPoint());
				combo_6.setText(antennaList.get(3).getStoreAreaOne());
				combo_7.setText(antennaList.get(3).getStoreAreaTwo());
				btnEnabled_2.setSelection(antennaList.get(3).isEnabled());
				btnEntryPoint_2.setSelection(antennaList.get(3).isEntryPoint());

				combo.setEnabled(btnIsEnabled.getSelection());
				combo_1.setEnabled(btnIsEnabled.getSelection());
				btnIsDoesNew.setEnabled(btnIsEnabled.getSelection());
				combo_2.setEnabled(btnEnabled.getSelection());
				combo_3.setEnabled(btnEnabled.getSelection());
				btnEntryPoint.setEnabled(btnEnabled.getSelection());
				combo_4.setEnabled(btnEnabled_1.getSelection());
				combo_5.setEnabled(btnEnabled_1.getSelection());
				btnEntryPoint_1.setEnabled(btnEnabled_1.getSelection());
				combo_6.setEnabled(btnEnabled_2.getSelection());
				combo_7.setEnabled(btnEnabled_2.getSelection());
				btnEntryPoint_2.setEnabled(btnEnabled_2.getSelection());
			}
			public void mouseUp(MouseEvent e) { }
			public void mouseDoubleClick(MouseEvent e) { }
		});
		
//		m_bindingContext = initDataBindings();

	}
//	protected DataBindingContext initDataBindings() {
//		DataBindingContext bindingContext = new DataBindingContext();
//		return bindingContext;
//	}
}
