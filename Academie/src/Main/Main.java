package Main;


import AppilBureautique.Fen;

public class Main {

	public static void main(String[] args) {
		Fen fen = new Fen();
		
		fen.setVisible(true);

		
		// TODO Auto-generated method stub
		
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Statuts : \n"+"1 : admin | 2 : enseignant | 3 : responsable | 4 :étudiant");
		int statut = 0;
		while(statut<=0 || statut > 5) {
			System.out.println("Votre statut?");
			statut = sc.nextInt();
			sc.nextLine();
		}
		System.out.println("Identifiant");
		String id = sc.nextLine();
		System.out.println("Mot de passe");
		String mdp = sc.nextLine();
		
		if (statut==1) {
			int action = 0;
			while(action!=10) {
			UserAdmin admin = new UserAdmin(id,mdp);
			System.out.println("Que voulez vous faire? \n-----------------------------");
			System.out.println("1 : Creer un nouvel étudiant");
			System.out.println("2 : Supprimer un étudiant");
			System.out.println("3 : Creer un nouvel enseignant");
			System.out.println("4 : Supprimer un enseignant");
			System.out.println("5 : Creer un nouveau collège");
			System.out.println("6 : Imprimer une fiche signalétique");
			System.out.println("7 : Calculer une distance");
			System.out.println("8 : Modifier les données d'un étudiant");
			System.out.println("9 : Modifier les données d'un enseignant");
			System.out.println("10 : Deconnexion");
			action = 0;
			
				while(action <=0 || action > 10) {
					action=sc.nextInt();
					sc.nextLine();
				}
				
				if (action == 1) {
					System.out.println("nom :");
					String nom = sc.nextLine();
					System.out.println("prenom :");
					String prenom = sc.nextLine();
					System.out.println("college :");
					College college = new College(sc.nextLine());
					System.out.println("date d'entree au college (aaaa-[m]m-[j]j):");
					Date anneeEntreeCollege = Date.valueOf(sc.nextLine());
					System.out.println("mail :");
					String mail = sc.nextLine();
					System.out.println("telephone :");
					String telephone = sc.nextLine();
					
					admin.creerEtudiant(nom, prenom, college, anneeEntreeCollege, mail, telephone);
					
				}
				
				if (action == 2) {
					
					System.out.println("nom :");
					String nom = sc.nextLine();
					System.out.println("prenom :");
					String prenom = sc.nextLine();
					admin.supprimerEtudiant(nom, prenom);
					
				}
				
				if (action == 3) {
					System.out.println("nom :");
					String nom = sc.nextLine();
					System.out.println("prenom :");
					String prenom = sc.nextLine();
					System.out.println("college principal :");
					College collegePrincipal = new College(sc.nextLine());
					System.out.println("departement Principal :");
					String dept = sc.nextLine();
					System.out.println("matiere enseignée :");
					String matiere = sc.nextLine();
					
					
					System.out.println("date de prise de fonction (aaaa-[m]m-[j]j):");
					Date datePriseDeFonction = Date.valueOf(sc.nextLine());
					System.out.println("adresse :");
					String adresse = sc.nextLine();
					System.out.println("mail :");
					String mail = sc.nextLine();
					System.out.println("telephone :");
					String telephone = sc.nextLine();
					
					String choix ="";
					System.out.println("College secondaire? o/n");
					while(!choix.equals("o")&&!choix.equals("n")) {
						
						choix = sc.nextLine();
					}
					if (choix=="o") {
						College collegeSecondaire = new College(sc.nextLine());
						System.out.println("departement Secondaire :");
						String deptSec = sc.nextLine();
						admin.creerEnseignant(nom, prenom, dept, deptSec, datePriseDeFonction, adresse, collegePrincipal, collegeSecondaire, mail, telephone);
					}
					else {
						admin.creerEnseignant(nom, prenom, dept, datePriseDeFonction, adresse, collegePrincipal, mail, telephone,matiere);
						
					}
					
				}
				
				if (action == 4) {
					
					System.out.println("nom :");
					String nom = sc.nextLine();
					System.out.println("prenom :");
					String prenom = sc.nextLine();
					admin.supprimerEnseignant(nom, prenom);
					
				}
				
				if (action == 5) {
					
					System.out.println("nom du collège :");
					String nom = sc.nextLine();
					System.out.println("Site internet : ");
					String siteInternet = sc.nextLine();
					System.out.println("Adresse : ");
					String adresse = sc.nextLine();
					admin.creerCollege(nom, adresse, siteInternet);
					
				}
				
				if (action == 6) {
					System.out.println("1 : Imprimer la fiche d'un étudiant \n2 : Imprimer la fiche signalétique d'un enseignant");
					int choix=0;
					while(choix<1 || choix>2) {
						choix = sc.nextInt();
						sc.nextLine();
					}
					
					System.out.println("nom :");
					String nom = sc.nextLine();
					System.out.println("prenom :");
					String prenom = sc.nextLine();
		
					if (choix == 1) {
						admin.imprimerFicheSignaletiqueEtudiant(nom, prenom);
					}
					
					if (choix ==2 ) {
						admin.imprimerFicheSignaletiqueEnseignant(nom, prenom);
					}
					
				}
				if (action == 7) {
					
					System.out.println("nom :");
					String nom = sc.nextLine();
					System.out.println("prenom :");
					String prenom = sc.nextLine();
					
					Enseignant ens = new Enseignant(nom,prenom);
					double[] dist = admin.calculerDistance(ens);
					System.out.println(dist[0]+"  ;  "+dist[1]);
					
				}
				
				if (action == 8) {
					System.out.println("Choisir l'information à modifier : \n -----------------------------");
					System.out.println("1 : Mail");
					System.out.println("2 : Telephone");
					System.out.println("3 : College");
					System.out.println("4 : Password");
					System.out.println("5 : Retour");
					int choix = sc.nextInt();
					while(choix<1 || choix>5) {
						choix = sc.nextInt();
						sc.nextLine();
					}
					
					if (choix==1) {
						System.out.println("Nom de l'étudiant : ");
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						System.out.println("Mail : ");
						String mail = sc.nextLine();
						
						admin.modifierMailEtudiant(mail, nom, prenom);
						
					}
					
					if (choix==2) {
						
						System.out.println("Nom de l'étudiant : ");
						sc.nextLine();
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						System.out.println("Telephone : ");
						String telephone = sc.nextLine();
						
						admin.modifierTelephoneEtudiant(telephone, nom, prenom);
						
					}
					
					if (choix==3) {
						
						System.out.println("Nom de l'étudiant : ");
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						System.out.println("Collège : ");
						String college = sc.nextLine();
						
						admin.modifierCollegeEtudiant(college, nom, prenom);
						
					}
					
					if (choix==4) {
						System.out.println("Nom de l'étudiant : ");
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						
						admin.modifierPasswordEtudiant( nom, prenom);
						
					}
					
				}
				
				if (action == 9) {
					System.out.println("Choisir l'information à modifier : \n -----------------------------");
					System.out.println("1 : Mail");
					System.out.println("2 : Telephone");
					System.out.println("3 : College Principal");
					System.out.println("4 : College Secondaire");
					System.out.println("5 : password");
					System.out.println("6 : Retour");
					int choix = sc.nextInt();
					while(choix<1 || choix>6) {
						choix = sc.nextInt();
						sc.nextLine();
					}
					
					if (choix==1) {
						System.out.println("Nom de l'enseignant : ");
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						System.out.println("Mail : ");
						String mail = sc.nextLine();
						
						admin.modifierMailEtudiant(mail, nom, prenom);
						
					}
					
					if (choix==2) {
						
						System.out.println("Nom de l'étudiant : ");
						sc.nextLine();
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						System.out.println("Telephone : ");
						String telephone = sc.nextLine();
						
						admin.modifierTelephoneEtudiant(telephone, nom, prenom);
						
					}
					
					if (choix==3) {
						
						System.out.println("Nom de l'étudiant : ");
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						System.out.println("Collège : ");
						String college = sc.nextLine();
						
						admin.modifierCollegeEtudiant(college, nom, prenom);
						
					}
					
					if (choix==4) {
						System.out.println("Nom de l'étudiant : ");
						String nom = sc.nextLine();
						System.out.println("Prenom de l'étudiant : ");
						String prenom = sc.nextLine();
						
						admin.modifierPasswordEtudiant( nom, prenom);
						
					}
				}
			}

			
		}
		
		
		
		if (statut==2) {
			UserEnseignant enseignant = new UserEnseignant(id,mdp);
			
			int action = 0;
			while(action!=3) {
			System.out.println("Que voulez vous faire?");
			System.out.println("1 : Ajouter une note");
			System.out.println("2 : Voir et modifier vos informations personnelles");
			System.out.println("3 : deconnexion");
			
			action = 0;
				while(action < 1 || action > 4) {
					action=sc.nextInt();
					sc.nextLine();
				}
				if(action==1) {
					System.out.println("nom de l'étudiant:");
					String nom = sc.nextLine();
					System.out.println("prenom de l'étudiant:");
					String prenom = sc.nextLine();
					Etudiant et = new Etudiant(nom, prenom);
					System.out.println("note :");
					double valeur = sc.nextDouble();
					sc.nextLine();
					Matiere mat = new Matiere(enseignant.enseignant.matiere);
					
					enseignant.ajouterNote(et, mat, valeur);
				}
				
				if(action==2) {
					enseignant.afficherDonnees();
					
					System.out.println("1 : modifier telephone");
					System.out.println("2 : modifier mail");
					System.out.println("3 : modifier adresse");
					System.out.println("4 : ne rien faire");
					int choix = sc.nextInt();
					sc.nextLine();
					if(choix==1) {
						System.out.println("nouveau téléphone:");
						String telephone = sc.nextLine();
						enseignant.modifierTelephone(telephone);
					}
					if(choix==2) {
						System.out.println("nouveau mail:");
						String mail = sc.nextLine();
						enseignant.modifierMail(mail);
					}
					if(choix==3) {
						System.out.println("nouvelle adresse:");
						String adresse = sc.nextLine();
						enseignant.modifierAdresse(adresse);
					}
					
					
				}
			}
			
			
		}
		
		
		if (statut==3) {
			UserResponsable responsable = new UserResponsable(id,mdp);
			
			int action = 0;
			while(action!=3) {
			System.out.println("Que voulez vous faire?");
			System.out.println("1 : Ajouter un enseignant");
			System.out.println("2 : Voir les informations du département");
			System.out.println("3 : deconnexion");
			
			action = 0;
				while(action < 1 || action > 4) {
					action=sc.nextInt();
					sc.nextLine();
				}
				
				if(action==1) {
					System.out.println("nom de l'enseignant:");
					String nom = sc.nextLine();
					System.out.println("prenom de l'enseignant:");
					String prenom = sc.nextLine();
					
					responsable.ajouterEnseignant(nom, prenom);
				}
				
				if(action==2) {
					responsable.afficherInfos();
				}
			}
		}
		
		
		if (statut==4) {
			UserEtudiant etudiant = new UserEtudiant(id,mdp);
			
			int action = 0;
			while(action==2) {
			System.out.println("Que voulez vous faire?");
			System.out.println("1 : Voir et modifier informations");
			System.out.println("2 : déconnexion");
			
			action = 0;
				while(action < 1 || action > 3) {
					action=sc.nextInt();
					sc.nextLine();
				}
				if(action==1) {
					System.out.println("Voulez vous voir vos matières? o/n");
					String choix = "";
					while(choix!="o"&&choix!="n") {
						
						choix = sc.nextLine();
					}
					etudiant.afficherInfo(choix=="o");
					
					System.out.println("Que voulez vous faire?");
					System.out.println("1 : Modifier mail");
					System.out.println("2 : Modifier telephone");
					System.out.println("3 : Ne rien faire");
					int action2 = 0;
					while(action2 < 1 || action2 > 3) {
						action2=sc.nextInt();
						sc.nextLine();
					}
					if (action2==1) {
						System.out.println("nouveau mail:");
						String mail = sc.nextLine();
						etudiant.modifierMail(mail);
					}
					if (action2==2) {
						System.out.println("nouveau telephone:");
						String telephone = sc.nextLine();
						etudiant.modifierTelephone(telephone);
					}
					
					
				}
			}

	}
	sc.close();	*/
}
	

}
