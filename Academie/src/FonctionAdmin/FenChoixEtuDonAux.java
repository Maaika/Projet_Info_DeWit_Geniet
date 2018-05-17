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
import Donnees.Etudiant;
import User.UserAdmin;
/**
 * Cette fen�tre affiche une fen�tre o� l'on rentre le nom et le pr�nom de l'�tudiant dont on veut modifier les donn�es.
 * Cette classe h�rite de JFrame qui impl�mente ActionListener.
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class FenChoixEtuDonAux extends JFrame implements ActionListener{

	/*
	 *  attributs de la fonction cr�er un enseignant
	 */
	// m�me construction que les classes pr�c�dentes 
	private static final long serialVersionUID = 1L;
	UserAdmin admin;
	private JPanel panChoixEtu = new JPanel();
	private JLabel label = new JLabel();
	private JComboBox<String> combo ; // Ajout d'un combobox

	private JButton bValider = new JButton ("Valider");
	
	private JPanel boutonPaneValider = new JPanel();
	
	
	/**
	 *  Ce constructeur permet d'ins�rer le nom et le pr�nom d'un �tudiant dont on veut modifier les informations
	 * @param admin : UserAdmin de l'utilisateur de la session
	 * @param nom : nom de l'enseignant dont on veut modifier les informations
	 * @param prenom : prenom de l'enseignant dont on veut modifier les informations
	 */
	public FenChoixEtuDonAux(UserAdmin admin, String nom, String prenom) {
		this.admin = admin;
		// Parametres de la fenetre
		this.setTitle("Modifier le mail"); // Modifier le titre de la fenetre
		this.setSize(400,200); // Modifier la taille
		this.setResizable(false); // taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Un clic sur croix entraine une fermeture de la fenetre
		this.setLocationRelativeTo(null); // Centrer la fenetre par rapport a l'ecran de l'ordi
		panChoixEtu.setBackground(Color.LIGHT_GRAY); // on d�finit sa couleur de fond
		this.setContentPane(panChoixEtu); // on pr�vient notre JFrame que pan sera son content pane

		
		// Combobox
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		this.combo = new JComboBox<String>(model);
		
		Connection conn = ConnectionJdbc.getInstance();
		
		try {
			Statement st = conn.createStatement();
			Statement state = conn.createStatement();
	    	ResultSet result = st.executeQuery("SELECT * FROM etudiant WHERE nom ILIKE '" + nom + "' AND prenom ILIKE '" + prenom + "'");
	    	ResultSet result2;
	    	
	    	while(result.next()) {
	    		result2 = state.executeQuery("SELECT nom FROM college WHERE numero_academique = " + result.getInt("id_college"));
				result2.next();
	    		combo.addItem(result.getInt("id")+"   "+result.getString("nom")+"     "+result2.getString("nom"));
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
		
		panChoixEtu.add(combo);
		
		
		
		boutonPaneValider.add(bValider);
		panChoixEtu.add(boutonPaneValider);
		bValider.addActionListener(this);
		
		
		
	    this.setVisible(true);
	}

	/**
	 *  Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 *  On valide le choix de l'�tudiant. 
	 *  @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int et = Integer.parseInt(combo.getSelectedItem().toString().split(" ")[0]); 
	    Etudiant etudiant = new Etudiant(et);
		if(e.getSource() == bValider) {
			dispose();

			ModifDonneesEtu modifEtu = new ModifDonneesEtu(admin,etudiant);
			modifEtu.setVisible(true);

		}
		
	}



}
