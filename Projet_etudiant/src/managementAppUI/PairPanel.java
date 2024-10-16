package managementAppUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import basicObjects.Pair;
import basicObjects.Project;
import basicObjects.Student;
import dataBase.DBToPair;
import dataBase.DBToProject;
import dataBase.DBToStudent;
import dataBase.PairToDB;

public class PairPanel extends JPanel {

    private JComboBox<Student> student1ComboBox;
    private JComboBox<Student> student2ComboBox;
    private JComboBox<Project> projectComboBox;
    private JTextField dateField;
    private JTextField reportGradeField;
    private JTextField defenseGradeStu1Field;
    private JTextField defenseGradeStu2Field;
    private JComboBox<Pair> pairComboBox;
    private JTabbedPane tabbedPane;
    private static DefaultTableModel tableModel;
    private JTable pairTable;

    public PairPanel(JTabbedPane tabbedPane) {
    	
        this.tabbedPane = tabbedPane;
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));


   
        JButton createPairButton = new JButton("Create a Pair ");
        createPairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPairPanel();
            }
        });
        buttonPanel.add(createPairButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        

        JButton deletePairButton = new JButton("Delete a Pair ");
        deletePairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePairPanel();
            }
        });
        buttonPanel.add(deletePairButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        

        JButton addGradesButton = new JButton("Grades - Delivery date");
        addGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradesAndDatesPanel();
            }
        });
        buttonPanel.add(addGradesButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        
        
  
        JButton showAllPairsButton = new JButton("Show All Pairs");
        showAllPairsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                updateTable(DBToPair.getPairs());
            }
        });
        buttonPanel.add(showAllPairsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        
        
        
        JButton showPairsForProjectButton = new JButton("Show Pairs by Project");
        showPairsForProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPairsForProject();
            }
        });
        buttonPanel.add(showPairsForProjectButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton showProjectsForStudentButton = new JButton("Show Projects by Student");
        showProjectsForStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProjectsForStudent();
            }
        });
        buttonPanel.add(showProjectsForStudentButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        
        JButton showMaxFinalGradeButton = new JButton("Show Max Grade by Project");
        showMaxFinalGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMaxFinalGrade();
            }
        });
        buttonPanel.add(showMaxFinalGradeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
   
        JButton showMinFinalGradeButton = new JButton("Show Min  Grade by Project");
        showMinFinalGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMinFinalGrade();
            }
        });
        buttonPanel.add(showMinFinalGradeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        

        JButton showAverageFinalGradeButton = new JButton("Show Average Final Grade");
        showAverageFinalGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAverageFinalGrade();
            }
        });
        buttonPanel.add(showAverageFinalGradeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    
        add(buttonPanel, BorderLayout.WEST);
        
        
        
        

        String[] columnNames = {"groupID", "projectID", "stu1 ", "stu2 ", "delivery", " report", " defense_stu1",
                "final_stu1", "defense_stu2", " final_stu2"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        pairTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(pairTable);
        scrollPane.setPreferredSize(new Dimension(650, 400));

        JPanel panelWithScrollPane = new JPanel(new BorderLayout());
        panelWithScrollPane.add(scrollPane, BorderLayout.CENTER);

        add(panelWithScrollPane, BorderLayout.CENTER);

    }
    

    private void showAllPairs() {
        updateTable(DBToPair.getPairs());
    }


    private void showPairsForProject() {
        
        String projectIDString = JOptionPane.showInputDialog(this, "Enter Project ID:", "Show Pairs by Project", JOptionPane.QUESTION_MESSAGE);

        if (projectIDString != null && !projectIDString.isEmpty()) {
            try {
                int projectID = Integer.parseInt(projectIDString);
                updateTable(DBToPair.getPairsForProject(projectID));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Project ID. Please enter a valid numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        }

    private void showProjectsForStudent() {

        String studentIDString = JOptionPane.showInputDialog(this, "Enter Student ID:", "Show Projects by Student", JOptionPane.QUESTION_MESSAGE);

        if (studentIDString != null && !studentIDString.isEmpty()) {
            try {
                int studentID = Integer.parseInt(studentIDString);

                updateTable(DBToPair.getPairsForStudent(studentID));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Student ID. Please enter a valid numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showMaxFinalGrade() {
  
        String projectIDString = JOptionPane.showInputDialog(this, "Enter Project ID:", "Show Max Final Grade", JOptionPane.QUESTION_MESSAGE);

        if (projectIDString != null && !projectIDString.isEmpty()) {
            try {
                int projectID = Integer.parseInt(projectIDString);

                double maxFinalGrade = DBToPair.getMaxFinalGradeForProject(projectID);

              
                JOptionPane.showMessageDialog(this, "Max Final Grade for Project " + projectID + ": " + maxFinalGrade, "Max Final Grade", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Project ID. Please enter a valid numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showMinFinalGrade() {
        
        String projectIDString = JOptionPane.showInputDialog(this, "Enter Project ID:", "Show Min Final Grade", JOptionPane.QUESTION_MESSAGE);

        if (projectIDString != null && !projectIDString.isEmpty()) {
            try {
                int projectID = Integer.parseInt(projectIDString);
            
                double minFinalGrade = DBToPair.getMinFinalGradeForProject(projectID);
                JOptionPane.showMessageDialog(this, "Min Final Grade for Project " + projectID + ": " + minFinalGrade, "Min Final Grade", JOptionPane.INFORMATION_MESSAGE);
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Project ID. Please enter a valid numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            }
        }
    
    private void showAverageFinalGrade() {

        String projectIDString = JOptionPane.showInputDialog(this, "Enter Project ID:", "Show Average Final Grade", JOptionPane.QUESTION_MESSAGE);

        if (projectIDString != null && !projectIDString.isEmpty()) {
            try {
                int projectID = Integer.parseInt(projectIDString);
          
                double averageFinalGrade = DBToPair.getAverageFinalGradeForProject(projectID);
                JOptionPane.showMessageDialog(this, "Average Final Grade for Project " + projectID + ": " + averageFinalGrade, "Average Final Grade", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException | SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching Average Final Grade. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    


	 private void createPairPanel() {
		 
		    JFrame frame = new JFrame("Create a Pair");
		    frame.setSize(400, 200);
		    frame.setLocationRelativeTo(null); 
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		 
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4,2));
	        
	
	      
	        List<Project> projectList = DBToProject.getProjects();
	        projectComboBox = new JComboBox<>(projectList.toArray(new Project[0]));

	        List<Student> studentList = DBToStudent.getStudents();
	        student1ComboBox = new JComboBox<>(studentList.toArray(new Student[0]));
	        student2ComboBox = new JComboBox<>(studentList.toArray(new Student[0]));


	        panel.add(projectComboBox);
	        panel.add(student1ComboBox);
	        panel.add(student2ComboBox);

	        JButton validateButton = new JButton("Validate");
	        panel.add(validateButton);

	        validateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Pair newPair = createPairField(panel)  ;

	                if (newPair != null) {
	                    try {
	                        newPair.addToDB();

	                        updateTable(DBToPair.getPairs());

	                        SwingUtilities.getWindowAncestor(panel).dispose();
	                    } catch (Exception ex) {
	                    
	                        ex.printStackTrace();
	                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            }
	        });

	        frame.add(panel);
	        frame.setVisible(true);
	 }
	 
	 
	 private void deletePairPanel() {
		 
		 JFrame frame = new JFrame("Delete a Pair");
		    frame.setSize(500, 100);
		    frame.setLocationRelativeTo(null); 
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    
		    JPanel panel = new JPanel();
		    panel.setLayout(new GridLayout(2, 2));

		    List<Pair> pairs = DBToPair.getPairs();
		    pairComboBox = new JComboBox<>(pairs.toArray(new Pair[0]));
		    panel.add(new JLabel("Select the Pair to delete:"));
		    panel.add(pairComboBox);

		    JButton deleteButton = new JButton("Validate \r\n"
		    		+ "\r\n"
		    		+ "");
		    panel.add(deleteButton);

		    deleteButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		           
		            Pair selectedPair = (Pair) pairComboBox.getSelectedItem();

		            if (selectedPair != null) {
		               
		                int result = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this Pair ?", "Confirmation", JOptionPane.YES_NO_OPTION);
		                
		                if (result == JOptionPane.YES_OPTION) {
		                    try {
		                        PairToDB pairToDB = new PairToDB();
		                        pairToDB.deletePair(selectedPair);    
		                        updateTable(DBToPair.getPairs());
		                        SwingUtilities.getWindowAncestor(panel).dispose();
		                    } catch (Exception ex) {
		                        ex.printStackTrace();
		                        JOptionPane.showMessageDialog(PairPanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		                    }
		                }
		            } else {
		                JOptionPane.showMessageDialog(PairPanel.this, "Please select a Pair to delete.\r\n"
		                		+ "\r\n"
		                		+ "", "Erreur", JOptionPane.ERROR_MESSAGE);
		            }
		        }
		    });

		    frame.add(panel);
		    frame.setVisible(true);
		}
	 
	 
	 
     
     private void GradesAndDatesPanel() {
  	    JFrame frame = new JFrame("Management of Grades and Delivery Dates");
  	    frame.setSize(500, 200);
  	    frame.setLocationRelativeTo(null); 
  	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

  	    JPanel panel = new JPanel();
  	    panel.setLayout(new GridLayout(10,2));
  	    

  	    
  	    List<Pair> pairs = DBToPair.getPairs();
  	    pairComboBox = new JComboBox<>(pairs.toArray(new Pair[0]));
  	    
	        JLabel dateLabel = new JLabel("Effective delivery date :");
	        dateField = new JTextField();

  	    JLabel reportGradeStu1Label = new JLabel("Report Grade for the Pair :");
  	    reportGradeField = new JTextField();

  	    JLabel defenseGradeStu1Label = new JLabel("Defense Grade Student 1:");
  	    defenseGradeStu1Field = new JTextField();

  	    JLabel defenseGradeStu2Label = new JLabel("Defense Grade Student 2:");
  	    defenseGradeStu2Field = new JTextField();
  	    
  
  	    pairComboBox.addActionListener(new ActionListener() {
  	    @Override
  	    public void actionPerformed(ActionEvent e) {
  	        Pair selectedPair = (Pair) pairComboBox.getSelectedItem();
  	        if (selectedPair != null) {
  	           
  	            updateFormFields(selectedPair);
  	        }
  	    }
  	});
  	    


  	    JButton validateButton = new JButton("Validate");
  	    
  	    panel.add(pairComboBox);
  	    panel.add(dateLabel);
  	    panel.add(dateField);
  	    panel.add(reportGradeStu1Label);
  	    panel.add(reportGradeField);
  	    panel.add(defenseGradeStu1Label);
  	    panel.add(defenseGradeStu1Field);
  	    panel.add(defenseGradeStu2Label);
  	    panel.add(defenseGradeStu2Field);
  	   
	   
	        
	        
  	    panel.add(validateButton);
  	    

  	    validateButton.addActionListener(new ActionListener() {
  	        @Override
  	        public void actionPerformed(ActionEvent e) {
  	            try {
  	            
  	                Pair selectedPair = (Pair) pairComboBox.getSelectedItem();
  	                if(selectedPair!=null) {
  	                String reportGradeStr = reportGradeField.getText();
  	                double reportGrade = (reportGradeStr.isEmpty()) ? 0.0 : Double.parseDouble(reportGradeStr);
  	                
  	                
  	        
  	                String defenseGradeStu1Str = defenseGradeStu1Field.getText();
  	                String defenseGradeStu2Str = defenseGradeStu2Field.getText();
  	                
  	                double defenseGradeStu1 = (defenseGradeStu1Str.isEmpty()) ? 0.0 : Double.parseDouble(defenseGradeStu1Str);
  	                double defenseGradeStu2 = (defenseGradeStu2Str.isEmpty()) ? 0.0 : Double.parseDouble(defenseGradeStu2Str);
  	                
  	                if (reportGrade < 0 || reportGrade > 20 || defenseGradeStu1 < 0 || defenseGradeStu1 > 20 || defenseGradeStu2 < 0 || defenseGradeStu2 > 20) {
  	                    throw new IllegalArgumentException("Grades must be between 0 and 20.\r\n"
  	                    		+ "\r\n"
  	                    		+ "");
  	                }
  	                
  	                String newDateStr = dateField.getText();
  	                java.util.Date newDate = null;

  	          
  	                if (!newDateStr.isEmpty()) {
  	                    try {
  	                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  	                        java.util.Date parsedDate = dateFormat.parse(newDateStr);
  	                        newDate = new java.util.Date(parsedDate.getTime());
  	                    } catch (ParseException ex) {
  	                    	JOptionPane.showMessageDialog(panel, "Please enter a date in the format yyyy-mm-dd", "Error", JOptionPane.ERROR_MESSAGE);
  	                    }
  	                }

  	                java.util.Date expectedDeliveryDate = selectedPair.getProject().getExpectedDeliveryDate();
  	                if (newDate != null & expectedDeliveryDate!=null) {
  	  
  	                    java.util.Date effectiveDeliveryDate = new java.sql.Date(newDate.getTime());
  	                    long daysOfDelay = calculateDaysOfDelay(expectedDeliveryDate, effectiveDeliveryDate);
  	                    double penalty = 0.5 * daysOfDelay;

  	                    double finalGradeStu1 = (reportGrade + defenseGradeStu1) / 2.0 - penalty;
  	                    double finalGradeStu2 = (reportGrade + defenseGradeStu2) / 2.0 - penalty;

  	                    selectedPair.setEffectiveDeliveryDate(effectiveDeliveryDate);
  	                    selectedPair.setReportGrade(reportGrade);
  	                    selectedPair.setDefenseGradeStu1(defenseGradeStu1);
  	                    selectedPair.setDefenseGradeStu2(defenseGradeStu2);
  	                    selectedPair.setFinalGradeStu1(finalGradeStu1);
  	                    selectedPair.setFinalGradeStu2(finalGradeStu2);
  	                } else {

  	                    
  	                    selectedPair.setEffectiveDeliveryDate(newDate);
  	                    selectedPair.setReportGrade(reportGrade);
  	                    selectedPair.setDefenseGradeStu1(defenseGradeStu1);
  	                    selectedPair.setDefenseGradeStu2(defenseGradeStu2);
  	                    
  	                    double finalGradeStu1 = 0.0;
  	                    double finalGradeStu2 = 0.0;
  	                    selectedPair.setFinalGradeStu1(finalGradeStu1);
  	                    selectedPair.setFinalGradeStu2(finalGradeStu2);
  	                    
  	  
  	                    PairToDB.updatePair(selectedPair);

  	                }

  	                PairToDB.updatePair(selectedPair);
  	                updateTable(DBToPair.getPairs());

  	           
  	                SwingUtilities.getWindowAncestor(panel).dispose();
  	                }
  	                else {
                          JOptionPane.showMessageDialog(PairPanel.this, "Please select a Pair delete.", "Error", JOptionPane.ERROR_MESSAGE);
                      }}catch (NumberFormatException ex) {
  	                JOptionPane.showMessageDialog(panel, "Please enter valid numeric values.\r\n"
  	                		+ "\r\n"
  	                		+ ".", "Erreur", JOptionPane.ERROR_MESSAGE);
  	            } catch (IllegalArgumentException ex) {
  	                JOptionPane.showMessageDialog(panel, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
  	             } catch (SQLException ex) {
  	                    ex.printStackTrace();
  	                }
  	        }
  	    });

  	    frame.add(panel);
  	    frame.setVisible(true);
     }
     


	 private Pair createPairField(JPanel panel)    {

		    Project selectedProject = (Project) projectComboBox.getSelectedItem();
		    Student selectedStudent1 = (Student) student1ComboBox.getSelectedItem();
		    Student selectedStudent2 = (Student) student2ComboBox.getSelectedItem();
		    
	
		    if (isStudentInPair(selectedProject.getProjectID(), selectedStudent1.getStudentID()) ||
		        isStudentInPair(selectedProject.getProjectID(), selectedStudent2.getStudentID())) {
		        JOptionPane.showMessageDialog(panel, "One of the students is already in a pair for this project.\r\n"
		        		+ "\r\n"
		        		+ ".", "Error", JOptionPane.ERROR_MESSAGE);
		        return null; 
		    }
	        if (selectedStudent1.equals(selectedStudent2)) {
	            JOptionPane.showMessageDialog(panel, "Two students in a pair can't be the same.", "Error", JOptionPane.ERROR_MESSAGE);
	            return null;
	        }

	            Date date = null;
		        double reportGrade = 0.0;
		        double defenseGradeStu1 = 0.0;
		        double finalGradeStu1 = 0.0;
		        double defenseGradeStu2 = 0.0;
		        double finalGradeStu2 = 0.0;

		        int studentID1 = selectedStudent1.getStudentID();
		        int studentID2 = selectedStudent2.getStudentID();

		               
		        Pair newPair = new Pair(selectedProject.getProjectID(), studentID1, studentID2, date,
		                   reportGrade, defenseGradeStu1, finalGradeStu1, defenseGradeStu2, finalGradeStu2);
		        

		        return newPair; 

	 }
	 
     private void updateFormFields(Pair selectedPair) {
 	    java.util.Date effectiveDeliveryDate = selectedPair.getEffectiveDeliveryDate();

 	    if (effectiveDeliveryDate == null) {
 	        
 	        dateField.setText("");
 	    } else {
 	      
 	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 	        dateField.setText(dateFormat.format(effectiveDeliveryDate));
 	    }

 	    reportGradeField.setText(String.valueOf(selectedPair.getReportGrade()));
 	    defenseGradeStu1Field.setText(String.valueOf(selectedPair.getDefenseGradeStu1()));
 	    defenseGradeStu2Field.setText(String.valueOf(selectedPair.getDefenseGradeStu2()));
 	}
	 

	 private boolean isStudentInPair(int projectID, int studentID) {
	     List<Pair> pairsForProject = DBToPair.getPairsForProject(projectID);
	     for (Pair pair : pairsForProject) {
	         if (pair.getStudent1().getStudentID() == studentID || pair.getStudent2().getStudentID() == studentID) {
	             return true; 
	         }
	     }
	     return false; 
	 }
	 
	 
	 
     public static void updateTable(List<Pair> pairs) {
 
          tableModel.setRowCount(0);

          for (Pair pair : pairs) {
              Object[] rowData = { pair.getGroupID(),pair.getProject().getProjectID(), 
                pair.getStudent1().getStudentID(), pair.getStudent2().getStudentID(),
                pair.getEffectiveDeliveryDate(), pair.getReportGrade(),
                pair.getDefenseGradeStu1(), pair.getFinalGradeStu1(), pair.getDefenseGradeStu2(),
                pair.getFinalGradeStu2()};
          tableModel.addRow(rowData);
    }
	 
          
}

     private static long calculateDaysOfDelay(java.util.Date expectedDeliveryDate, java.util.Date effectiveDeliveryDate) {
    	    long millisecondsPerDay = 24 * 60 * 60 * 1000;
    	    long expectedTime = expectedDeliveryDate.getTime();
    	    long effectiveTime = effectiveDeliveryDate.getTime();
    	    return Math.max(0, (effectiveTime - expectedTime) / millisecondsPerDay);
    	}
       


}







