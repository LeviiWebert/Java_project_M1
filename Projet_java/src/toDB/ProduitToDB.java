package toDB;

import java.sql.Connection;
import service.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import basicObject.Produit;

public class ProduitToDB {

    public static void addProduit(Produit produit) {
        String query = "INSERT INTO produit (id, nom, prix, description, stock) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                preparedStatement.setInt(1, produit.getId());
               // preparedStatement.setString(2, produit.getNom());
                preparedStatement.setDouble(3, produit.getPrix());
                preparedStatement.setString(4, produit.getDescription());
               // preparedStatement.setInt(5, produit.getStock());

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



