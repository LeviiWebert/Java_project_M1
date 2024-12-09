package managementAppUI;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import basicObject.Commande;
import basicObject.Facture;
import basicObject.LigneCommande;
import basicObject.Produit;
import toDB.CommandeToDB;
import toDB.FactureToDB;

public class Panier extends JFrame {
    private static final long serialVersionUID = 1L;
	private List<Produit> cart;
    private JPanel cartPanel;

    public Panier(List<Produit> cart, int clientId) {
        this.cart = cart;

        setTitle("Votre Panier");
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);

        // Titre
        JLabel titleLabel = new JLabel("🛒 Votre Panier", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 51, 102));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel des produits
        cartPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        cartPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        updateCartPanel();

        // Bas de la fenêtre
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);

        JButton checkoutButton = new JButton("Passer la commande");
        checkoutButton.setBackground(new Color(0, 153, 51));
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkoutButton.setFocusPainted(false);
        checkoutButton.addActionListener(e -> passerCommande(clientId));

        bottomPanel.add(checkoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateCartPanel() {
        cartPanel.removeAll();

        if (cart.isEmpty()) {
            JLabel emptyLabel = new JLabel("Votre panier est vide.", JLabel.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            emptyLabel.setForeground(new Color(128, 128, 128));
            cartPanel.add(emptyLabel);
        } else {
            for (Produit produit : cart) {
                cartPanel.add(createProductPanel(produit));
            }
        }

        cartPanel.revalidate();
        cartPanel.repaint();
    }

    private JPanel createProductPanel(Produit produit) {
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBackground(new Color(240, 248, 255));
        productPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 2));
        productPanel.setPreferredSize(new Dimension(400, 150));

        // Image du produit
        ImageIcon icon = new ImageIcon(produit.getImage().getImage());
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img), JLabel.CENTER);

        // Détails du produit
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.setBackground(new Color(240, 248, 255));
        JLabel productName = new JLabel("<html><b>" + produit.getMarque() + " " + produit.getModele() + "</b></html>");
        productName.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel productPrice = new JLabel("Prix: " + produit.getPrix() + "€");
        productPrice.setFont(new Font("Arial", Font.PLAIN, 12));
        detailsPanel.add(productName);
        detailsPanel.add(productPrice);

        // Bouton pour supprimer
        JButton removeButton = new JButton("🗑 Supprimer");
        removeButton.setBackground(new Color(255, 69, 0));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFont(new Font("Arial", Font.BOLD, 12));
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(e -> {
            cart.remove(produit);
            updateCartPanel();
        });

        // Organisation des composants
        productPanel.add(imageLabel, BorderLayout.WEST);
        productPanel.add(detailsPanel, BorderLayout.CENTER);
        productPanel.add(removeButton, BorderLayout.EAST);

        return productPanel;
    }

    private void passerCommande(int clientId) {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Votre panier est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Afficher une boîte de confirmation avant de continuer
        int response = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir passer la commande ?",
                "Confirmation de commande",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            // Création de la commande
            Commande commande = new Commande(clientId);
            double montantTotal = 0;

            for (Produit produit : cart) {
                int quantite = 1; // Quantité par défaut
                commande.ajouterProduit(produit, quantite);
                montantTotal += produit.getPrix() * quantite;
            }

            // Enregistrement de la commande et de la facture
            CommandeToDB.addCommande(commande);
            Facture facture = new Facture(commande);
            facture.setDateFacture(LocalDate.now());
            facture.setMontant(montantTotal);
            FactureToDB.addFacture(facture);

            // Confirmation et affichage de facture
            JOptionPane.showMessageDialog(this, "Commande passée avec succès !");
            cart.clear(); // Vider le panier après la commande
            updateCartPanel();

            int factureResponse = JOptionPane.showOptionDialog(
                    this,
                    "Votre commande a été passée avec succès. Voulez-vous voir la facture ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Oui", "Non"},
                    "Oui"
            );

            if (factureResponse == JOptionPane.YES_OPTION) {
                afficherFacture(facture);
            }
        } else {
            // L'utilisateur a annulé
            JOptionPane.showMessageDialog(this, "Commande annulée.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void afficherFacture(Facture facture) {
        // Créer une fenêtre modale pour afficher la facture
        JDialog factureDialog = new JDialog(this, "Facture VéloDauphine", true);
        factureDialog.setSize(600, 500);
        factureDialog.setLayout(new BorderLayout());
        
        // En-tête avec le nom de la boutique et un logo (si disponible)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219)); // Bleu de la boutique
        
        // Nom de la boutique avec un style spécifique
        JLabel titleLabel = new JLabel("VéloDauphine", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Sous-titre de la facture avec l'ID de la facture
        JLabel factureTitle = new JLabel("Facture #" + facture.getId(), JLabel.CENTER);
        factureTitle.setFont(new Font("Arial", Font.ITALIC, 16));
        factureTitle.setForeground(Color.WHITE);
        headerPanel.add(factureTitle, BorderLayout.SOUTH);
        
        factureDialog.add(headerPanel, BorderLayout.NORTH);

        // Panel des détails de la facture
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajouter les détails des produits de la facture
        for (LigneCommande ligne : facture.getCommande().getLignes()) {
            Produit produit = ligne.getProduit();
            
            // Créer un format de ligne pour chaque produit
            JPanel productPanel = new JPanel(new GridLayout(1, 4, 10, 10));
            productPanel.setBackground(Color.WHITE);
            productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JLabel marqueLabel = new JLabel(produit.getMarque(), JLabel.LEFT);
            marqueLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel modeleLabel = new JLabel(produit.getModele(), JLabel.LEFT);
            modeleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel quantiteLabel = new JLabel("Quantité: " + ligne.getQuantite(), JLabel.CENTER);
            quantiteLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JLabel prixLabel = new JLabel(String.format("%.2f€", produit.getPrix()), JLabel.RIGHT);
            prixLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            productPanel.add(marqueLabel);
            productPanel.add(modeleLabel);
            productPanel.add(quantiteLabel);
            productPanel.add(prixLabel);

            detailsPanel.add(productPanel);
        }

        // Total de la facture
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBackground(Color.WHITE);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel totalLabel = new JLabel("Total: " + facture.getMontant() + "€", JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(Color.RED);
        totalPanel.add(totalLabel, BorderLayout.CENTER);
        
        detailsPanel.add(totalPanel);

        // Ajouter les détails au dialogue
        factureDialog.add(detailsPanel, BorderLayout.CENTER);

        // Bouton pour fermer la fenêtre de facture
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Fermer");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setBackground(new Color(52, 152, 219));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        closeButton.addActionListener(e -> factureDialog.dispose());
        
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(closeButton);
        factureDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Centrer le dialogue par rapport à la fenêtre principale
        factureDialog.setLocationRelativeTo(this);
        factureDialog.setVisible(true);
    }

}
