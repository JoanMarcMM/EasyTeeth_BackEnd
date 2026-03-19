package com.easyteeth.EasyTeeth.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    int duration; // en minuto
    
    @ManyToMany(mappedBy = "treatments")
    @JsonIgnore
    private Set<Pathology> pathologies = new HashSet<>();

    @ManyToMany(mappedBy = "treatments")
    private Set<Speciality> specialities = new HashSet<>();

    @OneToMany(mappedBy = "treatment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TreatmentUtensil> treatmentUtensils = new HashSet<>();

    public Set<TreatmentUtensil> getTreatmentUtensils() {
        return treatmentUtensils;
    }

    public void setTreatmentUtensils(Set<TreatmentUtensil> treatmentUtensils) {
        this.treatmentUtensils = treatmentUtensils;
    }
	

	public Treatment() {}
	


	public Treatment(String name, int duration) {
		super();
		this.name = name;
		this.duration = duration;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public int getDuration() {
		return duration;
	}




	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public Set<Pathology> getPathologies() {
	    return pathologies;
	}

	public void setPathologies(Set<Pathology> pathologies) {
	    this.pathologies = pathologies;
	}
	
	public void addUtensil(Utensil utensil, int quantity) {
	    TreatmentUtensil tu = new TreatmentUtensil();
	    tu.setTreatment(this);
	    tu.setUtensil(utensil);
	    tu.setQuantity(quantity);
	    this.treatmentUtensils.add(tu);
	    utensil.getTreatmentUtensils().add(tu);
	}
	public Set<Speciality> getSpecialities() {
	    return specialities;
	}

	public void setSpecialities(Set<Speciality> specialities) {
	    this.specialities = specialities;
	}
	public void addSpeciality(Speciality speciality) {
	    this.specialities.add(speciality);
	    speciality.getTreatments().add(this);
	}

	public void removeSpeciality(Speciality speciality) {
	    this.specialities.remove(speciality);
	    speciality.getTreatments().remove(this);
	}
	
	
}