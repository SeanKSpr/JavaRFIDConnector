package edu.auburn.eng.rfid_4710.manager_gui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * JSONConfigurationFile is an implementation of the ConfigurationFile interface which utilizes the JSON standard
 * syntax to format the configuration file. This class also provides some GUI popups in order to facilitate the loading
 * and saving process utilizing SWT FileDialogs.
 * 
 * @since 1.1 (3-29-2015)
 * @version 1.2 (3-30-2015)
 * @author Sean Spurlin & Jared Watkins
 */
public class JSONConfigurationFile implements ConfigurationFile{
	
	private JsonObject jsonConfig;
	
	@Override
	public void saveConfiguration(String hostIP, List<Antenna> antennaList, ServerInfo serverInfo) {
		JsonObject jsonObj = new JsonObject();
		jsonObj = createJSONConfigFile(hostIP, antennaList, serverInfo);
		try {
			saveJsonObjAsJSONFile(jsonObj);
		} catch (IOException e) {
			displayErrorBox("Opening File", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Private helper method for displaying an SWT error styled MessageBox to the end user detailing an error
	 * and how/where it occurred.
	 * @param errorAction The action that was taken which caused the error to occur
	 * @param errorMessage The error message
	 */
	private void displayErrorBox(String errorAction, String errorMessage) {
		MessageBox dialog = new MessageBox(new Shell(), SWT.ERROR | SWT.OK);
				dialog.setText("Error during action: " + errorAction);
				dialog.setMessage("Error message: " + errorMessage);
				dialog.open();
	}
	
	/**
	 * Private helper method for saving a JSONConfigurationFile. It takes as input a JsonObject containing all the information
	 * to be saved as a JSON file, raises a save dialog to get the path and file name, then writes the JsonObject into the file
	 * specified by the user in the save dialog.
	 * @param jsonObj The completed JsonObject containing all the configuration information
	 * @throws IOException Exception which is thrown if the program fails to open and write to the specified save location
	 */
	private void saveJsonObjAsJSONFile(JsonObject jsonObj) throws IOException {
		String path = saveFile();
		if (path == null) { return;}
		FileWriter file = new FileWriter(path);
		try {
			file.write(jsonObj.toString());
		} catch (IOException e) {
			displayErrorBox("Opening File", e.getMessage());
			e.printStackTrace();
		} finally {
			file.flush();
			file.close();
		}
	}

	@Override
	public boolean loadConfiguration() throws LoadCancelledException{
		String pathAsString = getConfigFilePath();
		if (pathAsString == null) {
			throw new LoadCancelledException();
		}
		Path path = Paths.get(pathAsString);
		JsonParser parser = new JsonParser();
		String configFileContent;
		try {
			configFileContent = new String(Files.readAllBytes(path));
			JsonObject configurationFile = (JsonObject) parser.parse(configFileContent);
			this.jsonConfig = configurationFile;
		} catch (IOException e) {
			displayErrorBox("Reading a config file", e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * A private helper method for creating the JSONConfigurationFile. It takes as input information sent by the GUI and 
	 * parses it all into JSON which is then added to the JSONConfigurationFile's JSON file
	 * @param hostIP the Host name/IP of the Impinj RFID Reader
	 * @param antennaList A list of Antenna objects containing all the information necessary to set up the RFID antennas/readers
	 * @param serverInfo Information about the database to be connected to
	 * @return
	 */
	private JsonObject createJSONConfigFile(String hostIP, List<Antenna> antennaList, ServerInfo serverInfo) {
		JsonObject configurationFileFields = new JsonObject();
		JsonObject configurationFile = new JsonObject();
		configurationFileFields.addProperty("readerHost", hostIP);		
		addAntennaListToJSON(configurationFileFields, antennaList);
		addServerSetupToJSON(configurationFileFields, serverInfo);
		configurationFile.add("configurationFile", configurationFileFields);
		return configurationFile;
	}
	
	/**
	 * Private helper method used by the createJSONConfigFile which parses the ServerInfo into JSON and adds it to the passed
	 * JsonObject.
	 * @param configurationFileFields the JsonObject containing all the fields of the JSONConfigurationFile
	 * @param serverInfo Information about the database to be connected to
	 */
	private void addServerSetupToJSON(JsonObject configurationFileFields,
			ServerInfo serverInfo) {
		JsonObject jsonServerInfo = new JsonObject();
		jsonServerInfo.addProperty("owner", serverInfo.getOwner());
		jsonServerInfo.addProperty("password", serverInfo.getPassword());
		jsonServerInfo.addProperty("url", serverInfo.getUrl());
		configurationFileFields.add("serverSetup", jsonServerInfo);
		
	}
	
	/**
	 * Private helper method used by the createJSONConfigFile which takes each Antenna in the antennaList and parses them into JSON
	 * which it then adds to the passed JsonObject
	 * @param jsonObj The JsonObject containing all the fields of the JSONConfigurationFile
	 * @param antennaList A list of Antenna objects containing all the information necessary to set up the RFID antennas/readers
	 */
	private void addAntennaListToJSON(JsonObject jsonObj, List<Antenna> antennaList) {
		JsonArray jsonAntennas = new JsonArray();
		JsonObject jsonAntenna;
		for (Antenna antennaProperties : antennaList) {
			jsonAntenna = new JsonObject();
			jsonAntenna.addProperty("antennaID", antennaProperties.getAntennaID());
			jsonAntenna.addProperty("enabled", antennaProperties.isEnabled());
			jsonAntenna.addProperty("isEntryPoint", antennaProperties.isEntryPoint());
			jsonAntenna.addProperty("storeAreaOne", antennaProperties.getStoreAreaOne());
			jsonAntenna.addProperty("storeAreaTwo", antennaProperties.getStoreAreaTwo());
			jsonAntenna.addProperty("insertionLocation", antennaProperties.getInsertionLocation());
			jsonAntennas.add(jsonAntenna);
		}
		jsonObj.add("antennaList", jsonAntennas);
	}

	@Override
	public String getConfigFilePath() {
		return fileDialogBuilder(SWT.OPEN).getFilterPath();
	        
	}
	
	/**
	 * Private helper function for raising either Save/Load styled SWT FileDialog objects. These are used to obtain the full path
	 * and file name for an RFIDConfigurationFile
	 * @param STYLE the style of the FileDialog. Either SWT.SAVE or SWT.OPEN. See the documentation of SWT for additional STYLE codes.
	 * @return the full path which the user selects from the FileDialog
	 */
	private FileDialog fileDialogBuilder(int STYLE) {
		String fileFilterPath = Paths.get("").toString();
        FileDialog fileDialog = new FileDialog(new Shell(), STYLE);
        fileDialog.setFilterPath(fileFilterPath);
        fileDialog.setFilterExtensions(new String[]{"*.rmconf"});
        fileDialog.setFilterNames(new String[]{ "RFID Manager Configuration File"});
        fileDialog.setFilterPath(fileDialog.open());
		return fileDialog;
	}
	
	/**
	 * Simply calls fileDialogBuilder with the SWT.SAVE style code in order to raise a save dialog to the end user
	 * @return the full path which the user selects from the FileDialog
	 */
	public String saveFile() {
		return fileDialogBuilder(SWT.SAVE).getFilterPath();
	}
	
	/**
	 * Parses the JSONConfigurationFile to retrieve the Host name/IP of the Impinj Reader
	 * @return the host name/IP of the Impinj Reader
	 */
	public String getHostname() {
		JsonObject configurationFile = jsonConfig.get("configurationFile").getAsJsonObject();
		if (configurationFile.get("readerHost").isJsonNull()) {
			return "";
		}
		return configurationFile.get("readerHost").getAsString();
	}
	
	/**
	 * Parses the JSONConfigurationFile to retrieve all the Antenna setup information
	 * @return A List of Antennas containing all the setup information which was stored in the JSONConfigurationFile
	 */
	public ArrayList<Antenna> getAntennaList() {
		ArrayList<Antenna> antennaList = new ArrayList<Antenna>();
		JsonObject configurationFile = jsonConfig.get("configurationFile").getAsJsonObject();
		JsonArray antennasInJson = configurationFile.get("antennaList").getAsJsonArray();
		Antenna antenna;
		for (JsonElement antennaProperties : antennasInJson) {
			antenna = new Antenna();
			JsonObject jsonAntenna = antennaProperties.getAsJsonObject();
			if (jsonAntenna.get("enabled").isJsonNull()) {
				antenna.setEnabled(false);
			}
			else {
				antenna.setEnabled(jsonAntenna.get("enabled").getAsBoolean());
			}
			
			if (jsonAntenna.get("isEntryPoint").isJsonNull()) {
				antenna.setEntryPoint(false);
			}
			else {
				antenna.setEntryPoint(jsonAntenna.get("isEntryPoint").getAsBoolean());
			}
			
			if (jsonAntenna.get("storeAreaOne").isJsonNull()) {
				antenna.setStoreAreaOne("");
			}
			else {
				antenna.setStoreAreaOne(jsonAntenna.get("storeAreaOne").getAsString());
			}
			
			if (jsonAntenna.get("storeAreaTwo").isJsonNull()) {
				antenna.setStoreAreaTwo("");
			}
			else {
				antenna.setStoreAreaTwo(jsonAntenna.get("storeAreaTwo").getAsString());
			}
			
			antenna.setAntennaID(jsonAntenna.get("antennaID").getAsInt());
			
			if (jsonAntenna.get("insertionLocation").isJsonNull()) {
				antenna.setInsertionLocation("");
			}
			else {
				antenna.setInsertionLocation(jsonAntenna.get("insertionLocation").getAsString());
			}
			antennaList.add(antenna);
		}
		return antennaList;
	}
	
	/**
	 * Parses the JSONConfigurationFile to retrieve all the server information
	 * @return A ServerInfo object containing all the server/database information which was stored in the JSONConfigurationFile
	 */
	public ServerInfo getServerInfo() {
		ServerInfo serverInfo = new ServerInfo();
		JsonObject configurationFile = jsonConfig.get("configurationFile").getAsJsonObject();
		JsonObject serverInfoInJson = configurationFile.get("serverSetup").getAsJsonObject();
		
		if (serverInfoInJson.get("owner").isJsonNull()) {
			serverInfo.setOwner("");
		}
		else {
			serverInfo.setOwner(serverInfoInJson.get("owner").getAsString());
		}
		
		if (serverInfoInJson.get("password").isJsonNull()) {
			serverInfo.setPassword("");
		}
		else {
			serverInfo.setPassword(serverInfoInJson.get("password").getAsString());
		}
		
		if (serverInfoInJson.get("url").isJsonNull()) {
			serverInfo.setUrl("");
		}
		else {
			serverInfo.setUrl(serverInfoInJson.get("url").getAsString());
		}
		return serverInfo;
	}

	public static void main(String[] args) throws IOException {
		JSONConfigurationFile configFile = new JSONConfigurationFile();
		String hostName = null;
		ArrayList<Antenna> antennaList = null;
		ServerInfo serverInfo = null;
		try {
			configFile.loadConfiguration();
			hostName = configFile.getHostname();
			antennaList = configFile.getAntennaList();
			serverInfo = configFile.getServerInfo();
		} catch (LoadCancelledException e) {
			e.printStackTrace();
		}
		configFile.saveConfiguration(hostName, antennaList, serverInfo);

		configFile.displayErrorBox("Opening File", "Error message goes here");
		configFile.getConfigFilePath();
	}
}
