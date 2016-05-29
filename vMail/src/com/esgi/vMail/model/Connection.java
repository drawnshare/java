package com.esgi.vMail.model;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;

import com.sun.glass.ui.TouchInputSupport;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;

public class Connection extends XMPPTCPConnection {
	// Initialisation var
	private String name;
	private int priority;
	private boolean isEnabled;
	public Connection(XMPPTCPConnectionConfiguration config) {
		super(config);
		this.name = this.getServiceName().toString();
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

	//Status var
	private BooleanProperty isLogged = new SimpleBooleanProperty(false);
	private StringProperty statusMsg = new SimpleStringProperty("Initial");
	//Status method
	public BooleanProperty getIsLogged() {
		return isLogged;
	}

	public void setIsLogged(BooleanProperty isLogged) {
		this.isLogged = isLogged;
	}

	public void setStatusMsg(StringProperty statusMsg) {
		this.statusMsg = statusMsg;
	}

	public StringProperty getStatusMsg() {
		return statusMsg;
	}

	@Override
	public synchronized org.jivesoftware.smack.AbstractXMPPConnection connect() throws SmackException ,IOException ,XMPPException, InterruptedException {
		AbstractXMPPConnection connection = super.connect();
		if (connection.isConnected()) {
			this.getStatusMsg().set("Connected");
		}
		return connection;
	};

	@Override
	public synchronized void login() throws XMPPException, SmackException, IOException, InterruptedException {
		super.login();
		if (this.isAuthenticated()) {
			this.getStatusMsg().set("Logged");
		}
		this.isLogged.set(super.isAuthenticated());
	}

	public void loginAndWaitForPresence() throws XMPPException, SmackException, IOException, InterruptedException {
		this.login();
		while (!Roster.getInstanceFor(this).isSubscribedToMyPresence(this.getUser())) {

		}
	}

	@Override
	public void disconnect() {
		super.disconnect();
		this.isLogged.set(super.isAuthenticated());
	}
}
