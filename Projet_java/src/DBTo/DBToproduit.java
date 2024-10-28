package DBTo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	
    public static List<Produit> getproduit(){
    	
        List<Produit> produit = new ArrayList<>();
        
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

                    
                    
                    Produit prduit = new Produit (ID, marque, modele, prix, type,description,quantite_stock,adr_img);
                    produit.add(prduit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return produit;
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
                        String adr_img = resultSet.getString("adresse_image");


                        // Création d'une instance de Produit
                        produit = new Produit(produitId, marque, modele, prix, type, description, quantiteStock,adr_img);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Gestion des erreurs SQL
        }

        return produit;  // Retourne le produit ou null si aucun produit n'a été trouvé
    }
    
//    public static List<Produit> getproduitByModele(List<String> selectedProductModele) {
//        List<Produit> produitList = new ArrayList<>();
//
//        if (selectedProductModele == null || selectedProductModele.isEmpty()) {
//            return produitList; // retourne une liste vide si aucune sélection
//        }
//
//        // Supposons que vous ayez une méthode getproduit() qui renvoie tous les produits disponibles
//        List<Produit> allProducts = getproduit();
//
//        for (Produit produit : allProducts) {
//            for (String modele : selectedProductModele) {
//                if (produit.getModele().equals(modele)) {
//                    produitList.add(produit);
//                }
//            }
//        }
//
//        return produitList; // Retourne la liste des produits trouvés
//    }
    public static List<Produit> getproduitByModele(List<String> selectedProductModele) {
        List<Produit> produitList = new ArrayList<>();

        if (selectedProductModele == null || selectedProductModele.isEmpty()) {
            return produitList; // retourne une liste vide si aucune sélection
        }

        // Supposons que vous ayez une méthode getproduit() qui renvoie tous les produits disponibles
        List<Produit> allProducts = getproduit();

        for (Produit produit : allProducts) {
            if (selectedProductModele.contains(produit.getModele())) {
                produitList.add(produit);
            }
        }

        return produitList; // Retourne la liste des produits trouvés
    }


    
    
    public static void  main(String [] args){
    	
    	int maxproduitID = DBToproduit.getMaxProduitID();
        System.out.println("Le prduit ID maximum est : " + maxproduitID);
    	
        
        Produit p = DBToproduit.getProduitByid(maxproduitID);
        p.toString();
    	
    	//List<Produit> produits = DBToproduit.getproduit();

 	    //Vérification s'il y a des clients dans la liste
// 	    if (produits.isEmpty()) {
// 	        System.out.println("Aucun client trouvé.");
// 	    } else {
// 	        // Parcourir et afficher les informations de chaque client
// 	        for (Produit produit : produits){
// 	        	
// 	            System.out.println("ID produit : " + produit.getId());
// 	            System.out.println("marque : " + produit.getMarque());
// 	            System.out.println("modele : " + produit.getModele());
// 	            System.out.println("prix : " + produit.getPrix() +" € ");
// 	            System.out.println("type : " + produit.getType());
// 	            System.out.println("description : " + produit.getDescription());
// 	            System.out.println("quantite_stock : " + produit.getDescription());
// 	            System.out.println("-----------------------------------------");
// 	            
// 	        }
// 	    }
    	
    	
    	
    }
    
    
    
}


