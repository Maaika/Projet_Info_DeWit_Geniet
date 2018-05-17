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
 * Classe College, impl�mente les donn�es relatives aux coll�ges (nom,num�ro acad�mique, adresse, site internet,
 * liste d'�tudiants, liste d'enseignants...)
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
	 * Premier constructeur de coll�ge, permet de creer un coll�ge vide.
	 */
	public College() {
		this.nom = "";
		this.numeroAcademique = 0;
		this.adresse = new Adresse ();
	}
	
	/**
	 * Constructeur allant chercher les informations du coll�ge dans la BDD gr�ce � son indice.
	 * @param numeroAcademique : int, id du coll�ge dans la BDD
	 */
	public College(int numeroAcademique) {
		
		Connection conn = ConnectionJdbc.getInstance();
		this.numeroAcademique = numeroAcademique;
		
		//On initialise les valeurs servant pour les attributs final, au cas o� une erreur surviendrait
		String nom = "";
		double[] coord = {0,0};
		Adresse adresse = new Adresse("adresse inconnue",coord);
		String siteInternet = "";
		
		try {
			
			//Recherche dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM college WHERE numero_academique = " + numeroAcademique);
			result.next();
			
			//On r�cup�re les donn�es
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
	 * Constructeur recherchant les donn�es d'un coll�ge dans la BDD gr�ce � son nom.
	 * Si plusieurs coll�ges ont le m�me nom, on demandera � l'utilisateur de choisir.
	 * @param nom : String, nom du coll�ge
	 */
	public College(String nom) {
		Connection conn = ConnectionJdbc.getInstance();
		
		int n = 0;//nombre de coll�ges trouv�s
		List<Integer> listeColleges = new ArrayList<Integer>();//liste des indices des coll�ges trouv�s
		
		try {
			
			//On recherche tout les coll�ges ayant ce nom
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM college WHERE nom ILIKE " +"'"+ nom+"'");
			
			//On affiche les Coll�ges trouv�s
			System.out.println("n�       num�ro acad�mique");
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
		if (n==0) {//Si on a pas trouv� de coll�ges, on en cr�� un vide
			System.out.println("College inconnu");
			this.nom = "";
			this.numeroAcademique = 0;
			this.adresse = new Adresse ();
		}
		
		else {//Si on a trouv� au moins un coll�ge, on demande � l'utilisateur de choisir
			Scanner sc = new Scanner(System.in);//On ne ferme pas sc car cela ferme aussi System.in, ce qui cr�� des erreurs dans le main
			System.out.println("n� du college?");
			int num = -1;
			while(0>num || num>n-1){//On demande un num�ro correspondant bien � un coll�ge
				num = sc.nextInt()-1;
				sc.nextLine();
			}
			int numeroAcademique = listeColleges.get(num);
			
			//Ici on retrouve le constructeur pr�c�dent
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
			
			
	//M�thodes
	
	/**
	 * Recherche dans la BDD la liste des �tudiants du coll�ge
	 * 
	 * @return : List<Etudiant>, liste des �tudiants du coll�ge
	 */
	public List<Etudiant> listeEtudiant(){
		Connection conn = ConnectionJdbc.getInstance();
		List<Etudiant> l = new ArrayList<Etudiant>();
		Etudiant e;
		
		try {
			
			//On recherche les �tudiants dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT id FROM etudiant WHERE id_college = " + this.numeroAcademique);
			
			//On les ajoute � la liste
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
	 * Recherche dans la BDD les enseignants qui travaillent dans le coll�ge (en tant que coll�ge principal ou secondaire)
	 * 
	 * @return : List<Enseignant>, liste des enseignants du coll�ge
	 */
	public List<Enseignant> listeEnseignant(){
		//La fonction marche comme pour la pr�c�dente
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
	 
	
	


	
