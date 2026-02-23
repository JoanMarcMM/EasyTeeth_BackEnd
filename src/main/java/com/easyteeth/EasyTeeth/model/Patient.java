package com.easyteeth.EasyTeeth.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	String lastname1;
	String lastname2;
	String ssn;
	String dni;
	
	public Patient() {
		
	}

	public Patient(String name, String lastname1, String lastname2, String ssn, String dni) {
		super();
		this.name = name;
		this.lastname1 = lastname1;
		this.lastname2 = lastname2;
		this.ssn = ssn;
		this.dni = dni;
	}
	
	

	
	
	
	
	
	
}