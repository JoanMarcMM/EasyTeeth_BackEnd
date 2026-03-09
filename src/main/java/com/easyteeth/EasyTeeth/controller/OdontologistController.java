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
@RequestMapping("/odontologist")
public class OdontologistController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private OdontologistRepository odontologistRepository;

	
	public OdontologistController(
			OdontologistRepository odontologistRepository
    ) {
        this.odontologistRepository = odontologistRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Odontologist>> getOdontologist(@PathVariable("id") Long idOdontologist)
			throws IOException {
		Optional<Odontologist> odontologist = odontologistRepository.findById(idOdontologist);
		if (odontologist.isPresent()) {
			return ResponseEntity.ok(odontologist);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Odontologist>> getAll() throws IOException {
		List<Odontologist> odontologists = odontologistRepository.findAll();
		return ResponseEntity.ok(odontologists);
	}

}
