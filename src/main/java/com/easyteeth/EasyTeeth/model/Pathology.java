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
public class Pathology {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	
	@ManyToMany
    @JoinTable(
        name = "pathology_treatment",
        joinColumns = @JoinColumn(name = "pathology_id"),
        inverseJoinColumns = @JoinColumn(name = "treatment_id")
    )
    private Set<Treatment> treatments = new HashSet<>();
	

	
	public Pathology(){
		
	}





	public Pathology(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	
	
}