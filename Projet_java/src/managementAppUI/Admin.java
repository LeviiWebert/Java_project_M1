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
import java.util.Comparator;
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

	    // Ajouter une première ligne explicative
	    clientComboBox.addItem(null);
	        
	    // Charger les clients depuis la base de données et les ajouter à la JComboBox
	    List<Client> clients = DBToclient.getClients();
	    
	    //trier les client par le nom
	    clients.sort(
	    	    Comparator.comparing(
	    	        Client::getNom, 
	    	        Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER)
	    	    )
	    	);
	    
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
        produitComboBox.addItem(null);
        
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

        produitPanel.add(new JLabel("Sélectionnez un produit pour une mise a jour:"));
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
        produitPanel.add(new JLabel("Adresse de l'image :"));
        produitPanel.add(adresseImageField);

        addProductButton = new JButton("Ajouter un produit");
        deleteProductButton = new JButton("Supprimer le produit");
        updateStockButton = new JButton("Mettre à jour le Produit sélectionné ");
        
        // Ajouter le bouton "Vider les champs"
        JButton clearFieldsButton = new JButton("Vider les champs");

        addProductButton.setBackground(new Color(144, 238, 144));
        deleteProductButton.setBackground(new Color(255, 160, 160));
        updateStockButton.setBackground(new Color(173, 216, 230));
        clearFieldsButton.setBackground(new Color(173, 216, 230));

        produitPanel.add(addProductButton);
        produitPanel.add(deleteProductButton);
        produitPanel.add(updateStockButton);
        produitPanel.add(clearFieldsButton);


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
        
     // Ajouter un écouteur pour le changement de sélection dans la JComboBox
        clientComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client selectedClient = (Client) clientComboBox.getSelectedItem();
                
                // Vérifiez si un client a bien été sélectionné
                if (selectedClient != null) {
                    // Remplir les champs avec les informations du client sélectionné
                    nomField.setText(selectedClient.getNom());
                    prenomField.setText(selectedClient.getPrenom());
                    emailField.setText(selectedClient.getEmail());
                    telephoneField.setText(selectedClient.getTelephone());
                    adresseField.setText(selectedClient.getAdresse());
                    dateNaissanceField.setText(selectedClient.getDate_naissance());
                } else {
                    // Si aucun client n'est sélectionné, vider les champs
                    nomField.setText("");
                    prenomField.setText("");
                    emailField.setText("");
                    telephoneField.setText("");
                    adresseField.setText("");
                    dateNaissanceField.setText("");
                }
            }
        });
        

        // Ajouter un écouteur pour le changement de sélection dans la JComboBox
        produitComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Produit selectedProduit = (Produit) produitComboBox.getSelectedItem();
                
                // Vérifiez si un produit a bien été sélectionné
                if (selectedProduit != null) {
                    // Remplir les champs avec les informations du produit sélectionné
                    produitMarqueField.setText(selectedProduit.getMarque());
                    produitModeleField.setText(selectedProduit.getModele());
                    produitPrixField.setText(String.valueOf(selectedProduit.getPrix()));
                    produitTypeField.setText(selectedProduit.getType());
                    produitDescriptionField.setText(selectedProduit.getDescription());
                    produitQuantiteStockField.setText(String.valueOf(selectedProduit.getQuantite_stock()));
                } else {
                    // Si aucun produit n'est sélectionné, vider les champs
                    produitMarqueField.setText("");
                    produitModeleField.setText("");
                    produitPrixField.setText("");
                    produitTypeField.setText("");
                    produitDescriptionField.setText("");
                    produitQuantiteStockField.setText("");
                    adresseImageField.setText("");
                }
            }
        });
        
        // Ajouter un écouteur d'événements pour le bouton "Vider les champs"
        clearFieldsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearProductFields();  // Appeler la méthode pour vider les champs
            }
        });

   
    }
    
    // Méthode pour vider les champs
    private void clearProductFields() {
        produitMarqueField.setText("");
        produitModeleField.setText("");
        produitPrixField.setText("");
        produitTypeField.setText("");
        produitDescriptionField.setText("");
        produitQuantiteStockField.setText("");
        adresseImageField.setText("");
    }
    
    

    private void handleUpdateClient() {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();

        if (selectedClient != null) {
            // Vérification si tous les champs sont vides
            if (nomField.getText().isEmpty() &&
                prenomField.getText().isEmpty() &&
                emailField.getText().isEmpty() &&
                telephoneField.getText().isEmpty() &&
                adresseField.getText().isEmpty() &&
                dateNaissanceField.getText().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, 
                    "Tous les champs sont vides. Aucune mise à jour ne sera effectuée.", 
                    "Information", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Vérification du numéro de téléphone
            String telephone = telephoneField.getText();
            if (!telephone.isEmpty() && !telephone.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, 
                    "Le numéro de téléphone ne doit contenir que des chiffres.", 
                    "Erreur de validation", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Création de l'objet client mis à jour
            Client updatedClient = new Client();
            updatedClient.setClientID(selectedClient.getClientID());

            updatedClient.setNom(nomField.getText().isEmpty() ? selectedClient.getNom() : nomField.getText());
            updatedClient.setPrenom(prenomField.getText().isEmpty() ? selectedClient.getPrenom() : prenomField.getText());
            updatedClient.setEmail(emailField.getText().isEmpty() ? selectedClient.getEmail() : emailField.getText());
            updatedClient.setTelephone(telephoneField.getText().isEmpty() ? selectedClient.getTelephone() : telephoneField.getText());
            updatedClient.setAdresse(adresseField.getText().isEmpty() ? selectedClient.getAdresse() : adresseField.getText());
            updatedClient.setmdp(selectedClient.getmdp()); // Si le mot de passe n'est pas modifié

            // Validation de la date de naissance
            if (!dateNaissanceField.getText().isEmpty()) {
                if (!isValidDateFormat(dateNaissanceField.getText(), "yyyy-MM-dd")) {
                    JOptionPane.showMessageDialog(this, 
                        "Le format de la date de naissance est invalide. Veuillez utiliser le format aaaa-MM-jj.",
                        "Erreur de format de date",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                updatedClient.setDate_naissance(dateNaissanceField.getText());
            } else {
                updatedClient.setDate_naissance(selectedClient.getDate_naissance()); // Si la date n'est pas modifiée
            }

            try {
                // Mise à jour en base de données
                ClientToDB.updateClient(updatedClient);

                // Message de confirmation
                JOptionPane.showMessageDialog(this, "Client mis à jour avec succès.");

                // Rafraîchir la JComboBox après mise à jour
                refreshClientComboBox();

                // Réinitialisation des champs
                nomField.setText("");
                prenomField.setText("");
                emailField.setText("");
                telephoneField.setText("");
                adresseField.setText("");
                dateNaissanceField.setText("");
                clientComboBox.setSelectedIndex(-1);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du client : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
        }
    }


    private void handleDeleteClient() {
        Client selectedClient = (Client) clientComboBox.getSelectedItem();

        if (selectedClient != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Êtes-vous sûr de vouloir supprimer ce client ?", 
                "Confirmation de suppression", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Suppression du client dans la base de données
                ClientToDB.deleteClient(selectedClient.getClientID());

                // Message de confirmation
                JOptionPane.showMessageDialog(this, "Client supprimé avec succès.");

                // Rafraîchir la liste déroulante
                refreshClientComboBox();

                // Réinitialiser la sélection
                clientComboBox.setSelectedIndex(-1);
            }
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


    
    private void refreshClientComboBox() {
        // Récupérer la liste des clients depuis la base de données
        List<Client> clients = ClientToDB.getAllClients();

        // Trier la liste des clients par nom de famille de manière alphabétique
        clients.sort(Comparator.comparing(Client::getNom, String.CASE_INSENSITIVE_ORDER));

        // Vider la JComboBox existante
        clientComboBox.removeAllItems();

        // Ajouter un élément vide pour permettre une sélection
        clientComboBox.addItem(null);

        // Ajouter les clients triés à la JComboBox
        for (Client client : clients) {
            clientComboBox.addItem(client);
        }
    }


    private void handleAddProduct() {
        // Récupération des valeurs des champs
        String marque = produitMarqueField.getText();
        String modele = produitModeleField.getText();
        String prixText = produitPrixField.getText();
        String type = produitTypeField.getText();
        String description = produitDescriptionField.getText();
        String quantiteText = produitQuantiteStockField.getText();
        String adr_img = adresseImageField.getText();

        // Vérification si un champ est vide
        if (marque.isEmpty() || modele.isEmpty() || prixText.isEmpty() || type.isEmpty() ||
            description.isEmpty() || quantiteText.isEmpty() || adr_img.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Conversion des valeurs numériques
            double prix = Double.parseDouble(prixText);
            int quantite = Integer.parseInt(quantiteText);

            // Vérification si le produit existe déjà
            if (DBToproduit.isProduitExists(marque, modele)) {
                JOptionPane.showMessageDialog(this, "Ce produit existe déjà dans la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validation de l'adresse de l'image
            try {
                ImageIcon image = new ImageIcon(new URL(adr_img));
                image.setDescription(adr_img);

                // Création du nouveau produit
                Produit newProduit = new Produit(marque, modele, prix, type, description, quantite, image);

                // Ajout à la base de données
                ProduitToDB.addProduit(newProduit);

                // Affichage du message de succès
                JOptionPane.showMessageDialog(this, "Produit ajouté avec succès.");

                // Réinitialisation des champs
                produitMarqueField.setText("");
                produitModeleField.setText("");
                produitPrixField.setText("");
                produitTypeField.setText("");
                produitDescriptionField.setText("");
                produitQuantiteStockField.setText("");
                adresseImageField.setText("");

            } catch (MalformedURLException e) {
                JOptionPane.showMessageDialog(this, "L'adresse de l'image est invalide. Veuillez entrer une URL valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour le prix et la quantité.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void handleDeleteProduct() {
        // Récupérer le produit sélectionné dans la JComboBox
        Produit selectedProduit = (Produit) produitComboBox.getSelectedItem();

        if (selectedProduit != null) {
            // Demander une confirmation à l'utilisateur
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Êtes-vous sûr de vouloir supprimer ce produit ?", 
                "Confirmation de suppression", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Suppression du produit dans la base de données
                ProduitToDB.deleteProduit(selectedProduit.getId());

                // Message de confirmation
                JOptionPane.showMessageDialog(this, "Produit supprimé avec succès.");

                // Rafraîchir la liste déroulante des produits
                refreshProduitComboBox();

                // Réinitialiser la sélection dans la JComboBox
                produitComboBox.setSelectedIndex(-1);
            }
        } else {
            // Si aucun produit n'est sélectionné, afficher un message d'erreur
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit.");
        }
    }




    private void handleUpdateProduit() {
        // Récupérer le produit sélectionné
        Produit selectedProduit = (Produit) produitComboBox.getSelectedItem();
        
        // Récupérer les valeurs des champs
        String quantiteText = produitQuantiteStockField.getText();
        String prixText = produitPrixField.getText();
        String description = produitDescriptionField.getText();
        String adr_img = adresseImageField.getText();

        // Vérifier que le produit est sélectionné et que la quantité n'est pas vide
        if (selectedProduit != null && !quantiteText.isEmpty() && !prixText.isEmpty() && !description.isEmpty() && !adr_img.isEmpty()) {
            try {
                // Conversion des valeurs numériques
                int nouvelleQuantite = Integer.parseInt(quantiteText);
                double nouveauPrix = Double.parseDouble(prixText);

                // Mettre à jour les informations du produit
                selectedProduit.setQuantite_stock(nouvelleQuantite);
                selectedProduit.setPrix(nouveauPrix);
                selectedProduit.setDescription(description);
                
                // Vérification de l'adresse de l'image (optionnelle, mais peut être valide)
                try {
                    ImageIcon image = new ImageIcon(new URL(adr_img));
                    image.setDescription(adr_img);
                    selectedProduit.setImage(image);
                } catch (MalformedURLException e) {
                    // Si l'adresse de l'image est invalide, afficher un message
                    JOptionPane.showMessageDialog(this, "L'adresse de l'image est invalide. Veuillez entrer une URL valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Mettre à jour le produit dans la base de données
                ProduitToDB.updateProduit(selectedProduit);

                // Afficher un message de confirmation
                JOptionPane.showMessageDialog(this, "Produit mis à jour avec succès.");

                // Rafraîchir la liste déroulante des produits
                refreshProduitComboBox();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides pour la quantité et le prix.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Si un champ est vide ou qu'aucun produit n'est sélectionné
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit et remplir tous les champs nécessaires.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    
    
    
    // Méthode pour rafraîchir la JComboBox des produits
    private void refreshProduitComboBox() {
        // Effacer les éléments existants dans la JComboBox
        produitComboBox.removeAllItems();

        // Récupérer la liste des produits mis à jour depuis la base de données
        List<Produit> produits = DBToproduit.getproduit();

        // Ajouter les produits à la JComboBox
        for (Produit produit : produits) {
            produitComboBox.addItem(produit);
        }
    }

   // Variable globale pour le splash screen
    private static JWindow splashScreen;

    private static void showSplashScreen() {
        // Créer la fenêtre de chargement (splash screen)
        splashScreen = new JWindow();
        
        // Définir la taille de la fenêtre
        splashScreen.setSize(300, 100);
        
        // Positionner la fenêtre au centre de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - splashScreen.getWidth()) / 2;
        int y = (int) (screenSize.getHeight() - splashScreen.getHeight()) / 2;
        splashScreen.setLocation(x, y);
        
        // Ajouter un label avec le texte "Veuillez patienter..."
        JLabel label = new JLabel("Veuillez patienter...", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(Color.BLACK);
        
        // Ajouter le label à la fenêtre
        splashScreen.getContentPane().add(label);
        
        // Rendre la fenêtre de chargement visible
        splashScreen.setVisible(true);
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Créer et afficher le SplashScreen
                showSplashScreen();
                
                // Démarrer une tâche en arrière-plan pour attendre un peu
                Timer timer = new Timer(3000, e -> {
                    // Après 3 secondes, cacher le SplashScreen et afficher la fenêtre principale
                	   Admin adminInterface = new Admin();
                       adminInterface.setVisible(true);
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}
