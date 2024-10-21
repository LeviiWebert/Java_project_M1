package managementAppUI;

import basicObject.Produit;
import service.DBconnection; // Assure-toi d'importer ta classe de connexion à la base de données
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Shop extends JFrame {
    private List<Produit> my_product;
    private DefaultListModel<String> listModel;
    private JList<String> productList;
    private JTextField searchField;

    public Shop() {
        my_product = getproduit();

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

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(orderButton, BorderLayout.SOUTH);
    }

    // Fetch all products from the database
    public static List<Produit> getproduit() {
        List<Produit> produit = new ArrayList<>();

        try (Connection connection = DBconnection.getConnection()) {
            String query = "SELECT * FROM produit";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int ID = resultSet.getInt("id");
                    String marque = resultSet.getString("marque");
                    String modele = resultSet.getString("modele");
                    double prix = resultSet.getDouble("prix");
                    String type = resultSet.getString("type");
                    String description = resultSet.getString("description");
                    int quantite_stock = resultSet.getInt("quantite_stock");

                    Produit prduit = new Produit(ID, marque, modele, prix, type, description, quantite_stock);
                    produit.add(prduit);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produit;
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
        List<String> selectedProducts = productList.getSelectedValuesList();
        if (selectedProducts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No products selected.");
        } else {
            StringBuilder orderSummary = new StringBuilder("You have ordered:\n");
            for (String productName : selectedProducts) {
                orderSummary.append(productName).append("\n");
            }
            JOptionPane.showMessageDialog(this, orderSummary.toString());
        }
    }

    public static void main(String[] args) {
        // Create and show the shop
        Shop shop = new Shop();
        shop.setVisible(true);
    }
}
