package Donnees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ConnectionJdbc.ConnectionJdbc;

/**
 * 
 * @author Florent et Apolline
 * Classe Departement, impl�mente les donn�es relatives aux d�partements (Coll�ge,liste des enseignants, responsable...)
 */
public class Departement {
	
	
	public final int id;//id dans la BDD
	public College college;
	public List<Enseignant> listeEnseignants;
	public Enseignant responsable;//responsable du departement
	
	/**
	 * Constructeur de �partement recherchant les informations dans la BDD gr�ce � son id
	 * @param id : int, indice dans la BDD
	 */
	public Departement(int id) {
		this.id = id;
		Connection conn = ConnectionJdbc.getInstance();
		this.listeEnseignants = new ArrayList<Enseignant>();
		
		
		try {
			
			//Recherche dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM departement WHERE id = " + id);
			result.next();
			
			//Collecte des donn�es
			this.college = new College(result.getInt("id_college"));
			this.responsable = new Enseignant(result.getInt("id_responsable"));
			
			// recherche des enseignants du d�partement
			ResultSet result2 = state.executeQuery("SELECT id FROM enseignant WHERE id_dept_principal = "+id+" OR id_dept_secondaire = "+id);
			Enseignant ens;
			while(result2.next()) {//ajout des enseignants dans la liste
				ens = new Enseignant(result2.getInt("id"));
				this.listeEnseignants.add(ens);
			}
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
	}
	
	/**
	 * Calcul la moyenne du d�partement.
	 * @return : double, moyenne du d�partement
	 */
	public double moyenne() {
		Connection conn = ConnectionJdbc.getInstance();
		int n = 0;
		double somme = 0;
		
		try {
			/**
			 * recherche des notes des �l�ves du coll�ge dans les mati�res enseign�es par les enseignants
			 */
			for (Enseignant ens : this.listeEnseignants) {
				Statement state = conn.createStatement();
				String s = "SELECT n.valeur FROM note n JOIN matiere m ON n.id_matiere = m.id";
				s+="JOIN enseignant e ON e.id_matiere = m.id ";
				s+="JOIN etudiant et ON et.id = n.id_etudiant";
				s+="JOIN college c ON c.numero_academique = et.id_college";
				s+="WHERE e.id = "+ ens.id + " AND c.numero_academique = "+this.college.numeroAcademique;
				
				ResultSet result = state.executeQuery(s);
				
				//On fait la moyenne
				while(result.next()) {
					n+=1;
					somme+=result.getDouble("valeur");
				}
				
				result.close();
				state.close();
			}
			
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		if (n==0) {
			return(-1);
		}
		else {
			return(somme/n);
		}
	}
}
