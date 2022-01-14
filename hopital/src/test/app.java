package test;

import java.util.Scanner;

import model.Admin;
import model.Client;
import model.Vendeur;

public class app {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		menuHopital();

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

	public static void menuHopital() {
		// Choix patient/medecin/secretaire
		switch (saisieString("Portail du système informatique hospitalier\n. Connexion :\nM - Medecin\nS - Secretaire\nQ - Quitter")) {
		case "M":
		case "m":
			connexionMedecin();
			break;
		case "S":
		case "s":
			connexionSecretaire();
			break;
		case "Q":
		case "q":
			System.exit(0);
			break;
		default:
			System.out.println("Saisie incorrecte");
			break;
		}
		
		menuHopital();

	}


	// SECRETAIRE
	public static void connexionSecretaire() {
		System.out.println("Connexion secretaire");
		String login = saisieString("Saisir login secretaire : ");
		String password = saisieString("Saisie mot de passe : ");
		menuSecretaire();
		
//		if(connected instanceof Secretaire) {
//			menuSecretaire();
//		}else if(connected instanceof Medecin) {
//			System.out.println("Mauvaise page de connexion");
//		} else if(connected ==null) 
//		{
//			System.out.println("Identifiants invalides");
//		}
//		menuHopital();
	}


	public static void menuSecretaire() {
		creerRdv();
		afficherFile();
		partirPause();

	}

	private static void creerRdv() {
		creerComptePatient();

	}

	public static void creerComptePatient() {
		
	}

	private static void afficherFile() {

	}

	private static void partirPause() {

	}

	// FIN SECRETAIRE


	//MEDECIN

	public static void connexionMedecin() {
		menuMedecin();
	}

	public static void menuMedecin() {
		patientSuivant();
		afficherProchainPatient();
		afficherFileAttente();
		sauvegarderListeVisites();
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
