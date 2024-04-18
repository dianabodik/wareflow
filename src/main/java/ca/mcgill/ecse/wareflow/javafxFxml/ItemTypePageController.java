package ca.mcgill.ecse.wareflow.javafxFxml;

import java.io.IOException;

import ca.mcgill.ecse.wareflow.controller.ItemTypeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ItemTypePageController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField itemName1;
	
	@FXML
	private TextField expectedLifeSpan;
	
	@FXML
	private TextField itemName2;
	
	@FXML
	private TextField newItemName;
	
	@FXML
	private TextField newExpectedLifeSpan;
	
	@FXML
	private TextField itemName3;
	
	
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
	
	public void addItemClicked(ActionEvent event) {
		String aItemName = itemName1.getText();
		int aExpectedLifeSpan = Integer.parseInt(expectedLifeSpan.getText());
		
		if (ViewUtils.successful(ItemTypeController.addItemType(aItemName, aExpectedLifeSpan))) {
			itemName1.setText("");
			expectedLifeSpan.setText("");
		}
	}
	
	public void updateItemClicked(ActionEvent event) {
		String aItemName = itemName2.getText();
		String aNewItemName = newItemName.getText();
		int aNewExpectedLifeSpan = Integer.parseInt(newExpectedLifeSpan.getText());
		
		if (ViewUtils.successful(ItemTypeController.updateItemType(aItemName, aNewItemName, aNewExpectedLifeSpan))) {
			itemName2.setText("");
			newItemName.setText("");
			newExpectedLifeSpan.setText("");
		}
	}
	
	public void removeItemClicked(ActionEvent event) {
		String aItemName = itemName3.getText();
		ItemTypeController.deleteItemType(aItemName);
		itemName3.setText("");
	}
}
