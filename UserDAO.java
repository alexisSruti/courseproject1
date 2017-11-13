package softwaremodelingproject;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
/**
 *
 * @author Alexis/Sruti
 */
public class UserDAO {
    //Properties file
    static final Properties properties = loadProperties();

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = properties.getProperty("DB_URL");

    // Database credentials
    static final String USER = properties.getProperty("USERNAME");
    static final String PASSWORD = properties.getProperty("PASSWORD");

    public static ArrayList<User> findAll() {
        ArrayList<User> userList = new ArrayList<User>();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM StoreManagement.User";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("UserName");
		int id = rs.getInt("UserId");
		boolean isManager = rs.getBoolean("isManager");
                User c = new User(name, id, isManager);
                userList.add(c);
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
        return userList;
    }
    public static User findById(int ID) {
        User user = new User();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM StoreManagement.User WHERE UserID = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("UserName");
		int id = rs.getInt("Userid");
		boolean isManager = rs.getBoolean("isManager");
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
        return user;
    }
    public static void insertUser(String name, int id, boolean isManager) {
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
                sql = "INSERT INTO StoreManagement.User VALUES (" + id + ", '" + name + "', " + isManager + "');";
            } else {
                sql = "INSERT INTO StoreManagement.User VALUES (" + id + ", '" + name + "', " + isManager + "');";
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
    public static void updateUser(String name, int id, boolean isManager) {
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
                sql = "UPDATE StoreManagement.User SET UserName = '" + name + "', isManager = " + isManager
                    + " UserID = " + id + ";";
            } else {
              sql = "UPDATE StoreManagement.User SET UserName = '" + name + "', isManager = " + isManager
                  + " UserID = " + id + ";";
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
                String sql = "USE StoreManagement";
                stmt.executeUpdate(sql);
            } catch (Exception ex) {
                String sql = "CREATE DATABASE StoreManagement";
                stmt.executeUpdate(sql);
                sql = "USE StoreManagement";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE User (UserID int not NULL primary key,\n" +
                "UserName char(30) not NULL, isManager boolean not NULL);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES(7568687, 'John Doe', true);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES(3987652, 'Emma Smith', false);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES (2759314, 'Rachel Knope', false);";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES (3119037, 'Ben Green', true)";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES (9310143, 'Lily Jones', false); ";
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