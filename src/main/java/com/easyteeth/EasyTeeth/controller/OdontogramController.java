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
@RequestMapping("/odontogram")
public class OdontogramController {

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * 
	 * { "patientId": 1, 
	 * "toothId": 12, "
	 * sideId": 3, 
	 * "pathologyId": 5, 
	 * "treated":false, 
	 * "note": "Caries incipiente" }
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	@Autowired
	private OdontogramRepository odontogramRepository;
	private PatientRepository patientRepository;
	private ToothRepository toothRepository;
	private SideRepository sideRepository;
	private PathologyRepository pathologyRepository;

	public OdontogramController(OdontogramRepository odontogramRepository, PatientRepository patientRepository,
			ToothRepository toothRepository, SideRepository sideRepository, PathologyRepository pathologyRepository) {
		this.odontogramRepository = odontogramRepository;
		this.toothRepository = toothRepository;
		this.patientRepository = patientRepository;
		this.sideRepository = sideRepository;
		this.pathologyRepository = pathologyRepository;

	}

	@PostMapping("/new")
	public ResponseEntity<?> createOdontogram(@RequestBody OdontogramRequest req) {
		try {

			if (req == null || req.getPatientId() == null || req.getToothId() == null || req.getSideId() == null
					|| req.getPathologyId() == null) {

				return ResponseEntity.badRequest().body("Faltan campos obligatorios");
			}

			Patient patient = patientRepository.findById(req.getPatientId()).orElse(null);
			Tooth tooth = toothRepository.findById(req.getToothId()).orElse(null);
			Side side = sideRepository.findById(req.getSideId()).orElse(null);
			Pathology pathology = pathologyRepository.findById(req.getPathologyId()).orElse(null);

			if (patient == null)
				return ResponseEntity.badRequest().body("patientId no existe");
			if (tooth == null)
				return ResponseEntity.badRequest().body("toothId no existe");
			if (side == null)
				return ResponseEntity.badRequest().body("sideId no existe");
			if (pathology == null)
				return ResponseEntity.badRequest().body("pathologyId no existe");

			Odontogram od = new Odontogram();
			od.setPatient(patient);
			od.setTooth(tooth);
			od.setSide(side);
			od.setPathology(pathology);

			od.setTreated(req.getTreated() != null ? req.getTreated() : false);
			od.setNote(req.getNote());

			Odontogram saved = odontogramRepository.save(od);

			return ResponseEntity.status(HttpStatus.CREATED).body(saved);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
		}
	}

	

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Odontogram>> getOdontogram(@PathVariable("id") Long idOdontogram)
			throws IOException {
		Optional<Odontogram> odontogram = odontogramRepository.findById(idOdontogram);
		if (odontogram.isPresent()) {
			return ResponseEntity.ok(odontogram);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable long id) {

		if (odontogramRepository.existsById(id)) {
			odontogramRepository.deleteById(id);
			return ResponseEntity.ok("");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Odontogram>> getAll() throws IOException {
		List<Odontogram> odontograms = odontogramRepository.findAll();
		return ResponseEntity.ok(odontograms);
	}


	@PutMapping("/{id}")
	public ResponseEntity<?> updateOdontogram(@PathVariable Long id, @RequestBody OdontogramRequest body) {

		Odontogram db = odontogramRepository.findById(id).orElse(null);
		if (db == null)
			return ResponseEntity.notFound().build();

		if (body.getPatientId() != null) {
			db.setPatient(entityManager.getReference(Patient.class, body.getPatientId()));
		}

		if (body.getToothId() != null) {
			db.setTooth(entityManager.getReference(Tooth.class, body.getToothId()));
		}

		if (body.getSideId() != null) {
			db.setSide(entityManager.getReference(Side.class, body.getSideId()));
		}

		if (body.getPathologyId() != null) {
			db.setPathology(entityManager.getReference(Pathology.class, body.getPathologyId()));
		}

		if (body.getTreated() != null) {
			db.setTreated(body.getTreated());
		}

		if (body.getNote() != null) {
			db.setNote(body.getNote());
		}

		return ResponseEntity.ok(odontogramRepository.save(db));
	}

	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<?> getByPatient(@PathVariable Long patientId) {

	    List<Odontogram> list = odontogramRepository.findByPatientId(patientId);

	    if (list.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(list);
	}


	
	@GetMapping("/patient/{patientId}/tooth/{toothId}")
	public ResponseEntity<?> getByPatientAndTooth(@PathVariable Long patientId,
	                                              @PathVariable Long toothId) {

	    List<Odontogram> list =
	            odontogramRepository.findByPatientIdAndToothId(patientId, toothId);

	    if (list.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(list);
	}



	@GetMapping("/patient/{patientId}/tooth/{toothId}/side/{sideId}")
	public ResponseEntity<?> getByPatientToothAndSide(@PathVariable Long patientId,
	                                                  @PathVariable Long toothId,
	                                                  @PathVariable Long sideId) {

	    List<Odontogram> list =
	            odontogramRepository.findByPatientIdAndToothIdAndSideId(
	                    patientId, toothId, sideId);

	    if (list.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(list);
	}

}