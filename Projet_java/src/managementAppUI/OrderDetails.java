package managementAppUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import basicObject.Commande;
import basicObject.Facture;
import basicObject.LigneCommande;
import basicObject.Produit;

class OrderDetails extends JFrame {
    private Commande commande;

    public OrderDetails(Commande commande) {
        this.commande = commande;

        setTitle("Détails de la Commande");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Détails de la commande");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("Identifiant : " + commande.getId()));
        panel.add(new JLabel("Date : " + commande.getDateCommande()));
        panel.add(new JLabel("Statut : " + commande.getEtat()));

        panel.add(Box.createVerticalStrut(10));

        JLabel productsLabel = new JLabel("Produits :");
        productsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(productsLabel);

        for (LigneCommande ligne : commande.getLignes()) {
            JPanel productPanel = new JPanel(new BorderLayout());
            JLabel productLabel = new JLabel(ligne.getProduit().getMarque() + " : " +
                    ligne.getQuantite() + " x " + ligne.getPrixUnitaire() + " = " + ligne.getPrixTotal());
            productPanel.add(productLabel, BorderLayout.CENTER);

            // If you have product images, you can add them here
			ImageIcon icon = new ImageIcon(ligne.getProduit().getImage().getImage());
			Image productImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			JLabel imageLabel = new JLabel(new ImageIcon(productImage));
			productPanel.add(imageLabel, BorderLayout.WEST);
			
			panel.add(productPanel);
        }

        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Total : " + commande.getTotal()));

        panel.add(Box.createVerticalStrut(20));

        JButton factureButton = new JButton("Voir la facture de la commande");
        factureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherFacture(commande.getFacture());
            }
        });
        panel.add(factureButton, BorderLayout.SOUTH);
        
     // Add the details panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
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
