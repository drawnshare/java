package com.esgi.vMail.view_controler;

import com.esgi.vMail.view.options.OptionBuilder;
import com.esgi.vMail.view.options.OptionConnectionList;
import com.esgi.vMail.view_controler.event.EventOptionDisplayDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;

public class ListOptionsManager extends ManagerBuilder {
	/**
	 * Setting list key header
	 */


	/**
	 * Option list in option panel
	 */
	@FXML
	private ListView<Node> listOptions;
	/**
	 * Option body to modify
	 */
	@FXML
	private AnchorPane optionBody;
	/**
	 *
	 */
	private boolean isOptionPaneVisible = false;

	@FXML
	public void initialize() {
		final ObservableList<OptionBuilder> items = FXCollections.observableArrayList (
				//new OptionDisplay(),
				(OptionBuilder) new OptionConnectionList()
		);

		ObservableList<Node> nodeList = FXCollections.observableArrayList();
		for (OptionBuilder option : items) {
			option.getTitleObject().setOnMouseClicked(new EventOptionDisplayDetails(optionBody, option.getPaneContainer()));
			//option.getTitleObject().setOnScroll(new EventOptionDisplayDetails(optionBody, option.getPaneContainer()));
			//option.getTitleObject().addEventHandler(ActionEvent.ANY, new EventOptionDisplayDetails(optionBody, option.getPaneContainer()));
			nodeList.add(option.getTitleObject());
		}
		listOptions.setItems(nodeList);
		listOptions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		//listOptions.getSelectionModel().select(0);
	}

	@FXML
	public void callOptionPane() {
		if (isOptionPaneVisible) {

		}
	}
}