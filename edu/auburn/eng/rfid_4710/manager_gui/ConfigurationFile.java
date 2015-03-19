package edu.auburn.eng.rfid_4710.manager_gui;

import java.io.File;
import java.util.List;

public interface ConfigurationFile {
	public void saveConfiguration(String hostIP, List<String[]> antennaList);
	public ConfigurationFile loadConfiguration(File configurationFile);
	public String getConfigFilePath();
}
