package basicObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Commande {
	
    private int id;                // Identifiant unique de la commande
    private int clientId;          // Identifiant du client ayant passé la commande
    private Date dateCommande;     // Date à laquelle la commande a été passée
    private String etat;           // État de la commande (en_cours, validée, livrée)
    private List<LigneCommande> lignes;  // Liste des lignes de commande (produits commandés)
    private double total;          // Montant total de la commande

    // Constructeur
    public Commande(int clientId) {
    	
        this.clientId = clientId;
        this.dateCommande = new Date(); // Date actuelle
        this.etat = "en_cours";          // État initial
        this.lignes = new ArrayList<>(); // Initialiser la liste des lignes de commande
        this.total = 0.0;                // Montant total initial
    }

    // Méthode pour ajouter un produit à la commande
    public void ajouterProduit(Produit produit, int quantite) {
        // Crée une nouvelle ligne de commande
        LigneCommande ligne = new LigneCommande(this, produit, quantite);
        lignes.add(ligne);
        // Met à jour le total
        total += ligne.getPrixTotal(); // On suppose que la méthode getPrixTotal() existe dans LigneCommande
    }

    // Méthode pour valider la commande
    public void validerCommande() {
        this.etat = "validée";
        // Ici, vous pourriez également créer une facture, mettre à jour le stock des produits, etc.
        // Par exemple : DBToFacture.creerFacture(this);
    }

    // Méthode pour annuler la commande
    public void annulerCommande() {
        this.etat = "annulée";
        // Vous pourriez également gérer des retours de stock si nécessaire
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public String getEtat() {
        return etat;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }

    public double getTotal() {
        return total;
    }

    // Méthode pour afficher les détails de la commande
    public void afficherDetails() {
        System.out.println("Commande ID: " + id);
        System.out.println("Client ID: " + clientId);
        System.out.println("Date de Commande: " + dateCommande);
        System.out.println("État de la Commande: " + etat);
        System.out.println("Produits Commandés:");
        for (LigneCommande ligne : lignes) {
            System.out.println(" - " + ligne.getProduit().getMarque() + ": " + ligne.getQuantite() + " à " + ligne.getPrixUnitaire() + " chacun");
        }
        System.out.println("Total de la Commande: " + total);
    }
}
