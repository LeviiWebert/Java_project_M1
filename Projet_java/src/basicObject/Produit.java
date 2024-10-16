package basicObject;

import DBTo.DBToclient;
import DBTo.DBToproduit;

public class Produit {
	
    private int id;                 
    private String marque;            
    private String modele;            
    private double prix;      
    private String type;              
    private String description;
    private int quantite_stock;
    
    
    public Produit() {
    	//this.id = DBToproduit.getMaxClientID() + 1;   relation avec la base de donnee
    }
    
    
    public Produit(int id, String marque, String modele, double prix2, String type,String description,int quantite_stock) {
    	
        this.id = id;
        this.marque = marque ;
        this.modele = modele;
        this.prix = prix2;
        this.type = type;
        this.description = description;
        this.quantite_stock = quantite_stock;
        
    }


    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMarque() {
		return marque;
	}


	public void setMarque(String marque) {
		this.marque = marque;
	}


	public String getModele() {
		return modele;
	}


	public void setModele(String modele) {
		this.modele = modele;
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(int prix) {
		this.prix = prix;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getQuantite_stock() {
		return quantite_stock;
	}


	public void setQuantite_stock(int quantite_stock) {
		this.quantite_stock = quantite_stock;
	}


	// Méthode pour vérifier la disponibilité du produit
    public boolean estDisponible(int quantite) {
        return getQuantite_stock() >= quantite;   // Vérifie si la quantité demandée est disponible
    }

    // Méthode pour réduire le stock lors d'une commande
    public void reduireStock(int quantite) {
        if (estDisponible(quantite)) {
            quantite_stock -= quantite;      // Réduit le stock de la quantité commandée
            setQuantite_stock( quantite_stock);
        } else {
            System.out.println("Stock insuffisant pour " + getMarque());
        }
    }

    // Méthode pour afficher les détails du produit
    public void afficherDetails() {
        System.out.println("ID: " + getId());
        System.out.println("marque: " + getMarque());
        System.out.println("modele: " + getModele());
        System.out.println("Prix: " + getPrix() + " €");
        System.out.println("type: " + getType());
        System.out.println("Description: " + getDescription());
        System.out.println("Stock disponible: " + getQuantite_stock());
    }
}
