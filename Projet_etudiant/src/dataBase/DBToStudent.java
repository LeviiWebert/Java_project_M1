package dataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import basicObjects.Student;

public class DBToStudent {
	
	public static int getMaxStudentID() {
        int maxID = 0;

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT MAX(studentID) FROM student";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maxID = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return maxID;
    }

	
	 public static List<Student> getStudents() {
	        List<Student> students = new ArrayList<>();

	        try (Connection connection = DBConnection.getConnection()) {
	            String query = "SELECT * FROM student";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	                 ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    int studentID = resultSet.getInt("studentID");
	                    String name = resultSet.getString("name");
	                    String firstname = resultSet.getString("firstname");
	                    int educationID = resultSet.getInt("educationID");
	                   

	                    Student student = new Student(studentID, name, firstname, educationID);
	                    students.add(student);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	        return students;
	    }

}
