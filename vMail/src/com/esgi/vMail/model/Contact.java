package com.esgi.vMail.model;

import java.util.ArrayList;

import javax.print.attribute.standard.MediaSize.JIS;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jxmpp.jid.BareJid;

import com.esgi.vMail.view.StatusRound;
import com.esgi.vMail.view.StatusRound.Status;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Contact {
	public static class Event {
		//TODO faire tout les events.
		public static class OnPseudonymChange implements ChangeListener<String>{
			private ArrayList<? super ContactView> contactListViews;
			public OnPseudonymChange(ArrayList<? super ContactView> contactListViews) {
				this.contactListViews = contactListViews;
			}
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				contactListViews.forEach((contact) -> {((ContactView) contact).setName(newValue);});
			}
		}
		public static class OnEntryChange implements ChangeListener<RosterEntry>{
			private StringProperty pseudonym;
			public OnEntryChange(StringProperty pseudonym) {
				this.pseudonym = pseudonym;
			}
			@Override
			public void changed(ObservableValue<? extends RosterEntry> observable, RosterEntry oldValue,
					RosterEntry newValue) {
				pseudonym.set(newValue.getName());
			}

		}
		public static class OnPresenceChange implements ChangeListener<Presence> {
			private ArrayList<? super ContactView> contactListViews;
			public OnPresenceChange(ArrayList<? super ContactView> contactListViews2) {
				this.contactListViews = contactListViews2;
			}
			@Override
			public void changed(ObservableValue<? extends Presence> observable, Presence oldValue, Presence newValue) {
				contactListViews.forEach((contact) -> {((ContactView) contact).setPresence(newValue);});
			}
		}
	}

	public interface ContactView {
		public void setName(String name);
		public void setPresence(Presence presence);
	}

	public static class ContactListView extends HBox implements GraphicalModel<Contact>, ContactView {
		private Label pseudo = new Label();
		private Contact contact;
		private final ArrayList<? super ContactView> contactListViews;
		private StatusRound status = StatusRound.set(Status.OFFLINE);
//		public ContactListView(BareJid jid, String pseudo, Presence presence, ArrayList<ContactListView> contactListViews) {
//			this.jid = jid;
//			this.contactListViews = contactListViews;
//			this.pseudo.setText(pseudo);
//			StatusRound.update(this.status, presence);
//			this.getChildren().add(this.pseudo);
//			this.getChildren().add(this.status);
//		}

		public ContactListView(Contact contact) {
			this.contact = contact;
			this.pseudo.setText(contact.pseudonym.get());
			this.contactListViews = contact.contactListViews;
			StatusRound.update(this.status, contact.presence.get());
			this.getChildren().add(this.pseudo);
			this.getChildren().add(this.status);
			Tooltip infoPlus = new Tooltip();
			infoPlus.setText(contact.getEntry().getJid().asUnescapedString());
			Tooltip.install(this, infoPlus);
		}

		@Override
		public GraphicalModel<Contact> detach() {
			contactListViews.remove(this);
			this.contact = null;
			return this;
		}
		@Override
		public Contact getObjectModel() {
			return contact;
		}

		@Override
		public void setName(String name) {
			this.pseudo.setText(name);
		}

		@Override
		public void setPresence(Presence presence) {
			StatusRound.update(this.status, presence);
		}
	}

	public static class ContactChatHeader extends HBox implements GraphicalModel<Contact>, ContactView {
		private Contact contact;
		private ImageView avatar = new ImageView();
		private Label pseudo = new Label();
		private StatusRound status = StatusRound.set(Status.OFFLINE);
		private Label statusString = new Label();
		private final ArrayList<? super ContactView> contactListViews;
		public ContactChatHeader(Contact contact) {
			this.contact = contact;
			this.contactListViews = contact.contactListViews;
			this.setName(contact.pseudonym.get());
			this.statusString.setText(contact.presence.get().getStatus());
			StatusRound.update(status, this.contact.presence.get());
			this.getChildren().add(avatar);
			this.getChildren().add(new Separator(Orientation.VERTICAL));
			this.getChildren().add(pseudo);
			this.getChildren().add(new Separator(Orientation.VERTICAL));
			this.getChildren().add(status);
			this.getChildren().add(statusString);
			this.setSpacing(5);
			this.setPadding(new Insets(8));
		}

		@Override
		public GraphicalModel<Contact> detach() {
			contactListViews.remove(this);
			this.contact = null;
			return this;
		}

		@Override
		public Contact getObjectModel() {
			return contact;
		}

		@Override
		public void setName(String name) {
			this.pseudo.setText(name);
		}

		@Override
		public void setPresence(Presence presence) {
			StatusRound.update(this.status, presence);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					statusString.setText(presence.getStatus());
				}
			});
		}

	}

	public Contact(RosterEntry entry, Presence presence) {
		this(entry);
		this.presence.set(presence);
	}

	public Contact(RosterEntry entry) {
		this.entry.set(entry);
		this.pseudonym.set((entry.getName() == null)? entry.getJid().asUnescapedString(): entry.getName());
		this.pseudonym.addListener(new Event.OnPseudonymChange(this.contactListViews));
		this.entry.addListener(new Event.OnEntryChange(this.pseudonym));
		this.presence.addListener(new Event.OnPresenceChange(this.contactListViews));
	}

	private final ArrayList<? super ContactView> contactListViews = new ArrayList<>();

	private final StringProperty pseudonym = new SimpleStringProperty();
	private final ObjectProperty<RosterEntry> entry = new SimpleObjectProperty<>();
	private final ObjectProperty<Presence> presence = new SimpleObjectProperty<>();

	public void setPresence(Presence presence) {
		this.presence.set(presence);
	}

	public RosterEntry getEntry() {
		return entry.get();
	}

	public Presence getPresence() {
		return this.presence.get();
	}

	public void setPresenceListener(ChangeListener<Presence> listener) {
		this.presence.addListener(listener);
	}

	public ContactListView asContactListView() {
		ContactListView contactListView = new ContactListView(this);
		this.contactListViews.add(contactListView);
		return contactListView;
	}

	public ContactChatHeader asContactChatHeader() {
		ContactChatHeader contactChatHeader = new ContactChatHeader(this);
		this.contactListViews.add(contactChatHeader);
		return contactChatHeader;
	}
}
