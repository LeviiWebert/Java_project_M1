package DBTo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import basicObject.Commande;
import basicObject.LigneCommande;
import basicObject.Produit;
import service.DBconnection;

public class DBTocommande {
	
	
	
	public static int getMaxIdCommande() {
        int maxID = 0;

        try (Connection connection = DBconnection.getConnection()) {
            String query = "SELECT MAX(id) FROM commande";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    maxID = resultSet.getInt(1); // Récupère la valeur du MAX(id)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Gestion des erreurs SQL
        }

        return maxID;  // Retourne l'identifiant de commande maximum
    }

	
	public static Commande getCommandeById(int commandeId) {
	    Commande commande = null;

	    try (Connection connection = DBconnection.getConnection()) {
	        // Requête pour récupérer la commande
	        String queryCommande = "SELECT * FROM commande WHERE id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(queryCommande)) {
	            preparedStatement.setInt(1, commandeId);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    int client_id = resultSet.getInt("client_id");
	                    Date date_commande = resultSet.getDate("date_commande");
	                    String statut = resultSet.getString("statut");
	                    double total = resultSet.getDouble("total_commande");

	                    // Créer l'instance de la commande
	                    commande = new Commande(commandeId, client_id, date_commande, statut, new ArrayList<>(), total);

	                    // Requête pour récupérer les lignes de commande associées à cette commande
	                    String queryLigneCommande = "SELECT * FROM lignecommande WHERE commande_id = ?";
	                    try (PreparedStatement preparedStatementLigne = connection.prepareStatement(queryLigneCommande)) {
	                        preparedStatementLigne.setInt(1, commandeId);
	                        try (ResultSet resultSetLigne = preparedStatementLigne.executeQuery()) {
	                            while (resultSetLigne.next()) {
	                                int produit_id = resultSetLigne.getInt("produit_id");
	                                int quantite = resultSetLigne.getInt("quantite");
	                                double prix_unitaire = resultSetLigne.getDouble("prix_unitaire");
	                               // System.out.println("saluuuuut");

	                                // Récupérer le produit correspondant à produitId
	                                Produit produit = DBToproduit.getProduitByid(produit_id); // Méthode à implémenter dans DBTOproduit
	                              //  System.out.println(produit.toString());

	                                // Créer la ligne de commande et l'ajouter à la commande
	                                LigneCommande ligneCommande = new LigneCommande(commande, produit, quantite);
	                                commande.getLignes().add(ligneCommande); // Ajouter la ligne à la commande
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return commande;
	}
	
    public static void main(String[] args) {
        // Simulons un ID de commande à rechercher
        int commandeId = 10;

        // Appel de la méthode pour récupérer la commande
        Commande commande = getCommandeById(commandeId);

        // Afficher les détails de la commande
        if (commande != null) {
            System.out.println("Commande récupérée :");
            System.out.println(commande.toString());
           // System.out.println(commande.getLignes());
            System.out.println("Lignes de commande :");
            for (LigneCommande ligne : commande.getLignes()) {
               // System.out.println("salut");
                System.out.println(ligne.toString());
            }
        } else {
            System.out.println("Aucune commande trouvée avec l'ID " + commandeId);
        }
    }


}
