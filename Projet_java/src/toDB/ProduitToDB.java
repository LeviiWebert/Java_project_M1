package toDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import basicObject.Produit;
import service.DBconnection;

public class ProduitToDB {

    public static void addProduit(Produit produit) {
        String query = "INSERT INTO produit (id, marque, modele ,prix, description, quantite_stock) VALUES (?, ?, ?, ?, ?,?)";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                preparedStatement.setInt(1, produit.getId());
                preparedStatement.setString(2, produit.getMarque());
                preparedStatement.setString(3, produit.getModele());
                preparedStatement.setDouble(4, produit.getPrix());
                preparedStatement.setString(5, produit.getDescription());
                preparedStatement.setInt(6, produit.getQuantite_stock());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Produit ajouté avec succès.");
                } else {
                    System.out.println("L'insertion du produit a échoué.");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduit(int produitId) {
        String query = "DELETE FROM produit WHERE id = ?";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                preparedStatement.setInt(1, produitId);
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Produit supprimé avec succès.");
                } else {
                    System.out.println("Aucun produit trouvé avec l'identifiant " + produitId + ".");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
