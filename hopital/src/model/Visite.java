package dao;

import java.time.LocalDate;

public class Visite {
	private Integer numero;
	private Patient patient;
	private Medecin medecin;
	private int prix;
	private Integer salle;
	private LocalDate dateVisite;
	
	public Visite(Integer numero, Patient patient, Medecin medecin, int prix, Integer salle, LocalDate dateVisite) {
		super();
		this.numero = numero;
		this.patient = patient;
		this.medecin = medecin;
		this.prix = prix;
		this.salle = salle;
		this.dateVisite = dateVisite;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Medecin getMedecin() {
		return medecin;
	}

	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public Integer getSalle() {
		return salle;
	}

	public void setSalle(Integer salle) {
		this.salle = salle;
	}

	public LocalDate getDateVisite() {
		return dateVisite;
	}

	public void setDateVisite(LocalDate dateVisite) {
		this.dateVisite = dateVisite;
	}

	@Override
	public String toString() {
		return "Visite [numero=" + numero + ", patient=" + patient + ", medecin=" + medecin + ", prix=" + prix + ", salle=" + salle + ", dateVisite=" + dateVisite + "]";
	}
	
	
	
	
}
