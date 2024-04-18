package ca.mcgill.ecse.wareflow.javafxFxml;

import ca.mcgill.ecse.wareflow.controller.TOUser;
import ca.mcgill.ecse.wareflow.controller.TOShipmentOrder;
import ca.mcgill.ecse.wareflow.controller.ShipmentOrderController;
import ca.mcgill.ecse.wareflow.javafxFxml.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

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

public class ViewShipmentOrderController {
    @FXML private TableView<TOShipmentOrder> overviewTable;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize(){
        overviewTable.getColumns().add(createTableColumn("ID", "id"));
        overviewTable.getColumns().add(createTableColumn("Status", "status"));
        overviewTable.getColumns().add(createTableColumn("Priority", "priority"));
        overviewTable.addEventHandler(WareFlowFxmlView.REFRESH_EVENT, e -> overviewTable.setItems(ViewUtils.getShipmentOrders()));
        WareFlowFxmlView.getInstance().registerRefreshEvent(overviewTable);
        refreshTable();
    }

    private static ViewShipmentOrderController instance;

    public ViewShipmentOrderController() {
        instance = this;
    }

    public static ViewShipmentOrderController getInstance() {
        return instance;
    }

    public void refreshTable() {
        overviewTable.setItems(ViewUtils.getShipmentOrders());
    }

    public static TableColumn<TOShipmentOrder,String> createTableColumn(String header, String propertyName){
        TableColumn<TOShipmentOrder,String> column = new TableColumn<>(header);
        column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
        return column;
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
