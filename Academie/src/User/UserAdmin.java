package User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import ConnectionJdbc.ConnectionJdbc;
import Donnees.Adresse;
import Donnees.College;
import Donnees.Enseignant;
import Donnees.Etudiant;

/**
 * 
 * @author Florent & Apolline
 * Classe UserAdmin, implémente les actions réalisables par un administrateur.
 *
 */
public class UserAdmin extends User {

	// Attribute
	public College college;

	/*
	 * Constructors
	 */
	
	/**
	 * Constructeur de UserAdmin
	 * @param id : String, identifiant
	 * @param mdp : String, mot de passe
	 */
	public UserAdmin(String id, String mdp) {
		super(id, mdp);
	}
		
	/**
	 * 
	 * Constructeur de UserAdmin
	 * @param id : String, identifiant
	 * @param mdp : String, mot de passe
	 * @param college : College, college de l'administrateur
	 */
	public UserAdmin(String id, String mdp, College college) {
		super(id, mdp);
		this.college = college;	
	}
	
	/*
	 * Methods
	 */
	/**
	 * Crée la fiche signalétique de l'enseignant
	 * @param enseignant : Enseignant
	 */
	public void imprimerFicheSignaletiqueEnseignant(Enseignant enseignant) {
		enseignant.ficheSignaletique();
	}
	
	/**
	 * Crée la fiche signalétique de l'étudiant
	 * @param etudiant : Etudiant
	 */
	public void imprimerFicheSignaletiqueEtudiant(Etudiant etudiant) {
		etudiant.ficheSignaletique();
	}
	
