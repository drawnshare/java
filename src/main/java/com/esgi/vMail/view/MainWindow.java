package com.esgi.vMail.view;

import com.esgi.ModuleAnnotations.model.InitView;
import com.esgi.ModuleAnnotations.model.PluginView;
import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.view_controler.MainWindowManager;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@PluginView(viewType = PluginView.ViewType.RIGHT)
public class MainWindow extends WindowBuilder{
	public MainWindow(Stage primaryStage) {
		super(primaryStage);
	}

	@Override
	Object loadController() {
		return new MainWindowManager();
	}
	public MainWindow() {}
	@Override
	String getStageTitle() {
		return LangManager.text("vMail.title");
	}
	@Override
	String getFXMLPath() {
		return "vMail.fxml";
	}
	@InitView
	@Override
	public Pane getRootLayout() {
		return super.getRootLayout();
	}
}
