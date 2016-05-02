package com.esgi.vMail.model.DAO;

import java.util.ArrayList;

import org.jdom2.Element;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import com.esgi.vMail.control.PassCrypt;
import com.esgi.vMail.model.Configuration;
import com.esgi.vMail.model.DAL.DAL_XML;
import com.sun.xml.internal.bind.v2.runtime.Name;

public class DAO_Connection_XML {
	final static String fileName = "servers";
	static {
		DAL_XML.getOrCreateFile(fileName, "servers.xml");
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
				.setHost(server.getChild("host").getText())
				.setPort(Integer.parseInt(server.getChild("port").getText()))
				.build();
		String name = server.getChild("name").getText();
		boolean isEnabled = Boolean.getBoolean(server.getChild("isEnable").getText());
		return new Configuration(configuration, name, isEnabled);
	}

	public static ArrayList<Configuration> getAll2ConnectionConf() {
		ArrayList<Configuration> connectionList = new ArrayList<>();
		for (Element connectionNode : DAO_Connection_XML.getAll()) {
			connectionList.add(DAO_Connection_XML.convertServer2Configuration(connectionNode));
		}
		return connectionList;
	}
}
