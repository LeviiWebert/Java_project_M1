package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import basicObject.Client;


public class DBToclient {
	
	public static int getMaxClientID() {
	    int maxID = 0;
	    try (Connection connection = DBconnection.getConnection()) {
	        if (connection != null) {
	            System.out.println("Connexion réussie !");
	            String query = "SELECT MAX(clientID) FROM client";
	            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	                 ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    maxID = resultSet.getInt(1);
	                }
	            }
	        } else {
	            System.out.println("La connexion a échoué !");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return maxID;
	}
 
	
	
	
	public static void main(String[] args) {
		
	    int maxClientID = DBToclient.getMaxClientID();
	    
	    System.out.println("Le client ID maximum est : " + maxClientID);
	}
	
	
}



