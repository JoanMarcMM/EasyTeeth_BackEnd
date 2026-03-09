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
@RequestMapping("/side")
public class SideController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SideRepository sideRepository;

	
	public SideController(
			SideRepository sideRepository
    ) {
        this.sideRepository = sideRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Side>> getSide(@PathVariable("id") Long idSide)
			throws IOException {
		Optional<Side> side = sideRepository.findById(idSide);
		if (side.isPresent()) {
			return ResponseEntity.ok(side);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Side>> getAll() throws IOException {
		List<Side> sides = sideRepository.findAll();
		return ResponseEntity.ok(sides);
	}

}