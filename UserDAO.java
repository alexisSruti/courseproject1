package softwaremodelingproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
/**
 *
 * @author Alexis/Sruti
 */
public class UserDAO {
    //Properties file
    static final Properties PROPERTIES = loadProperties();

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = PROPERTIES.getProperty("DB_URL");

    // Database credentials
    static final String USER = PROPERTIES.getProperty("USERNAME");
    static final String PASSWORD = PROPERTIES.getProperty("PASSWORD");

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
                String username = rs.getString("UserUsername");
                String password = rs.getString("UserPassword");
                String fileName = rs.getString("UserFileName");
                User c = new User(name, id, isManager, username, password, fileName);
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
            String sql = "SELECT * FROM StoreManagement.User WHERE UserID = " + ID;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("UserName");
		int id = rs.getInt("UserID");
		boolean isManager = rs.getBoolean("isManager");
                String username = rs.getString("UserUsername");
                String password =rs.getString("UserPassword");
                String fileName = rs.getString("UserFileName");
                User c = new User(name, id, isManager, username, password, fileName);
                user = c;
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

    //Username
    public static User findByUsernameAndPassword(String usernameIn, String passwordIn) {
        User user = new User();
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM StoreManagement.User WHERE UserUsername = '" + usernameIn
                    + "' AND UserPassword = '" + passwordIn + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("UserName");
		int id = rs.getInt("UserID");
		boolean isManager = rs.getBoolean("isManager");
                String username = rs.getString("UserUsername");
                String password =rs.getString("UserPassword");
                String fileName = rs.getString("UserFileName");
                User c = new User(name, id, isManager, username, password, fileName);
                user = c;
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
    public static void insertUser(String name, int id, boolean isManager, 
            String username, String password, String fileName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "INSERT INTO StoreManagement.User VALUES (" + id + ", '" + name 
                    + "', " + isManager + ", '" + username + "', '" + password + "', '" + fileName + "');";
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
    public static void updateUser(String name, int id, boolean isManager, String username, String password, String fileName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "UPDATE StoreManagement.User SET UserName = '" + name + "', isManager = " + isManager
                + ", UserUsername = '" + username + "', UserPassword = '" 
                + password + "', UserFileName = '" + fileName + "' WHERE UserID = " + id + ";";
            
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
                String sql = "SELECT * FROM StoreManagement.User";
                stmt.executeQuery(sql);
            } catch (Exception ex) {
                String sql = "USE StoreManagement";
                stmt.executeUpdate(sql);
                sql = "CREATE TABLE User(UserID int not NULL primary key,\n" +
                "UserName char(30) not NULL, isManager boolean not NULL, UserUsername char(30), UserPassword char(30), UserFileName char(35));";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES (1, 'admin', true, 'admin', 'pass', 'admin.jpg');";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES(7568687, 'John Doe', true, 'johndoe321', 'jd325', 'myPicJohn.jpg');";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES(3987652, 'Emma Smith', false, 'emmasmith542', 'e541smith', 'profilePicEmma.jpg');";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES (2759314, 'Rachel Knope', false, 'rachelknope940', 'rachel123', 'myDisplayPic.jpg');";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES (3119037, 'Ben Green', true, 'bengreen410', '12ben45', 'benProfilePic.jpg');";
                stmt.executeUpdate(sql);
                sql = "INSERT INTO User VALUES (9310143, 'Lily Jones', false, 'lilyJones092', '523Lily', 'lilyDisplayPic.jpg'); ";
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