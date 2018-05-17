package Donnees;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import ConnectionJdbc.ConnectionJdbc;

/**
 * 
 * @author Florent et Apolline
 * Classe Etudiant, implémente les données relatives aux étudiants
 */
public class Etudiant {
	
	/*
	 * Attributes
	 */
	public final int id;
	public final String nom;
	public final String prenom;
	public String mail;
	public String telephone;
	public Date anneeEntreeCollege;
	public College college;
	
	
	/*
	 * Setters
	 */
	/**
	 * 
	 * @param mail : String, nouveau mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * 
	 * @param telephone : String, nouveau téléphone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/*
	 * Constructors
	 */
	/**
	 * Constructeur de l'étudiant à partir de son indice dans la BDD
	 * @param id : int, id de l'étudiant dans la BDD
	 */
	public Etudiant(int id) {
		Connection conn = ConnectionJdbc.getInstance();
		
		String nom = "";
		String prenom ="";
		this.id = id;
		
		try {
			
			//Recherche dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM etudiant WHERE id = " + id);
			result.next();
			
			//Collecte des données
			nom = result.getString("nom");
			prenom = result.getString("prenom");
			this.mail = result.getString("mail");
			this.telephone = result.getString("telephone");
			this.anneeEntreeCollege = result.getDate("annee_entree_college");
			this.college = new College(result.getInt("id_college"));
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		this.nom = nom;
		this.prenom = prenom;
	}
	
	/**
	 * Constructeur d'etudiant recherchant les données dans la BDD grâce au nom et au prenom, avec choix de l'utilisteur en
	 * cas d'étudiants avec les mêmes prénoms et noms
	 * @param nom : String, nom de l'étudiant
	 * @param prenom : String, nom de l'étudiant
	 */
	public Etudiant(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
		
		Connection conn = ConnectionJdbc.getInstance();
		
		//initialisation du nombre d'étudiants trouvés et de la liste de ces étudiants
		int n = 0;
		List<Integer> listeEtudiants = new ArrayList<Integer>();
		
		try {
			
			//Recherche dans la BDD
			//On créé un statement par ResultSet ouverts simultanéments
			Statement state = conn.createStatement();
			Statement st = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM etudiant WHERE nom ILIKE '" + nom + "' AND prenom ILIKE '" + prenom + "'");
			ResultSet result2;
			
			System.out.println("n°       Nom       Prenom       College");
			
			while(result.next()) {//On affiche les résultats, avec leurs collèges (result2)
				result2 = st.executeQuery("SELECT nom FROM college WHERE numero_academique = " + result.getInt("id_college"));
				result2.next();
				n+=1;
				listeEtudiants.add(result.getInt("id"));
				System.out.println(n + " : " + result.getString("nom")+"   "+ result.getString("prenom")+"   "+result2.getString("nom"));
			}
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		if (n==0) {//Si on a rien truvé, on créé un étudiant vide
			System.out.println("Elève inconnu");

			this.id=0;
		}
		
		else {//Sinon on demande à l'utilisateur de choisir
			Scanner sc = new Scanner(System.in);
			int num = -1;
			System.out.println("n° de l'étudiant?");
			while(0>num || num>n-1){
				num = sc.nextInt()-1;
				sc.nextLine();
			}
			
			//On retrouve le constructeur précédent
			this.id = listeEtudiants.get(num);
			
			try {
				
				
				Statement state = conn.createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM etudiant WHERE id = " + this.id);
				result.next();
				
				
				nom = result.getString("nom");
				prenom = result.getString("prenom");
				this.mail = result.getString("mail");
				this.telephone = result.getString("telephone");
				this.anneeEntreeCollege = result.getDate("annee_entree_college");
				this.college = new College(result.getInt("id_college"));
				
				result.close();
				state.close();
			}
			catch(Exception e){
				e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
			}
			
		
		
	}
	}
	
	/*
	 * Methods
	 */
	
	/**
	 * Calcul la moyenne de l'élève
	 * @return : double, moyenne de l'élève
	 */
	public double moyenneGenerale() {
		Connection conn = ConnectionJdbc.getInstance();
		int n = 0;
		double somme = 0;
		
		try {//Recherche des notes de l'élève dans la BDD
			Statement state = conn.createStatement();
			String s = "SELECT valeur FROM note WHERE id_etudiant = " + this.id;
				
			ResultSet result = state.executeQuery(s);
				
			//Moyenne de ces notes	
			while(result.next()) {
				n+=1;
				somme+=result.getDouble("valeur");
			}
				
			result.close();
			state.close();			
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		if (n==0) {//Si l'élève n'est pas noté, on renvoie -1
			return(-1);
		}
		else {//Sinon on renvoie la moyenne
			return(somme/n);
		}
	}
	
	/**
	 * Ecrit la fiche signalétique de l'élève dans un fichier
	 */
	public void ficheSignaletique() {
		
		String filePath = "/Academie/fiche_"+this.prenom+"_"+this.nom; // chemin relatif vers le fichier
		Path logFile = Paths.get(filePath).toAbsolutePath();//On le transforme en chemin absolu
		if (!Files.exists(logFile)) { // si le fichier n’existe pas on le cree
			try {
				Files.createFile(logFile);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Ecriture dans le fichier
		String s = "";
		try (BufferedWriter writer = Files.newBufferedWriter(logFile,StandardCharsets.UTF_8, StandardOpenOption.WRITE)) { // buffer en ecriture (ecrase l’existant), encodage UTF8
			s+="Nom : "+this.nom+"\n";
			s+="Prenom : "+this.prenom+"\n";
			s+="Mail : "+this.mail+"\n";
			s+="Téléphone : "+this.telephone+"\n";
			s+="Année d'entrée au collège : "+this.anneeEntreeCollege+"\n";
			s+="College : " + this.college.nom+"\n";
			
			writer.write(s);
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	@Override
	public String toString() {
		String s = "L'eleve : " + this.prenom + " " + this.nom + " est entre(e) au college " + this.college + " en " + this.anneeEntreeCollege + "\n";
		s += "Son mail est : " + this.mail + " et son numero de telephone est : " + this.telephone + "\n";
		return s;
	}
	
	/**
	 * 
	 * @return String, liste des matières de l'étudiant
	 */
	public String voirMatieres() {
		Connection conn = ConnectionJdbc.getInstance();
		List<String> matieres = new ArrayList<String>();
		
		try {
			Statement state = conn.createStatement();
			String st = "SELECT matiere.nom FROM matiere JOIN matiere_etudiant ON matiere.id = matiere_etudiant.id_matiere";
				
			ResultSet result = state.executeQuery(st);
				
				
			while(result.next()) {
				matieres.add(result.getString("nom"));
			}
				
			result.close();
			state.close();			
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		String s = "L'eleve a pour matieres : " + matieres;
		
		return s;
	}


}
