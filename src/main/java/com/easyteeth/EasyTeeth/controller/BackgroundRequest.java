package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;
import java.time.LocalDateTime;

public class BackgroundRequest {
    private String familyHistory;
    private String healthState;
    private String lifeHabits;
    private String allergies;
    private String medication;
    private boolean importantAllergie;
    private boolean infectiousDisease;
    private boolean hasSignedConsent;
    private boolean hasSignedAnesthesia;

    private Long patientId;
    

    public BackgroundRequest() {}


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


	public Long getPatientId() {
		return patientId;
	}


	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

    
}