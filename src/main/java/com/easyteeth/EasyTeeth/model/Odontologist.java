package com.easyteeth.EasyTeeth.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
    private Set<Speciality> specialities = new HashSet<>();

    
    @ManyToMany
    @JoinTable(
        name = "schedule",
        joinColumns = @JoinColumn(name = "odontologist_id"),
        inverseJoinColumns = @JoinColumn(name = "availability_id")
    )
    private Set<Availability> availabilities = new HashSet<>();
 

    public Odontologist() {}
	
	
}