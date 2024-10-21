package toDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import basicObject.Commande;
import service.DBconnection;

public class CommandeToDB {

    public static void addCommande(Commande commande) {
        String query = "INSERT INTO commande (client_id, date_commande, statut, total_commande) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                // Définir les valeurs des paramètres
                preparedStatement.setInt(1, commande.getClientId());
                preparedStatement.setDate(2, new java.sql.Date(commande.getDateCommande().getTime()));
                preparedStatement.setString(3, commande.getEtat());
                preparedStatement.setDouble(4, commande.getTotal());

                // Exécuter l'insertion
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            commande.setId(generatedKeys.getInt(1));
                            System.out.println("Commande ajoutée avec l'ID : " + commande.getId());
                        }
                    }
                } else {
                    System.out.println("L'insertion de la commande a échoué.");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteCommande(int commandeID) {
        String query = "DELETE FROM commande WHERE id = ?";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                // Définir la valeur du paramètre
                preparedStatement.setInt(1, commandeID);

                // Exécuter la suppression
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Commande avec l'ID " + commandeID + " supprimée avec succès.");
                } else {
                    System.out.println("Aucune commande trouvée avec l'ID " + commandeID + ".");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
