/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

import java.util.ArrayList;

/**
 *
 * @author Alexis
 */
public class RuntimeData {
    double checkoutTotal = 0;
    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Product> checkoutList = new ArrayList<>();
    UserProxy user = new UserProxy();
    
    public void setProductList(ArrayList<Product> list) {
        productList = list;
    }
    public ArrayList<Product> getProductList() {
        return productList;
    }
    public void setCheckoutList(ArrayList<Product> list) {
        checkoutList = list;
    }
    public ArrayList<Product> getCheckoutList() {
        return checkoutList;
    }
    public void addToCheckoutList(Product added) {
        checkoutList.add(added);
    }
    public void setCheckoutTotal(double total) {
        checkoutTotal = total;
    }
    public double getCheckoutTotal() {
        return checkoutTotal;
    }
    public void addToCheckoutTotal(double added) {
        checkoutTotal += added;
    }
    public UserProxy getUser() {
        return user;
    }
    public void setUser(UserProxy userIn) {
        user = userIn;
    }
}
