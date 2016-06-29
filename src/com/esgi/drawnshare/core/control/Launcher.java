package com.esgi.drawnshare.core.control;

import com.esgi.drawnshare.core.view.CoreView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		CoreView view = new CoreView(primaryStage);
		view.getWindowStage().show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
