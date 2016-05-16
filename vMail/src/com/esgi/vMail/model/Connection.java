package com.esgi.vMail.model;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import com.sun.glass.ui.TouchInputSupport;

public class Connection extends XMPPTCPConnection {
	private String name;
	private int priority;
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
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Connection [name=" + name + ", priority=" + priority + ", isEnabled=" + isEnabled + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Connection)) {
			return false;
		} else {
			Connection otherConnection = (Connection) obj;
			if (this.getName().equals(otherConnection.getName())) {
				return true;
			} else {
				return false;
			}
		}
	}
}
