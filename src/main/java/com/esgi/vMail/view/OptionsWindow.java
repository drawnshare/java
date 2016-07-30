package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.view_controler.ListOptionsManager;

public class OptionsWindow extends WindowBuilder {

	@Override
	Object loadController() {
		return new ListOptionsManager();
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
