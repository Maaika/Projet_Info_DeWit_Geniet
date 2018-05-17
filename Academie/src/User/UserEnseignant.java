package User;

import java.sql.Connection;
import java.sql.Statement;

import ConnectionJdbc.ConnectionJdbc;
import Donnees.Adresse;
import Donnees.Enseignant;
import Donnees.Etudiant;
import Donnees.Matiere;
import Donnees.Note;

/**
 * 
 * @author Florent & Apolline
 * Classe UserEtudiant, implémente les actions réalisables par un étudiant.
.
 *
*/
public class UserEnseignant extends User {
	/*
	 * Attributes
	 */
	public Enseignant enseignant;
	
	/*
	 * Constructor
	 */
	/**
	 * Constructeur de UserEnseignant
	 * @param identifiant : String
	 * @param mdp : String
	 */
	public UserEnseignant(String identifiant, String mdp) {
		super(identifiant, mdp);
	}

	/*
	 * Methods
	 */
	/**
	 * Ajoute une note à un étudiant dans une matière
	 * @param etudiant : Etudiant
	 * @param matiere : Matiere
	 * @param valeur : double
	 */
	public void ajouterNote (Etudiant etudiant, Matiere matiere, double valeur) {
		 Note note = new Note(valeur, matiere, etudiant);
	}
	
	/**
	 * Affiche les données d'un enseignant
	 */
	public void afficherDonnees() {
		System.out.println(this.enseignant.toString());
	}
	
	/**
	 * Modifie le téléphone de l'enseignant
	 * @param numero : String
	 */
	public void modifierTelephone(String numero) {
		this.enseignant.setTelephone(numero);
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {//mise à jour de la BDD
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET telephone = "+numero+" WHERE id = "+this.enseignant.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifie le mail de l'enseignant
	 * @param mail : String
	 */
	
	public void modifierMail(String mail) {
		this.enseignant.setMail(mail);

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET mail = "+mail+" WHERE id = "+this.enseignant.id);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Modifie l'adresse de l'enseignant
	 * @param adresse : String
	 */
	
	public void modifierAdresse(String ad) {
		Adresse adresse = new Adresse(ad);
		this.enseignant.setAdresse(adresse);

		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			state.executeUpdate("UPDATE enseignant SET id_adresse = "+adresse.id+" WHERE id = "+this.enseignant.id);
			
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
	}
	
	
}