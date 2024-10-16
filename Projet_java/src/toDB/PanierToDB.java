package toDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import basicObject.Panier;
import service.DBconnection;
import basicObject.LigneCommande;

public class PanierToDB {

    public static void addPanier(Panier panier) {
        String queryPanier = "INSERT INTO panier (clientId, total) VALUES (?, ?)";
        String queryLigneCommande = "INSERT INTO ligne_commande_panier (panierID, produitID, quantite, prixUnitaire, prixTotal) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatementPanier = connection.prepareStatement(queryPanier, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementLigneCommande = connection.prepareStatement(queryLigneCommande)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                // Insertion du panier
                preparedStatementPanier.setInt(1, panier.getClientId());
                preparedStatementPanier.setDouble(2, panier.getTotal());
                int affectedRowsPanier = preparedStatementPanier.executeUpdate();

                if (affectedRowsPanier > 0) {
                    System.out.println("Panier ajouté avec succès.");

                    // Récupération de l'identifiant du panier généré
                    ResultSet generatedKeys = preparedStatementPanier.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int panierId = generatedKeys.getInt(1);

                        // Insertion des lignes de commande du panier
                        List<LigneCommande> lignes = panier.getLignes();
                        for (LigneCommande ligne : lignes) {
                            preparedStatementLigneCommande.setInt(1, panierId);
                            preparedStatementLigneCommande.setInt(2, ligne.getProduit().getId());
                            preparedStatementLigneCommande.setInt(3, ligne.getQuantite());
                            preparedStatementLigneCommande.setDouble(4, ligne.getPrixUnitaire());
                            preparedStatementLigneCommande.setDouble(5, ligne.getPrixTotal());
                            preparedStatementLigneCommande.addBatch();
                        }
                        preparedStatementLigneCommande.executeBatch();
                    }
                } else {
                    System.out.println("L'insertion du panier a échoué.");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletePanier(int panierId) {
        String queryLigneCommande = "DELETE FROM ligne_commande_panier WHERE panierID = ?";
        String queryPanier = "DELETE FROM panier WHERE id = ?";
        
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement preparedStatementLigneCommande = connection.prepareStatement(queryLigneCommande);
             PreparedStatement preparedStatementPanier = connection.prepareStatement(queryPanier)) {

            if (connection != null) {
                System.out.println("Connexion réussie !");

                // Suppression des lignes de commande du panier
                preparedStatementLigneCommande.setInt(1, panierId);
                preparedStatementLigneCommande.executeUpdate();

                // Suppression du panier
                preparedStatementPanier.setInt(1, panierId);
                int affectedRowsPanier = preparedStatementPanier.executeUpdate();

                if (affectedRowsPanier > 0) {
                    System.out.println("Panier supprimé avec succès.");
                } else {
                    System.out.println("Aucun panier trouvé avec l'identifiant " + panierId + ".");
                }
            } else {
                System.out.println("La connexion a échoué !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
