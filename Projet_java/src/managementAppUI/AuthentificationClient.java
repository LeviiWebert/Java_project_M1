package managementAppUI;

import basicObject.Client;
import service.LoadingServiceUI;
import toDB.ClientToDB;
import DBTo.DBToclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AuthentificationClient extends JFrame {

    // Déclaration des composants d'interface utilisateur
    private JComboBox<Client> clientComboBox;
    private JPasswordField passwordField;
    private JTextField nomField, prenomField, emailField, telephoneField, adresseField, dateNaissanceField;
    private JPasswordField newPasswordField;
    private JButton submitButton, existingClientButton, newClientButton;
    private JPanel clientSelectionPanel, newClientPanel;

    // Palette de couleurs pour un design moderne
    private final Color bgColor = new Color(245, 245, 245); // Arrière-plan gris clair
    private final Color accentColor = new Color(52, 152, 219); // Bleu clair pour les boutons et accents
    private final Color textColor = new Color(44, 62, 80); // Couleur du texte pour une bonne lisibilité
    private final Font font = new Font("SansSerif", Font.PLAIN, 14); // Police uniforme

    public AuthentificationClient() {
        setTitle("Authentification Client"); // Titre de la fenêtre
        setSize(700, 500); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportement de fermeture
        setLocationRelativeTo(null); // Centre la fenêtre
        setLayout(new BorderLayout()); // Gestionnaire de disposition principal

        // Initialisation des éléments de l'interface utilisateur
        initUI();
    }

    // Méthode d'initialisation de l'interface utilisateur
    private void initUI() {
        // Boutons pour basculer entre les modes "Connexion" et "Inscription"
        existingClientButton = createStyledButton("Déjà inscrit ?");
        newClientButton = createStyledButton("Vous n’êtes pas encore inscrit ?");

        // Panneau en haut pour les boutons de bascule
        JPanel choicePanel = new JPanel();
        choicePanel.setBackground(bgColor); // Couleur de fond
        choicePanel.add(existingClientButton);
        choicePanel.add(newClientButton);

        // Panneau pour la sélection d'un client existant
        clientSelectionPanel = createClientSelectionPanel();

        // Panneau pour le formulaire de création d'un nouveau client
        newClientPanel = createNewClientPanel();

        // Bouton de soumission
        submitButton = createStyledButton("Soumettre");
        submitButton.addActionListener(e -> handleSubmit()); // Action de soumission

        // Ajout des panneaux principaux au frame
        add(choicePanel, BorderLayout.NORTH);
        add(clientSelectionPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        // Le panneau d'inscription est masqué par défaut
        newClientPanel.setVisible(false);

        // Actions pour basculer entre les panneaux
        existingClientButton.addActionListener(e -> showExistingClientPanel());
        newClientButton.addActionListener(e -> showNewClientPanel());
    }

    // Crée et configure le panneau pour la sélection d'un client existant
    private JPanel createClientSelectionPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(bgColor);

        // ComboBox pour sélectionner un client existant
        clientComboBox = new JComboBox<>();
        List<Client> clients = DBToclient.getClients();
        clients.forEach(clientComboBox::addItem); // Ajout des clients à la liste déroulante

        // Champ de saisie pour le mot de passe
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(1000, 30)); // Dimension du champ

        // Ajout des composants au panneau
        panel.add(createStyledLabel("Sélectionnez un client existant:"));
        panel.add(clientComboBox);
        panel.add(createStyledLabel("Mot de passe:"));
        panel.add(passwordField);

        return panel;
    }

    // Crée et configure le panneau pour l'inscription d'un nouveau client
    private JPanel createNewClientPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBackground(bgColor);

        // Champs de saisie pour les informations du nouveau client
        nomField = new JTextField();
        prenomField = new JTextField();
        emailField = new JTextField();
        telephoneField = new JTextField();
        adresseField = new JTextField();
        dateNaissanceField = new JTextField();
        newPasswordField = new JPasswordField();

        // Ajout des champs au panneau avec des labels stylisés
        addFieldToPanel(panel, "Nom:", nomField);
        addFieldToPanel(panel, "Prénom:", prenomField);
        addFieldToPanel(panel, "Email:", emailField);
        addFieldToPanel(panel, "Téléphone:", telephoneField);
        addFieldToPanel(panel, "Adresse:", adresseField);
        addFieldToPanel(panel, "Date de Naissance:", dateNaissanceField);
        addFieldToPanel(panel, "Mot de passe:", newPasswordField);

        return panel;
    }

    // Méthode utilitaire pour ajouter des champs au panneau
    private void addFieldToPanel(JPanel panel, String label, JTextField textField) {
        panel.add(createStyledLabel(label));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        )); // Bordure stylisée pour les champs de saisie
        panel.add(textField);
    }

    // Crée un JLabel stylisé
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(font);
        return label;
    }

    // Crée un JButton stylisé
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Espacement pour un meilleur rendu visuel
        return button;
    }

    // Affiche le panneau de connexion pour client existant
    private void showExistingClientPanel() {
        newClientPanel.setVisible(false); // Masquer le panneau d'inscription
        add(clientSelectionPanel, BorderLayout.CENTER);
        clientSelectionPanel.setVisible(true);
        submitButton.setText("Se connecter"); // Changement du texte du bouton
        revalidate();
        repaint();
    }

    // Affiche le panneau de création de compte pour nouveau client
    private void showNewClientPanel() {
        clientSelectionPanel.setVisible(false); // Masquer le panneau de connexion
        add(newClientPanel, BorderLayout.CENTER);
        newClientPanel.setVisible(true);
        submitButton.setText("Créer un compte"); // Changement du texte du bouton
        revalidate();
        repaint();
    }

    // Gestion des actions de soumission
    private void handleSubmit() {
        if (clientSelectionPanel.isVisible()) {
            // Connexion d'un client existant
            Client selectedClient = (Client) clientComboBox.getSelectedItem();
            String enteredPassword = new String(passwordField.getPassword());
            String storedPassword = DBToclient.getClientPassword(selectedClient.getClientID());

            if (enteredPassword.equals(storedPassword)) {
                JOptionPane.showMessageDialog(this, "Authentification réussie pour le client: " + selectedClient.getNom());
                LoadingServiceUI loadingService = new LoadingServiceUI();
                loadingService.showLoadingDialog(this);
                // Initialisation de la fenêtre Shop avec l'ID du client
                SwingUtilities.invokeLater(() -> {
                    new Shop(selectedClient.getClientID(),loadingService).setVisible(true);
                });
                
                dispose(); // Fermer la fenêtre actuelle
            } else {
                JOptionPane.showMessageDialog(this, "Mot de passe incorrect. Veuillez réessayer.");
            }
        } else if (newClientPanel.isVisible()) {
            // Création d'un nouveau client
            if (areNewClientFieldsValid()) {
            	
            	   if (!isValidDateFormat(dateNaissanceField.getText(), "yyyy-MM-dd")) {
                       JOptionPane.showMessageDialog(this, 
                           "Le format de la date de naissance est invalide. Veuillez utiliser le format yyyy-MM-dd.",
                           "Erreur de format de date",
                           JOptionPane.ERROR_MESSAGE);
                       return;
                   }
                Client newClient = new Client(
                    dateNaissanceField.getText(),
                    nomField.getText(),
                    prenomField.getText(),
                    emailField.getText(),
                    telephoneField.getText(),
                    DBToclient.getMaxClientID() + 1,
                    adresseField.getText(),
                    new String(newPasswordField.getPassword())
                );
                ClientToDB.addCustomer(newClient);

                JOptionPane.showMessageDialog(this, "Nouveau client créé: " + newClient.getNom());
                new AccueilClient(newClient.getClientID()).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tous les champs doivent être remplis.");
            }
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

    // Vérifie que tous les champs sont remplis pour l'inscription
    private boolean areNewClientFieldsValid() {
        return !nomField.getText().isEmpty() &&
               !prenomField.getText().isEmpty() &&
               !emailField.getText().isEmpty() &&
               !telephoneField.getText().isEmpty() &&
               !adresseField.getText().isEmpty() &&
               !dateNaissanceField.getText().isEmpty() &&
               newPasswordField.getPassword().length > 0;
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthentificationClient accueilClient = new AuthentificationClient();
            accueilClient.setVisible(true);
        });
    }
}


