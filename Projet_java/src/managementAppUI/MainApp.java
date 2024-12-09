package managementAppUI;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainApp() {
        setTitle("Accueil Principal");
        setSize(700, 600); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ferme l'application à la fermeture
        setLocationRelativeTo(null); // Centre la fenêtre
        setLayout(new BorderLayout()); // Gestionnaire de disposition principal

        // Créer le panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBackground(new Color(245, 245, 245)); // Couleur de fond

        // Titre de l'application
        JLabel titleLabel = new JLabel("Bienvenue chez VéloDauphine", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(44, 62, 80));
        mainPanel.add(titleLabel);

        // Bouton pour accéder à l'espace client
        JButton clientButton = new JButton("Espace Client");
        clientButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        clientButton.setBackground(new Color(52, 152, 219));
        clientButton.setForeground(Color.WHITE);
        clientButton.setFocusPainted(false);
        clientButton.addActionListener(e -> {
            AuthentificationClient clientInterface = new AuthentificationClient();
            clientInterface.setVisible(true);
            dispose(); // Ferme l'écran principal
        });
        mainPanel.add(clientButton);

        // Bouton pour accéder à l'espace admin
        JButton adminButton = new JButton("Espace Admin");
        adminButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        adminButton.setBackground(new Color(231, 76, 60));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        adminButton.addActionListener(e -> {
            Admin adminInterface = new Admin();
            adminInterface.setVisible(true);
            dispose(); // Ferme l'écran principal
        });
        mainPanel.add(adminButton);

        // Ajout du panneau principal à la fenêtre
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp accueilPrincipal = new MainApp();
            accueilPrincipal.setVisible(true);
        });
    }
}
