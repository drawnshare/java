package com.esgi.vMail.control.event;

import java.util.Collection;
import java.util.LinkedList;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.RosterListener;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.view_controler.MainWindowManager.Group;
import com.esgi.vMail.view_controler.MainWindowManager.Group.Contact;

import javafx.beans.property.BooleanProperty;

public class ListenOnRosterChange implements RosterListener {

	private Roster roster;
	private Connection connection;
	private LinkedList<RosterEntry> contacts = new LinkedList<>();

	public ListenOnRosterChange(Connection connection,Roster roster) {
		this.roster = roster;
	}

	@Override
	public void entriesAdded(Collection<Jid> addeds) {
		for (Jid jid : addeds) {
			RosterEntry contact = roster.getEntry(jid.asBareJid());
			Contact newContact = new Contact(contact);
			newContact.setPresence(roster.getPresence(jid.asBareJid()));
			for (RosterGroup group : contact.getGroups()) {
				if (!ConnectionManager.getGroupList().filtered((groupPane) -> {return groupPane.getRosterGroup().equals(group);}).isEmpty()) {
					for (Group groupPane : ConnectionManager.getGroupList()) {
						if (groupPane.getRosterGroup().equals(group)) {
							groupPane.getContactsListInGroup().add(newContact);
						}
					}
				} else {
					Group newGroup = new Group(group);
					ConnectionManager.getGroupList().add(newGroup);
					newGroup.getContactsListInGroup().add(newContact);
				}
			}
		}
//		// TODO Auto-generated method stub
//		System.out.println(addeds);
//		LinkedList<RosterEntry> contacts = new LinkedList<>(roster.getEntries());
//		contacts.removeAll(this.contacts);
//		for (RosterEntry contact : contacts) {
//			Contact newContact = new Contact(contact);
////			newContact.setPresence(connection, roster);
//			for (RosterGroup group : contact.getGroups()) {
//				if (!ConnectionManager.getGroupList().filtered((groupPane) -> {return groupPane.getRosterGroup().equals(group);}).isEmpty()) {
//					for (Group groupPane : ConnectionManager.getGroupList()) {
//						if (groupPane.getRosterGroup().equals(group)) {
//							groupPane.getContactsListInGroup().add(newContact);
//						}
//					}
//				} else {
//					Group newGroup = new Group(group);
//					ConnectionManager.getGroupList().add(newGroup);
//					newGroup.getContactsListInGroup().add(newContact);
//				}
//			}
//		}
//		this.contacts.addAll(contacts);
	}

	@Override
	public void entriesUpdated(Collection<Jid> updateds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void entriesDeleted(Collection<Jid> removeds) {
		for (Jid jid : removeds) {
			for (RosterGroup group : roster.getEntry(jid.asBareJid()).getGroups()) {
				ConnectionManager.getGroupList().forEach((groupPane) -> {
					if (groupPane.getRosterGroup().equals(group)) {
						groupPane.getContactsListInGroup().removeIf((contactView) -> {
							return contactView.getContact().equals(roster.getEntry(jid.asBareJid()));
						});
					}
				});
			}
		}
	}

	@Override
	public void presenceChanged(Presence presence) {
		for (Group group : ConnectionManager.getGroupList()) {
			if (!group.getContactsListInGroup().filtered((contact) -> {return contact.getContact().getJid().equals(presence.getFrom());}).isEmpty()) {
				group.getContactsListInGroup().filtered((contact) -> {return contact.getContact().getJid().equals(presence.getFrom());}).get(0).setPresence(presence);
			}
		}
	}

//	@Override
//	public void entriesAdded(Collection<String> addeds) {
//		System.out.println(addeds);
//		LinkedList<RosterEntry> contacts = new LinkedList<>(roster.getEntries());
//		contacts.removeAll(this.contacts);
//		for (RosterEntry contact : contacts) {
//			Contact newContact = new Contact(contact);
////			newContact.setPresence(connection, roster);
//			newContact.setPresence(roster.getPresence(contact.getUser()));
//			for (RosterGroup group : contact.getGroups()) {
//				if (!ConnectionManager.getGroupList().filtered((groupPane) -> {return groupPane.getRosterGroup().equals(group);}).isEmpty()) {
//					for (Group groupPane : ConnectionManager.getGroupList()) {
//						if (groupPane.getRosterGroup().equals(group)) {
//							groupPane.getContactsListInGroup().add(newContact);
//						}
//					}
//				} else {
//					Group newGroup = new Group(group);
//					ConnectionManager.getGroupList().add(newGroup);
//					newGroup.getContactsListInGroup().add(newContact);
//				}
//			}
//		}
//		this.contacts.addAll(contacts);
//	}
//
//	@Override
//	public void entriesDeleted(Collection<String> deleteds) {
//		LinkedList<RosterEntry> contacts = new LinkedList<>(roster.getEntries());
//		LinkedList<RosterEntry> oldContacts = this.contacts;
//		oldContacts.removeAll(contacts);
//		for (RosterEntry contact : oldContacts) {
//			for (RosterGroup group : contact.getGroups()) {
//				ConnectionManager.getGroupList().forEach((groupPane) -> {
//					if (groupPane.getRosterGroup().equals(group)) {
//						groupPane.getContactsListInGroup().removeIf((contactView) -> {
//							return contactView.getContact().equals(contact);
//						});
//					}
//				});
//			}
//		}
//		this.contacts.removeAll(oldContacts);
//	}
//
//	@Override
//	public void entriesUpdated(Collection<String> updateds) {
//
//	}
//
//	@Override
//	public void presenceChanged(Presence presence) {
//		for (Group group : ConnectionManager.getGroupList()) {
//			if (!group.getContactsListInGroup().filtered((contact) -> {return contact.getContact().getUser().equals(presence.getFrom());}).isEmpty()) {
//				group.getContactsListInGroup().filtered((contact) -> {return contact.getContact().getUser().equals(presence.getFrom());}).get(0).setPresence(presence);
//			}
//		}
//	}


}
