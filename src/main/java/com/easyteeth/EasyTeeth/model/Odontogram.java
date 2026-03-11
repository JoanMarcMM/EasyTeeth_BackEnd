package com.easyteeth.EasyTeeth.model;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.persistence.Table;


@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"patient_id", "tooth_id", "side_id","pathology_id"}
    )
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Odontogram{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tooth_id", nullable = false)
    private Tooth tooth;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "side_id", nullable = false)
    private Side side;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pathology_id", nullable = false)
    private Pathology pathology;
    
    
    private boolean treated;
    private String note;

    public Odontogram() {}
    
    

	public Odontogram(Patient patient, Tooth tooth, Side side, Pathology pathology,boolean treated, String note) {
		super();
		this.patient = patient;
		this.tooth = tooth;
		this.side = side;
		this.pathology = pathology;
		this.treated = treated;
		this.note = note;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Patient getPatient() {
		return patient;
	}



	public void setPatient(Patient patient) {
		this.patient = patient;
	}



	public Tooth getTooth() {
		return tooth;
	}

	public void setTooth(Tooth tooth) {
		this.tooth = tooth;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public Pathology getPathology() {
		return pathology;
	}

	public void setPathology(Pathology pathology) {
		this.pathology = pathology;
	}

	public boolean isTreated() {
		return treated;
	}

	public void setTreated(boolean treated) {
		this.treated = treated;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}



	

    
}