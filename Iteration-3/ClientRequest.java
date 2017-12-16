/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

import java.util.HashMap;

/**
 * Comprised of a command string and a Hashmap where the values are Json strings.
 * @author Alexis
 */
public class ClientRequest {
    private String command;
    private HashMap<String, String> data;
    
    // Command constants
    static final String PRODUCT_FIND_ALL = "Get All Products";
    static final String DO_CHECKOUT = "Do Checkout";
    static final String INSERT_PRODUCT = "Insert Product";
    static final String FIND_BY_ID = "Find By Id";
    static final String UPDATE_PRODUCT = "Update Product";
    static final String DO_LOGIN = "Find User by Username and Password";
    static final String FIND_USER_NUMBER = "Find User Number";
    static final String COUNT_USERNAME = "Count Username";
    static final String INSERT_USER = "Insert User";
    static final String UPDATE_USER = "Update User";
    static final String BUSINESS_REPORT = "Get Business Report By Sort";
    static final String CHECK_DATABASE = "Database Check";
    static final String GET_ORDER_ID = "Get Order Id";
    
    // Data key constants
    static final String PRODUCT_LIST = "product list";
    static final String CHECKOUT_LIST = "checkout list";
    static final String CHECKOUT_TOTAL = "checkout total";
    static final String PRODUCT = "product";
    static final String ID = "id";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String USER = "user";
    static final String SORT = "sort";

    public ClientRequest(String type, HashMap<String, String> data) {
        this.command = type;
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public HashMap<String, String> getData() {
        return data;
    }
    
    @Override
    public String toString() {
        String ret;
        if (data != null) {
            ret = "Client Command: " + command + ". Client Data: " + data + ".\n";
        } else {
            ret = "Client Command: " + command + ". Client Data: null.\n";
        }
        return ret;
    }
}
