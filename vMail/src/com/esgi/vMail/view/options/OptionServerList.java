package com.esgi.vMail.view.options;

import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.view.option_controler.OptionServerListManager;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class OptionServerList extends OptionBuilder {

	public OptionServerList() {
		super();
		this.iconAndTitle(RESOURCE_PATH + getOptionName() + IMAGE_EXTENSION ,LangManager.text(KEY_HEADER + getOptionName()));
	}

	@Override
	String getOptionName() {
		return "servers";
	}

	@Override
	Pane makeOptionPane() {
		BorderPane optionPane = new BorderPane();
		ListView<OptionServerListManager.ServerLine> serverList = new ListView<>(OptionServerListManager.getServerLineList());
		optionPane.setCenter(serverList);
		Button btnAdd = new Button(LangManager.text("settings.list.servers.add"));
		Button btnMod = new Button(LangManager.text("settings.list.servers.mod"));
		Button btnDel = new Button(LangManager.text("settings.list.servers.del"));
		VBox btnList = new VBox(btnAdd, btnMod, btnDel);
		optionPane.setRight(btnList);
		return optionPane;
	}
}

