package com.esgi.vMail.view_controler;

import com.esgi.vMail.view.StatusRound;

import org.jivesoftware.smack.packet.Message;
import org.omg.CORBA.INITIALIZE;

import com.esgi.vMail.model.Chat.ChatTab;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class ChatManager {
	private ChatTab tab;

	@FXML
	private WebView messageDisplay;

	@FXML
	private TextArea messageEditor;

	@FXML
	private Label contactPseudo;

	@FXML
	private Circle contactStatus;

	public WebView getMessageDisplay() {
		return messageDisplay;
	}

	public void setContactPseudo(String contactPseudo) {
		this.contactPseudo.setText(contactPseudo);
	}

	public void setContactStatus(StatusRound.Status status) {
		StatusRound.update((StatusRound) this.contactStatus, status);
	}

	public ChatTab getTab() {
		return tab;
	}

	public void setTab(ChatTab tab) {
		this.tab = tab;
	}

	@FXML
	public void initialize() {
		System.out.println("Yolo?");
//		System.out.println(messageEditor.getParent());
//		messageEditor.setOnKeyReleased((event) -> {
//			if (event.getCode().equals(KeyCode.ENTER)) {
//				tab.getMessages().add(new Message(tab.getReceiver().getEntry().getJid(), messageEditor.getText()));
//				messageEditor.clear();
//			}
//		});
	}
}
