package AppilBureautique;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ConnectionJdbc.ConnectionJdbc;


/**
 * Cette fen�tre permet d'afficher la fen�tre de connexion � la base de donn�es de l'utilisateur. 
 * Cette classe h�rite de JFrame et impl�mente ActionListener. 
 * 
 * @author Apolline De Wit, Florent Geniet
 */
public class Fen extends JFrame implements ActionListener {
	
	/*
	 * Attributs
	 */
	private static final long serialVersionUID = 1L; 	// attribut h�rit� de JFrame.
	private JPanel fond = new JPanel(); 	// Ajout d'un panneau qui est le fond de la fen�tre.
	
	// Ajout d'un combobox (panneau d�roulant) :
	String[] tab = {"Administrateur", "Enseignant", "Responsable d'un d�partement", "Eleve"};
	private JComboBox<String> combo = new JComboBox<String>(tab); 
	
	// Ajout de textes :
	private JLabel texte = new JLabel("Vous etes : "); 
	private JLabel id = new JLabel("Identifiant : ");
	private JLabel mdp = new JLabel("Mot de passe : ");
	
	// Ajout de zones de textes (� remplir par l'utilisateur) :
	private JTextField identifiant = new JTextField();
	private JTextField motdepasse = new JTextField();
	
	// Ajout de boutons et des panneaux les comprenant pour les ins�rer sur le panneau de fond :
	private JButton b = new JButton ("Se connecter ");
	private JButton bQuitter = new JButton ("Quitter ");
	private JPanel boutonPane = new JPanel();
	private JPanel boutonPaneQuitter = new JPanel();
	
	
	/**
	 * Constructeur de la fen�tre Fen de connexion. 
	 */
	public Fen() {
		// Param�tres de la fen�tre
		this.setTitle("Authentification requise"); 		// Modifier le titre de la fenetre
		this.setSize(250,200); 		// Modifier la taille
		this.setResizable(false); 		// taille non modifiable par l'utilisateur
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// Un clic sur la croix rouge entra�ne la fermeture de la fen�tre
		this.setLocationRelativeTo(null); 		// Centrer la fen�tre par rapport a l'�cran de l'ordi
		fond.setBackground(Color.yellow);		// D�finition de la couleur du fond
		this.setContentPane(fond);		// D�finition du fond comme ContentPane (le conteneur de tout ce que l'on met dans la fen�tre)
		
		// On d�finit le nombre de colonnes (la taille) des zones de texte que l'on veut remplir :
		identifiant.setColumns(10);
		motdepasse.setColumns(10);
		
		boutonPane.add(b);		// on ajoute le bouton de connexion � un panneau
		boutonPaneQuitter.add(bQuitter);	// On ajoute le bouton pour quitter l'application � un panneau
		
		// On ajoute les �l�ments de connexion sur le panneau de fond
		this.getContentPane().add(texte);
		this.getContentPane().add(combo);
		this.getContentPane().add(id);
		this.getContentPane().add(identifiant);
		this.getContentPane().add(mdp);
		this.getContentPane().add(motdepasse);
		this.getContentPane().add(boutonPane);

		// Ajout d'un listener sur la combobox. Il permet de r�cup�rer l'�l�ment s�lectionn� dans la combobox.
		combo.addItemListener(new ItemListener() { 
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Vous avez s�lectionn� " + e.getItem());
				}
			}
		});
		
		// Action Listener permet de d�finir l'action avec la m�thode impl�ment�e quand l'on clique sur les boutons
		b.addActionListener(this);
		bQuitter.addActionListener(this);
		
		// On ajoute les panneaux contenant les boutons sur le fond 
		this.getContentPane().add(bQuitter);
		this.getContentPane().add(boutonPaneQuitter);
		
		// On rend la fen�tre fen visible (n�cessaire pour qu'elle s'affiche).
		this.setVisible(true);
	}

	/**
	 * Cette m�thode d�finit ce qu'il se passe lorsque l'on clique sur les boutons. 
	 * Si la connexion est accept�e, une fen�tre s'ouvre en fonction du statut de l'utilisateur.
	 * Le bouton 'quitter' permet de fermer l'application.
	 * @param e : il s'agit d'une action effectu�e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b) {	// Si l'utilisateur clique sur le bouton b 'se connecter'
			dispose(); 		// On ferme la fen�tre affich�e
			String login = identifiant.getText();		// On r�cup�re l'identifiant et le mot de passe entr�s par l'utilisateur
		    String password = motdepasse.getText();
		    
		    Connection conn = ConnectionJdbc.getInstance();		// On se connecte � la base de donn�e pour v�rifier leur exactitude en fonction du statut de l'utilisateur
		    try{    	
		    	if(combo.getSelectedItem().equals(tab[0])) {		// Cas o� l'utilisateur est un administrateur
		    		Statement st = conn.createStatement();
			    	ResultSet result = st.executeQuery("SELECT password FROM administrateur WHERE login ='"+login+"'");
			    	if(result.next()){
	                    String motDePasse = result.getString(1); 		// On r�cup�re le mot de passe dans la base de donn�es
	                if(motDePasse.equals(password)){		// Si le mot de passe est correct 
	                    JOptionPane.showMessageDialog(null,"Connexion r�ussie ! ","Success",JOptionPane.PLAIN_MESSAGE);
	                    FenAdmin fenAdmin = new FenAdmin(login, password);		// On peut alors g�n�rer et afficher la fen�tre des options de l'administrateur
						fenAdmin.setVisible(true);
	                }else {
	                    JOptionPane.showMessageDialog(null,"Mot de passe incorrect ! ","Error",1);
	                }
	                }else {
	                    JOptionPane.showMessageDialog(null,"Login incorrect ! ","Error",1);
	                }
	                    conn.close();
	            }
		    	if(combo.getSelectedItem().equals(tab[1])) {		// Cas o� l'utilisateur est un enseignant
		    		Statement st = conn.createStatement();
			    	ResultSet result = st.executeQuery("SELECT password FROM enseignant WHERE login LIKE '"+login+"'");
			    	if(result.next()){
	                    String motDePasse = result.getString(1);
	                if(motDePasse.equals(password)){
	                    JOptionPane.showMessageDialog(null,"Connexion r�ussie ! ","Success",JOptionPane.PLAIN_MESSAGE);
	                    FenEns fenEns = new FenEns(login, password); 		// On peut alors g�n�rer et afficher la fen�tre des options de l'enseignant
						fenEns.setVisible(true);
	                }else {
	                    JOptionPane.showMessageDialog(null,"Mot de passe incorrect ! ","Error",1);
	                }
	                }else {
	                    JOptionPane.showMessageDialog(null,"Login incorrect ! ","Error",1);
	                }
	                    conn.close();
	            }
		    	if(combo.getSelectedItem().equals(tab[2])) {		// Cas o� l'utilisateur est responsable d'un d�partement
		    		Statement st = conn.createStatement();
			    	ResultSet result = st.executeQuery("SELECT password FROM enseignant e JOIN college c ON e.id = c.responsable WHERE login ='"+login+"'");
			    	if(result.next()){
	                    String motDePasse = result.getString(1);
	                if(motDePasse.equals(password)){
	                    JOptionPane.showMessageDialog(null,"Connexion r�ussie ! ","Success",JOptionPane.PLAIN_MESSAGE);
	                    FenRespo fenRespo = new FenRespo(login, password);		// On peut alors g�n�rer et afficher la fen�tre des options du responsable d'un d�partement
						fenRespo.setVisible(true);
	                }else {
	                    JOptionPane.showMessageDialog(null,"Mot de passe incorrect ! ","Error",1);
	                }
	                }else {
	                    JOptionPane.showMessageDialog(null,"Login incorrect ! ","Error",1);
	                }
	                    conn.close();
				}
		    	if(combo.getSelectedItem().equals(tab[3])) { 		// Cas o� l'utilisateur est un �tudiant
		    		Statement st = conn.createStatement();
			    	ResultSet result = st.executeQuery("SELECT password FROM etudiant WHERE login ='"+login+"'");
			    	if(result.next()){
	                    String motDePasse = result.getString(1);
	                if(motDePasse.equals(password)){
	                    JOptionPane.showMessageDialog(null,"Connexion r�ussie ! ","Success",JOptionPane.PLAIN_MESSAGE);
	                	FenEleve fenEleve = new FenEleve(login, password);		// On peut alors g�n�rer et afficher la fen�tre des options de l'�tudiant
						fenEleve.setVisible(true);
	                }else {
	                    JOptionPane.showMessageDialog(null,"Mot de passe incorrect ! ","Error",1);
	                }
	                }else {
	                    JOptionPane.showMessageDialog(null,"Login incorrect ! ","Error",1);
	                }
	                    conn.close();
		    	}
		    }
		    catch (SQLException e4) {		// On g�re les exceptions
                System.out.println(e4.getMessage());
            }
		
		}
		if(e.getSource() == bQuitter) {		// Si l'on clique sur le bouton quitter, on quitte l'application, la fen�tre de connexion se ferme.
			dispose();
		}
	}
}
