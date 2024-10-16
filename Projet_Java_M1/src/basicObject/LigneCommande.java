package basicObject;


public class LigneCommande {
	
    private Commande commande;     
    private Produit produit;        
    private int quantite;  
    
    

    public LigneCommande(Commande commande, Produit produit, int quantite) {
        this.commande = commande; 
        this.produit = produit;    
        this.quantite = quantite;  
    }

    public Commande getCommande() {
        return commande;          
    }

    public Produit getProduit() {
        return produit;           
    }

    public int getQuantite() {
        return quantite;         
    }

    public double getPrixUnitaire() {
        return produit.getPrix(); 
    }

    public double getPrixTotal() {
        return getPrixUnitaire() * quantite; 
    }

    // Méthode pour afficher les détails de la ligne de commande
    public void afficherDetails() {
        System.out.println("Produit: " + produit.getNom());
        System.out.println("Quantité: " + quantite);
        System.out.println("Prix Unitaire: " + getPrixUnitaire());
        System.out.println("Prix Total: " + getPrixTotal());
    }
}
