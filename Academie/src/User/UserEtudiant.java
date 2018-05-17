package User;

import java.sql.Connection;
import java.sql.Statement;

import ConnectionJdbc.ConnectionJdbc;
import Donnees.College;
import Donnees.Etudiant;

/**
 * 
 * @author Florent & Apolline
 * Classe UserEtudiant, implémente les actions réalisables par un étudiant.
 * 
 */
public class UserEtudiant extends User {
	/*
	 *  Attributes
	 */
	public College college;
	public Etudiant etudiant;
	
	/*
	 * Constructor
	 */
	
	/**
	 * Constructeur de UserEtudiant
	 * @param id : String
	 * @param mdp : String
	 */
	public UserEtudiant(String id, String mdp) {
		super(id, mdp);
	}
	
	/*
	 * Methods
	 */
	
	/**
	 * Modifie le téléphone de l'étudiant
	 * @param numero : String
	 */
	public void modifierTelephone(String numero) {
		this.etudiant.setTelephone(numero);
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE etudiant SET telephone = "+numero+" WHERE id = "+this.etudiant.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifie le mail de l'étudiant
	 * @param mail : String
	 */
	public void modifierMail(String mail) {
		this.etudiant.setMail(mail);

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE etudiant SET mail = "+mail+" WHERE id = "+this.etudiant.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche les matières de l'étudiant
	 */
	public void afficherMatieres() {
		System.out.println("Les matieres de l'eleve sont : " + etudiant.voirMatieres());
	}
	
	/**
	 * Affiche les informations de l'étudiant
	 * @param bool : boolean, définit si on veut voir les matières de l'étudiant ou non
	 */
	public void afficherInfo(boolean bool) {
		if(bool) {
			System.out.println(etudiant.toString());
			afficherMatieres();
		}
		else {
			System.out.println(etudiant.toString());
		}
		
	}
	
}
