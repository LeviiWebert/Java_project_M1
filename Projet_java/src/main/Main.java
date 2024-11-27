
package main;

import java.time.LocalDate;
import basicObject.*;
import service.*;
import toDB.ClientToDB;
import toDB.CommandeToDB;
import toDB.FactureToDB;
import toDB.ProduitToDB;
import managementAppUI.*;

public class Main {
	public static void main(String[] args) {
		AuthentificationClient a = new AuthentificationClient();
		a.setVisible(true);
		// Création d'un client
		Client client = new Client(new DateBDD(2002, 12, 10).getDateBDD(), "John Doe", "DOe", "john.doe@example.com",
				"123456789", "adresse", "1234");

		// Ajout du client à la base de données
		ClientToDB.addCustomer(client);

		// Création d'un produit
		Produit produit = new Produit(1, "Laptop", "", 999.99, "test", "A high-performance laptop", 10, null);
		// Ajout du produit à la base de données
		ProduitToDB.addProduit(produit);

		// Création d'une commande
		Commande commande = new Commande(client.getClientID());

		// Ajout du produit à la commande
		commande.ajouterProduit(produit, 2);

		// Validation de la commande
		commande.validerCommande();

		// Ajout de la commande à la base de données
		CommandeToDB.addCommande(commande);

		// Création d'une facture pour la commande
		Facture facture = new Facture(1, commande);

		// Ajout de la facture à la base de données
		FactureToDB.addFacture(facture);

		// Affichage des détails du client, de la commande, du produit et de la facture
		System.out.println("\nDétails du client :");
		client.afficherDetails();

		System.out.println("\nDétails de la commande :");
		commande.afficherDetails();

		System.out.println("\nDétails du produit :");
		System.out.println(produit.toString());

		System.out.println("\nDétails de la facture :");
		facture.afficherDetails();

		// Suppression du produit de la base de données
		ProduitToDB.deleteProduit(produit.getId());

		// Suppression du client de la base de données
		ClientToDB.deleteClient(client.getClientID());

		// Affichage des messages de suppression
		System.out.println("\nProduit et client supprimés de la base de données.");
	}
}
