package toDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import basicObject.Produit;
import service.DBconnection;

public class ProduitToDB {
	
	
	
    public static void addProduit(Produit produit) {
        String query = "INSERT INTO produit (id, marque, modele ,prix, description, quantite_stock, adr_img) VALUES (?, ?, ?, ?, ?,?,?)";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            System.out.println("Connexion réussie !");

			preparedStatement.setInt(1, produit.getId());
			preparedStatement.setString(2, produit.getMarque());
			preparedStatement.setString(3, produit.getModele());
			preparedStatement.setDouble(4, produit.getPrix());
			preparedStatement.setString(5, produit.getDescription());
			preparedStatement.setInt(6, produit.getQuantite_stock());
			preparedStatement.setString(7, produit.getImage().getDescription());


			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
			    System.out.println("Produit ajouté avec succès.");
			} else {
			    System.out.println("L'insertion du produit a échoué.");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    

    public static void deleteProduit(int produitId) {
        String query = "DELETE FROM produit WHERE id = ?";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            System.out.println("Connexion réussie !");

			preparedStatement.setInt(1, produitId);
			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
			    System.out.println("Produit supprimé avec succès.");
			} else {
			    System.out.println("Aucun produit trouvé avec l'identifiant " + produitId + ".");
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    // Nouvelle méthode pour mettre à jour un produit
    public static void updateProduit(Produit produit) {
        // Établir une connexion à la base de données
        try (Connection connection = DBconnection.getConnection()) {
            String sql = "UPDATE produit SET marque = ?, modele = ?, prix = ?, type = ?, description = ?, quantite_stock = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, produit.getMarque());
            statement.setString(2, produit.getModele());
            statement.setDouble(3, produit.getPrix());
            statement.setString(4, produit.getType());
            statement.setString(5, produit.getDescription());
            statement.setInt(6, produit.getQuantite_stock());
            statement.setInt(7, produit.getId()); // L'ID du produit à mettre à jour

            // Exécuter la mise à jour
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produit mis à jour avec succès.");
            } else {
                System.out.println("Aucun produit trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du produit: " + e.getMessage());
        }
    }
}
