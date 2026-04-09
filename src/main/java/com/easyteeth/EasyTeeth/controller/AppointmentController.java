package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;
import com.easyteeth.EasyTeeth.dto.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	private StockBoxRepository stockBoxRepository;
	
	public AppointmentController(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            BoxRepository boxRepository,
            OdontologistRepository odontologistRepository,
            TreatmentRepository treatmentRepository,
            StockBoxRepository stockBoxRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.boxRepository = boxRepository;
        this.odontologistRepository = odontologistRepository;
        this.treatmentRepository = treatmentRepository;
        this.stockBoxRepository = stockBoxRepository;
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
	        
	        // Add utensils to stockBox for the appointment day
	        addUtensilsToStockBox(saved);
	        
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
			Appointment appointment = appointmentRepository.findById(id).orElse(null);
			
			// Remove utensils from stockBox before deleting appointment
			if (appointment != null) {
				removeUtensilsFromStockBox(appointment);
			}
			
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

	    // Store old values before updating to detect changes
	    Long oldBoxId = db.getBox() != null ? db.getBox().getId() : null;
	    Long oldTreatmentId = db.getTreatment() != null ? db.getTreatment().getId() : null;
	    LocalDateTime oldDate = db.getDate();
	    Appointment oldAppointmentCopy = new Appointment();
	    oldAppointmentCopy.setBox(db.getBox());
	    oldAppointmentCopy.setTreatment(db.getTreatment());
	    oldAppointmentCopy.setDate(oldDate);

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

	    Appointment updated = appointmentRepository.save(db);
	    
	    // Check if box, treatment, or date changed
	    Long newBoxId = updated.getBox() != null ? updated.getBox().getId() : null;
	    Long newTreatmentId = updated.getTreatment() != null ? updated.getTreatment().getId() : null;
	    LocalDateTime newDate = updated.getDate();
	    
	    boolean boxChanged = !java.util.Objects.equals(oldBoxId, newBoxId);
	    boolean treatmentChanged = !java.util.Objects.equals(oldTreatmentId, newTreatmentId);
	    boolean dateChanged = !java.util.Objects.equals(oldDate, newDate);
	    
	    if (boxChanged || treatmentChanged || dateChanged) {
	        // If any of these changed, update stockBox
	        removeUtensilsFromStockBox(oldAppointmentCopy);
	        addUtensilsToStockBox(updated);
	    }

	    return ResponseEntity.ok(updated);
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

	/**
	 * Helper method to add utensils from a treatment to the StockBox table
	 * for the appointment day and box
	 */
	private void addUtensilsToStockBox(Appointment appointment) {
		try {
			if (appointment == null || appointment.getTreatment() == null || 
				appointment.getBox() == null || appointment.getDate() == null) {
				return;
			}

			Treatment treatment = appointment.getTreatment();
			Set<TreatmentUtensil> treatmentUtensils = treatment.getTreatmentUtensils();
			LocalDate appointmentDay = appointment.getDate().toLocalDate();
			Long boxId = appointment.getBox().getId();

			for (TreatmentUtensil tu : treatmentUtensils) {
				if (tu.getUtensil() != null) {
					StockBoxRequest stockBoxRequest = new StockBoxRequest();
					stockBoxRequest.setUtensilId(tu.getUtensil().getId());
					stockBoxRequest.setBoxId(boxId);
					stockBoxRequest.setQuantity(tu.getQuantity());
					stockBoxRequest.setStocked(false); // Not stocked by default
					stockBoxRequest.setDay(appointmentDay);

					// This will use the upsert logic in StockBoxController
					// So if it exists, it will accumulate the quantity
					Optional<StockBox> existing = stockBoxRepository.findByUtensilIdAndBoxIdAndDay(
						tu.getUtensil().getId(),
						boxId,
						appointmentDay
					);

					if (existing.isPresent()) {
						StockBox stockBox = existing.get();
						stockBox.setQuantity(stockBox.getQuantity() + tu.getQuantity());
						stockBoxRepository.save(stockBox);
					} else {
						StockBox stockBox = new StockBox();
						stockBox.setUtensil(tu.getUtensil());
						stockBox.setBox(appointment.getBox());
						stockBox.setQuantity(tu.getQuantity());
						stockBox.setStocked(false);
						stockBox.setDay(appointmentDay);
						stockBoxRepository.save(stockBox);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Helper method to remove utensils from the StockBox table
	 * when an appointment is deleted or updated
	 */
	private void removeUtensilsFromStockBox(Appointment appointment) {
		try {
			if (appointment == null || appointment.getTreatment() == null || 
				appointment.getBox() == null || appointment.getDate() == null) {
				return;
			}

			Treatment treatment = appointment.getTreatment();
			Set<TreatmentUtensil> treatmentUtensils = treatment.getTreatmentUtensils();
			LocalDate appointmentDay = appointment.getDate().toLocalDate();
			Long boxId = appointment.getBox().getId();

			for (TreatmentUtensil tu : treatmentUtensils) {
				if (tu.getUtensil() != null) {
					Optional<StockBox> existing = stockBoxRepository.findByUtensilIdAndBoxIdAndDay(
						tu.getUtensil().getId(),
						boxId,
						appointmentDay
					);

					if (existing.isPresent()) {
						StockBox stockBox = existing.get();
						int newQuantity = stockBox.getQuantity() - tu.getQuantity();
						
						if (newQuantity <= 0) {
							// Delete the record if quantity becomes 0 or less
							stockBoxRepository.deleteById(stockBox.getId());
						} else {
							// Subtract the quantity
							stockBox.setQuantity(newQuantity);
							stockBoxRepository.save(stockBox);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}