package com.easyteeth.EasyTeeth.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@ManyToOne(optional = false)
	@JoinColumn(name = "supplier_id", nullable = false)
	private Supplier supplier;
	@OneToMany(mappedBy = "utensil", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TreatmentUtensil> treatmentUtensils = new HashSet<>();

	public Set<TreatmentUtensil> getTreatmentUtensils() {
	    return treatmentUtensils;
	}

	
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

	
	
	public void setTreatmentUtensils(Set<TreatmentUtensil> treatmentUtensils) {
	    this.treatmentUtensils = treatmentUtensils;
	}
	
	public void addTreatment(Treatment treatment, int quantity) {
	    TreatmentUtensil tu = new TreatmentUtensil();
	    tu.setUtensil(this);
	    tu.setTreatment(treatment);
	    tu.setQuantity(quantity);

	    this.treatmentUtensils.add(tu);
	    treatment.getTreatmentUtensils().add(tu);
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