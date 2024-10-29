package basicObject;

import DBTo.DBToclient;

public class Client {
	
	private int clientID;
	private String date_naissance;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String adresse;
	private String mdp;
	

	
	public Client(){
		this.clientID = DBToclient.getMaxClientID() + 1;		
	}
	
	public Client(String date_naissance,String nom,String prenom,String email,String telephone,String adresse,String mdp){
		
		this.clientID = DBToclient.getMaxClientID() + 1;   // relation avec la base de donnes
		this.date_naissance = date_naissance;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.adresse = adresse;
		this.mdp = mdp;
		
	}
	
	public Client(String date_naissance,String nom,String prenom,String email,String telephone,int clientID,String adresse,String mdp){
		
		this.clientID = clientID;
		this.date_naissance = date_naissance;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.adresse = adresse;
		this.mdp = mdp;
	}


	public String getmdp(){
		return mdp;
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


	public void setClientID(int id) {
		this.clientID= id;
	}
	
	public void setmdp(String mdp){
		this.mdp = mdp;
	}
	
	public String afficherDetails() {
		return "C'est le client n°"+String.valueOf(clientID);
	}
	
	public String toString() {
	        return nom + " " + prenom;  // Affiche "Nom Prénom" dans la liste déroulante
	}

}
