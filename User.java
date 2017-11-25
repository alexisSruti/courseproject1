package softwaremodelingproject;

import java.io.File;
import java.util.*;

/**
 *
 * @author Sruti/Alexis
 */
public class User implements Comparable {
  private String name;
  private int id;
  private boolean isManager;
  private String username;
  private String password;
  private File image;
  public User() {
    name = "null";
    id = 0;
    isManager = false;
    username = "null";
    password = "null";
    image = null;
  }
  public User(String nameIn, int idIn, boolean isManagerIn, String usernameIn, String passwordIn, File imageIn) {
    name = nameIn;
    id = idIn;
    isManager = isManagerIn;
    username = usernameIn;
    password = passwordIn;
    image = imageIn;
  }

  //Getters
  public String getName() {
    return name;
  }
  public int getId() {
    return id;
  }
  public boolean isManager() {
    return isManager;
  }
  public String getUsername() {
    return username;
  }
  public String getPassword() {
    return password;
  }
  public File getImage() {
      return image;
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
  public void setUsername(String usernameIn) {
    this.username = usernameIn;
  }
  public void setPassword(String passwordIn) {
    this.password = passwordIn;
  }
  public void setImage(File imageIn) {
      image = imageIn;
  }
  //toString method
  public String toString() {
    String str = "Name: " + name + " ID: " + id + " isManager: " + isManager + " Username: " + username +
                  " Password: " + password;
    return str;
  }

  //Override compareTo method
  public int compareTo(Object c) {
    int cId = ((User) c).getId();
    return this.id-cId;
  }

}