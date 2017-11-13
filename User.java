package softwaremodelingproject;
import java.util.*;
public class User implements Comparable {
  private String name;
  private int id;
  private boolean isManager;
  public User() {
    name = "null";
    id = 0;
    isManager = false;
  }
  public User(String nameIn, int idIn, boolean isManagerIn) {
    name = nameIn;
    id = idIn;
    isManager = isManagerIn;
  }

  //Getters
  public String getName() {
    return name;
  }
  public int getId() {
    return id;
  }
  public boolean getIsManager() {
    return isManager;
  }

  //Setters
  public void setName(String nameIn) {
    this.name = nameIn;
  }
  public void setId(int idIn) {
    this.id = idIn;
  }
  public void setIsManager(boolean isManagerIn) {
    this.isManager = isManagerIn;
  }

  //toString method
  public String toString() {
    String str = "Name: " + name + " ID: " + id + " isManager: " + isManager;
    return str;
  }

  //Override compareTo method
  public int compareTo(Object c) {
    int cId = ((User) c).getId();
    return this.id-cId;
  }

}