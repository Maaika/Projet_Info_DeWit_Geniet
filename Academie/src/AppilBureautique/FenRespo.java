package AppilBureautique;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import User.UserResponsable;
/**
 * Cette fen�tre permet d'afficher les actions que peut effectuer le responsable d'un d�partement. 
 * Cette classe h�rite de JFrame et impl�mente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenRespo extends JFrame implements ActionListener {

	/*
	 * Attributs
	 */
	private static final long serialVersionUID = 1L;	// attribut h�rit� de JFrame
	protected UserResponsable respo;		// attribut UserResponsable de l'utilisateur qui se connecte
	private JPanel fond = new JPanel(); 	// instantiation d'un objet JPanel
	
	// D�finition des boutons de choix d'action pr�sents sur la fen�tre ainsi que des panneaux les comprenant :
	private JButton b1 = new JButton ("Voir les informations du d�partement");
	private JButton b2 = new JButton ("Modifier les informations du d�partement");
	private JPanel boutonPane1 = new JPanel();
	private JPanel boutonPane2 = new JPanel();
	
	private JLabel result = new JLabel("Moyenne : ");	
	private JTextField resultMoyenne = new JTextField();
	
	/**
	 *  Constructeur de la fen�tre pr�sentant les options du responsable d'un d�partement
	 *  @param identifiant : identifiant du responsable
	 * @param motdepasse :  mot de passe de connexion ddu responsable
	 */
	public FenRespo(String identifiant, String motdepasse) {
		this.respo = new UserResponsable(identifiant, motdepasse) ; 		// On initialise l'identifiant et le mot de passe de l'utilisateur qui s'est connect�
		
		// Param�tres de la fen�tre
		this.setTitle("D�partement"); 		// Modifier le titre de la fenetre
		this.setSize(400,200); 		// Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); 		// Centrer la fenetre par rapport a l'ecran de l'ordi
		fond.setBackground(Color.green); 		// on d�finit sa couleur de fond
		this.setContentPane(fond); 		// on pr�vient notre JFrame que pan sera son content pane
				
		// On ajoute les boutons aux panneaux et les panneaux au fond :
		boutonPane1.add(b1);
		boutonPane2.add(b2);
		fond.add(boutonPane1);
		fond.add(boutonPane2);
				
		// Action Listener permet de d�finir l'action avec la m�thode impl�ment�e quand l'on clique sur les boutons
		b1.addActionListener(this);
		b2.addActionListener(this);	
		
		// On rend la fen�tre visible
		this.setVisible(true);
	}
	
	/**
	 * Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * Ils correspondent � chaque action que peut r�aliser le responsable. 
	 * Nous n'avons pas eu le temps de finaliser leurs actions, elles n'aboutissent pas pour le moment. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1) {
			dispose();
			double moy = this.respo.departement.moyenne();
			Double moyen = new Double(moy);
			String moyenne = moyen.toString();
			fond.add(result);
			fond.add(resultMoyenne);
		    resultMoyenne.setText(moyenne); // J'ai tent� d'ajouter la moyenne � un textfield, mais cela n'a pas abouti
		}
		if(e.getSource() == b2) {
			dispose();
			
		}
	}
}
