package com.esgi.vMail.control.event;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.Contact;
import com.esgi.vMail.model.Group;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterGroup;
import org.jivesoftware.smack.roster.RosterListener;
import org.jxmpp.jid.Jid;

import java.util.Collection;

//import com.esgi.vMail.view_controler.MainWindowManager.Group;
//import com.esgi.vMail.view_controler.MainWindowManager.Group.Contact;

public class ListenOnRosterChange implements RosterListener {

	private Connection connection;
	private Roster roster;
	private ChatManager chatManager;

	public ListenOnRosterChange(Connection connection) {
		this.connection = connection;
		this.roster = connection.getRoster();
		this.chatManager = connection.getChatManager();
		chatManager.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {

				if (createdLocally) {
					ConnectionManager.getContactMap().putIfAbsent(ConnectionManager.getContactByJID(chat.getParticipant()), new com.esgi.vMail.model.Chat(chat));
				} else {
					if (ConnectionManager.getContactByJID(chat.getParticipant()) == null) {
						ConnectionManager.getContactMap().put(createContact(chat.getParticipant()), new com.esgi.vMail.model.Chat(chat));
					} else {
						ConnectionManager.getContactMap().putIfAbsent(ConnectionManager.getContactByJID(chat.getParticipant()), new com.esgi.vMail.model.Chat(chat));
						ConnectionManager.getContactMap().get(ConnectionManager.getContactByJID(chat.getParticipant())).setChatXMPP(chat);
					}
				}
			}
		});
	}

	@Override
	public void entriesAdded(Collection<Jid> addeds) {
		for (Jid jid : addeds) {
			this.createContact(jid);
			//SI cela plante, le code qui etait ici est maintenant dans createContactAndChat
		}
	}

	public Contact createContact (Jid jid) {
		return createContactAndChat(jid, null);
	}

	public Contact createContactAndChat(Jid jid, com.esgi.vMail.model.Chat chat) {
		Contact newContact = (ConnectionManager.getContactByJID(jid.asBareJid()) == null)? new Contact(roster.getEntry(jid.asBareJid())): ConnectionManager.getContactByJID(jid);
		ConnectionManager.getContactMap().put(newContact, chat);
//		System.out.println(Thread.currentThread().getName());
		newContact.setPresence(roster.getPresence(jid.asBareJid()));
		chatManager.createChat(jid.asEntityJidIfPossible());
		for (RosterGroup group : newContact.getEntry().getGroups()) {
			if (!ConnectionManager.getGroupList().filtered((groupPane) -> {return groupPane.getGroupXMPP().equals(group);}).isEmpty()) {
				for (Group groupPane : ConnectionManager.getGroupList()) {
					if (groupPane.getGroupXMPP().equals(group)) {
						groupPane.getContactList().add(newContact);
					}
				}
			} else {
				Group newGroup = new Group(group);
				ConnectionManager.getGroupList().add(newGroup);
				newGroup.getContactList().add(newContact);
			}
		}
		return newContact;
	}

	@Override
	public void entriesUpdated(Collection<Jid> updateds) {
		// TODO Auto-generated method stub
		updateds.forEach((jid) -> {
			System.out.println(jid.asBareJid() + " updated ->" + ConnectionManager.getContactByJID(jid));
		});
	}

	@Override
	public void entriesDeleted(Collection<Jid> removeds) {
		for (Jid jid : removeds) {
//			removeIf((contact) -> {return contact.getEntry().getJid().equals(jid.asBareJid());});
			ConnectionManager.getContactMap().keySet().forEach((contact) -> {
				if (contact.getEntry().getJid().equals(jid.asBareJid())) {
					ConnectionManager.getContactMap().remove(contact);
				}
			});
			for (RosterGroup group : roster.getEntry(jid.asBareJid()).getGroups()) {
				ConnectionManager.getGroupList().forEach((groupPane) -> {
					if (groupPane.getGroupXMPP().equals(group)) {
						groupPane.getContactList().removeIf((contactView) -> {
							return contactView.getEntry().equals(roster.getEntry(jid.asBareJid()));
						});
					}
				});
			}
		}
	}

	@Override
	public void presenceChanged(Presence presence) {
//		ConnectionManager.getContactMap().keySet().filtered((contact) -> {return contact.getEntry().getJid().equals(presence.getFrom().asBareJid());}).forEach((contact) -> {contact.setPresence(presence);});
		ConnectionManager.getContactMap().keySet().forEach((contact) -> {
			if (contact.getEntry().getJid().equals(presence.getFrom().asBareJid())) {
				contact.setPresence(presence);
			}
		});
//		for (Group group : ConnectionManager.getGroupList()) {
//			if (!group.getContactsListInGroup().filtered((contact) -> {return contact.getContact().getJid().equals(presence.getFrom().asBareJid());}).isEmpty()) {
//				group.getContactsListInGroup().filtered((contact) -> {return contact.getContact().getJid().equals(presence.getFrom().asBareJid());}).get(0).setPresence(presence);
//			}
//		}
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
