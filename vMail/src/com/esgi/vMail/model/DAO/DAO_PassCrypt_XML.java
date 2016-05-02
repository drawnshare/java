package com.esgi.vMail.model.DAO;

import org.jdom2.Element;

import com.esgi.vMail.model.DAL.DAL_XML;

public class DAO_PassCrypt_XML {
	final static String fileName = "secret";
	static {
		DAL_XML.getOrCreateFile(fileName, "secret.xml");
	}
	public static String getKeyPass() {
		return DAL_XML.getElementByName("key", fileName).getText();
	}
}
