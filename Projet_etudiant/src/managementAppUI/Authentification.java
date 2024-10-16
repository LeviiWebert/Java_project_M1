package managementAppUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authentification extends JFrame {

    private JTextField idField;
    private JPasswordField mdpField;

    public Authentification() {
     
        setTitle("Authentification");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        

   
        JLabel idLabel = new JLabel("Login:");
        JLabel mdpLabel = new JLabel("Password:");

        idField = new JTextField(10);
        mdpField = new JPasswordField(10);

        JButton loginButton = new JButton("Log in");

   
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                char[] mdpc = mdpField.getPassword();
                String mdp = new String(mdpc);

           
                if (id.equals("admin") && mdp.equals("admin")) {
                    JOptionPane.showMessageDialog(Authentification.this, "Approved Connection.\nClick OK to continue.");
                    dispose(); 

                    
                    showMainWindow();
                } else {
                    JOptionPane.showMessageDialog(Authentification.this, "Incorrect identifiers. Please try again\r\n"
                    		+ "\r\n"
                    		+ ".", "Identification error\r\n"
                    				+ "\r\n"
                    				+ ".", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    
        setLayout(new GridLayout(3, 2));

      
        add(idLabel);
        add(idField);
        add(mdpLabel);
        add(mdpField);
        add(new JLabel()); 
        add(loginButton);
    }

    private void showMainWindow() {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(1000, 700);
        mainFrame.setTitle(" My Projects Management");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        PairPanel pairPanel = new PairPanel(tabbedPane);
        tabbedPane.addTab("Pairs", pairPanel);

        ProjectPanel projetPanel = new ProjectPanel(tabbedPane);
        tabbedPane.addTab("Projects", projetPanel);

        StudentPanel studentPanel = new StudentPanel(tabbedPane);
        tabbedPane.addTab("Students", studentPanel);

        EducationPanel educationPanel = new EducationPanel(tabbedPane);
        tabbedPane.addTab("Educations", educationPanel);

        mainFrame.add(tabbedPane);
        mainFrame.setVisible(true);
    }
}
