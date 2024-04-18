/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.wareflow.controller;

// line 40 "../../../../../WareFlowTransferObjects.ump"
public class TOUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOUser Attributes
  private String username;
  private String password;
  private String name;
  private String phoneNumber;
  private boolean isEmployee;
  private String address;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOUser(String aUsername, String aPassword, String aName, String aPhoneNumber, boolean aIsEmployee, String aAddress)
  {
    username = aUsername;
    password = aPassword;
    name = aName;
    phoneNumber = aPhoneNumber;
    isEmployee = aIsEmployee;
    address = aAddress;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsEmployee(boolean aIsEmployee)
  {
    boolean wasSet = false;
    isEmployee = aIsEmployee;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public boolean getIsEmployee()
  {
    return isEmployee;
  }

  public String getAddress()
  {
    return address;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsEmployee()
  {
    return isEmployee;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "isEmployee" + ":" + getIsEmployee()+ "," +
            "address" + ":" + getAddress()+ "]";
  }
}