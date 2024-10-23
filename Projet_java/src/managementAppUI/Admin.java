package managementAppUI;


import basicObject.Client;
import DBTo.DBToclient;
import DBTo.DBToproduit;
import basicObject.Produit;
import toDB.ClientToDB;
import toDB.ProduitToDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Admin extends JFrame {

   
	private JComboBox<Client> clientComboBox;
    private JComboBox<Produit> produitComboBox;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField telephoneField;
    private JTextField adresseField;
    private JTextField dateNaissanceField;
    private JButton updateClientButton;
    private JButton deleteClientButton;
    private JButton addProductButton;
    private JButton deleteProductButton;
    private JTextField produitNomField;
    private JTextField produitDescriptionField;
    private JTextField produitPrixField;
    private JTextField produitMarqueField;
    private JTextField produitModeleField;
    private JTextField produitTypeField;

    
    private JTextField produitQuantiteField;
    private JTextField produitCategorieField;

    public Admin() {
        setTitle("Espace Admin");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de gestion des clients
        JPanel clientPanel = new JPanel(new GridLayout(8, 2));
        clientComboBox = new JComboBox<>();
        List<Client> clients = DBToclient.getClients();
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }
        clientPanel.add(new JLabel("Sélectionnez un client:"));
        clientPanel.add(clientComboBox);

        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        telephoneField = new JTextField();
        adresseField = new JTextField();
        dateNaissanceField = new JTextField();

        clientPanel.add(new JLabel("Nom:"));
        clientPanel.add(nomField);
        clientPanel.add(new JLabel("Prénom:"));
        clientPanel.add(prenomField);
        clientPanel.add(new JLabel("Email:"));
        clientPanel.add(emailField);
        clientPanel.add(new JLabel("Téléphone:"));
        clientPanel.add(telephoneField);
        clientPanel.add(new JLabel("Adresse:"));
        clientPanel.add(adresseField);
        clientPanel.add(new JLabel("Date de Naissance:"));
        clientPanel.add(dateNaissanceField);

        // Boutons pour mise à jour et suppression de client
        updateClientButton = new JButton("Mettre à jour le client");
        deleteClientButton = new JButton("Supprimer le client");
        clientPanel.add(updateClientButton);
        clientPanel.add(deleteClientButton);

        // Panel de gestion des produits
        JPanel produitPanel = new JPanel(new GridLayout(7, 2));
        produitComboBox = new JComboBox<>();
        List<Produit> produits = DBToproduit.getproduit();
        for (Produit produit : produits) {
            produitComboBox.addItem(produit);
        }

        produitNomField = new JTextField();
        produitDescriptionField = new JTextField();
        produitPrixField = new JTextField();
        produitQuantiteField = new JTextField();
        produitCategorieField = new JTextField();

        produitPanel.add(new JLabel("Nom du produit:"));
        produitPanel.add(produitNomField);
        produitPanel.add(new JLabel("Description:"));
        produitPanel.add(produitDescriptionField);
        produitPanel.add(new JLabel("Prix:"));
        produitPanel.add(produitPrixField);
        produitPanel.add(new JLabel("Quantité:"));
        produitPanel.add(produitQuantiteField);
        produitPanel.add(new JLabel("Catégorie:"));
        produitPanel.add(produitCategorieField);

        // Boutons pour ajouter et supprimer des produits
        addProductButton = new JButton("Ajouter le produit");
        deleteProductButton = new JButton("Supprimer le produit");
        produitPanel.add(addProductButton);
        produitPanel.add(deleteProductButton);

        // Ajout des panels dans la frame principale
        add(clientPanel, BorderLayout.NORTH);
        add(produitPanel, BorderLayout.CENTER);

        // Gestion des événements
        updateClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateClient();
            }
        });

        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteClient();
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddProduct();
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteProduct();
            }
        });
    }

//    private void handleUpdateClient() {
//    	
//        Client selectedClient = (Client) clientComboBox.getSelectedItem();
//        
//        if (selectedClient != null) {
//            // Mise à jour des informations du client
//            selectedClient.setNom(nomField.getText());
//            selectedClient.setPrenom(prenomField.getText());
//            selectedClient.setEmail(emailField.getText());
//            selectedClient.setTelephone(telephoneField.getText());
//            selectedClient.setAdresse(adresseField.getText());
//            selectedClient.setDate_naissance(dateNaissanceField.getText());
//
//            ClientToDB.updateClient(selectedClient);
//            JOptionPane.showMessageDialog(this, "Client mis à jour avec succès.");
//        } else {
//            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
//        }
//    }
    
    
    private void handleUpdateClient() {
        // Récupérer le client sélectionné dans la ComboBox
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        
        if (selectedClient != null) {
            // Créer un nouvel objet Client avec les informations mises à jour
            Client updatedClient = new Client();
            updatedClient.setClientID(selectedClient.getClientID()); // On garde le même ID que le client sélectionné
            updatedClient.setNom(nomField.getText());
            updatedClient.setPrenom(prenomField.getText());
            updatedClient.setEmail(emailField.getText());
            updatedClient.setTelephone(telephoneField.getText());
            updatedClient.setAdresse(adresseField.getText());
            updatedClient.setDate_naissance(dateNaissanceField.getText());

            System.out.println(selectedClient.getClientID());

            System.out.println(updatedClient.toString());
            System.out.println("sallllllut");
            
            // Appeler la méthode pour mettre à jour le client dans la base de données
            ClientToDB.updateClient(updatedClient);
            
            JOptionPane.showMessageDialog(this, "Client mis à jour avec succès.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
        }
    }


    private void handleDeleteClient() {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        if (selectedClient != null) {
            ClientToDB.deleteClient(selectedClient.getClientID());
            JOptionPane.showMessageDialog(this, "Client supprimé avec succès.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
        }
    }

    private void handleAddProduct() {
    	
        String nom = produitNomField.getText();
        String marque = produitMarqueField.getText();
        String modele = produitModeleField.getText();
        String description = produitDescriptionField.getText();
        String type = produitDescriptionField.getText();
        String prixText = produitPrixField.getText();
        String quantiteText = produitQuantiteField.getText();
        String categorie = produitCategorieField.getText();

        if (nom.isEmpty() || description.isEmpty() || prixText.isEmpty() || quantiteText.isEmpty() || categorie.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs du produit doivent être remplis.");
            return;
        }
    //    public Produit(int id, String marque, String modele, double prix, String type,String description,int quantite_stock){

        try {
            double prix = Double.parseDouble(prixText);
            int quantite = Integer.parseInt(quantiteText);
            Produit newProduit = new Produit(marque,modele,prix,type,description,quantite);
            ProduitToDB.addProduit(newProduit);
            JOptionPane.showMessageDialog(this, "Produit ajouté avec succès.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour le prix et la quantité.");
        }
    }

    private void handleDeleteProduct() {
        Produit selectedProduit = (Produit) produitComboBox.getSelectedItem();
        if (selectedProduit != null) {
            ProduitToDB.deleteProduit(selectedProduit.getId());// a revoir
            JOptionPane.showMessageDialog(this, "Produit supprimé avec succès.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit.");
        }
    }

    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.setVisible(true);
    }
}

