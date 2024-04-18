package ca.mcgill.ecse.wareflow.controller;

import java.sql.Date;
import ca.mcgill.ecse.wareflow.model.ShipmentNote;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder;
import ca.mcgill.ecse.wareflow.model.User;
import ca.mcgill.ecse.wareflow.model.WarehouseStaff;
import ca.mcgill.ecse.wareflow.model.Employee;
import ca.mcgill.ecse.wareflow.persistence.WareFlowPersistence;

public class ShipmentNoteController {
  public static String addShipmentNote(Date date, String description, int orderID,
      String username) {
      //1. Input Validation
    
      // Strings should not be null
      //date should not be null 
      
      // Need to check if the employee exists in the system or not
      // Need to check order with orderid exists in the system or not
      String error = "";
      if ( description == null || description.equals("")) {
          return "Order description cannot be empty";
      }
      if (date == null) {
          return "Date cannot be empty";
      }
      ShipmentOrder order = ShipmentOrder.getWithId(orderID);
      if (order == null) {
          return "Order does not exist";
      }
      WarehouseStaff staff = (WarehouseStaff) User.getWithUsername(username);
      if (staff == null) {
          return "Staff does not exist";
      }
      try {
          order.addShipmentNote(date, description, staff);
          WareFlowPersistence.save();
      }catch(Exception e) {
          return e.getMessage();
      }
      return error;
  }

  // index starts at 0
  public static String updateShipmentNote(int orderID, int index, Date newDate,
      String newDescription, String newUsername) {
      String error = "";
      //1. Input validation
      //Strings cannot be null or empty
      // Employee with new username should exist in the system
      // Shipment note should exits in the system
      // Order with orderId should exist in the system
      if (newDescription == null || newDescription.equals("")) {
          return "Order description cannot be empty";
      }
      if (newDate == null) {
          return "Date cannot be empty";
      }
      ShipmentOrder order = ShipmentOrder.getWithId(orderID);
      if (order == null) {
          return "Order does not exist";
      }
      WarehouseStaff staff = (WarehouseStaff) User.getWithUsername(newUsername);
      if(staff == null) {
          return "Staff does not exist";
      }
      if(!(index < order.getShipmentNotes().size())) {
          return "Note does not exist";
      }
      try {
          ShipmentNote note = order.getShipmentNote(index);
          note.setDate(newDate);
          note.setDescription(newDescription);
          note.setNoteTaker(staff);
          WareFlowPersistence.save();
      }catch(Exception e) {
          e.getMessage();
      }
      return error;
  }

  // index starts at 0
  public static void deleteShipmentNote(int orderID, int index) {
      //1. input validation
      //check if the order exist with orderID
      //check if the note exist with index
      
      ShipmentOrder order = ShipmentOrder.getWithId(orderID);
      if (order != null) {
          if (index < order.getShipmentNotes().size()) {
              ShipmentNote note = order.getShipmentNote(index);
              if (note != null) {
                  note.delete();
                  WareFlowPersistence.save();
              }
          }
      }
      //if the note does not exist in the system then do nothing.
  }
}

