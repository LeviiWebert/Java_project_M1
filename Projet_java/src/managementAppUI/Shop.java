package managementAppUI;

import basicObject.Client;
import basicObject.Commande;
import basicObject.Produit;
import service.LoadingServiceUI;
import toDB.CommandeToDB;
import DBTo.DBToproduit;
import DBTo.DBToclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Shop extends JFrame {
    private List<Produit> my_products;
    private List<Produit> panier;
    private JTextField searchField;
    private JPanel productPanel;
    private JScrollPane scrollPane;
    private int client_id;

    public Shop(int client_id, LoadingServiceUI loadingService) {
        // Chargement des produits
        this.client_id = client_id;
        this.panier = new ArrayList<>();
        my_products = DBToproduit.getproduit();
        loadingService.hideLoadingDialog();

        // Configuration de la fenêtre
        setTitle("Boutique de Vélos - Découvrez nos modèles");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245)); // Fond doux et uniforme

        // Barre de recherche
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(30, 144, 255)); // Bleu vif

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton searchButton = new JButton("🔍 Rechercher");
        searchButton.setBackground(new Color(255, 215, 0));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton resetButton = new JButton("⟲ Revenir à la boutique");
        resetButton.setBackground(new Color(0, 51, 102));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        resetButton.setVisible(false);

        searchButton.addActionListener(e -> searchProducts());
        resetButton.addActionListener(e -> resetShop(resetButton));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        searchPanel.add(resetButton, BorderLayout.WEST);

        // Liste des produits
        productPanel = new JPanel(new GridLayout(0, 3, 15, 15)); // Grille avec espaces entre produits
        productPanel.setBackground(new Color(245, 245, 245));
        loadProducts();

        scrollPane = new JScrollPane(productPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Bas de la fenêtre
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(30, 144, 255)); // Bleu vif

        JButton viewOrdersButton = new JButton("📦 Voir mes commandes");
        JButton viewCartButton = new JButton("🛒 Voir le Panier");
        JButton backButton = new JButton("🏠 Retour à l'Accueil");

        viewOrdersButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewCartButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        viewOrdersButton.setBackground(new Color(50, 205, 50));
        viewCartButton.setBackground(new Color(100, 149, 237));
        backButton.setBackground(new Color(0, 51, 102));

        viewOrdersButton.setForeground(Color.WHITE);
        viewCartButton.setForeground(Color.WHITE);
        backButton.setForeground(Color.WHITE);

        viewOrdersButton.addActionListener(e -> viewOrders());
        viewCartButton.addActionListener(e -> viewCart());
        backButton.addActionListener(e -> backToAccueil());

        buttonPanel.add(viewOrdersButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(backButton);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Chargement initial des produits
    private void loadProducts() {
        productPanel.removeAll();
        for (Produit product : my_products) {
            JPanel panel = createProductPanel(product);
            productPanel.add(panel);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    // Création d'un panel produit stylisé
    private JPanel createProductPanel(Produit product) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 1));
        panel.setPreferredSize(new Dimension(250, 300));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur interactif

        // Image du produit
        ImageIcon icon = new ImageIcon(product.getImage().getImage());
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img), JLabel.CENTER);

        // Détails du produit
        JLabel detailsLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<b>" + product.getMarque() + "</b><br>"
                + product.getModele() + "<br>"
                + "Type : " + product.getType() + "<br>"
                + "<b>Prix : " + product.getPrix() + " €</b></div></html>", JLabel.CENTER);
        detailsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Boutons
        JButton detailsButton = new JButton("📖 Détails");
        JButton addToCartButton = new JButton("➕ Ajouter au panier");

        detailsButton.setBackground(new Color(255, 215, 0));
        addToCartButton.setBackground(new Color(50, 205, 50));

        detailsButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        addToCartButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        detailsButton.setForeground(Color.BLACK);
        addToCartButton.setForeground(Color.WHITE);

        detailsButton.addActionListener(e -> new DetailsProduit(product).setVisible(true));
        addToCartButton.addActionListener(e -> {
            panier.add(product);
            JOptionPane.showMessageDialog(this, product.getModele() + " ajouté au panier !");
        });

        // Organisation
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(detailsButton);
        buttonPanel.add(addToCartButton);

        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(detailsLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void searchProducts() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        productPanel.removeAll();

        List<Produit> filteredProducts = new ArrayList<>();
        for (Produit product : my_products) {
            if (product.getMarque().toLowerCase().contains(searchTerm) ||
                product.getModele().toLowerCase().contains(searchTerm) ||
                product.getType().toLowerCase().contains(searchTerm)) {
                filteredProducts.add(product);
            }
        }

        if (filteredProducts.isEmpty()) {
            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.setBackground(Color.WHITE);

            JLabel messageLabel = new JLabel("🚫 Aucun produit trouvé pour : " + searchTerm, JLabel.CENTER);
            messageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

            JButton backButton = new JButton("⟲ Revenir à la boutique");
            backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
            backButton.setBackground(new Color(255, 69, 0));
            backButton.setForeground(Color.WHITE);
            backButton.addActionListener(e -> resetShop(backButton));

            messagePanel.add(messageLabel, BorderLayout.CENTER);
            messagePanel.add(backButton, BorderLayout.SOUTH);

            productPanel.add(messagePanel);
        } else {
            for (Produit product : filteredProducts) {
                productPanel.add(createProductPanel(product));
            }
        }

        productPanel.revalidate();
        productPanel.repaint();
    }

    private void resetShop(JButton resetButton) {
        searchField.setText("");
        loadProducts();
        resetButton.setVisible(false);
    }

    private void viewOrders() {
        Orders orders = new Orders(client_id);
        orders.setVisible(true);
        this.dispose();
    }

    private void backToAccueil() {
        AccueilClient accueilClient = new AccueilClient(client_id);
        accueilClient.setVisible(true);
        this.dispose();
    }

    private void viewCart() {
        new Panier(panier, client_id).setVisible(true);
    }
}
