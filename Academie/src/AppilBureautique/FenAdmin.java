package AppilBureautique;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import FonctionAdmin.AjoutCollege;
import FonctionAdmin.AjoutEnseignant;
import FonctionAdmin.AjoutEtudiant;
import FonctionAdmin.CalculerDistance;
import FonctionAdmin.FenChoixEnsDon;
import FonctionAdmin.FenChoixEtuDonEtu;
import FonctionAdmin.FicheSignaletique;
import User.UserAdmin;
/**
 * Cette fen�tre permet d'afficher les actions que peut effectuer l'administrateur. 
 * Cette classe h�rite de JFrame et impl�mente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenAdmin extends JFrame implements ActionListener {
	
	/*
	 * Attributs
	 */
	private static final long serialVersionUID = 1L;		// attribut h�rit� de JFrame
	protected UserAdmin admin;		// attribut UserAdmin de l'utilisateur qui se connecte
	private JPanel fond = new JPanel(); 	// instantiation d'un objet JPanel
	
	// D�finition des boutons de choix d'action pr�sents sur la fen�tre : 
	private JButton bEns = new JButton ("Ajouter/supprimer un enseignant dans la base de donn�es");
	private JButton bEleve = new JButton("Ajouter/supprimer un �l�ve dans la base de donn�es");
	private JButton bCollege = new JButton("Ajouter un coll�ge � la base de donn�es");
	private JButton bFiche = new JButton("Imprimer une fiche signal�tique");
	private JButton bDist = new JButton("Calculer une distance");
	private JButton bDonneesEtu = new JButton("Modifier les informations d'un �tudiant");
	private JButton bDonneesEns = new JButton("Modifier les informations d'un enseignant");
	private JButton bDeco = new JButton("Deconnexion");
	
	// D�finition des panneaux contenant les boutons
	private JPanel boutonPaneEns = new JPanel();
	private JPanel boutonPaneEleve = new JPanel();
	private JPanel boutonPaneCollege = new JPanel();
	private JPanel boutonPaneFiche = new JPanel();
	private JPanel boutonPaneDist = new JPanel();
	private JPanel boutonPaneDonneesEtu = new JPanel();
	private JPanel boutonPaneDonneesEns = new JPanel();
	private JPanel boutonPaneDeco = new JPanel();
	
	/**
	 * Constructeur de la fen�tre pr�sentant les options de l'administrateur.
	 * @param identifiant : identifiant de l'administrateur
	 * @param motdepasse :  mot de passe de connexion de l'administrateur
	 */
	public FenAdmin(String identifiant, String motdepasse) {
		this.admin = new UserAdmin(identifiant, motdepasse); 	// On initialise l'identifiant et le mot de passe de l'utilisateur qui s'est connect�
		
		// Param�tres de la fenetre
		this.setTitle("Administration"); 	// Modifier le titre de la fenetre
		this.setSize(400,400); 		// Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); 		// Centrer la fenetre par rapport a l'ecran de l'ordi
		fond.setBackground(Color.lightGray);		 // on d�finit sa couleur de fond
		this.setContentPane(fond); 		// on pr�vient notre JFrame que fond sera son contentPane
		
		// On ajoute les boutons aux panneaux :
		boutonPaneEns.add(bEns);
		boutonPaneEleve.add(bEleve);
		boutonPaneCollege.add(bCollege);
		boutonPaneFiche.add(bFiche);
		boutonPaneDist.add(bDist);
		boutonPaneDonneesEtu.add(bDonneesEtu);
		boutonPaneDonneesEns.add(bDonneesEns);
		boutonPaneDeco.add(bDeco);
		
		// On ajoute les panneaux au fond :
		fond.add(boutonPaneEns);
		fond.add(boutonPaneCollege);
		fond.add(boutonPaneEleve);
		fond.add(boutonPaneFiche);
		fond.add(boutonPaneDist);
		fond.add(boutonPaneDonneesEtu);
		fond.add(boutonPaneDonneesEns);
		fond.add(boutonPaneDeco);
				
		// Action Listener permet de d�finir l'action avec la m�thode impl�ment�e quand l'on clique sur les boutons
		bEns.addActionListener(this);
		bEleve.addActionListener(this);
		bCollege.addActionListener(this);	
		bFiche.addActionListener(this);
		bDist.addActionListener(this);
		bDonneesEtu.addActionListener(this);
		bDonneesEns.addActionListener(this);
		bDeco.addActionListener(this);
				
		// On rend la fen�tre visible
		this.setVisible(true);	
	}
	
	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  Ils correspondent � chaque action que peut r�aliser l'administrateur et ouvrent de nouvelles fen�tres
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bEns) {
			dispose();
			AjoutEnseignant ajoutEns = new AjoutEnseignant(admin);
			ajoutEns.setVisible(true);
		}
		if(e.getSource() == bEleve) {
			dispose();
			AjoutEtudiant ajoutEt = new AjoutEtudiant(admin);
			ajoutEt.setVisible(true);
		}
		if(e.getSource() == bCollege) {
			dispose();
			AjoutCollege ajoutColl = new AjoutCollege(admin);
			ajoutColl.setVisible(true);
		}
		if(e.getSource() == bFiche) {
			dispose();
			FicheSignaletique fiche = new FicheSignaletique(admin);
			fiche.setVisible(true);
		}
		if(e.getSource() == bDist) {
			dispose();
			CalculerDistance calc = new CalculerDistance(admin);
			calc.setVisible(true);
		}
		if(e.getSource() == bDonneesEtu) {
			dispose();
			FenChoixEtuDonEtu FenChoix = new FenChoixEtuDonEtu(admin);
			FenChoix.setVisible(true);
		}
		if(e.getSource() == bDonneesEns) {
			dispose();
			FenChoixEnsDon modifEns = new FenChoixEnsDon(admin);
			modifEns.setVisible(true);
		}
		if(e.getSource() == bDeco) {
			dispose();
			Fen fen = new Fen();
			fen.setVisible(true);
		}
	}
	
}
