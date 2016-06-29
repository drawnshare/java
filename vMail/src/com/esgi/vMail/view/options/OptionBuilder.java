package com.esgi.vMail.view.options;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class OptionBuilder {
	final static String IMAGE_EXTENSION = ".png";
	final static String KEY_HEADER = "settings.list.";
	final static String RESOURCE_DEFAULT_PATH = "/com/esgi/vMail/resource/default/";
	Pane paneContainer;
	Node titleObject;

	abstract String getOptionName();

	public OptionBuilder() {
		this.paneContainer = this.makeOptionPane();
	}

	abstract Pane makeOptionPane();

	public Node getTitleObject() {
		return titleObject;
	}

	public Pane getPaneContainer() {
		return paneContainer;
	}

	public void iconAndTitle(String imagePath, String title) {
		iconAndTitle(new Image(imagePath), new SimpleStringProperty(title));
	}

	public void iconAndTitle(Image image, StringProperty title) {
		VBox boxListOption = new VBox();
		boxListOption.setAlignment(Pos.CENTER);
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(64);
		imageView.setFitWidth(64);
		boxListOption.getChildren().add(imageView);
		boxListOption.getChildren().add(new Label(title.getValue()));
		this.titleObject = boxListOption;
	}
}
