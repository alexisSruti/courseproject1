/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

import java.util.Calendar;

/**
 *
 * @author Alexis
 */
public class Product implements Comparable {
    private String name;
    private int id;
    private double price;
    private int quantity;
    private String vendor;
    private Calendar expiration;
    
    public Product() {
        name = "null";
        id = 0;
        price = 0;
        quantity = 0;
        vendor = "null";
        expiration = null;
    }
    public Product(String name, int id, double price, int quantity, String vendor, 
            Calendar expiration) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.vendor = vendor;
        this.expiration = expiration;
    }
    public void setName(String str){
        name = str;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setVendor(String str) {
        vendor = str;
    }
    public String getVendor() {
        return vendor;
    }
    public void setExpiration(Calendar c) {
        expiration = c;
    }
    public Calendar getExpiration() {
        return expiration;
    }
    public String toString() {
        String str = "Name: " + name + " ID:  " + id + " Price: " + price + " Quantity: " + quantity
                + " Vendor: " + vendor + " Expiration Date: ";
        if (expiration != null) {
            str += expiration.getTime().toString();
        } else if (expiration == null) {
            str += "NULL";
        }
        return str;
    }
    @Override
    public int compareTo(Object p) {
        int pId=((Product)p).getId();
        /* For Ascending order*/
        return this.id-pId;
    }
}
