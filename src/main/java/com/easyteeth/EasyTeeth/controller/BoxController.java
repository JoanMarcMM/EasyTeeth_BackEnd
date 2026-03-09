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
@RequestMapping("/box")
public class BoxController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BoxRepository boxRepository;

	
	public BoxController(
			BoxRepository boxRepository
    ) {
        this.boxRepository = boxRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Box>> getBox(@PathVariable("id") Long idBox)
			throws IOException {
		Optional<Box> box = boxRepository.findById(idBox);
		if (box.isPresent()) {
			return ResponseEntity.ok(box);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Box>> getAll() throws IOException {
		List<Box> boxes = boxRepository.findAll();
		return ResponseEntity.ok(boxes);
	}

}

