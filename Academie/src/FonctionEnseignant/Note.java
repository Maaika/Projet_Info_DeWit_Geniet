package FonctionEnseignant;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ConnectionJdbc.ConnectionJdbc;
import Donnees.Etudiant;
import Donnees.Matiere;
import User.UserEnseignant;
/**
 * Cette fen�tre permet d'entrer la note d'un �tudiant dans la base de donn�es.
 * Cette classe h�rite de JFrame qui impl�mente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class Note extends JFrame implements ActionListener  {
	
	/*
	 *  attributs
	 */
	// m�me construction que les classes pr�c�dentes
	private static final long serialVersionUID = 1L;
	private JPanel panNote = new JPanel();
	private JLabel label = new JLabel();
	
	private String matiere;
	
	private UserEnseignant ens;
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Pr�nom : ");
	private JLabel note = new JLabel("Note : ");
	private JTextField nomNote = new JTextField();
	private JTextField prenomNote = new JTextField();
	private JTextField noteNote = new JTextField();
	
	private JButton valider = new JButton();
	private JPanel boutonPane = new JPanel();
	
	/**
	 * Ce constructeur permet d'entrer la note d'un �l�ve dans la base de donn�es.
	 * @param ens : enseignant dont on veut modifier une information
	 */
	public Note(UserEnseignant ens) {
		// Parametres de la fenetre
		this.setTitle("Consulter, ajouter, modifier une note"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panNote.setBackground(Color.red); // on d�finit sa couleur de fond
		this.setContentPane(panNote); // on pr�vient notre JFrame que pan sera son content pane
		
		nomNote.setColumns(10);
		prenomNote.setColumns(10);
		
		panNote.add(nom);
		panNote.add(nomNote);
		panNote.add(prenom);
		panNote.add(prenomNote);
		
		// Combobox
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		final JComboBox<String> combo = new JComboBox<String>(model);
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement st = conn.createStatement();
	    	ResultSet result = st.executeQuery("SELECT nom FROM matiere");	// on ajoute le nom des mati�res dans la combobox
	    	
	    	while(result.next()) {
	    		combo.addItem(result.getString("nom"));
	    	}
	    	result.close();
	    	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					label.setText("Vous avez s�lectionn� " + e.getItem());
				}
			}
		});
				
		panNote.add(combo);
		this.matiere = (String) combo.getSelectedItem();
		
	    boutonPane.add(valider);
	    valider.addActionListener(this);
	    panNote.add(boutonPane);
	    
	    this.setVisible(true);
	}

	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * 	Le bouton valider permet d'entrer la note d'un �l�ve dans la base de donn�es. 
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == valider) {
			String nom = nomNote.getText();
		    String prenom = prenomNote.getText();
		    Etudiant et = new Etudiant(nom, prenom);
		    Matiere mat = new Matiere(matiere); // on transforme le nom de la mati�re en un objet mati�re pour la fonction ajouterNote
		    
			Connection conn = ConnectionJdbc.getInstance();
			
			try {
				Statement st = conn.createStatement();
				// on s�lectionne la valeur de la note d'un �l�ve selon la mati�re choisie dans la combobox. 
		    	ResultSet result = st.executeQuery("SELECT n.valeur FROM note n JOIN etudiant e ON n.id_etudiant = e.id JOIN matiere m ON m.id_matiere = m.id WHERE e.nom LIKE '"+nom+"' AND e.prenom ILIKE '"+prenom+"' AND m.nom = '"+matiere+"' ");		
		    	
		    	if(result == null) {
		    		noteNote.setColumns(10);
		    		panNote.add(note);
		    		panNote.add(noteNote);
		    		String valeur = noteNote.getText();
		    		double value = Double.parseDouble(valeur); // on transforme notre String en double pour la fonction ajouterNote
		    		
		    		ens.ajouterNote(et, mat, value);
		    	}
		    	if(result != null) {
		    		// on peut afficher la note
		    	}
		    	result.close();
		    	conn.close();
			} catch (Exception a) {
				a.printStackTrace();
			}		
		}
		
	}


}
