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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop extends JFrame {
    private List<Produit> my_products;
    private DefaultListModel<String> listModel;
    private JList<String> productList;
    private JTextField searchField;
    private int client_id;
    private List<Produit> panier;

    public Shop(int client_id) {
    	listModel = new DefaultListModel<String>();
        this.client_id = client_id;
        my_products = DBToproduit.getproduit();
        Client client = (Client) DBToclient.getClientByID(client_id);
        this.panier = new ArrayList<Produit>();

        setTitle("Boutique");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(0, 2));  // Affichage vertical des produits

        for (Produit product : my_products) {
            JPanel panel = createProductPanel(product);
            productPanel.add(panel);
        }
        productPanel.setBackground(new Color(173, 216, 230));
        
        JScrollPane scrollPane = new JScrollPane(productPanel);

        
        // Create the search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProducts(productPanel);
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);


        // Button pour voir ses commandes
        JButton ordersButton = new JButton("Voir mes commandes");
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrders();
            }
        });

        // Create a panel to hold the order and orders buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ordersButton);
        
        // Create the back button
        JButton backButton = new JButton("Retourner à l'Accueil");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToAccueil();
            }
        });

        // Add the back button to the button panel
        buttonPanel.add(backButton);

        // Bouton pour voir le panier
        JButton viewCartButton = new JButton("Voir le Panier");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCart();
            }
        });
        buttonPanel.add(viewCartButton, BorderLayout.SOUTH);

        // Add components to the frame
        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void searchProducts(JPanel panel) {
        String searchTerm = searchField.getText().toLowerCase().trim(); 
        panel.removeAll(); 

        
        if (searchTerm.isEmpty()) {
            panel.revalidate(); 
            panel.repaint();
            return;
        }

        for (Produit product : my_products) {
            if (product.getMarque().toLowerCase().contains(searchTerm) || 
                product.getModele().toLowerCase().contains(searchTerm) || 
                product.getType().toLowerCase().contains(searchTerm)) {
                JPanel productPanel = createProductPanel(product);
                panel.add(productPanel);
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    

    private void viewOrders() {
        Orders orders = new Orders(client_id);
        orders.setVisible(true);
        this.dispose();
    }

    // Back to AccueilClient
    private void backToAccueil() {
        AccueilClient accueilClient = new AccueilClient(client_id);
        accueilClient.setVisible(true);
        this.dispose();
    }
    
    
    // Créer un panel pour chaque produit avec un bouton pour afficher les détails et ajouter au panier
    private JPanel createProductPanel(Produit product) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(173, 216, 230));

        ImageIcon icon = new ImageIcon(product.getImage().getImage());
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Redimensionner à 100x100 pixels
        JLabel imageLabel = new JLabel(new ImageIcon(img)); 
        JLabel detailsLabel = new JLabel("<html>" + "Marque : " + product.getMarque() 
        + "<br>" + "Modèle : " + product.getModele() 
        + "<br>" + "Type : " + product.getType() 
        + "<br>" + "Prix : " + product.getPrix() + "€</html>");
        JButton detailsButton = new JButton("Détails");
        JButton addToCartButton = new JButton("Ajouter au panier");
        panel.setPreferredSize(new Dimension(150, 200));
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DetailsProduit(product).setVisible(true);
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panier.add(product);
                JOptionPane.showMessageDialog(null, product.getModele() + " ajouté au panier !");
            }
        });

        panel.add(imageLabel, BorderLayout.WEST);
        panel.add(detailsLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(detailsButton);
        buttonPanel.add(addToCartButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void viewCart() {
        new Panier(panier,client_id).setVisible(true);;
    }
    
    public static void main(String[] args) {
        // Create and show the shop
        Shop shop = new Shop(1); // Assuming client_id is 1 for testing
        shop.setVisible(true);
    }
}
