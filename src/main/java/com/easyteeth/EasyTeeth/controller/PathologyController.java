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
@RequestMapping("/pathology")
public class PathologyController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PathologyRepository pathologyRepository;

	
	public PathologyController(
			PathologyRepository pathologyRepository
    ) {
        this.pathologyRepository = pathologyRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Pathology>> getPathology(@PathVariable("id") Long idPathology)
			throws IOException {
		Optional<Pathology> pathology = pathologyRepository.findByIdWithTreatments(idPathology);
		if (pathology.isPresent()) {
			return ResponseEntity.ok(pathology);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Pathology>> getAll() throws IOException {
		List<Pathology> pathologies = pathologyRepository.findAll();
		return ResponseEntity.ok(pathologies);
	}

}