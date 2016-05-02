package com.esgi.vMail.view_controler.event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class EventOptionDisplayDetails implements EventHandler<MouseEvent> {
	private AnchorPane optionBody;
	private Pane pane;

	public EventOptionDisplayDetails(AnchorPane optionBody, Pane pane) {
		this.optionBody = optionBody;
		this.pane = pane;
	}

	@Override
	public void handle(MouseEvent event) {
		optionBody.getChildren().clear();
		optionBody.getChildren().add(pane);
	}

}