	/**
	 * Ajoute un enseignant dans la BDD
	 * @param nom : String
	 * @param prenom : String
	 * @param dept : String, nom du departement principal
	 * @param datePriseDeFonction : Date
	 * @param adresse : String
	 * @param collegePrincipal : String
	 */
	public void creerEnseignant(String nom, String prenom, String dept, Date datePriseDeFonction, String adresse, College collegePrincipal ) {
		
		Connection conn = ConnectionJdbc.getInstance();
		
		creerAdresse(adresse);
		
		try {//Recherch de l'adresse
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT id FROM adresse WHERE adresse ILIKE '"+ adresse+"'");
			result.next();
			int id_adresse = result.getInt("id");
			
			//Recherche du departement
			ResultSet result2 = st.executeQuery("SELECT id FROM departement WHERE nom ILIKE '" + dept+"'");
			result2.next();
			int id_dept_principal = result2.getInt("id");
			
			//Création de l'identifiant de l'enseignant (max(id)+1)
			ResultSet result3 = st.executeQuery("SELECT MAX(id) FROM enseignant");
			result3.next();
			int id = result3.getInt("max")+1;
			
			//Ajout de l'enseignant à la BDD
			PreparedStatement state = conn.prepareStatement("INSERT INTO enseignant(nom, prenom, prise_de_fonction, id_adresse, id_college_principal, id_dept_principal, login, password, id ) VALUES (?,?,?,?,?,?,?,?,?) ");
			state.setString(1,nom);
			state.setString(2, prenom);
			state.setDate(3, datePriseDeFonction);
			state.setInt(4, id_adresse);
			state.setInt(5, collegePrincipal.numeroAcademique);
			state.setInt(6, id_dept_principal);
			state.setString(7, prenom.substring(0,0).toUpperCase()+nom);
			state.setString(8,creerMotDePasse());
			state.setInt(9,id);
			state.executeUpdate();
			
			state.close();
			st.close();
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	/**
	 * Ajoute un enseignant dans la BDD
	 * @param nom : String
	 * @param prenom : String
	 * @param dept : String, departement principal
	 * @param datePriseDeFonction : Date
	 * @param adresse : String
	 * @param collegePrincipal : String
	 * @param mail : String
	 * @param telephone : String
	 * @param matiere : String
	 */
	public void creerEnseignant(String nom, String prenom, String dept, Date datePriseDeFonction, String adresse, College collegePrincipal, String mail, String telephone, String matiere ) {
		
		//Cette méthode fonctionne comme la précédente
		Connection conn = ConnectionJdbc.getInstance();
		
		creerAdresse(adresse);
		
		try {
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT id FROM adresse WHERE adresse ILIKE '"+ adresse+"'");
			result.next();
			int id_adresse = result.getInt("id");
			
			ResultSet result2 = st.executeQuery("SELECT id FROM departement WHERE nom LIKE '" + dept+"'");
			result2.next();
			int id_dept_principal = result2.getInt("id");
			
			ResultSet result3 = st.executeQuery("SELECT MAX(id) FROM enseignant");
			result3.next();
			int id = result3.getInt("max")+1;
			
			ResultSet result4 = st.executeQuery("SELECT id FROM matiere WHERE nom LIKE '" + matiere+"'");
			result4.next();
			int id_matiere = result4.getInt("id");
			
			PreparedStatement state = conn.prepareStatement("INSERT INTO enseignant(id,nom, prenom, prise_de_fonction, id_adresse, id_college_principal, id_dept_principal, mail, telephone, login, password, id_matiere ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ");
			state.setInt(1, id);
			state.setString(2,nom);
			state.setString(3, prenom);
			state.setDate(4, datePriseDeFonction);
			state.setInt(5, id_adresse);
			state.setInt(6, collegePrincipal.numeroAcademique);
			state.setInt(7, id_dept_principal);
			state.setString(8, mail);
			state.setString(9, telephone);
			state.setString(10, prenom.substring(0, 1)+nom);
			state.setString(11, creerMotDePasse());
			state.setInt(12, id_matiere);
			state.executeUpdate();
			
			state.close();
			st.close();
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}

	/**
	 * Ajoute un enseignant dans la BDD
	 * @param nom : String
	 * @param prenom : String
	 * @param dept : String
	 * @param deptSec : String
	 * @param datePriseDeFonction : Date
	 * @param adresse : String
	 * @param collegePrincipal : String
	 * @param collegeSecondaire : String
	 * @param mail : String
	 * @param telephone : String
	 */
	public void creerEnseignant(String nom, String prenom, String dept, String deptSec, Date datePriseDeFonction, String adresse, College collegePrincipal, College collegeSecondaire, String mail, String telephone ) {
	
		//Cette méthode fonctionne comme les 2 dernières
		Connection conn = ConnectionJdbc.getInstance();
	
		creerAdresse(adresse);
	
		try {
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT id FROM adresse WHERE adresse LIKE "+ adresse);
			result.next();
			int id_adresse = result.getInt("id");
			
			ResultSet result2 = st.executeQuery("SELECT id FROM departement WHERE nom LIKE " + dept);
			result2.next();
			int id_dept_principal = result2.getInt("id");
			
			ResultSet result3 = st.executeQuery("SELECT id FROM departement WHERE nom LIKE " + deptSec);
			result3.next();
			int id_dept_secondaire = result3.getInt("id");
			
			
			PreparedStatement state = conn.prepareStatement("INSERT INTO enseignant(nom, prenom, prise_de_fonction, id_adresse, id_college_principal, id_college_secondaire, id_dept_principal, id_dept_secondaire, mail, telephone, login, password ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?) ");
			state.setString(1,nom);
			state.setString(2, prenom);
			state.setDate(3, datePriseDeFonction);
			state.setInt(4, id_adresse);
			state.setInt(5, collegePrincipal.numeroAcademique);
			state.setInt(6, collegeSecondaire.numeroAcademique);
			state.setInt(7, id_dept_principal);
			state.setInt(8, id_dept_secondaire);
			state.setString(9, mail);
			state.setString(10, telephone);
			state.setString(11, prenom.substring(0, 1)+nom);
			state.setString(12, creerMotDePasse());
			state.executeUpdate();
			
			state.close();
			st.close();
			}
		catch(Exception e){
			e.printStackTrace(); 
			}
		}
	
	/**
	 * Supprime un enseignant de la BDD
	 * @param nom : String
	 * @param prenom : String
	 */
	public void supprimerEnseignant(String nom, String prenom) {
		
		//Creation de l'enseignant(et donc choix de l'enseignant grâce au constructeur)
		Enseignant ens = new Enseignant(nom, prenom);
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			//Suppression
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM enseignant WHERE id = "+ens.id);
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
		
		
	}
		
//-------------------------------------------------------------------------------------------------------------|
//Les Fonctions Creer/Supprimer etudiant et collège fonctionnent comme pour la creation/suppression d'etudiant |
//-------------------------------------------------------------------------------------------------------------|

	/**
	 * Ajout d'un etudiant dans la BDD
	 * @param nom : String
	 * @param prenom : String
	 * @param college : String
	 * @param anneeEntreeCollege : String
	 */
	public void creerEtudiant(String nom, String prenom, College college, Date anneeEntreeCollege) {
		
		Connection conn = ConnectionJdbc.getInstance();
		
		
		
		try {
			
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT MAX(id) FROM etudiant");
			result.next();
			int id = result.getInt("max")+1;
			
			PreparedStatement state = conn.prepareStatement("INSERT INTO etudiant(id, nom, prenom, id_college, annee_entree_college, login, password ) VALUES (?,?,?,?,?,?,?) ");
			state.setInt(1, id);
			state.setString(2, nom);
			state.setString(3, prenom);
			state.setInt(4,college.numeroAcademique);
			state.setDate(5, anneeEntreeCollege);
			state.setString(6, prenom.substring(0, 1)+nom);
			state.setString(7, creerMotDePasse());
			state.executeUpdate();
			
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	

	/**
	 * Ajout d'un etudiant dans la BDD
	 * @param nom : String
	 * @param prenom : String
	 * @param college : String
	 * @param anneeEntreeCollege : String
	 * @param mail : String
	 * @param telephone : String
	 */
	public void creerEtudiant(String nom, String prenom, College college, Date anneeEntreeCollege, String mail ,String telephone) {
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT MAX(id) FROM etudiant");
			result.next();
			int id = result.getInt("max")+1;
			
			
			PreparedStatement state = conn.prepareStatement("INSERT INTO etudiant(id, nom, prenom, id_college, annee_entree_college, mail ,telephone ,login ,password ) VALUES (?,?,?,?,?,?,?,?,?) ");
			state.setInt(1, id);
			state.setString(2, nom);
			state.setString(3, prenom);
			state.setInt(4,college.numeroAcademique);
			state.setDate(5, anneeEntreeCollege);
			state.setString(6, mail);
			state.setString(7, telephone);
			state.setString(8, prenom.substring(0, 1)+nom);
			state.setString(9, creerMotDePasse());
			state.executeUpdate();
			
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	/**
	 * Supprime un étudiant de la BDD
	 * @param nom : String
	 * @param prenom : String
	 */
	public void supprimerEtudiant(String nom, String prenom) {
		
		Etudiant et = new Etudiant(nom, prenom);
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			
			Statement st = conn.createStatement();
			st.executeUpdate("DELETE FROM etudiant WHERE id = "+et.id);
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
		
		
	}
	
	/**
	 * Ajoute un college à la BDD
	 * @param nom : String
	 * @param adresse : String
	 * @param siteInternet : String
	 */
	public void creerCollege(String nom, String adresse, String siteInternet) {
		Connection conn = ConnectionJdbc.getInstance();
		Adresse ad = new Adresse(adresse);
		
		try {
			
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT MAX(numero_academique) FROM college");
			result.next();
			int id = result.getInt("max")+1;
			
			
			PreparedStatement state = conn.prepareStatement("INSERT INTO college(numero_academique, nom, site_internet, id_adr ) VALUES (?,?,?,?) ");
			state.setInt(1, id);
			state.setString(2, nom);
			state.setString(3, siteInternet);
			state.setInt(4,ad.id);
			state.executeUpdate();
			
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	/**
	 * Modifie le téléphone d'un étudiant dans la BDD
	 * @param telephone : String
	 * @param et : Etudiant
	 */
	public void modifierTelephoneEtudiant(String telephone, Etudiant et) {

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE etudiant SET telephone = '"+telephone+"' WHERE id = "+et.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Modifie le mail d'un étudiant dans la BDD
	 * @param mail : String
	 * @param et : String
	 */
	public void modifierMailEtudiant(String mail, Etudiant et) {
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE etudiant SET mail = '"+mail+"' WHERE id = "+et.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Modifie le collège d'un étudiant dans la BDD
	 * @param col : College
	 * @param et : Etudiant
	 */
	public void modifierCollegeEtudiant(College col, Etudiant et) {

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE etudiant SET id_college = "+col.numeroAcademique+" WHERE id = "+et.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifie le mot de passe d'un étudiant dans la BDD (mot de passe généré aléatoirement)
	 * @param et : Etudiant
	 */
	public void modifierPasswordEtudiant(Etudiant et) {
		String mdp = creerMotDePasse();

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE etudiant SET password = '"+mdp+"' WHERE id = "+et.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifie le téléphone d'un enseignant dans la BDD
	 * @param telephone : String
	 * @param ens : Enseignant
	 */
	public void modifierTelephoneEnseignant(String telephone, Enseignant ens) {

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET telephone = "+telephone+" WHERE id = "+ens.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Modifie le mail d'un enseignant dans la BDD
	 * @param mail : String
	 * @param ens : Enseignant
	 */
	public void modifierMailEnseignant(String mail, Enseignant ens) {

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET mail = "+mail+" WHERE id = "+ens.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Modifie le téléphone d'un college principal dans la BDD
	 * @param college : College
	 * @param ens : Enseignant
	 */
	public void modifierCollegePrincipalEnseignant(College college,Enseignant ens) {

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET id_college_principal = "+college.numeroAcademique+" WHERE id = "+ens.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifie le college secondaire d'un enseignant dans la BDD
	 * @param college : College
	 * @param ens : Enseignant
	 */
	public void modifierCollegeSecondaireEnseignant(College college, Enseignant ens) {

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET id_college_secondaire = "+college.numeroAcademique+" WHERE id = "+ens.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifie le mot de passe d'un enseignant dans la BDD
	 * @param ens : Enseignant
	 */
	public void modifierPasswordEnseignant(Enseignant ens) {
		String mdp = creerMotDePasse();

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET password = "+mdp+" WHERE id = "+ens.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Calcul les/la distances entre le domicile de l'enseignant et son/ses collèges d'affectation
	 * @param enseignant : Enseignant
	 * @return : double[], tableau des/de la distances entre le domicile de l'enseignant et son/ses collèges d'affectation
	 */
	public double[] calculerDistance(Enseignant enseignant) {
		double dist1 = enseignant.getAdresse().calculerDistance(enseignant.collegePrincipal.adresse);
		double[] dist= {dist1,0};
		if (enseignant.collegeSecondaire!=null) {
			double dist2 = enseignant.getAdresse().calculerDistance(enseignant.collegeSecondaire.adresse);
			dist[1]=dist2;
		}
		return dist;
	}
	
	
	/**
	 * Crée les données d'une adresse et les mets dans la BDD si l'adresse n'est pas déjà dans la BDD
	 * @param adresse : String adresse
	 */
	private void creerAdresse(String adresse) {
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {//Recherche de l'adresse
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT adresse FROM adresse WHERE adresse ILIKE '" + adresse+"'");
			
			if(!result.next()) {//Si l'adresse n'existe pas, on cherche ses coordonnées avec l'API geocoding google
				
				GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDTCMkXi0MF9HPQwvdgryfF1Zsqs4Ygebk").build();
				GeocodingResult[] results = null;
				try {
					results = GeocodingApi.geocode(context,adresse).await();
				} catch (ApiException | InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				double[] coord = {Double.parseDouble(gson.toJson(results[0].geometry.location.lat)),Double.parseDouble(gson.toJson(results[0].geometry.location.lng))};
				
				
				ResultSet result2 = state.executeQuery("SELECT MAX(id) FROM adresse");
				result2.next();
				int id = result2.getInt("max")+1;
				
				//On insert ces données
				PreparedStatement st = conn.prepareStatement("INSERT INTO adresse(id,adresse,lat,lon) VALUES(?,?,?,?)");
				st.setInt(1, id);
				st.setString(2, adresse);
				st.setDouble(3, coord[0]);
				st.setDouble(4, coord[1]);
				st.executeUpdate();
				
				st.close();
			}			
			state.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return : String, mot de passe généré aléatoirement
	 */
	private String creerMotDePasse() {
		Random r = new Random();
		String mdp="";
		//Alphabet dans lequel on choisit les lettres du mdp
		String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9"};
		int n = alphabet.length;
		for(int i=1;i<=12;i++) {//Le mdp fait 12 lettres de longueur
			mdp+=alphabet[r.nextInt(n)];
		}
		return(mdp);
	}
	
	
}

