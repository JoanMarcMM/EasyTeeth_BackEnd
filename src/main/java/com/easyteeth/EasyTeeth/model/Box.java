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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_box_numBox", columnNames = "numBox"))
public class Box {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	int numBox;

	

	
	public Box(){
		
	}




	



	public Box(int numBox) {
		super();
		this.numBox = numBox;
	}


	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public int getNumBox() {
		return numBox;
	}




	public void setNumBox(int numBox) {
		this.numBox = numBox;
	}
	
	

}