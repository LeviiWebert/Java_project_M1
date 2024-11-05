package service;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadingServiceUI {
    private JDialog loadingDialog;

    public void showLoadingDialog(Window parent) {
        new Thread(() -> {
            try {
                if (!isInternetAvailable()) {
                    showMessageDialog(parent, "Vous avez besoin d'une connection internet pour afficher les images des produits");
                    return;
                }
                
                loadingDialog = new JDialog(parent, "Chargement");
                JPanel panel = new JPanel(new BorderLayout());
                JLabel loadingLabel = new JLabel("Chargement en cours, veuillez patienter...", JLabel.CENTER);
                URL resource = getClass().getResource("/ressources/spinner-loading-pjM1.gif");
                ImageIcon loadingIcon = null;
                if (resource == null) {
                    System.err.println("Le fichier de ressource n'a pas été trouvé.");
                } else {
                    loadingIcon = new ImageIcon(resource);
                }
                JLabel iconLabel = new JLabel(loadingIcon, JLabel.CENTER);
                
                panel.add(iconLabel, BorderLayout.CENTER);
                panel.add(loadingLabel, BorderLayout.SOUTH);
                loadingDialog.getContentPane().add(panel);
                loadingDialog.setSize(500, 400);
                loadingDialog.setLocationRelativeTo(parent);
                loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                loadingDialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dispose();
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
        JDialog messageDialog = new JDialog(parent, "Erreur de connexion");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(message), BorderLayout.CENTER);
        messageDialog.getContentPane().add(panel);
        messageDialog.setSize(530, 200);
        messageDialog.setLocationRelativeTo(parent);
        messageDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        messageDialog.setVisible(true);
    }
}
