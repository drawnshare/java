package com.esgi.vMail.model.DAO;

import java.io.File;
import java.util.ArrayList;

import org.jdom2.Element;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import com.esgi.vMail.control.PassCrypt;
import com.esgi.vMail.model.Configuration;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.DAL.DAL_XML;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager.NameMap;
import com.sun.xml.internal.bind.v2.runtime.Name;

public class DAO_Connection_XML extends DAO_XML{
	final static String fileName = "connections";
	static {
		switch (DAL_XML.getOrCreateFile(fileName, "connections.xml")) {
		case XMLState.CREATED:
			DAL_XML.setDocument4File(fileName, "connectionList");
		case XMLState.EXIST:
			DAL_XML.setXSD4FileByXSDName(fileName, fileName);
			break;
		default:
			break;
		}
	}
//	public static XMPPTCPConnectionConfiguration getServerByName(String name) {
//		return DAL_XML.getElementByName(name, fileName);
//	}
	public static ArrayList<Element> getAll() {
		Element serverList = DAL_XML.getRootNode(fileName);
		ArrayList<Element> elementList = new ArrayList<>();
		for (Element element : serverList.getChildren()) {
			elementList.add(element);
		}
		return elementList;
	}
	public static Configuration convertServer2Configuration(Element server) {
		PassCrypt passCrypt = new PassCrypt();
		passCrypt.setSecretKey(DAO_PassCrypt_XML.getKeyPass().getBytes());
		XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword(
						server.getChild("username").getText(),
						passCrypt.decryptInString(server.getChild("password").getText().getBytes()))
				.setServiceName(server.getChild("serviceName").getText())
				.setResource(server.getChild("resourceName").getText())
				.setHost(server.getChild("host").getText())
				.setPort(Integer.parseInt(server.getChild("port").getText()))
				.build();
		String name = server.getChild("name").getText();
		boolean isEnabled = Boolean.getBoolean(server.getChild("isEnable").getText());
		return new Configuration(configuration, name, isEnabled);
	}
	public static Element convertConnection2Server(Connection connection) {
		PassCrypt passCrypt = new PassCrypt();
		passCrypt.setSecretKey(DAO_PassCrypt_XML.getKeyPass().getBytes());
		Element connectionNode = new Element("connection");
		Element name = new Element("name");
		Element username = new Element("username");
		Element password = new Element("password");
		Element serviceName = new Element("serviceName");
		Element resourceName = new Element("resourceName");
		Element priority = new Element("priority");
		Element host = new Element("host");
		Element port = new Element("port");
		Element isEnable = new Element("isEnable");
		connectionNode.addContent(name);
		connectionNode.addContent(username);
		connectionNode.addContent(password);
		connectionNode.addContent(host);
		connectionNode.addContent(port);
		connectionNode.addContent(serviceName);
		connectionNode.addContent(resourceName);
		connectionNode.addContent(priority);
		connectionNode.addContent(isEnable);

		name.setText(connection.getName());
		username.setText(connection.getUser());
		password.setText(passCrypt.crypt(connection.getConfiguration().getPassword()).toString());
		serviceName.setText(connection.getServiceName());
		resourceName.setText(connection.getConfiguration().getResource());
		host.setText(connection.getConfiguration().getServiceName());
		port.setText(Integer.toString(connection.getPort()));
		isEnable.setText(Boolean.toString(connection.isEnabled()));

		return connectionNode;
	}

	public static void insertConnection(Connection connection) {
		DAL_XML.insertChild(DAL_XML.getRootNode(fileName).getName(), fileName, DAO_Connection_XML.convertConnection2Server(connection));	
	}

	public static ArrayList<Configuration> getAll2ConnectionConf() {
		ArrayList<Configuration> connectionList = new ArrayList<>();
		for (Element connectionNode : DAO_Connection_XML.getAll()) {
			connectionList.add(DAO_Connection_XML.convertServer2Configuration(connectionNode));
		}
		return connectionList;
	}
}
