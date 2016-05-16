package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;

public class ConnectionEditor extends WindowBuilder {

	@Override
	String getStageTitle() {
		// TODO Auto-generated method stub
		return LangManager.text("editor.connection.title");
	}

	@Override
	String getFXMLPath() {
		return "../view/vMailConnectionEditor.fxml";
	}

}
