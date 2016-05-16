package com.esgi.vMail.control.event;

import java.util.List;
import java.util.function.Predicate;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.DAO.DAO_Connection_XML;
import com.esgi.vMail.view.option_controler.OptionConnectionListManager.ServerLine;

import javafx.collections.ListChangeListener;

public class EventOnConnectionListChange implements ListChangeListener<Connection> {

	@Override
	public void onChanged(javafx.collections.ListChangeListener.Change<? extends Connection> change) {
		while (change.next()) {
			System.out.println(change);
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
				Predicate<ServerLine> predicate = (serverLine) -> {return serverLine.getConnection().equals(connection);};
				ConnectionManager.getDisplayedConnectionList().removeIf(predicate);
			}
		}
	}

	private void handleAdded(List<? extends Connection> addedSubList) {
		for (Connection connection : addedSubList) {
			DAO_Connection_XML.insertConnection(connection);
			if (ConnectionManager.getDisplayedConnectionList() != null) {
				ConnectionManager.getDisplayedConnectionList().add(new ServerLine(connection));
			}
		}
	}

}
