package basicObjects;

import java.util.*;
import javax.swing.JOptionPane;

import dataBase.DBToPair;
import dataBase.DBToProject;
import dataBase.DBToStudent;
import dataBase.PairToDB;

public class Pair{

	private final int groupID;
	private Project project;
	private Student student1;
	private Student student2;
	private Date effectiveDeliveryDate;
	private double reportGrade;
	private double defenseGradeStu1;
	private double finalGradeStu1;
	private double defenseGradeStu2;
	private double finalGradeStu2;
	
    public Pair() {

	       this.groupID = DBToPair.getMaxGroupID(this.project.getProjectID())+1 ;
    }
	
	public Pair(Project project, Student student1, Student student2, java.util.Date effectiveDeliveryDate, 
			double reportGrade, double defenseGradeStu1, double finalGradeStu1, double defenseGradeStu2, double finalGradeStu2 ){

	    this.project=project;
	    this.groupID = DBToPair.getMaxGroupID(this.project.getProjectID())+1;
	    this.student2=student2;
	    this.effectiveDeliveryDate= effectiveDeliveryDate;
	    this.reportGrade=reportGrade;
	    this.defenseGradeStu1=defenseGradeStu1;
	    this.finalGradeStu1=finalGradeStu1;
	    this.defenseGradeStu2=defenseGradeStu2;
	    this.finalGradeStu2=finalGradeStu2;
	   }
	
	public Pair(int projectID, int studentID1, int studentID2, java.sql.Date effectiveDeliveryDate,
			double reportGrade, double defenseGradeStu1, double finalGradeStu1, double defenseGradeStu2, double finalGradeStu2) {

	   
	    this.effectiveDeliveryDate= effectiveDeliveryDate;
	    this.reportGrade=reportGrade;
	    this.defenseGradeStu1=defenseGradeStu1;
	    this.finalGradeStu1=finalGradeStu1;
	    this.defenseGradeStu2=defenseGradeStu2;
	    this.finalGradeStu2=finalGradeStu2;
      

        Project p = findProjectByID(projectID);
        this.groupID = DBToPair.getMaxGroupID(p.getProjectID()) + 1;
        Student s1= findStudentByID(studentID1);
        Student s2= findStudentByID(studentID2);

        this.project = p;
 
        if (s1 != null) {
            this.student1 = s1;
            
        } else {
            String errorMessage = "Student not found for ID" + studentID1;
            JOptionPane.showMessageDialog(null, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException(errorMessage);  
            
        } if(s2!=null) {
        	this.student2 = s2;
        	
        } else {
            String errorMessage = "Student not found for ID " + studentID2;
            JOptionPane.showMessageDialog(null, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException(errorMessage);
        }
    }
	

	
	public Pair(int groupID, int projectID, int studentID1, int studentID2, Date effectiveDeliveryDate,
			double reportGrade, double defenseGradeStu1, double defenseGradeStu2, double finalGradeStu1, double finalGradeStu2 ) {

        this.groupID = groupID;  

        Project p = findProjectByID(projectID);
        Student s1=findStudentByID(studentID1);
        Student s2=findStudentByID(studentID2);
	    this.effectiveDeliveryDate= effectiveDeliveryDate;
	    this.reportGrade=reportGrade;
	    this.defenseGradeStu1=defenseGradeStu1;
	    this.defenseGradeStu2=defenseGradeStu2;
	    this.finalGradeStu1=finalGradeStu1;
	    this.finalGradeStu2=finalGradeStu2;
 
        if (p != null ) {
            this.project = p;
        }
        else {    	 
            String errorMessage = "Project not found for ID : " + projectID;
            JOptionPane.showMessageDialog(null, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);

            
            throw new IllegalArgumentException(errorMessage);
        	 }

        if (s1 != null) {
            this.student1 = s1;
      
        } else {
            String errorMessage = "Student not found for ID " + studentID1;
            JOptionPane.showMessageDialog(null, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException(errorMessage);
        }

        if(s2!=null) {
        	this.student2 = s2;
        }
        else {
      
            String errorMessage = "Student not found for ID " + studentID2;
            JOptionPane.showMessageDialog(null, errorMessage, "Erreur", JOptionPane.ERROR_MESSAGE);

        
            throw new IllegalArgumentException(errorMessage);
        }
    }


    private Project findProjectByID(int projectID) {
        List<Project> projects = DBToProject.getProjects();


        for (Project project : projects) {
            if (project.getProjectID() == projectID) {
                return project;
            }
        }


        return null;
    }
    
    
    private Student findStudentByID(int studentID) {
        List<Student> students = DBToStudent.getStudents();

  
        for (Student student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }

        return null;
    }


	public int getGroupID() {
		return groupID;
	}
	
	public double getDefenseGradeStu1() {
		return defenseGradeStu1;
	}

	public void setDefenseGradeStu1(double defenseGradeStu1) {
		this.defenseGradeStu1 = defenseGradeStu1;
	}

	public double getFinalGradeStu1() {
		return finalGradeStu1;
	}

	public void setFinalGradeStu1(double finalGradeStu1) {
		this.finalGradeStu1 = finalGradeStu1;
	}

	public double getDefenseGradeStu2() {
		return defenseGradeStu2;
	}

	public void setDefenseGradeStu2(double defenseGradeStu2) {
		this.defenseGradeStu2 = defenseGradeStu2;
	}

	public double getFinalGradeStu2() {
		return finalGradeStu2;
	}

	public void setFinalGradeStu2(double finalGradeStu2) {
		this.finalGradeStu2 = finalGradeStu2;
	}
	

	public Student getStudent1() {
		return student1;
	}

	public void setStudent1(Student student1) {
		this.student1 = student1;
	}

	public Student getStudent2() {
		return student2;
	}

	public void setStudent2(Student student2) {
		this.student2 = student2;
	}

	public Date getEffectiveDeliveryDate() {
		return effectiveDeliveryDate;
	}

	public void setEffectiveDeliveryDate(Date effectiveDeliveryDate) {
		this.effectiveDeliveryDate = effectiveDeliveryDate;
	}

	public double getReportGrade() {
		return reportGrade;
	}

	public void setReportGrade(double reportGrade) {
		this.reportGrade = reportGrade;
	}


	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public void addToDB() {
  
		PairToDB ptoDB = new PairToDB();
        ptoDB.addPair(this); 
    }
	


	    @Override
	    public String toString() {


	        return  "Group ID: " + groupID + "\n" +
	                ",Project: " + project.getProjectID() + "\n" +
	                ",Student 1: " + student1.getName() + "\n" +
	                " " + student1.getFirstName() + "\n" +
	                ",Student 2: " + student2.getName() + "\n" +
	                 " " + student2.getFirstName();
	                
	    }

	}
