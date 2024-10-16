package basicObject;

public class Client {
	
	private int clientID;
	private String date_naissance;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String adresse;
	

	
	public Client(){
		this.clientID = 0;  //relation avec la base de donnes
	}
	
	
	public Client(String date_naissance,String nom,String prenom,String email,String telephone,String adresse){
		
		this.clientID = 0;  // relation avec la base de donnes
		this.date_naissance = date_naissance;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.adresse = adresse;
	}
	
	public Client(String date_naissance,String nom,String prenom,String email,String telephone,int clientID,String adresse){
		
		this.clientID = clientID;
		this.date_naissance = date_naissance;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.adresse = adresse;
	}


	public int getClientID() {
		return clientID;
	}


	public String getDate_naissance() {
		return date_naissance;
	}


	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	

}