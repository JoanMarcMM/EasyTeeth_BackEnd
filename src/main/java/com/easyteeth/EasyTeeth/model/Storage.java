package com.easyteeth.EasyTeeth.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity
public class Storage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	int numStorage;

	

	
	public Storage(){
		
	}




	public Storage(int numStorage) {
		super();
		this.numStorage = numStorage;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public int getNumStorage() {
		return numStorage;
	}




	public void setNumStorage(int numStorage) {
		this.numStorage = numStorage;
	}
	
	

}