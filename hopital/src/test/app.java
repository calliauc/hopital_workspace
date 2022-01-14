package test;

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


public class app {
	
	
	static Compte connected = null;
	static DAOCompte daoC = new DAOCompte();
	static DAOPatient daoP = new DAOPatient();
	static DAOVisite daoA = new DAOVisite();
	
	static boolean secretaireEnPause;
	static List<Patient> fileAttente = new ArrayList<Patient>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		connectionHopital();

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

	
	
	public static void connectionHopital() {
		// Choix patient/medecin/secretaire
		System.out.println(" v Connexion au systeme hospitalier v");
		String login = saisieString("Saisir votre login");
		String password = saisieString("Saisir votre password");
		connected = daoC.seConnecter(login, password);

		if(connected instanceof Medecin) {menuMedecin();}
		else if(connected instanceof Secretaire) {menuSecretaire();}
		else if(connected ==null) 
		{
			System.out.println("Identifiants invalides !");
		}

		connectionHopital();

	}


	// SECRETAIRE
	
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
		case 4 : connected = null; connectionHopital(); break;
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
				System.out.println("Monsieur "+ p.getNom() +" a ete ajoute a la file d'attente");
			}
		}
		if (!patientConnu) {
			creerComptePatient();
		}
	}

	public static void creerComptePatient() {
		
	}

	private static void afficherFile() {

	}

	private static void partirPause() {

	}

	// FIN SECRETAIRE


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
			connectionHopital();
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
