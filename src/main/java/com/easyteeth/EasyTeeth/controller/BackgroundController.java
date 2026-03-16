package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/background")
public class BackgroundController {

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * 
	 *
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	@Autowired
	private PatientRepository patientRepository; 
	@Autowired
	private BackgroundRepository backgroundRepository; 
	
	public BackgroundController(
            PatientRepository patientRepository

    ) {
        this.patientRepository = patientRepository;
        
    }

	@PostMapping("/new")
	public ResponseEntity<?> createBackground(@RequestBody BackgroundRequest req) {
	    try {
	        
	        if (req == null ||
	            req.getFamilyHistory() == null || req.getFamilyHistory().trim().isEmpty() ||
	            req.getHealthState() == null || req.getHealthState().trim().isEmpty() ||
	            req.getLifeHabits() == null || req.getLifeHabits().trim().isEmpty() ||
	            req.getAllergies() == null || req.getAllergies().trim().isEmpty() ||
	            req.getMedication() == null || req.getMedication().trim().isEmpty() ) {

	            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
	        }
	        
	        Patient patient = patientRepository.findById(req.getPatientId()).orElse(null); 


	        if (patient == null) return ResponseEntity.badRequest().body("patientId no existe");
        
	        Background b = new Background();
	        b.setFamilyHistory(req.getFamilyHistory().trim());
	        b.setHealthState(req.getHealthState().trim());
	        b.setLifeHabits(req.getLifeHabits().trim());
	        b.setAllergies(req.getAllergies().trim());
	        b.setMedication(req.getMedication().trim());
	        
	        b.setImportantAllergie(req.isImportantAllergie());
	        b.setInfectiousDisease(req.isInfectiousDisease());
	        b.setHasSignedConsent(req.isHasSignedConsent());
	        b.setHasSignedAnesthesia(req.isHasSignedAnesthesia());
	        
	        b.setPatient(patient);

	        Background saved = backgroundRepository.save(b);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

	    } catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
	    }
	}

	

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Background>> getBackground(@PathVariable("id") Long idBackground)
			throws IOException {
		Optional<Background> background = backgroundRepository.findById(idBackground);
		if (background.isPresent()) {
			return ResponseEntity.ok(background);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBackground(@PathVariable long id) {

		if (backgroundRepository.existsById(id)) {
			backgroundRepository.deleteById(id);
			return ResponseEntity.ok("");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Background>> getAll() throws IOException {
		List<Background> backgrounds = backgroundRepository.findAll();
		return ResponseEntity.ok(backgrounds);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBackground(@PathVariable Long id,
	                                          @RequestBody BackgroundRequest body) {

	    Background db = backgroundRepository.findById(id).orElse(null);
	    if (db == null) return ResponseEntity.notFound().build();

	    if (body.getFamilyHistory() != null && !body.getFamilyHistory().trim().isEmpty()) {
	        db.setFamilyHistory(body.getFamilyHistory());
	    }
	    
	    if (body.getHealthState() != null && !body.getHealthState().trim().isEmpty()) {
	        db.setHealthState(body.getHealthState());
	    }
	    
	    if (body.getLifeHabits() != null && !body.getLifeHabits().trim().isEmpty()) {
	        db.setLifeHabits(body.getLifeHabits());
	    }
	    
	    if (body.getAllergies() != null && !body.getAllergies().trim().isEmpty()) {
	        db.setAllergies(body.getAllergies());
	    }
	    
	    if (body.getMedication() != null && !body.getMedication().trim().isEmpty()) {
	        db.setMedication(body.getMedication());
	    }

	    if (body.isImportantAllergie()==db.isImportantAllergie()) {
	    }else {
	    	db.setImportantAllergie(body.isImportantAllergie());
	    }
	    
	    if (body.isInfectiousDisease()==db.isInfectiousDisease()) {
	    }else {
	    	db.setInfectiousDisease(body.isInfectiousDisease());
	    }
	    
	    if (body.isHasSignedConsent()==db.isHasSignedConsent()) {
	    }else {
	    	db.setHasSignedConsent(body.isHasSignedConsent());
	    }
	    
	    if (body.isHasSignedAnesthesia()==db.isHasSignedAnesthesia()) {
	    }else {
	    	db.setHasSignedAnesthesia(body.isHasSignedAnesthesia());
	    }

	    

	    return ResponseEntity.ok(backgroundRepository.save(db));
	}
	
	
	

	

	@GetMapping("/patientId/{patientId}")
	public ResponseEntity<List<Background>> findByPatientId(@PathVariable("patientId") Long patientId) {

	    List<Background> backgrounds = backgroundRepository.findByPatientId(patientId);

	    if (backgrounds.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(backgrounds);
	}

	
	
	
	
	
	

}