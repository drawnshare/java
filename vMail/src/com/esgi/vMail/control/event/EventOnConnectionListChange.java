package com.esgi.vMail.control.event;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.DAO.DAO_Connection_XML;
import com.esgi.vMail.view.option_controler.OptionConnectionListManager.ServerLine;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;

public class EventOnConnectionListChange implements ListChangeListener<Connection> {

	@Override
	public void onChanged(javafx.collections.ListChangeListener.Change<? extends Connection> change) {
		while (change.next()) {

			//Verify connection

			System.out.println(change);
			if (change.wasAdded()) {
				for (Connection connection : change.getAddedSubList()) {
					try {
						connection.connect();
					} catch (SmackException | IOException | XMPPException | InterruptedException e) {
						e.printStackTrace();
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle(LangManager.text("error.connection.title"));
						alert.setHeaderText(new StringBuilder()
								.append(LangManager.text("error.connection.header"))
								.append(" ")
								.append(connection.getName())
								.append(" .")
								.append(LangManager.text("error.connection.header.end"))
								.toString()
						);
						alert.show();
					}
				}
				
			}

			if (change.wasReplaced()) {
				this.handleRemoved(change.getRemoved());
				this.handleAdded(change.getAddedSubList());
			} else if (change.wasRemoved()) {
				this.handleRemoved(change.getRemoved());
			} else if (change.wasAdded()) {
				this.handleAdded(change.getAddedSubList());
			}
		}
	}

	private void handleRemoved(List<? extends Connection> removed) {
		for (Connection connection : removed) {
			DAO_Connection_XML.deleteConnection(connection.getName());
			if (ConnectionManager.getDisplayedConnectionList() != null) {
				ConnectionManager.getDisplayedConnectionList().removeIf(new Predicate<ServerLine>() {
					@Override
					public boolean test(ServerLine serverLine) {
						return serverLine.getConnection().equals(connection);
					}
				});
			}
		}
	}

	private void handleAdded(List<? extends Connection> addedSubList) {
		for (Connection connection : addedSubList) {
			DAO_Connection_XML.insertConnection(connection);
			if (connection.isEnabled()) {
				try {
					connection.connect();
					connection.login();
				} catch (SmackException | IOException | XMPPException | InterruptedException e) {
					// TODO Auto-generated catch block
					connection.getStatusMsg().set(e.getMessage());
				}
			}
			if (ConnectionManager.getDisplayedConnectionList() != null) {
				ConnectionManager.getDisplayedConnectionList().add(new ServerLine(connection));
			}
		}
	}

}
