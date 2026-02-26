package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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

@RestController
@RequestMapping("/document")
/*
  
  http://localhost:8080/document/new
  
 {
  "name": "archivo.txt",
  "urlDocument": "http://localhost:8080/document/new",
  "creationDate": "2026-02-26T10:00:00", 
  "patientId": 1    
} 
 
 * */

public class DocumentController {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private PatientRepository patientRepository;

	@PostMapping("/new")
	public ResponseEntity<?> createDocument(@RequestBody DocumentRequest req) {
	    List<String> errors = new ArrayList<>();

	    if (req == null) errors.add("Body vacío");
	    else {
	        if (req.getName() == null || req.getName().isBlank()) errors.add("name es obligatorio");
	        if (req.getUrlDocument() == null || req.getUrlDocument().isBlank()) errors.add("urlDocument es obligatorio");
	        if (req.getCreationDate() == null) errors.add("creationDate es obligatorio");
	        if (req.getPatientId() == null) errors.add("patientId es obligatorio");
	    }

	    if (!errors.isEmpty()) {
	        return ResponseEntity.badRequest().body(errors);
	    }

	    Patient patient = patientRepository.findById(req.getPatientId()).orElse(null);
	    if (patient == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(List.of("No existe patient con id " + req.getPatientId()));
	    }

	    Document document = new Document();
	    document.setName(req.getName().trim());
	    document.setUrlDocument(req.getUrlDocument().trim());
	    document.setCreationDate(req.getCreationDate());
	    document.setPatient(patient);

	    Document saved = documentRepository.save(document);
	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Document>> getDocument(@PathVariable("id") Long idDocument) throws IOException {
		Optional<Document> document = documentRepository.findById(idDocument);
		if (document.isPresent()) {
			return ResponseEntity.ok(document);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteDocument(@PathVariable("id") long idDocument) {

		if (documentRepository.existsById(idDocument)) {
			documentRepository.deleteById(idDocument);
			return ResponseEntity.ok("");
		} else {	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Document>> getAll() throws IOException {
		List<Document> documents = documentRepository.findAll();
		return ResponseEntity.ok(documents);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateDocument(
	        @PathVariable("id") Long idDocument,
	        @RequestBody DocumentRequest req
	) {
	    Optional<Document> optionalDocument = documentRepository.findById(idDocument);
	    if (optionalDocument.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    Document documentDB = optionalDocument.get();
	    List<String> errors = new ArrayList<>();

	    // ====== NAME ======
	    if (req.getName() != null) {
	        String name = req.getName().trim();
	        if (!name.isEmpty()) documentDB.setName(name);
	        else errors.add("name no puede estar vacío");
	    }

	    // ====== URL ======
	    if (req.getUrlDocument() != null) {
	        String url = req.getUrlDocument().trim();
	        if (!url.isEmpty()) documentDB.setUrlDocument(url);
	        else errors.add("urlDocument no puede estar vacío");
	    }

	    // ====== CREATION DATE ======
	    if (req.getCreationDate() != null) {
	        documentDB.setCreationDate(req.getCreationDate());
	    }

	    // ====== PATIENT (por id) ======
	    if (req.getPatientId() != null) {
	        Patient patient = patientRepository.findById(req.getPatientId()).orElse(null);
	        if (patient == null) {
	            errors.add("No existe patient con id " + req.getPatientId());
	        } else {
	            documentDB.setPatient(patient);
	        }
	    }

	    if (!errors.isEmpty()) {
	        return ResponseEntity.badRequest().body(errors);
	    }

	    Document updated = documentRepository.save(documentDB);
	    return ResponseEntity.ok(updated);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Document>> findByDocument(@PathVariable("name") String name) {
		List<Document> documents = documentRepository.findByNameContainingIgnoreCase(name);

		if (documents.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(documents);
		}
	}

	@GetMapping("/date/{date}")
	public ResponseEntity<List<Document>> findByDate(@PathVariable("date") LocalDateTime date) {
		List<Document> documents = documentRepository.findByCreationDate(date);

		if (documents.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(documents);
		}
	}
	
	@GetMapping("/dateBetween/{date}")
	public ResponseEntity<List<Document>> findByDay(@PathVariable("date") LocalDate date) {

	    LocalDateTime start = date.atStartOfDay();
	    LocalDateTime end = date.plusDays(1).atStartOfDay();

	    List<Document> documents = documentRepository.findByCreationDateBetween(start, end);

	    if (documents.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(documents);
	}
	
	

	@GetMapping("/patientId/{patientId}")
	public ResponseEntity<List<Document>> findByPatientId(@PathVariable("patientId") Long patientId) {

	    List<Document> documents = documentRepository.findByPatientId(patientId);

	    if (documents.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(documents);
	}

}
