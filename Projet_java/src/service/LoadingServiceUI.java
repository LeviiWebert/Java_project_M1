package service;

import java.awt.BorderLayout;
import java.awt.Window;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LoadingServiceUI {
    private JDialog loadingDialog;

    public void showLoadingDialog(Window parent) {
        // Créer et afficher le dialogue de chargement sur le thread de dispatching des événements de Swing
        SwingUtilities.invokeLater(() -> {
            loadingDialog = new JDialog(parent, "Chargement en cours, veuillez patienter...");
            JPanel panel = new JPanel(new BorderLayout());
            JLabel loadingLabel = new JLabel("Chargement en cours, veuillez patienter...", JLabel.CENTER);

            panel.add(loadingLabel, BorderLayout.CENTER);
            loadingDialog.getContentPane().add(panel);
            loadingDialog.setSize(400, 200);
            loadingDialog.setLocationRelativeTo(parent);
            loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            loadingDialog.setVisible(true);

            // Démarrer le thread de vérification de la connexion Internet
            new Thread(() -> {
                try {
                    if (!isInternetAvailable()) {
                        SwingUtilities.invokeLater(() -> {
                            showMessageDialog(parent, "Vous avez besoin d'une connexion internet pour afficher les images des produits");
                            loadingDialog.dispose();
                        });
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            SwingUtilities.invokeLater(() -> loadingDialog.dispose());
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
            panel.add(new JLabel(message, JLabel.CENTER), BorderLayout.CENTER);
            messageDialog.getContentPane().add(panel);
            messageDialog.setSize(400, 150);
            messageDialog.setLocationRelativeTo(parent);
            messageDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            messageDialog.setVisible(true);
        });
    }
}
