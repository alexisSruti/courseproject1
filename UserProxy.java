/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Alexis
 */
public class UserProxy {

    User heldUser;
    Image imageActual;
    Boolean imageNeedsLoading;

    public UserProxy(String nameIn, int idIn, boolean isManagerIn, String usernameIn, String passwordIn, File imageIn) {
        heldUser = new User(nameIn, idIn, isManagerIn, usernameIn, passwordIn, imageIn);
        imageNeedsLoading = true;
    }
    public UserProxy(User user) {
        this.heldUser = user;
        imageNeedsLoading = true;
    }
    public UserProxy() {
        
    }

    //Getters
    public String getName() {
        return heldUser.getName();
    }

    public int getId() {
        return heldUser.getId();
    }

    public boolean isManager() {
        return heldUser.isManager();
    }

    public String getUsername() {
        return heldUser.getUsername();
    }

    public String getPassword() {
        return heldUser.getPassword();
    }

    public File getImageFile() {
        return heldUser.getImage();
    }
    public Image getImage() {
        if (imageNeedsLoading) {
            loadImage();
        }
        return imageActual;
    }

    //Setters
    public void setUser(User userIn) {
        heldUser = userIn;
    }
    public void setName(String nameIn) {
        heldUser.setName(nameIn);
    }

    public void setId(int idIn) {
        heldUser.setId(idIn);
    }

    public void setIsManager(boolean isManagerIn) {
        heldUser.setIsManager(isManagerIn);
    }

    public void setUsername(String usernameIn) {
        heldUser.setUsername(usernameIn);
    }

    public void setPassword(String passwordIn) {
        heldUser.setPassword(passwordIn);
    }

    public void setImage(File imageIn) {
        heldUser.setImage(imageIn);
        imageNeedsLoading = true;
    }
    public void loadImage() {
        try {
                imageActual = ImageIO.read(heldUser.getImage()).getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                imageNeedsLoading = false;
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    //toString method

    public String toString() {
        String str = "Name: " + heldUser.getName() + " ID: " + heldUser.getId() + " isManager: " + heldUser.isManager()
                + " Username: " + heldUser.getUsername() + " Password: " + heldUser.getPassword();
        return str;
    }
}
