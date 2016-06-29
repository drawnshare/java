package com.esgi.drawnshare.core.view_controller;

import com.esgi.drawnshare.core.view.CoreView;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.util.Map;

public class CoreController {
	private CoreView view;
	
	@FXML
	private BorderPane moduleLayout;

	public void setView(CoreView view) {
		this.view = view;
	}

	public CoreView getView() {
		return view;
	}
	
	public void setLeftPane(Pane pane, Map<String,Double> map) {
		this.moduleLayout.setLeft(this.determineLayout(pane, map));
	}

	public void setRightPane(Pane pane, Map<String, Double> map) {
		this.moduleLayout.setRight(this.determineLayout(pane, map));
	}

	public void setBottomPane(Pane pane, Map<String, Double> map) {
		this.moduleLayout.setBottom(this.determineLayout(pane, map));
	}

	private Pane determineLayout(Pane pane, Map<String, Double> map) {
		if (map.containsKey("MaxHeight")) {
			pane.setMaxHeight(map.get("MaxHeight"));
		}
		if (map.containsKey("MinHeight")) {
			pane.setMinHeight(map.get("MinHeight"));
		}
		if (map.containsKey("PrefHeight")) {
			pane.setPrefHeight(map.get("PrefHeight"));
		}
		if (map.containsKey("MaxWidth")) {
			pane.setMaxHeight(map.get("MaxWidth"));
		}
		if (map.containsKey("MinWidth")) {
			pane.setMinHeight(map.get("MinWidth"));
		}
		if (map.containsKey("PrefWidth")) {
			pane.setPrefHeight(map.get("PrefWidth"));
		}
		return pane;
	}
}
