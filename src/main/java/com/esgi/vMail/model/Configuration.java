package com.esgi.vMail.model;

import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class Configuration {
	private XMPPTCPConnectionConfiguration configuration;
	private String name;
	private int priority;
	private boolean isEnabled;
	public Configuration(XMPPTCPConnectionConfiguration configuration, String name) {
		this.configuration = configuration;
		this.name = name;
	}
	public Configuration(XMPPTCPConnectionConfiguration configuration, String name, boolean isEnabled) {
		this(configuration, name);
		this.isEnabled = isEnabled;
	}
	public Configuration(XMPPTCPConnectionConfiguration configuration, String name, int priority, boolean isEnabled) {
		this(configuration, name, isEnabled);
		this.priority = priority;
	}

	public Configuration() {}
	/**
	 * @return the configuration
	 */
	public XMPPTCPConnectionConfiguration getConfiguration() {
		return configuration;
	}
	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(XMPPTCPConnectionConfiguration configuration) {
		this.configuration = configuration;
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
