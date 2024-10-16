package dataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import basicObjects.Education;
import managementAppUI.PairPanel;
import managementAppUI.StudentPanel;

public class EducationToDB {
	    public void addEducation(Education e) {
	    	
	       
	        try (Connection connection = DBConnection.getConnection()) {
	        	 String Query = "INSERT INTO education (educationID, name, promo) VALUES (?,?, ?)";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(Query)) {
                    preparedStatement.setInt(1, e.getEducationID());
                    preparedStatement.setString(2, e.getName());
                    preparedStatement.setString(3, e.getPromo());

	                preparedStatement.executeUpdate();
	            }
	        } catch (SQLException e2) {
	            e2.printStackTrace(); 
	        }
	    }

	    public void deleteEducation(Education education) {
	        try (Connection connection = DBConnection.getConnection()) {
	        	
	           
	            String deletePairsQuery = "DELETE FROM pair WHERE studentID_1 IN (SELECT studentID FROM student WHERE educationID = ?) OR studentID_2 IN (SELECT studentID FROM student WHERE educationID = ?)";
	            try (PreparedStatement deletePairsStatement = connection.prepareStatement(deletePairsQuery)) {
	                deletePairsStatement.setInt(1, education.getEducationID());
	                deletePairsStatement.setInt(2, education.getEducationID());
	                deletePairsStatement.executeUpdate();
	            }
	            
	          
	            String deleteStudentsQuery = "DELETE FROM student WHERE educationID = ?";
	            try (PreparedStatement deleteStudentsStatement = connection.prepareStatement(deleteStudentsQuery)) {
	                deleteStudentsStatement.setInt(1, education.getEducationID());
	                deleteStudentsStatement.executeUpdate();
	            }

	

	      
	            String deleteEducationQuery = "DELETE FROM education WHERE educationID = ?";
	            try (PreparedStatement deleteEducationStatement = connection.prepareStatement(deleteEducationQuery)) {
	                deleteEducationStatement.setInt(1, education.getEducationID());
	                deleteEducationStatement.executeUpdate();
	            }

	   
	            PairPanel.updateTable(DBToPair.getPairs());
	            StudentPanel.updateTable(DBToStudent.getStudents());
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	    }



	    public static void updateEducation(Education education) {
	    	 try (Connection connection = DBConnection.getConnection()) {
	        String query = "UPDATE education SET name=?, promo=? WHERE educationID=?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, education.getName());
	            preparedStatement.setString(2, education.getPromo());

	            preparedStatement.setInt(3,education.getEducationID());
	            preparedStatement.executeUpdate();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	         
	        }
	    }catch (SQLException e) {
	        e.printStackTrace(); 
	    }

	    }	    
     }

