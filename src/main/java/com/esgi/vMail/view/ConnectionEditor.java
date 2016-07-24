package com.esgi.vMail.view;

import com.esgi.vMail.control.LangManager;
import com.esgi.vMail.view_controler.ConnectionEditorManager;

public class ConnectionEditor extends WindowBuilder {

	@Override
	Object loadController() {
		return new ConnectionEditorManager();
	}

	@Override
	String getStageTitle() {
		return LangManager.text("editor.connection.title");
	}

	@Override
	String getFXMLPath() {
		return "vMailConnectionEditor.fxml";
	}

}
