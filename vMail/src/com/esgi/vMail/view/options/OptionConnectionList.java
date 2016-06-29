package com.esgi.vMail.view.options;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.view.option_controler.OptionConnectionListManager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OptionConnectionList extends OptionBuilder {

	public OptionConnectionList() {
		super();
		this.iconAndTitle(RESOURCE_DEFAULT_PATH + getOptionName() + IMAGE_EXTENSION ,LangManager.text(KEY_HEADER + getOptionName()));
	}

	@Override
	String getOptionName() {
		return "connections";
	}

	@Override
	Pane makeOptionPane() {
		BorderPane optionPane = new BorderPane();
		ListView<OptionConnectionListManager.ServerLine> serverList = new ListView<>(OptionConnectionListManager.getServerLineList());
		/**
		 * Fill the displayServerList for modification purpose
		 */
		ConnectionManager.setDisplayedConnectionList(serverList.getItems());
		optionPane.setCenter(serverList);
		Button btnAdd = new Button(LangManager.text("settings.list.connections.add"));
		btnAdd.setPrefWidth(Double.MAX_VALUE);
		btnAdd.setOnAction(new OptionConnectionListManager.DisplayConnectionEditor());
		Button btnMod = new Button(LangManager.text("settings.list.connections.mod"));
		btnMod.setPrefWidth(Double.MAX_VALUE);
		btnMod.setOnAction(new OptionConnectionListManager.DisplayConnectionEditor(serverList));
		Button btnDel = new Button(LangManager.text("settings.list.connections.del"));
		btnDel.setPrefWidth(Double.MAX_VALUE);
		btnDel.setOnAction(new OptionConnectionListManager.DeleteConnection(serverList));
		VBox btnList = new VBox(btnAdd, btnMod, btnDel);
		btnList.setMaxWidth(128);
		btnList.setSpacing(5);
		btnList.setPadding(new Insets(5));
		optionPane.setRight(btnList);
		return optionPane;
	}
}

