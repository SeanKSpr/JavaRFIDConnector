package edu.auburn.eng.rfid_4710.manager_gui;

import java.util.ArrayList;
import java.util.List;

public class ServerInfo {
	private String owner, password, url;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<String> toStringList() {
		ArrayList<String> serverInfoAsString = new ArrayList<String>();
		serverInfoAsString.add(owner);
		serverInfoAsString.add(password);
		serverInfoAsString.add(url);
		return serverInfoAsString;
	}
}
