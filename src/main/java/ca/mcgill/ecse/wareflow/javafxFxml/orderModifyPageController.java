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
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import ca.mcgill.ecse.wareflow.controller.ShipmentOrderController;

public class orderModifyPageController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField orderID1;
	
	@FXML
	private TextField datePlaced;
	
	@FXML
	private TextField description;
	
	@FXML
	private TextField containerNumber;
	
	@FXML
	private TextField itemQuantity;
	
	@FXML
	private TextField clientUsername;
	
	@FXML
	private TextField orderID2;
	
	@FXML
	private TextField newDatePlaced;
	
	@FXML
	private TextField newDescription;
	
	@FXML
	private TextField newContainerNumber;
	
	@FXML
	private TextField newItemQuantity;
	
	@FXML
	private TextField newClientUsername;
	
	@FXML
	private TextField orderID3;
	
	
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
	
	public void addOrderClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID1.getText());
		Date aDatePlaced = Date.valueOf(datePlaced.getText());
		String aDescription = description.getText();
		int aContainerNumber = Integer.parseInt(containerNumber.getText());
		int aItemQuantity = Integer.parseInt(itemQuantity.getText());
		String aClientUsername = clientUsername.getText();
		
		if (ViewUtils.successful(ShipmentOrderController.addShipmentOrder(aOrderID, aDatePlaced, aDescription, aClientUsername, aContainerNumber, aItemQuantity))) {
			orderID1.setText("");
			datePlaced.setText("");
			description.setText("");
			containerNumber.setText("");
			itemQuantity.setText("");
			clientUsername.setText("");
		}
	}
	public void updateOrderClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID2.getText());
		Date aDatePlaced = Date.valueOf(newDatePlaced.getText());
		String aDescription = newDescription.getText();
		int aContainerNumber = Integer.parseInt(newContainerNumber.getText());
		int aItemQuantity = Integer.parseInt(newItemQuantity.getText());
		String aClientUsername = newClientUsername.getText();
		
		if (ViewUtils.successful(ShipmentOrderController.updateShipmentOrder(aOrderID, aDatePlaced, aDescription, aClientUsername, aContainerNumber, aItemQuantity))) {
			orderID2.setText("");
			newDatePlaced.setText("");
			newDescription.setText("");
			newContainerNumber.setText("");
			newItemQuantity.setText("");
			newClientUsername.setText("");
		}
	}
	
	public void removeOrderClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID3.getText());
		ShipmentOrderController.deleteShipmentOrder(aOrderID);
		orderID3.setText("");
	}
}
