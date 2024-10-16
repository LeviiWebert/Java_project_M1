package managementAppUI;

import javax.swing.*;

public class MainApp extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Authentification authentificationPage = new Authentification();
            authentificationPage.setVisible(true);
        });
    }
}
