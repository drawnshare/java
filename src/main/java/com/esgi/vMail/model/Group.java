package com.esgi.vMail.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import org.jivesoftware.smack.roster.RosterGroup;

import java.util.ArrayList;

public class Group {
	public static class Event {
		public static class OnTitleChange implements ChangeListener<String> {
			private ArrayList<GroupTitledPane> groupTitledpanes;
			public OnTitleChange(ArrayList<GroupTitledPane> groupTitledpanes) {
				this.groupTitledpanes = groupTitledpanes;
			}
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				groupTitledpanes.forEach((group) -> {group.setText(newValue);});
			}
		}
		public static class OnEntryChange implements ChangeListener<RosterGroup>{
			private StringProperty title;
			public OnEntryChange(StringProperty title) {
				this.title = title;
			}
			@Override
			public void changed(ObservableValue<? extends RosterGroup> observable, RosterGroup oldValue,
					RosterGroup newValue) {
				title.set(newValue.getName());
			}

		}
	}
	public static class GroupTitledPane extends TitledPane implements GraphicalModel<Group> {
		private Group group;
		private final ListView<Contact.ContactListView> contactList = new ListView<>();
		public GroupTitledPane(Group group) {
			this.group = group;
			this.setText(group.title.get());
			this.setContent(this.contactList);
			for (Contact contact : group.contactList) {
				this.contactList.getItems().add(contact.asContactListView());
			}
		}
		@Override
		public GraphicalModel<Group> detach() {
			contactList.getItems().remove(this);
			this.group = null;
			return this;
		}
		@Override
		public Group getObjectModel() {
			return group;
		}
	}

	public Group(RosterGroup groupXMPP) {
		this.groupXMPP.set(groupXMPP);
		this.title.set(groupXMPP.getName());
	}

	private final ArrayList<GroupTitledPane> groupTitledPanes = new ArrayList<>();

	private final StringProperty title = new SimpleStringProperty();
	private final ObjectProperty<RosterGroup> groupXMPP = new SimpleObjectProperty<>();
	private final ListProperty<Contact> contactList = new SimpleListProperty<>(FXCollections.observableArrayList());

	public ObservableList<Contact> getContactList() {
		return contactList;
	}

	public RosterGroup getGroupXMPP() {
		return groupXMPP.get();
	}

	public GroupTitledPane asGroupTitledPane() {
		GroupTitledPane groupTitledPane = new GroupTitledPane(this);
		this.groupTitledPanes.add(groupTitledPane);
		return groupTitledPane;
	}
}
