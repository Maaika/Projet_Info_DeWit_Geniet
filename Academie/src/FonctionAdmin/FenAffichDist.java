package FonctionAdmin;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Donnees.Enseignant;
import User.UserAdmin;
/**
 * Cette fenêtre affiche le résultat du calcul de distance.
 * Cette classe hérite de JFrame.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenAffichDist extends JFrame {
	
	/*
	 *  Attributs de la fonction créer un enseignant
	 */
	
	// même construction que les classes précédentes 
	private static final long serialVersionUID = 1L;
	private JPanel panAffichage = new JPanel();
	private JLabel label = new JLabel();
	
	private UserAdmin admin;
	private Enseignant ens;
	
	private JLabel lab1 = new JLabel("dist1 (km): ");
	private JLabel dis1;
	private JLabel lab2 = new JLabel("dist2 (km): ");
	private JLabel dis2;
	
	
	
	
	/**
	 * Ce constructeur affiche le résultat dans une nouvelle fenêtre de la distance calculée
	 * @param dis1 : distance au collège principal d'un enseignant
	 * @param dis2 : distance au collège secondaire d'un enseignant
	 */
	public FenAffichDist(double dis1, double dis2) {
		this.dis1 = new JLabel(dis1+"");
		this.dis2 = new JLabel(dis2+"");
		
		// Parametres de la fenetre
		this.setTitle("Distance calculée"); 		// Modifier le titre de la fenetre
		this.setSize(400,200);		 // Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		 // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); 		// Centrer la fenetre par rapport a l'ecran de l'ordi
		panAffichage.setBackground(Color.LIGHT_GRAY); 		// on définit sa couleur de fond
		this.setContentPane(panAffichage); 		// on prévient notre JFrame que pan sera son content pane

		panAffichage.add(lab1);
		panAffichage.add(this.dis1);
		panAffichage.add(lab2);
		panAffichage.add(this.dis2);
		
		
	    this.setVisible(true);
	}

}
