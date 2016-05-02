package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;

public class OptionsWindow extends WindowBuilder {

	@Override
	String getStageTitle() {
		return LangManager.text("settings.title");
	}
	@Override
	String getFXMLPath() {
		return "../view/vMailOptions.fxml";
	}

}
