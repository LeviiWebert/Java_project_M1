package toDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import basicObject.LigneCommande;
import service.DBconnection;

public class LigneCommandeToDB {
	
	
	
	

	public static void addLigneCommande(LigneCommande ligneCommande) {
		String query = "INSERT INTO lignecommande (commande_id, produit_id, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";
		try (Connection connection = DBconnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query,
						PreparedStatement.RETURN_GENERATED_KEYS)) {

			System.out.println("Connexion réussie !");

			// Définir les valeurs des paramètres
			preparedStatement.setInt(1, ligneCommande.getCommande().getId());
			preparedStatement.setInt(2, ligneCommande.getProduit().getId());
			preparedStatement.setInt(3, ligneCommande.getQuantite());
			preparedStatement.setDouble(4, ligneCommande.getPrixUnitaire());

			// Exécuter l'insertion
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Ligne de commande ajoutée avec succès.");
			} else {
				System.out.println("L'insertion de la ligne de commande a échoué.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	public static void deleteLigneCommande(int commandeID, int produitID) {
		String query = "DELETE FROM ligne_commande WHERE commandeID = ? AND produitID = ?";
		try (Connection connection = DBconnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			System.out.println("Connexion réussie !");

			// Définir les valeurs des paramètres
			preparedStatement.setInt(1, commandeID);
			preparedStatement.setInt(2, produitID);

			// Exécuter la suppression
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				System.out.println("Ligne de commande supprimée avec succès.");
			} else {
				System.out.println("Aucune ligne de commande trouvée pour commandeID " + commandeID
						+ " et produitID " + produitID + ".");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
