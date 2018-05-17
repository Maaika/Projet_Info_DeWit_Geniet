package ConnectionJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Cette fenêtre permet de se connecter à la base de données.
 * 
 * @author Florent Geniet, Apolline De Wit
 */
public class ConnectionJdbc {
	
	/*
	 * Attributs
	 */
	private final String DB_URL = "jdbc:postgresql://localhost:5432/academie"; 		// driver JDBC utilise et url de la base de donnees
	private final String USER = "postgres"; 	// nom d’utilisateur
	private final String PASS = "postgres";		 // mot de passe associe
	private static Connection conn;
	
	/**
	 * Cette méthode réalise la connexion à la base de données
	 */
	private ConnectionJdbc() {
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS); 	// ouvre une connexion vers la base de donnees academie
		}
		catch(Exception e){
			e.printStackTrace(); 	// pour gérer les erreurs (pas de pilote, base inexistante, etc.)
		}
	}
	/**
	 * Cette méthode gère les erreurs de connexion : elle crée une nouvelle connection si celle-ci est nulle ou si elle s'est arrêtée. 
	 * @return une connexion 
	 */
	public static Connection getInstance() {
		if (conn == null) {
			new ConnectionJdbc();
		}
		try {
			if (conn.isClosed()) {
				new ConnectionJdbc();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (conn);
	}
	
}
