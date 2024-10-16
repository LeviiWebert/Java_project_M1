package basicObject;

public class Produit {
    private int id;                 // Identifiant unique du produit
    private String nom;             // Nom du produit
    private double prix;            // Prix unitaire du produit
    private String description;      // Description du produit
    private int stock;              // Quantité disponible en stock

    // Constructeur
    public Produit(int id, String nom, double prix, String description, int stock) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.stock = stock;
    }

    // Getters
    public int getId() {
        return id;                  // Retourne l'identifiant du produit
    }

    public String getNom() {
        return nom;                 // Retourne le nom du produit
    }

    public double getPrix() {
        return prix;                // Retourne le prix unitaire du produit
    }

    public String getDescription() {
        return description;         // Retourne la description du produit
    }

    public int getStock() {
        return stock;               // Retourne la quantité en stock
    }

    // Setter pour mettre à jour le stock
    public void setStock(int stock) {
        this.stock = stock;         // Met à jour la quantité en stock
    }

    // Méthode pour vérifier la disponibilité du produit
    public boolean estDisponible(int quantite) {
        return stock >= quantite;   // Vérifie si la quantité demandée est disponible
    }

    // Méthode pour réduire le stock lors d'une commande
    public void reduireStock(int quantite) {
        if (estDisponible(quantite)) {
            stock -= quantite;      // Réduit le stock de la quantité commandée
        } else {
            System.out.println("Stock insuffisant pour " + nom);
        }
    }

    // Méthode pour afficher les détails du produit
    public void afficherDetails() {
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Prix: " + prix + " €");
        System.out.println("Description: " + description);
        System.out.println("Stock disponible: " + stock);
    }
}
