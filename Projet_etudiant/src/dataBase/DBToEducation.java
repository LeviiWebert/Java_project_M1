package dataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import basicObjects.Education;


public class DBToEducation {
	
	public static int getMaxEducationID() {
        int maxID = 0;

        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT MAX(educationID) FROM education";
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

	
	 public static List<Education> getEducations() {
	        List<Education> educations = new ArrayList<>();

	        try (Connection connection = DBConnection.getConnection()) {
	            String query = "SELECT * FROM education";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	                 ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    int educationID = resultSet.getInt("educationID");
	                    String name = resultSet.getString("name");
	                    String promo = resultSet.getString("promo");
	       
	                   

	                    Education education = new Education(educationID, name, promo);
	                    educations.add(education);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }
	        return educations;
	    }
}
