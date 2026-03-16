package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/patient")

public class PatientController {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private BackgroundRepository backgroundRepository;

	@Autowired
	private OdontogramRepository odontogramRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private ImageRepository imageRepository;

	

	@PostMapping("/new")
	public ResponseEntity<?> createPatient(@RequestBody PatientRequest req) {
	    try {
	        
	        if (req == null ||
	            req.getName() == null || req.getName().trim().isEmpty() ||
	            req.getLastname1() == null || req.getLastname1().trim().isEmpty() ||
	            req.getLastname2() == null || req.getLastname2().trim().isEmpty() ||
	            req.getSsn() == null || req.getSsn().trim().isEmpty() ||
	            req.getDni() == null || req.getDni().trim().isEmpty()) {

	            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
	        }
	                
	        
	        Patient p = new Patient();
	        p.setName(req.getName().trim());
	        p.setLastname1(req.getLastname1().trim());
	        p.setLastname2(req.getLastname2().trim());
	        p.setSsn(req.getSsn().trim());
	        p.setDni(req.getDni().trim());


	        Patient saved = patientRepository.save(p);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

	    } catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
	    }
	}

	

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Patient>> getPatient(@PathVariable("id") Long idPatient) throws IOException {
		Optional<Patient> patient = patientRepository.findById(idPatient);
		if (patient.isPresent()) {
			return ResponseEntity.ok(patient);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Transactional
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable long id) {
	    try {
	        if (!patientRepository.existsById(id)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
	        }

	        backgroundRepository.deleteByPatientId(id);
	        imageRepository.deleteByPatientId(id);
	        documentRepository.deleteByPatientId(id);
	        odontogramRepository.deleteByPatientId(id);
	        appointmentRepository.deleteByPatientId(id);
	        patientRepository.deleteById(id);

	        return ResponseEntity.noContent().build();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/index")
	public ResponseEntity<List<Patient>> getAll() throws IOException {
		List<Patient> patients = patientRepository.findAll();
		return ResponseEntity.ok(patients);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateAppointment(@PathVariable Long id,
	                                          @RequestBody PatientRequest body) {

	    Patient db = patientRepository.findById(id).orElse(null);
	    if (db == null) return ResponseEntity.notFound().build();

	    if (body.getName() != null && !body.getName().trim().isEmpty()) {
	        db.setName(body.getName().trim());
	    }
	    
	    if (body.getLastname1() != null && !body.getLastname1().trim().isEmpty()) {
	        db.setLastname1(body.getLastname1().trim());
	    }
	    
	    if (body.getLastname2() != null && !body.getLastname2().trim().isEmpty()) {
	        db.setLastname2(body.getLastname2().trim());
	    }
	    
	    if (body.getSsn() != null && !body.getSsn().trim().isEmpty()) {
	        db.setSsn(body.getSsn().trim());
	    }
	    
	    if (body.getDni() != null && !body.getDni().trim().isEmpty()) {
	        db.setDni(body.getDni().trim());
	    }

	    return ResponseEntity.ok(patientRepository.save(db));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Patient>> findByName(@PathVariable("name") String name) {
		List<Patient> patients = patientRepository.findByNameContainingIgnoreCase(name);

		if (patients.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(patients);
		}
	}

	@GetMapping("/dni/{dni}")
	public ResponseEntity<List<Patient>> findByDni(@PathVariable("dni") String dni) {
		List<Patient> patients = patientRepository.findByDniContainingIgnoreCase(dni);

		if (patients.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(patients);
		}
	}

	@GetMapping("/ssn/{ssn}")
	public ResponseEntity<List<Patient>> findBySsn(@PathVariable("ssn") String ssn) {
		List<Patient> patients = patientRepository.findBySsnContainingIgnoreCase(ssn);

		if (patients.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(patients);
		}
	}

}
