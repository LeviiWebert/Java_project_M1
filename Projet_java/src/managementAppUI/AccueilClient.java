package managementAppUI;

import javax.swing.*;

import service.LoadingServiceUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccueilClient extends JFrame {
    private int client_id;

    public AccueilClient(int client_id) {
        this.client_id = client_id;

        setTitle("Accueil Client");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        // Create the title label
        JLabel titleLabel = new JLabel("Bienvenue chez VeloDauphine", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        // Create the shop button
        JButton shopButton = new JButton("Boutique");
        shopButton.setPreferredSize(new Dimension(150, 50));
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShop();
            }
        });

        // Create the my orders button
        JButton myOrdersButton = new JButton("Mes Commandes");
        myOrdersButton.setPreferredSize(new Dimension(150, 50));
        myOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openOrders();
            }
        });

        // Create the logout button
        JButton logoutButton = new JButton("Me déconnecter");
        logoutButton.setPreferredSize(new Dimension(150, 50));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.setOpaque(false); // Make panel background transparent
        buttonPanel.add(shopButton);
        buttonPanel.add(myOrdersButton);
        buttonPanel.add(logoutButton);

        // Add components to the frame
        add(buttonPanel, BorderLayout.CENTER);
    }

    // Open the shop window
    private void openShop() {
    	LoadingServiceUI loadingService = new LoadingServiceUI();
        loadingService.showLoadingDialog(this);
        SwingUtilities.invokeLater(() -> {
        	new Shop(client_id,loadingService).setVisible(true);
        });
        this.dispose();
    }

    // Open the orders window
    private void openOrders() {
        Orders orders = new Orders(client_id);
        orders.setVisible(true);
        this.dispose();
    }

    // Logout method
    private void logout() {
        // Implement logout functionality here
        JOptionPane.showMessageDialog(this, "Déconnecté.");
        this.dispose();
    }

    public static void main(String[] args) {
        // Create and show the Ac	²cueilClient
        AccueilClient accueilClient = new AccueilClient(1); // Assuming client_id is 1 for testing
        accueilClient.setVisible(true);
    }
}


