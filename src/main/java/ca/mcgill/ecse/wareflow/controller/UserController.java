package ca.mcgill.ecse.wareflow.controller;

import ca.mcgill.ecse.wareflow.model.Employee;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.wareflow.application.WareFlowApplication;
import ca.mcgill.ecse.wareflow.model.Client;
import ca.mcgill.ecse.wareflow.model.Manager;
import ca.mcgill.ecse.wareflow.model.User;
import ca.mcgill.ecse.wareflow.model.WareFlow;
import ca.mcgill.ecse.wareflow.persistence.WareFlowPersistence;

public class UserController {
  
  private static List<Employee> employees;

  private static WareFlow wareflow = WareFlowApplication.getWareFlow();

  public static String updateManager(String password) {
    Manager manager = wareflow.getManager();
    if (!wareflow.hasManager()) {
      return "Error: No manager.";
    }
    
    if (password == null || password.equals("")) {
      return "Password cannot be empty";
    }
    
    if(password.length() < 4) {
      return "Password must be at least four characters long";
    }
    
    if(!password.matches(".*[!#$].*")) {
      return "Password must contain one character out of !#$";
    }
    
    if(!password.matches(".*[a-z].*")) {
      return "Password must contain one lower-case character";
    }
    
    if(!password.matches(".*[A-Z].*")) {
      return "Password must contain one upper-case character";
    }
    
    try {
      manager.setPassword(password);
      return "";
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  // address is ignored if the isEmployee is true
  public static String addEmployeeOrClient(String username, String password, String name,
      String phoneNumber, boolean isEmployee, String address) {
    if (username == null || username.equals("")) {
      return "Username cannot be empty";
    }

    if (username.equals("manager")) {
      return "Username cannot be manager";
    }

    if (!validUsername(username)) {
      return "Invalid username";
    }

    if (password == null || password.equals("")) {
      return "Password cannot be empty";
    }

    if (User.hasWithUsername(username)) {
      if (isEmployee) {
        return "Username already linked to an employee account";
      } else {
        return "Username already linked to a client account";
      }
    }

    try {
      if (isEmployee) {
        wareflow.addEmployee(username, name, password, phoneNumber);
        WareFlowPersistence.save();
      } else {
        wareflow.addClient(username, name, password, phoneNumber, address);
        WareFlowPersistence.save();
      }
      return "";
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  private static Boolean validUsername(String username) {
    for (char c : username.toCharArray()) {
      if (!Character.isLetterOrDigit(c)) {
        return false;
      }
    }
    return true;
  }

  // newAddress is ignored if the user is an employee
  public static String updateEmployeeOrClient(String username, String newPassword, String newName,
      String newPhoneNumber, String newAddress) {
    if (username == null || username.equals("")) {
      return "Error: Username cannot be empty.";
    }

    if (newPassword == null || newPassword.equals("")) {
      return "Password cannot be empty";
    }

    try {
      if (!User.hasWithUsername(username)) {
        return "Error: User does not exist.";
      }
      if (newAddress == null || newAddress.equals("")) {
        User employee = Employee.getWithUsername(username);
        employee.delete();
        wareflow.addEmployee(username, newName, newPassword, newPhoneNumber);
        WareFlowPersistence.save();
      } else {
        User client = Client.getWithUsername(username);
        client.delete();
        wareflow.addClient(username, newName, newPassword, newPhoneNumber, newAddress);
        WareFlowPersistence.save();
      }
      return "";
    } catch (Exception e) {
      return e.getMessage();
    }
  
  }

  public static void deleteEmployeeOrClient(String username) {
    if (wareflow.getManager() != null && wareflow.getManager().getUsername().equals(username)) {
      return;
    }
    
    try {
      User user = User.getWithUsername(username);
      WareFlowPersistence.save();

      if (user != null) {
        user.delete();
        WareFlowPersistence.save();
      }
    } catch (Exception e) {

    }
  }

  public static List<TOUser> getEmployees() {
    List<TOUser> toUsers = new ArrayList<TOUser>();

    List<Employee> employees = wareflow.getEmployees();

    for(Employee e : employees) {
      
        String username = e.getUsername();
        String password = e.getPassword();
        String name = e.getName();
        String phoneNumber = e.getPhoneNumber();

        TOUser toUser = new TOUser(username, password, name, phoneNumber, true, null);
        toUsers.add(toUser);

    }
    return toUsers;
  }
}
