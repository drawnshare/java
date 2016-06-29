package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;

import com.sun.org.apache.bcel.internal.util.ClassLoader;
import javafx.stage.Stage;

import java.net.URISyntaxException;

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
		return "vMail.fxml";
	}
}
