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
public class TotalOrderDAO {
    //Properties file
    static final Properties PROPERTIES = loadProperties();

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = PROPERTIES.getProperty("DB_URL");

    // Database credentials
    static final String USER = PROPERTIES.getProperty("USERNAME");
    static final String PASSWORD = PROPERTIES.getProperty("PASSWORD");

    public static int findOrderNumber() {
        int id = 0;
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT OrderID FROM StoreManagement.TotalOrder ORDER BY OrderID DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		id = rs.getInt("OrderID");
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
        return id;
    }

    public static void insertTotalOrder(int orderId, double totalCost, String date) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "INSERT INTO StoreManagement.TotalOrder VALUES (" + orderId + ", " + totalCost 
                    + ", DATE '" + date + "');";
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
                String sql = "SELECT * FROM StoreManagement.TotalOrder";
                stmt.executeQuery(sql);
            } catch (Exception ex) {
                String sql = "USE StoreManagement";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE TotalOrder(OrderID int not NULL primary key, TotalCost decimal(9,2), \n" +
                    "Date datetime);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO TotalOrder VALUES (1, 28.48, DATE '2017-11-19');";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO TotalOrder VALUES (2, 19.24, DATE '2017-11-17');";
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
