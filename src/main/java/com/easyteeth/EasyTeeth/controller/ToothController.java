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
@RequestMapping("/tooth")
public class ToothController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ToothRepository toothRepository;

	
	public ToothController(
			ToothRepository toothRepository
    ) {
        this.toothRepository = toothRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Tooth>> getTooth(@PathVariable("id") Long idTooth)
			throws IOException {
		Optional<Tooth> tooth = toothRepository.findById(idTooth);
		if (tooth.isPresent()) {
			return ResponseEntity.ok(tooth);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Tooth>> getAll() throws IOException {
		List<Tooth> teeth = toothRepository.findAll();
		return ResponseEntity.ok(teeth);
	}

}