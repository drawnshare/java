package com.esgi.vMail.view_controler;

import java.nio.file.attribute.AttributeView;

import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.model.Chat;
import com.esgi.vMail.model.Chat.ChatTab;
import com.esgi.vMail.model.Contact;
import com.esgi.vMail.model.Contact.ContactListView;
import com.esgi.vMail.model.Group;
import com.esgi.vMail.model.Group.GroupTitledPane;
import com.esgi.vMail.view.OptionsWindow;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;

public class MainWindowManager extends ManagerBuilder {

	@FXML
	private TabPane tabConvList;

	@FXML
	private MenuItem menuIOption;

	@FXML
	private Accordion groupListView;

	@FXML
	private Menu menuStatus;

	@FXML
	private Circle menuRoundStatus;


	@FXML
	public void callOptionPane() {
		OptionsWindow optionsWindow = new OptionsWindow();
		optionsWindow.getWindowStage().initModality(Modality.WINDOW_MODAL);
		optionsWindow.getWindowStage().initOwner(this.windowBuilder.getWindowStage());
		optionsWindow.getWindowStage().show();
	}

	@FXML
	private void initialize() {
		ConnectionManager.getGroupList().forEach((group) -> { this.groupListView.getPanes().add(group.asGroupTitledPane());});
		for (Chat chat : ConnectionManager.getContactMap().values()) {
			//tabConvList.getTabs().add(chat.asChatTab());
			chat.getChatXMPP().addMessageListener(new ChatMessageListener() {
				@Override
				public void processMessage(org.jivesoftware.smack.chat.Chat chat, Message message) {
					if (!ConnectionManager.getContactMap().get(ConnectionManager.getContactByJID(chat.getParticipant())).hasAView()) {
						tabConvList.getTabs().add(ConnectionManager.getContactMap().get(ConnectionManager.getContactByJID(chat.getParticipant())).asChatTab());
					}
				}
			});
		}
//		groupListView.getPanes().forEach((group) -> {
//			((ListView<Contact.ContactListView>) group.getContent()).getItems().forEach((contact) -> {
//				contact.setOnMouseClicked((event) -> {
//					if (event.getClickCount() == 2) {
//						if (!ConnectionManager.getContactMap().get(contact.getObjectModel()).hasAView()) {
//							Chat chat = ConnectionManager
//									.getContactMap()
//									.get(contact.getObjectModel());
//							ChatTab chatTab = chat.asChatTab();
//							ObservableList<Tab> tabs = tabConvList.getTabs();
//							tabs.add(chatTab);
//							tabConvList.getSelectionModel().select(chatTab);
//							event.consume();
//						}
//					}
//				});
//			});
//		});
		for (TitledPane group : groupListView.getPanes()) {
			ListView<Contact.ContactListView> listContacts = (ListView<Contact.ContactListView>) group.getContent();
			for (ContactListView contact : listContacts.getItems()) {
				contact.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) {
							if (!ConnectionManager.getContactMap().get(contact.getObjectModel()).hasAView()) {
								Chat chat = ConnectionManager
										.getContactMap()
										.get(contact.getObjectModel());
								System.out.println(chat.getChatXMPP().getParticipant());
								System.out.println(ConnectionManager.getContactByJID(chat.getChatXMPP().getParticipant()).getPresence());
								Tab chatTab = chat.asChatTab();
								ObservableList<Tab> tabs = tabConvList.getTabs();
								tabs.add(chatTab);
								tabConvList.getSelectionModel().select(chatTab);
								event.consume();
							} else {
								tabConvList.getSelectionModel().select(ConnectionManager.getContactMap().get(ConnectionManager.getContactByJID(contact.getObjectModel().getEntry().getJid())).selectFirstChatTab());
							}
						}
					}
				});
			}
		}
    }

	public TabPane getTabConvList() {
		return tabConvList;
	}

	public Circle getMenuRoundStatus() {
		return menuRoundStatus;
	}
}
