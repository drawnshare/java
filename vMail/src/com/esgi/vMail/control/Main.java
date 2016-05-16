package com.esgi.vMail.control;

import java.io.IOException;

import com.esgi.vMail.view.MainWindow;

import javafx.application.Application;
import javafx.stage.Stage;

//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		MainWindow mainWindow = new MainWindow(primaryStage);
		mainWindow.getWindowStage().show();
//		System.out.println(mainWindow.getControler());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
