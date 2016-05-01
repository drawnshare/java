package com.esgi.vMail.model.DAO;

import org.jdom2.Element;

import com.esgi.vMail.model.DAL.DAL_XML;

public class DAO_Preference_XML {
	final static String fileName = "preferences";
	static {
		DAL_XML.getOrCreateFile(fileName, "preferences.xml");
	}
	public static Element getPropertyByName(String name) {
		return DAL_XML.getElementByName(name, fileName);
	}
}
