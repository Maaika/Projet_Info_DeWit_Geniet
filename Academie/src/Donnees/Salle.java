package Donnees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ConnectionJdbc.ConnectionJdbc;

/**
 * 
 * @author Florent et Apolline
 * Classe Salle, implémente les données relatives aux salles
 */

public class Salle {
	
	public String nom;
	public int nombreDePlace;
	
	/**
	 * Constructeur de Salle, recherche les données dans la BDD avec l'id de la salle.
	 * @param id : int, id de la salle dans la BDD
	 */
	public Salle (int id) {
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			//Recherche dans la BDD
			
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM salle WHERE id = " + id);
			result.next();
			
			//Collecte des données
			this.nom = result.getString("nom");
			this.nombreDePlace = result.getInt("nb_place");
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
	}

}
