package com.esgi.vMail.control;

import com.esgi.vMail.control.event.EventOnConnectionListChange;
import com.esgi.vMail.control.event.ListenOnRosterChange;
import com.esgi.vMail.model.*;
import com.esgi.vMail.model.DAO.DAO_Connection_XML;
import com.esgi.vMail.view.option_controler.OptionConnectionListManager.ServerLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.Jid;

import java.io.IOException;

public class ConnectionManager {
	private static ObservableMap<Jid, AccountManager> ownerList = FXCollections.observableHashMap();
	private static ObservableList<Connection> connectionList;
	private static ObservableList<ServerLine> displayedConnectionList;
	private static ObservableList<Group> groupList = FXCollections.observableArrayList();
	private static ObservableMap<Contact, Chat> contactMap = FXCollections.observableHashMap();

	static {
		ConnectionManager.connectionList = ConnectionManager.importConnectionListFromXML();
		ConnectionManager.initRostersAndChat();
		ConnectionManager.loginEnabledConnection();
		ConnectionManager.connectionList.addListener(new EventOnConnectionListChange());
	}

	public static void init() {

	}

	private static void initRostersAndChat() {
		for (Connection connection : connectionList) {
			connection.setRoster(Roster.getInstanceFor(connection));
			connection.setChatManager(ChatManager.getInstanceFor(connection));
			connection.getRoster().setRosterLoadedAtLogin(false);
			connection.getRoster().addRosterListener(new ListenOnRosterChange(connection));
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

	public static ObservableList<Group> getGroupList() {
		return groupList;
	}

	public static ObservableMap<Contact, Chat> getContactMap() {
		return contactMap;
	}

	public static ObservableMap<Jid, AccountManager> getOwnerList() {
		return ownerList;
	}

	public static Contact getContactByJID(Jid JID) {
		Contact[] contacts = new Contact[ConnectionManager.getContactMap().keySet().size()];
		System.out.println("Entry size "+contacts.length);
		ConnectionManager.getContactMap().keySet().toArray(contacts);
		for (Contact contact : contacts) {
			if (contact.getEntry().getJid().equals(JID.asBareJid())) {
				return contact;
			}
		}
		return null;
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

	private static void loginEnabledConnection() {
		for (Connection connection : connectionList.filtered((connectionT) -> connectionT.isEnabled())) {
			try {
				connection.connect();
				connection.login();
				Thread.sleep(1000);
				ownerList.put(connection.getUser(), AccountManager.getInstance(connection));
				connection.getRoster().reload();
				Presence presence = new Presence(Presence.Type.subscribe);
				connection.sendStanza(presence);
				System.out.println("Attributes = "+AccountManager.getInstance(connection).getAccountAttributes().toArray());
				if (connection.getRoster().isSubscribedToMyPresence(connection.getUser())) {
					presence.setType(Type.available);
					presence.setMode(Mode.away);
					connection.sendStanza(presence);
				}
				System.out.println("OwnPresence! ===== "+connection.getRoster().getPresence(connection.getUser().asBareJid()));
			} catch (SmackException | IOException | XMPPException | InterruptedException e) {
				// TODO Auto-generated catch block
				connection.getStatusMsg().set(e.getMessage());
			}
		}

	}
}
