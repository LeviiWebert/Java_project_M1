package basicObject;

import java.time.LocalDate;

public class Facture {
	
   

	private int id;                       // Identifiant unique de la facture
    private Commande commande;            // Référence à la commande associée
    private LocalDate dateFacture;        // Date à laquelle la facture a été générée
    private double montant;                // Montant total de la facture

    // Constructeur
    public Facture(int id, Commande commande) {
        this.id = id;                           // Initialisation de l'identifiant
        this.commande = commande;               // Référence à la commande
        this.dateFacture = LocalDate.now();     // Date actuelle
        this.montant = calculerMontant();       // Calculer le montant total de la commande
    }
    
    public Facture(int id, Commande commande,LocalDate dateFacture,double montant) {
        this.id = id;                           // Initialisation de l'identifiant
        this.commande = commande;               // Référence à la commande
        this.dateFacture = dateFacture;    // Date actuelle
        this.montant = montant;            // Calculer le montant total de la commande
    }

    // Méthode pour calculer le montant total de la commande
    private double calculerMontant() {
        double total = 0.0;
        for (LigneCommande ligne : commande.getLignes()) {
            total += ligne.getPrixTotal();       // Somme des prix de chaque ligne
        }
        return total;                             // Retourner le montant total
    }

    // Getters
    public void setId(int id) {
		this.id = id;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public void setDateFacture(LocalDate dateFacture) {
		this.dateFacture = dateFacture;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}
	
    public int getId() {
        return id;                             // Retourne l'identifiant de la facture
    }

    public Commande getCommande() {
        return commande;                       // Retourne la commande associée
    }

    public LocalDate getDateFacture() {
        return dateFacture;                   // Retourne la date de la facture
    }

    public double getMontant() {
        return montant;                       // Retourne le montant total de la facture
    }

    // Méthode pour afficher les détails de la facture
    public void afficherDetails() {
        System.out.println("Facture ID: " + id);
        System.out.println("Date de Facture: " + dateFacture);
        System.out.println("Commande associée: " + commande.getId());
        System.out.println("Montant Total: " + montant + " €");
        System.out.println("Détails de la commande :");
        for (LigneCommande ligne : commande.getLignes()) {
            ligne.afficherDetails();           // Afficher les détails de chaque ligne
        }
    }
}
