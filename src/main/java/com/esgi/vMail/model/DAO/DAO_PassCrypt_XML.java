package com.esgi.vMail.model.DAO;

import com.esgi.vMail.control.PassCrypt;
import com.esgi.vMail.model.DAL.DAL_XML;
import com.esgi.vMail.model.DAO.DAO_XML.XMLState;
import org.jdom2.Element;

public class DAO_PassCrypt_XML {
	final static String fileName = "secret";
	private static Element rootNode;
	static {
		switch (DAL_XML.getOrCreateFile(fileName, fileName +".xml")) {
		case XMLState.CREATED:
			DAL_XML.setDocument4File(fileName, "hashPass");
			DAO_PassCrypt_XML.rootNode = DAL_XML.getRootNode(fileName);
			PassCrypt crypt = new PassCrypt();
			crypt.generateKey();
			DAO_PassCrypt_XML.setKeyPass(crypt.getSecretKeyInBytes());
			break;
		case XMLState.EXIST:
			DAO_PassCrypt_XML.rootNode = DAL_XML.getRootNode(fileName);
			break;
		default:
			break;
		}
	}
	public static byte[] getKeyPass() {
		byte[] key = new byte[rootNode.getChildren().size()];
		for (Element element : rootNode.getChildren()) {
			Byte.parseByte(element.getText());
		}
		return key;
	}
	public static void setKeyPass(byte[] key) {
		for (int idx = 0; idx < key.length; idx++) {
			Element keyPart = new Element("keyPart");
			keyPart.setAttribute("part", Integer.toString(idx));
			keyPart.setText(Byte.toString(key[idx]));
			rootNode.addContent(keyPart);
		}
		DAL_XML.saveDocument(fileName, rootNode);
	}
}
