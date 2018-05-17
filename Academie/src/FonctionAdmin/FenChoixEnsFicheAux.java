package FonctionAdmin;

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

import AppilBureautique.FenAdmin;
import ConnectionJdbc.ConnectionJdbc;
import Donnees.Enseignant;
import User.UserAdmin;
/**
 * Cette fenêtre permet de choisir l'enseignant parmi un menu déroulant en vue d'imprimer leur fiche signalétique.
 * Cette classe hérite de JFrame qui implémente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenChoixEnsFicheAux extends JFrame implements ActionListener{
	
	/*
	 *  attributs de la fonction créer un enseignant
	 */
	// même construction que les classes précédentes 
	private static final long serialVersionUID = 1L;
	UserAdmin admin;
	private JPanel panChoixEns = new JPanel();
	private JLabel label = new JLabel();
	private JComboBox<String> combo ; // Ajout d'un combobox
	private JButton bValider = new JButton ("Valider");
	
	private JPanel boutonPaneValider = new JPanel();
	
	
	/**
	 * Ce constructeur permet de choisir l'enseignant dont on imprime la fiche signalétique. 
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param nom : nom de l'enseignant dont on veut modifier les informations
	 * @param prenom : prenom de l'enseignant dont on veut modifier les informations
	 */
	public FenChoixEnsFicheAux(UserAdmin admin, String nom, String prenom) {
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Choix de l'enseignant"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panChoixEns.setBackground(Color.LIGHT_GRAY); // on définit sa couleur de fond
		this.setContentPane(panChoixEns); // on prévient notre JFrame que pan sera son content pane

		
		// Combobox
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		this.combo = new JComboBox<String>(model);
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement st = conn.createStatement();
			Statement state = conn.createStatement();
			Statement state2 = conn.createStatement();
	    	ResultSet result = st.executeQuery("SELECT * FROM enseignant WHERE nom ILIKE '" + nom + "' AND prenom ILIKE '" + prenom + "'");
	    	ResultSet result2;
	    	ResultSet result3;
	    	String s;
	    	while(result.next()) {
	    		result2 = state.executeQuery("SELECT nom FROM college WHERE numero_academique = " + result.getInt("id_college_principal"));
	    		result3 = state2.executeQuery("SELECT nom FROM college WHERE numero_academique = " + result.getInt("id_college_secondaire"));
				result2.next();
				s=result.getInt("id")+"   "+result.getString("prenom")+"   "+result.getString("nom")+"     "+result2.getString("nom");
				if(result3.next()) {
					s+="   "+result3.getString("nom");
				}
				combo.addItem(s);
					
	    	}
	    	result.close();
	    	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		combo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					label.setText("Vous avez sélectionné " + e.getItem());
				}
			}
		});
		
		panChoixEns.add(combo);
				
		boutonPaneValider.add(bValider);
		panChoixEns.add(boutonPaneValider);
		bValider.addActionListener(this);
		
			
	    this.setVisible(true);
	}


	/**
	 *  Cette méthode définit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide le choix de l'enseignant dont on imprime la fiche signalétique.
	 *  @param e : il s'agit d'une action effectuée
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int ens = Integer.parseInt(combo.getSelectedItem().toString().split(" ")[0]); 
	    Enseignant enseignant = new Enseignant(ens);
		if(e.getSource() == bValider) {
			dispose();
			this.admin.imprimerFicheSignaletiqueEnseignant(enseignant);
			FenAdmin fenAd = new FenAdmin(this.admin.id, this.admin.getMdp());
			fenAd.setVisible(true);


		}
		
	}


}
