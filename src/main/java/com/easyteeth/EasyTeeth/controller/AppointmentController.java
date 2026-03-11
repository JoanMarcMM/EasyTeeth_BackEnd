package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;
import com.easyteeth.EasyTeeth.dto.*;

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
@RequestMapping("/appointment")
public class AppointmentController {

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * 
	 * NECESITA JSON COMO ESTE: { 
	 "motive": "Revision", 
	 "date":"2026-02-25T10:00:00", 
	 "patient": { "id": 5 }, 
	 "box": { "id": 2 },
	 "odontologist": { "id": 1 }, 
	 "treatment": { "id": 3 } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	@Autowired
	private AppointmentRepository appointmentRepository;
	private PatientRepository patientRepository; 
	private BoxRepository boxRepository; 
	private OdontologistRepository odontologistRepository;
	private TreatmentRepository treatmentRepository;
	
	public AppointmentController(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            BoxRepository boxRepository,
            OdontologistRepository odontologistRepository,
            TreatmentRepository treatmentRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.boxRepository = boxRepository;
        this.odontologistRepository = odontologistRepository;
        this.treatmentRepository = treatmentRepository;
    }

	@PostMapping("/new")
	public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest req) {
	    try {
	        
	        if (req == null ||
	            req.getMotive() == null || req.getMotive().trim().isEmpty() ||
	            req.getDate() == null ||
	            req.getPatientId() == null ||
	            req.getBoxId() == null ||
	            req.getOdontologistId() == null ||
	            req.getTreatmentId() == null) {

	            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
	        }
	        
	        Patient patient = patientRepository.findById(req.getPatientId()).orElse(null); 
	        Box box = boxRepository.findById(req.getBoxId()).orElse(null);
	        Odontologist odontologist = odontologistRepository.findById(req.getOdontologistId()).orElse(null);
	        Treatment treatment = treatmentRepository.findById(req.getTreatmentId()).orElse(null);

	        if (patient == null) return ResponseEntity.badRequest().body("patientId no existe");
	        if (box == null) return ResponseEntity.badRequest().body("boxId no existe");
	        if (odontologist == null) return ResponseEntity.badRequest().body("odontologistId no existe");
	        if (treatment == null) return ResponseEntity.badRequest().body("treatmentId no existe");

	        
	        Appointment ap = new Appointment();
	        ap.setMotive(req.getMotive().trim());
	        ap.setDate(req.getDate());
	        ap.setPatient(patient);
	        ap.setBox(box);
	        ap.setOdontologist(odontologist);
	        ap.setTreatment(treatment);

	        Appointment saved = appointmentRepository.save(ap);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

	    } catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
	    }
	}

	private boolean validateAppointment(Appointment appointment) {
		if (appointment == null) {
			return false;
		}

		if (appointment.getBox() == null || appointment.getDate() == null || appointment.getOdontologist() == null
				|| appointment.getPatient() == null || appointment.getTreatment() == null
				|| appointment.getMotive() == null) {
			return false;
		}

		return true;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Appointment>> getAppointment(@PathVariable("id") Long idAppointment)
			throws IOException {
		Optional<Appointment> appointment = appointmentRepository.findById(idAppointment);
		if (appointment.isPresent()) {
			return ResponseEntity.ok(appointment);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable long id) {

		if (appointmentRepository.existsById(id)) {
			appointmentRepository.deleteById(id);
			return ResponseEntity.ok("");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Appointment>> getAll() throws IOException {
		List<Appointment> appointments = appointmentRepository.findAll();
		return ResponseEntity.ok(appointments);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAppointment(@PathVariable Long id,
	                                          @RequestBody AppointmentRequest body) {

	    Appointment db = appointmentRepository.findById(id).orElse(null);
	    if (db == null) return ResponseEntity.notFound().build();

	    if (body.getMotive() != null && !body.getMotive().trim().isEmpty()) {
	        db.setMotive(body.getMotive().trim());
	    }

	    if (body.getDate() != null) {
	        db.setDate(body.getDate());
	    }

	    if (body.getPatientId() != null) {
	        db.setPatient(entityManager.getReference(Patient.class, body.getPatientId()));
	    }
	    if (body.getBoxId() != null) {
	        db.setBox(entityManager.getReference(Box.class, body.getBoxId()));
	    }
	    if (body.getOdontologistId() != null) {
	        db.setOdontologist(entityManager.getReference(Odontologist.class, body.getOdontologistId()));
	    }
	    if (body.getTreatmentId() != null) {
	        db.setTreatment(entityManager.getReference(Treatment.class, body.getTreatmentId()));
	    }

	    return ResponseEntity.ok(appointmentRepository.save(db));
	}
	
	
	

	@GetMapping("/date/{date}")
	public ResponseEntity<List<Appointment>> findByDate(@PathVariable("date") LocalDateTime date) {
		List<Appointment> appointments = appointmentRepository.findByDate(date);

		if (appointments.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(appointments);
		}
	}
	
	@GetMapping("/dateBetween/{date}")
	public ResponseEntity<List<Appointment>> findByDay(@PathVariable("date") LocalDate date) {

	    LocalDateTime start = date.atStartOfDay();
	    LocalDateTime end = date.plusDays(1).atStartOfDay();

	    List<Appointment> appointments = appointmentRepository.findByDateBetween(start, end);

	    if (appointments.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(appointments);
	}
	
	

	@GetMapping("/patientId/{patientId}")
	public ResponseEntity<List<Appointment>> findByPatientId(@PathVariable("patientId") Long patientId) {

	    List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

	    if (appointments.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(appointments);
	}

	
	
	@GetMapping("/boxId/{boxId}")
	public ResponseEntity<List<Appointment>> findByBoxId(@PathVariable("boxId") Long boxId) {

	    List<Appointment> appointments = appointmentRepository.findByBoxId(boxId);

	    if (appointments.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(appointments);
	}
	
	
	
	@GetMapping("/odontologistId/{odontologistId}")
	public ResponseEntity<List<Appointment>> findByOdontologistId(@PathVariable("odontologistId") Long odontologistId) {

	    List<Appointment> appointments = appointmentRepository.findByOdontologistId(odontologistId);

	    if (appointments.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(appointments);
	}
	
	
	@GetMapping("/treatmentId/{treatmentId}")
	public ResponseEntity<List<Appointment>> findByTreatmentId(@PathVariable("treatmentId") Long treatmentId) {

	    List<Appointment> appointments = appointmentRepository.findByTreatmentId(treatmentId);

	    if (appointments.isEmpty()) return ResponseEntity.notFound().build();
	    return ResponseEntity.ok(appointments);
	}
	
	@GetMapping("/calendar/week")
	public ResponseEntity<List<AppointmentCalendarDto>> getWeeklyCalendar(
	        @RequestParam("start") LocalDate startDate,
	        @RequestParam(value = "boxId", required = false) Long boxId
	) {
	    try {
	        LocalDateTime start = startDate.atStartOfDay();
	        LocalDateTime end = startDate.plusDays(7).atStartOfDay();

	        List<Appointment> appointments = appointmentRepository.findCalendarAppointments(start, end, boxId);

	        List<AppointmentCalendarDto> result = appointments.stream().map(a -> {
	            AppointmentCalendarDto dto = new AppointmentCalendarDto();

	            dto.setId(a.getId());
	            dto.setMotive(a.getMotive());
	            dto.setStart(a.getDate());

	            dto.setDurationMinutes(
	                    a.getTreatment() != null ? a.getTreatment().getDuration() : 0
	            );

	            dto.setBoxId(
	                    a.getBox() != null ? a.getBox().getId() : null
	            );
	            dto.setBoxNum(
	                    a.getBox() != null ? a.getBox().getNumBox() : 0
	            );

	            dto.setTreatmentId(
	                    a.getTreatment() != null ? a.getTreatment().getId() : null
	            );
	            dto.setTreatmentName(
	                    a.getTreatment() != null && a.getTreatment().getName() != null
	                            ? a.getTreatment().getName()
	                            : ""
	            );

	            dto.setPatientId(
	                    a.getPatient() != null ? a.getPatient().getId() : null
	            );
	            dto.setPatientFullName(
	                    buildPatientFullName(a.getPatient())
	            );

	            dto.setOdontologistId(
	                    a.getOdontologist() != null ? a.getOdontologist().getId() : null
	            );
	            dto.setOdontologistFullName(
	                    buildOdontologistFullName(a.getOdontologist())
	            );

	            return dto;
	        }).toList();

	        return ResponseEntity.ok(result);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	private String buildPatientFullName(Patient patient) {
	    if (patient == null) return "";

	    String name = patient.getName() != null ? patient.getName() : "";
	    String lastname1 = patient.getLastname1() != null ? patient.getLastname1() : "";
	    String lastname2 = patient.getLastname2() != null ? patient.getLastname2() : "";

	    return (name + " " + lastname1 + " " + lastname2).trim().replaceAll("\\s+", " ");
	}

	private String buildOdontologistFullName(Odontologist odontologist) {
	    if (odontologist == null) return "";

	    String name = odontologist.getName() != null ? odontologist.getName() : "";
	    String lastname1 = odontologist.getLastname1() != null ? odontologist.getLastname1() : "";
	    String lastname2 = odontologist.getLastname2() != null ? odontologist.getLastname2() : "";

	    return (name + " " + lastname1 + " " + lastname2).trim().replaceAll("\\s+", " ");
	}

}