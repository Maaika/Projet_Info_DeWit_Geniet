package User;

/**
 * 
 * @author Florent & Apolline
 * Classe User, classe générale servant à regrouper les attributs communs des autres classes utilisateurs.
 *
 */
public class User {
	/*
	 * Attributes
	 */
	public String id;
	private String mdp;
	
	/*
	 * Getters and Setters
	 */
	/**
	 * 
	 * @return : Mot de passe
	 */
	public String getMdp() {
		return mdp;
	}

	/**
	 * 
	 * @param mdp : nouveau mot de passe
	 */
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	/*
	 * Constructor
	 */
	/**
	 * Constructeur de User
	 * @param id : String, identifiant
	 * @param mdp : String, mot de passe
	 */
	public User(String id, String mdp) {
		this.id = id;
		setMdp(mdp);
	}
}
