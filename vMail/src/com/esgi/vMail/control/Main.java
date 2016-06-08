package com.esgi.vMail.control;

import java.io.IOException;

import com.esgi.vMail.view.MainWindow;
import com.esgi.vMail.view.StatusRound;
import com.esgi.vMail.view.StatusRound.Status;
import com.esgi.vMail.view_controler.MainWindowManager;

import javafx.application.Application;
import javafx.stage.Stage;

//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		ConnectionManager.setFxThread(Thread.currentThread());
		ConnectionManager.init();
		MainWindow mainWindow = new MainWindow(primaryStage);
		mainWindow.getWindowStage().show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
