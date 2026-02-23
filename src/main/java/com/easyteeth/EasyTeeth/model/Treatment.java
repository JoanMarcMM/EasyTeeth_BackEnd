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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
public class Treatment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	int duration; //in minutes

	
	@ManyToMany(mappedBy = "treatments")
    private Set<Pathology> pathologies = new HashSet<>();
	
	@ManyToMany(mappedBy = "treatments")
    private Set<Speciality> specialities = new HashSet<>();
	
	public Treatment(){
		
	}

	


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
	
	
}