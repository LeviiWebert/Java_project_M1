package managementAppUI;

import basicObject.Client;
import DBTo.DBToclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AuthentificationClient extends JFrame {
    private JComboBox<Client> clientComboBox;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField telephoneField;
    private JTextField adresseField;
    private JTextField dateNaissanceField;
    private JButton submitButton;

    public AuthentificationClient() {
        setTitle("Authentification Client");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create the client selection panel
        JPanel clientSelectionPanel = new JPanel(new GridLayout(2, 1));
        clientComboBox = new JComboBox<>();
        List<Client> clients = DBToclient.getClients();
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }

        clientSelectionPanel.add(new JLabel("Sélectionnez un client existant:"));
        clientSelectionPanel.add(clientComboBox);

        // Create the new client form
        JPanel newClientPanel = new JPanel(new GridLayout(6, 2));
        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        telephoneField = new JTextField();
        adresseField = new JTextField();
        dateNaissanceField = new JTextField();

        newClientPanel.add(new JLabel("Nom:"));
        newClientPanel.add(nomField);
        newClientPanel.add(new JLabel("Prénom:"));
        newClientPanel.add(prenomField);
        newClientPanel.add(new JLabel("Email:"));
        newClientPanel.add(emailField);
        newClientPanel.add(new JLabel("Téléphone:"));
        newClientPanel.add(telephoneField);
        newClientPanel.add(new JLabel("Adresse:"));
        newClientPanel.add(adresseField);
        newClientPanel.add(new JLabel("Date de Naissance:"));
        newClientPanel.add(dateNaissanceField);

        // Create the submit button
        submitButton = new JButton("Soumettre");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        // Add components to the frame
        add(clientSelectionPanel, BorderLayout.NORTH);
        add(newClientPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
    }

    private void handleSubmit() {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        if (selectedClient != null) {
            // Handle existing client login
            JOptionPane.showMessageDialog(this, "Client existant sélectionné: " + selectedClient.getNom());
            // Redirection vers la classe Shop avec le client existant
            Shop shop = new Shop(selectedClient.getClientID());
            shop.setVisible(true);
            this.dispose();
        } else {
            // Handle new client creation
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String email = emailField.getText();
            String telephone = telephoneField.getText();
            String adresse = adresseField.getText();
            String dateNaissance = dateNaissanceField.getText();

            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || telephone.isEmpty() || adresse.isEmpty() || dateNaissance.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.");
            } else {
                Client newClient = new Client(dateNaissance, nom, prenom, email, telephone, DBToclient.getMaxClientID() + 1, adresse);
                JOptionPane.showMessageDialog(this, "Nouveau client créé: " + newClient.getNom());
                // Redirection vers la classe Shop avec le nouveau client
                AccueilClient accueilClient = new AccueilClient(newClient.getClientID());
                accueilClient.setVisible(true);
                this.dispose();
            }
        }
    }

    public static void main(String[] args) {
        AuthentificationClient accueilClient = new AuthentificationClient();
        accueilClient.setVisible(true);
    }
}
