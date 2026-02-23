package com.easyteeth.EasyTeeth.model;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;



@Entity
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	String urlDocument;
	LocalDateTime creationDate;
	@ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
	Patient patient;
	
	public Document() {
		
	}

	public Document(String name, String urlDocument, LocalDateTime creationDate, Patient patient) {
		super();
		this.name = name;
		this.urlDocument = urlDocument;
		this.creationDate = creationDate;
		this.patient = patient;
	}

	


}