package test;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
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
	static DAOVisite daoV = new DAOVisite();
	
	static Integer salleMedecin = null;

	static boolean secretaireEnPause;
	static LinkedList<Patient> fileAttente = new LinkedList<Patient>();
	//static int nb_patients_file ;
	static List<Visite> visites = new ArrayList<Visite>();

	public static void main(String[] args) {

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
		System.out.println("\n\n| Connexion au systeme hospitalier |\n");
		String login = saisieString("\nSaisir votre login :");
		String password = saisieString("\nSaisir votre password :");
		connected = daoC.seConnecter(login, password);

		if(connected instanceof Medecin) {
			while (salleMedecin != (Integer)1 && salleMedecin != (Integer)2)
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

	public static void menuSecretaire() {

		System.out.println("Menu secretaire");
		System.out.println("1 - Creer un rendez-vous");
		System.out.println("2 - Afficher file d'attente");
		System.out.println("3 - Partir en pause");
		System.out.println("4 - Afficher l'historique de visites d'un patient");
		System.out.println("5 - Se deconnecter");

		switch (saisieInt("Choisir une opération"))
		{
		case 1 : creerRdv(); break;
		case 2 : afficherFile(); break;
		case 3 : partirPause(); break;
		case 4 : afficherVisitesPatient(); break;
		case 5 : connected = null; connexionHopital(); break;
		}

		menuSecretaire();
	}

	private static void creerRdv() {
		List<Patient> listePatients = new ArrayList<Patient>();
		listePatients = daoP.findAll();
		
		
				String patientExistant = saisieString("Ce patient est-il connu de l'hopital ? (O/N)");
		switch (patientExistant) {
		case "o":
		case "O":
			Integer idPatient = saisieInt("ID du patient ?");
			for (Patient p : listePatients) {
				if (idPatient == p.getId()) {
					fileAttente.add(p);
					System.out.println("Mr. / Mme. "+ p.getNom() +" a ete ajoute(e) a la file d'attente");
					break;
				}
			} break;
		case "n":
		case "N":
			creerComptePatient(); break;
		default : break;
		}
	}
		

	public static void creerComptePatient() {
		Patient p = new Patient(null, null);
		
		p.setNom(saisieString("Nom du nouveau patient :"));
		p.setPrenom(saisieString("Prenom du nouveau patient :"));

		p.setId(daoP.insert(p));
		fileAttente.add(p);
		System.out.println("Mr. / Mme. "+p.getNom()+" a ete ajoute(e) a la base et a la file d'attente");
		
	}

	private static void afficherFile() {
		System.out.println("Il y a " + fileAttente.size() + " patients dans la file d'attente");
		for (Patient p : fileAttente) {
			System.out.println("Le patient n°" + (fileAttente.indexOf(p)+1) +" est Mr. / Mme " + p.getNom());
		}
	}

	private static void partirPause() {
		
		secretaireEnPause = true ;
		
		try 
		{
			File f=new File("liste des patients.txt");
			final FileOutputStream fichier = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
		    /// oos.writeUTF("La secretaire est partie en pause à :");
		 
		    	oos.writeObject(fileAttente);
		   
		    fileAttente.clear();
		 
		    oos.close();
		    
		}
		   
		      

		catch (java.io.IOException e) 
		{
		      e.printStackTrace();
		} 
		

	}

	
	public static void rentrerDePause() 
	{
		
		secretaireEnPause = false ; 
		File f =new File("liste des patients.txt");
		
		try {
		      final FileInputStream fichier = new FileInputStream(f);
		      ObjectInputStream ois = new ObjectInputStream(fichier);
		      fileAttente= (LinkedList<Patient>) ois.readObject();
		      f.delete();
		    } 
		catch( Exception e)
		{
			
		}
		
		  
	}
		

	// FIN SECRETAIRE

	public static void afficherVisitesPatient() {
		
		Integer patientId= saisieInt("Entrer l'id du patient");
		List<Visite> patientVisites = DAOVisite.VisitefindByPatient(patientId);
		System.out.println(patientVisites);

	}

	//MEDECIN


	public static void menuMedecin() {

		
		
		System.out.println("Menu medecin [" + connected.getLogin() + " en salle "+salleMedecin+"]");
		System.out.println("1 - Faire entrer le patient suivant");
		System.out.println("2 - Afficher le patient suivant");
		System.out.println("3 - Afficher la file d'attente");
		System.out.println("4 - Afficher vos dernières visites non enregistrées ("+visites.size()+")");
		System.out.println("5 - Sauvegarder vos dernières visites("+visites.size()+")");
		System.out.println("6 - Se deconnecter");

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
			afficherListeVisites();
			break;
		case 5:
			sauvegarderListeVisites();
			break;
		case 6:
			connected = null;
			salleMedecin = 0;
			connexionHopital();
			break;		
		default :
			System.out.println("Cette operation n'existe pas");
			break;

		}
		menuMedecin();
	}


	private static void patientSuivant() {
		System.out.println("|\tLe patient précédent est sorti\t|");
		if (!fileAttente.isEmpty()) {
			afficherProchainPatient();
			System.out.println("|\tVisite en cours\t|\n\n");
			visites.add(new Visite(fileAttente.pollFirst(), (Medecin)connected, salleMedecin));
			if (visites.size()>=10) {
				System.out.println("Debut de la sauvegarde automatique des visites");
				sauvegarderListeVisites();
			}
		} else {
			System.out.println("| Aucun autre patient n'est prévu| ");
		}
		System.out.println("\n");
	}

	private static void afficherProchainPatient() {
		if (!fileAttente.isEmpty()) {
			System.out.print("\nPatient suivant : ");
			System.out.println(fileAttente.getFirst());

		} else {
			System.out.println("| Aucun patient n'est prévu| ");
		}
		System.out.println("\n");
	}

	private static void afficherFileAttente() {
		if (!fileAttente.isEmpty()) {
			System.out.println("\n| Liste des patients pour les consultations à venir |\n");
			for (Patient patient : fileAttente) {
				System.out.println("\t" + patient);
			}

		} else {
			System.out.println("| Aucun patient n'est prévu |");
		}
		System.out.println("\n");
	}

	public static void afficherListeVisites() {
		if (!visites.isEmpty()) {
			System.out.println("\n| Liste visites non enregistrées |\n");
			for (Visite visite : visites) {
				System.out.println("\t" + visite);
			}

		} else {
			System.out.println("| Aucune visite n'est à enregistrer |");
		}
		System.out.println("\n");
	}


	private static void sauvegarderListeVisites() {
		if (!visites.isEmpty()) {
			System.out.println("\n| Enregistrement des visites |\n");
			for (Visite visite : visites) {
				daoV.insert(visite);
			}
			visites.removeAll(visites);
			System.out.println("\n| Visites enregistrees |\n");
		} else {
			System.out.println("| Aucune visite n'est a enregistrer |");
		}
		System.out.println("\n");
	}


	//FIN MEDECIN

}
