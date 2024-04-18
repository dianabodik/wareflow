package ca.mcgill.ecse.wareflow.application;

import ca.mcgill.ecse.wareflow.model.WareFlow;
import ca.mcgill.ecse.wareflow.persistence.WareFlowPersistence;
import ca.mcgill.ecse.wareflow.javafxFxml.WareFlowFxmlView;
import ca.mcgill.ecse.wareflow.persistence.WareFlowPersistence;
import javafx.application.Application;
import javafx.stage.Stage;

public class WareFlowApplication {

  private static WareFlow wareFlow;

  public static void main(String[] args) {
    // TODO Start the application user interface here
    wareFlow = getWareFlow();
    Application.launch(WareFlowFxmlView.class, args);
  }

  public static WareFlow getWareFlow() {
    if (wareFlow == null) {
      // these attributes are default, you should set them later with the setters
      wareFlow = WareFlowPersistence.load();
    }
    return wareFlow;
  }
}
