package ca.mcgill.ecse.wareflow.javafxFxml;

import java.io.IOException;
import java.sql.Date;

import ca.mcgill.ecse.wareflow.controller.ItemContainerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ItemContainerPageController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField containerNumber1;
	
	@FXML
	private TextField areaNumber;
	
	@FXML
	private TextField slotNumber;
	@FXML
	private TextField dateAdded;
	
	@FXML
	private TextField itemType;
	
	@FXML
	private TextField containerNumber2;
	
	@FXML
	private TextField newAreaNumber;
	
	@FXML
	private TextField newSlotNumber;
	
	@FXML
	private TextField newDateAdded;
	
	@FXML
	private TextField newItemType;
	
	@FXML
	private TextField containerNumber3;

	
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
	
	public void addContainerClicked(ActionEvent event) {
		int aContainerNumber = Integer.parseInt(containerNumber1.getText());
		int aAreaNumber = Integer.parseInt(areaNumber.getText());
		int aSlotNumber = Integer.parseInt(slotNumber.getText());
		Date aDate = Date.valueOf(dateAdded.getText());
		String aItemType = itemType.getText();
		if (ViewUtils.successful(ItemContainerController.addItemContainer(aContainerNumber, aAreaNumber, aSlotNumber, aDate, aItemType))) {
			containerNumber1.setText("");
			areaNumber.setText("");
			slotNumber.setText("");
			dateAdded.setText("");
			itemType.setText("");
		}
	}
	
	public void updateContainerClicked(ActionEvent event) {
		int aContainerNumber = Integer.parseInt(containerNumber2.getText());
		int aAreaNumber = Integer.parseInt(newAreaNumber.getText());
		int aSlotNumber = Integer.parseInt(newSlotNumber.getText());
		Date aDate = Date.valueOf(newDateAdded.getText());
		String aItemType = newItemType.getText();
		if (ViewUtils.successful(ItemContainerController.updateItemContainer(aContainerNumber, aAreaNumber, aSlotNumber, aDate, aItemType))) {
			containerNumber2.setText("");
			newAreaNumber.setText("");
			newSlotNumber.setText("");
			newDateAdded.setText("");
			newItemType.setText("");
		}
	}
	
	public void removeContainerClicked(ActionEvent event) {
		int aContainerNumber = Integer.parseInt(containerNumber3.getText());
		ItemContainerController.deleteItemContainer(aContainerNumber);
		containerNumber3.setText("");
	}
}
