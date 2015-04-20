package edu.auburn.eng.sks0024.rfid_connector;

import edu.auburn.eng.rfid_4710.manager_gui.ServerInfo;

/**
 * RFIDReader is an interface for interacting with the RFID reader. It only includes the bare methods needed to setup
 * the RFID reader and then have the reader begin reading tags.
 * 
 * @since 	1.1	(2-23-2015)
 * @version 1.2 (4-20-2015)
 * @author Sean Spurlin 
 */
public interface RFIDConnector extends Runnable{
	/**
	 * Function:		run
	 * 
	 * Precondition:	readerBootstrap has been called and the reader has a hostname and a ReaderLocation
	 * Postcondition:	The implementing class will begin reading RFID tags and adding them to a collection of read tags
	 */
	public void run();
	/**
	 * Function:		 connectorBootstrap
	 * 
	 * Precondition:	 The RFID reader is connected to the computer running this software
	 * Postcondition:	 The software RFID reader knows the hostname of the Impinj RFID reader and a Timer has been set 
	 * to update the database periodically with the Tags read by the RFID reader. The database information has been set
	 * in the RFIDDatabaseManager implementation class.
	 * @param hostname - The IP address/hostname of the hardware RFID reader
	 * @param serverInformation The server information needed to connect to the UPC database
	 */
	public void connectorBootstrap(String hostname, ServerInfo serverInformation);
}
