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
@RequestMapping("/treatment")
public class TreatmentController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TreatmentRepository treatmentRepository;

	
	public TreatmentController(
			TreatmentRepository treatmentRepository
    ) {
        this.treatmentRepository = treatmentRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Treatment> getTreatment(@PathVariable Long id) {

	    return treatmentRepository.findByIdWithSpecialities(id)
	        .map(ResponseEntity::ok)
	        .orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/index")
	public ResponseEntity<List<Treatment>> getAll() throws IOException {
		List<Treatment> treatments = treatmentRepository.findAll();
		return ResponseEntity.ok(treatments);
	}

}