package basicObjects;
import java.text.SimpleDateFormat;
import java.util.Date;
import dataBase.ProjectToDB;
import dataBase.DBToProject;

public class Project {
	private final int projectID;
	private String subject;
	private String theme;
	private Date expectedDeliveryDate;

	
	
	public int getProjectID() {
		   return projectID;
	}
	
	public Project(){

	       this.projectID = DBToProject.getMaxProjectID() + 1;
	 }
	 
    public Project(String subject, String theme, java.sql.Date date){

	       this.projectID = DBToProject.getMaxProjectID() + 1;
	       this.subject=subject;
	       this.theme=theme;
	       this.expectedDeliveryDate=date;
	 }
    

    public Project(String subject, String theme) {

	       this.projectID = DBToProject.getMaxProjectID() + 1;
           this.subject = subject;
           this.theme = theme;
    }
    
    public Project(int projectID, String subject, String theme, Date  expectedDeliveryDate2){

	       this.projectID = projectID;
	       this.subject=subject;
	       this.theme=theme;
	       this.expectedDeliveryDate=expectedDeliveryDate2;
	 }
	 
	public String getSubject() {
		   return subject;
	}

	public void setSubject(String subject) {
		   this.subject = subject;
	}

	public String getTheme() {
		   return theme;
	}

    public void setTheme(String theme) {
		   this.theme = theme;
	}
    
	public Date getExpectedDeliveryDate() {
		   return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(java.sql.Date newDate) {
		   this.expectedDeliveryDate = newDate;

	}
	
	public void setExpectedDeliveryDate(Date newDate2) {
		this.expectedDeliveryDate=newDate2;
	}
	 
	
	public void addToDB() {

		ProjectToDB ptoDB = new ProjectToDB();
        ptoDB.addProject(this); 
    }
	
	 @Override
	    public String toString() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	        return " ID: " + projectID + ", " +  subject + ", " + theme +
	               ", " + (expectedDeliveryDate != null ? dateFormat.format(expectedDeliveryDate) : "Not specified");
	    }



	
	
}