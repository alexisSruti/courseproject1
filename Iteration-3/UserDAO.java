package softwaremodelingproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
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

    public static int findUserNumber() {
        int id = 0;
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT UserID FROM StoreManagement.User ORDER BY UserID DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		id = rs.getInt("UserID");
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
    public static int countUsername(String usernameIn) {
        int id = 0;
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "SELECT count(*) AS Count FROM StoreManagement.User WHERE BINARY UserUsername = '" + usernameIn + "';";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		id = rs.getInt("Count");
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
            String sql = "SELECT * FROM StoreManagement.User WHERE BINARY UserUsername = '" + usernameIn
                    + "' AND BINARY UserPassword = '" + passwordIn + "' LIMIT 1;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
		// Retrieve by column name
		String name = rs.getString("UserName");
		int id = rs.getInt("UserID");
		boolean isManager = rs.getBoolean("isManager");
                String username = rs.getString("UserUsername");
                String password =rs.getString("UserPassword");
                InputStream in = rs.getBinaryStream(6);
                if (in != null) {
                    File file = new File("image.png");
                    OutputStream f = new FileOutputStream(file);
                    int c = 0;
                    while ((c = in.read()) > -1) {
                        f.write(c);
                    }
                    f.close();
                    in.close();
                    User u = new User(name, id, isManager, username, password, file);
                    user = u;
                } else {
                    User u = new User(name, id, isManager, username, password, null);
                    user = u;
                }
            }
        }
        catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
	} catch (IOException e) {
            e.printStackTrace();
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
            String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "INSERT INTO StoreManagement.User VALUES (" + id + ", '" + name 
                    + "', " + isManager + ", '" + username + "', '" + password + "', null);";
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
    
    public static void updateUser(String name, int id, boolean isManager, String username, String password, File file) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // STEP 3: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            stmt = conn.createStatement();
            String sql = "UPDATE StoreManagement.User SET UserName = ?, isManager = ?, "
                    + "UserUsername = ?, UserPassword = ?, Image = ? WHERE UserID = ?;";
            
            FileInputStream fin = null;
            if (file != null) {
                fin = new FileInputStream(file);
            }
            PreparedStatement pre = conn.prepareStatement(sql);
            
            pre.setString(1, name);
            pre.setBoolean(2, isManager);
            pre.setString(3, username);
            pre.setString(4, password);
            if (fin != null) {
                pre.setBinaryStream(5,(InputStream)fin,(int)file.length());
            } else {
                pre.setBinaryStream(5, null);
            }
            pre.setInt(6, id);
            
            pre.executeUpdate();
            pre.close();
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
                "UserName char(30) not NULL, isManager boolean not NULL, UserUsername char(30), UserPassword char(30), Image mediumblob);";
                stmt.executeUpdate(sql);
                
                sql = "INSERT INTO User VALUES(?, ?, ?, ?, ?, null);";
                PreparedStatement pre = conn.prepareStatement(sql);
                
                pre.setInt(1, 1);
                pre.setString(2, "admin");
                pre.setBoolean(3, true);
                pre.setString(4, "admin");
                pre.setString(5, "pass");
                pre.executeUpdate();
                
                pre.setInt(1, 2);
                pre.setString(2, "John Doe");
                pre.setBoolean(3, true);
                pre.setString(4, "johndoe321");
                pre.setString(5, "jd325");
                pre.executeUpdate();
                
                pre.setInt(1, 3);
                pre.setString(2, "Emma Smith");
                pre.setBoolean(3, false);
                pre.setString(4, "emmasmith542");
                pre.setString(5, "e541smith");
                pre.executeUpdate();
                
                pre.setInt(1, 4);
                pre.setString(2, "Rachel Knope");
                pre.setBoolean(3, false);
                pre.setString(4, "rachelknope940");
                pre.setString(5, "rachel123");
                pre.executeUpdate();
                
                pre.setInt(1, 5);
                pre.setString(2, "Ben Green");
                pre.setBoolean(3, true);
                pre.setString(4, "bengreen410");
                pre.setString(5, "12ben45");
                pre.executeUpdate();
                
                pre.setInt(1, 6);
                pre.setString(2, "Lily Jones");
                pre.setBoolean(3, false);
                pre.setString(4, "lilyJones092");
                pre.setString(5, "523Lily");
                pre.executeUpdate();

                pre.close();
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
