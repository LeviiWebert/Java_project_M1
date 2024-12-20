package DBTo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import basicObject.Facture;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import basicObject.Commande;
import service.DBconnection;

public class DBTofacture {
	
	
	
	
	
	public static int getMaxIdFacture() {
	    int maxID = 0;

	    // Utilisation de la connexion à la base de données pour exécuter la requête
	    try (Connection connection = DBconnection.getConnection()) {
	        String query = "SELECT MAX(id) FROM Facture";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	             ResultSet resultSet = preparedStatement.executeQuery()) {

	            // Récupérer la valeur du MAX(id)
	            if (resultSet.next()) {
	                maxID = resultSet.getInt(1);  // Retourne le MAX(id) de la table Facture
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Gestion des exceptions SQL
	    }

	    return maxID;  // Retourne l'identifiant maximum
	}
	
	
	
	
	
	
	public static Facture getFactureById(int factureId) {
	    Facture facture = null;

	    try (Connection connection = DBconnection.getConnection()) {
	        // Requête pour récupérer la facture
	        String queryFacture = "SELECT * FROM facture WHERE id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(queryFacture)) {
	            preparedStatement.setInt(1, factureId);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    int commande_id = resultSet.getInt("commande_id");
	                    Date date_facture = resultSet.getDate("date_facture");
	                    double montant = resultSet.getDouble("montant");

	                    // Récupérer la commande associée à la facture
	                    Commande commande = DBTocommande.getCommandeById(commande_id); // Méthode pour récupérer la commande

	                    // Créer l'instance de la facture

	                    facture = new Facture(factureId, commande, ((java.sql.Date)date_facture).toLocalDate(), montant);

	                    // Si vous voulez récupérer et afficher des détails supplémentaires sur la commande ou les lignes de commande,
	                    // vous pouvez le faire ici, en vous basant sur la commande associée.
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Gestion des erreurs SQL
	    }

	    return facture;  // Retourner la facture trouvée (ou null si aucune facture n'a été trouvée)
	}
	
	
	
	
	
	
	public static List<Facture> getToutesLesFactures() {
	    List<Facture> factures = new ArrayList<>();

	    // Bloc try pour gérer la connexion
	    try (Connection connection = DBconnection.getConnection()) { // Utilisation de try-with-resources
	        String query = "SELECT * FROM Facture";

	        // Préparation de la requête
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
	             ResultSet rs = preparedStatement.executeQuery()) {

	            // Parcourir les résultats de la requête
	            while (rs.next()) {
	                // Récupérer la commande associée
	                //DBTocommande DBTocommande = new DBTocommande();
	                Commande commande = DBTo.DBTocommande.getCommandeById(rs.getInt("commande_id"));

	                // Ajouter la facture à la liste
	                factures.add(new Facture(
	                        rs.getInt("id"),
	                        commande,
	                        rs.getDate("date_facture").toLocalDate(),
	                        rs.getDouble("montant")
	                ));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();  // Gestion des erreurs SQL
	    }

	    return factures; // Retourner la liste des factures
	}
	
	
	
	
	

	}




