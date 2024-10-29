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
import java.net.MalformedURLException;
import java.net.URL;
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

    // Champs pour les détails du produit
    private JTextField produitDescriptionField;
    private JTextField produitPrixField;
    private JTextField produitMarqueField;
    private JTextField produitModeleField;
    private JTextField produitTypeField;
    private JTextField produitQuantiteStockField;
    private JTextField adresseImageField;

    private JButton updateClientButton;
    private JButton deleteClientButton;
    private JButton addProductButton;
    private JButton deleteProductButton;
    private JButton updateStockButton;

    public Admin() {
        setTitle("Espace Admin");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de gestion des clients
        JPanel clientPanel = new JPanel(new GridLayout(8, 2));
        clientPanel.setBackground(new Color(230, 230, 250));

        clientComboBox = new JComboBox<>();
        List<Client> clients = DBToclient.getClients();
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }
        clientPanel.add(new JLabel("Sélectionnez un client:"));
        clientPanel.add(clientComboBox);

        // Champs de texte pour les informations du client
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

        updateClientButton = new JButton("Mettre à jour le client");
        deleteClientButton = new JButton("Supprimer le client");
        updateClientButton.setBackground(new Color(173, 216, 230));
        deleteClientButton.setBackground(new Color(255, 160, 160));
        clientPanel.add(updateClientButton);
        clientPanel.add(deleteClientButton);

        JPanel produitPanel = new JPanel(new GridLayout(10, 2));
        produitPanel.setBackground(new Color(255, 250, 240));

        produitComboBox = new JComboBox<>();
        List<Produit> produits = DBToproduit.getproduit();
        for (Produit produit : produits) {
            produitComboBox.addItem(produit);
        }

        produitComboBox.setPreferredSize(new Dimension(300, 25));
        produitComboBox.setBackground(new Color(255, 255, 255));

        produitComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Produit) {
                    Produit produit = (Produit) value;
                    label.setText("Marque: " + produit.getMarque() + " | Modèle: " + produit.getModele() + " | Type: " + produit.getType());
                    label.setToolTipText("Marque: " + produit.getMarque() + " | Modèle: " + produit.getModele() + " | Type: " + produit.getType());
                }
                return label;
            }
        });

        produitPanel.add(new JLabel("Sélectionnez un produit:"));
        produitPanel.add(produitComboBox);

        produitMarqueField = new JTextField();
        produitModeleField = new JTextField();
        produitPrixField = new JTextField();
        produitTypeField = new JTextField();
        produitDescriptionField = new JTextField();
        produitQuantiteStockField = new JTextField();
        adresseImageField = new JTextField();

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
//        produitPanel.add(new JLabel("Adresse de l'image :"));
//        produitPanel.add(adresseImageField);

        addProductButton = new JButton("Ajouter le produit");
        deleteProductButton = new JButton("Supprimer le produit");
        updateStockButton = new JButton("Mettre à jour le stock");

        addProductButton.setBackground(new Color(144, 238, 144));
        deleteProductButton.setBackground(new Color(255, 160, 160));
        updateStockButton.setBackground(new Color(173, 216, 230));

        produitPanel.add(addProductButton);
        produitPanel.add(deleteProductButton);
        produitPanel.add(updateStockButton);

        add(clientPanel, BorderLayout.NORTH);
        add(produitPanel, BorderLayout.CENTER);

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

        updateStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	handleUpdateProduit();
            }
        });
    }

    private void handleUpdateClient() {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();
        
        if (selectedClient != null) {
            Client updatedClient = new Client();
            updatedClient.setClientID(selectedClient.getClientID());

            updatedClient.setNom(nomField.getText().isEmpty() ? selectedClient.getNom() : nomField.getText());
            updatedClient.setPrenom(prenomField.getText().isEmpty() ? selectedClient.getPrenom() : prenomField.getText());
            updatedClient.setEmail(emailField.getText().isEmpty() ? selectedClient.getEmail() : emailField.getText());
            updatedClient.setTelephone(telephoneField.getText().isEmpty() ? selectedClient.getTelephone() : telephoneField.getText());
            updatedClient.setAdresse(adresseField.getText().isEmpty() ? selectedClient.getAdresse() : adresseField.getText());
            updatedClient.setmdp(selectedClient.getmdp());

            if (!isValidDateFormat(dateNaissanceField.getText(), "yyyy-MM-dd")) {
                JOptionPane.showMessageDialog(this, 
                    "Le format de la date de naissance est invalide. Veuillez utiliser le format yyyy-MM-dd.",
                    "Erreur de format de date",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            updatedClient.setDate_naissance(dateNaissanceField.getText());

            ClientToDB.updateClient(updatedClient);
            JOptionPane.showMessageDialog(this, "Client mis à jour avec succès.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
        }
    }

    private boolean isValidDateFormat(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
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
        String type = produitTypeField.getText();
        String description = produitDescriptionField.getText();
        String quantiteText = produitQuantiteStockField.getText();
        String adr_img = adresseImageField.getText();

        try {
            double prix = Double.parseDouble(prixText);
            int quantite = Integer.parseInt(quantiteText);
            ImageIcon image;
            try {
            	image = new ImageIcon(new URL(adr_img));
				image.setDescription(adr_img);
				Produit newProduit = new Produit(marque, modele, prix, type, description, quantite, image);
	            ProduitToDB.addProduit(newProduit);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
            
            JOptionPane.showMessageDialog(this, "Produit ajouté avec succès.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour le prix et la quantité.");
        }
    }

    private void handleDeleteProduct() {
        Produit selectedProduit = (Produit) produitComboBox.getSelectedItem();
        if (selectedProduit != null) {
            ProduitToDB.deleteProduit(selectedProduit.getId());
            JOptionPane.showMessageDialog(this, "Produit supprimé avec succès.");
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit.");
        }
    }

    private void handleUpdateProduit() {
        Produit selectedProduit = (Produit) produitComboBox.getSelectedItem();
        String quantiteText = produitQuantiteStockField.getText();
        String adr_img = adresseImageField.getText();

        if (selectedProduit != null && !quantiteText.isEmpty()) {
            try {
                int nouvelleQuantite = Integer.parseInt(quantiteText);
                selectedProduit.setQuantite_stock(nouvelleQuantite);
                selectedProduit.setImage(null);
                ProduitToDB.updateProduit(selectedProduit);
                JOptionPane.showMessageDialog(this, "Stock mis à jour avec succès.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une valeur valide pour la quantité.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit et entrer une quantité.");
        }
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Admin adminInterface = new Admin();
                adminInterface.setVisible(true);
            }
        });
    }
}
