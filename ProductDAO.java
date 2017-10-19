package softwaremodelingproject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *
 * @author Alexis
 */
public class ProductDAO {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/StoreManagement?useSSL=false";

    // Database credentials
    static final String USER = "root";
    static final String PASSWORD = "pass";

    public static ArrayList<Product> findAll() {
        ArrayList<Product> productList = new ArrayList<Product>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Product";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("ProductName");
		int ID = rs.getInt("ProductID");
		double price = rs.getDouble("Price");
		int quantity = rs.getInt("Quantity");
                String vendor = rs.getString("Vendor");
                Date x = rs.getDate("Expiration");
                Calendar expiration = Calendar.getInstance();
                if (x != null) {
                    expiration.setTime(x);
                } else if (x == null) {
                    expiration = null;
                }
                Product p = new Product(name, ID, price, quantity, vendor, expiration);
                productList.add(p);
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
        return productList;
    }
    public static Product findById(int ID) {
        Product product = new Product();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM Product WHERE ProductID = " + ID;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("ProductName");
		double price = rs.getDouble("Price");
		int quantity = rs.getInt("Quantity");
                String vendor = rs.getString("Vendor");
                Date x = rs.getDate("Expiration");
                Calendar expiration = Calendar.getInstance();
                if (x != null) {
                    expiration.setTime(x);
                } else if (x == null) {
                    expiration = null;
                }
                Product p = new Product(name, ID, price, quantity, vendor, expiration);
                System.out.println(p);
                product = p;
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
        return product;
    }
    public static void insertProduct(String name, int id, double price, int quantity, String vendor, 
            Calendar expiration) {
        Connection conn = null;
        Statement stmt = null;
        String formatted = "";
        if (expiration == null) {
            formatted = "null";
        } else {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            formatted = format1.format(expiration.getTime());
        }
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql;
            if (formatted.equals("null")) {
                sql = "INSERT INTO Product VALUES (" + id + ", '" + name + "', " + price + ", " 
                    + quantity + ", " + formatted + ", '" + vendor + "');";
            } else {
                sql = "INSERT INTO Product VALUES (" + id + ", '" + name + "', " + price + ", " 
                    + quantity + ", DATE '" + formatted + "', '" + vendor + "');";
            }
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
    public static void updateProduct(String name, int id, double price, int quantity, String vendor, 
            Calendar expiration) {
        Connection conn = null;
        Statement stmt = null;
        String formatted = "";
        if (expiration == null) {
            formatted = "null";
        } else {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            formatted = format1.format(expiration.getTime());
        }
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "";
            if (formatted.equals("null")) {
                sql = "UPDATE Product SET ProductName = '" + name + "', Price = " + price + ", Quantity = " 
                    + quantity + ", Vendor = '" + vendor + "' WHERE"
                    + " ProductID = " + id + ";";
            } else {
                sql = "UPDATE Product SET ProductName = '" + name + "', Price = " + price + ", Quantity = " 
                    + quantity + ", Expiration = DATE '" + formatted + "', Vendor = '" + vendor + "' WHERE"
                    + " ProductID = " + id + ";";
            }
            //System.out.println(sql);
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
    public static void createAndFillTable (){
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE Product (ProductID int not NULL primary key,\n" +
                "ProductName char(30) not NULL, Price decimal(9,2), Quantity int,\n" +
                "Expiration datetime, Vendor char(30));";
            stmt.executeQuery(sql);
            sql = "INSERT INTO Product VALUES(7568087, 'Apple', 1.87, 5, DATE '2017-10-27', 'FarmersMarket');";
            stmt.executeQuery(sql);
            sql = "INSERT INTO Product VALUES(0987652, 'Pineapple', 3.50, 1, DATE '2017-10-27', 'DelMonte');";
            stmt.executeQuery(sql);
            sql = "INSERT INTO Product VALUES (2719364, 'Paper Plate', 2.10, 1, NULL, 'GreatValue');";
            stmt.executeQuery(sql);
            sql = "INSERT INTO Product VALUES (3819038, 'Bread', 1.56, 1, DATE '2017-10-22', 'GreatValue')";
            stmt.executeQuery(sql);
            sql = "INSERT INTO Product VALUES (9320193, 'Oranges', 5.88, 2, DATE '2017-10-30', 'Cuties'); ";
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
