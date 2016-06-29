package com.esgi.vMail.view_controler;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import com.esgi.vMail.control.ConnectionManager;
import com.esgi.vMail.model.Configuration;
import com.esgi.vMail.model.Connection;
import com.esgi.vMail.model.DAO.DAO_Connection_XML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class ConnectionEditorManager extends ManagerBuilder {

	private boolean is4Modifications = false;

	@FXML
	private TextField txtBName;
	@FXML
	private TextField txtBUser;

	@FXML
	private PasswordField txtBPassword;

	@FXML
	private TextField txtBServerAddress;

	@FXML
	private TextField txtBPort;

	@FXML
	private TextField txtBResourceName;

	@FXML
	private Slider sliderPriority;

	@FXML
	private Button btnConnectionCheck;

	@FXML
	private ProgressIndicator progressConnectionStatus;

	@FXML
	private Label labelConnectionStatus;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnReset;

	@FXML
	private Button btnAbort;



	/**
	 * @param is4Modifications the is4Modifications to set
	 */
	public void setIs4Modifications(boolean is4Modifications) {
		this.is4Modifications = is4Modifications;
	}

	/**
	 * @return the Name
	 */
	public String getName() {
		return txtBName.getText();
	}

	/**
	 * @param Name the Name to set
	 */
	public void setName(String name) {
		this.txtBName.setText(name);
	}

	/**
	 * @return the User
	 */
	public String getUser() {
		return txtBUser.getText();
	}

	/**
	 * @param User the User to set
	 */
	public void setUser(String user) {
		this.txtBUser.setText(user);
	}

	/**
	 * @return the Password
	 */
	public String getPassword() {
		return txtBPassword.getText();
	}

	/**
	 * @param Password the Password to set
	 */
	public void setPassword(String password) {
		this.txtBPassword.setText(password);
	}

	/**
	 * @return the ServerAddress
	 */
	public String getServerAddress() {
		return txtBServerAddress.getText();
	}

	/**
	 * @param ServerAddress the ServerAddress to set
	 */
	public void setServerAddress(String serverAddress) {
		this.txtBServerAddress.setText(serverAddress);
	}

	/**
	 * @return the Port
	 */
	public int getPort() {
		return Integer.parseInt(txtBPort.getText());
	}

	/**
	 * @param Port the Port to set
	 */
	public void setPort(int port) {
		this.txtBPort.setText(Integer.toString(port));
	}

	/**
	 * @return the ResourceName
	 */
	public String getResourceName() {
		return txtBResourceName.getText();
	}

	/**
	 * @param ResourceName the ResourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.txtBResourceName.setText(resourceName);
	}

	/**
	 * @return the Priority
	 */
	public double getPriorityValue() {
		return sliderPriority.getValue();
	}

	/**
	 * @param Priority the Priority to set
	 */
	public void setPriorityValue(double priorityValue) {
		this.sliderPriority.setValue(priorityValue);
	}

	/**
	 * @return the ConnectionStatus
	 */
	public String getConnectionStatus() {
		return labelConnectionStatus.getText();
	}

	/**
	 * @param ConnectionStatus the ConnectionStatus to set
	 */
	public void setLabelConnectionStatus(String connectionStatus) {
		this.labelConnectionStatus.setText(connectionStatus);
	}

	/**
	 * @return the txtBName
	 */
	public TextField getTxtBName() {
		return txtBName;
	}

	/**
	 * @return the txtBUser
	 */
	public TextField getTxtBUser() {
		return txtBUser;
	}

	/**
	 * @return the txtBPassword
	 */
	public PasswordField getTxtBPassword() {
		return txtBPassword;
	}

	/**
	 * @return the txtBServerAddress
	 */
	public TextField getTxtBServerAddress() {
		return txtBServerAddress;
	}

	/**
	 * @return the txtBPort
	 */
	public TextField getTxtBPort() {
		return txtBPort;
	}

	/**
	 * @return the txtBResourceName
	 */
	public TextField getTxtBResourceName() {
		return txtBResourceName;
	}

	/**
	 * @return the sliderPriority
	 */
	public Slider getSliderPriority() {
		return sliderPriority;
	}

	/**
	 * @return the btnConnectionCheck
	 */
	public Button getBtnConnectionCheck() {
		return btnConnectionCheck;
	}

	/**
	 * @return the progressConnectionStatus
	 */
	public ProgressIndicator getProgressConnectionStatus() {
		return progressConnectionStatus;
	}

	/**
	 * @return the labelConnectionStatus
	 */
	public Label getLabelConnectionStatus() {
		return labelConnectionStatus;
	}

	/**
	 * @return the btnSave
	 */
	public Button getBtnSave() {
		return btnSave;
	}

	/**
	 * @return the btnReset
	 */
	public Button getBtnReset() {
		return btnReset;
	}

	/**
	 * @return the btnAbort
	 */
	public Button getBtnAbort() {
		return btnAbort;
	}

	@FXML
	public void checkConnection() {
		if (this.checkRequireField()) {
			this.progressConnectionStatus.setVisible(true);
			boolean isConfValid = false;
			try {
				isConfValid = ConnectionManager.isConnectionValid(new Connection(new Configuration(
						XMPPTCPConnectionConfiguration.builder()
						.setUsernameAndPassword(txtBUser.getText(), txtBPassword.getText())
						.setXmppDomain(JidCreate.domainBareFrom(txtBServerAddress.getText()))
						.setHost(txtBServerAddress.getText())
						.setPort(Integer.parseInt(txtBPort.getText()))
						.setHostnameVerifier(new HostnameVerifier() {
							@Override
							public boolean verify(String hostname, SSLSession session) {
								return true;
							}
						})
						.build(),
						txtBName.getText()
				)));
			} catch (NumberFormatException | XmppStringprepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (isConfValid) {
				this.labelConnectionStatus.setText("OK");
				this.labelConnectionStatus.setStyle("-fx-color: green;");
			} else {
				this.labelConnectionStatus.setText("Fail");
				this.labelConnectionStatus.setStyle("-fx-color: red;");
			}
			this.progressConnectionStatus.setVisible(false);
			this.labelConnectionStatus.setVisible(true);
		}
	}

	@FXML
	public void saveConnection() {
		if (this.checkRequireField()) {
			XMPPTCPConnectionConfiguration xmppConfiguration = null;
			try {
				xmppConfiguration = XMPPTCPConnectionConfiguration.builder()
						.setUsernameAndPassword(txtBUser.getText(), txtBPassword.getText())
						.setXmppDomain(JidCreate.domainBareFrom(txtBServerAddress.getText()))
						.setHost(txtBServerAddress.getText())
						.setPort(Integer.parseInt(txtBPort.getText()))
						.build();
			} catch (NumberFormatException | XmppStringprepException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name;
			if (txtBName.getText().trim().equals("")) {
				name = txtBUser.getText();
			} else {
				name = txtBName.getText();
			}
			Configuration configuration = new Configuration(xmppConfiguration, name);
			Connection connection = new Connection(configuration);
			connection.setPriority((int)sliderPriority.getValue());
			connection.setEnabled(true);
			if (ConnectionManager.getConnectionList().filtered((connect) -> {return connect.getName().equals(txtBName.getText());}).isEmpty()) {
				ConnectionManager.getConnectionList().add(connection);
			} else {
				if (is4Modifications) {
					ConnectionManager.getConnectionList().set(ConnectionManager.getConnectionList().indexOf(ConnectionManager.getConnectionList().filtered((connect) -> {return connect.getName().equals(txtBName.getText());}).get(0)), connection);
				}
			}

			this.abort();
		}
	}

	@FXML
	public void resetConnection() {
		TextField[] txtBList = {
				txtBUser,
				txtBPassword,
				txtBServerAddress,
				txtBName
		};
		for (TextField textField : txtBList) {
			textField.setText("");
		}
		txtBPort.setText("5222");
		txtBResourceName.setText("vMail-xxxxxx");
		sliderPriority.setValue(0);
	}

	public boolean checkRequireField() {
		boolean isValid = true;
		TextField[] txtBList = {
				txtBUser,
				txtBPassword,
				txtBServerAddress,
				txtBPort
		};
		for (TextField textField : txtBList) {
			if (textField.getText() == null && textField.getText().trim().isEmpty()) {
				textField.setStyle("-fx-border-color: red;");
				textField.setPromptText("Require");
				isValid = false;
			} else {
				textField.setStyle("-fx-border-color: none;");
			}
		}
		System.out.println(isValid);
		return isValid;
	}

	@FXML
	public void abort() {
		this.windowBuilder.getWindowStage().close();
	}
}
