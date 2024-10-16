package basicObjects;

import java.util.List;

import javax.swing.JOptionPane;

import dataBase.DBToEducation;

import dataBase.DBToStudent;
import dataBase.StudentToDB;

public class Student {
	
	private final int studentID;
	private String name;
	private String firstname;
    private Education education;

	public Student(){ 
		  

	       this.studentID = DBToStudent.getMaxStudentID() + 1;                           
	    }   
	    
    public Student(String name, String firstname, Education education) {  

	     this.studentID = DBToStudent.getMaxStudentID() + 1;
	     this.name = name;  
		 this.firstname = firstname;   
		 this.education = education;   
	}  

    
	public Student(String name, String firstname, int educationID) {

	     this.studentID = DBToStudent.getMaxStudentID() + 1 ;
	        Education e = findEducationByID(educationID);
	        if (e != null) {
	            this.name = name;
	            this.firstname = firstname;
	            this.education = e;
	        } else {
	        	
	            String errorMessage = "Education not found for ID:\r\n"
	            		+ "\r\n"
	            		+ "" + educationID;
	            JOptionPane.showMessageDialog(null, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);

	       
	            throw new IllegalArgumentException(errorMessage);
	        }
	    }

	
	    private Education findEducationByID(int educationID) {
	        List<Education> educations = DBToEducation.getEducations();

	        
	        for (Education education : educations) {
	            if (education.getEducationID() == educationID) {
	                return education;
	            }
	        }

	   
	        return null;
	    }
 
		 public Student(int StudentID, String name, String firstname, int educationID) {  
	            this.studentID = StudentID;  
	    
		        Education e = findEducationByID(educationID);

		       
		        if (e != null) {
		            this.name = name;
		            this.firstname = firstname;
		            this.education = e;
		        } else {
		        
		            String errorMessage = "Education not found for ID:\r\n"
		            		+ "\r\n"
		            		+ " " + educationID;
		            JOptionPane.showMessageDialog(null, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);

		            throw new IllegalArgumentException(errorMessage);
		        }
	
		}  

	public int getStudentID() {
		    return studentID; 
	}   

	 public String getName() {   
		    return name;
	}

	 
	 public void setName(String name) {
		this.name = name;
	}


	 public String getFirstName() {
		return firstname;
	}

	 public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	 public Education getEducation() {
		return education;
	}

	 public void setEducation(Education education) {
		this.education = education;
	}

	 public void addToDB() {
			StudentToDB stoDB = new StudentToDB();
	        stoDB.addStudent(this); 
	    }
	 
	 @Override
	 public String toString() {
	     return "Student ID: " + studentID +
	            ", Name: " + name +
	            ", Firstname: " + firstname +
	            ", Education: " + (education != null ? education.getEducationID() : "Not specified");
	 }
}
