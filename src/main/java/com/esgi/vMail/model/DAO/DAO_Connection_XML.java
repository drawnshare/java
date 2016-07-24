package com.esgi.vMail.model.DAO;

import com.esgi.vMail.control.PassCrypt;
import com.esgi.vMail.model.Configuration;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.DAL.DAL_XML;
import org.jdom2.Element;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.ArrayList;

public class DAO_Connection_XML extends DAO_XML{
	private final static String fileName = "connections";
	private static Element rootNode;
	static {
		switch (DAL_XML.getOrCreateFile(fileName, fileName +".xml")) {
		case XMLState.CREATED:
			DAL_XML.setDocument4File(fileName, "connectionList");
		case XMLState.EXIST:
			/*DAL_XML.setXSD4FileByXSDName(fileName, fileName);*/
			DAO_Connection_XML.rootNode = DAL_XML.getRootNode(fileName);
			break;
		default:
			break;
		}
	}

	public static boolean deleteConnection(String name) {
		boolean isOneOrManyDeleted = false;
		for (Element connection : DAO_Connection_XML.getAll()) {
			if (connection.getChild("name").getText().equals(name)) {
				System.out.println("DAO_Connection_XML.deleteConnection() name = "+ connection.getChild("name").getText());
				connection.detach();
				connection = null;
				DAL_XML.saveDocument(fileName, rootNode);
				isOneOrManyDeleted = true;
			}
		}
		return isOneOrManyDeleted;
	}

	public static ArrayList<Element> getAll() {
		Element serverList = rootNode;
		ArrayList<Element> elementList = new ArrayList<>();
		for (Element element : serverList.getChildren()) {
			elementList.add(element);
		}
		return elementList;
	}
	public static Configuration convertServer2Configuration(Element server) {
		PassCrypt passCrypt = new PassCrypt();
		passCrypt.setSecretKey(DAO_PassCrypt_XML.getKeyPass());
		XMPPTCPConnectionConfiguration configuration = null;
		try {
			configuration = XMPPTCPConnectionConfiguration.builder()
					.setUsernameAndPassword(
							server.getChild("username").getText(),
							passCrypt.decryptB64(server.getChild("password").getText()))
//				.setServiceName(server.getChild("serviceName").getText())
					.setXmppDomain(JidCreate.domainBareFrom(server.getChild("host").getText()))
					//TODO A remplacer tout de meme
					.setSecurityMode(SecurityMode.disabled)
					.setHostnameVerifier(new HostnameVerifier() {
						@Override
						public boolean verify(String hostname, SSLSession session) {
							// TODO Auto-generated method stub
							return true;
						}
					})
					.setResource(server.getChild("resourceName").getText())
					.setHost(server.getChild("host").getText())
					.setPort(Integer.parseInt(server.getChild("port").getText()))
					.build();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmppStringprepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name = server.getChild("name").getText();
		int priority = Integer.parseInt(server.getChild("priority").getText());
		boolean isEnabled = Boolean.valueOf(server.getChild("isEnable").getText().trim().toLowerCase());
		System.out.println("DAO_Connection_XML.convertServer2Configuration() isEnabled = "+isEnabled+ " | Elt isEnabled = "+ server.getChild("isEnable").getText());
		return new Configuration(configuration, name, priority , isEnabled);
	}
	public static Element convertConnection2Server(Connection connection) {
		PassCrypt passCrypt = new PassCrypt();
		passCrypt.setSecretKey(DAO_PassCrypt_XML.getKeyPass());
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
		System.out.println("USER = "+ connection.getConfiguration().getUsername());
		username.setText(connection.getConfiguration().getUsername().toString());
		System.out.println(passCrypt.encryptB64(connection.getConfiguration().getPassword()));
		password.setText(passCrypt.encryptB64(connection.getConfiguration().getPassword()));
		serviceName.setText(connection.getServiceName().toString());
		resourceName.setText(connection.getConfiguration().getResource().toString());
		host.setText(connection.getHost());
		System.out.println("port = "+connection.getPort());
		port.setText(Integer.toString(connection.getPort()));
		System.out.println("priority = "+ connection.getPriority());
		priority.setText(Integer.toString(connection.getPriority()));
		isEnable.setText(Boolean.toString(connection.isEnabled()));

		return connectionNode;
	}

	public static void insertConnection(Connection connection) {
		DAL_XML.insertChild(rootNode.getName(), rootNode, DAO_Connection_XML.convertConnection2Server(connection));
		DAL_XML.saveDocument(fileName, rootNode);
	}

	public static ArrayList<Configuration> getAll2ConnectionConf() {
		ArrayList<Configuration> connectionList = new ArrayList<>();
		for (Element connectionNode : DAO_Connection_XML.getAll()) {
			connectionList.add(DAO_Connection_XML.convertServer2Configuration(connectionNode));
		}
		return connectionList;
	}
}
