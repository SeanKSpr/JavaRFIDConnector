package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.ArrayList;
import java.util.List;

/**
 * ServerInfo is simply a data class used for storing all the information needed to connect to a database (in particular a
 * PostgreSQL database). This information includes the owner/user name of the database, the password, and the url to the database.
 * @since 1 (3-18-2015)
 * @version 1.1 (3-29-2015)
 * @author Sean Spurlin & Jared Watkins
 */
public class ServerInfo {
	private String owner, password, url;
	
	/**
	 * Get method for obtaining the owner/user name of the database
	 * @return the owner/user name of the database
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Set method for owner
	 * @param owner the user name/owner of the database
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * get method for password
	 * @return the password of the database
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * set method for password
	 * @param password the password of the database
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * get method for url
	 * @return the url to the database
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * set method for url
	 * @param url the url of the database
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Helper method which converts the ServerInfo into a List of Strings
	 * List[0] = owner
	 * List[1] = password
	 * List[2] = url
	 * @return a List of Strings pertaining to the ServerInfo fields
	 */
	public List<String> toStringList() {
		ArrayList<String> serverInfoAsString = new ArrayList<String>();
		serverInfoAsString.add(owner);
		serverInfoAsString.add(password);
		serverInfoAsString.add(url);
		return serverInfoAsString;
	}
}
