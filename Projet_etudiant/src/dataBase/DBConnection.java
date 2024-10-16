package dataBase;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection { 
    private static final String URL = "jdbc:mysql://localhost/project_java_db";
    private static final String USER = "root";
    private static final String PASSWORD = "yoo";

    public static java.sql.Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database\r\n"
            		+ "\r\n"
            		+ "", e);
        }
    }
}