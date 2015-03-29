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

	private void saveJsonObjAsJSONFile(JsonObject jsonObj) throws IOException {
		String path = saveFile();
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
	
	private void displayErrorBox(String errorAction, String errorMessage) {
		MessageBox dialog = new MessageBox(new Shell(), SWT.ERROR | SWT.OK);
				dialog.setText("Error during action: " + errorAction);
				dialog.setMessage("Error message: " + errorMessage);
				dialog.open();
	}

	private JsonObject createJSONConfigFile(String hostIP, List<Antenna> antennaList, ServerInfo serverInfo) {
		JsonObject configurationFileFields = new JsonObject();
		JsonObject configurationFile = new JsonObject();
		configurationFileFields.addProperty("readerHost", hostIP);		
		addAntennaListToJSON(configurationFileFields, antennaList);
		addServerSetupToJSON(configurationFileFields, serverInfo);
		configurationFile.add("configurationFile", configurationFileFields);
		return configurationFile;
	}

	private void addServerSetupToJSON(JsonObject configurationFileFields,
			ServerInfo serverInfo) {
		JsonObject jsonServerInfo = new JsonObject();
		jsonServerInfo.addProperty("owner", serverInfo.getOwner());
		jsonServerInfo.addProperty("password", serverInfo.getPassword());
		jsonServerInfo.addProperty("url", serverInfo.getUrl());
		configurationFileFields.add("serverSetup", jsonServerInfo);
		
	}

	private void addAntennaListToJSON(JsonObject jsonObj, List<Antenna> antennaList) {
		JsonArray jsonAntennas = new JsonArray();
		JsonObject jsonAntenna;
		for (Antenna antennaProperties : antennaList) {
			jsonAntenna = new JsonObject();
			jsonAntenna.addProperty("enabled", antennaProperties.isEnabled());
			jsonAntenna.addProperty("isEntryPoint", antennaProperties.isEntryPoint());
			jsonAntenna.addProperty("storeAreaOne", antennaProperties.getStoreAreaOne());
			jsonAntenna.addProperty("storeAreaTwo", antennaProperties.getStoreAreaTwo());
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
	
	public ServerInfo getServerInfo() {
		ServerInfo serverInfo = new ServerInfo();
		JsonObject configurationFile = jsonConfig.get("configurationFile").getAsJsonObject();
		JsonObject serverInfoInJson = configurationFile.get("serverSetup").getAsJsonObject();
		serverInfo.setOwner(serverInfoInJson.get("owner").getAsString());
		serverInfo.setPassword(serverInfoInJson.get("password").getAsString());
		serverInfo.setUrl(serverInfoInJson.get("url").getAsString());
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
		//String savePath = configFile.saveFile();
		//FileWriter writer = new FileWriter(savePath);
		//writer.write("Test 2");
		//writer.flush();
		//writer.close();
	}
}
