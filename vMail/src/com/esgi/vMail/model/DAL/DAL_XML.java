package com.esgi.vMail.model.DAL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class DAL_XML {
	static HashMap<String, File> listFile = null;

	public static void saveDocument(String name) {
		try {
			Document document = DAL_XML.getDocument(name);
			Element root = document.getRootElement().detach();
			document = new Document(root);
			XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
			output.output(document, new FileOutputStream(listFile.get(name)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Element getRootNode(String name) {
		return DAL_XML.getDocument(name).getRootElement();
	}

	public static void getOrCreateFile(String name,String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		listFile.put(name, file);
	}

	public static Document getDocument(String name) {
		try {
			SAXBuilder parser = new SAXBuilder();
			return parser.build(listFile.get(name));
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Not an XML file or don't exist.");
		}
		return null;
	}
	/**
	 * May return all <code>Element</code> in XML except root <code>Element</code>
	 * @param propertyName
	 * @param fileName
	 * @return
	 */
	public static Element getElementByName(String propertyName, String fileName) {
		String[] nodeListName = propertyName.split("[.]");
		Element needle = DAL_XML.getRootNode(fileName);
		for (String string : nodeListName) {
			needle.getChild(string);
			if (needle == null) {
				System.err.println("Property " +propertyName+ " don't exist");
				return null;
			}
		}
		return needle;
	}
}
