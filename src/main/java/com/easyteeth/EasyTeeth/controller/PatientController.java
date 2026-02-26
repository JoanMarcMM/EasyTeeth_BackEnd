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

	@PostMapping("/new")
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		try {

			if (validatePatient(patient) == false) {
				return ResponseEntity.badRequest().body(null);
			}

			Patient savedPatient = patientRepository.save(patient);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	private boolean validatePatient(Patient patient) {
		if (patient == null) {
			return false;
		}

		if (patient.getName().isBlank() || patient.getLastname1().isBlank() || patient.getLastname2().isBlank()
				|| patient.getDni().isBlank() || patient.getSsn().isBlank()) {
			return false;
		}

		return true;
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

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable long id) {

		if (patientRepository.existsById(id)) {
			patientRepository.deleteById(id);
			return ResponseEntity.ok("");
		} else {	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Patient>> getAll() throws IOException {
		List<Patient> patients = patientRepository.findAll();
		return ResponseEntity.ok(patients);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePatient(@PathVariable(value = "id", required = true) Long id,
			@RequestBody Patient patientBody) {

		Optional<Patient> optionalPatient = patientRepository.findById(id);

		if (optionalPatient.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Patient patientDB = optionalPatient.get();

		List<String> errors = new ArrayList<>();

		// ====== NAME ======
		if (patientBody.getName() != null) {
			String name = patientBody.getName().trim();
			if (!name.isEmpty()) {
				if (!name.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
					errors.add("name no puede contener números ni caracteres raros");
				} else {
					patientDB.setName(name);
				}
			}
		}

		// ====== LASTNAME1 ======
		if (patientBody.getLastname1() != null) {
			String lastname1 = patientBody.getLastname1().trim();
			if (!lastname1.isEmpty()) {
				if (!lastname1.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
					errors.add("lastname1 no puede contener números ni caracteres raros");
				} else {
					patientDB.setLastname1(lastname1);
				}
			}
		}

		// ====== LASTNAME2 ======
		if (patientBody.getLastname2() != null) {
			String lastname2 = patientBody.getLastname2().trim();
			if (!lastname2.isEmpty()) {
				if (!lastname2.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")) {
					errors.add("lastname2 no puede contener números ni caracteres raros");
				} else {
					patientDB.setLastname2(lastname2);
				}
			}
		}

		// ====== DNI ======
		if (patientBody.getDni() != null) {
			String dni = patientBody.getDni().trim();
			if (!dni.isEmpty()) {
				patientDB.setDni(dni);	
			}
		}

		// ====== SSN ======
		if (patientBody.getSsn() != null) {
			String ssn = patientBody.getSsn().trim();
			if (!ssn.isEmpty()) {
				patientDB.setSsn(ssn);	
			}
		}

		if (!errors.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}

		Patient patientUpdated = patientRepository.save(patientDB);

		return ResponseEntity.ok(patientUpdated);
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
