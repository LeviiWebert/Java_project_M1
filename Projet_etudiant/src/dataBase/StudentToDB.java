package dataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import basicObjects.Student;
import managementAppUI.PairPanel;

public class StudentToDB {
	    public void addStudent(Student s) {
	    	
	       
	        try (Connection connection = DBConnection.getConnection()) {
	        	 String Query = "INSERT INTO student (name, firstname, educationID) VALUES (?,?,?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(Query)) {
                    preparedStatement.setString(1, s.getName());
                    preparedStatement.setString(2, s.getFirstName());
                    preparedStatement.setInt(3, s.getEducation().getEducationID());
	                preparedStatement.executeUpdate();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	    }

	    public void deleteStudent(Student student) {
	        
	        try (Connection connection = DBConnection.getConnection()) {
	           
	            String deletePairsQuery = "DELETE FROM pair WHERE studentID_1 = ? OR studentID_2 = ?";
	            try (PreparedStatement deletePairsStatement = connection.prepareStatement(deletePairsQuery)) {
	                deletePairsStatement.setInt(1, student.getStudentID());
	                deletePairsStatement.setInt(2, student.getStudentID());
	                deletePairsStatement.executeUpdate();
	            }

	           
	            String deleteStudentQuery = "DELETE FROM student WHERE studentID = ?";
	            try (PreparedStatement deleteStudentStatement = connection.prepareStatement(deleteStudentQuery)) {
	                deleteStudentStatement.setInt(1, student.getStudentID());
	                deleteStudentStatement.executeUpdate();
	            }
	            
	            PairPanel.updateTable(DBToPair.getPairs());

	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	    }



	    public static void updateStudent(Student student) {
	    	 try (Connection connection = DBConnection.getConnection()) {
	        String query = "UPDATE student SET name=?, firstname=?, educationID=? WHERE studentID=?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, student.getName());
	            preparedStatement.setString(2, student.getFirstName());

	            preparedStatement.setInt(3,student.getEducation().getEducationID());
	            preparedStatement.setInt(4,student.getStudentID());
	            preparedStatement.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	           
	        }
	    }catch (SQLException e) {
	        e.printStackTrace(); 
	    }


	}
	    

	}

