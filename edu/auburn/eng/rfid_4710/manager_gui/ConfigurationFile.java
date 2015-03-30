package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.List;

/**
 * Interface for a ConfigurationFile used by the GUI in order to save and load store layout information which is input 
 * into the GUI by the end user. A ConfigurationFile must be able to be saved, loaded, and must be able to return the path
 * to the ConfigurationFile.
 * 
 * @author Sean Spurlin && Jared Watkins
 * @since 1 (3-20-2015)
 * @version 1.1 (3-29-2015)
 */
public interface ConfigurationFile {
	/**
	 * Save a ConfigurationFile in some defined format. The file must contain information about the host IP of the reader,
	 * all the setup information for each Antenna, and information for connecting to the client's server.
	 * @param hostIP The IP of the Impinj Reader
	 * @param antennaList A list of Antennas which contain all the setup information for each RFID Antenna/Readerr
	 * @param serverInfo Information relating to connecting to a database (currently owner, url, password)
	 */
	public void saveConfiguration(String hostIP, List<Antenna> antennaList, ServerInfo serverInfo);
	public boolean loadConfiguration() throws LoadCancelledException;
	public String getConfigFilePath();
}
