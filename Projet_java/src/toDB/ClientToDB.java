package toDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import basicObject.Client;
import service.DBconnection;

public class ClientToDB {
	
	
	public static List<Client> getAllClients() {
	    List<Client> clients = new ArrayList<>();
	    try (Connection conn = DBconnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM client")) {
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Client client = new Client();
	            client.setClientID(rs.getInt("clientID"));
	            client.setNom(rs.getString("nom"));
	            client.setPrenom(rs.getString("prenom"));
	            client.setEmail(rs.getString("email"));
	            client.setTelephone(rs.getString("telephone"));
	            client.setAdresse(rs.getString("adresse"));
	            client.setDate_naissance(rs.getString("date_naissance"));
	            // Ajouter le client à la liste
	            clients.add(client);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return clients;
	}

	
	
	

	public static void addCustomer(Client client) {
		String query = "INSERT INTO client (date_naissance, nom, prenom, email, telephone, adresse, mdp) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DBconnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query,
						PreparedStatement.RETURN_GENERATED_KEYS)) {

			System.out.println("Connexion réussie !");

			// Définir les valeurs des paramètres
			preparedStatement.setString(1, client.getDate_naissance());
			preparedStatement.setString(2, client.getNom());
			preparedStatement.setString(3, client.getPrenom());
			preparedStatement.setString(4, client.getEmail());
			preparedStatement.setString(5, client.getTelephone());
			preparedStatement.setString(6, client.getAdresse());
			preparedStatement.setString(7, client.getmdp());

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

	public static void deleteClient(int clientID) {
		String query = "DELETE FROM client WHERE clientID = ?";
		try (Connection connection = DBconnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	

	
	
	public static void updateClient(Client client) {
		String query = "UPDATE client SET date_naissance = ?, nom = ?, prenom = ?, email = ?, telephone = ?, adresse = ? WHERE clientID = ?";
		try (Connection connection = DBconnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			System.out.println("Connexion réussie pour la mise à jour du client !");

			// Définir les valeurs des paramètres pour la mise à jour
			preparedStatement.setString(1, client.getDate_naissance());
			preparedStatement.setString(2, client.getNom());
			preparedStatement.setString(3, client.getPrenom());
			preparedStatement.setString(4, client.getEmail());
			preparedStatement.setString(5, client.getTelephone());
			preparedStatement.setString(6, client.getAdresse());
			preparedStatement.setInt(7, client.getClientID()); // Le WHERE se base sur le clientID

			// Exécuter la mise à jour
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Client mis à jour avec succès. ID : " + client.getClientID());
			} else {
				System.out.println("La mise à jour du client a échoué. ID : " + client.getClientID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

}
