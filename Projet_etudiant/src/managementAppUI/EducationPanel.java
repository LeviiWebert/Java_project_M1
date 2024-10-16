package managementAppUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import basicObjects.Education;
import dataBase.DBToEducation;
import dataBase.DBToStudent;
import dataBase.EducationToDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EducationPanel extends JPanel {

    private JTextField nameField;
    private JTextField promoField;
    private JTable educationTable;
    private JComboBox<Education> educationComboBox;
    private DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;

    public EducationPanel(JTabbedPane tabbedPane) {
    	this.tabbedPane = tabbedPane;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        
        
        
        JButton createEducationButton = new JButton("Create an Education");
        createEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	createEducationPanel();
     
            }
        });
        buttonPanel.add(createEducationButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        
        

        JButton modifyEducationButton = new JButton("Modify an Education");
        modifyEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	modifyEducationPanel();
            }
        });
        buttonPanel.add(modifyEducationButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        
        

        
        JButton deleteEducationButton = new JButton("Delete an Education ");
        deleteEducationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               deleteEducationPanel();
                
            }
        });
        buttonPanel.add(deleteEducationButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        JButton showAllEducationsButton = new JButton("Show All Educations");
        showAllEducationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
   
                updateTable(DBToEducation.getEducations());
            }

		
        });
        buttonPanel.add(showAllEducationsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        
        String[] columnNames = {"Education ID", "Name", "Promo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        add(buttonPanel, BorderLayout.WEST);
        educationTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(educationTable);
        scrollPane.setPreferredSize(new Dimension(650, 400));

        JPanel panelWithScrollPane = new JPanel(new BorderLayout());
        panelWithScrollPane.add(scrollPane, BorderLayout.CENTER);

        add(panelWithScrollPane);
        add(panelWithScrollPane, BorderLayout.WEST);
        
        List<Education> educations = DBToEducation.getEducations();

        for (Education education : educations) {
            Object[] rowData = {education.getEducationID(), education.getName(), education.getPromo()};
            tableModel.addRow(rowData);
        }

        int[] columnWidths = {100, 200, 200};
        for (int i = 0; i < columnWidths.length; i++) {
            educationTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }
    
    
    
    

   
    private void createEducationPanel() {
    	
    	JFrame frame = new JFrame("Create an Education");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel promoLabel = new JLabel("Promo:");
        promoField = new JTextField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(promoLabel);
        panel.add(promoField);

        JButton validateButton = new JButton("Validate");
        panel.add(validateButton);

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Education newEducation = createEducationField(panel);
                if (newEducation != null) {
                    newEducation.addToDB();
                    tableModel.addRow(new Object[]{newEducation.getEducationID(), newEducation.getName(), newEducation.getPromo()});
                    SwingUtilities.getWindowAncestor(panel).dispose();
                }
            }
        });
        frame.add(panel);
        frame.setVisible(true);
    }
    
    
    
    
    
    
     private void deleteEducationPanel() {
    	
    	JFrame frame = new JFrame("Delete an Education");
        frame.setSize(500, 100);
        frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));


        List<Education> educations = DBToEducation.getEducations();
        educationComboBox = new JComboBox<>(educations.toArray(new Education[0]));
        panel.add(new JLabel("Select the Education to delete :"));
        panel.add(educationComboBox);

        JButton deleteButton = new JButton("Validate \r\n"
        		+ "\r\n"
        		+ "");
        panel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                Education selectedEducation = (Education) educationComboBox.getSelectedItem();

                if (selectedEducation != null) {
                   
                    int result = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this Education? This will automatically delete all the Students registered there and even more so all the Pairs associated with these students.\r\n"
                    		+ "\r\n"
                    		+ "", "Confirmation", JOptionPane.YES_NO_OPTION);
                    
                    if (result == JOptionPane.YES_OPTION) {
                        try {
                        	 EducationToDB educationToDB = new EducationToDB();
		                     educationToDB.deleteEducation(selectedEducation);

		                       
		                     updateTable(DBToEducation.getEducations());
		                       SwingUtilities.getWindowAncestor(panel).dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(EducationPanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(EducationPanel.this, "Please select an Education to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
        
    }
    
    
    private void modifyEducationPanel() {
        JFrame frame = new JFrame("Modify an Education");
    	frame.setSize(400, 200);
    	frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(5, 2));


        JLabel educationLabel = new JLabel("Education:");
        List<Education> educations = DBToEducation.getEducations();
        educationComboBox = new JComboBox<>(educations.toArray(new Education[0]));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel promoLabel = new JLabel("Promo:");
        promoField = new JTextField();
        

      
        educationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Education selectedEducation = (Education) educationComboBox.getSelectedItem();
                if (selectedEducation != null) {
                  
                    updateFormFields(selectedEducation);
                }

            }
        });

        JButton validateButton = new JButton("Validate");

        
        panel.add(educationLabel);
        panel.add(educationComboBox);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(promoLabel);
        panel.add(promoField);
        panel.add(validateButton);

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Education selectedEducation = (Education) educationComboBox.getSelectedItem();
                    if (selectedEducation!=null) {
                    String name = nameField.getText();
                    String promo = promoField.getText();

                    
                    selectedEducation.setName(name);
                    selectedEducation.setPromo(promo);


                  
                    EducationToDB.updateEducation(selectedEducation);

               
                    updateEducationTable();

                    SwingUtilities.getWindowAncestor(panel).dispose();
                    } else {
	                    JOptionPane.showMessageDialog(EducationPanel.this, "Please select an Education to delete.", "Error", JOptionPane.ERROR_MESSAGE);
	            }

                } catch (Exception ex) {
                 
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }


    
    
    private Education createEducationField(JPanel panel) {
        String name = nameField.getText();
        String promo = promoField.getText();


        if (name.isEmpty()) {
            name = "Default name\r\n"
            		+ "\r\n"
            		+ "";
        }

        if (promo.isEmpty()) {
            promo = "Default Promo ";
        }

        return new Education(name, promo);
    }
    
    
    
    private void updateFormFields(Education selectedEducation) {
        nameField.setText(selectedEducation.getName());
        promoField.setText(selectedEducation.getPromo());

    }


    
    private void updateTable(List<Education> educations) {
 	 
 	          tableModel.setRowCount(0);
 	          for (Education education : educations) {
 	              Object[] rowData = { education.getEducationID(),education.getName(),education.getPromo()};
 	          tableModel.addRow(rowData);
 	    }
    }
 	          
 	          
 	 private void updateEducationTable() {
 		       
 		       tableModel.setRowCount(0);
 		       List<Education> educations = DBToEducation.getEducations();
 	     	   for (Education education : educations) {
 	     	   Object[] rowData = { education.getEducationID(),education.getName(),education.getPromo()};
 	     	   tableModel.addRow(rowData);
        }  
 	}
}
