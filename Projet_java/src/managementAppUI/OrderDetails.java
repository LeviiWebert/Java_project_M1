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
        // Créer une nouvelle fenêtre pour afficher la facture
        JDialog factureDialog = new JDialog(this, "Facture", true);
        factureDialog.setSize(400, 300);
        factureDialog.setLayout(new BorderLayout());
        factureDialog.getContentPane().setBackground(Color.LIGHT_GRAY); // Fond de la fenêtre

        if (facture == null) {
            JOptionPane.showMessageDialog(factureDialog, "La facture n'existe pas", "Erreur", JOptionPane.ERROR_MESSAGE);
            factureDialog.dispose(); // Fermer le dialogue si la facture n'existe pas
            return;
        } else {
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

            // Ajouter le total de la facture
            JLabel totalLabel = new JLabel("Total: " + facture.getMontant() + "€", SwingConstants.CENTER);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
            totalLabel.setForeground(Color.RED);
            detailsPanel.add(totalLabel);

            factureDialog.add(detailsPanel, BorderLayout.CENTER);

            // Bouton de fermeture
            JButton closeButton = new JButton("Fermer");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    factureDialog.dispose();
                }
            });
            factureDialog.add(closeButton, BorderLayout.SOUTH);
        }

        // Afficher le dialogue
        factureDialog.setVisible(true);
    }
}
