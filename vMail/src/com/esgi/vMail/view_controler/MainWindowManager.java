package com.esgi.vMail.view_controler;

import java.util.ArrayList;
import java.util.Map;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.view.OptionsWindow;
import com.esgi.vMail.view.StatusRound;
import com.esgi.vMail.view.StatusRound.Status;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class MainWindowManager extends ManagerBuilder {

	public static class Group extends TitledPane {
		public static class Contact extends HBox {
			private Label pseudo;
			private StatusRound status = StatusRound.set(Status.OFFLINE);
			private RosterEntry contact;
			public Contact(RosterEntry contact) {
				this.contact = contact;
				this.pseudo = new Label((!(contact.getName() == null))? contact.getName() : contact.getUser());
//				this.status = (contact.)
				this.getChildren().add(pseudo);
				this.getChildren().add(status);
				Tooltip tooltip = new Tooltip();
				tooltip.setGraphic(status);
				Tooltip.install(this, tooltip);
			}

			public void setPresence(Presence presence) {
				System.out.println(presence.getType()+"."+presence.getStatus());
				System.out.print(this.contact.getName());
				switch (presence.getType()) {
				case available:
					System.out.print("=> dispo");
					
					switch (presence.getMode()) {
					case available:
						System.out.println(" = tres dispo");
						StatusRound.update(this.status, Status.ONLINE);
						break;
					case chat:
						System.out.println(" = tres tres dispo");
						StatusRound.update(this.status, Status.ONLINE);
						break;
					case away:
						System.out.println(" = AFK");
						StatusRound.update(this.status, Status.AWAY);
						break;
					case xa:
						System.out.println(" = long AFK");
						StatusRound.update(this.status, Status.AWAY);
						break;
					case dnd:
						System.out.println(" = DND");
						StatusRound.update(this.status, Status.BUSY);
						break;
					default:
						System.out.println(" = WTF Oo");
						StatusRound.update(this.status, Status.ONLINE);
						break;
					}
					break;
				default:
					System.out.println("=> pas dispo");
					StatusRound.update(this.status, Status.OFFLINE);
					break;
				}
				this.applyCss();
//				case available:
//					System.out.println("=> dispo");
//					this.status = StatusRound.set(Status.ONLINE);
//					break;
//				case chat:
//					System.out.println("=> dispo++");
//					this.status = StatusRound.set(Status.ONLINE);
//					break;
//				case away:
//					System.out.println("=> POLA");
//					this.status = StatusRound.set(Status.AWAY);
//					break;
//				case xa:
//					System.out.println("=> pola++");
//					this.status = StatusRound.set(Status.AWAY);
//					break;
//				case dnd:
//					System.out.println("=> NPD");
//					this.status = StatusRound.set(Status.BUSY);
//					break;
//				default:
//					this.status = StatusRound.set(Status.HIDDEN);
//					break;
//				}
			}

			public void setPresence(StatusRound status) {
				this.status = status;
			}

			public RosterEntry getContact() {
				return contact;
			}
		}
//		private ListView<Contact> contactsInGroup = new ListView<>();
		private RosterGroup rosterGroup;

		public Group(RosterGroup group) {
			super();
			this.setText(group.getName());
			this.setContent(new ListView<Contact>());
			this.rosterGroup = group;
//			ArrayList<Contact> contacts = new ArrayList<>();
//			group.getEntries().forEach((contact) -> {contacts.add(new Contact(contact));});
//			this.contactsInGroup = new ListView<>(FXCollections.observableArrayList(contacts));
//			this.getChildren().add(this.contactsInGroup);
		}

		public RosterGroup getRosterGroup() {
			return rosterGroup;
		}

		public ObservableList<Contact> getContactsListInGroup () {
			return ((ListView<Contact>) this.getContent()).getItems();
		}
	}


	@FXML
	private MenuItem menuIOption;

	@FXML
	private Accordion groupListView;

	@FXML
	public void callOptionPane() {
		OptionsWindow optionsWindow = new OptionsWindow();
		optionsWindow.getWindowStage().setAlwaysOnTop(true);
		optionsWindow.getWindowStage().show();
	}

	@FXML
	private void initialize() {
		this.groupListView.getPanes().addAll(ConnectionManager.getGroupList());
    }

//	private Accordion createContactList(Accordion contacts) {
//		for (Map.Entry<Connection, Roster> connection_roster : ConnectionManager.getConnectionRosterMap().entrySet()) {
//			for (RosterGroup group : connection_roster.getValue().getGroups()) {
//				contacts.getPanes().add(new TitledPane(group.getName(), this.generateContactInGroup(group)));
//			}
//		}
//		return contacts;
//	}
//
//	private ListView<Contact> generateContactsInGroup(RosterGroup group) {
//		ObservableList<Contact> contactsGroup = FXCollections.observableArrayList();
//		for (RosterEntry entry : group.getEntries()) {
//			contactsGroup.add(new Contact(entry));
//		}
//		return new ListView<MainWindowManager.Contact>(contactsGroup);
//	}
}
