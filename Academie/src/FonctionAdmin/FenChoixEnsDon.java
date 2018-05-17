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
 * Cette fen�tre affiche une fen�tre o� l'on rentre le nom et le pr�nom de l'enseignant dont on veut modifier les donn�es.
 * Cette classe h�rite de JFrame qui impl�mente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenChoixEnsDon extends JFrame implements ActionListener{

	/*
	 *  attributs de la fonction cr�er un enseignant
	 */
	// m�me construction que les classes pr�c�dentes 
	private static final long serialVersionUID = 1L;
	private UserAdmin admin;
	private JPanel panChoixEns = new JPanel();
	private JLabel label = new JLabel();
	private JLabel nom = new JLabel("Nom de l'�tudiant : ");
	private JLabel prenom = new JLabel("Pr�nom de l'�tudiant : ");
	private JTextField nomEns = new JTextField();
	private JTextField prenomEns = new JTextField();
	private JButton bValider = new JButton ("Valider");
	
	private JPanel boutonPaneValider = new JPanel();
	
	
	/**
	 * Ce constructeur permet d'ins�rer le nom et le pr�nom d'un enseignant dont on veut modifier les informations
	 * @param admin : UserAdmin de l'utilisateur de la session
	 */
	public FenChoixEnsDon(UserAdmin admin) {
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Choix de l'enseignant"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panChoixEns.setBackground(Color.LIGHT_GRAY); // on d�finit sa couleur de fond
		this.setContentPane(panChoixEns); // on pr�vient notre JFrame que pan sera son content pane

		nomEns.setColumns(10);
		prenomEns.setColumns(10);
		
		panChoixEns.add(nom);
		panChoixEns.add(nomEns);
		panChoixEns.add(prenom);
		panChoixEns.add(prenomEns);
		
		
		boutonPaneValider.add(bValider);
		panChoixEns.add(boutonPaneValider);
		bValider.addActionListener(this);
		
		
		
	    this.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String nom = this.nomEns.getText();
	    String prenom = this.prenomEns.getText();
		if(e.getSource() == bValider) {
			dispose();
			FenChoixEnsDonAux choix2 = new FenChoixEnsDonAux(admin,nom,prenom);
			choix2.setVisible(true);

		}
		
	}



}
