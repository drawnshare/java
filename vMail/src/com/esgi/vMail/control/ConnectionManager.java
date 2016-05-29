package com.esgi.vMail.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.Manager;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.parsing.ExceptionLoggingCallback;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.RosterLoadedListener;
import org.jivesoftware.smack.roster.SubscribeListener;
import org.jivesoftware.smack.roster.SubscribeListener.SubscribeAnswer;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;

import com.esgi.vMail.control.event.EventOnConnectionListChange;
import com.esgi.vMail.control.event.ListenOnRosterChange;
import com.esgi.vMail.model.Configuration;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.DAO.DAO_Connection_XML;
import com.esgi.vMail.view.option_controler.OptionConnectionListManager.EventOnConnectionChangeStatus;
import com.esgi.vMail.view.option_controler.OptionConnectionListManager.ServerLine;
import com.esgi.vMail.view_controler.MainWindowManager.Group;
import com.esgi.vMail.view_controler.MainWindowManager.Group.Contact;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.Event;

public class ConnectionManager {
	private static ObservableList<Connection> connectionList;
	private static ObservableList<ServerLine> displayedConnectionList;
	private static ObservableMap<Connection, Roster> connectionRosterMap;
	private static ObservableList<Group> groupList = FXCollections.observableArrayList();

	static {
		ConnectionManager.connectionList = ConnectionManager.importConnectionListFromXML();
		ConnectionManager.initRosters();
		ConnectionManager.connectionList.addListener(new EventOnConnectionListChange());
	}

	private static void initRosters() {
		connectionRosterMap = FXCollections.observableHashMap();
		for (Connection connection : connectionList) {
			Roster connectionRoster = Roster.getInstanceFor(connection);
			connectionRoster.setRosterLoadedAtLogin(false);
			connectionRosterMap.put(connection, connectionRoster);
			connectionRoster.addRosterListener(new ListenOnRosterChange(connection ,connectionRoster));
		}
	}

	//Testing purpose
	public static void displayRosterEntries() {
		for (Map.Entry<Connection, Roster> connection : connectionRosterMap.entrySet()) {
			connection.getValue().getGroups().forEach((group) -> {System.out.println(group.getName());});
			for (RosterEntry entry : connection.getValue().getEntries()) {
				System.out.println(entry);
			}
		}
	}

	public static ObservableList<Connection> importConnectionListFromXML() {
		ObservableList<Connection> connectionList = FXCollections.observableArrayList();
		for (Configuration configuration : DAO_Connection_XML.getAll2ConnectionConf()) {
			connectionList.add(new Connection(configuration));
		}
		return connectionList;
	}

	/**
	 * @return the connectionList
	 */
	public static ObservableList<Connection> getConnectionList() {
		return connectionList;
	}

	public static ObservableMap<Connection, Roster> getConnectionRosterMap() {
		return connectionRosterMap;
	}

	public static ObservableList<Group> getGroupList() {
		return groupList;
	}

	/**
	 * @param displayedConnectionList the displayedConnectionList to set
	 */
	public static void setDisplayedConnectionList(ObservableList<ServerLine> displayedConnectionList) {
		ConnectionManager.displayedConnectionList = displayedConnectionList;
	}

	/**
	 * @return the displayedConnectionList
	 */
	public static ObservableList<ServerLine> getDisplayedConnectionList() {
		return displayedConnectionList;
	}

	public static boolean isConnectionValid(Connection connection) {
		try {
			connection.connect();
			return connection.isConnected();
		} catch (SmackException | IOException | XMPPException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return false;
	}

	public static void loginEnabledConnection() {
		for (Connection connection : connectionList.filtered((connection) -> { return connection.isEnabled(); })) {
			try {
				connection.connect();
				connection.login();
				Thread.sleep(1000);
				ConnectionManager.getConnectionRosterMap().get(connection).reload();
			} catch (SmackException | IOException | XMPPException | InterruptedException e) {
				// TODO Auto-generated catch block
				connection.getStatusMsg().set(e.getMessage());
			}
		}

	}
}
