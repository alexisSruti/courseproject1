/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwaremodelingproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
/**
 *
 * @author Alexis
 */
public class OrderLineDAO {
    //Properties file
    static final Properties PROPERTIES = loadProperties();

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = PROPERTIES.getProperty("DB_URL");

    // Database credentials
    static final String USER = PROPERTIES.getProperty("USERNAME");
    static final String PASSWORD = PROPERTIES.getProperty("PASSWORD");

    public static ArrayList<BusinessReport> getBusinessReportBy(String sort) {
        ArrayList<BusinessReport> reportList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT StoreManagement.Product.ProductName, StoreManagement.Product.ProductID, "
                    + "sum(StoreManagement.OrderLine.Quantity) as TotalSold, sum(StoreManagement.OrderLine.Cost) "
                    + "as TotalRevenue FROM StoreManagement.Product JOIN StoreManagement.OrderLine ON "
                    + "StoreManagement.Product.ProductID = StoreManagement.OrderLine.ProductID GROUP BY "
                    + "StoreManagement.OrderLine.ProductID ORDER BY " + sort + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("ProductName");
		int id = rs.getInt("ProductID");
		int totalSold = rs.getInt("TotalSold");
                double totalRevenue = rs.getDouble("TotalRevenue");
                BusinessReport c = new BusinessReport(name, id, totalSold, totalRevenue);
                reportList.add(c);
            }
        }
        catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
	} catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
	} finally {
            // finally block used to close resources
            try {
		if (stmt != null) {
                    stmt.close();
		}
            } catch (SQLException se2) {
		// nothing we can do }
		try {
                    if (conn != null) {
			conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
		} // end finally try
            } // end try
	}
        return reportList;
    }

    public static void insertOrderLine(int productId, int orderId, int quantity, double cost) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "INSERT INTO StoreManagement.OrderLine VALUES (" + productId + ", " + orderId
                    + ", " + quantity + ", " + cost + ");";
            stmt.executeUpdate(sql);
        }
        catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
	} catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
	} finally {
            // finally block used to close resources
            try {
		if (stmt != null) {
                    stmt.close();
		}
            } catch (SQLException se2) {
		// nothing we can do }
		try {
                    if (conn != null) {
			conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
		} // end finally try
            } // end try
	}
    }

    private static Properties loadProperties() {
        InputStream input = null;
        Properties properties = new Properties();
        try {
            input = new FileInputStream("db.properties");
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

    public static void checkDatabase() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            try {
                String sql = "SELECT * FROM StoreManagement.OrderLine";
                stmt.executeQuery(sql);
            } catch (Exception ex) {
                String sql = "USE StoreManagement";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE OrderLine(OrderID int not NULL, ProductID int not NULL, \n" +
                    "Quantity int, Cost decimal(9,2));";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO OrderLine VALUES (1, 987652, 5, 17.50);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO OrderLine VALUES (1, 2719364, 3, 6.30);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO OrderLine VALUES (1, 3819038, 3, 4.68)";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO OrderLine VALUES (2, 9320193, 2, 11.76);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO OrderLine VALUES (2, 7568087, 4, 7.48);";
                stmt.executeUpdate(sql);
            }
        }
        catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
	} catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
	} finally {
            // finally block used to close resources
            try {
		if (stmt != null) {
                    stmt.close();
		}
            } catch (SQLException se2) {
		// nothing we can do }
		try {
                    if (conn != null) {
			conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
		} // end finally try
            } // end try
	}
    }

}

