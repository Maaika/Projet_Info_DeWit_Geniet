package AppilBureautique;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import User.UserEtudiant;
/**
 * Cette fenêtre permet d'afficher les actions que peut effectuer l'étudiant. 
 * Cette classe hérite de JFrame et implémente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenEleve extends JFrame implements ActionListener {

	/*
	 * Attributs
	 */
	private static final long serialVersionUID = 1L;	// attribut hérité de JFrame
	protected UserEtudiant et;		// attribut UserEtudiant de l'utilisateur qui se connecte
	private JPanel fond = new JPanel(); // instantiation d'un objet JPanel
	
	// Définition des boutons de choix d'action présents sur la fenêtre ainsi que des panneaux les comprenant :
	private JButton b1 = new JButton ("Voir informations personnelles");
	private JButton b2 = new JButton("Voir la liste des matières");
	private JPanel boutonPane1 = new JPanel();
	private JPanel boutonPane2 = new JPanel();
	
	/**
	 *  Constructeur de la fenêtre présentant les options de l'étudiant
	 *  @param identifiant : identifiant de l'étudiant
	 * @param motdepasse :  mot de passe de connexion de l'étudiant
	 */
	public FenEleve(String identifiant, String motdepasse) {
		this.et = new UserEtudiant(identifiant, motdepasse); 	// On initialise l'identifiant et le mot de passe de l'utilisateur qui s'est connecté
		
		// Paramètres de la fenetre
		this.setTitle("Eleve");		// Modifier le titre de la fenetre
		this.setSize(400,200); 		// Modifier la taille
		this.setResizable(false);		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); 		// Centrer la fenetre par rapport a l'ecran de l'ordi			
		fond.setBackground(Color.orange);	 // on définit sa couleur de fond
		this.setContentPane(fond);		 // on prévient notre JFrame que pan sera son content pane
				
		// On ajoute les boutons aux panneaux et les panneaux au fond :
		boutonPane1.add(b1);
		boutonPane2.add(b2);
		fond.add(boutonPane1);
		fond.add(boutonPane2);
				
		// Action Listener permet de définir l'action avec la méthode implémentée quand l'on clique sur les boutons
		b1.addActionListener(this);
		b2.addActionListener(this);				
				
		// On rend la fenêtre visible
		this.setVisible(true);
	}

	/**
	 * Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * Ils correspondent à chaque action que peut réaliser l'élève. 
	 * Nous n'avons pas eu le temps de finaliser leurs actions, elles n'aboutissent pas pour le moment. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1) {
			dispose();
		}
		if(e.getSource() == b2) {
			dispose();
		}
		
		
	}
}
