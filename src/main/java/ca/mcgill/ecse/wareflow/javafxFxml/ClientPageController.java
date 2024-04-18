package ca.mcgill.ecse.wareflow.javafxFxml;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

import java.io.IOException;

import ca.mcgill.ecse.wareflow.controller.UserController;
import javafx.event.ActionEvent;


public class ClientPageController {
	@FXML
	private ListView<?> clientList;

	@FXML
	private TextField userName1;

	@FXML
	private TextField phoneNumber;

	@FXML
	private TextField username3;

	@FXML
	private TextField name;

	@FXML
	private TextField Password;

	@FXML
	private TextField username2;

	@FXML
	private TextField newPassword;

	@FXML
	private TextField newName;

	@FXML
	private TextField newPhoneNumber;

	@FXML
	private TextField Address;

	@FXML
	private TextField newClientAddress;

	@FXML
	private ButtonBar buttonBar;

	private Stage stage;
	private Scene scene;
	private Parent root;


	// Methods for actions specified in the FXML
	public void addClientClicked(ActionEvent event) {
		String username = userName1.getText();
		String aName = name.getText();
		String aPassword = Password.getText();
		String aPhoneNumber = phoneNumber.getText();
		String aAddress = Address.getText();

		if (ViewUtils.successful(UserController.addEmployeeOrClient(username, aPassword, aName, aPhoneNumber, false, aAddress))) {
			userName1.setText("");
			name.setText("");
			Password.setText("");
			phoneNumber.setText("");
			Address.setText("");
		}
	}

	@FXML
	public void removeClientClicked(ActionEvent event) {
		String username = username3.getText();
		UserController.deleteEmployeeOrClient(username);
		username3.setText("");
	}

	@FXML
	public void updateClientClicked(ActionEvent event) {
		String aUserName = username2.getText();
		String aNewPassword = newPassword.getText();
		String aNewName = newName.getText();
		String aNewPhoneNumber = newPhoneNumber.getText();
		String aNewAddress = newClientAddress.getText();
		if (ViewUtils.successful(UserController.updateEmployeeOrClient(aUserName, aNewPassword, aNewName, aNewPhoneNumber, aNewAddress))) {
			username2.setText("");
			newPassword.setText("");
			newName.setText("");
			newPhoneNumber.setText("");
			newClientAddress.setText("");
		}
	}

	public void homeButtonClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("WareFlowHome.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void employeesButtonClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("WareFlowEmployee.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void clientsOnClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("WareFlowClient.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void itemTypesClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("WareFlowItemType.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void itemContainersClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("WareFlowItemContainer.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void shipmentOrderClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("WareFlowShipmentPage.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
