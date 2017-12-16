package softwaremodelingproject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author Alexis
 */
public class StoreServer extends Thread {
    static final Properties PROPERTIES = loadProperties();
    
    protected Socket clientSocket;
    private Gson gson = new Gson();
    
    StoreServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
        start();
    }
    
    public void run() {
        System.out.println("New communication thread started for client socket " + clientSocket.getInetAddress() + " at " + System.currentTimeMillis());

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String json = in.readLine();
                if (json == null) break;

                ClientRequest request = gson.fromJson(json, ClientRequest.class);
                String command = request.getCommand();   // convert JSON text into a Request object
                System.out.println(command);
                
                if (command.equals(ClientRequest.PRODUCT_FIND_ALL)) {
                    ArrayList<Product> productList = new ArrayList<>();
                    productList = ProductDAO.findAll();
                    String response = gson.toJson(productList, new TypeToken<ArrayList<Product>>(){}.getType());
                    out.println(response);
                } else if (command.equals(ClientRequest.DO_CHECKOUT)) {
                    ArrayList<Product> list = new ArrayList<>();
                    list = gson.fromJson(request.getData().get(ClientRequest.PRODUCT_LIST), 
                            new TypeToken<ArrayList<Product>>(){}.getType());
                    for (Product p : list) {
                        ProductDAO.updateProduct(p.getName(), p.getId(), p.getPrice(), p.getQuantity(), 
                                p.getVendor(), p.getExpiration());
                    }
                    list = gson.fromJson(request.getData().get(ClientRequest.CHECKOUT_LIST), 
                            new TypeToken<ArrayList<Product>>(){}.getType());
                    int orderID = TotalOrderDAO.findOrderNumber() + 1;
                    for (Product p : list) {
                        OrderLineDAO.insertOrderLine(orderID, p.getId(), p.getQuantity(), p.getPrice() * p.getQuantity());
                    }
                    double checkoutTotal = gson.fromJson(request.getData().get(ClientRequest.CHECKOUT_TOTAL),
                            Double.class);
                    Date date = new Date();
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatted = format1.format(date);
                    TotalOrderDAO.insertTotalOrder(orderID, checkoutTotal, formatted);
                    String response = gson.toJson("Done", String.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.INSERT_PRODUCT)) {
                    Product p = gson.fromJson(request.getData().get(ClientRequest.PRODUCT), Product.class);
                    ProductDAO.insertProduct(p.getName(), p.getId(), p.getPrice(), p.getQuantity(),
                            p.getVendor(), p.getExpiration());
                    String response = gson.toJson("Done", String.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.FIND_BY_ID)) {
                    int id = gson.fromJson(request.getData().get(ClientRequest.ID), Integer.class);
                    Product p = ProductDAO.findById(id);
                    String response = gson.toJson(p, Product.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.UPDATE_PRODUCT)) {
                    Product p = gson.fromJson(request.getData().get(ClientRequest.PRODUCT), Product.class);
                    ProductDAO.updateProduct(p.getName(), p.getId(), p.getPrice(), p.getQuantity(),
                            p.getVendor(), p.getExpiration());
                    String response = gson.toJson("Done", String.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.DO_LOGIN)) {
                    String username = gson.fromJson(request.getData().get(ClientRequest.USERNAME), 
                            String.class);
                    String password = gson.fromJson(request.getData().get(ClientRequest.PASSWORD),
                            String.class);
                    User user = UserDAO.findByUsernameAndPassword(username, password);
                    String response = gson.toJson(user, User.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.FIND_USER_NUMBER)) {
                    int id = 0;
                    id = UserDAO.findUserNumber();
                    String response = gson.toJson(id, Integer.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.COUNT_USERNAME)) {
                    int count = 0;
                    String username = gson.fromJson(request.getData().get(ClientRequest.USERNAME),
                            String.class);
                    count = UserDAO.countUsername(username);
                    String response = gson.toJson(count, Integer.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.INSERT_USER)) {
                    User user = gson.fromJson(request.getData().get(ClientRequest.USER), User.class);
                    UserDAO.insertUser(user.getName(), user.getId(), user.isManager(),
                            user.getUsername(), user.getPassword());
                    String response = gson.toJson("Done", String.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.UPDATE_USER)) {
                    UserProxy user = gson.fromJson(request.getData().get(ClientRequest.USER),
                            UserProxy.class);
                    UserDAO.updateUser(user.getName(), user.getId(), user.isManager(),
                        user.getUsername(), user.getPassword(), user.getImageFile());
                    String response = gson.toJson("Done", String.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.BUSINESS_REPORT)) {
                    ArrayList<BusinessReport> list = new ArrayList<>();
                    String sort = gson.fromJson(request.getData().get(ClientRequest.SORT), String.class);
                    list = OrderLineDAO.getBusinessReportBy(sort);
                    String response = gson.toJson(list, new TypeToken<ArrayList<BusinessReport>>(){}.getType());
                    out.println(response);
                } else if (command.equals(ClientRequest.CHECK_DATABASE)) {
                    ProductDAO.checkDatabase();
                    UserDAO.checkDatabase();
                    TotalOrderDAO.checkDatabase();
                    OrderLineDAO.checkDatabase();
                    String response = gson.toJson("Done", String.class);
                    out.println(response);
                } else if (command.equals(ClientRequest.GET_ORDER_ID)) {
                    int orderID = TotalOrderDAO.findOrderNumber();
                    String response = gson.toJson(orderID, Integer.class);
                    out.println(response);
                }
                
                if (command.equals("BYE")) break;

            }
            System.out.println("Connection closed for client from " + clientSocket.getInetAddress());
            out.close();
            in.close();
            clientSocket.close();

        } catch (Exception e) {
            System.err.println("Problem with Communication Server" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(PROPERTIES.getProperty("SERVER_PORT"));
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server Socket created!");
        while (true) {
            System.out.println("Waiting for a new connection...");
            new StoreServer(serverSocket.accept()); // if there is a client connecting
            //thread.start(); // then make a new Server thread and run it
        }
    }
    
    private static Properties loadProperties() {
        InputStream input = null;
        Properties properties = new Properties();
        try {
            input = new FileInputStream("server.properties");
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

