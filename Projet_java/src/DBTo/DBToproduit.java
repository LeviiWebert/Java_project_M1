package DBTo;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import basicObject.Client;
import basicObject.Produit;
import service.DBconnection;

public class DBToproduit {
	
	
	public static int getMaxProduitID() {
	    int maxID = 0;
	    try (Connection connection = DBconnection.getConnection()) {
	        if (connection != null) {
	            System.out.println("Connexion réussie !");
	            String query = "SELECT MAX(id) FROM produit";
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
	
	
	
	
	
    public static List<Produit> getProduits(){
    	
        List<Produit> produits = new ArrayList<>();
        
        try (Connection connection = DBconnection.getConnection()) {
            String query = "SELECT * FROM produit";  // Assure-toi que cette table existe dans ta base de données
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                 
                while (resultSet.next()) {
                    int ID = resultSet.getInt("id");
                    String marque = resultSet.getString("marque");
                    String modele = resultSet.getString("modele");
                    double prix = resultSet.getDouble("prix");
                    String type = resultSet.getString("type");
                    String description = resultSet.getString("description");
                    int quantite_stock = resultSet.getInt("quantite_stock");
                    String adr_img = resultSet.getString("adr_img");
                    
                    

                    
                    Produit produit;
                    ImageIcon image;
					try {
						image = new ImageIcon(new URL(adr_img));
						image.setDescription(adr_img);
						produit = new Produit (ID, marque, modele, prix, type,description,quantite_stock,image);
						produits.add(produit);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return produits;
    }
    
    
    
    
    
    
    public static Produit getProduitByid(int produitId) {
        Produit produit = null;

        try (Connection connection = DBconnection.getConnection()) {
            String query = "SELECT * FROM produit WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, produitId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Récupération des informations du produit
                        String marque = resultSet.getString("marque");
                        String modele = resultSet.getString("modele");
                        double prix = resultSet.getDouble("prix");
                        String type = resultSet.getString("type");
                        String description = resultSet.getString("description");
                        int quantiteStock = resultSet.getInt("quantite_stock");

                        String adr_img = resultSet.getString("adr_img");

                        // Création d'une instance de Produit

                        ImageIcon image;
                        try {
                        	image = new ImageIcon(new URL(adr_img));
    						image.setDescription(adr_img);
							produit = new Produit(produitId, marque, modele, prix, type, description, quantiteStock,image);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Gestion des erreurs SQL
        }

        return produit;  // Retourne le produit ou null si aucun produit n'a été trouvé
    }
    
    
    
    
    

    public static List<Produit> getproduitByModele(List<String> selectedProductModele) {
        List<Produit> produitList = new ArrayList<>();

        if (selectedProductModele == null || selectedProductModele.isEmpty()) {
            return produitList; // retourne une liste vide si aucune sélection
        }

        // Supposons que vous ayez une méthode getproduit() qui renvoie tous les produits disponibles
        List<Produit> allProducts = getProduits();

        for (Produit produit : allProducts) {
            if (selectedProductModele.contains(produit.getModele())) {
                produitList.add(produit);
            }
        }

        return produitList; // Retourne la liste des produits trouvés
    }
    
    // Méthode pour vérifier si un produit existe déjà dans la base de données
    public static boolean isProduitExists(String marque, String modele) {
        // Remplacez cette ligne par la logique pour vérifier les doublons dans votre base de données
        // Par exemple, effectuez une requête SELECT pour vérifier si un produit avec la même marque et le même modèle existe déjà
        String query = "SELECT COUNT(*) FROM produit WHERE marque = ? AND modele = ?";
        
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, marque);
            stmt.setString(2, modele);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Si le compteur est supérieur à 0, cela signifie que le produit existe déjà
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // Retourner false si le produit n'existe pas
    }
    
    
}


