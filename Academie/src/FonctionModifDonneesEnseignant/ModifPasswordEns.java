package FonctionModifDonneesEnseignant;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AppilBureautique.FenAdmin;
import ConnectionJdbc.ConnectionJdbc;
import Donnees.Enseignant;
import FonctionAdmin.FenAfficherPassword;
import User.UserAdmin;
/**
 * Cette fenêtre permet de modifier le mot de passe de l'enseignant. 
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class ModifPasswordEns extends JFrame implements ActionListener{
	
	/*
	 *  attributs 
	 */
	// même construction que les classes précédentes.
	private static final long serialVersionUID = 1L;
	private JPanel panModifPassEns = new JPanel();
	private JLabel label = new JLabel();
	
	private UserAdmin admin;
	private Enseignant ens;
	
	private JButton bValider = new JButton ("Valider");
	private JPanel boutonPaneValider = new JPanel();
	
	
	/**
	 * Ce constructeur permet de modifier le mot de passe d'un enseignant
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param ens : enseignant dont on veut modifier une information
	 */ 
	public ModifPasswordEns(UserAdmin admin, Enseignant ens) {
		this.ens = ens;
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Modifier le mot de passe"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panModifPassEns.setBackground(Color.LIGHT_GRAY); // on définit sa couleur de fond
		this.setContentPane(panModifPassEns); // on prévient notre JFrame que pan sera son content pane

		boutonPaneValider.add(bValider);
		panModifPassEns.add(boutonPaneValider);
		bValider.addActionListener(this);
	
	    this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * 	Le bouton valider confirme la modification du mot de passe de l'enseignant.
	 * Le bouton retour revient à la page précédente.
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bValider) {
			dispose();
			this.admin.modifierPasswordEnseignant(this.ens);
			Connection conn = ConnectionJdbc.getInstance();
			
			String mdp = "";
			
			try {
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery("SELECT password FROM enseignant WHERE id = "+this.ens.id);
				res.next();
				mdp = res.getString("password");
			}
			catch(Exception a) {
				a.printStackTrace();
			}
			FenAfficherPassword fenPass = new FenAfficherPassword(mdp);
			fenPass.setVisible(true);
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);
		}
		
	}



}
