package managementAppUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import DBTo.DBToclient;
import DBTo.DBTocommande;
import basicObject.Client;
import basicObject.Commande;

public class Orders extends JFrame {
    private int client_id;
    private DefaultListModel<String> listModel;
    private JList<String> orderList;

    public Orders(int client_id) {
        this.client_id = client_id;
        Client client = DBToclient.getClientByID(client_id);

        // Configurations générales de la fenêtre
        setTitle("Mes Commandes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // Fond bleu clair

        // Titre en haut
        JLabel titleLabel = new JLabel("Mes Commandes", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112)); // Bleu foncé
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Marges
        add(titleLabel, BorderLayout.NORTH);

        // Liste des commandes
        listModel = new DefaultListModel<>();
        List<Commande> commandes = DBToclient.getCommandesByClientID(client_id);
        for (Commande commande : commandes) {
            listModel.addElement("Commande ID: " + commande.getId() + " - Date: " + commande.getDateCommande());
        }

        orderList = new JList<>(listModel);
        orderList.setFont(new Font("Arial", Font.PLAIN, 16));
        orderList.setBackground(new Color(255, 255, 255)); // Fond blanc
        orderList.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 112), 2)); // Bordure bleu foncé
        JScrollPane scrollPane = new JScrollPane(orderList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espacement

        // Boutons
        JButton viewOrderDetailsButton = new JButton("Détails de la commande");
        JButton returnToHomeButton = new JButton("Retour à l'accueil");

        styleButton(viewOrderDetailsButton);
        styleButton(returnToHomeButton);

        viewOrderDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrderDetails();
            }
        });

        returnToHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToHome();
            }
        });

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255)); // Fond bleu clair
        buttonPanel.add(viewOrderDetailsButton);
        buttonPanel.add(returnToHomeButton);

        // Ajouter les composants à la fenêtre
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Méthode pour styliser les boutons
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180)); // Bleu acier
        button.setForeground(Color.WHITE); // Texte blanc
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Marges internes
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Détails de la commande
    private void viewOrderDetails() {
        String selectedOrder = orderList.getSelectedValue();
        if (selectedOrder == null) {
            JOptionPane.showMessageDialog(this, "Sélectionner une commande.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int orderId = Integer.parseInt(selectedOrder.split(" ")[2]);
        Commande commande = DBTocommande.getCommandeById(orderId);
        if (commande == null) {
            JOptionPane.showMessageDialog(this, "Commande non trouvée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        OrderDetails orderDetails = new OrderDetails(commande);
        orderDetails.setVisible(true);
    }

    // Retour à l'accueil
    private void returnToHome() {
        AccueilClient accueilClient = new AccueilClient(client_id);
        accueilClient.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        Orders orders = new Orders(1); // Exemple de test avec client_id = 1
        orders.setVisible(true);
    }
}
