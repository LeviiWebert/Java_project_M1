package basicObjects;

import dataBase.DBToEducation;
import dataBase.EducationToDB;

public class Education {
    public void setName(String name) {
		this.name = name;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	private int educationID;
    private String name;
    private String promo;

	public Education(){

	       this.educationID = DBToEducation.getMaxEducationID() + 1;
	 }
	
    public Education( String name, String promo) {

	     this.educationID = DBToEducation.getMaxEducationID() + 1;
         this.name = name;
         this.promo = promo;
    }
    

    
    public Education(int educationID, String name, String promo){
	       this.educationID = educationID;
	       this.name=name;
	       this.promo=promo;
	 }
 

    public int getEducationID() {
        return educationID;
    }

    public String getName() {
        return name;
    }

    public String getPromo() {
        return promo;
    }
    
	public void addToDB() {

		EducationToDB etoDB = new EducationToDB();
        etoDB.addEducation(this); 
    }
	
	 @Override
	    public String toString() {
	        return "Education ID: " + educationID +
	               ", Name: " + name +
	               ", Promo: " + promo;
	    }
	 
}
