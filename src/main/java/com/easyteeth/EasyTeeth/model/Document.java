package com.easyteeth.EasyTeeth.model;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;



@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

	public String getUrlDocument() {
		return urlDocument;
	}

	public void setUrlDocument(String urlDocument) {
		this.urlDocument = urlDocument;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	


}