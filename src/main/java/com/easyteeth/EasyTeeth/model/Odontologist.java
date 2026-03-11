package com.easyteeth.EasyTeeth.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Odontologist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname1;
    private String lastname2;
    private String dni;
    private String licenseNumber;
    

    @ManyToMany
    @JoinTable(
        name = "odontologist_speciality",
        joinColumns = @JoinColumn(name = "odontologist_id"),
        inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    @JsonIgnore
    private Set<Speciality> specialities = new HashSet<>();

    
    @ManyToMany
    @JoinTable(
        name = "schedule",
        joinColumns = @JoinColumn(name = "odontologist_id"),
        inverseJoinColumns = @JoinColumn(name = "availability_id")
    )
    private Set<Availability> availabilities = new HashSet<>();
 

    public Odontologist() {}


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


	public String getLastname1() {
		return lastname1;
	}


	public void setLastname1(String lastname1) {
		this.lastname1 = lastname1;
	}


	public String getLastname2() {
		return lastname2;
	}


	public void setLastname2(String lastname2) {
		this.lastname2 = lastname2;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getLicenseNumber() {
		return licenseNumber;
	}


	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}


	public Set<Speciality> getSpecialities() {
		return specialities;
	}


	public void setSpecialities(Set<Speciality> specialities) {
		this.specialities = specialities;
	}


	public Set<Availability> getAvailabilities() {
		return availabilities;
	}


	public void setAvailabilities(Set<Availability> availabilities) {
		this.availabilities = availabilities;
	}
	
	
}