package com.esgi.vMail.view.option_controler;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.view.ConnectionEditor;
import com.esgi.vMail.view.StatusRound;
import com.esgi.vMail.view.StatusRound.Status;
import com.esgi.vMail.view_controler.ConnectionEditorManager;
import com.sun.corba.se.spi.activation.Server;
import com.sun.javafx.geom.AreaOp.AddOp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class OptionConnectionListManager {

	//Begin of intern class
	public static class ServerLine extends HBox {
		private CheckBox isEnable;
		private Label serverName;
		private StatusRound statusRound;
		private Tooltip statusTip;
		private Connection connection;
		public ServerLine(Connection connection) {
			//Initialize Graphical Object with Connection state
			this.connection = connection;
			this.statusRound = (connection.isAuthenticated()) ? StatusRound.set(Status.ONLINE) : StatusRound.set(Status.BUSY);
			this.isEnable = new CheckBox();
			this.isEnable.setSelected(connection.isEnabled());
			this.statusTip = new Tooltip(connection.getStatusMsg().get());
			this.serverName = new Label(connection.getName());
			//Add graphical settings
			this.setSpacing(20);
			Tooltip.install(this, this.statusTip);
			//Add sub-graphical-component to ServerLine component
			this.getChildren().add(this.isEnable);
			this.getChildren().add(this.serverName);
			this.getChildren().add(this.statusRound);
			//Add event
			this.connection.getStatusMsg().addListener(new OptionConnectionListManager.ChangeStatusMsg(this.statusTip));
			this.connection.getIsLogged().addListener(new OptionConnectionListManager.ChangeStatusRoundColor(this.statusRound));
			this.isEnable.setOnAction(new OptionConnectionListManager.EventOnConnectionChangeStatus(connection));
			this.setOnMouseClicked(new OptionConnectionListManager.EventOnServerLineDoubleClick(connection));
		}

		public Connection getConnection() {
			return connection;
		}
	}

	public static class ChangeStatusMsg implements ChangeListener<String> {
		private Tooltip status;

		public ChangeStatusMsg(Tooltip status) {
			this.status = status;
		}

		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			this.status.setText(newValue);
		};
	}

	public static class ChangeStatusRoundColor implements ChangeListener<Boolean> {

		private StatusRound round;

		public ChangeStatusRoundColor(StatusRound round) {
			this.round = round;
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue) {
				round = StatusRound.set(Status.ONLINE);
			} else {
				round = StatusRound.set(Status.OFFLINE);
			}
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

	public static class DisplayConnectionEditor implements EventHandler<ActionEvent> {
		public enum Type {
			ADD,
			MOD
		}

		private Type type;
		private ListView<ServerLine> list;

		public DisplayConnectionEditor() {
			this.type = Type.ADD;
		}

		public DisplayConnectionEditor(ListView<ServerLine> list) {
			this.type = Type.MOD;
			this.list = list;
		}

		@Override
		public void handle(ActionEvent event) {
			ConnectionEditor connectionEditor = new ConnectionEditor();
			switch (type) {
			case MOD:
				ServerLine serverLine = list.getSelectionModel().getSelectedItem();
				connectionEditor.getWindowStage().setTitle(connectionEditor.getWindowStage().getTitle() + " (" + LangManager.text("editor.connection.title.editing") + " " + serverLine.getConnection().getName() + ")");
				ConnectionEditorManager controler = (ConnectionEditorManager) connectionEditor.getControler();
				controler.setIs4Modifications(true);
				controler.setName(serverLine.getConnection().getName());
				controler.setUser(serverLine.getConnection().getConfiguration().getUsername().toString());
				controler.setPassword(serverLine.getConnection().getConfiguration().getPassword());
				controler.setServerAddress(serverLine.getConnection().getServiceName().toString());
				controler.setPort(serverLine.getConnection().getPort());
				controler.setResourceName(serverLine.getConnection().getConfiguration().getResource().toString());
				controler.setPriorityValue(serverLine.getConnection().getPriority());
			case ADD:
				connectionEditor.getWindowStage().show();
				break;
			default:
				break;
			}
		};
	}

	public static class DeleteConnection implements EventHandler<ActionEvent> {
		private ListView<ServerLine> list;
		public DeleteConnection(ListView<ServerLine> list) {
			this.list = list;
		}
		public void handle(ActionEvent event) {
			ConnectionManager.getConnectionList().remove(list.getSelectionModel().getSelectedItem().getConnection());
		};
	}
	//End of intern class

	public static ObservableList<OptionConnectionListManager.ServerLine> getServerLineList() {
		ObservableList<OptionConnectionListManager.ServerLine> serverLineList = FXCollections.observableArrayList();
		for (Connection connection : ConnectionManager.getConnectionList()) {
			serverLineList.add(new OptionConnectionListManager.ServerLine(connection));
		}
		return serverLineList;
	}

}