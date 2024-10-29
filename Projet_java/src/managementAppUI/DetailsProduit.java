package managementAppUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import basicObject.Produit;

public class DetailsProduit extends JFrame {
    public DetailsProduit(Produit product) {
        setTitle("Détails du Produit");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel(product.getImage());
        JTextArea detailsText = new JTextArea();
        detailsText.setText("Nom : " + product.getModele() + "\n" +
                            "Marque : " + product.getMarque() + "\n" +
                            "Prix : " + product.getPrix() + "€\n" +
                            "Description : " + product.getDescription());
        detailsText.setEditable(false);

        add(imageLabel, BorderLayout.NORTH);
        add(detailsText, BorderLayout.CENTER);
    }
}