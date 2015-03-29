package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.List;

public interface ConfigurationFile {
	public void saveConfiguration(String hostIP, List<Antenna> antennaList, ServerInfo serverInfo);
	public boolean loadConfiguration() throws LoadCancelledException;
	public String getConfigFilePath();
}
