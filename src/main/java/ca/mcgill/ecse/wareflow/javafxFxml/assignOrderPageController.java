package ca.mcgill.ecse.wareflow.javafxFxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import ca.mcgill.ecse.wareflow.controller.ShipmentOrderController;

public class assignOrderPageController implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField orderID;
	
	private String timeEstimate;
	
	private String priority;
	
	private String approvalRequired;
	
	@FXML
	private ChoiceBox<String> timeEstimateChoiceBox;
	
	@FXML
	private ChoiceBox<String> priorityChoiceBox;
	
	@FXML
	private ChoiceBox<String> approvalRequiredChoiceBox;
	
	@FXML
	private TextField employeeUsername;
	
	private String[] timeEstimateList = {"LessThanADay", "OneToThreeDays", "ThreeToSevenDays", "OneToThreeWeeks", "ThreeOrMoreWeeks" };
	
	private String[] priorityList = {"Urgent", "Normal", "Low"};
	
	private String[] approvalRequiredList = {"true", "false" };
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		timeEstimateChoiceBox.getItems().addAll(timeEstimateList);
		priorityChoiceBox.getItems().addAll(priorityList);
		approvalRequiredChoiceBox.getItems().addAll(approvalRequiredList);
		timeEstimateChoiceBox.setOnAction(this::getTimeEstimate);
		priorityChoiceBox.setOnAction(this::getPriority);
		approvalRequiredChoiceBox.setOnAction(this::getApprovalRequried);
	}
	
	public void getTimeEstimate(ActionEvent event) {
		timeEstimate = timeEstimateChoiceBox.getValue();
	}
	public void getPriority(ActionEvent event) {
		priority = priorityChoiceBox.getValue();
	}
	public void getApprovalRequried(ActionEvent event) {
		approvalRequired = approvalRequiredChoiceBox.getValue();
	}
	
	public void assignOrderClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID.getText());
		String aEmployeeUsername = employeeUsername.getText();
		if (ViewUtils.successful(ShipmentOrderController.assignOrderToStaff(aOrderID, aEmployeeUsername, timeEstimate, priority, approvalRequired))) {
			orderID.setText("");
			employeeUsername.setText("");
		}
	}
	
}
