package com.esgi.vMail.view.options;

import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.view.option_controler.OptionConnectionListManager;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OptionConnectionList extends OptionBuilder {

	public OptionConnectionList() {
		super();
		this.iconAndTitle(RESOURCE_PATH + getOptionName() + IMAGE_EXTENSION ,LangManager.text(KEY_HEADER + getOptionName()));
	}

	@Override
	String getOptionName() {
		return "connections";
	}

	@Override
	Pane makeOptionPane() {
		BorderPane optionPane = new BorderPane();
		ListView<OptionConnectionListManager.ServerLine> serverList = new ListView<>(OptionConnectionListManager.getServerLineList());
		optionPane.setCenter(serverList);
		Button btnAdd = new Button(LangManager.text("settings.list.connections.add"));
		Button btnMod = new Button(LangManager.text("settings.list.connections.mod"));
		Button btnDel = new Button(LangManager.text("settings.list.connections.del"));
		VBox btnList = new VBox(btnAdd, btnMod, btnDel);
		optionPane.setRight(btnList);
		return optionPane;
	}
}

