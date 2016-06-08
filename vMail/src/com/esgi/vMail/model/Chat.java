package com.esgi.vMail.model;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jivesoftware.smack.SmackException.NotConnectedException;
//import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.control.LangManager;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Chat {
//	public static class Event {
//		public static class On
//	}
	public static class ChatTab implements GraphicalModel<Chat> {
		private Tab tab;
		private TextFlow messagesDisplay = new TextFlow();
//		private WebEngine displayEngine = messagesDisplay.getEngine();
		private TextArea messageEditor = new TextArea();
		private Chat chat;
		private ObjectProperty<Contact> receiver;
		private ArrayList<ChatTab> chatTabs = new ArrayList<>();
		private ListProperty<Message> messages;
//		private final StringBuffer messageBuilder = new StringBuffer();
//		public ChatTab(ObjectProperty<Contact> receiver,ListProperty<Message> messages ,ArrayList<ChatTab> chatTabs) {
//			this.loadFXMLAndController();
//			this.receiver = receiver;
//			this.messages = messages;
//			this.chatTabs = chatTabs;
//			this.onMessageProcessed();
//		}

		public ChatTab(Chat chat) {
			super();
			this.receiver = chat.receiver;
			this.messages = chat.messages;
			this.chatTabs = chat.chatTabs;
			this.chat = chat;
			this.tab = this.buildChatTab();
			this.setOnMessageSend();
			this.onMessageProcessed();
//			this.setText((receiver.get().getEntry().getName() != null)? receiver.get().getEntry().getName() : receiver.get().getEntry().getJid().asUnescapedString());
		}

		private void setOnMessageSend() {
			messageEditor.setOnKeyReleased((event) -> {
				if (event.getCode().equals(KeyCode.ENTER) && !messageEditor.getText().trim().isEmpty()) {

					Message message = new Message(this.getReceiver().getEntry().getJid(), messageEditor.getText().trim());
					this.getMessages().add(message);
					try {
						if (message.getBody() != null) {
							this.getObjectModel().chatXMPP.get().sendMessage(message);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					messageEditor.clear();
				}
				event.consume();
			});
		}

		private Tab buildChatTab() {
			Tab tab = new Tab();
			tab.setText((receiver.get().getEntry().getName() != null)? receiver.get().getEntry().getName() : receiver.get().getEntry().getJid().asUnescapedString());
			BorderPane borderPane = new BorderPane();
			tab.setContent(new AnchorPane(borderPane));
			AnchorPane.setTopAnchor(borderPane, 0.0);
			AnchorPane.setBottomAnchor(borderPane, 0.0);
			AnchorPane.setLeftAnchor(borderPane, 0.0);
			AnchorPane.setRightAnchor(borderPane, 0.0);
			borderPane.setTop(chat.receiver.get().asContactChatHeader());
			borderPane.setCenter(new ScrollPane(messagesDisplay));
			borderPane.setBottom(messageEditor);

			//Layout Settings
			messagesDisplay.setPadding(new Insets(5));
			messageEditor.setMaxHeight(70);
			messageEditor.setMinHeight(20);
			messageEditor.setPrefHeight(50);



			return tab;
		}

		public ListProperty<Message> getMessages() {
			return messages;
		}

		public Contact getReceiver() {
			return receiver.get();
		}

		@Override
		public GraphicalModel<Chat> detach() {
			chatTabs.remove(this);
			chat = null;
			return this;
		}

		@Override
		public boolean equals(Object obj) {
			boolean isEqual = false;
			if (obj instanceof Chat.ChatTab) {
				ChatTab otherChatTab = (ChatTab) obj;
				if (otherChatTab.getReceiver().equals(this.getReceiver())) {
					isEqual = false;
				}
			}
			return isEqual;
		}

		private void onMessageProcessed() {
			this.messages.get().addListener(new ListChangeListener<Message>() {
				@Override
				public synchronized void onChanged(javafx.collections.ListChangeListener.Change<? extends Message> change) {
					while (change.next()) {
						if (change.wasAdded()) {

							for (Message message : change.getAddedSubList()) {
								if (message.getBody() != null) {
									String pseudo;
									Color color;
									if (message.getFrom() != null) {
										pseudo = receiver.get().getEntry().getName();
										System.out.println(receiver.get().getEntry().getName());
										color = Color.ALICEBLUE;
									} else {
										pseudo = LangManager.text("me");
										color = Color.DARKORANGE;
									}
									Text txtPseudo = new Text(pseudo +": ");
									txtPseudo.setFill(color);
									txtPseudo.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
									Text txtMessage = new Text(message.getBody()+ "\n");
									messagesDisplay.getChildren().addAll(txtPseudo, txtMessage);
								}
							}
						}
					}
				}
			});
		}

		@Override
		public Chat getObjectModel() {
			return chat;
		}

	}

	private final ArrayList<ChatTab> chatTabs = new ArrayList<>();

	private final ListProperty<Message> messages = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ObjectProperty<org.jivesoftware.smack.chat.Chat> chatXMPP = new SimpleObjectProperty<>();
	private final ObjectProperty<Contact> receiver = new SimpleObjectProperty<>();
	public Chat(org.jivesoftware.smack.chat.Chat chatXMPP) {
		this.chatXMPP.set(chatXMPP);
		System.out.println(ConnectionManager.getContactMap());
		this.receiver.set((Contact) FXCollections.observableArrayList(ConnectionManager.getContactMap().keySet().toArray()).filtered((contact) -> {return ((Contact) contact).getEntry().getJid().equals(chatXMPP.getParticipant().asBareJid());}).get(0));
		this.listen2NewMessages();
	}

	public Chat(org.jivesoftware.smack.chat.Chat chatXMPP, Contact receiver) {
		this.listen2NewMessages();
		this.chatXMPP.set(chatXMPP);
		this.receiver.set(receiver);
	}

	private void listen2NewMessages() {
		this.chatXMPP.addListener(new ChangeListener<org.jivesoftware.smack.chat.Chat>() {

			@Override
			public void changed(ObservableValue<? extends org.jivesoftware.smack.chat.Chat> observable,
					org.jivesoftware.smack.chat.Chat oldValue, org.jivesoftware.smack.chat.Chat newValue) {
				newValue.addMessageListener(new ChatMessageListener() {
					@Override
					public void processMessage(org.jivesoftware.smack.chat.Chat chat, Message message) {
						Platform.runLater(() -> {
							messages.add(message);
						});
					}
				});
			}

		});
//		this.chatXMPP.get().addMessageListener(new ChatMessageListener() {
//			@Override
//			public void processMessage(org.jivesoftware.smack.chat.Chat chat, Message message) {
//				messages.add(message);
//
//			}
//		});
//		this.messages.get().addListener(new ListChangeListener<Message>() {
//			@Override
//			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Message> change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//						for (Message message : change.getAddedSubList()) {
//							System.out.println(message.getBody());
//							if (message.getTo().asEntityJidIfPossible().equals(chatXMPP.get().getParticipant())) {
//								try {
//									chatXMPP.get().sendMessage(message);
//								} catch (NotConnectedException | InterruptedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						}
//					}
//				}
//			}
//		});
	}

	public org.jivesoftware.smack.chat.Chat getChatXMPP() {
		return chatXMPP.get();
	}

	public ObservableList<Message> getMessages() {
		return messages.get();
	}

	public void setChatXMPP(org.jivesoftware.smack.chat.Chat chat) {
		this.chatXMPP.set(chat);
	}

	public Tab asChatTab() {
		ChatTab chatTab = new ChatTab(this);
		this.chatTabs.add(chatTab);
		return chatTab.tab;
	}

	public boolean hasAView() {
		return !chatTabs.isEmpty();
	}

	public Tab selectFirstChatTab() {
		return chatTabs.get(0).tab;
	}

}
