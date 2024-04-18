package ca.mcgill.ecse.wareflow.javafxFxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

import ca.mcgill.ecse.wareflow.controller.ShipmentOrderController;


public class orderStatusPageController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField orderID1;
	
	@FXML
	private TextField orderID2;
	
	@FXML
	private TextField date;
	
	@FXML
	private TextField reason;
	
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
	
	public void startWorkClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID1.getText());
		if (ViewUtils.successful(ShipmentOrderController.StartWork(aOrderID))) {
			orderID1.setText("");
		}
	}
	
	public void markApprovedClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID1.getText());
		if (ViewUtils.successful(ShipmentOrderController.MarkApproved(aOrderID))) {
			orderID1.setText("");
		}
	}
	
	public void markCompleteClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID1.getText());
		if (ViewUtils.successful(ShipmentOrderController.MarkComplete(aOrderID))) {
			orderID1.setText("");
		}
	}
	
	public void markDissapprovedClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID2.getText());
		Date aDate = Date.valueOf(date.getText());
		String aReason = reason.getText();
		
		if (ViewUtils.successful(ShipmentOrderController.MarkDisaprroved(aOrderID, aDate, aReason))) {
			orderID2.setText("");
			date.setText("");
			reason.setText("");
		}
	}
}
