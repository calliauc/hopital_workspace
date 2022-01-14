package test;

import java.util.Scanner;

public class app {

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
		else if(connected instanceof Vendeur) {menuSecretaire();}
		else if(connected ==null) 
		{
			System.out.println("Identifiants invalides !");
		}

		connectionHopital();

	}


	// SECRETAIRE
	public static void connexionSecretaire() {
		menuSecretaire();
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
