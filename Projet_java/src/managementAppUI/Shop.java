package managementAppUI;

import basicObject.Client;
import basicObject.Commande;
import basicObject.Produit;
import toDB.CommandeToDB;
import DBTo.DBToproduit;
import DBTo.DBToclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Shop extends JFrame {
	
    private List<Produit> my_product;
    private DefaultListModel<String> listModel;
    private JList<String> productList;
    private JTextField searchField;
    private int client_id;

    public Shop(int client_id) {
        this.client_id = client_id;
        my_product = DBToproduit.getproduit();
        Client client = (Client) DBToclient.getClientByID(client_id);

        setTitle("Shop");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the list model and populate it with product names
        listModel = new DefaultListModel<>();
        for (Produit product : my_product) {
            listModel.addElement(product.getMarque() + " " + product.getModele());
        }

        // Create the product list and add it to a scroll pane
        productList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(productList);

        // Create the search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Create the order button
        JButton orderButton = new JButton("Order");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderProducts();
            }
        });

        // Create the orders button
        JButton ordersButton = new JButton("View Orders");
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrders();
            }
        });

        // Create a panel to hold the order and orders buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);
        buttonPanel.add(ordersButton);

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Search products based on the search field input
    private void searchProducts() {
        String searchTerm = searchField.getText().toLowerCase();
        listModel.clear();
        for (Produit product : my_product) {
            if (product.getMarque().toLowerCase().contains(searchTerm) || product.getModele().toLowerCase().contains(searchTerm)) {
                listModel.addElement(product.getMarque() + " " + product.getModele());
            }
        }
    }

    // Order selected products
    private void orderProducts() {
        List<String> selectedProductNames = productList.getSelectedValuesList();
        List<Produit> selectedProducts = DBToproduit.getproduitByName(selectedProductNames);

        if (selectedProductNames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products selected.");
        } else {
            Commande commande = new Commande(client_id);
            for (Produit produit : selectedProducts) {
                commande.ajouterProduit(produit, client_id);
            }
            CommandeToDB.addCommande(commande);
            StringBuilder orderSummary = new StringBuilder("You have ordered:\n");
            for (String productName : selectedProductNames) {
                orderSummary.append(productName).append("\n");
            }
            JOptionPane.showMessageDialog(this, orderSummary.toString());
        }
    }

    // View orders and their status
    private void viewOrders() {
        List<Commande> commandes = DBToclient.getCommandesByClientID(client_id);
        if (commandes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No orders found.");
        } else {
            StringBuilder ordersSummary = new StringBuilder("Your orders:\n");
            for (Commande commande : commandes) {
                ordersSummary.append("Order ID: ").append(commande.getId())
                        .append(", Date: ").append(commande.getDateCommande())
                        .append(", Status: ").append(commande.getEtat())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(this, ordersSummary.toString());
        }
    }

    public static void main(String[] args) {
        // Create and show the shop
        Shop shop = new Shop(1); // Assuming client_id is 1 for testing
        shop.setVisible(true);
    }
}
