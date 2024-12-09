package managementAppUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import basicObject.Produit;

public class DetailsProduit extends JFrame {
	
    private static final long serialVersionUID = 1L;

	public DetailsProduit(Produit product) {
        setTitle("Détails du Produit");
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(173, 216, 230));

        
        ImageIcon icon = new ImageIcon(product.getImage().getImage());
		Image productImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		JLabel imageLabel = new JLabel(new ImageIcon(productImage));
        JTextArea detailsText = new JTextArea();
        detailsText.setText(product.toString());
        detailsText.setEditable(false);

        add(imageLabel, BorderLayout.NORTH);
        add(detailsText, BorderLayout.CENTER);
    }
}