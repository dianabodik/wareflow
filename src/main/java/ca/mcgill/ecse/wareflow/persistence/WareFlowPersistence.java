package ca.mcgill.ecse.wareflow.persistence;

import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.WareFlow;

public class WareFlowPersistence {

    private static String filename = "wareflow.json";
    private static JsonSerializer serializer = new JsonSerializer("ca.mcgill.ecse.wareflow");
    public static void setFilename(String newFilename) {
        WareFlowPersistence.filename = newFilename;
    }

    public static void save() {
        save(WareFlowApplication.getWareFlow());
    }

    public static void save(WareFlow wareFlow) {
        serializer.serialize(wareFlow, filename);
    }

    public static WareFlow load() {
        var wareFlow = (WareFlow) serializer.deserialize(filename);
        if (wareFlow == null) {
            wareFlow = new WareFlow();
        } else {
            wareFlow.reinitialize();
        }
        return wareFlow;
    }
}
