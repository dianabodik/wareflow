package ca.mcgill.ecse.wareflow.features;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.controller.UserController;
import ca.mcgill.ecse.wareflow.controller.ShipmentOrderController;
import ca.mcgill.ecse.wareflow.controller.TOShipmentNote;
import ca.mcgill.ecse.wareflow.controller.TOShipmentOrder;
import ca.mcgill.ecse.wareflow.model.Employee;
import ca.mcgill.ecse.wareflow.model.Manager;
import ca.mcgill.ecse.wareflow.model.User;
import ca.mcgill.ecse.wareflow.model.WarehouseStaff;
import ca.mcgill.ecse.wareflow.model.WareFlow;
import ca.mcgill.ecse.wareflow.model.ItemContainer;
import ca.mcgill.ecse.wareflow.model.ItemType;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder.PriorityLevel;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder.Status;
import ca.mcgill.ecse.wareflow.model.ShipmentOrder.TimeEstimate;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ShipmentOrderStepDefinitions {
  
  WareFlow wareFlow = WareFlowApplication.getWareFlow();
  String error;
  List<TOShipmentOrder> allOrders;
  List<TOShipmentNote> allNotes;
  
  @Given("the following employees exist in the system")
  public void the_following_employees_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> employees = dataTable.asMaps();
    for (var employee : employees) {
      String username = employee.get("username");
      String password = employee.get("password");
      String name = employee.get("name");
      String phoneNumber = employee.get("phoneNumber");
      
      wareFlow.addEmployee(username, name, password, phoneNumber);
    }
  }

  @Given("the following manager exists in the system")
  public void the_following_manager_exists_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> managers = dataTable.asMaps();
    for (var manager : managers) {
      String username = manager.get("username");
      String password = manager.get("password");
      String name = manager.get("name");
      String phoneNumber = manager.get("phoneNumber");
      
      Manager newManager = new Manager(username, name, password, phoneNumber, wareFlow);
    }
  }

  @Given("the following items exist in the system")
  public void the_following_items_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> items = dataTable.asMaps();
    for (var item : items) {
      String name = item.get("name");
      String expectedLifeSpanString = item.get("expectedLifeSpan");
      int expectedLifeSpan = 0;
      if (expectedLifeSpanString != null) {
        expectedLifeSpan = Integer.parseInt(expectedLifeSpanString);
      }
      
      wareFlow.addItemType(name, expectedLifeSpan);
    }
  }

  @Given("order {string} is marked as {string} with requires approval {string}")
  public void order_is_marked_as_with_requires_approval(String orderIdString, String statusString,
      String requiresApprovalString) {
    int orderId = Integer.parseInt(orderIdString);
    List<ShipmentOrder> allOrders = wareFlow.getOrders();
    ShipmentOrder order = null;
    for (ShipmentOrder o: allOrders) {
  	  if (o.getId() == orderId) {
  		  order = o;
  	  }
    }
    
    boolean requiresApproval = Boolean.parseBoolean(requiresApprovalString);
    
    
    ShipmentOrder.Status status = ShipmentOrder.Status.valueOf(statusString);
    if (order != null) {
    	order.setManagerApprovalRequired(requiresApproval);
	    switch(status) {
	  	case Open:
	  		break;
	  	case Assigned:
	  		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), requiresApproval);
	  		break;
	  	case InProgress:
	  		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), requiresApproval);
	  		order.startWork(order);
	  		break;
	  	case Completed:
	  		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), true);
	  		order.startWork(order);
	  		order.completeOrder(order);
	  		break;
	  	case Closed:
	  		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), requiresApproval);
	  		order.startWork(order);
	  		order.completeOrder(order);
	  		order.approve();
	  		break;
	  	default:
	  		break;
	    }
    }
    
  }

  @Given("the following containers exist in the system")
  public void the_following_containers_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> containers = dataTable.asMaps();
    for (var container : containers) {
      int containerNumber = Integer.parseInt(container.get("containerNumber"));
      Date purchaseDate = Date.valueOf(container.get("purchaseDate"));
      int areaNumber = Integer.parseInt(container.get("areaNumber"));
      int slotNumber = Integer.parseInt(container.get("slotNumber"));
      ItemType type = ItemType.getWithName(container.get("type"));
      
      wareFlow.addItemContainer(containerNumber, areaNumber, slotNumber, purchaseDate, type);
    }
  }

  @Given("the following orders exist in the system")
  public void the_following_orders_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> orders = dataTable.asMaps();
    for (var order : orders) {
      int id = Integer.parseInt(order.get("id"));
      User orderPlacer = User.getWithUsername(order.get("orderPlacer"));
      Date placedOnDate = Date.valueOf(order.get("placedOnDate"));
      String description = order.get("description");
      int containerNumber = Integer.parseInt(order.get("containerNumber"));
      int quantity = Integer.parseInt(order.get("quantity"));
      
      
      Status status = ShipmentOrder.Status.valueOf(order.get("status"));
      WarehouseStaff processedBy = null;
      TimeEstimate timeToResolve = null ;
      PriorityLevel priority = null;  
      Boolean approvalRequired = false;
      
      if (!(status.equals(Status.Open))) {
    	  processedBy = (WarehouseStaff) User.getWithUsername(order.get("processedBy"));;
    	  timeToResolve = TimeEstimate.valueOf(order.get("timeToResolve"));
    	  priority = PriorityLevel.valueOf(order.get("priority"));
    	  approvalRequired = Boolean.parseBoolean(order.get("approvalRequired"));
      }
      ShipmentOrder newOrder = wareFlow.addOrder(id, placedOnDate, description, quantity, orderPlacer);
      newOrder.setContainer(ItemContainer.getWithContainerNumber(containerNumber));
      
      switch(status) {
      	case Open:
      		break;
      	case Assigned:
      		newOrder.assignOrder(processedBy, priority, timeToResolve, approvalRequired);
      		break;
      	case InProgress:
      		newOrder.assignOrder(processedBy, priority, timeToResolve, approvalRequired);
      		newOrder.startWork(newOrder);
      		break;
      	case Completed:
      		newOrder.assignOrder(processedBy, priority, timeToResolve, true);
      		newOrder.startWork(newOrder);
      		newOrder.completeOrder(newOrder);
      		break;
      	case Closed:
      		newOrder.assignOrder(processedBy, priority, timeToResolve, approvalRequired);
      		newOrder.startWork(newOrder);
      		newOrder.completeOrder(newOrder);
      		newOrder.approve();
      		break;
      }
    }
    
  }

  @Given("the following notes exist in the system")
  public void the_following_notes_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> notes = dataTable.asMaps();
    for (var note : notes) {
      String noteTaker = note.get("noteTaker");
      int orderId = Integer.parseInt(note.get("orderId"));
      Date addedOnDate = Date.valueOf(note.get("addedOnDate"));
      String description = note.get("description");
      
      List<ShipmentOrder> allOrders = wareFlow.getOrders();
      ShipmentOrder order = null;
      for (ShipmentOrder o: allOrders) {
    	  if (o.getId() == orderId) {
    		  order = o;
    	  }
      }
      if (order != null) {
      order.addShipmentNote(addedOnDate, description,(WarehouseStaff)WarehouseStaff.getWithUsername(noteTaker));
      }
    }
  }

  @Given("order {string} is marked as {string}")
  public void order_is_marked_as(String orderIdString, String statusString) {
    int orderId = Integer.parseInt(orderIdString);
    ShipmentOrder.Status status = ShipmentOrder.Status.valueOf(statusString);
    List<ShipmentOrder> allOrders = wareFlow.getOrders();
    ShipmentOrder order = null;
    for (ShipmentOrder o: allOrders) {
  	  if (o.getId() == orderId) {
  		  order = o;
  	  }
    }
    if (order != null) {
    	switch(status) {
      	case Open:
      		break;
      	case Assigned:
      		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), order.getManagerApprovalRequired());
      		break;
      	case InProgress:
      		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), order.getManagerApprovalRequired());
      		order.startWork(order);
      		break;
      	case Completed:
      		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), true);
      		order.startWork(order);
      		order.completeOrder(order);
      		break;
      	case Closed:
      		order.assignOrder(order.getOrderPicker(), order.getPriority(), order.getTimeToFullfill(), order.getManagerApprovalRequired());
      		order.startWork(order);
      		order.completeOrder(order);
      		order.approve();
      		break;
      	default:
      		break;
      }
    }
    
    
  }

  @When("the manager attempts to view all shipment orders in the system")
  public void the_manager_attempts_to_view_all_shipment_orders_in_the_system() {
    allOrders = ShipmentOrderController.getOrders();
  }

  @When("the warehouse staff attempts to start the order {string}")
  public void the_warehouse_staff_attempts_to_start_the_order(String orderId) {
    int id = Integer.parseInt(orderId);
    error = ShipmentOrderController.StartWork(id);
  }

  @When("the manager attempts to assign the order {string} to {string} with estimated time {string}, priority {string}, and requires approval {string}")
  public void the_manager_attempts_to_assign_the_order_to_with_estimated_time_priority_and_requires_approval(
      String orderId, String username, String priority, String timeEstimate, String requireApproval) {
    int id = Integer.parseInt(orderId);
    error = ShipmentOrderController.assignOrderToStaff(id, username, priority, timeEstimate, requireApproval);
  }

  @When("the warehouse staff attempts to complete the order {string}")
  public void the_warehouse_staff_attempts_to_complete_the_order(String orderId) {
    int id = Integer.parseInt(orderId);
    error = ShipmentOrderController.MarkComplete(id);
  }

  @When("the manager attempts to disapprove the order {string} on date {string} and with reason {string}")
  public void the_manager_attempts_to_disapprove_the_order_on_date_and_with_reason(String id, String date, String reason){
	  int orderId = Integer.parseInt(id);
	    Date orderDate = Date.valueOf(date);
	    error = ShipmentOrderController.MarkDisaprroved(orderId, orderDate, reason);
  }

  @When("the manager attempts to approve the order {string}")
  public void the_manager_attempts_to_approve_the_order(String id) {
	  int orderId = Integer.parseInt(id);
	  error = ShipmentOrderController.MarkApproved(orderId);
  }

  @Then("the following shipment orders shall be presented")
  public void the_following_shipment_orders_shall_be_presented(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> orders = dataTable.asMaps();
    int i = 0;
    for (var order : orders) {
      TOShipmentOrder currentOrder = allOrders.get(i);
      int id = Integer.parseInt(order.get("id"));
      String orderPlacerUsername = order.get("orderPlacer");
      Date placedOnDate = Date.valueOf(order.get("placedOnDate"));
      String description = order.get("description");
      String assetName = order.get("assetName");
      int expectLifeSpan = Integer.parseInt(order.get("expectLifeSpan"));
      Date purchaseDate = Date.valueOf(order.get("purchaseDate"));
      int areaNumber = Integer.parseInt(order.get("areaNumber"));
      int slotNumber = Integer.parseInt(order.get("slotNumber"));
      int quantity = Integer.parseInt(order.get("quantity"));
      String status = order.get("status");
      String processedBy = order.get("processedBy");
      String timeToResolve = order.get("timeToResolve");
      String priority = order.get("priority");
      boolean approvalRequired = Boolean.parseBoolean(order.get("approvalRequired"));
      
      assertEquals(id, currentOrder.getId());
      assertEquals(orderPlacerUsername, currentOrder.getOrderPlacer());
      assertEquals(placedOnDate, currentOrder.getPlacedOnDate());
      assertEquals(description, currentOrder.getDescription());
      assertEquals(assetName, currentOrder.getItemName());
      assertEquals(expectLifeSpan, currentOrder.getExpectedLifeSpanInDays());
      assertEquals(purchaseDate, currentOrder.getAddedOnDate());
      assertEquals(areaNumber, currentOrder.getAreaNumber());
      assertEquals(slotNumber, currentOrder.getSlotNumber());
      assertEquals(quantity, currentOrder.getQuantity());
      assertEquals(status, currentOrder.getStatus());
      assertEquals(processedBy, currentOrder.getProcessedBy());
      assertEquals(timeToResolve, currentOrder.getTimeToResolve());
      assertEquals(priority, currentOrder.getPriority());
      assertEquals(approvalRequired, currentOrder.isApprovalRequired());
      
      i++;
    }
  }

  @Then("the order with id {string} shall have the following notes")
  public void the_order_with_id_shall_have_the_following_notes(String orderId,
      io.cucumber.datatable.DataTable dataTable) {
    int id = Integer.parseInt(orderId);
    List<TOShipmentOrder> allOrders = ShipmentOrderController.getOrders();
    TOShipmentOrder currentOrder = null;
    for (var order : allOrders) {
      if (order.getId() == id) {
        currentOrder = order;
      }
      if (currentOrder != null) {
    
      allNotes = currentOrder.getNotes();
      List<Map<String, String>> rows = dataTable.asMaps();
      int i = 0;
      for (var row : rows) {
        TOShipmentNote currentNote = allNotes.get(i);
        String noteTaker = row.get("noteTaker");
        Date date = Date.valueOf(row.get("addedOnDate"));
        String description = row.get("description");
        
        assertEquals(noteTaker, currentNote.getNoteTakerUsername());
        assertEquals(date, currentNote.getDate());
        assertEquals(description, currentNote.getDescription());
        
        i++;
      }
     }
    }
  }

  @Then("the order with id {string} shall have no notes")
  public void the_order_with_id_shall_have_no_notes(String orderId) {
    int id = Integer.parseInt(orderId);
    TOShipmentOrder currentOrder = null;
    for (var order : allOrders) {
      if (order.getId() == id) {
        currentOrder = order;
      }
    }
    
    assertNotNull(currentOrder);
    assertEquals(currentOrder.hasNotes(), false);
  }

  @Then("the order {string} shall not exist in the system")
  public void the_order_shall_not_exist_in_the_system(String orderId) {
    int id = Integer.parseInt(orderId);
    TOShipmentOrder currentOrder = null;
    
    if (allOrders == null) {
      assertNull(allOrders);
    } else {
      for (var order : allOrders) {
        if (order.getId() == id) {
          currentOrder = order;
          break;
        }
        assertNull(currentOrder);
      }
    }
  }

  @Then("the number of orders in the system shall be {string}")
  public void the_number_of_orders_in_the_system_shall_be(String expectedNumberOfOrders) {
    int numOrders = wareFlow.numberOfOrders();
    assertEquals((Integer)numOrders, Integer.parseInt(expectedNumberOfOrders));
  }

  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String expectedError) {
    assertEquals(expectedError, error);
  }

  @Then("the order {string} shall be marked as {string}")
  public void the_order_shall_be_marked_as(String orderId, String expectedStatus) {
    int id = Integer.parseInt(orderId);
    ShipmentOrder order = ShipmentOrder.getWithId(id);
    ShipmentOrder.Status status = order.getStatus();
    
    assertEquals(expectedStatus, status.toString());
  }

  @Then("the order {string} shall be assigned to {string}")
  public void the_order_shall_be_assigned_to(String orderId, String expectedStaff) {
    int id = Integer.parseInt(orderId);
    ShipmentOrder order = ShipmentOrder.getWithId(id);
    
    assertEquals(order.getOrderPicker(), WarehouseStaff.getWithUsername(expectedStaff));
  }

  @Then("the order {string} shall have estimated time {string}, priority {string}, and requires approval {string}")
  public void the_order_shall_have_estimated_time_priority_and_requires_approval(String orderId,
      String expectedTime, String expectedPriority, String expectedApproval) {
    int id = Integer.parseInt(orderId);
    ShipmentOrder order = ShipmentOrder.getWithId(id);
    ShipmentOrder.TimeEstimate timeEstimate = ShipmentOrder.TimeEstimate.valueOf(expectedTime);
    ShipmentOrder.PriorityLevel priorityLevel = ShipmentOrder.PriorityLevel.valueOf(expectedPriority);
    boolean approvalRequired = Boolean.parseBoolean(expectedApproval);
    
    assertEquals(timeEstimate, order.getTimeToFullfill());
    assertEquals(priorityLevel, order.getPriority());
    assertEquals(approvalRequired, order.getManagerApprovalRequired());
  }
}
