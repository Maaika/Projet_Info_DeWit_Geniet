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
 * Cette fen�tre permet � un administrateur d'ajouter un enseignant � la base de donn�es 
 * Cette classe h�rite de JFrame et impl�mente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class AjoutEtudiant extends JFrame implements ActionListener{

	/*
	 *  attributs de la fonction cr�er un �tudiant
	 */
	private static final long serialVersionUID = 1L;		// attribut h�rit� de JFrame
	private UserAdmin admin;		// attribut UserAdmin de l'utilisateur qui se connecte
	private JPanel panEt = new JPanel();		// panneau de fond

	// D�finition de textes :
	private JLabel label = new JLabel();
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Pr�nom : ");
	private JLabel date = new JLabel("Ann�e entr�e coll�ge : ");
	private JLabel college = new JLabel("Coll�ge  : ");
	private JTextField nomEt = new JTextField();
	
	// D�finition de zones de texte :
	private JTextField prenomEt = new JTextField();
	private JTextField dateEt = new JTextField("2000-1-1");
	
	private JComboBox<String> combo; // Ajout d'un combobox
	
	// D�finition d'un bouton de retour � la fen�tre pr�c�dante et d'un bouton valider ainsi que des panneaux les comprenant :
	private JButton valider = new JButton();
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPane = new JPanel();
	private JPanel boutonPaneRetour = new JPanel();

	/**
	 * Cette m�thode permet � l'administrateur d'ajouter un �tudiant � la base de donn�es 
	 * @param admin : UserAdmin qui vient de se connecter
	 */
	public AjoutEtudiant(UserAdmin admin) {
		this.admin = admin;
		
		// Param�tres de la fen�tre
		this.setTitle("Ajouter un enseignant � la base de donn�es"); 		// Modifier le titre de la fenetre
		this.setSize(400,200);		 // Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); 		// Centrer la fenetre par rapport a l'ecran de l'ordi
		panEt.setBackground(Color.blue); 		// on d�finit sa couleur de fond
		this.setContentPane(panEt);		 // on pr�vient notre JFrame que pan sera son content pane

		
		// On d�finit un combobox par d�faut
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		this.combo = new JComboBox<String>(model);
		
		Connection conn = ConnectionJdbc.getInstance();		// Connexion � la base de donn�es 
		
		try {
			Statement st = conn.createStatement();
	    	ResultSet result = st.executeQuery("SELECT nom FROM college");		// Requ�te SQL 
	    	
	    	while(result.next()) {
	    		combo.addItem(result.getString("nom"));		// On ajoute les noms des coll�ges dans la combobox
	    	}
	    	result.close();
	    	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// On instancie un ItemListener qui permet de r�cup�rer l'item s�lectionn� dans la combobox 
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					label.setText("Vous avez s�lectionn� " + e.getItem());
				}
			}
		});
				
		
		// On d�finit la taille des zones de texte :
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
	    
		// Action Listener permet de d�finir l'action avec la m�thode impl�ment�e quand l'on clique sur les boutons
	    valider.addActionListener(this);
	    bRetour.addActionListener(this);
		
		// On rend la fen�tre visible 
	    this.setVisible(true);
	}

	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide l'ajout d'un �tudiant � la base de donn�es ou l'on retourne � la page pr�c�dente. 
	 *  @param e : il s'agit d'une action effectu�e
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
