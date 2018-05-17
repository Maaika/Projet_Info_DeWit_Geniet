package FonctionModifDonneesEnseignant;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AppilBureautique.FenAdmin;
import Donnees.Enseignant;
import User.UserAdmin;
/**
 * Cette fenêtre permet de modifier le numéro de téléphone de l'enseignant. 
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class ModifTelEns extends JFrame implements ActionListener{
	
	/*
	 *  attributs de la fonction créer un enseignant
	 */
	// même construction que les classes précédentes
	private static final long serialVersionUID = 1L;
	private JPanel panModifTelEns = new JPanel();
	private JLabel label = new JLabel();
	
	private UserAdmin admin;
	private Enseignant ens;
	private JLabel tel = new JLabel("Nouveau Telephone : ");
	private JTextField nouvTel = new JTextField();
	
	
	private JButton bValider = new JButton ("Valider");
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPaneValider = new JPanel();
	private JPanel boutonPaneRetour = new JPanel();
	
	/**
	 * Ce constructeur permet de modifier le numéro de téléphone d'un enseignant
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param ens : enseignant dont on veut modifier une information
	 */
	public ModifTelEns(UserAdmin admin, Enseignant ens) {
		this.ens = ens;
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Modifier le téléphone"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panModifTelEns.setBackground(Color.LIGHT_GRAY); // on définit sa couleur de fond
		this.setContentPane(panModifTelEns); // on prévient notre JFrame que pan sera son content pane

		
		nouvTel.setColumns(10);
		
		panModifTelEns.add(tel);
		panModifTelEns.add(nouvTel);
		
		boutonPaneValider.add(bValider);
		panModifTelEns.add(boutonPaneValider);
		bValider.addActionListener(this);
		
		boutonPaneRetour.add(bRetour);
		panModifTelEns.add(boutonPaneRetour);
		bRetour.addActionListener(this);
		
		
	    this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * 	Le bouton valider confirme la modification du téléphone de l'enseignant.
	 * Le bouton retour revient à la page précédente.
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String tel = this.tel.getText();
		if(e.getSource() == bValider) {
			dispose();
			this.admin.modifierTelephoneEnseignant(tel, this.ens);
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
