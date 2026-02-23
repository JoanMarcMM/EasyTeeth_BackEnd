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
public class Utensil {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	String brand;
	String model;
	double price;
	@OneToOne
    @JoinColumn(name = "supplier_id", nullable = false)
	Supplier supplier;

	

	
	public Utensil(){
		
	}




	public Utensil(String name, String brand, String model, double price, Supplier supplier) {
		super();
		this.name = name;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.supplier = supplier;
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




	public String getBrand() {
		return brand;
	}




	public void setBrand(String brand) {
		this.brand = brand;
	}




	public String getModel() {
		return model;
	}




	public void setModel(String model) {
		this.model = model;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public Supplier getSupplier() {
		return supplier;
	}




	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	


	
}