package ca.mcgill.ecse.wareflow.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.wareflow.model.WareFlow;
import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder.PriorityLevel;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder.Status;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder.TimeEstimate;
import ca.mcgill.ecse.wareflow.model.User;
import ca.mcgill.ecse.wareflow.model.Employee;
import ca.mcgill.ecse.wareflow.model.ItemContainer;
import ca.mcgill.ecse.wareflow.model.ItemType;
import ca.mcgill.ecse.wareflow.model.ShipmentNote;
import ca.mcgill.ecse.wareflow.persistence.WareFlowPersistence;

public class ShipmentOrderController {

  // containerNumber -1 means that no container is specified and quantity has to be -1 as well
  private static WareFlow wareFlow = WareFlowApplication.getWareFlow();
  public static String addShipmentOrder(int id, Date placedOnDate, String description,
                                        String username, int containerNumber, int quantity) {
	  String error = "";
	 if (ShipmentOrder.hasWithId(id)) {
      return "Order id already exists";
    }

    User orderPlacer = User.getWithUsername(username);
    if (orderPlacer == null) {
      return "The order placer does not exist";
    }



    if (containerNumber != -1 ) {
      if (quantity <= 0) {
        return "Order quantity must be larger than 0 when container is specified";
      }
      ItemContainer itemContainer = ItemContainer.getWithContainerNumber(containerNumber);
      if (itemContainer == null) {
        return "The container does not exist";
      }
    }

    if (description == null || description.isEmpty()) {
      return "Order description cannot be empty";
    }


    if (containerNumber == -1 && quantity != 0) {

      return "Order quantity must 0 when container is not specified";
    }
    try {
      ShipmentOrder shipment_order = wareFlow.addOrder(id, placedOnDate, description, quantity, orderPlacer);
      shipment_order.setContainer(ItemContainer.getWithContainerNumber(containerNumber));
	  WareFlowPersistence.save();
	}
    catch (Exception e) {
      return e.getMessage();
    }
    return error;
    
  }

  // newContainerNumber -1 means that no container is specified and newQuantity has to be -1 as well
  public static String updateShipmentOrder(int id, Date newPlacedOnDate, String newDescription,
                                           String newUsername, int newContainerNumber, int newQuantity) {

    if (newContainerNumber != -1 ) {
      if (newQuantity <= 0) {
        return "Order quantity must be larger than 0 when container is specified";
      }
      boolean container_exists = false;
      for (ShipmentOrder order : wareFlow.getOrders()) {
        if (order.getContainer().getContainerNumber() == newContainerNumber) {
          container_exists = true;
        }
      }
      if (!container_exists) {
        return "The container does not exist";
      }
    }

    User orderPlacer = User.getWithUsername(newUsername);
    if (orderPlacer == null) {
      return "The order placer does not exist";
    }

    if (newDescription == null || newDescription.isEmpty()) {
      return "Order description cannot be empty";
    }

    if (newContainerNumber == -1 && newQuantity != 0) {
      return "Order quantity must 0 when container is not specified";
    }
    ShipmentOrder shipment_order = ShipmentOrder.getWithId(id);
    try {
      shipment_order.setPlacedOnDate(newPlacedOnDate);
      shipment_order.setDescription(newDescription);
      shipment_order.setOrderPlacer(orderPlacer);
      shipment_order.setContainer(ItemContainer.getWithContainerNumber(newContainerNumber));
      shipment_order.setQuantity(newQuantity);
	  WareFlowPersistence.save();
	}
    catch(Exception e) {
      return e.getMessage();
    }
    return "";
  }

