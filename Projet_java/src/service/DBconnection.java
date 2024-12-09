package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
//	private static final String URL = "jdbc:mysql://localhost:3306/bd_java";
//	private static final String USER = "root";
//	private static final String PASSWORD = "yoo";
//
//	public static java.sql.Connection getConnection() {
//		try {
//			return DriverManager.getConnection(URL, USER, PASSWORD);
//		} catch (SQLException e) {
//			throw new RuntimeException("Error connecting to database\r\n" + "\r\n" + "", e);
//		}
//	}
	private static final String URL = "jdbc:mysql://mysql-webert.alwaysdata.net/webert_java_m1";
    private static final String USERNAME = "webert";
    private static final String PASSWORD = "pq3**cK7ZBMnngm";
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DBconnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException("Unable to load JDBC Driver.", e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            throw new SQLException("Unable to establish database connection.", e);
        }
    }

    // Static method to get connection instance
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            new DBconnection(); // Establish the connection if it's not already established
        }
        return connection;
    }

    // Close the connection if needed
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed.");
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = DBconnection.getConnection();
            if (conn != null) {
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}