package FonctionModifDonneesEtudiant;

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
import Donnees.Etudiant;
import FonctionAdmin.FenAfficherPassword;
import User.UserAdmin;
/**
 * Cette fen�tre permet de modifier le mot de passe de l'�tudiant. 
 * Cette classe h�rite de JFrame qui impl�mente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class ModifPasswordEtu extends JFrame implements ActionListener{

	/*
	 *  attributs de la fonction cr�er un enseignant
	 */
	// m�me construction que les classes pr�c�dentes.
	private static final long serialVersionUID = 1L;
		
	private JPanel panModifPassEtu = new JPanel();
	private JLabel label = new JLabel();
	
	private UserAdmin admin;
	private Etudiant etu;
	
	private JButton bValider = new JButton ("Valider");
	
	private JPanel boutonPaneValider = new JPanel();
	
	
	/**
	 * Ce constructeur permet de modifier le mot de passe d'un �tudiant.
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param ens : enseignant dont on veut modifier une information
	 */ 
	public ModifPasswordEtu(UserAdmin admin, Etudiant etu) {
		this.etu = etu;
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Modifier le mail"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panModifPassEtu.setBackground(Color.LIGHT_GRAY); // on d�finit sa couleur de fond
		this.setContentPane(panModifPassEtu); // on pr�vient notre JFrame que pan sera son content pane

		boutonPaneValider.add(bValider);
		panModifPassEtu.add(boutonPaneValider);
		bValider.addActionListener(this);
		
		
	    this.setVisible(true);
	}

	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * 	Le bouton valider confirme la modification du mot de passe de l'�tudiant.
	 * Le bouton retour revient � la page pr�c�dente.
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bValider) {
			dispose();
			this.admin.modifierPasswordEtudiant(this.etu);
			Connection conn = ConnectionJdbc.getInstance();
			
			String mdp = "";
			
			try {
				Statement st = conn.createStatement();
				ResultSet res = st.executeQuery("SELECT password FROM etudiant WHERE id = "+this.etu.id);
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
