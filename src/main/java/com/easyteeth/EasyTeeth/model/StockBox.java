package com.easyteeth.EasyTeeth.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"utensil_id", "box_id"}
    )
)
public class StockBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "utensil_id")
    private Utensil utensil;

    @ManyToOne(optional = false)
    @JoinColumn(name = "box_id")
    private Box box;

   
    private int quantity;
    
    private boolean stocked;
    
    private LocalDate day;

    public StockBox() {
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utensil getUtensil() {
		return utensil;
	}

	public void setUtensil(Utensil utensil) {
		this.utensil = utensil;
	}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public int getQuantity() {
		return quantity;
	}

	public boolean isStocked() {
		return stocked;
	}

	public void setStocked(boolean stocked) {
		this.stocked = stocked;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
    

}