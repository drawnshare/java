package com.esgi.drawnshare.core.view;

import com.esgi.drawnshare.core.view_controller.CoreController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class CoreView {
	private Stage windowStage;
    private Pane rootLayout;
    private CoreController controller;

	/**
	 * @return the controller
	 */
	public CoreController getController() {
		return controller;
	}
	/**
	 * @return the windowStage
	 */
	public Stage getWindowStage() {
		return windowStage;
	}
	/**
	 * @param windowStage the windowStage to set
	 */
	public void setWindowStage(Stage windowStage) {
		this.windowStage = windowStage;
	}
	/**
	 * @return the rootLayout
	 */
	public Pane getRootLayout() {
		return rootLayout;
	}
	/**
	 * @param rootLayout the rootLayout to set
	 */
	public void setRootLayout(Pane rootLayout) {
		this.rootLayout = rootLayout;
	}
	/**
	 *
	 *
	 */
	@SuppressWarnings("WeakerAccess")
	public CoreView() {
		super();
		this.windowStage = new Stage();
		this.windowStage.setTitle(getStageTitle());
		this.loadFXML(this.getFXMLPath());
	}

	/**
	 * Constructor for main Window
	 * @param primaryStage The primary Stage
	 */
	public CoreView(Stage primaryStage) {
		super();
		this.windowStage = primaryStage;
		this.windowStage.setTitle(getStageTitle());
		this.loadFXML(this.getFXMLPath());
	}

	private void loadFXML(String fxmlPath) {
		try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CoreView.class.getResource(fxmlPath));
//            loader.setResources(LangManager.getBundle());
            this.rootLayout = loader.load();
            this.controller = loader.getController();
            this.controller.setView(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            windowStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	@SuppressWarnings("SameReturnValue")
	private String getStageTitle() {
		return "Draw'n'share";
	}
	@SuppressWarnings("SameReturnValue")
	private String getFXMLPath(){
		return "../view/CoreView.fxml";
	}
}