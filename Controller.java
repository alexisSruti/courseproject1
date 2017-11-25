/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Alexis
 */
public class Controller {
    
    SoftwareModelingFrame frame;
    RuntimeData data = new RuntimeData();
    
    public Controller(SoftwareModelingFrame frameIn) {
        frame = frameIn;
    }
    
    public void action(ActionEvent e) {
        if (e.getSource() == frame.getCheckoutButton()) {
            data.setProductList(ProductDAO.findAll());
            Collections.sort(data.getProductList());
            frame.checkoutButtonAction();
        } else if (e.getSource() == frame.getCheckoutCancelButton()) {
            data.setProductList(new ArrayList<>());
            data.setCheckoutList(new ArrayList<>());
            data.setCheckoutTotal(0);
            frame.checkoutCancelButtonAction();
        } else if (e.getSource() == frame.getManageButton()) {
            frame.manageButtonAction();
        } else if (e.getSource() == frame.getAddNewButton()) {
            frame.addNewButtonAction();
        } else if (e.getSource() == frame.getManageCancelButton()) {
            frame.manageCancelButtonAction();
        } else if (e.getSource() == frame.getUpdateButton()) {
            frame.updateButtonAction(ProductDAO.findAll());
        } else if (e.getSource() == frame.getCheckoutLookupCancel()) {
            frame.checkoutLookupCancelAction();
        } else if (e.getSource() == frame.getCheckoutLookupButton()) {
            frame.checkoutLookupButtonAction(data.getProductList());
        } else if (e.getSource() == frame.getEditLookupCancel()) {
            frame.editLookupCancelAction();
        } else if (e.getSource() == frame.getEditProductCancel()) {
            frame.editProductCancelAction();
        } else if (e.getSource() == frame.getAddProductCancel()) {
            frame.addProductCancelAction();
        } else if (e.getSource() == frame.getFinishAndPayButton()) {
            ArrayList<Product> productList = data.getProductList();
            ArrayList<Product> checkoutList = data.getCheckoutList();
            for (Product p : productList) {
                ProductDAO.updateProduct(p.getName(), p.getId(), p.getPrice(), p.getQuantity(), 
                        p.getVendor(), p.getExpiration());
            }
            int orderID = TotalOrderDAO.findOrderNumber() + 1;
            double total = 0;
            for (Product p : checkoutList) {
                OrderLineDAO.insertOrderLine(orderID, p.getId(), p.getQuantity(), p.getPrice() * p.getQuantity());
            }
            Date date = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = format1.format(date);
            TotalOrderDAO.insertTotalOrder(orderID, data.getCheckoutTotal(), formatted);
            data.setProductList(new ArrayList<>());
            data.setCheckoutList(new ArrayList<>());
            data.setCheckoutTotal(0);
            frame.finishAndPayButtonAction();
        } else if (e.getSource() == frame.getViewProfileButton()) {
            frame.viewProfileButtonAction(data.getUser());
        } else if (e.getSource() == frame.getAddUserButton()) {
            frame.addUserButtonAction();
        } else if (e.getSource() == frame.getNewUserCancelButton()) {
            frame.newUserCancelButtonAction();
        } else if (e.getSource() == frame.getLogOutButton()) {
            data.setUser(new User());
            frame.logOutButtonAction();
        } else if (e.getSource() == frame.getBusinessReportButton()) {
            frame.businessReportButtonAction();
        } else if (e.getSource() == frame.getBusinessReportCancelButton()) {
            frame.businessReportCancelButtonAction();
        } else if (e.getSource() == frame.getSoldDescButton()) {
            frame.soldDescButtonAction();
        } else if (e.getSource() == frame.getNameAscButton()) {
            frame.nameAscButtonAction();
        } else if (e.getSource() == frame.getNameDescButton()) {
            frame.nameDescButtonAction();
        } else if (e.getSource() == frame.getIdAscButton()) {
            frame.idAscButtonAction();
        } else if (e.getSource() == frame.getIdDescButton()) {
            frame.idDescButtonAction();
        } else if (e.getSource() == frame.getSoldAscButton()) {
            frame.soldAscButtonAction();
        } else if (e.getSource() == frame.getRevenueAscButton()) {
            frame.revenueAscButtonAction();
        } else if (e.getSource() == frame.getRevenueDescButton()) {
            frame.revenueDescButtonAction();
        } else if (e.getSource() == frame.getChangePasswordButton()) {
            frame.changePasswordButtonAction();
        } else if (e.getSource() == frame.getChangePictureButton()) {
            frame.changePictureButtonAction();
        } else if (e.getSource() == frame.getUserCancelButton()) {
            frame.userCancelButtonAction();
        }
    }
    
