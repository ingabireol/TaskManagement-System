package olim.com.taskmanagementsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class
 */
public class DBUtil {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/taskmanagementsystemDb";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "078868";
    
    // Load the PostgreSQL driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Get a database connection
     * 
     * @return Connection object
     * @throws SQLException if a database error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
    
    /**
     * Close database connection
     * 
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}