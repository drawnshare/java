package com.esgi.vMail.model;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class Connection extends XMPPTCPConnection {
	private String name;
	private boolean isEnabled;
	public Connection(XMPPTCPConnectionConfiguration config) {
		super(config);
		this.name = this.getServiceName();
	}
	public Connection(Configuration configuration) {
		this(configuration.getConfiguration());
		this.name = configuration.getName();
		this.isEnabled = configuration.isEnabled();
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
