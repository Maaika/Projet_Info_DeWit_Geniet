package FonctionAdmin;

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
import User.UserAdmin;
/**
 * Cette fen�tre affiche le nouveau mot de passe g�n�r�.
 * Cette classe h�rite de JFrame.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenAfficherPassword extends JFrame {
		
	/*
	 *  attributs de la fonction cr�er un enseignant
	 */
	// m�me construction que les classes pr�c�dentes 
	private static final long serialVersionUID = 1L;
	private JPanel panAffichage = new JPanel();
	private JLabel label = new JLabel();
	
	private UserAdmin admin;
	private Enseignant ens;
	
	private JLabel lab1 = new JLabel("Nouveau mot de passe : ");
	private JLabel password;
	
	

	public FenAfficherPassword(String mdp) {
		password = new JLabel(mdp);
		// Parametres de la fenetre
		this.setTitle("Nouveau mot de passe g�n�r�"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panAffichage.setBackground(Color.LIGHT_GRAY); // on d�finit sa couleur de fond
		this.setContentPane(panAffichage); // on pr�vient notre JFrame que pan sera son content pane

		panAffichage.add(lab1);
		panAffichage.add(password);
		
		
	    this.setVisible(true);
	}

}
