package FonctionEnseignant;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Donnees.Etudiant;
import User.UserEnseignant;
/**
 * Cette fen�tre permet d'afficher les informations d'un �tudiant.
 * Cette classe h�rite de JFrame qui impl�mente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class AfficherInfoEleve  extends JFrame implements ActionListener {
	
	/*
	 * Attributs
	 */
	// m�me construction que les classes pr�c�dentes
	private UserEnseignant ens;
	private JPanel panInfo = new JPanel();
	
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Pr�nom : ");
	private JTextField nomInfo = new JTextField();
	private JTextField prenomInfo = new JTextField();
	
	private JButton valider = new JButton();
	private JPanel boutonPane = new JPanel();
	
	/**
	 * Ce constructeur permet d'afficher les informations d'un �l�ve. 
	 * @param ens : enseignant dont on veut modifier une information
	 */
	public AfficherInfoEleve(UserEnseignant ens) {
		// Parametres de la fenetre
		this.setTitle("Afficher les informations d'un �l�ve"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panInfo.setBackground(Color.red); // on d�finit sa couleur de fond
		this.setContentPane(panInfo); // on pr�vient notre JFrame que pan sera son content pane
		
		nomInfo.setColumns(10);
		prenomInfo.setColumns(10);
		
		panInfo.add(nom);
		panInfo.add(nomInfo);
		panInfo.add(prenom);
		panInfo.add(prenomInfo);
		
		boutonPane.add(valider);
	    valider.addActionListener(this);
	    panInfo.add(boutonPane);
		    
	    this.setVisible(true);
		
	}
	
	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * 	Le bouton valider conduit � l'affichage des informations de l'�tudiant choisi. 
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == valider) {
			String nom = nomInfo.getText();
		    String prenom = prenomInfo.getText();
		    Etudiant et = new Etudiant(nom, prenom);
		   
		    et.ficheSignaletique();
		    et.voirMatieres();
		}
		
	}
}