package test;

import java.util.Scanner;

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
		connexionSecretaire();
		connexionMedecin();

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
