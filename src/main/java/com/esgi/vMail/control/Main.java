package com.esgi.vMail.control;

import com.esgi.vMail.view.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		ConnectionManager.init();
		MainWindow mainWindow = new MainWindow(primaryStage);
		mainWindow.getWindowStage().show();	
	}

	public static void main(String[] args) {
		launch(args);
	}
}
