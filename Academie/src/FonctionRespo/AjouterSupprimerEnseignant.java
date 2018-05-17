package FonctionRespo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import User.UserResponsable;
/**
 * Cette fen�tre permet d'ajouter ou supprimer un enseignant d'un d�partement. 
 * Cette classe h�rite de JFrame qui impl�mente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class AjouterSupprimerEnseignant extends JFrame implements ActionListener  {

	/*
	 * Attributs
	 */
	// m�me construction que les classes pr�c�dentes.
	private static final long serialVersionUID = 1L;
	private JPanel panneau = new JPanel();
	private UserResponsable respo;
	
	private String[] tab = {"Ajouter", "Supprimer"};
	private JComboBox<String> combo = new JComboBox<String>(tab); // Ajout d'un combobox
	private JLabel label = new JLabel();
	
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Pr�nom : ");
	
	private JButton valider = new JButton();
	private JPanel boutonPane = new JPanel();
	
	/**
	 * Ce constructeur permet d'ajouter ou de supprimer un enseignant d'un d�partement. 
	 * @param respo
	 */
	public AjouterSupprimerEnseignant(UserResponsable respo) {
		// Parametres de la fenetre
		this.setTitle("Ajouter ou supprimer un enseignant du d�partement"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panneau.setBackground(Color.green); // on d�finit sa couleur de fond
		this.setContentPane(panneau); // on pr�vient notre JFrame que pan sera son content pane
		
		
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					label.setText("Vous avez s�lectionn� " + e.getItem());
				}
			}
		});
				
		panneau.add(combo);
				
	    boutonPane.add(valider);
	    valider.addActionListener(this);
	    panneau.add(boutonPane);
	    
	   
	    
	    this.setVisible(true);
	}

	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * 	Le bouton valider confirme la suppression ou l'ajout d'un enseignant � un d�partement. 
	 * Le bouton retour revient � la page pr�c�dente.
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == valider) {
			String nom = this.nom.getText();
		    String prenom = this.prenom.getText();
		    
			
			try {
				
				if(combo.getSelectedItem().equals(tab[0])) {
					respo.ajouterEnseignant(nom, prenom,true);
				}
				
				if(combo.getSelectedItem().equals(tab[1])) {
					respo.supprimerEnseignant(nom, prenom);
				}
				
			} catch (Exception a) {
				a.printStackTrace();
			}		
		}
		
	}
}
