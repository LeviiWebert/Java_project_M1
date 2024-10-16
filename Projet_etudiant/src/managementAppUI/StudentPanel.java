package managementAppUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import basicObjects.Education;
import basicObjects.Pair;
import basicObjects.Project;
import basicObjects.Student;
import dataBase.DBToEducation;
import dataBase.DBToPair;
import dataBase.DBToProject;
import dataBase.DBToStudent;
import dataBase.PairToDB;
import dataBase.ProjectToDB;
import dataBase.StudentToDB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class StudentPanel extends JPanel {
	    private JTextField nameField;
	    private JTextField firstnameField;
	    private JComboBox<Education> educationComboBox;
	    private JComboBox<Student> studentComboBox;
	    private JTable studentTable;
	    private static DefaultTableModel tableModel;
	    private JTabbedPane tabbedPane;
		 

	    public StudentPanel(JTabbedPane tabbedPane) {
	    	
	    	this.tabbedPane = tabbedPane;
	        JPanel buttonPanel = new JPanel();
	        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
	        
	        

	        JButton createStudentButton = new JButton("Create a Student");
	        createStudentButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
                   createStudentPanel();
	            }
	        });
	        
            buttonPanel.add(createStudentButton);
	        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        
	        
	        
	        JButton modifyStudentButton = new JButton("Modify a Student");
	        modifyStudentButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	modifyStudentPanel();
	            }
	        });
	          buttonPanel.add(modifyStudentButton);
		      buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		      
		      
		        
	        JButton deleteStudentButton = new JButton("Delete a Student ");
	        deleteStudentButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                   deleteStudentPanel();
	            }
	        });  
	          buttonPanel.add(deleteStudentButton);
		      buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		      
		      JButton showAllStudentsButton = new JButton("Show All Students");
		        showAllStudentsButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		   
		                updateTable(DBToStudent.getStudents());
		            }

				
		        });
		        buttonPanel.add(showAllStudentsButton);
		        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		        

		      add(buttonPanel, BorderLayout.WEST);


		      
	        String[] columnNames = {"Student ID", "Name", "FirstName", "Education ID"};
	        tableModel = new DefaultTableModel(columnNames, 0) {
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };

	        studentTable = new JTable(tableModel);
	        
	        JScrollPane scrollPane = new JScrollPane(studentTable);
	        scrollPane.setPreferredSize(new Dimension(650, 400));
	        
	        JPanel panelWithScrollPane = new JPanel(new BorderLayout());
	        panelWithScrollPane.add(scrollPane, BorderLayout.CENTER);
	        
	        
	        add(panelWithScrollPane, BorderLayout.WEST);
	        

	        List<Student> students = DBToStudent.getStudents();
	        tableModel.setRowCount(0);
	        for (Student student : students) {
	            Object[] rowData = {student.getStudentID(), student.getName(), student.getFirstName(), student.getEducation().getEducationID()};
	            tableModel.addRow(rowData);
	        }

	        int[] columnWidths = {100, 200, 200, 300};
	        for (int i = 0; i < columnWidths.length; i++) {
	            studentTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
	        }

	    }
	    
	    

	    private void createStudentPanel() {
	    	
	    	JFrame frame = new JFrame("Create a Student");
	        frame.setSize(600, 150);
	        frame.setLocationRelativeTo(null); 
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4, 2));

	        JLabel nameLabel = new JLabel("Name:");
	        nameField = new JTextField();

	        JLabel firstnameLabel = new JLabel("First Name:");
	        firstnameField = new JTextField();
	        
	        JLabel educationLabel = new JLabel("Education :");
	        
	        List<Education> educationList = DBToEducation.getEducations();
	        educationComboBox = new JComboBox<>(educationList.toArray(new Education[0]));

	        panel.add(nameLabel);
	        panel.add(nameField);
	        panel.add(firstnameLabel);
	        panel.add(firstnameField);
	        panel.add(educationLabel);
	        panel.add(educationComboBox);
	        
	     
	        
	        JButton validateButton = new JButton("Validate");
	        panel.add(validateButton);
	        
	        validateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            
	                Student newStudent = createStudentField(panel);
	                if (newStudent != null) {
	                    newStudent.addToDB();
	                    
	                
	                    StudentToDB.updateStudent(newStudent);

	                  
	                    updateStudentTable();

	           
	                    SwingUtilities.getWindowAncestor(panel).dispose();
	                }
	            }
	        });
	        frame.add(panel);
	        frame.setVisible(true);
	    }
	    
	    
	    
	    private void deleteStudentPanel() {
	       	
	    	JFrame frame = new JFrame("Delete a Student");
	        frame.setSize(400,100);
	        frame.setLocationRelativeTo(null); 
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(2, 2));

	        List<Student> students = DBToStudent.getStudents();
	        studentComboBox = new JComboBox<>(students.toArray(new Student[0]));
	        panel.add(new JLabel("Select a Student to delete :"));
	        panel.add(studentComboBox);

	        JButton deleteButton = new JButton("Validate \r\n"
	        		+ "\r\n"
	        		);
	        panel.add(deleteButton);

	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	     
	                Student selectedStudent = (Student) studentComboBox.getSelectedItem();

	                if (selectedStudent != null) {
	                   
	                    int result = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this student? This will automatically delete all pairs in which it is present.\r\n"
	                    		+ "\r\n"
	                    		+ "", "Confirmation", JOptionPane.YES_NO_OPTION);
	                    
	                    if (result == JOptionPane.YES_OPTION) {
	                        try {
	                        	 StudentToDB studentToDB = new StudentToDB();
			                     studentToDB.deleteStudent(selectedStudent);

			                     StudentToDB.updateStudent(selectedStudent);
			                     updateStudentTable();
			                       SwingUtilities.getWindowAncestor(panel).dispose();
	                        } catch (Exception ex) {
	                            ex.printStackTrace();
	                            JOptionPane.showMessageDialog(StudentPanel.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                        }
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(StudentPanel.this, "Select a Student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            }
	            });
	        
	        frame.add(panel);
	        frame.setVisible(true);
	    }

	    
	    private void modifyStudentPanel() {
	       	
	    	JFrame frame = new JFrame("Modify a Student");
	        frame.setSize(600, 150);
	        frame.setLocationRelativeTo(null); 
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(5, 2));

	        JLabel studentLabel = new JLabel("Student:");
	        List<Student> students = DBToStudent.getStudents();
	        studentComboBox = new JComboBox<>(students.toArray(new Student[0]));

	        JLabel nameLabel = new JLabel("Name:");
	        nameField = new JTextField();

	        JLabel firstnameLabel = new JLabel("Firstname:");
	        firstnameField = new JTextField();
	        
	        JLabel educationLabel = new JLabel("Education:");

	        List<Education> educations = DBToEducation.getEducations();
	        educationComboBox = new JComboBox<>(educations.toArray(new Education[0]));

	        studentComboBox.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Student selectedStudent = (Student) studentComboBox.getSelectedItem();
	                if (selectedStudent != null) {
	                    
	                    updateFormFields(selectedStudent);
	                }
	            }
	        });

	        JButton validateButton = new JButton("Validate");

	      
	        panel.add(studentLabel);
	        panel.add(studentComboBox);

	        panel.add(nameLabel);
	        panel.add(nameField);
	        panel.add(firstnameLabel);
	        panel.add(firstnameField);
	        panel.add(educationLabel); 
	        panel.add(educationComboBox);
	        panel.add(validateButton);

	        validateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    Student selectedStudent = (Student) studentComboBox.getSelectedItem();
	                    if (selectedStudent!=null) {
	                    String name = nameField.getText();
	                    String firstname = firstnameField.getText();
	                    Education selectedEducation = (Education) educationComboBox.getSelectedItem();

	                  
	                    selectedStudent.setName(name);
	                    selectedStudent.setFirstName(firstname);
	                    selectedStudent.setEducation(selectedEducation);

	                    StudentToDB.updateStudent(selectedStudent);

	                 
	                    updateStudentTable();

	                    SwingUtilities.getWindowAncestor(panel).dispose();
	                } else {
	                    JOptionPane.showMessageDialog(StudentPanel.this, "Please select a Student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
	                } } catch (Exception ex) {
	              
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        frame.add(panel);
	        frame.setVisible(true);
	    }
	    
	    

	    private Student createStudentField(JPanel panel) {

	        String name = nameField.getText();
	        String firstname = firstnameField.getText();
	        Education selectedEducation = (Education) educationComboBox.getSelectedItem();
	        


	        if (name.isEmpty()) {
	            name = "Default Name";
	        }

	        if (firstname.isEmpty()) {
	            firstname = "Default Firstname";
	        }

	        if (selectedEducation != null) {
	          
	            int educationID = selectedEducation.getEducationID();

	            return new Student(name, firstname, educationID);
	        } else {

	            JOptionPane.showMessageDialog(panel, "Please select an education.\r\n"
	            		+ "\r\n"
	            		+ "", "Error", JOptionPane.ERROR_MESSAGE);
	            return null;
	        }
	    }
	    


	    private void updateFormFields(Student selectedStudent) {
	        nameField.setText(selectedStudent.getName());
	        firstnameField.setText(selectedStudent.getFirstName());

	        educationComboBox.setSelectedItem(selectedStudent.getEducation());
	    }
	    

	    private void updateStudentTable() {
	      
	        tableModel.setRowCount(0);

	        List<Student> students = DBToStudent.getStudents();
	          for (Student student : students) {
	              Object[] rowData = {student.getStudentID(), student.getName(), student.getFirstName(), student.getEducation().getEducationID()};
	              tableModel.addRow(rowData);
	           }
	       }

		public static void updateTable(List<Student> students) {
			
			tableModel.setRowCount(0);
	           for (Student student : students) {
	               Object[] rowData = {student.getStudentID(), student.getName(), student.getFirstName(), student.getEducation().getEducationID()};
	               tableModel.addRow(rowData);
	           }
	    
			
		}
		

}
     