package Donnees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ConnectionJdbc.ConnectionJdbc;

/**
 * 
 * @author Florent et Apolline
 * Classe Matiere, implémente les données relatives aux matières
 */
public class Matiere {
	
	public final int id;
	public final String nom;
	public List<Enseignant> enseignantsMatiere;
	public Salle salle;
	
	/**
	 * Constructeur de matiere, recherche les informations dans la BDD grâce à l'id de la matiere.
	 * @param id : int, id de la matiere dans la BDD
	 */
	public Matiere (int id) {
		Connection conn = ConnectionJdbc.getInstance();
		
		String nom = "";
		this.enseignantsMatiere = new ArrayList<Enseignant>();
		this.id = id;
		try {//Recherche dans la BDD
			
			
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM matiere WHERE id = " + id);
			result.next();
			
			//Collecte des données
			nom = result.getString("nom");
			this.salle = new Salle(result.getInt("id_salle"));
			
			ResultSet result2 = state.executeQuery("SELECT id FROM enseignant WHERE id_matiere = " + id);
			Enseignant ens;
			while(result2.next()) {
				ens = new Enseignant(result2.getInt("id"));
				this.enseignantsMatiere.add(ens);
			}
			
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		this.nom = nom;
	}
	
	/**
	 * Constructeur de matiere, recherche les informations dans la BDD grâce au nom de la matiere, supposé unique.
	 * @param nom : nom de la matière
	 */
	public Matiere (String nom) {
		//Ce constructeur fonctionne exactement comme le précédent.
		Connection conn = ConnectionJdbc.getInstance();
		
		this.nom = nom;
		
		int id = 0;
		this.enseignantsMatiere = new ArrayList<Enseignant>();
		try {
			
			
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM matiere WHERE nom = " + nom);
			result.next();
			
			
			id = result.getInt("id");
			this.salle = new Salle(result.getInt("id_salle"));
			
			ResultSet result2 = state.executeQuery("SELECT id FROM enseignant WHERE id_matiere = " + id);
			Enseignant ens;
			while(result2.next()) {
				ens = new Enseignant(result2.getInt("id"));
				this.enseignantsMatiere.add(ens);
			}
			
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		this.id = id;
		
	}

	/**
	 * Renvoie la moyenne des notes obtenues dans la matière
	 * @return : double, moyenne de la matiere
	 */
	public double moyenne() {
		Connection conn = ConnectionJdbc.getInstance();
		double valeur = 0;
		int n = 0;
	
		try {
			
			//recherche des notes dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM note WHERE id_matiere = " + this.id);
			result.next();
			
			//Somme des notes
			while(result.next()) {
				valeur += result.getDouble("valeur");
				n+=1;
			}
			
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		if (n==0) {//Si il n'y a pas eu de notes, on renvoie -1
			return(-1);
		}
		
		else {
			return(valeur/n);
		}
		
	}

}