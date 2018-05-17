package FonctionAdmin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AppilBureautique.FenAdmin;
import Donnees.Etudiant;
import FonctionModifDonneesEtudiant.ModifCollege;
import FonctionModifDonneesEtudiant.ModifMailEtu;
import FonctionModifDonneesEtudiant.ModifPasswordEtu;
import FonctionModifDonneesEtudiant.ModifTelEtu;
import User.UserAdmin;
/**
 * Cette fenêtre permet de choisir l'information d'un étudiant que l'on veut modifier.
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class ModifDonneesEtu extends JFrame implements ActionListener{

	/*
	 *  attributs de la fonction créer un enseignant
	 */
	public static final long serialVersionUID = 1L;
	
	private JPanel panModifEtu = new JPanel();
	private JLabel label = new JLabel();
	
	private UserAdmin admin;
	private Etudiant etu;

	private JButton bMail = new JButton ("Modifier le mail");
	private JButton bTelephone = new JButton("Modifier le telephone");
	private JButton bCollege = new JButton("Modifier le Collège");
	private JButton bPassword = new JButton("Modifier le mot de passe");
	
	private JPanel boutonPaneMail = new JPanel();
	private JPanel boutonPaneTelephone = new JPanel();
	private JPanel boutonPaneCollege = new JPanel();
	private JPanel boutonPanePassword = new JPanel();
	
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPaneRetour = new JPanel();
	
	/**
	 * Ce constructeur permet de sélectionner l'information de l'étudiant que l'on souhaite modifier.
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param etu : étudiant dont on veut modifier une information
	 */
	public ModifDonneesEtu(UserAdmin admin, Etudiant etu) {
		this.etu = etu;
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Ajouter un collège à la base de données"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panModifEtu.setBackground(Color.LIGHT_GRAY); // on définit sa couleur de fond
		this.setContentPane(panModifEtu); // on prévient notre JFrame que pan sera son content pane

		boutonPaneMail.add(bMail);
		boutonPaneTelephone.add(bTelephone);
		boutonPaneCollege.add(bCollege);
		boutonPanePassword.add(bPassword);
		panModifEtu.add(boutonPaneMail);
		panModifEtu.add(boutonPaneTelephone);
		panModifEtu.add(boutonPaneCollege);
		panModifEtu.add(boutonPanePassword);
				
		bMail.addActionListener(this);
		bTelephone.addActionListener(this);
		bCollege.addActionListener(this);	
		bPassword.addActionListener(this);
		
		boutonPaneRetour.add(bRetour);
		panModifEtu.add(boutonPaneRetour);
		
		bRetour.addActionListener(this);

	    this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  Les boutons permettent de choisir la donnée d'un étudiant que l'on veut modifier. 
	 *  Le bouton retour permet de revenir à la fenêtre précédente. 
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bMail) {
			dispose();
			ModifMailEtu modifMail = new ModifMailEtu(admin,etu);
			modifMail.setVisible(true);
		}
		if(e.getSource() == bTelephone) {
			dispose();
			ModifTelEtu modifTel = new ModifTelEtu(admin,etu);
			modifTel.setVisible(true);
		}
		if(e.getSource() == bCollege) {
			dispose();
			ModifCollege modifColl = new ModifCollege(admin,etu);
			modifColl.setVisible(true);
		}
		if(e.getSource() == bPassword) {
			dispose();
			ModifPasswordEtu modifPass = new ModifPasswordEtu(admin,etu);
			modifPass.setVisible(true);
		}
		if(e.getSource()==bRetour) {
			dispose();
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);
		}
		
	}

}
