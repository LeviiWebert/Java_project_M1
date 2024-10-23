package basicObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DBTo.DBToclient;
import DBTo.DBTocommande;
import toDB.LigneCommandeToDB;


public class Commande {
	
    private int id;                // Identifiant unique de la commande
    private int clientId;          // Identifiant du client ayant passé la commande
    private Date dateCommande;     // Date à laquelle la commande a été passée
    private String etat;           // État de la commande (en_cours, validée, livrée)
    private List<LigneCommande>lignes; //Liste des lignes de commande (produits commandés)
    private double total;         // Montant total de la commande

    
    // Constructeur modifié
    
    public Commande(int clientId) {
    	
    	this.id  = DBTocommande.getMaxIdCommande() + 1;
        this.clientId = clientId;
        this.dateCommande = new Date();  // Date actuelle
        this.etat = "en cours";          // État initial
        this.lignes = new ArrayList<>(); // Initialiser la liste des lignes de commande
        this.total =  calculerTotal();   // Montant total
    }
    
public Commande(int id, int clientId) {
    	
    	this.id  = id;
        this.clientId = clientId;
        this.dateCommande = new Date();  // Date actuelle
        this.etat = "en cours";          // État initial
        this.lignes = new ArrayList<>(); // Initialiser la liste des lignes de commande
        this.total =  calculerTotal();   // Montant total
    }
    
    
    public Commande(int clientId, Date dateCommande, String etat, List<LigneCommande> lignes) {
    	
    	this.id  = DBTocommande.getMaxIdCommande() + 1;
        this.clientId = clientId;
        this.dateCommande = (dateCommande != null) ? dateCommande : new Date(); // Date actuelle par défaut si non fournie
        this.etat = (etat != null && !etat.isEmpty()) ? etat : "en cours"; // État par défaut "en_cours" si non fourni
        this.lignes = (lignes != null) ? lignes : new ArrayList<>();  // Initialiser la liste des lignes, vide si null
        this.total = calculerTotal();  // Calculer automatiquement le total en fonction des lignes de commande
        
    }
    
    public Commande(int id,int clientId, Date dateCommande, String etat, List<LigneCommande> lignes,double total) {
    	
    	this.id  = id;
        this.clientId = clientId;
        this.dateCommande = (dateCommande != null) ? dateCommande : new Date(); // Date actuelle par défaut si non fournie
        this.etat = (etat != null && !etat.isEmpty()) ? etat : "en cours"; // État par défaut "en_cours" si non fourni
        this.lignes = (lignes != null) ? lignes : new ArrayList<>();  // Initialiser la liste des lignes, vide si null
        this.total = calculerTotal();  // Calculer automatiquement le total en fonction des lignes de commande
        this.total = total;
    }


    // Méthode pour calculer le total de la commande
    private double calculerTotal() {
        double somme = 0.0;
        for (LigneCommande ligne : lignes) {
            somme += ligne.getPrixUnitaire() * ligne.getQuantite();
        }
        return somme;
    }

    // Méthode pour ajouter un produit à la commande
    public void ajouterProduit(Produit produit, int quantite) {
        // Crée une nouvelle ligne de commande
        LigneCommande ligne = new LigneCommande(this, produit, quantite);
        lignes.add(ligne);
        // Met à jour le total
        total += ligne.getPrixTotal(); // On suppose que la méthode getPrixTotal() existe dans LigneCommande
        LigneCommandeToDB.addLigneCommande(ligne);
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

    public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public void setLignes(List<LigneCommande> lignes) {
		this.lignes = lignes;
	}

	public void setTotal(double total) {
		this.total = total;
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
    
    public String toString() {
		StringBuffer sb  = new StringBuffer();
		
        sb.append("Commande ID: ").append(id).append("\n");
        sb.append("Client ID: ").append(clientId).append("\n");
        sb.append("Date de Commande: ").append(dateCommande).append("\n");
        sb.append("État de la Commande: ").append(etat).append("\n");
        sb.append("Produits Commandés:\n");
        
        for (LigneCommande ligne : lignes) {
            sb.append(" - ").append(ligne.getProduit().getMarque())
              .append(": ").append(ligne.getQuantite())
              .append(" à ").append(ligne.getPrixUnitaire())
              .append(" chacun\n");
        }
        
        sb.append("Total de la Commande: ").append(total).append("\n");
        
        return sb.toString(); // Retourne la chaîne de caractères complète
    }
}
