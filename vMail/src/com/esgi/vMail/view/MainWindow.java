package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;

import javafx.stage.Stage;

public class MainWindow extends WindowBuilder{
	public MainWindow(Stage primaryStage) {
		super(primaryStage);
	}
	@Override
	String getStageTitle() {
		return LangManager.text("vMail.title");
	}
	@Override
	String getFXMLPath() {
		return "../view/vMail.fxml";
	}
}
