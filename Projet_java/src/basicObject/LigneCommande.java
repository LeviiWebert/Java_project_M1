package basicObject;

import DBTo.DBTolignecommande;

public class LigneCommande {

	private int id;
	private Commande commande;
	private Produit produit;
	private int quantite;

	public LigneCommande(Commande commande, Produit produit, int quantite) {
        this.setId(DBTolignecommande.getMaxLigneCommande() + 1);                           // Initialisation de l'identifiant
		this.commande = commande;
		this.produit = produit;
		this.quantite = quantite;
	}

//    public LigneCommande(int produitId, int quantite2, double prixUnitaire) {}

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
		System.out.println("Produit: " + produit.getMarque());
		System.out.println("Quantité: " + quantite);
		System.out.println("Prix Unitaire: " + getPrixUnitaire());
		System.out.println("Prix Total: " + getPrixTotal());
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();

		sb.append("Produit: ").append(produit.getMarque()).append("\n");
		sb.append("Quantité: ").append(quantite).append("\n");
		sb.append("Prix Unitaire: ").append(getPrixUnitaire()).append("\n");
		sb.append("Prix Total: ").append(getPrixTotal()).append("\n");

		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
