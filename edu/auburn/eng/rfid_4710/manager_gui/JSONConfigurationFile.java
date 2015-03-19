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

public class JSONConfigurationFile implements ConfigurationFile {
	private JsonObject jsonConfig;
	@Override
	public void saveConfiguration(String hostIP, List<String[]> antennaList) {
		JsonObject jsonObj = new JsonObject();
		jsonObj.add("configurationFile", createJSONConfigFile(hostIP, antennaList));
		try {
			saveJsonObjAsJSONFile(jsonObj);
		} catch (IOException e) {
			displayErrorBox("Opening File", e.getMessage());
		}
	}

	private void displayErrorBox(String errorAction, String errorMessage) {
		MessageBox dialog = new MessageBox(new Shell(), SWT.ERROR | SWT.OK);
				dialog.setText("Error during action: " + errorAction);
				dialog.setMessage("Error message: " + errorMessage);
				dialog.open();
	}

	private void saveJsonObjAsJSONFile(JsonObject jsonObj) throws IOException {
		String path = saveFile();
		FileWriter file = new FileWriter(path);
		try {
			file.write(jsonObj.toString());
		} catch (IOException e) {
			displayErrorBox("Writing to File", e.getMessage());
		} finally {
			file.flush();
			file.close();
		}
	}

	@Override
	public ConfigurationFile loadConfiguration() {
		String pathAsString = getConfigFilePath();
		Path path = Paths.get(pathAsString);
		JsonParser parser = new JsonParser();
		String configFileContent;
		try {
			configFileContent = new String(Files.readAllBytes(path));
			JsonObject configurationFile = (JsonObject) parser.parse(configFileContent);
			this.jsonConfig = configurationFile;
		} catch (IOException e) {
			displayErrorBox("Reading a config file", e.getMessage());
		}
		return this;
	}
	
	private JsonObject createJSONConfigFile(String hostIP, List<String[]> antennaList) {
		JsonObject configurationFileFields = new JsonObject();
		JsonObject configurationFile = new JsonObject();
		configurationFileFields.addProperty("readerHost", hostIP);		
		addAntennaListToJSON(configurationFileFields, antennaList);
		configurationFile.add("configurationFile", configurationFileFields);
		return configurationFile;
		
	}

	private void addAntennaListToJSON(JsonObject jsonObj, List<String[]> antennaList) {
		JsonArray jsonAntennas = new JsonArray();
		JsonObject jsonAntenna;
		for (String[] antennaProperties : antennaList) {
			jsonAntenna = new JsonObject();
			jsonAntenna.addProperty("enabled", Boolean.parseBoolean(antennaProperties[0]));
			jsonAntenna.addProperty("isEntryPoint", Boolean.parseBoolean(antennaProperties[1]));
			jsonAntenna.addProperty("storeAreaOne", antennaProperties[2]);
			jsonAntenna.addProperty("storeAreaTwo", antennaProperties[3]);
			jsonAntennas.add(jsonAntenna);
		}
		jsonObj.add("antennaList", jsonAntennas);
	}

	@Override
	public String getConfigFilePath() {
		return fileDialogBuilder(SWT.OPEN).getFilterPath();
	        
	}

	private FileDialog fileDialogBuilder(int STYLE) {
		String fileFilterPath = Paths.get("").toString();
        FileDialog fileDialog = new FileDialog(new Shell(), STYLE);
        fileDialog.setFilterPath(fileFilterPath);
        fileDialog.setFilterExtensions(new String[]{"*.rmconf"});
        fileDialog.setFilterNames(new String[]{ "RFID Manager Configuration File"});
        fileDialog.setFilterPath(fileDialog.open());
		return fileDialog;
	}
	
	public String saveFile() {
		return fileDialogBuilder(SWT.SAVE).getFilterPath();
	}
	
	public String getHostname() {
		JsonObject configurationFile = jsonConfig.get("configurationFile").getAsJsonObject();
		return configurationFile.get("readerHost").getAsString();
	}
	
	public ArrayList<Antenna> getAntennaList() {
		ArrayList<Antenna> antennaList = new ArrayList<Antenna>();
		JsonObject configurationFile = jsonConfig.get("configurationFile").getAsJsonObject();
		JsonArray antennasInJson = configurationFile.get("antennaList").getAsJsonArray();
		Antenna antenna;
		for (JsonElement antennaProperties : antennasInJson) {
			antenna = new Antenna();
			JsonObject jsonAntenna = antennaProperties.getAsJsonObject();
			antenna.setEnabled(jsonAntenna.get("enabled").getAsBoolean());
			antenna.setEntryPoint(jsonAntenna.get("isEntryPoint").getAsBoolean());
			antenna.setStoreAreaOne(jsonAntenna.get("storeAreaOne").getAsString());
			antenna.setStoreAreaTwo(jsonAntenna.get("storeAreaTwo").getAsString());
			antennaList.add(antenna);
		}
		return antennaList;
	}
	
	public static void main(String[] args) throws IOException {
		JSONConfigurationFile configFile = new JSONConfigurationFile();
		configFile.loadConfiguration();
		String hostName = configFile.getHostname();
		ArrayList<Antenna> antennaList = configFile.getAntennaList();
		
		configFile.saveConfiguration(hostName, Antenna.toListOfStringArrays(antennaList));
		
		configFile.displayErrorBox("Opening File", "Error message goes here");
		configFile.getConfigFilePath();
		//String savePath = configFile.saveFile();
		//FileWriter writer = new FileWriter(savePath);
		//writer.write("Test 2");
		//writer.flush();
		//writer.close();
	}
}
