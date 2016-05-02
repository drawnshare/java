package com.esgi.vMail.control;

import java.io.IOException;
import java.util.ArrayList;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import com.esgi.vMail.model.Configuration;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.DAO.DAO_Connection_XML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConnectionManager {
	public static ObservableList<Connection> connectionList;
	static {
		ConnectionManager.connectionList = ConnectionManager.getConnectionList();
	}

	public static ObservableList<Connection> getConnectionList() {
		ObservableList<Connection> connectionList = FXCollections.observableArrayList();
		for (Configuration configuration : DAO_Connection_XML.getAll2ConnectionConf()) {
			connectionList.add(new Connection(configuration));
		}
		return connectionList;
	}

	public static boolean isConnectionValid(Connection connection) {
		try {
			connection.connect();
			return connection.isConnected();
		} catch (SmackException | IOException | XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return false;
	}

	public static void connectEnabledConnection(ObservableList<Connection> connectionList) {
		for (Connection connection : connectionList) {
			if (connection.isEnabled()) {
				try {
					connection.connect();
				} catch (SmackException | IOException | XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
