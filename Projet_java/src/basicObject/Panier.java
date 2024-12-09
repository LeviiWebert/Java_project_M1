package basicObject;

import java.util.ArrayList;
import java.util.List;

public class Panier {

	private int id;
	private int clientId; // Identifiant du client
	private List<LigneCommande> lignes; // Liste des lignes de commande (produits ajoutés)
	private double total; // Montant total du panier

	// Constructeur
	public Panier(int clientId) {

		this.clientId = clientId; // Initialisation de l'identifiant du client
		this.lignes = new ArrayList<>(); // Initialisation de la liste des lignes de commande
		this.total = 0.0; // Initialisation du total
	}

	// Méthode pour ajouter un produit au panier
	public void ajouterProduit(Produit produit, int quantite) {
		// Vérifier la disponibilité du produit
		if (produit.estDisponible(quantite)) {
			// Créer une nouvelle ligne de commande
			LigneCommande ligne = new LigneCommande(null, produit, quantite);
			lignes.add(ligne); // Ajouter la ligne au panier
			total += ligne.getPrixTotal(); // Mettre à jour le total
			produit.reduireStock(quantite); // Réduire le stock du produit
			System.out.println(quantite + " " + produit.getMarque() + " ajouté au panier.");
		} else {
			System.out.println("Stock insuffisant pour " + produit.getMarque());
		}
	}

	// Méthode pour retirer un produit du panier
	public void retirerProduit(Produit produit) {
		for (LigneCommande ligne : lignes) {
			if (ligne.getProduit().getId() == produit.getId()) {
				total -= ligne.getPrixTotal(); // Mettre à jour le total
				lignes.remove(ligne); // Retirer la ligne du panier
				produit.setQuantite_stock((produit.getQuantite_stock() + ligne.getQuantite())); // Rétablir le stock du
																								// produit
				System.out.println(produit.getMarque() + " retiré du panier.");
				return; // Sortir de la méthode
			}
		}
		System.out.println(produit.getMarque() + " n'est pas dans le panier.");
	}

	// Méthode pour afficher les détails du panier
	public void afficherDetails() {
		System.out.println("Détails du Panier pour le Client ID: " + clientId);
		if (lignes.isEmpty()) {
			System.out.println("Le panier est vide.");
			return;
		}
		for (LigneCommande ligne : lignes) {
			ligne.afficherDetails(); // Afficher les détails de chaque ligne
		}
		System.out.println("Total du Panier: " + total + " €");
	}

	// Méthode pour valider le panier et créer une commande
	public Commande validerPanier() {
		Commande commande = new Commande(clientId); // Créer une nouvelle commande
		for (LigneCommande ligne : lignes) {
			commande.ajouterProduit(ligne.getProduit(), ligne.getQuantite()); // Ajouter chaque produit à la commande
		}
		lignes.clear(); // Vider le panier après validation
		total = 0.0; // Réinitialiser le total
		System.out.println("Panier validé. Commande créée pour le Client ID: " + clientId);
		return commande; // Retourner la commande
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public List<LigneCommande> getLignes() {
		return lignes;
	}

	public void setLignes(List<LigneCommande> lignes) {
		this.lignes = lignes;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
