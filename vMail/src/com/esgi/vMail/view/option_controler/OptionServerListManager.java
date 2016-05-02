package com.esgi.vMail.view.option_controler;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.model.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class OptionServerListManager {
	//Begin of intern class
	public static class ServerLine extends HBox{
		private CheckBox isEnable;
		private Label serverName;
		private Label status;
		public ServerLine(Connection connection) {
			//Initialize Graphical Object with Connection state
			this.isEnable = new CheckBox();
			this.isEnable.setSelected(connection.isEnabled());
			this.serverName = new Label(connection.getName());
			//Add sub-graphical-component to ServerLine component
			this.getChildren().add(this.isEnable);
			this.getChildren().add(this.serverName);
			this.getChildren().add(this.status);
			//Add event
			this.isEnable.setOnAction(new OptionServerListManager.EventOnConnectionChangeStatus(connection));
			this.setOnMouseClicked(new OptionServerListManager.EventOnServerLineDoubleClick(connection));
		}
	}
	public static class EventOnConnectionChangeStatus  implements EventHandler<ActionEvent> {
		private Connection connection;

		public EventOnConnectionChangeStatus(Connection connection) {
			this.connection = connection;
		}

		@Override
		public void handle(ActionEvent event) {
			this.connection.setEnabled(((CheckBox) event.getSource()).isSelected());
		}
	}

	public static class EventOnServerLineDoubleClick implements EventHandler<MouseEvent> {
		private Connection connection;

		public EventOnServerLineDoubleClick(Connection connection) {
			this.connection = connection;
		}

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			if (event.getClickCount() == 2) {
				System.err.println("TODO Edit Connection");
			}
		}

	}
	//End of intern class

	public static ObservableList<OptionServerListManager.ServerLine> getServerLineList() {
		ObservableList<OptionServerListManager.ServerLine> serverLineList = FXCollections.observableArrayList();
		for (Connection connection : ConnectionManager.connectionList) {
			serverLineList.add(new OptionServerListManager.ServerLine(connection));
		}
		return serverLineList;
	}

}

