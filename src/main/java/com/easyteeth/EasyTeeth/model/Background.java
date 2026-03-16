package com.easyteeth.EasyTeeth.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Background {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String familyHistory;
	String healthState;
	String lifeHabits;
	String allergies;
	String medication;
	boolean importantAllergie;
	boolean infectiousDisease;
	boolean hasSignedConsent;
	boolean hasSignedAnesthesia;
	@OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
	Patient patient;
	
	public Background() {
		
	}

	public Background(String familyHistory, String healthState, String lifeHabits, String allergies, String medication,
			boolean importantAllergie, boolean infectiousDisease, boolean hasSignedConsent,
			boolean hasSignedAnesthesia) {
		super();
		this.familyHistory = familyHistory;
		this.healthState = healthState;
		this.lifeHabits = lifeHabits;
		this.allergies = allergies;
		this.medication = medication;
		this.importantAllergie = importantAllergie;
		this.infectiousDisease = infectiousDisease;
		this.hasSignedConsent = hasSignedConsent;
		this.hasSignedAnesthesia = hasSignedAnesthesia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}

	public String getHealthState() {
		return healthState;
	}

	public void setHealthState(String healthState) {
		this.healthState = healthState;
	}

	public String getLifeHabits() {
		return lifeHabits;
	}

	public void setLifeHabits(String lifeHabits) {
		this.lifeHabits = lifeHabits;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getMedication() {
		return medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public boolean isImportantAllergie() {
		return importantAllergie;
	}

	public void setImportantAllergie(boolean importantAllergie) {
		this.importantAllergie = importantAllergie;
	}

	public boolean isInfectiousDisease() {
		return infectiousDisease;
	}

	public void setInfectiousDisease(boolean infectiousDisease) {
		this.infectiousDisease = infectiousDisease;
	}

	public boolean isHasSignedConsent() {
		return hasSignedConsent;
	}

	public void setHasSignedConsent(boolean hasSignedConsent) {
		this.hasSignedConsent = hasSignedConsent;
	}

	public boolean isHasSignedAnesthesia() {
		return hasSignedAnesthesia;
	}

	public void setHasSignedAnesthesia(boolean hasSignedAnesthesia) {
		this.hasSignedAnesthesia = hasSignedAnesthesia;
	}
	
	


}