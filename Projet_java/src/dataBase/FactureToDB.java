package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import basicObject.Facture;
import basicObject.Commande;

public class FactureToDB {

    public static void addFacture(Facture facture) {
        String query = "INSERT INTO facture (commandeID, dateFacture, montant) VALUES (?, ?, ?)";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                // Définir les valeurs des paramètres
                preparedStatement.setInt(1, facture.getCommande().getId());
                preparedStatement.setDate(2, java.sql.Date.valueOf(facture.getDateFacture()));
                preparedStatement.setDouble(3, facture.getMontant());

                // Exécuter l'insertion
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            facture.setId(generatedKeys.getInt(1));
                            System.out.println("Facture ajoutée avec l'ID : " + facture.getId());
                        }
                    }
                } else {
                    System.out.println("L'insertion de la facture a échoué.");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFacture(int factureID) {
        String query = "DELETE FROM facture WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                // Définir la valeur du paramètre
                preparedStatement.setInt(1, factureID);

                // Exécuter la suppression
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Facture avec l'ID " + factureID + " supprimée avec succès.");
                } else {
                    System.out.println("Aucune facture trouvée avec l'ID " + factureID + ".");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
