package User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ConnectionJdbc.ConnectionJdbc;
import Donnees.Departement;
import Donnees.Enseignant;

/**
 * 
 * @author Florent & Apolline
 * Classe UserResponsable, implémente les actions réalisables par un responsable.
 * 
 */
public class UserResponsable {
	public UserEnseignant userEnseignant;
	public Departement departement;
	
	/*
	 * Constructor
	 */
	/**
	 * Constructeur de UserResponsable
	 * @param identifiant : String
	 * @param mdp : String
	 */
	public UserResponsable(String identifiant, String mdp) {
		this.userEnseignant = new UserEnseignant(identifiant, mdp);
	}
	
	/*
	 * Method
	 */
	/**
	 * Ajoute un enseignant au département dans la BDD
	 * @param nom : String
	 * @param prenom : String
	 * @param secondaire : boolean, true si le departement est secondaire pour l'enseignant
	 */
	public void ajouterEnseignant(String nom, String prenom, boolean secondaire) {
		Enseignant ens = new Enseignant(nom, prenom);
		departement.listeEnseignants.add(ens);
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			if(secondaire) {
				state.executeUpdate("UPDATE enseignant SET id_dept_secondaire = "+this.departement.id+" WHERE id = "+ens.id);
			}
			else {
				state.executeUpdate("UPDATE enseignant SET id_dept_principal = "+this.departement.id+" WHERE id = "+ens.id);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime un enseignant du département dans la BDD
	 * @param nom : String
	 * @param prenom : String
	 */
	public void supprimerEnseignant(String nom, String prenom) {
		Enseignant ens = new Enseignant(nom, prenom);
		departement.listeEnseignants.remove(ens);
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM enseignant WHERE id = "+ens.id);
			result.next();
			boolean secondaire = result.getInt("id_dept_secondaire")==this.departement.id;
			boolean principal = result.getInt("id_dept_principal")==this.departement.id;
			if(secondaire) {
				state.executeUpdate("UPDATE enseignant SET id_dept_secondaire = -1 WHERE id = "+ens.id);
			}
			if(principal) {
				state.executeUpdate("UPDATE enseignant SET id_dept_principal = -1 WHERE id = "+ens.id);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Affiche la moyenne du département
	 */
	public void afficherInfos() {
		System.out.println("La moyenne du departement est de : " + departement.moyenne());
	}
}

