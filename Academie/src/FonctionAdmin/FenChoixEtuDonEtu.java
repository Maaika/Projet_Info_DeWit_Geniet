package FonctionAdmin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import User.UserAdmin;
/**
 * Cette fenêtre affiche une fenêtre où l'on choisit le bon étudiant parmi ceux qui ont le même nom.
 * Cela permet d'accéder à leurs données. 
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenChoixEtuDonEtu extends JFrame implements ActionListener {
	
	/*
	 *  attributs de la fonction créer un enseignant
	 */
	// même construction que les classes précédentes
	public static final long serialVersionUID = 1L;
	public UserAdmin admin;
	public JPanel panChoixEtu = new JPanel();
	public JLabel label = new JLabel();
	public JLabel nom = new JLabel("Nom de l'étudiant : ");
	public JLabel prenom = new JLabel("Prénom de l'étudiant : ");
	public JTextField nomEtu = new JTextField();
	public JTextField prenomEtu = new JTextField();
	private JButton bValider = new JButton ("Valider");
	
	private JPanel boutonPaneValider = new JPanel();
	
	
	/**
	 * Ce constructeur permet de sélectionner l'étudiant dont on veut modifier les informations si deux ont le même nom.
	 * @param admin : UserAdmin de l'utilisateur de la session
	 */
	public FenChoixEtuDonEtu(UserAdmin admin) {
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Modifier le mail"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panChoixEtu.setBackground(Color.LIGHT_GRAY); // on définit sa couleur de fond
		this.setContentPane(panChoixEtu); // on prévient notre JFrame que pan sera son content pane

		nomEtu.setColumns(10);
		prenomEtu.setColumns(10);
		
		panChoixEtu.add(nom);
		panChoixEtu.add(nomEtu);
		panChoixEtu.add(prenom);
		panChoixEtu.add(prenomEtu);
		
		
		boutonPaneValider.add(bValider);
		panChoixEtu.add(boutonPaneValider);
		bValider.addActionListener(this);
		
	    this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide le choix de l'étudiant.
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String nom = this.nomEtu.getText();
	    String prenom = this.prenomEtu.getText();
		if(e.getSource() == bValider) {
			dispose();
			FenChoixEtuDonAux choix2 = new FenChoixEtuDonAux(admin,nom,prenom);
			choix2.setVisible(true);

		}
		
	}



}
