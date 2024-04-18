/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.wareflow.controller;

// line 36 "../../../../../WareFlowTransferObjects.ump"
public class TOManager
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOManager Attributes
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOManager(String aPassword)
  {
    password = aPassword;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "]";
  }
}