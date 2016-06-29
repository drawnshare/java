package com.esgi.vMail.model;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.control.LangManager;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

public class Chat {
//	public static class Event {
//		public static class On
//	}
	public static class ChatTab implements GraphicalModel<Chat> {
		private Tab tab;
		private WebView messagesDisplay = new WebView();
		private WebEngine displayEngine = messagesDisplay.getEngine();
		private TextArea messageEditor = new TextArea();
		private Chat chat;
		private ObjectProperty<Contact> receiver;
		private ArrayList<ChatTab> chatTabs = new ArrayList<>();
		private ListProperty<Message> messages;
		private final StringBuilder messageBuilder = new StringBuilder();
		private int nbMissingMessage;
		private int pseudoLength;
//		public ChatTab(ObjectProperty<Contact> receiver,ListProperty<Message> messages ,ArrayList<ChatTab> chatTabs) {
//			this.loadFXMLAndController();
//			this.receiver = receiver;
//			this.messages = messages;
//			this.chatTabs = chatTabs;
//			this.onMessageProcessed();
//		}

		public ChatTab(Chat chat) {
			super();
			messageBuilder
			.append("<head>")
			.append("<script language=\"javascript\" type=\"text/javascript\">")
			.append("function toBottom(){window.scrollTo(0, document.body.scrollHeight);}")
			.append("</script>")
			.append("</head>")
			.append("<body onload='toBottom()'>");
			//.append("</body>");
			this.receiver = chat.receiver;
			this.messages = chat.messages;
			this.chatTabs = chat.chatTabs;
			this.chat = chat;
			this.tab = this.buildChatTab();
			this.setOnPresenceChange();
			this.setOnMessageSend();
			this.onMessageProcessed();
			this.setOnSelection();
//			this.setText((receiver.get().getEntry().getName() != null)? receiver.get().getEntry().getName() : receiver.get().getEntry().getJid().asUnescapedString());
		}

		private void setOnSelection() {
			this.tab.selectedProperty().addListener((bool, oldValue, newValue) -> {
				if (newValue) {
					messageEditor.requestFocus();
					tab.setText(receiver.get().getEntry().getName());
					nbMissingMessage = 0;
				}
			});
		}

		private void setOnPresenceChange() {
			this.receiver.get().setPresenceListener((observable, oldValue, newValue) -> {
                messageBuilder.append(receiver.get().getEntry().getName()).append(' ').append(LangManager.text("chat.presence.change")).append(' ').append(LangManager.text("chat.presence.status."+newValue.getMode().name())).append("<br>");
                Platform.runLater(() -> displayEngine.loadContent(messageBuilder.toString()));
            });

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
//			ScrollPane scrollPane = new ScrollPane(messagesDisplay);
			borderPane.setCenter(messagesDisplay);
			borderPane.setBottom(messageEditor);

			//Layout Settings
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
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends Message> change) {
					if (!tab.isSelected()) {
						while (change.next()) {
							if (change.wasAdded()) {
								change.getAddedSubList().forEach((message) -> {
									if (message.getBody() != null) {
										nbMissingMessage++;
										tab.setText(receiver.get().getEntry().getName() + ' ' + '(' + nbMissingMessage + ')');
									} else {
										System.out.println(message.toXML());
									}
								});
							}
						}
					}
				}

			});
			this.messages.get().addListener(new ListChangeListener<Message>() {
				@Override
				public synchronized void onChanged(javafx.collections.ListChangeListener.Change<? extends Message> change) {
					while (change.next()) {
						if (change.wasAdded()) {

							for (Message message : change.getAddedSubList()) {
								if (message.getBody() != null) {
									String pseudo;
									Color color;
									// TODO NE MARCHE PAS DU TOUT
									if (message.getFrom() != null) {
										pseudo = receiver.get().getEntry().getName();
										System.out.println(receiver.get().getEntry().getName());
										color = Color.AQUAMARINE;
									} else {
										pseudo = LangManager.text("chat.me");
										color = Color.DARKORANGE;
									}
									// TODO ----------------------
									Text txtPseudo = new Text(pseudo +": ");
//									txtPseudo.setFill(color);
//									txtPseudo.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));

									Text txtMessage = new Text(escapeHtml4(message.getBody()).replace("\n".subSequence(0, 1), "<br>".subSequence(0, 4))+ "<br>");
									messageBuilder.append(txtPseudo.getText()).append(txtMessage.getText());
									displayEngine.loadContent(messageBuilder.toString());
//									displayEngine.executeScript("window.scrollTo(0,document.body.scrollHeight);");
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
		this.chatXMPP.addListener((observable, oldValue, newValue) -> newValue.addMessageListener((chat, message) -> Platform.runLater(() -> messages.add(message))));
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
