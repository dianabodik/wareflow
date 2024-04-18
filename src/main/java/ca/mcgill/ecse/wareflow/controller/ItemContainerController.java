package ca.mcgill.ecse.wareflow.controller;

import java.sql.Date;

import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.ItemContainer;
import ca.mcgill.ecse.wareflow.model.ItemType;
import ca.mcgill.ecse.wareflow.model.WareFlow;
import ca.mcgill.ecse.wareflow.persistence.WareFlowPersistence;

public class ItemContainerController {

    private static WareFlow wareFlow = WareFlowApplication.getWareFlow(); // Create instance of WareFlow

    public static String addItemContainer(int containerNumber, int areaNumber, int slotNumber,
            Date addedOnDate, String itemTypeName) {

        String error ="";
        // Retrieve the ItemType instance
        ItemType itemType = ItemType.getWithName(itemTypeName);
        if (itemType == null) {
            return "The item type does not exist";
        }
        
        // Check if the container number is valid
        if (containerNumber <= 0) {
            return "The container number shall not be less than 1";
        }

        // Check if the area number is valid
        if (areaNumber < 0) {
            return "The area number shall not be less than 0";
        }

        // Check if the slot number is valid
        if (slotNumber < 0) {
            return "The slot number shall not be less than 0";
        }

        // Create a new item container
        try {
            wareFlow.addItemContainer(containerNumber, areaNumber, slotNumber, addedOnDate, itemType);
            WareFlowPersistence.save();
        } catch (Exception e) {
            return e.getMessage();
        }
        return error;
    }

    public static String updateItemContainer(int containerNumber, int newAreaNumber, int newSlotNumber,
            Date newAddedOnDate, String newItemTypeName) {
        String error = "";

        // Retrieve the ItemContainer instance
        ItemContainer itemContainer = ItemContainer.getWithContainerNumber(containerNumber);
        if (itemContainer == null) {
            return "The item container does not exist";
        }
        
        // Check if the container number is valid
        if (containerNumber <= 0) {
            return "The container number shall not be less than 1";
        }

        // Check if the area number is valid
        if (newAreaNumber < 0) {
            return "The area number shall not be less than 0";
        }

        // Check if the slot number is valid
        if (newSlotNumber < 0) {
            return "The slot number shall not be less than 0";
        }

        // Retrieve the new ItemType instance
        ItemType newItemType = ItemType.getWithName(newItemTypeName);
        if (newItemType == null) {
            return "The item type does not exist";
        }

        // Update the ItemContainer
        try {
            itemContainer.setAreaNumber(newAreaNumber);
            itemContainer.setSlotNumber(newSlotNumber);
            itemContainer.setAddedOnDate(newAddedOnDate);
            itemContainer.setItemType(newItemType);
            WareFlowPersistence.save();
        } catch (RuntimeException e) {
            error = e.getMessage();
        }

        return error;
    }

    public static String deleteItemContainer(int containerNumber) {
        String error = "";

        // Retrieve the ItemContainer instance
        ItemContainer itemContainer = ItemContainer.getWithContainerNumber(containerNumber);
        if (itemContainer == null) {
            return "The item container does not exist";
        }

        // Delete the ItemContainer
        try {
            itemContainer.delete();
            WareFlowPersistence.save();
        } catch (RuntimeException e) {
            error = e.getMessage();
        }

        return error;
    }
}