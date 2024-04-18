package ca.mcgill.ecse.wareflow.javafxFxml;

import java.util.List;

import ca.mcgill.ecse.wareflow.controller.ShipmentOrderController;
import ca.mcgill.ecse.wareflow.controller.TOShipmentOrder;
import ca.mcgill.ecse.wareflow.controller.TOUser;
import ca.mcgill.ecse.wareflow.controller.UserController;
import ca.mcgill.ecse.wareflow.javafxFxml.WareFlowFxmlView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUtils {

  public static boolean callController(String result) {
    if (result.isEmpty()) {
      WareFlowFxmlView.getInstance().refresh();
      return true;
    }
    showError(result);
    return false;
  }

  public static boolean successful(String controllerResult) {
    return callController(controllerResult);
  }

  public static void makePopupWindow(String title, String message) {
    Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    VBox dialogPane = new VBox();

    // create UI elements
    Text text = new Text(message);
    Button okButton = new Button("OK");
    okButton.setOnAction(a -> dialog.close());

    // display the popup window
    int innerPadding = 10; // inner padding/spacing
    int outerPadding = 100; // outer padding
    dialogPane.setSpacing(innerPadding);
    dialogPane.setAlignment(Pos.CENTER);
    dialogPane.setPadding(new Insets(innerPadding, innerPadding, innerPadding, innerPadding));
    dialogPane.getChildren().addAll(text, okButton);
    Scene dialogScene = new Scene(dialogPane, outerPadding + 5 * message.length(), outerPadding);
    dialog.setScene(dialogScene);
    dialog.setTitle(title);
    dialog.show();
  }

  public static void showError(String result) {
    makePopupWindow("Error", result);
  }
  
  public static ObservableList<TOUser> getEmployees() {
    List<TOUser> employees = UserController.getEmployees();
    return FXCollections.observableList(employees);
  }
  public static ObservableList<TOShipmentOrder> getShipmentOrders(){
    List<TOShipmentOrder> shipmentOrders = ShipmentOrderController.getOrders();
    return FXCollections.observableList(shipmentOrders);
  }
  
}
