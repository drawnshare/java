package com.esgi.vMail.model.DAL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.esgi.vMail.control.Main;

public class DAL_XML {
	public static class XMLAndXSDFile {
		File xmlFile;
		File xsdFile;
		public XMLAndXSDFile(File xmlFile) {
			this.xmlFile = xmlFile;
		}
		/**
		 * @return the xmlFile
		 */
		public File getXmlFile() {
			return xmlFile;
		}
		/**
		 * @param xmlFile the xmlFile to set
		 */
		public void setXmlFile(File xmlFile) {
			this.xmlFile = xmlFile;
		}
		/**
		 * @return the xsdFile
		 */
		public File getXsdFile() {
			return xsdFile;
		}
		/**
		 * @param xsdFile the xsdFile to set
		 */
		public void setXsdFile(File xsdFile) {
			this.xsdFile = xsdFile;
		}
	}
	static HashMap<String, XMLAndXSDFile> listFile;

	static {
		listFile = new HashMap<>();
	}

	public static void saveDocument(String name, Element root) {
		try {

			Document document = new Document(root.detach());
			XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
			output.output(document, new FileOutputStream(listFile.get(name).getXmlFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Element getRootNode(String name) {
		return DAL_XML.getDocument(name).getRootElement();
	}
	/**
	 *
	 * @param name
	 * @param path
	 * @return return -1 if fail, 1 if create, 0 if already exist
	 */
	public static int getOrCreateFile(String name,String path) {
		File file = new File(path);
		int state = -1;
		if (!file.exists()) {
			try {
				state = 1;
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			state = 0;
		}
		listFile.put(name, new XMLAndXSDFile(file));
		return state;
	}

	public static Document getDocument(String name) {
		try {
			SAXBuilder parser = new SAXBuilder();
			/*if (listFile.get(name).getXsdFile() != null) {
				XMLReaderJDOMFactory schemaXSD = new XMLReaderXSDFactory(listFile.get(name).getXsdFile());
				parser = new SAXBuilder(schemaXSD);
			} else {
				parser = new SAXBuilder();
			}*/
			return parser.build(listFile.get(name).getXmlFile());
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
//			System.err.println("Not an XML file or don't exist.");
		}
		return null;
	}

	public static void setDocument4File(String name, String rootNodeName) {
		try {
			Element root = new Element(rootNodeName);
			Document document = new Document(root);
			XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
			output.output(document, new FileOutputStream(listFile.get(name).getXmlFile()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * May return all <code>Element</code> in XML. Leave blank if you want root <code>Element</code>
	 * @param propertyName
	 * @param root
	 * @return
	 */
	public static Element getElementByName(String propertyName, Element root) {
		String[] nodeListName = propertyName.split("[.]");
		Element needle = root;
		for (String string : nodeListName) {
			if (string == root.getName()) {
				continue;
			}
			needle = needle.getChild(string);
			if (needle == null) {
				System.err.println("Element " +propertyName+ " don't exist");
				return null;
			}
		}
		return needle;
	}
	public static void insertChild(String propertyName, Element root, Element node) {
		Element parent = DAL_XML.getElementByName(propertyName, root);
		parent.addContent(node);
	}

	public static void setXSD4FileByXSDName(String fileName, String xsdName) {
		DAL_XML.setXSD4File(fileName, DAL_XML.class.getResource(String.format("%s.xsd", xsdName)).getFile());
	}

	public static void setXSD4File(String fileName, String path2XSD) {
		listFile.get(fileName).setXsdFile(new File(path2XSD));
	}
}
