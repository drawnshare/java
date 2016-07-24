package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.view_controler.ManagerBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class WindowBuilder {
	protected Stage windowStage;
    protected Pane rootLayout;
    protected ManagerBuilder controler;

	/**
	 * @return the controler
	 */
	public Object getControler() {
		return controler;
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
            loader.setLocation(WindowBuilder.class.getClassLoader().getResource(fxmlPath));
            loader.setResources(LangManager.getBundle());
			if (loadController() != null) {
			    loader.setController(loadController());
			}
			this.rootLayout = loader.load();
            this.controler = loader.getController();
            this.controler.setWindowBuilder(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            windowStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	abstract Object loadController();
	abstract String getStageTitle();
	abstract String getFXMLPath();
}
