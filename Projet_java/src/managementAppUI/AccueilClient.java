package managementAppUI;

import basicObject.Client;
import basicObject.Commande;
import basicObject.Facture;
import basicObject.LigneCommande;
import basicObject.Produit;
import service.DBconnection;
import toDB.CommandeToDB;
import DBTo.DBToproduit;
import DBTo.DBToclient;
import DBTo.DBTocommande;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AccueilClient extends JFrame {
    private int client_id;
    private DefaultListModel<String> listModel;
    private JList<String> orderList;

    public AccueilClient(int client_id) {
        this.client_id = client_id;
        Client client = (Client) DBToclient.getClientByID(client_id);

        setTitle("Accueil Client");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the list model and populate it with order IDs
        listModel = new DefaultListModel<>();
        System.out.print(client_id+"\n");
        List<Commande> commandes = DBToclient.getCommandesByClientID(client_id);
        for (Commande commande : commandes) {
            listModel.addElement("Order ID: " + commande.getId() + " - Date: " + commande.getDateCommande());
        }

        // Create the order list and add it to a scroll pane
        orderList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(orderList);

        // Create the view order details button
        JButton viewOrderDetailsButton = new JButton("Details de la commande");
        viewOrderDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrderDetails();
            }
        });

        
        // Create the shop button
        JButton shopButton = new JButton("Boutique");
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShop();
            }
        });
        
        JButton deleteOrderButton = new JButton("Annuler ma commande");
        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.add(viewOrderDetailsButton);
        buttonPanel.add(shopButton);
        buttonPanel.add(deleteOrderButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
    }

 // View order details
    private void viewOrderDetails() {
        String selectedOrder = orderList.getSelectedValue();
        if (selectedOrder == null) {
            JOptionPane.showMessageDialog(this, "Sélectionner une commande.");
            return;
        }

        int orderId = Integer.parseInt(selectedOrder.split(" ")[2]);
        Commande commande = DBTocommande.getCommandeById(orderId);
        if (commande == null) {
            JOptionPane.showMessageDialog(this, "Commande non trouvée.");
            return;
        }

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
            JLabel productLabel = new JLabel(ligne.getProduit().getMarque() + " : " +
                    ligne.getQuantite() + " x " + ligne.getPrixUnitaire() + " = " + ligne.getPrixTotal());
            panel.add(productLabel);
        }

        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Total : " + commande.getTotal()));

        panel.add(Box.createVerticalStrut(20));
        
        JButton factureButton = new JButton("Voir la facture de la commande");
        factureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.print(commande);
                afficherFacture(commande.getFacture());
            }
        });
        panel.add(factureButton);
        

        JDialog dialog = new JDialog((Frame) null, "Détails de la Commande", true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

	 // Method to delete an order
    private void deleteOrder() {
        String selectedOrder = orderList.getSelectedValue();
        if (selectedOrder == null) {
            JOptionPane.showMessageDialog(this, "No order selected.");
            return;
        }

        int orderId = Integer.parseInt(selectedOrder.split(" ")[2]);
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Order ID: " + orderId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            CommandeToDB.deleteCommande(orderId);
            listModel.removeElement(selectedOrder);
            JOptionPane.showMessageDialog(this, "Order ID: " + orderId + " has been deleted.");
        }
    }

    

    // Open the shop window
    private void openShop() {
        Shop shop = new Shop(client_id);
        shop.setVisible(true);
        this.dispose();
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
        }
        else {
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
    
    
    public static void main(String[] args) {
        // Create and show the AccueilClient
        AccueilClient accueilClient = new AccueilClient(1); // Assuming client_id is 1 for testing
        accueilClient.setVisible(true);
    }
}
