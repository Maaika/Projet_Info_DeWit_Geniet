package FonctionModifDonneesEtudiant;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AppilBureautique.FenAdmin;
import Donnees.Etudiant;
import User.UserAdmin;
/**
 * Cette fenêtre permet de modifier le numéro de téléphone de l'étudiant.
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class ModifTelEtu extends JFrame implements ActionListener{

	/*
	 *  attributs de la fonction créer un enseignant
	 */
	// même construction que les classes précédentes
	private static final long serialVersionUID = 1L;
		
	private JPanel panModifTelEtu = new JPanel();
	private JLabel label = new JLabel();
	
	private UserAdmin admin;
	private Etudiant etu;
	private JLabel tel = new JLabel("Nouveau Telephone : ");
	private JTextField nouvTel = new JTextField();
	
	
	private JButton bValider = new JButton ("Valider");
	
	private JPanel boutonPaneValider = new JPanel();
	
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPaneRetour = new JPanel();
	
	/**
	 * Ce constructeur permet de modifier le numéro de téléphone d'un étudiant.
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param ens : enseignant dont on veut modifier une information
	 */
	public ModifTelEtu(UserAdmin admin, Etudiant etu) {
		this.etu = etu;
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Modifier le téléphone"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panModifTelEtu.setBackground(Color.LIGHT_GRAY); // on définit sa couleur de fond
		this.setContentPane(panModifTelEtu); // on prévient notre JFrame que pan sera son content pane

		
		nouvTel.setColumns(10);
		
		panModifTelEtu.add(tel);
		panModifTelEtu.add(nouvTel);
		
		
		boutonPaneValider.add(bValider);
		panModifTelEtu.add(boutonPaneValider);
		bValider.addActionListener(this);
		
		boutonPaneRetour.add(bRetour);
		panModifTelEtu.add(boutonPaneRetour);
		bRetour.addActionListener(this);
		
		
	    this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * 	Le bouton valider confirme la modification du téléphone de l'étudiant.
	 * Le bouton retour revient à la page précédente.
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String tel = this.tel.getText();
		if(e.getSource() == bValider) {
			dispose();
			this.admin.modifierTelephoneEtudiant(tel, this.etu);
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);
		}
		if(e.getSource()==bRetour) {
			dispose();
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);
		}
		
	}

}
