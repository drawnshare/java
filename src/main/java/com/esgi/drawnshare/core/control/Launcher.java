package com.esgi.drawnshare.core.control;

import com.esgi.drawnshare.core.view.CoreView;
import custo.java.nio.Directory;
import custo.javax.module.model.ModuleFactory;
import custo.javax.module.processing.ModuleCluster;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		CoreView view = new CoreView(primaryStage);
		view.getWindowStage().show();
		try {
			ModuleProcessor processor = new ModuleProcessor(view.getController());
			processor.getFactory().loadAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
