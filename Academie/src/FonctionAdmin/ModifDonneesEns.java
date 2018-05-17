package FonctionAdmin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import AppilBureautique.FenAdmin;
import Donnees.Enseignant;
import FonctionModifDonneesEnseignant.ModifCollegeP;
import FonctionModifDonneesEnseignant.ModifCollegeS;
import FonctionModifDonneesEnseignant.ModifMailEns;
import FonctionModifDonneesEnseignant.ModifPasswordEns;
import FonctionModifDonneesEnseignant.ModifTelEns;
import User.UserAdmin;
/**
 * Cette fenêtre permet de choisir l'information d'un enseignant que l'on veut modifier.
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class ModifDonneesEns extends JFrame implements ActionListener{
	
	/*
	 *  attributs de la fonction créer un enseignant
	 */
	// mpeme construction que les classes précédentes
	private static final long serialVersionUID = 1L;
	private JPanel panModifEns = new JPanel();
	private JLabel label = new JLabel();
	
	private Enseignant ens;
	private UserAdmin admin;
	
	
	private JButton bMail = new JButton ("Modifier le mail");
	private JButton bTelephone = new JButton("Modifier le telephone");
	private JButton bCollegeP = new JButton("Modifier le Collège Principal");
	private JButton bCollegeS = new JButton("Modifier le Collège Secondaire");
	private JButton bPassword = new JButton("Modifier le mot de passe");
	
	private JPanel boutonPaneMail = new JPanel();
	private JPanel boutonPaneTelephone = new JPanel();
	private JPanel boutonPaneCollegeP = new JPanel();
	private JPanel boutonPaneCollegeS = new JPanel();
	private JPanel boutonPanePassword = new JPanel();
	
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPaneRetour = new JPanel();
	
	/**
	 * Ce constructeur permet de sélectionner l'information de l'enseignant que l'on souhaite modifier.
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param ens : enseignant dont on veut modifier une information
	 */
	public ModifDonneesEns(UserAdmin admin, Enseignant ens) {
		this.ens = ens;
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Ajouter un collège à la base de données"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panModifEns.setBackground(Color.LIGHT_GRAY); // on définit sa couleur de fond
		this.setContentPane(panModifEns); // on prévient notre JFrame que pan sera son content pane

		boutonPaneMail.add(bMail);
		boutonPaneTelephone.add(bTelephone);
		boutonPaneCollegeP.add(bCollegeP);
		boutonPaneCollegeS.add(bCollegeS);
		boutonPanePassword.add(bPassword);
		panModifEns.add(boutonPaneMail);
		panModifEns.add(boutonPaneTelephone);
		panModifEns.add(boutonPaneCollegeP);
		panModifEns.add(boutonPaneCollegeS);
		panModifEns.add(boutonPanePassword);
				
		bMail.addActionListener(this);
		bTelephone.addActionListener(this);
		bCollegeP.addActionListener(this);
		bCollegeS.addActionListener(this);	
		bPassword.addActionListener(this);
		
		boutonPaneRetour.add(bRetour);
		panModifEns.add(boutonPaneRetour);

		bRetour.addActionListener(this);
	
	    this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  Les boutons permettent de choisir la donnée d'un enseignant que l'on veut modifier. 
	 *  Le bouton retour permet de revenir à la fenêtre précédente. 
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	    
		if(e.getSource() == bMail) {
			dispose();
			ModifMailEns modifMail = new ModifMailEns(admin,ens);
			modifMail.setVisible(true);
		}
		if(e.getSource() == bTelephone) {
			dispose();
			ModifTelEns modifTel = new ModifTelEns(admin,ens);
			modifTel.setVisible(true);
		}
		if(e.getSource() == bCollegeP) {
			dispose();
			ModifCollegeP modifColl = new ModifCollegeP(admin,ens);
			modifColl.setVisible(true);
		}
		if(e.getSource() == bCollegeS) {
			dispose();
			ModifCollegeS modifColl = new ModifCollegeS(admin,ens);
			modifColl.setVisible(true);
		}
		if(e.getSource() == bPassword) {
			dispose();
			ModifPasswordEns modifPass = new ModifPasswordEns(admin,ens);
			modifPass.setVisible(true);
		}
		if(e.getSource()==bRetour) {
			dispose();
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);
		}
		
	}

}
