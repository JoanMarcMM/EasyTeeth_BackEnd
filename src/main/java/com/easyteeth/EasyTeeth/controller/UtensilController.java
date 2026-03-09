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
@RequestMapping("/utensil")
public class UtensilController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtensilRepository utensilRepository;

	
	public UtensilController(
			UtensilRepository utensilRepository
    ) {
        this.utensilRepository = utensilRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Utensil>> getUtensil(@PathVariable("id") Long idUtensil)
			throws IOException {
		Optional<Utensil> utensil = utensilRepository.findById(idUtensil);
		if (utensil.isPresent()) {
			return ResponseEntity.ok(utensil);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Utensil>> getAll() throws IOException {
		List<Utensil> utensils = utensilRepository.findAll();
		return ResponseEntity.ok(utensils);
	}

}