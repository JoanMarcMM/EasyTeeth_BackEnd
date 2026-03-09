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
@RequestMapping("/storage")
public class StorageController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private StorageRepository storageRepository;

	
	public StorageController(
			StorageRepository storageRepository
    ) {
        this.storageRepository = storageRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Storage>> getStorage(@PathVariable("id") Long idStorage)
			throws IOException {
		Optional<Storage> storage = storageRepository.findById(idStorage);
		if (storage.isPresent()) {
			return ResponseEntity.ok(storage);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Storage>> getAll() throws IOException {
		List<Storage> storages = storageRepository.findAll();
		return ResponseEntity.ok(storages);
	}

}