    public void action(ActionEvent e, HashMap<String, String> map) {
        if (e.getSource() == frame.getAddSubmitButton()) {
            String name = map.get(frame.NAME);
            int ID = 0;
            double price = 0;
            int quantity = 0;
            boolean success = true;
            try {
                ID = Integer.parseInt(map.get(frame.ID));
            } catch (NumberFormatException ex) {
                frame.showDialog("ID must be an integer.");
                success = false;
            }
            try {
                price = Double.parseDouble(map.get(frame.PRICE));
            } catch (NumberFormatException ex) {
                frame.showDialog("Price must be a number.");
                success = false;
            }
            try {
                quantity = Integer.parseInt(map.get(frame.QUANTITY));
            } catch (NumberFormatException ex) {
                frame.showDialog("Quantity must be an integer.");
                success = false;
            }
            String vendor = map.get(frame.VENDOR);
            if (name.equals("")) {
                frame.showDialog("Cannot leave name field blank.");
                success = false;
            }
            if (vendor.equals("")) {
                frame.showDialog("Cannot leave vendor field blank.");
            }
            String expiration = map.get(frame.EXPIRATION);
            Calendar exp;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (expiration.equals("null")) {
                    exp = null;
                } else {
                    exp = Calendar.getInstance();
                    exp.setTime(format1.parse(expiration));
                }
                if (success) {
                    ProductDAO.insertProduct(name, ID, price, quantity, vendor, exp);
                    frame.addSubmitButtonAction();
                }
            } catch (ParseException ex) {
                frame.showDialog("Please enter properly formatted date.");
            }
        } else if(e.getSource() == frame.getCheckoutLookupSubmit()) {
            try {
                ArrayList<Product> productList = data.getProductList();
                int id = Integer.parseInt(map.get(frame.ID));
                int quantity = Integer.parseInt(map.get(frame.QUANTITY));
                Product p = new Product();
                int low = 0;
                int high = productList.size();
                int i = 0;
                while (low <= high) {
                    i = low + ((high - low) / 2);
                    if (productList.get(i).getId() == id) {
                        p = productList.get(i);
                        break;
                    } else if (productList.get(i).getId() < id) {
                        low = i + 1;
                    } else {
                        high = i - 1;
                    }
                }
                if (p.getName().equals("null")) {
                    frame.showDialog("Please enter a valid Product ID.");
                } else if (quantity < 0) {
                    frame.showDialog("Please enter a valid quantity.");
                } else if (quantity > p.getQuantity()) {
                    frame.showDialog("Quantity cannot exceed the amount of product in stock.");
                } else if (quantity <= p.getQuantity()) {
                    double totalPrice = quantity * p.getPrice();
                    data.addToCheckoutTotal(totalPrice);
                    Product newProduct = new Product(p.getName(), p.getId(), p.getPrice(), quantity, p.getVendor(),
                        p.getExpiration());
                    data.addToCheckoutList(newProduct);
                    productList.get(i).setQuantity(p.getQuantity() - quantity);
                    frame.checkoutLookupSubmitAction(newProduct, totalPrice, data.getCheckoutTotal());
                }
            } catch (NumberFormatException ex) {
                frame.showDialog("Please enter properly formatted numbers.");
            }
        } else if (e.getSource() == frame.getEditLookupSubmit()) {
            try {
                int id = Integer.parseInt(map.get(frame.ID));
                Product p = ProductDAO.findById(id);
                if (p.getName().equals("null")) {
                    frame.showDialog("Please enter a valid Product ID.");
                } else if (!p.getName().equals("null")) {
                    Calendar expiration = p.getExpiration();
                    String formatted = "";
                    if (expiration == null) {
                        formatted = "null";
                    } else {
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                        formatted = format1.format(expiration.getTime());
                    }
                    frame.editLookupSubmitAction(p.getName(), p.getId(), p.getPrice(),
                        p.getQuantity(), p.getVendor(), formatted);
                }
            } catch (NumberFormatException ex) {
                frame.showDialog("Please enter properly formatted numbers.");
            }
        } else if (e.getSource() == frame.getEditSubmitButton()) {
            String name = map.get(frame.NAME);
            int ID = 0;
            double price = 0;
            int quantity = 0;
            boolean success = true;
            try {
                ID = Integer.parseInt(map.get(frame.ID));
            } catch (NumberFormatException ex) {
                frame.showDialog("ID must be an integer.");
                success = false;
            }
            try {
                price = Double.parseDouble(map.get(frame.PRICE));
            } catch (NumberFormatException ex) {
                frame.showDialog("Price must be a number.");
                success = false;
            }
            try {
                quantity = Integer.parseInt(map.get(frame.QUANTITY));
            } catch (NumberFormatException ex) {
                frame.showDialog("Quantity must be an integer.");
                success = false;
            }
            String vendor = map.get(frame.VENDOR);
            if (name.equals("")) {
                frame.showDialog("Cannot leave name field blank.");
                success = false;
            }
            if (vendor.equals("")) {
                frame.showDialog("Cannot leave vendor field blank.");
            }
            String expiration = map.get(frame.EXPIRATION);
            Calendar exp;
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (expiration.equals("null")) {
                    exp = null;
                } else {
                    exp = Calendar.getInstance();
                    exp.setTime(format1.parse(expiration));
                }
                if (success) {
                    ProductDAO.updateProduct(name, ID, price, quantity, vendor, exp);
                    frame.editSubmitButtonAction();
                }
            } catch (ParseException ex) {
                frame.showDialog("Please enter properly formatted date.");
            }
        } else if (e.getSource() == frame.getNewUserGeneratePasswordButton()) {
            String password = "def";
            String username = map.get(frame.USERNAME);
            if (!username.equals("")) {
                password += username.substring(0,1);
            }
            for (int i = 0; i < 3; i++) {
                int number = (int)(9 * Math.random()) + 1;
                password += number;
            }
            frame.newUserGeneratePasswordButtonAction(password);
        } else if (e.getSource() == frame.getLoginButton()) {
            String username = map.get(frame.USERNAME);
            String password = map.get(frame.PASSWORD);
            data.setUser(UserDAO.findByUsernameAndPassword(username, password));
            User user = data.getUser();
            if(user.getName().equals("null") && user.getId() == 0) {
                frame.showDialog("Incorrect username or password.");
            } else {
                frame.loginButtonAction(user);
            }
        } else if (e.getSource() == frame.getNewUserSubmitButton()) {
            String name = map.get(frame.NAME);
            int id = 0;
            id = UserDAO.findUserNumber() + 1;
            boolean isManager = false;
            if (map.get(frame.ISMANAGER).equals("true")) {
                isManager = true;
            }
            String username = map.get(frame.USERNAME);
            String password = map.get(frame.PASSWORD);
            int countOfUsernames = UserDAO.countUsername(username);
            if (name.equals("") || username.equals("") || password.equals("") || countOfUsernames > 0) {
                if (password.equals("")) {
                    frame.showDialog("Please generate a default password.");
                } else if (name.equals("") || username.equals("")) {
                    frame.showDialog("You cannot leave any fields blank.");
                }
                if (countOfUsernames > 0) {
                    frame.showDialog("You must select a unique username.");
                }
            } else {
                UserDAO.insertUser(name, id, isManager, username, password);
                frame.newUserSubmitButtonAction();
            }
        } else if (e.getSource() == frame.getPasswordSubmitButton()) {
            String password = map.get(frame.PASSWORD);
            User user = data.getUser();
            if(map.get(frame.OLD_PASSWORD).equals(user.getPassword())) {
                UserDAO.updateUser(user.getName(), user.getId(), user.isManager(),
                    user.getUsername(), password, user.getImage());
                user.setPassword(password);
                frame.passwordSubmitButtonAction1();
                frame.showDialog("Password updated successfully!");
            } else {
                frame.showDialog("Please ensure the current password is correct.");
            }
            frame.passwordSubmitButtonAction2();
        }
    }
    
    public static void setImageLabel(JLabel imageLabel, File imageFile) {
        Image imageFromFile;
        try {
                imageFromFile = ImageIO.read(imageFile).getScaledInstance(100, 100, Image.SCALE_DEFAULT);
                imageLabel.setIcon(new ImageIcon(imageFromFile));
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    public static ArrayList<BusinessReport> createBusinessReport(String sort) {
        return OrderLineDAO.getBusinessReportBy(sort);
    }
    
    public void changePicture(File file) {
        String filename = file.getName();
        if (!filename.endsWith(".png") && !filename.endsWith(".jpeg") && !filename.endsWith(".jpg")){
            frame.showDialog("Please select a jpeg or png file.");
        } else {
            User user = data.getUser();
            UserDAO.updateUser(user.getName(), user.getId(), user.isManager(), 
                    user.getUsername(), user.getPassword(), file);
            user.setImage(file);
            frame.changePictureButtonAction1(file);
        }
    }
    public static void checkDatabase() {
        ProductDAO.checkDatabase();
        UserDAO.checkDatabase();
        TotalOrderDAO.checkDatabase();
        OrderLineDAO.checkDatabase();
    }
    
}
