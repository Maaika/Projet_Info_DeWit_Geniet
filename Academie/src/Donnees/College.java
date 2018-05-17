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
 * Classe College, implémente les données relatives aux collèges (nom,numéro académique, adresse, site internet,
 * liste d'étudiants, liste d'enseignants...)
 */
public class College {
	/*
	 * Attributs
	 */
	public final String nom;
	public final int numeroAcademique;//Sert aussi d'identifiant dans la BDD
	public final Adresse adresse;
	public String siteInternet;
	
	/**
	 * Premier constructeur de collège, permet de creer un collège vide.
	 */
	public College() {
		this.nom = "";
		this.numeroAcademique = 0;
		this.adresse = new Adresse ();
	}
	
	/**
	 * Constructeur allant chercher les informations du collège dans la BDD grâce à son indice.
	 * @param numeroAcademique : int, id du collège dans la BDD
	 */
	public College(int numeroAcademique) {
		
		Connection conn = ConnectionJdbc.getInstance();
		this.numeroAcademique = numeroAcademique;
		
		//On initialise les valeurs servant pour les attributs final, au cas où une erreur surviendrait
		String nom = "";
		double[] coord = {0,0};
		Adresse adresse = new Adresse("adresse inconnue",coord);
		String siteInternet = "";
		
		try {
			
			//Recherche dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM college WHERE numero_academique = " + numeroAcademique);
			result.next();
			
			//On récupère les données
			nom = result.getString("nom");
			adresse = new Adresse(result.getInt("id_adr"));
			siteInternet = result.getString("site_internet");
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		this.nom = nom;
		this.adresse = adresse;
		this.siteInternet = siteInternet;
		
		}
	
	/**
	 * Constructeur recherchant les données d'un collège dans la BDD grâce à son nom.
	 * Si plusieurs collèges ont le même nom, on demandera à l'utilisateur de choisir.
	 * @param nom : String, nom du collège
	 */
	public College(String nom) {
		Connection conn = ConnectionJdbc.getInstance();
		
		int n = 0;//nombre de collèges trouvés
		List<Integer> listeColleges = new ArrayList<Integer>();//liste des indices des collèges trouvés
		
		try {
			
			//On recherche tout les collèges ayant ce nom
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM college WHERE nom ILIKE " +"'"+ nom+"'");
			
			//On affiche les Collèges trouvés
			System.out.println("n°       numéro académique");
			while(result.next()) {
				n+=1;
				listeColleges.add(result.getInt("numero_academique"));
				System.out.println(n + " : " + result.getInt("numero_academique")+"    "+nom);
			}
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		if (n==0) {//Si on a pas trouvé de collèges, on en créé un vide
			System.out.println("College inconnu");
			this.nom = "";
			this.numeroAcademique = 0;
			this.adresse = new Adresse ();
		}
		
		else {//Si on a trouvé au moins un collège, on demande à l'utilisateur de choisir
			Scanner sc = new Scanner(System.in);//On ne ferme pas sc car cela ferme aussi System.in, ce qui créé des erreurs dans le main
			System.out.println("n° du college?");
			int num = -1;
			while(0>num || num>n-1){//On demande un numéro correspondant bien à un collège
				num = sc.nextInt()-1;
				sc.nextLine();
			}
			int numeroAcademique = listeColleges.get(num);
			
			//Ici on retrouve le constructeur précédent
			this.numeroAcademique = numeroAcademique;
			
			this.nom = nom;
			double[] coord = {0,0};
			Adresse adresse = new Adresse("adresse inconnue",coord);
			String siteInternet = "";
			
			try {
				
				
				Statement state = conn.createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM college WHERE numero_academique = " + numeroAcademique);
				result.next();
				
				
				adresse = new Adresse(result.getInt("id_adr"));
				siteInternet = result.getString("site_internet");
				
				result.close();
				state.close();
			}
			catch(Exception e){
				e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
			}
			
			this.adresse = adresse;
			this.siteInternet = siteInternet;
		}
		
	}
			
			
	//Méthodes
	
	/**
	 * Recherche dans la BDD la liste des étudiants du collège
	 * 
	 * @return : List<Etudiant>, liste des étudiants du collège
	 */
	public List<Etudiant> listeEtudiant(){
		Connection conn = ConnectionJdbc.getInstance();
		List<Etudiant> l = new ArrayList<Etudiant>();
		Etudiant e;
		
		try {
			
			//On recherche les étudiants dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT id FROM etudiant WHERE id_college = " + this.numeroAcademique);
			
			//On les ajoute à la liste
			while(result.next()) {
				e = new Etudiant(result.getInt("id"));
				l.add(e);
			}
			
			result.close();
			state.close();
		}
		catch(Exception ex){
			ex.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		return l;
		
	}
	
	/**
	 * Recherche dans la BDD les enseignants qui travaillent dans le collège (en tant que collège principal ou secondaire)
	 * 
	 * @return : List<Enseignant>, liste des enseignants du collège
	 */
	public List<Enseignant> listeEnseignant(){
		//La fonction marche comme pour la précédente
		Connection conn = ConnectionJdbc.getInstance();
		List<Enseignant> l = new ArrayList<Enseignant>();
		Enseignant e;
		
		try {
			
			
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT id FROM enseignant WHERE id_college_principal = " + this.numeroAcademique + "OR id_college_secondaire = " + this.numeroAcademique);
			
			
			while(result.next()) {
				e = new Enseignant(result.getInt("id"));
				l.add(e);
			}
			
			result.close();
			state.close();
		}
		catch(Exception ex){
			ex.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		return l;
	}
	
}
	 
	
	


	
