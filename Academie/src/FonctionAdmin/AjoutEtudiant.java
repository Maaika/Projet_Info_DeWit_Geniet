package FonctionAdmin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AppilBureautique.FenAdmin;
import ConnectionJdbc.ConnectionJdbc;
import Donnees.College;
import User.UserAdmin;
/**
 * Cette fenêtre permet à un administrateur d'ajouter un enseignant à la base de données 
 * Cette classe hérite de JFrame et implémente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class AjoutEtudiant extends JFrame implements ActionListener{

	/*
	 *  attributs de la fonction créer un étudiant
	 */
	private static final long serialVersionUID = 1L;		// attribut hérité de JFrame
	private UserAdmin admin;		// attribut UserAdmin de l'utilisateur qui se connecte
	private JPanel panEt = new JPanel();		// panneau de fond

	// Définition de textes :
	private JLabel label = new JLabel();
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Prénom : ");
	private JLabel date = new JLabel("Année entrée collège : ");
	private JLabel college = new JLabel("Collège  : ");
	private JTextField nomEt = new JTextField();
	
	// Définition de zones de texte :
	private JTextField prenomEt = new JTextField();
	private JTextField dateEt = new JTextField("2000-1-1");
	
	private JComboBox<String> combo; // Ajout d'un combobox
	
	// Définition d'un bouton de retour à la fenêtre précédante et d'un bouton valider ainsi que des panneaux les comprenant :
	private JButton valider = new JButton();
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPane = new JPanel();
	private JPanel boutonPaneRetour = new JPanel();

	/**
	 * Cette méthode permet à l'administrateur d'ajouter un étudiant à la base de données 
	 * @param admin : UserAdmin qui vient de se connecter
	 */
	public AjoutEtudiant(UserAdmin admin) {
		this.admin = admin;
		
		// Paramètres de la fenêtre
		this.setTitle("Ajouter un enseignant à la base de données"); 		// Modifier le titre de la fenetre
		this.setSize(400,200);		 // Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); 		// Centrer la fenetre par rapport a l'ecran de l'ordi
		panEt.setBackground(Color.blue); 		// on définit sa couleur de fond
		this.setContentPane(panEt);		 // on prévient notre JFrame que pan sera son content pane

		
		// On définit un combobox par défaut
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		this.combo = new JComboBox<String>(model);
		
		Connection conn = ConnectionJdbc.getInstance();		// Connexion à la base de données 
		
		try {
			Statement st = conn.createStatement();
	    	ResultSet result = st.executeQuery("SELECT nom FROM college");		// Requête SQL 
	    	
	    	while(result.next()) {
	    		combo.addItem(result.getString("nom"));		// On ajoute les noms des collèges dans la combobox
	    	}
	    	result.close();
	    	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// On instancie un ItemListener qui permet de récupérer l'item sélectionné dans la combobox 
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					label.setText("Vous avez sélectionné " + e.getItem());
				}
			}
		});
				
		
		// On définit la taille des zones de texte :
		nomEt.setColumns(10);
		prenomEt.setColumns(10);
		dateEt.setColumns(10);
		
		// On ajoute les boutons aux panneaux :
	    boutonPane.add(valider);
	    boutonPaneRetour.add(bRetour);
	    
		// On ajoute les panneaux au fond :
		panEt.add(nom);
		panEt.add(nomEt);
		panEt.add(prenom);
		panEt.add(prenomEt);
		panEt.add(date);
		panEt.add(dateEt);
		panEt.add(college);
		panEt.add(combo);
	    
		// Action Listener permet de définir l'action avec la méthode implémentée quand l'on clique sur les boutons
	    valider.addActionListener(this);
	    bRetour.addActionListener(this);
		
		// On rend la fenêtre visible 
	    this.setVisible(true);
	}

	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide l'ajout d'un étudiant à la base de données ou l'on retourne à la page précédente. 
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == valider) {
			dispose();
			String nom = nomEt.getText();
		    String prenom = prenomEt.getText();
		    String college = combo.getSelectedItem().toString(); 
		    College coll = new College(college);
		    String dat = dateEt.getText();
		    Date date = Date.valueOf(dat);
			this.admin.creerEtudiant(nom, prenom, coll, date);
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
