package ca.mcgill.ecse.wareflow.controller;

import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.ItemType;
import ca.mcgill.ecse.wareflow.model.WareFlow;
import ca.mcgill.ecse.wareflow.persistence.WareFlowPersistence;

public class ItemTypeController {
    private static WareFlow wareFlow = WareFlowApplication.getWareFlow();
  public static String addItemType(String name, int expectedLifeSpanInDays) {
      String error = "";
      
      // What is not allowed in the Item Type?
      
      
      // 1. Data validation
      
      // not null
      // name can't be empty
      // expectedLifeSpan must be greater than 0
      
      if (name == null || name.equals("")) {
          return "The name must not be empty";
      }
      if (expectedLifeSpanInDays <= 0) {
          return "The expected life span must be greater than 0 days";
      }
      
      // 2. Duplicate check
      
      // we want this to be null for this type to be added in the system
      ItemType a = ItemType.getWithName(name);
      if (a != null) {
          return "The item type already exists";
      }
      
      // create the new ItemType in the system now.
      
      try {
          wareFlow.addItemType(name, expectedLifeSpanInDays);
          WareFlowPersistence.save();


      } catch(Exception e) {
          return e.getMessage();
        }
      
      return error;
      
      
      
  }

  public static String updateItemType(String oldName, String newName,
      int newExpectedLifeSpanInDays) {
    String error = "";
    
    // 1. Data validation for new ItemType name and new expectedLifeSpan
    
    // not null
    // name must not be empty
    // expected life span must be greater than 0
    
    if (newName == null || newName.equals("")) {
        return "The name must not be empty";
    }
    if (newExpectedLifeSpanInDays <= 0) {
        return "The expected life span must be greater than 0 days";
    }
    
    // 2. Check if the Item type with the old name exist in the system or not
    
    ItemType oldItemType = ItemType.getWithName(oldName);
    if (oldItemType == null) {
        return "The item type does not exist";
    }
    
    // 3. Duplicate check: Checking if the ItemType with the new name already exists in the system or not
    if (!(newName.equals(oldName))) {
        ItemType newItemType = ItemType.getWithName(newName);
        if (newItemType != null) {
            return "The item type already exists";
        }
    }
    
    // 4. Update the ItemType
    
    try {
        //call oldItemType 
        // update type with new information using set functions
        
        oldItemType.setName(newName);
        oldItemType.setExpectedLifeSpanInDays(newExpectedLifeSpanInDays);
        WareFlowPersistence.save();
    } catch(Exception e) {
        return e.getMessage();
    }
    
    return error;
  }

  public static void deleteItemType(String name) {
      // 1. Checking if the ItemType exits with name in the system or not
      
      ItemType itemToDelete = ItemType.getWithName(name);
      
      if (itemToDelete != null) {
          itemToDelete.delete();
          WareFlowPersistence.save();
      }
      
      // 2. If it does not exist then no need to delete the Item Type
  }
}
