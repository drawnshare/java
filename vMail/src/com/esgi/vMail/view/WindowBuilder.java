package com.esgi.vMail.view;

import java.io.IOException;

import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.control.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

abstract class WindowBuilder {
	protected Stage windowStage;
    protected Pane rootLayout;
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
	public WindowBuilder() {
		super();
		this.windowStage = new Stage();
		this.windowStage.setTitle(getStageTitle());
		this.loadFXML(this.getFXMLPath());
	}

	/**
	 * Constructor for main Window
	 * @param primaryStage
	 */
	public WindowBuilder(Stage primaryStage) {
		super();
		this.windowStage = primaryStage;
		this.windowStage.setTitle(getStageTitle());
		this.loadFXML(this.getFXMLPath());
	}

	private void loadFXML(String fxmlPath) {
		try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(fxmlPath));
            loader.setResources(LangManager.getBundle());
            rootLayout = (Pane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            windowStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	abstract String getStageTitle();
	abstract String getFXMLPath();
}
