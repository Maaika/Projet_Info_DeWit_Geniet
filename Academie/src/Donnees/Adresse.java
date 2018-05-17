package Donnees;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import ConnectionJdbc.ConnectionJdbc;
/**
 * Cette classe définit les objets de type adresse. 
 * 
 * @author Florent Geniet, Apolline De Wit
  */
public class Adresse {
	
	/*
	 * Attributs
	 */
	public final int id;//indice dans la base de données
	public String adresse;
	protected double[] coordonnees;//latitude & longitude
	
	// Constructeur simple d'une adresse : si aucun paramètre n'est rentré, on ne l'a créé pas. 
	public Adresse() {
		this.id = 0;
	}
	
	/**
	 * Constructeur d'adresse avec l'indice de la BDD.
	 * @param id_adr : int, on rentre l'indice de l'adresse dans la BDD
	 */
	public Adresse(int id_adr) {
		this.id = id_adr;
		Connection conn = ConnectionJdbc.getInstance();	
		
		try{
			Statement state = conn.createStatement();		 
			ResultSet result = state.executeQuery("SELECT * FROM adresse WHERE id = " + id_adr);
			result.next();
			
			this.adresse = result.getString("adresse");
			double[] coord = {result.getDouble("lat"),result.getDouble("lon")};
			this.coordonnees = coord;
			
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
		//On ferme la connection si elle a été ouverte
		finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * Constructeur d'adresse ne prenant pas en compte la BDD (utile pour creer des adresses n'existant pas) 
	 * 
	 * @param adresse : String, nom de l'adresse
	 * @param coord : double [], coordonnées de l'adresse
	 */
	public Adresse(String adresse, double[] coord) {
		this.id=0;
		this.adresse = adresse;
		this.coordonnees=coord;
	}
	
	/**
	 * Constructeur d'adresse recherchant une adresse dans la BDD avec son nom, et l'y ajoutant si elle
	 * n'y est pas. Les coordonnées sont créées grâce à l'API geocoding google.
	 * @param adresse : String, nom de l'adresse
	 */
	public Adresse(String adresse) {
		this.adresse = adresse;
		int id = 0;
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT adresse FROM adresse WHERE adresse ILIKE '" + adresse+"'"); //On recherche une adresse ayant ce nom
			
			if(!result.next()) { //Si l'adresse n'existe pas, on recherche ses coordonnées avec l'API
				
				
				GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDTCMkXi0MF9HPQwvdgryfF1Zsqs4Ygebk").build();
				GeocodingResult[] results = null;
				try {
					results = GeocodingApi.geocode(context,adresse).await();
				} catch (ApiException | InterruptedException | IOException e) {
					e.printStackTrace();
				}
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				double[] coord = {Double.parseDouble(gson.toJson(results[0].geometry.location.lat)),Double.parseDouble(gson.toJson(results[0].geometry.location.lng))};
				this.coordonnees = coord;
				
				//On créé un nouvel identifiant pour la BDD, qui sera le max des id + 1
				ResultSet result2 = state.executeQuery("SELECT MAX(id) FROM adresse");
				result2.next();
				id = result2.getInt("max")+1;
				
				//On ajoute l'adresse
				PreparedStatement st = conn.prepareStatement("INSERT INTO adresse(id,adresse,lat,lon) VALUES(?,?,?,?)");
				st.setInt(1, id);
				st.setString(2, adresse);
				st.setDouble(3, coord[0]);
				st.setDouble(4, coord[1]);
				st.executeUpdate();
				
				st.close();
			}			
			state.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.id = id;

	}
	
	/**
	 * Fonction calculant la distance à vol d'oiseau (distance géodésique) entre cette adresse et une deuxième
	 * 
	 * @param adr : Adresse, deuxième adresse
	 * @return :double, distance directe entre les 2 adresses
	 */
	public double calculerDistance(Adresse adr) {
		double R = 6371; //rayon de la terre en km
		try {//Le try permet de gérer le cas où une adresse est sans coordonnées
			// On passe les angles en rad
				double latitude1 = this.coordonnees[0] * Math.PI / 180;
				double latitude2 = this.coordonnees[0] * Math.PI / 180;
				
				double longitude1 = this.coordonnees[1] * Math.PI / 180;
				double longitude2 = this.coordonnees[1] * Math.PI / 180;
				
				//Formule de la distance géodésique
				return (R * Math.acos(Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude2 - longitude1) + Math.sin(latitude1) * Math.sin(latitude2)));
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Coordonnées de l'adresse inconnues");
			return(0);
		}
	}

}
