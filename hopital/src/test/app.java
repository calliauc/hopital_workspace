package test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.DAOCompte;
import dao.DAOPatient;
import dao.DAOVisite;

import model.Compte;
import model.Medecin;
import model.Patient;
import model.Secretaire;
import model.Visite;


public class app {


	static Compte connected = null;
	static DAOCompte daoC = new DAOCompte();
	static DAOPatient daoP = new DAOPatient();
	static DAOVisite daoA = new DAOVisite();
	
	static int salleMedecin = 0;

	static boolean secretaireEnPause;
	static List<Patient> fileAttente = new ArrayList<Patient>();

	public static void main(String[] args) {
		//System.out.println("pause");
		//partirPause() ;

		connexionHopital();

	}

	public static String saisieString(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextLine();
	}

	public static double saisieDouble(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextDouble();
	}

	public static int saisieInt(String msg) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(msg);
		return sc.nextInt();
	}




	public static void connexionHopital() {

		// Choix patient/medecin/secretaire
		System.out.println(" v Connexion au systeme hospitalier v");
		String login = saisieString("Saisir votre login");
		String password = saisieString("Saisir votre password");
		connected = daoC.seConnecter(login, password);

		if(connected instanceof Medecin) {
			while (salleMedecin != 1 || salleMedecin != 2)
				salleMedecin = saisieInt("Allez vous consulter dans la salle 1 ou 2 ?");
			menuMedecin();
		}
		else if(connected instanceof Secretaire) {
			menuSecretaire();
		}
		else if(connected ==null) {
			System.out.println("Identifiants invalides !");
		}

		connexionHopital();

	}




	// SECRETAIRE
	public static void connexionSecretaire() {
		System.out.println("Connexion secretaire");
		String login = saisieString("Saisir login secretaire : ");
		String password = saisieString("Saisie mot de passe : ");
		connected= daoC.seConnecter(login, password);

		if(connected instanceof Secretaire) {
			menuSecretaire();
		}else if(connected instanceof Medecin) {
			System.out.println("Mauvaise page de connexion");
		} else if(connected ==null) 
		{
			System.out.println("Identifiants invalides");
		}
		
	}


	public static void menuSecretaire() {

		System.out.println("Menu secretaire");
		System.out.println("1 - Creer un rendez-vous");
		System.out.println("2 - Afficher file d'attente");
		System.out.println("3 - Partir en pause");
		System.out.println("4 - Se deconnecter");

		int choix = saisieInt("Choisir une opération");
		switch (choix)
		{
		case 1 : creerRdv(); break;
		case 2 : afficherFile(); break;
		case 3 : partirPause(); break;
		case 4 : connected = null; connexionHopital(); break;
		}

		menuSecretaire();
	}

	private static void creerRdv() {
		List<Patient> listePatients = new ArrayList<Patient>();
		listePatients = daoP.findAll();

		int idPatient = saisieInt("ID du patient ?");
		boolean patientConnu=false;
		for (Patient p : listePatients) {
			if (idPatient == p.getId()) {
				patientConnu=true;
				fileAttente.add(p);
				System.out.println("Mr. / Mme. "+ p.getNom() +" a ete ajoute(e) a la file d'attente");
			}
		}
		if (!patientConnu) {
			creerComptePatient();
		}
	}

	public static void creerComptePatient() {
		Patient p = new Patient(null, null, null);
		p.setNom(saisieString("Nom du nouveau patient :"));
		p.setPrenom(saisieString("Prenom du nouveau patient :"));
		
		fileAttente.add(p);
		System.out.println("Mr. / Mme. "+p.getNom()+" a ete ajoute(e) a la base et a la file d'attente");
	}

	private static void afficherFile() {
		System.out.println("Il y a " + fileAttente.size() + " patients dans la file d'attente");
		for (Patient p : fileAttente) {
			System.out.println("Le patient " + fileAttente.indexOf(p)+1 +" est Mr. / Mme " + p.getNom());
		}
	}

	private static void partirPause() {
		secretaireEnPause = true ; 
		System.out.println("pause");
		List<Patient> listePatients = new ArrayList<Patient>();
		listePatients = daoP.findAll();
		
		ObjectOutputStream oos = null;
		
		try {
		      final FileOutputStream fichier = new FileOutputStream("liste des patients.txt");
		      oos = new ObjectOutputStream(fichier);
		      /// oos.writeUTF("La secretaire est partie en pause à :");
		      
		      oos.writeObject(listePatients);
		      oos.flush();
		} 
		catch (final java.io.IOException e) {
		      e.printStackTrace();
		} 
		finally {
			try {
				if (oos != null) {
			          oos.flush();
			          oos.close();
			    }
			}
			catch (final IOException ex) {
			        ex.printStackTrace();
			}
		}
		
		rentrerDePause() ;
	}
	
	public static void rentrerDePause () 
	{
		secretaireEnPause = false ; 
		
	}
	
	

	private static Object LocalDate() {
		// TODO Auto-generated method stub
		return null;
	}

	// FIN SECRETAIRE

	public static void afficherVisitesPatient() {
		
		Integer patientId= saisieInt("Entrer l'id du patient");
		List<Visite> patientVisites = DAOVisite.VisitefindByPatient(patientId);
		System.out.println(patientVisites);

	}

	//MEDECIN


	public static void menuMedecin() {

		System.out.println("Menu medecin");
		System.out.println("1 - Faire entrer le patient suivant");
		System.out.println("2 - Afficher le patient suivant");
		System.out.println("3 - AZfficher la file d'attente");
		System.out.println("4 - Sauvegarder vos dernières visites");
		System.out.println("5 - Se deconnecter");

		switch(saisieInt("Choix ?")) {
		case 1:
			patientSuivant();
			break;
		case 2:
			afficherProchainPatient();
			break;
		case 3:
			afficherFileAttente();
			break;
		case 4:
			sauvegarderListeVisites();
			break;
		case 5:
			connected = null;
			salleMedecin = 0;
			connexionHopital();
			break;		
		default :
			System.out.println("Cette operation n'existe pas");
			break;

		}
	}

	private static void patientSuivant() {

	}

	private static void afficherProchainPatient() {

	}

	private static void afficherFileAttente() {

	}


	private static void sauvegarderListeVisites() {

	}


	//FIN MEDECIN

}
