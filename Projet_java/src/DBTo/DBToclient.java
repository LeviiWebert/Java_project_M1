package DBTo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;

import basicObject.Client;
import basicObject.Commande;
import service.DBconnection;

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

	public static List<Client> getClients() {
		List<Client> clients = new ArrayList(); // Renommé de "Client" à "clients"

		try (Connection connection = DBconnection.getConnection()) {
			String query = "SELECT * FROM client";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);
					ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					// Récupérer les données de chaque colonne
					int clientID = resultSet.getInt("clientID"); // Renommé "ClientID" à "clientID"
					String nom = resultSet.getString("nom");
					String prenom = resultSet.getString("prenom");
					String email = resultSet.getString("email");
					String telephone = resultSet.getString("telephone");
					String adresse = resultSet.getString("adresse");
					String dateNaissance = resultSet.getString("date_naissance"); // Renommé pour correspondre à la
																					// convention camelCase
					String mot_de_passe = resultSet.getString("mdp");

					// Créer une nouvelle instance de Client et l'ajouter à la liste
					Client client = new Client(dateNaissance, nom, prenom, email, telephone, clientID, adresse,
							mot_de_passe);
					clients.add(client); // Ajouté à la liste "clients"
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Gestion des erreurs SQL
		}

		return clients; // Retourne la liste des clients
	}

	public static Client getClientByID(int id_client) {
		Client client = null; // Initialiser à null

		try (Connection connection = DBconnection.getConnection()) {
			String query = "SELECT * FROM client WHERE clientID = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setInt(1, id_client); // Définir le paramètre du clientID
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) { // Vérifie si un résultat a été trouvé
						// Récupérer les données de chaque colonne
						int clientID = resultSet.getInt("clientID");
						String nom = resultSet.getString("nom");
						String prenom = resultSet.getString("prenom");
						String email = resultSet.getString("email");
						String telephone = resultSet.getString("telephone");
						String adresse = resultSet.getString("adresse");
						String dateNaissance = resultSet.getString("date_naissance");
						String mot_de_passe = resultSet.getString("mdp");

						// Créer une nouvelle instance de Client
						client = new Client(dateNaissance, nom, prenom, email, telephone, clientID, adresse,
								mot_de_passe);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Gestion des erreurs SQL
		}

		return client; // Retourne l'objet Client ou null si non trouvé
	}

	public static List<Commande> getCommandesByClientID(int clientID) {
		List<Commande> commandes = new ArrayList(); // Liste des commandes

		try (Connection connection = DBconnection.getConnection()) {
			String query = "SELECT DISTINCT * FROM commande WHERE client_id = ?";

			try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
				preparedStatement.setInt(1, clientID);
				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					// Récupérer les données de chaque colonne
					int id = resultSet.getInt("id"); // Identifiant unique de la commande
					Date dateCommande = resultSet.getDate("date_commande"); // Date à laquelle la commande a été passée
					String etat = resultSet.getString("statut"); // État de la commande (en_cours, validée, livrée)
					double total = resultSet.getDouble("total_commande"); // Montant total de la commande

					// Créer une nouvelle instance de Commande et l'ajouter à la liste
					Commande commande = new Commande(id, clientID);
					commande.setEtat(etat);
					commande.setTotal(total);
					commande.setDateCommande(dateCommande);
					commandes.add(commande); // Ajouté à la liste des commandes
				}
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Gestion des erreurs SQL
		}

		return commandes; // Retourne la liste des commandes
	}

	public static String getClientPassword(int clientID) {
		String password = null;
		String query = "SELECT mdp FROM client WHERE clientID = ?";


		try (Connection connection = DBconnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, clientID);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				password = resultSet.getString("mdp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;
	}

}
