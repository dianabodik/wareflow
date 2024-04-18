package ca.mcgill.ecse.wareflow.javafxFxml;

import java.io.IOException;
import java.sql.Date;

import ca.mcgill.ecse.wareflow.controller.ShipmentNoteController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NotePageController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField noteDate;
	
	@FXML
	private TextField noteDescription;
	
	@FXML
	private TextField orderID1;
	
	@FXML
	private TextField employeeUsername;
	
	@FXML
	private TextField orderID2;
	
	@FXML
	private TextField newNoteDate;
	
	@FXML
	private TextField newDescription;
	
	@FXML
	private TextField newEmployeeUsername;
	
	@FXML
	private TextField index1;
	
	@FXML
	private TextField orderID3;
	
	@FXML
	private TextField index2;
	
	
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
	
	public void addNoteClicked(ActionEvent event) {
		Date aNoteDate = Date.valueOf(noteDate.getText());
		String aNoteDescription = noteDescription.getText();
		int aOrderID = Integer.parseInt(orderID1.getText());
		String userName = employeeUsername.getText();
		
		if (ViewUtils.successful(ShipmentNoteController.addShipmentNote(aNoteDate, aNoteDescription, aOrderID, userName))) {
			noteDate.setText("");
			noteDescription.setText("");
			orderID1.setText("");
			employeeUsername.setText("");
		}
	}
	
	public void updateNoteClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID2.getText());
		Date aNoteDate = Date.valueOf(newNoteDate.getText());
		String aNoteDescription = newDescription.getText();
		String userName = newEmployeeUsername.getText();
		int aIndex = Integer.parseInt(index1.getText());
		
		
		if (ViewUtils.successful(ShipmentNoteController.updateShipmentNote(aOrderID, aIndex, aNoteDate, aNoteDescription, userName))) {
			newNoteDate.setText("");
			newDescription.setText("");
			orderID2.setText("");
			newEmployeeUsername.setText("");
			index1.setText("");
		}
	}
	
	public void removeNoteClicked(ActionEvent event) {
		int aOrderID = Integer.parseInt(orderID3.getText());
		int aIndex = Integer.parseInt(index2.getText());
		ShipmentNoteController.deleteShipmentNote(aOrderID, aIndex);
		orderID3.setText("");
		index2.setText("");
	}
}
