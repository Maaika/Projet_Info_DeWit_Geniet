package FonctionAdmin;

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
 * Cette fen�tre permet � un administrateur de la calculer la distance entre l'adresse d'un enseignant et l'adresse d'un coll�ge.
 * Cette classe h�rite de JFrame et impl�mente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class CalculerDistance extends JFrame implements ActionListener {

	// idem des commentaires des trois classes pr�c�dentes 
	private static final long serialVersionUID = 1L;
	JPanel panDist = new JPanel();
	private UserAdmin admin;
	
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Pr�nom : ");
	private JTextField nomDist = new JTextField();
	private JTextField prenomDist = new JTextField();
	
	private JButton valider = new JButton();
	private JPanel boutonPane = new JPanel();
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPaneRetour = new JPanel();
	
	
	/**
	 * Cette m�thode permet de calculer la distance entre le domicile d'un enseignant et l'adresse d'un coll�ge. 
	 * @param admin : UserAdmin de l'utilisateur qui se connecte
	 */
	public CalculerDistance(UserAdmin admin) {
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Caluler une distance "); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panDist.setBackground(Color.blue); // on d�finit sa couleur de fond
		this.setContentPane(panDist); // on pr�vient notre JFrame que pan sera son content pane

		
		// m�me construction que les classes pr�c�dentes
		nomDist.setColumns(10);
		prenomDist.setColumns(10);
		
		panDist.add(nom);
		panDist.add(nomDist);
		panDist.add(prenom);
		panDist.add(prenomDist);
		
		boutonPane.add(valider);
	    valider.addActionListener(this);
	    panDist.add(boutonPane);	
	    	    
	    boutonPaneRetour.add(bRetour);
		panDist.add(boutonPaneRetour);
		bRetour.addActionListener(this);
		
		this.setVisible(true);
	}

	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide le calcul d'une distance ou l'on retourne � la page pr�c�dente. 
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == valider) {
			dispose();
			String nom = nomDist.getText();
		    String prenom = prenomDist.getText();
		    Enseignant ens = new Enseignant(nom, prenom);
		    double[] dis = this.admin.calculerDistance(ens);
		    FenAffichDist fenAffich = new FenAffichDist(dis[0],dis[1]);
		    fenAffich.setVisible(true);
		    
		    
		}
		if(e.getSource()==bRetour) {
			dispose();
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);
		}
	}
	
}
