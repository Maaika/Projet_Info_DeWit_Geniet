package AppilBureautique;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import FonctionEnseignant.AfficherInfoEleve;
import FonctionEnseignant.Note;
import User.UserEnseignant;
/**
 * Cette fenêtre permet d'afficher les actions que peut effectuer l'enseignant 
 * Cette classe hérite de JFrame et implémente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenEns extends JFrame implements ActionListener {

	/*
	 * Attributs
	 */
	private static final long serialVersionUID = 1L;		// attribut hérité de JFrame
	protected UserEnseignant ens; 	// attribut UserEnseignant de l'utilisateur qui se connecte
	private JPanel fond = new JPanel(); // instantiation d'un objet JPanel
	
	// Définition des boutons de choix d'action présents sur la fenêtre ainsi que des panneaux les comprenant :
	private JButton bNote = new JButton ("Voir et/ou modifier note");
	private JButton bEleve = new JButton("Voir les informations d'un étudiant");
	private JButton bEns = new JButton("Consulter ses propres informations");
	private JPanel boutonPaneNote = new JPanel();
	private JPanel boutonPaneEleve = new JPanel();
	private JPanel boutonPaneEns = new JPanel();
	
	/**
	 *  Constructeur de la fenêtre présentant les options de l'enseignant
	 *  @param identifiant : identifiant de l'enseignant
	 * @param motdepasse :  mot de passe de connexion de l'enseignant
	 */
	public FenEns(String identifiant, String motdepasse) {
		this.ens = new UserEnseignant(identifiant, motdepasse); 	// On initialise l'identifiant et le mot de passe de l'utilisateur qui s'est connecté
		
		// Paramètres de la fenêtre
		this.setTitle("Enseignement"); 		// Modifier le titre de la fenetre
		this.setSize(400,200); 		// Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Un clic sur croix entraine une fermeture de la fenetre
		fond.setBackground(Color.red);		 // on définit sa couleur de fond
		this.setContentPane(fond); 		// on prévient notre JFrame que pan sera son content pane
		
		// On ajoute les boutons aux panneaux et les panneaux au fond :
		boutonPaneNote.add(bNote);
		boutonPaneEleve.add(bEleve);
		boutonPaneEns.add(bEns);
		fond.add(boutonPaneNote);
		fond.add(boutonPaneEleve);
		fond.add(boutonPaneEns);
			
		// Action Listener permet de définir l'action avec la méthode implémentée quand l'on clique sur les boutons
		bNote.addActionListener(this);
		bEleve.addActionListener(this);		
		bEns.addActionListener(this);
				
		// On rend la fenêtre visible
		this.setVisible(true);
	}

	/**
	 * Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * Ils correspondent à chaque action que peut réaliser l'enseignant. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bNote) {
			dispose();
			Note note = new Note(ens);
			note.setVisible(true);
			
		}
		if(e.getSource() == bEleve) {
			dispose();
			AfficherInfoEleve aff = new AfficherInfoEleve(ens);
			aff.setVisible(true);
		}
		if(e.getSource() == bEns) {
			dispose();
			this.ens.afficherDonnees(); 
		}
	}

}
