package managementAppUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import basicObjects.Pair;
import basicObjects.Project;
import basicObjects.Student;
import dataBase.DBToPair;
import dataBase.DBToProject;
import dataBase.DBToStudent;
import dataBase.PairToDB;
import dataBase.ProjectToDB;
import exceptions.InvalidDateFormatException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;  // Ajout de l'importation

public class ProjectPanel extends JPanel {

    private JTextField subjectField;
    private JTextField themeField;
    private JTextField dateField;
    private JTable projectTable;
    private JComboBox<Project> projectComboBox;
    private static DefaultTableModel tableModel;
    private JTabbedPane tabbedPane;
    
    

    public ProjectPanel(JTabbedPane tabbedPane) {
    	this.tabbedPane = tabbedPane;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
    	
        JButton createProjectButton = new JButton("Create a Project");
        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	createProjectPanel();

            }
        });
        buttonPanel.add(createProjectButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        
        JButton modifyProjectButton = new JButton("Modify a Project");
        modifyProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	  ModifyProjectPanel();
            }
        });
        buttonPanel.add(modifyProjectButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        
        JButton deleteProjectButton = new JButton("Delete a Project");
        deleteProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	deleteProjectPanel();
            }
        });
        buttonPanel.add(deleteProjectButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        

        JButton showAllProjectsButton = new JButton("Show All Projects");
        showAllProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
   
                updateTable(DBToProject.getProjects());
            }
        });
        buttonPanel.add(showAllProjectsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        
        
        JButton showProjectsByThemeButton = new JButton("Show Projects by Theme");
        showProjectsByThemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProjectsForTheme();
            }
        });
        buttonPanel.add(showProjectsByThemeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        

        add(buttonPanel, BorderLayout.WEST);
        
        
        
        
        String[] columnNames = {"Project ID", "Subject", "Theme", "Expected Delivery Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        projectTable = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(projectTable);
        scrollPane.setPreferredSize(new Dimension(650, 400));
        JPanel panelWithScrollPane = new JPanel(new BorderLayout());
        panelWithScrollPane.add(scrollPane, BorderLayout.CENTER);

        add(panelWithScrollPane, BorderLayout.WEST);
        

        List<Project> projects = DBToProject.getProjects();
        for (Project project : projects) {
            Object[] rowData = {project.getProjectID(), project.getSubject(), project.getTheme(), project.getExpectedDeliveryDate()};
            tableModel.addRow(rowData);
        }

        int[] columnWidths = {100, 800, 200, 300};
        for (int i = 0; i < columnWidths.length; i++) {
            projectTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }
    
    
    
    private void showProjectsForTheme() {
     
        String theme = JOptionPane.showInputDialog(this, "Enter Theme:", "Show Projects by Theme", JOptionPane.QUESTION_MESSAGE);

        if (theme != null && !theme.isEmpty()) {
         
            updateTable(DBToProject.getProjectsForTheme(theme));
        }
    }



    private void createProjectPanel() {
    	    JFrame frame = new JFrame("Create a Project");
    	    frame.setSize(500, 150);
    	    frame.setLocationRelativeTo(null); 
    	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));


        JLabel subjectLabel = new JLabel("Subject:");
        subjectField = new JTextField();

        JLabel themeLabel = new JLabel("Theme:");
        themeField = new JTextField();

        JLabel dateLabel = new JLabel("Expected delivery date:");
        dateField = new JTextField();

        panel.add(subjectLabel);
        panel.add(subjectField);
        panel.add(themeLabel);
        panel.add(themeField);
        panel.add(dateLabel);
        panel.add(dateField);

        JButton validateButton = new JButton("Validate");
        panel.add(validateButton);

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 try {
                Project newProject = createProjectField(panel);
                if (newProject != null) {
                    newProject.addToDB();
                    tableModel.addRow(new Object[]{newProject.getProjectID(), newProject.getSubject(), newProject.getTheme(), newProject.getExpectedDeliveryDate()});
                    SwingUtilities.getWindowAncestor(panel).dispose();
                }
            } catch (InvalidDateFormatException ex) {
                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Date format error", JOptionPane.ERROR_MESSAGE);
            }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }



    private void updateFormFields(Project selectedProject) {
    	
        java.util.Date expectedDeliveryDate = selectedProject.getExpectedDeliveryDate();

        if (expectedDeliveryDate == null) {

            dateField.setText("");
        } else {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateField.setText(dateFormat.format(expectedDeliveryDate));
        }

        subjectField.setText(String.valueOf(selectedProject.getSubject()));
        themeField.setText(String.valueOf(selectedProject.getTheme()));
    }

    
    private void deleteProjectPanel() {
    	JFrame frame = new JFrame("Delete a Project");
        frame.setSize(400, 100);
        frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

     
        List<Integer> projectIDs = DBToProject.getProjectIDs();
        JComboBox<Integer> projectIDComboBox = new JComboBox<>(projectIDs.toArray(new Integer[0]));

        panel.add(new JLabel("Select the project ID to delete:"));
        panel.add(projectIDComboBox);

        JButton deleteButton = new JButton("Validate ");
        panel.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
          
                Integer selectedProjectID = (Integer) projectIDComboBox.getSelectedItem();
                
                if (selectedProjectID != null) {
                    int result = JOptionPane.showConfirmDialog(ProjectPanel.this,
                            "Deleting this project will result in the deletion of the associated pairs.\nAre you sure you want to continue?\r\n"
                            + "\r\n"
                            + "",
                            "Deletion confirmation\r\n"
                            + "\r\n"
                            + "", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                      
                        try {
                            ProjectToDB projectToDB = new ProjectToDB();
                            projectToDB.deleteProject(selectedProjectID);

                        
                            updateProjectTable();
                            SwingUtilities.getWindowAncestor(panel).dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(ProjectPanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(ProjectPanel.this, "Please select a Project ID to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
    
    private void ModifyProjectPanel() {
    	
    	 JFrame frame = new JFrame("Modify an Project");
    	 frame.setSize(500, 150);
    	 frame.setLocationRelativeTo(null); 
    	 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	    
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));


        List<Project> projects = DBToProject.getProjects();
        JLabel projectLabel = new JLabel("Project:");
        projectComboBox = new JComboBox<>(projects.toArray(new Project[0]));
        
        JLabel subjectLabel = new JLabel("Subject:");
         subjectField = new JTextField();

        JLabel themeLabel = new JLabel("Theme:");
         themeField = new JTextField();

        JLabel dateLabel = new JLabel("Expected delivery date:");
         dateField = new JTextField();


	    projectComboBox.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        Project selectedProject= (Project) projectComboBox.getSelectedItem();
	        if (selectedProject != null) {
	            updateFormFields(selectedProject);
	        }
	    }
	});

        JButton validateButton = new JButton("Validate");
  

        panel.add(projectLabel);
        panel.add(projectComboBox);

        panel.add(subjectLabel);
        panel.add(subjectField);
        panel.add(themeLabel);
        panel.add(themeField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(validateButton);
        
  

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Project selectedProject = (Project) projectComboBox.getSelectedItem();
                    if(selectedProject !=null) {
                    String subject = subjectField.getText();
                    String theme = themeField.getText();
                    String newDateStr = dateField.getText();
                    Date newDate = null;

                    if (!newDateStr.isEmpty()) {
                        if (!isValidDateFormat(newDateStr)) {
                            throw new InvalidDateFormatException("Please enter a date in \\\"yyyy-MM-dd\\\" format.");
                        }

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date parsedDate = dateFormat.parse(newDateStr);
                        newDate = new Date(parsedDate.getTime());
                    }

	                
	                java.util.Date ExpectedDeliveryDate = newDate;
	        
	                selectedProject.setExpectedDeliveryDate(ExpectedDeliveryDate);
	                selectedProject.setSubject(subject);
	                selectedProject.setTheme(theme);

	    
	                ProjectToDB.updateProject(selectedProject);

	                updateProjectTable();

	                SwingUtilities.getWindowAncestor(panel).dispose();
                    } else {
                        JOptionPane.showMessageDialog(ProjectPanel.this, "Please select a Project  to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                    }} catch (InvalidDateFormatException ex) {
	                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Date format error", JOptionPane.ERROR_MESSAGE);
	            } catch (NumberFormatException ex) {
	              
	                JOptionPane.showMessageDialog(panel, "Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
	            } catch (ParseException e1) {
					
					e1.printStackTrace();
				}
	        }
	    });
        
        frame.add(panel);
	    frame.setVisible(true);
	    
	}
    
    
    private Project createProjectField(JPanel panel) throws InvalidDateFormatException {
        String subject = subjectField.getText();
        String theme = themeField.getText();
        String dateString = dateField.getText();
        java.sql.Date date1 = null;
        
        if (!dateString.isEmpty()) {
            if (!isValidDateFormat(dateString)) {
                throw new InvalidDateFormatException("Please enter a date in \\\"yyyy-MM-dd\\\" format.");
            }

            try {
                date1 = java.sql.Date.valueOf(dateString);
            } catch (IllegalArgumentException e) {
     
                e.printStackTrace();
            }
        }
        
        
        if (subject.isEmpty()) {
            subject = null;
        }

        if (theme.isEmpty()) {
            theme = null;
        }

        return new Project(subject, theme, date1);
    }


    

    private void updateProjectTable() {
       
        tableModel.setRowCount(0);

      
        List<Project> projects = DBToProject.getProjects();
        for (Project project : projects) {
            Object[] rowData = {project.getProjectID(), project.getSubject(), project.getTheme(), project.getExpectedDeliveryDate()};
            tableModel.addRow(rowData);
        }
    }
    
    public static void updateTable(List<Project> projects) {
        tableModel.setRowCount(0);

        for (Project project : projects) {
            Object[] rowData = {project.getProjectID(), project.getSubject(), project.getTheme(), project.getExpectedDeliveryDate()};
            tableModel.addRow(rowData);
        }
    }

    
	private boolean isValidDateFormat(String date) {
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormat.setLenient(false);
	        dateFormat.parse(date);
	        return true;
	    } catch (ParseException e) {
	        return false;
	    }
	}
	

    
}

