package edu.auburn.eng.rfid_4710.manager_gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONConfigurationFile implements ConfigurationFile{
	
	@Override
	public void saveConfiguration(String hostIP, List<String[]> antennaList) {
		JsonObject jsonObj = new JsonObject();
		jsonObj.add("configurationFile", createJSONConfigFile(hostIP, antennaList));
		try {
			saveJsonObjAsJSONFile(jsonObj);
		} catch (IOException e) {
			//TODO display error popup
			e.printStackTrace();
		}
	}

	private void saveJsonObjAsJSONFile(JsonObject jsonObj) throws IOException {
		String path = saveFile();
		FileWriter file = new FileWriter(path);
		try {
			file.write(jsonObj.toString());
		} catch (IOException e) {
			//TODO: display error popup
			e.printStackTrace();
		} finally {
			file.flush();
			file.close();
		}
	}

	@Override
	public ConfigurationFile loadConfiguration(File configurationFile) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private JsonObject createJSONConfigFile(String hostIP, List<String[]> antennaList) {
		JsonObject jsonObj = new JsonObject();
		jsonObj.addProperty("readerHost", hostIP);		
		addAntennaListToJSON(jsonObj, antennaList);
		return jsonObj;
		
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
	
	public static void main(String[] args) throws IOException {
		JSONConfigurationFile configFile = new JSONConfigurationFile();
		//configFile.getConfigFileName();
		String savePath = configFile.saveFile();
		FileWriter writer = new FileWriter(savePath);
		writer.write("Test 2");
		writer.flush();
		writer.close();
	}
}
