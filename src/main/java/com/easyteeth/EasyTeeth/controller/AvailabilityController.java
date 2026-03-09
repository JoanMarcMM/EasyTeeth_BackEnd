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
@RequestMapping("/availability")
public class AvailabilityController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AvailabilityRepository availabilityRepository;

	
	public AvailabilityController(
			AvailabilityRepository availabilityRepository
    ) {
        this.availabilityRepository = availabilityRepository;
    }

	

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Availability>> getAvailability(@PathVariable("id") Long idAvailability)
			throws IOException {
		Optional<Availability> availability = availabilityRepository.findById(idAvailability);
		if (availability.isPresent()) {
			return ResponseEntity.ok(availability);
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	@GetMapping("/index")
	public ResponseEntity<List<Availability>> getAll() throws IOException {
		List<Availability> availabilities = availabilityRepository.findAll();
		return ResponseEntity.ok(availabilities);
	}

	
	
	
	


}

