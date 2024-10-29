package managementAppUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import basicObject.Commande;
import basicObject.Facture;
import basicObject.LigneCommande;
import basicObject.Produit;
import toDB.CommandeToDB;
import toDB.FactureToDB;

import DBTo.DBToproduit;

import java.io.FileNotFoundException;

public class Panier extends JFrame {
    private List<Produit> cart;

    public Panier(List<Produit> cart,int clientId) {
        this.cart = cart;

        setTitle("Panier");
        setSize(600, 400);
        setLayout(new BorderLayout());

        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new GridLayout(0, 1));

        for (Produit Produit : cart) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel imageLabel = new JLabel(Produit.getImage());
            JLabel detailsLabel = new JLabel("<html>" + Produit.getMarque() + "<br>" + Produit.getPrix() + "€</html>");
            panel.add(imageLabel, BorderLayout.WEST);
            panel.add(detailsLabel, BorderLayout.CENTER);
            cartPanel.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Bouton pour passer la commande
        JButton checkoutButton = new JButton("Passer la commande");
        checkoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				passerCommande(clientId);
                cart.clear();  // Vide le panier après la commande
                dispose();
				
			}
		});
        add(checkoutButton, BorderLayout.SOUTH);
    }
    
    private void passerCommande(int clientId) {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Votre panier est vide.");
            return;
        }

        // Créer une nouvelle commande pour le client
        Commande commande = new Commande(clientId);
        double montantTotal = 0;

        for (Produit produit : cart) {
            int quantite = 1;  // On suppose une quantité de 1 pour chaque produit dans le panier
            commande.ajouterProduit(produit, quantite);
            montantTotal += produit.getPrix() * quantite;
        }

        // Enregistrer la commande dans la base de données
        CommandeToDB.addCommande(commande);

        // Créer et enregistrer la facture
        // Enregistre la facture dans la base de données
        Facture facture = new Facture(commande);
        facture.setDateFacture(LocalDate.now());
        facture.setMontant(montantTotal);
        FactureToDB.addFacture(facture); 

        // Afficher un message de confirmation avec un bouton pour télécharger le PDF
        JOptionPane.showMessageDialog(this, "Commande passée avec succès ! Vous pouvez voir la facture.");
        JButton downloadButton = new JButton("Afficher la facture");

        downloadButton.addActionListener(e -> {
        	afficherFacture(facture);
        });

        // Fenêtre de confirmation avec téléchargement de facture
        JDialog confirmationDialog = new JDialog(this, "Confirmation de commande", true);
        confirmationDialog.setLayout(new BorderLayout());
        confirmationDialog.setSize(300, 200);
        confirmationDialog.setLocationRelativeTo(this);
        confirmationDialog.add(new JLabel("Commande passée avec succès !"), BorderLayout.CENTER);
        confirmationDialog.add(downloadButton, BorderLayout.SOUTH);
        confirmationDialog.setVisible(true);
    }

    private void afficherFacture(Facture facture) {
        // Créer une nouvelle fenêtre pour afficher la facture
        JDialog factureDialog = new JDialog(this, "Facture", true);
        factureDialog.setSize(400, 300);
        factureDialog.setLayout(new BorderLayout());
        factureDialog.getContentPane().setBackground(Color.LIGHT_GRAY); // Fond de la fenêtre

        // Titre de la facture
        JLabel titleLabel = new JLabel("Facture #" + facture.getId(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        factureDialog.add(titleLabel, BorderLayout.NORTH);

        // Panel pour afficher les détails de la facture
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(0, 1));
        detailsPanel.setBackground(Color.WHITE); // Fond blanc pour les détails

        // Ajouter les détails des lignes de commande
        for (LigneCommande ligne : facture.getCommande().getLignes()) {
            Produit produit = ligne.getProduit();
            int quantite = ligne.getQuantite();
            double prix = produit.getPrix();

            // Ajouter une ligne avec les détails du produit
            String details = String.format("%s %s - Quantité: %d - Prix: %.2f€", 
                produit.getMarque(), produit.getModele(), quantite, prix);
            JLabel detailLabel = new JLabel(details);
            detailLabel.setForeground(Color.BLACK);
            detailsPanel.add(detailLabel);
        }

        // Ajouter le panel des détails à la fenêtre
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        factureDialog.add(scrollPane, BorderLayout.CENTER);

        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(e -> factureDialog.dispose());
        factureDialog.add(closeButton, BorderLayout.SOUTH);

        // Afficher la fenêtre de la facture
        factureDialog.setLocationRelativeTo(this);
        factureDialog.setVisible(true);
    }

    
   

}