  public static void deleteShipmentOrder(int id) {
    ShipmentOrder shipmentOrder = ShipmentOrder.getWithId(id);
    try{
      shipmentOrder.delete();
	  WareFlowPersistence.save();

	}
    catch(Exception e){

    }
  }
  public static List<TOShipmentOrder> getOrders() { 
	  List<ShipmentOrder> orders = wareFlow.getOrders();
	  List<TOShipmentOrder> listOfOrders = new ArrayList<TOShipmentOrder>();
	  for (ShipmentOrder o: orders) {
		  int id = o.getId();
		  int quantity = o.getQuantity();
		  Date placedOnDate = o.getPlacedOnDate();
		  String description = o.getDescription();
		  String orderPlacer = o.getOrderPlacer().getUsername();
		  ItemType itemType = null;
		  String itemName = null;
		  int expectedLifeSpanInDays = -1;
		  Date addedOnDate = null;
		  int areaNumber = -1;
		  int slotNumber = -1;
		  ItemContainer itemContainer = o.getContainer();
		  String status = o.getStatus().toString();
		  String processedBy = null;
		  if(o.getOrderPicker() != null) {

			  processedBy = o.getOrderPicker().getUsername();

		  }
          String timeToResolve = null;
          if (o.getTimeToFullfill() != null) {
            timeToResolve = o.getTimeToFullfill().toString();
          }
          String priority = null;
          if (o.getPriority() != null) {
            priority = o.getPriority().toString();
          }
          
          boolean approvalRequired = o.getManagerApprovalRequired();
		  
		  
		  if (itemContainer != null) {
			  itemType = itemContainer.getItemType();
			  itemName = itemType.getName();
			  expectedLifeSpanInDays = itemType.getExpectedLifeSpanInDays();
			  addedOnDate = itemContainer.getAddedOnDate();
			  areaNumber = itemContainer.getAreaNumber();
			  slotNumber = itemContainer.getSlotNumber();
		  }
		  List<ShipmentNote> shipmentNotes = o.getShipmentNotes();
		  TOShipmentNote listOfNotes[] = new TOShipmentNote[o.getShipmentNotes().size()];
		  int i = 0;
		  for (ShipmentNote n: shipmentNotes) {
			  Date noteDate = n.getDate();
			  String noteDescription = n.getDescription();
			  String noteTakerUsername = n.getNoteTaker().getUsername();
			  TOShipmentNote note = new TOShipmentNote(noteDate,noteDescription,noteTakerUsername);
			  listOfNotes[i] = note;
			  i++;
		  }
          TOShipmentOrder order = new TOShipmentOrder(id, quantity, placedOnDate, description, orderPlacer, status,processedBy, timeToResolve, priority, approvalRequired, itemName, expectedLifeSpanInDays, addedOnDate, areaNumber, slotNumber, listOfNotes);
		  listOfOrders.add(order);
	  }
	  return listOfOrders;
  }
  // returns all shipment orders
  public static String assignOrderToStaff(int orderId, String employeeUsername, String timeEstimate, String priority, String requiresApproval ) {
		String error = "";
		boolean approvalRequired = Boolean.valueOf(requiresApproval);
		ShipmentOrder order = null;
		List<ShipmentOrder> allOrders = wareFlow.getOrders();
		for (ShipmentOrder o: allOrders) {
			if (o.getId() == orderId) {
				order = o;
			}
		}

		if (order == null ) {
			return "shipment order does not exist.";
		}
		Employee staff = (Employee) User.getWithUsername(employeeUsername);
		if (staff == null ) {
			return "Staff to assign does not exist.";
		}
		TimeEstimate time = TimeEstimate.valueOf(timeEstimate);
		PriorityLevel priorityLevel = PriorityLevel.valueOf(priority);
		
		Status status = order.getStatus();
		switch (status)
		{
			case Open:
				try {
					order.assignOrder(staff, priorityLevel, time, approvalRequired);
					WareFlowPersistence.save();

				}
				catch (Exception e) {
					return e.getMessage();
				}
        break;
			case Assigned:
				return "The shipment order is already assigned.";
			
			case InProgress:
				return "Cannot assign a shipment order which is in progress.";
			
			case Completed:
				return "Cannot assign a shipment order which is completed.";
				
			case Closed:
				return "Cannot assign a shipment order which is closed.";
			default:
				break;

		}
		
		return error;
	}
	
