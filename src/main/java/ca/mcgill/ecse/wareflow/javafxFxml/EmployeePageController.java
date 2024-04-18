package ca.mcgill.ecse.wareflow.javafxFxml;

import java.io.IOException;

import ca.mcgill.ecse.wareflow.controller.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeePageController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField employeeName;
	
	@FXML
	private TextField employeeID;
	
	@FXML
	private TextField employeeUsername;
	
	@FXML
	private TextField employeePassword;
	
	@FXML
	private TextField employeePhoneNumber;
	
	@FXML
	private TextField employeeUsername2;
	
	
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
	
	public void addEmployeeClicked(ActionEvent event) {
		String aName = employeeName.getText();
		int aEmployeeID = Integer.parseInt(employeeID.getText());
		String aUsername = employeeUsername.getText();
		String aPassword = employeePassword.getText();
		String aPhoneNumber = employeePhoneNumber.getText();
		
		if (ViewUtils.successful(UserController.addEmployeeOrClient(aUsername, aPassword, aName, aPhoneNumber, true, null))) {
			employeeName.setText("");
			employeeID.setText("");
			employeeUsername.setText("");
			employeePassword.setText("");
			employeePhoneNumber.setText("");
		}
		
	}
	
	public void updateEmployeeClicked(ActionEvent event) {
		String aName = employeeName.getText();
		int aEmployeeID = Integer.parseInt(employeeID.getText());
		String aUsername = employeeUsername.getText();
		String aPassword = employeePassword.getText();
		String aPhoneNumber = employeePhoneNumber.getText();
		
		if (ViewUtils.successful(UserController.updateEmployeeOrClient(aUsername, aPassword, aName, aPhoneNumber, null))) {
			employeeName.setText("");
			employeeID.setText("");
			employeeUsername.setText("");
			employeePassword.setText("");
			employeePhoneNumber.setText("");
		}
	}
	
	public void removeEmployeeClicked(ActionEvent event) {
		String aUsername = employeeUsername2.getText();
		UserController.deleteEmployeeOrClient(aUsername);
		employeeUsername2.setText("");
	}
	
}
