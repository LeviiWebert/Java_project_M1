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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            listModel.addElement(product.getModele());
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
        
     // Create the back button
        JButton backButton = new JButton("Back to Accueil");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToAccueil();
            }
        });

        // Add the back button to the button panel
        buttonPanel.add(backButton);


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
                listModel.addElement(product.getModele());
            }
        }
    }

    private void orderProducts() {
        List<String> selectedProductModeles = productList.getSelectedValuesList();
        List<Produit> selectedProducts = DBToproduit.getproduitByModele(selectedProductModeles);
        
        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products selected.");
            return;
        }

        
        Commande commande = new Commande(client_id);
        
        JDialog dialog = new JDialog(this, "Adjust Quantities", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        Map<Produit, Integer> productQuantities = new HashMap<>();
        JPanel productsPanel = new JPanel(new GridLayout(selectedProducts.size(), 1));

        for (Produit product : selectedProducts) {
            productQuantities.put(product, 0);  // Initialize quantity to 0

            JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel nameLabel = new JLabel(product.getMarque() + " " + product.getModele());
            JButton minusButton = new JButton("-");
            JLabel quantityLabel = new JLabel("0");
            JButton plusButton = new JButton("+");

            minusButton.addActionListener(e -> {
                int quantity = productQuantities.get(product);
                if (quantity > 0) {
                    productQuantities.put(product, --quantity);
                    quantityLabel.setText(String.valueOf(quantity));
                }
            });

            plusButton.addActionListener(e -> {
                int quantity = productQuantities.get(product);
                productQuantities.put(product, ++quantity);
                quantityLabel.setText(String.valueOf(quantity));
            });

            productPanel.add(nameLabel);
            productPanel.add(minusButton);
            productPanel.add(quantityLabel);
            productPanel.add(plusButton);

            productsPanel.add(productPanel);
        }

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            for (Produit product : selectedProducts) {
                int quantity = productQuantities.get(product);
                if (quantity > 0) {
                    commande.ajouterProduit(product, quantity);
                    System.out.print("Produit ajouté avec succès à la commande");                
                }
            }

            if (commande.getLignes().size() > 0) {
                CommandeToDB.addCommande(commande);
                JOptionPane.showMessageDialog(this, "Order placed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No quantities selected.");
            }

            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);

        dialog.add(new JScrollPane(productsPanel), BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);


        // Affichage du récapitulatif de la commande
        StringBuilder orderSummary = new StringBuilder("You have ordered:\n");
        for (String productName : selectedProductModeles) {
            orderSummary.append(productName).append("\n");
        }
        JOptionPane.showMessageDialog(this, orderSummary.toString());
    }


    private void viewOrders() {
        AccueilClient accueilClient = new AccueilClient(client_id);
        accueilClient.setVisible(true);
        this.dispose();
    }

    // Back to AccueilClient
    private void backToAccueil() {
        AccueilClient accueilClient = new AccueilClient(client_id);
        accueilClient.setVisible(true);
        this.dispose();
    }
    public static void main(String[] args) {
        // Create and show the shop
        Shop shop = new Shop(1); // Assuming client_id is 1 for testing
        shop.setVisible(true);
    }
}