	public static String StartWork(int orderId) {
		String error = "";
		ShipmentOrder order = null;
		List<ShipmentOrder> allOrders = wareFlow.getOrders();
		for (ShipmentOrder o: allOrders) {
			if (o.getId() == orderId) {
				order = o;
			}
		}
		if (order == null ) {
			return "shipment order does not exist.";
		}
		

		Status orderStatus = order.getStatus();
		switch (orderStatus) {
			case Open:
				return "Cannot start a shipment order which is open.";
			case Assigned:
				try {
					order.startWork(order);
					WareFlowPersistence.save();

				}
				catch(Exception e) {
					e.getMessage();
				}
				break;

			case InProgress:
				return "The shipment order is already in progress.";
			
			case Completed:
				return "Cannot start a shipment order which is completed.";
				
			case Closed:
				return "Cannot start a shipment order which is closed.";
			default:
				break;

		}
	return error;
	}
	
	public static String MarkComplete(int orderId) {
		String error = "";
		ShipmentOrder order = null;
		List<ShipmentOrder> allOrders = wareFlow.getOrders();
		for (ShipmentOrder o: allOrders) {
			if (o.getId() == orderId) {
				order = o;
			}
		}

		if (order == null) {
			return "shipment order does not exist.";
		}
		Status orderStatus = order.getStatus();
		
		switch (orderStatus) {
			case Open:
				return "Cannot complete a shipment order which is open.";
			case Assigned:
				return "Cannot complete a shipment order which is assigned.";
			
			case InProgress:
				try {
						order.completeOrder(order);
						WareFlowPersistence.save();

				}
				catch (Exception e) {
					e.getMessage();
				}
				break;
			case Completed:
				return "The shipment order is already completed.";
				
			case Closed:
				return "The shipment order is already closed.";
			default:
				break;

		}
		return error;
	}

  public static String MarkDisaprroved(int orderId, Date date, String reason){

	  ShipmentOrder order = null;
		List<ShipmentOrder> allOrders = wareFlow.getOrders();
		for (ShipmentOrder o: allOrders) {
			if (o.getId() == orderId) {
				order = o;
			}
		}
      if (order == null){
          return "shipment order does not exist.";

      }
      Status orderStatus = order.getStatus();
      //can only disapprove if in completed state
      switch (orderStatus){
          case Open:
              return "Cannot disapprove a shipment order which is open.";
          case Assigned:
              return "Cannot disapprove a shipment order which is assigned.";
          case InProgress:
              return "Cannot disapprove a shipment order which is in progress.";
          case Completed:
        	  try{
                  order.disapprove(date, reason);
                  order.addShipmentNote(date, reason, wareFlow.getManager());
				  WareFlowPersistence.save();
			  }
              catch (Exception e){
                  return e.getMessage();
              }
              break;
          case Closed:
              return "Cannot disapprove a shipment order which is closed.";
          default:
        	  break;

      }
      return "";
  }

  public static String MarkApproved(int orderId){
	  ShipmentOrder order = null;
		List<ShipmentOrder> allOrders = wareFlow.getOrders();
		for (ShipmentOrder o: allOrders) {
			if (o.getId() == orderId) {
				order = o;
			}
		}
      if (order == null){
          return "shipment order does not exist.";

      }
      Status orderStatus = order.getStatus();
      //can only approve if in completed state
      switch (orderStatus){
          case Open:
              return "Cannot approve a shipment order which is open.";
          case Assigned:
              return "Cannot approve a shipment order which is assigned.";
          case Closed:
              return "The shipment order is already closed.";

          case InProgress:
              return "Cannot approve a shipment order which is in progress.";
          case Completed:
              try{
                  order.approve();
				  WareFlowPersistence.save();
			  }
              catch (Exception e){
                  return e.getMessage();
              }
              break;
          default:
        	  break;

      }
      return "";

  }
}
