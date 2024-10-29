package managementAppUI;

import basicObject.Client;
import basicObject.Commande;
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
        System.out.print(commandes);
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
            JOptionPane.showMessageDialog(this, "Selectionner une commande.");
            return;
        }

        int orderId = Integer.parseInt(selectedOrder.split(" ")[2]);
        Commande commande = DBTocommande.getCommandeById(orderId);
        if (commande == null) {
            JOptionPane.showMessageDialog(this, "Commande non trouvé.");
            return;
        }

        StringBuilder orderDetails = new StringBuilder("Détails de la commande:\n");
        orderDetails.append("Identifiant : ").append(commande.getId()).append("\n");
        orderDetails.append("Date : ").append(commande.getDateCommande()).append("\n");
        orderDetails.append("Status : ").append(commande.getEtat()).append("\n");
        orderDetails.append("Produits :\n");
        for (LigneCommande ligne : commande.getLignes()) {
            orderDetails.append(ligne.getProduit().getMarque()).append(" : ")
                        .append(ligne.getQuantite()).append(" x ")
                        .append(ligne.getPrixUnitaire()).append(" = ")
                        .append(ligne.getPrixTotal()).append("\n");
        }
        orderDetails.append("Total: ").append(commande.getTotal());

        JOptionPane.showMessageDialog(this, orderDetails.toString());
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

    public static void main(String[] args) {
        // Create and show the AccueilClient
        AccueilClient accueilClient = new AccueilClient(1); // Assuming client_id is 1 for testing
        accueilClient.setVisible(true);
    }
}
