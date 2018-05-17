package FonctionAdmin;

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
import javax.swing.JTextField;

import AppilBureautique.FenAdmin;
import User.UserAdmin;
/**
 * Cette fenêtre permet de choisir l'enseignant ou l'étudiant dont on veut imprimer la fiche signalétique.
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FicheSignaletique extends JFrame implements ActionListener {

	/*
	 * Attributs 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panFiche = new JPanel();
	private UserAdmin admin;
	
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Prénom : ");
	private JTextField nomFiche = new JTextField();
	private JTextField prenomFiche = new JTextField();
	
	String[] tab = {"Enseignant", "Eleve"};
	private JComboBox<String> combo = new JComboBox<String>(tab); // Ajout d'un combobox
	
	
	private JButton valider = new JButton();
	private JPanel boutonPane = new JPanel();
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPaneRetour = new JPanel();
	
	
	/**
	 * Ce constructeur permet de choisir l'enseignant ou l'étudiant dont on veut imrpimer la fiche signalétique
	 * @param admin : UserAdmin de l'utilisateur de la session
	 */
	public FicheSignaletique(UserAdmin admin) {
		// Parametres de la fenetre
		this.setTitle("Imprimer une fiche signalétique"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panFiche.setBackground(Color.blue); // on définit sa couleur de fond
		this.setContentPane(panFiche); // on prévient notre JFrame que pan sera son content pane

		nomFiche.setColumns(10);
		prenomFiche.setColumns(10);
		
		panFiche.add(combo);
		panFiche.add(nom);
		panFiche.add(nomFiche);
		panFiche.add(prenom);
		panFiche.add(prenomFiche);
		
		combo.addItemListener(new ItemListener() { // Ajout d'un listener sur la combobox
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Vous avez sélectionné " + e.getItem());
				}
			}
		});
		
		boutonPane.add(valider);
	    valider.addActionListener(this);
	    panFiche.add(boutonPane);		
	    
	    boutonPaneRetour.add(bRetour);
		panFiche.add(boutonPaneRetour);
		bRetour.addActionListener(this);
		
	    this.admin = admin;
		this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide le choix de l'enseignant ou de l'étudiant dont on veut imrpimer la fiche signalétique. 
	 *  Le bouton retour permet de revenir à la fenêtre précédente. 
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String nom = this.nomFiche.getText();
		String prenom = this.prenomFiche.getText();
		if(e.getSource() == valider) {
			dispose();
			if(combo.getSelectedItem().equals(tab[0])) {
				FenChoixEnsFicheAux fenChoix = new FenChoixEnsFicheAux(admin,nom,prenom);
				fenChoix.setVisible(true);
			}
			if(combo.getSelectedItem().equals(tab[1])) {
				FenChoixEtuFicheAux fenChoix = new FenChoixEtuFicheAux(admin,nom,prenom);
				fenChoix.setVisible(true);
			}
		}
		if(e.getSource()==bRetour) {
			dispose();
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);
		}
	}
	
}
