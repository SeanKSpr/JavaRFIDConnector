package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.List;

public interface ConfigurationFile {
	public void saveConfiguration(String hostIP, List<String[]> antennaList);
	public ConfigurationFile loadConfiguration();
	public String getConfigFilePath();
}
