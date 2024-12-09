package service;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class LoadingServiceUI {
    private JWindow splashScreen;
    //private JDialog loadingDialog;

    public void showLoadingDialog(Window parent) {
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
        // Démarrer le thread de vérification de la connexion Internet
        new Thread(() -> {
            try {
                if (!isInternetAvailable()) {
                    SwingUtilities.invokeLater(() -> {
                        showMessageDialog(parent, "Vous avez besoin d'une connexion internet pour afficher les images des produits");
                        splashScreen.dispose();
                    });
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void hideLoadingDialog() {
        if (splashScreen != null) {
            SwingUtilities.invokeLater(() -> splashScreen.dispose());
        }
    }

    private boolean isInternetAvailable() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(5000); // Timeout de 5 secondes
            urlConnect.connect();
            return urlConnect.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }

    private void showMessageDialog(Window parent, String message) {
        SwingUtilities.invokeLater(() -> {
            JDialog messageDialog = new JDialog(parent, "Erreur de connexion");
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JLabel(message), BorderLayout.CENTER);
            messageDialog.getContentPane().add(panel);
            messageDialog.setSize(530, 200);
            messageDialog.setLocationRelativeTo(parent);
            messageDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            messageDialog.setVisible(true);
        });
    }
}


