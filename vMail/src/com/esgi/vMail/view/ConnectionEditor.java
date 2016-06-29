package com.esgi.vMail.view;

import com.esgi.vMail.view.WindowBuilder;
import com.esgi.vMail.control.LangManager;

public class ConnectionEditor extends WindowBuilder {

	@Override
	String getStageTitle() {
		return LangManager.text("editor.connection.title");
	}

	@Override
	String getFXMLPath() {
		return "../view/vMailConnectionEditor.fxml";
	}

}
