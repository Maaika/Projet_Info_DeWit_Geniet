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
public class AjoutEnseignant extends JFrame implements ActionListener {

	/*
	 *  Attributs de la fonction cr�er un enseignant
	 */
	private static final long serialVersionUID = 1L;		// attribut h�rit� de JFrame
	private UserAdmin admin;		// attribut UserAdmin de l'utilisateur qui se connecte
	private JPanel panEns = new JPanel();		// panneau de fond
	
	// D�finition de textes :
	private JLabel label = new JLabel();
	private JLabel nom = new JLabel("Nom : ");
	private JLabel prenom = new JLabel("Pr�nom : ");
	private JLabel dpt = new JLabel("D�partement : ");
	private JLabel date = new JLabel("Date prise de fonction : ");
	private JLabel adresse = new JLabel("Adresse : ");
	private JLabel collegePc = new JLabel("Coll�ge principal : ");
	
	// D�finition de zones de texte :
	private JTextField nomEns = new JTextField();
	private JTextField prenomEns = new JTextField();
	private JTextField dptEns = new JTextField();
	private JTextField dateEns = new JTextField("2000-1-1");
	private JTextField adresseEns = new JTextField();
	
	private JComboBox<String> combo; // Ajout d'un combobox
	
	// D�finition d'un bouton de retour � la fen�tre pr�c�dante et d'un bouton valider ainsi que des panneaux les comprenant :
	private JButton valider = new JButton();
	private JButton bRetour = new JButton("Retour");
	private JPanel boutonPane = new JPanel();
	private JPanel boutonPaneRetour = new JPanel();
	
	/**
	 * Cette m�thode permet � l'administrateur d'ajouter un enseignant � la base de donn�es 
	 * @param admin : UserAdmin qui vient de se connecter
	 */
	public AjoutEnseignant(UserAdmin admin) {
		this.admin = admin;
		
		// Param�tres de la fen�tre
		this.setTitle("Ajouter un enseignant � la base de donn�es"); 		// Modifier le titre de la fenetre
		this.setSize(400,200);		 // Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		 // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); 		// Centrer la fenetre par rapport a l'ecran de l'ordi
		panEns.setBackground(Color.lightGray); 		// on d�finit sa couleur de fond
		this.setContentPane(panEns);		 // on pr�vient notre JFrame que pan sera son content pane

		
		// On d�finit une combobox par d�faut
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		this.combo = new JComboBox<String>(model);
		
		Connection conn = ConnectionJdbc.getInstance();		// On se connecte � la base de donn�es
		
		try {
			Statement st = conn.createStatement();		
	    	ResultSet result = st.executeQuery("SELECT nom FROM college");		// On cr�e une requ�te SQL
	    	
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
				
		
		// On d�finit la taille des zones de texte
		nomEns.setColumns(10);
		prenomEns.setColumns(10);
		dptEns.setColumns(10);
		dateEns.setColumns(10);
		adresseEns.setColumns(10);
		
		// On ajoute les boutons aux panneaux :
	    boutonPane.add(valider);
	    boutonPaneRetour.add(bRetour);
	    
		// On ajoute les panneaux au fond :
		panEns.add(nom);
		panEns.add(nomEns);
		panEns.add(prenom);
		panEns.add(prenomEns);
		panEns.add(dpt);
		panEns.add(dptEns);
		panEns.add(date);
		panEns.add(dateEns);
		panEns.add(adresse);
		panEns.add(adresseEns);
		panEns.add(collegePc);
		panEns.add(combo);
		panEns.add(boutonPane);
		panEns.add(boutonPaneRetour);
		
		
		// Action Listener permet de d�finir l'action avec la m�thode impl�ment�e quand l'on clique sur les boutons
	    valider.addActionListener(this);
	    bRetour.addActionListener(this);
		
		// On rend la fen�tre visible 
	    this.setVisible(true);
	}

	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide l'ajout d'un enseignant � la base de donn�es ou l'on retourne � la page pr�c�dente. 
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == valider) {
			dispose();
			String nom = nomEns.getText();
		    String prenom = prenomEns.getText();
		    String dpt = dptEns.getText();
		    String dat = dateEns.getText();
		    Date date = Date.valueOf(dat);
		    String adresse = adresseEns.getText();
		    String collegePc = combo.getSelectedItem().toString();
		    College collP = new College(collegePc);
			this.admin.creerEnseignant(nom, prenom, dpt, date, adresse, collP);
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
