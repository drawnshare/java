package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;

public class OptionsWindow extends WindowBuilder {

	@Override
	Object loadController() {
		return null;
	}

	@Override
	String getStageTitle() {
		return LangManager.text("settings.title");
	}
	@Override
	String getFXMLPath() {
		return "vMailOptions.fxml";
	}

}
