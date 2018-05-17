package Donnees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ConnectionJdbc.ConnectionJdbc;

/**
 * 
 * @author Florent et Apolline
 * Classe Note, ne contient que son constructeur, qui ajoute une note à la BDD
 */
public class Note {
	
	/**
	 * Constructeur de Note, ajoute une note à la BDD
	 * @param valeur : double, valeur de la note
	 * @param matiere : Matiere, matière de la note
	 * @param etudiant : Etudiant, etudiant ayant reçu la note
	 */
	public Note(double valeur,Matiere matiere,Etudiant etudiant) {
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {//On commence par vérifier que la note n'a pas déjà été attribuée
			
			
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM note WHERE valeur = "+valeur+" AND id_matiere = "+matiere.id+" AND id_etudiant="+etudiant.id);
			if (result.next()) {
				System.out.println("Note déjà attribuée");
			}
			else {//Si la note n'a pas été attribuée, on l'ajoute
				state.executeUpdate("INSERT INTO note VALUES("+valeur+","+etudiant.id+","+matiere.id+")");
			}
			
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}

	}

}

