package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import basicObject.Client;


public class CustomerToDB {
	
	public static void addCustomer(Client client) {
        String query = "INSERT INTO client (date_naissance, nom, prenom, email, telephone, adresse) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                // Définir les valeurs des paramètres
                preparedStatement.setString(1, client.getDate_naissance());
                preparedStatement.setString(2, client.getNom());
                preparedStatement.setString(3, client.getPrenom());
                preparedStatement.setString(4, client.getEmail());
                preparedStatement.setString(5, client.getTelephone());
                preparedStatement.setString(6, client.getAdresse());

                // Exécuter l'insertion
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            System.out.println("Client ajouté avec l'ID : " + client.getClientID());
                        }
                    }
                } else {
                    System.out.println("L'insertion du client a échoué.");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	 public static void deleteClient(int clientID) {
	        String query = "DELETE FROM client WHERE clientID = ?";
	        try (Connection connection = DBconnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            if (connection != null) {
	                System.out.println("Connexion réussie !");

	                // Définir la valeur du paramètre
	                preparedStatement.setInt(1, clientID);

	                // Exécuter la suppression
	                int affectedRows = preparedStatement.executeUpdate();

	                if (affectedRows > 0) {
	                    System.out.println("Client avec l'ID " + clientID + " supprimé avec succès.");
	                } else {
	                    System.out.println("Aucun client trouvé avec l'ID " + clientID + ".");
	                }
	            } else {
	                System.out.println("La connexion a échoué !");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	
	
	public static void main(String[] args) {
		Client t = new Client();
		addCustomer(null);
		
	}
	
	
}

