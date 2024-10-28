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

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private JTextField produitDescriptionField;
    private JTextField produitPrixField;
    private JTextField produitMarqueField;
    private JTextField produitModeleField;
    private JTextField produitTypeField;
    private JTextField produitQuantiteStockField;

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
        JPanel produitPanel = new JPanel(new GridLayout(8, 2)); // Augmenter les lignes à 8

        // Créer le JComboBox pour sélectionner le produit
        produitComboBox = new JComboBox<>();

        // Charger les produits et ajouter chaque produit au JComboBox
        List<Produit> produits = DBToproduit.getproduit();
        for (Produit produit : produits) {
            produitComboBox.addItem(produit);
        }

        // Fixer une largeur minimum pour afficher le texte complet des produits
        produitComboBox.setPreferredSize(new Dimension(300, 25)); // Ajuster la largeur à 200

        // Ajouter un renderer personnalisé pour afficher le nom du produit et un tooltip
        produitComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value instanceof Produit) {
                    Produit produit = (Produit) value;
                    label.setText("Marque: "+produit.getMarque()+" .Modele: "+produit.getModele()+" .Type: "+produit.getType()); // Affiche uniquement le nom du produit
                    label.setToolTipText("Marque: "+produit.getMarque()+" .Modele: "+produit.getModele()+" .Type: "+produit.getType()); // Tooltip avec le texte complet
                }
                
                return label;
            }
        });

        // Ajouter les composants au JPanel
        produitPanel.add(new JLabel("Sélectionnez un produit:"));
        produitPanel.add(produitComboBox);



	     // Champs de texte pour les nouveaux attributs
	     produitMarqueField = new JTextField();
	     produitModeleField = new JTextField();
	     produitPrixField = new JTextField();
	     produitTypeField = new JTextField();
	     produitDescriptionField = new JTextField();
	     produitQuantiteStockField = new JTextField();
	
	     // Ajout des labels et champs dans le panel
	     produitPanel.add(new JLabel("Marque :"));
	     produitPanel.add(produitMarqueField);
	     produitPanel.add(new JLabel("Modèle :"));
	     produitPanel.add(produitModeleField);
	     produitPanel.add(new JLabel("Prix :"));
	     produitPanel.add(produitPrixField);
	     produitPanel.add(new JLabel("Type :"));
	     produitPanel.add(produitTypeField);
	     produitPanel.add(new JLabel("Description :"));
	     produitPanel.add(produitDescriptionField);
	     produitPanel.add(new JLabel("Quantité en stock :"));
	     produitPanel.add(produitQuantiteStockField);
	
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

    
    private void handleUpdateClient() {
        // Récupérer le client sélectionné dans la ComboBox
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        
        if (selectedClient != null) {
            Client updatedClient = new Client();
            updatedClient.setClientID(selectedClient.getClientID());

            updatedClient.setNom(nomField.getText().isEmpty() ? selectedClient.getNom() : nomField.getText());
            updatedClient.setPrenom(prenomField.getText().isEmpty() ? selectedClient.getPrenom() : prenomField.getText());
            updatedClient.setEmail(emailField.getText().isEmpty() ? selectedClient.getEmail() : emailField.getText());
            updatedClient.setTelephone(telephoneField.getText().isEmpty() ? selectedClient.getTelephone() : telephoneField.getText());
            updatedClient.setAdresse(adresseField.getText().isEmpty() ? selectedClient.getAdresse() : adresseField.getText());

            // Vérifier si la date de naissance est au bon format "yyyy-MM-dd"
            if (!isValidDateFormat(dateNaissanceField.getText(), "yyyy-MM-dd")) {
                JOptionPane.showMessageDialog(this, 
                    "Le format de la date de naissance est invalide. Veuillez utiliser le format yyyy-MM-dd.",
                    "Erreur de format de date",
                    JOptionPane.ERROR_MESSAGE);
                return; // Sortir de la méthode pour laisser l'utilisateur corriger la date
            }

            updatedClient.setDate_naissance(dateNaissanceField.getText());

            // Appel de la mise à jour dans la base de données
            ClientToDB.updateClient(updatedClient);
            JOptionPane.showMessageDialog(this, "Client mis à jour avec succès.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
        }
    }

    // Méthode utilitaire pour vérifier le format de la date
    private boolean isValidDateFormat(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false); // Désactiver l'analyse de dates partielles
        try {
            sdf.parse(date); // Essayer de parser la date
            return true;     // Si le parsing réussit, le format est correct
        } catch (ParseException e) {
            return false;    // Si une exception est levée, le format est incorrect
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
    	
        String marque = produitMarqueField.getText();
        String modele = produitModeleField.getText();
        String prixText = produitPrixField.getText();
        String type = produitDescriptionField.getText();
        String description = produitDescriptionField.getText();
        String quantiteText = produitQuantiteStockField.getText();

        if (marque.isEmpty() || description.isEmpty() || prixText.isEmpty() || quantiteText.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Tous les champs du produit doivent être remplis.");
            return;
        }

        
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

