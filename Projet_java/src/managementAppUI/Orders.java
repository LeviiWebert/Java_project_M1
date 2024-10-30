package managementAppUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import DBTo.DBToclient;
import DBTo.DBTocommande;
import basicObject.Client;
import basicObject.Commande;

class Orders extends JFrame {
    private int client_id;
    private DefaultListModel<String> listModel;
    private JList<String> orderList;

    public Orders(int client_id) {
        this.client_id = client_id;
        Client client = (Client) DBToclient.getClientByID(client_id);

        setTitle("Mes Commandes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the list model and populate it with order IDs
        listModel = new DefaultListModel<>();
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

     // Create the return to home button
        JButton returnToHomeButton = new JButton("Retour à l'accueil");
        returnToHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHome();
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.add(viewOrderDetailsButton);
        buttonPanel.add(returnToHomeButton);

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
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

        OrderDetails orderDetails = new OrderDetails(commande);
        orderDetails.setVisible(true);
    }

    public static void main(String[] args) {
        Orders orders = new Orders(1); // Assuming client_id is 1 for testing
        orders.setVisible(true);
    }
    
    // Method to return to the home screen
    private void returnToHome() {
        AccueilClient accueilClient = new AccueilClient(client_id);
        accueilClient.setVisible(true);
        this.dispose();
    }

}