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
 * Classe Enseignant, implémente les données relatives aux enseignants
 */
public class Enseignant {
	
	/*
	 * Attributes
	 */
	// Personnal attributes
	public final String nom;
	public final String prenom;
	private String telephone;
	private String mail;
	protected Adresse adresse;
	
	// Professional attributes
	public final int id;
	public String matiere;
	protected final Date datePriseDeFonction;
	public College collegePrincipal;
	public College collegeSecondaire;
	
	/*
	 * Getter and Setters
	 */
	
	/**
	 * 
	 * @return : Adresse, adresse de l'enseignant
	 */
	public Adresse getAdresse() {
		return adresse;
	}
	
	/**
	 * 
	 * @param telephone : String, telephone de l'enseignant
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 
	 * @param mail : String, mail de l'enseignant
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * 
	 * @param adresse : Nouvelle adresse de l'enseignant
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	/**
	 * Constructeur d'enseignant recherchant les données dans la BDD grâce à l'id de l'enseignant
	 * @param id : int, id de l'enseignant dans la BDD
	 */
	public Enseignant(int id) {
		Connection conn = ConnectionJdbc.getInstance();
		
		String nom = "";
		String prenom ="";
		Date priseFonction = new Date();
		this.id = id;
		try {
			
			//Recherche dans la BDD
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM enseignant WHERE id = " + id);
			result.next();
			
			//Recupération des données
			nom = result.getString("nom");
			prenom = result.getString("prenom");
			this.mail = result.getString("mail");
			this.telephone = result.getString("telephone");
			priseFonction = result.getDate("prise_de_fonction");
			this.collegePrincipal = new College(result.getInt("id_college_principal"));
			int id_sec = result.getInt("id_college_secondaire");
			if ( !result.wasNull()) {//On ne récupère un collège secondaire que si il y en avait un
				this.collegeSecondaire = new College(id_sec);
			}
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		
		this.nom = nom;
		this.prenom = prenom;
		this.datePriseDeFonction = priseFonction;
	}
	
	
	/**
	 * Constructeur d'enseignant recherchant les données dans la BDDgrâce au nom et au prenom, avec choix de l'utilistaeur en
	 * cas d'enseignants avec les mêmes prénoms et nom
	 * @param nom : String, nom de l'enseignant
	 * @param prenom : String, prenom de l'enseignant
	 */
	public Enseignant(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
		
		Connection conn = ConnectionJdbc.getInstance();
		
		//Nombre d'enseignants trouvés et liste de leurs indices
		int n = 0;
		List<Integer> listeEnseignants = new ArrayList<Integer>();
		
		try {//recherche dans la BDD
			
			//On créé un statement par requête car un statement ne peut avoir qu'un resultSet ouvert à la fois.
			Statement state = conn.createStatement();
			Statement state2 = conn.createStatement();
			Statement state3 = conn.createStatement();
			//recherche des enseignants
			ResultSet result = state.executeQuery("SELECT * FROM enseignant WHERE nom ILIKE '" + nom + "' AND prenom ILIKE '" + prenom+"'");
			ResultSet result2;
			ResultSet result3;
			
			String s;
			
			System.out.println("n°       Nom       Prenom       College Principal       College Secondaire       Prise de fonction");
			
			while(result.next()) {
				//Recherche du collège principal et secondaire de l'enseignant
				result2 = state2.executeQuery("SELECT nom FROM college WHERE numero_academique = " + result.getInt("id_college_principal"));
				result3 = state3.executeQuery("SELECT nom FROM college WHERE numero_academique = " + result.getInt("id_college_secondaire"));
				result2.next();
		
				n+=1;
				s=n + " : " + result.getString("nom")+"   "+ result.getString("prenom")+"   "+result2.getString("nom")+"   ";
				if(result3.next()) {//le collège secondaire peut ne pas exister
					s+=result3.getString("nom");
				}
				else {
					s+="                                ";
				}
				s+=result.getDate("prise_de_fonction");
				listeEnseignants.add(result.getInt("id"));
				//On affiche les résultats
				System.out.println(s);
			}
			
			result.close();
			state.close();
		}
		catch(Exception e){
			e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
		}
		if (n==0) {//Si on a rien trouvé, on créé un enseigannt vide.
			System.out.println("Enseignant inconnu");

			this.id=0;
			this.datePriseDeFonction = new Date();
		}
		
		else {//On demande à l'utilisateur de choisir
			Scanner sc = new Scanner(System.in);
			int num = -1;
			System.out.println("n° de l'enseignant?");
			while(0>num || num>n-1){
				num = sc.nextInt()-1;
				sc.nextLine();
			}
			
			//On retrouve ici le constructeur précédent
			this.id = listeEnseignants.get(num);
			
			Date priseFonction = new Date();
			
			try {
				
				
				Statement state = conn.createStatement();
				ResultSet result = state.executeQuery("SELECT * FROM enseignant WHERE id = " + id);
				result.next();
				
				
				nom = result.getString("nom");
				prenom = result.getString("prenom");
				this.mail = result.getString("mail");
				this.telephone = result.getString("telephone");
				priseFonction = result.getDate("prise_de_fonction");
				this.collegePrincipal = new College(result.getInt("id_college_principal"));
				int id_sec = result.getInt("id_college_secondaire");
				if ( !result.wasNull()) {
					this.collegeSecondaire = new College(id_sec);
				}
				
				this.adresse = new Adresse(result.getInt("id_adresse"));
				
				result.close();
				state.close();
			}
			catch(Exception e){
				e.printStackTrace(); // pour gerer les erreurs (pas de pilote, base inexistante, etc.)
			}
			
			this.datePriseDeFonction = priseFonction;
		
		
	}
	}
	
	/**
	 * Ecrit dans un fichier la fiche signalétique d'un étudiant
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
			s+="Prise de fonction : "+this.datePriseDeFonction+"\n";
			s+="College principal : " + this.collegePrincipal.nom+"\n";
			s+="College secondaire : "+this.collegeSecondaire.nom+"\n";
			
			writer.write(s);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	

}
