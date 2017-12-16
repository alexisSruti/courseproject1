/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Alexis
 */
public class RemoteDataAdapter {
    static final Properties PROPERTIES = loadProperties();
    
    static final String SERVER_HOSTNAME = new String(PROPERTIES.getProperty("CLIENT_IP"));
    static final int PORT_NUMBER = Integer.parseInt(PROPERTIES.getProperty("CLIENT_PORT"));
    private Gson gson = new Gson();
    
    public ArrayList<Product> productFindAll() {
        ArrayList<Product> list = new ArrayList<>();
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
        ClientRequest request = new ClientRequest(ClientRequest.PRODUCT_FIND_ALL, null);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        list = gson.fromJson(serverAnswer, new TypeToken<ArrayList<Product>>(){}.getType());

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public void doCheckout(ArrayList<Product> productList, ArrayList<Product> checkoutList, double checkoutTotal) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String,String> map = new HashMap<>();
        String productListJson = gson.toJson(productList, new TypeToken<ArrayList<Product>>(){}.getType());
        map.put(ClientRequest.PRODUCT_LIST, productListJson);
        String checkoutListJson = gson.toJson(checkoutList, new TypeToken<ArrayList<Product>>(){}.getType());
        map.put(ClientRequest.CHECKOUT_LIST, checkoutListJson);
        String checkoutTotalJson = gson.toJson(checkoutTotal, Double.class);
        map.put(ClientRequest.CHECKOUT_TOTAL, checkoutTotalJson);
        ClientRequest request = new ClientRequest(ClientRequest.DO_CHECKOUT, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        String response = gson.fromJson(serverAnswer, String.class);
        System.out.println("Server response: " + response);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void insertProduct(Product p) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, String> map = new HashMap<>();
        String productJson = gson.toJson(p, Product.class);
        map.put(ClientRequest.PRODUCT, productJson);
        ClientRequest request = new ClientRequest(ClientRequest.INSERT_PRODUCT, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        String response = gson.fromJson(serverAnswer, String.class);
        System.out.println("Server response: " + response);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Product productFindById(int id) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        Product p = new Product();
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, String> map = new HashMap<>();
        String idJson = gson.toJson(id, Integer.class);
        map.put(ClientRequest.ID, idJson);
        ClientRequest request = new ClientRequest(ClientRequest.FIND_BY_ID, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        p = gson.fromJson(serverAnswer, Product.class);
        System.out.println("Server response: " + serverAnswer);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p;
    }
    public void updateProduct(Product p) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, String> map = new HashMap<>();
        String productJson = gson.toJson(p, Product.class);
        map.put(ClientRequest.PRODUCT, productJson);
        ClientRequest request = new ClientRequest(ClientRequest.UPDATE_PRODUCT, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        String response = gson.fromJson(serverAnswer, String.class);
        System.out.println("Server response: " + response);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public User userFindByUsernameAndPassword(String username, String password) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        User user = new User();
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, String> map = new HashMap<>();
        String usernameJson = gson.toJson(username, String.class);
        map.put(ClientRequest.USERNAME, usernameJson);
        String passwordJson = gson.toJson(password, String.class);
        map.put(ClientRequest.PASSWORD, passwordJson);
        ClientRequest request = new ClientRequest(ClientRequest.DO_LOGIN, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        user = gson.fromJson(serverAnswer, User.class);
        System.out.println("Server response: " + serverAnswer);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return user;
    } 
    public int findUserNumber() {
        int id = 0;
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
        ClientRequest request = new ClientRequest(ClientRequest.FIND_USER_NUMBER, null);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        id = gson.fromJson(serverAnswer, Integer.class);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    public int userCountUsername(String username) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        int count = 0;
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, String> map = new HashMap<>();
        String usernameJson = gson.toJson(username, String.class);
        map.put(ClientRequest.USERNAME, usernameJson);
        ClientRequest request = new ClientRequest(ClientRequest.COUNT_USERNAME, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        count = gson.fromJson(serverAnswer, Integer.class);
        System.out.println("Server response: " + serverAnswer);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return count;
    }
    public void insertUser(User user) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, String> map = new HashMap<>();
        String userJson = gson.toJson(user, User.class);
        map.put(ClientRequest.USER, userJson);
        ClientRequest request = new ClientRequest(ClientRequest.INSERT_USER, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        String response = gson.fromJson(serverAnswer, String.class);
        System.out.println("Server response: " + response);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void updateUser(UserProxy userIn) {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, String> map = new HashMap<>();
        User user = new User(userIn.getName(), userIn.getId(), userIn.isManager(),
            userIn.getUsername(), userIn.getPassword(), userIn.getImageFile());
        String userJson = gson.toJson(user, User.class);
        map.put(ClientRequest.USER, userJson);
        ClientRequest request = new ClientRequest(ClientRequest.UPDATE_USER, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        String response = gson.fromJson(serverAnswer, String.class);
        System.out.println("Server response: " + response);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public ArrayList<BusinessReport> getBusinessReportBy(String sort) {
        ArrayList<BusinessReport> list = new ArrayList<>();
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
        HashMap<String, String> map = new HashMap<>();
        String sortJson = gson.toJson(sort, String.class);
        map.put(ClientRequest.SORT, sortJson);
        ClientRequest request = new ClientRequest(ClientRequest.BUSINESS_REPORT, map);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        list = gson.fromJson(serverAnswer, new TypeToken<ArrayList<BusinessReport>>(){}.getType());

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public static void checkDatabase() {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        ClientRequest request = new ClientRequest(ClientRequest.CHECK_DATABASE, null);
        String toSend = "{\"command\":\"Database Check\"}";
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        System.out.println("Server response: " + serverAnswer);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public int getOrderId() {
        System.out.println("Attemping to connect to host " + SERVER_HOSTNAME + " on port " + PORT_NUMBER);
        int id = 0;
        
        try {
        Socket socket = new Socket(SERVER_HOSTNAME, PORT_NUMBER);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        ClientRequest request = new ClientRequest(ClientRequest.GET_ORDER_ID, null);
        String toSend = gson.toJson(request, ClientRequest.class);
        System.out.println("Sending: " + toSend);
        out.println(toSend);
        String serverAnswer = in.readLine();
        id = gson.fromJson(serverAnswer, Integer.class);
        System.out.println("Server response: " + serverAnswer);

        out.close();
        in.close();
        stdIn.close();
        socket.close();
        System.out.println("Connection closed");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    
    private static Properties loadProperties() {
        InputStream input = null;
        Properties properties = new Properties();
        try {
            input = new FileInputStream("client.properties");
            properties.load(input);
           } catch (IOException ex) {
                ex.printStackTrace();
           } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
        return properties;
    }
}
