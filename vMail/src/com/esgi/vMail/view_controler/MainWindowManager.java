package com.esgi.vMail.view_controler;

import com.esgi.vMail.view.OptionsWindow;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainWindowManager extends ManagerBuilder {

	@FXML
	MenuItem menuIOption;

	@FXML
	public void callOptionPane() {
		OptionsWindow optionsWindow = new OptionsWindow();
		optionsWindow.getWindowStage().setAlwaysOnTop(true);
		optionsWindow.getWindowStage().show();
	}
}
