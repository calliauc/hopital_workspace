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
		connexionPatient();

	}

	private static void connexionPatient() {
		creerComptePatient();
	}

	private static void creerComptePatient() {

	}

	private static void connexionMedecin() {

	}

	private static void connexionSecretaire() {

	}




}
