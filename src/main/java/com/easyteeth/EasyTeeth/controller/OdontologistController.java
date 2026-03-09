package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/odontologist")
public class OdontologistController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OdontologistRepository odontologistRepository;
    private SpecialityRepository specialityRepository;
    private AvailabilityRepository availabilityRepository;

    public OdontologistController(
            OdontologistRepository odontologistRepository,
            SpecialityRepository specialityRepository,
            AvailabilityRepository availabilityRepository
    ) {
        this.odontologistRepository = odontologistRepository;
        this.specialityRepository = specialityRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createOdontologist(@RequestBody OdontologistRequest req) {
        try {

            if (req == null ||
                req.getName() == null || req.getName().trim().isEmpty() ||
                req.getLastname1() == null || req.getLastname1().trim().isEmpty() ||
                req.getLastname2() == null || req.getLastname2().trim().isEmpty() ||
                req.getDni() == null || req.getDni().trim().isEmpty() ||
                req.getLicenseNumber() == null || req.getLicenseNumber().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Faltan campos obligatorios");
            }

            Odontologist odontologist = new Odontologist();
            odontologist.setName(req.getName().trim());
            odontologist.setLastname1(req.getLastname1().trim());
            odontologist.setLastname2(req.getLastname2().trim());
            odontologist.setDni(req.getDni().trim());
            odontologist.setLicenseNumber(req.getLicenseNumber().trim());

            if (req.getSpecialityIds() != null) {
                Set<Speciality> specialities = new HashSet<>();
                for (Long id : req.getSpecialityIds()) {
                    specialityRepository.findById(id).ifPresent(specialities::add);
                }
                odontologist.setSpecialities(specialities);
            }

            if (req.getAvailabilityIds() != null) {
                Set<Availability> availabilities = new HashSet<>();
                for (Long id : req.getAvailabilityIds()) {
                    availabilityRepository.findById(id).ifPresent(availabilities::add);
                }
                odontologist.setAvailabilities(availabilities);
            }

            Odontologist saved = odontologistRepository.save(odontologist);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Odontologist>> getOdontologist(@PathVariable("id") Long id) throws IOException {
        Optional<Odontologist> odontologist = odontologistRepository.findById(id);
        if (odontologist.isPresent()) {
            return ResponseEntity.ok(odontologist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOdontologist(@PathVariable long id) {
        if (odontologistRepository.existsById(id)) {
            odontologistRepository.deleteById(id);
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @GetMapping("/index")
    public ResponseEntity<List<Odontologist>> getAll() throws IOException {
        List<Odontologist> odontologists = odontologistRepository.findAll();
        return ResponseEntity.ok(odontologists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOdontologist(@PathVariable Long id,
                                                @RequestBody OdontologistRequest body) {

        Odontologist db = odontologistRepository.findById(id).orElse(null);
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

        if (body.getDni() != null && !body.getDni().trim().isEmpty()) {
            db.setDni(body.getDni().trim());
        }

        if (body.getLicenseNumber() != null && !body.getLicenseNumber().trim().isEmpty()) {
            db.setLicenseNumber(body.getLicenseNumber().trim());
        }

        if (body.getSpecialityIds() != null) {
            Set<Speciality> specialities = new HashSet<>();
            for (Long specialityId : body.getSpecialityIds()) {
                specialities.add(entityManager.getReference(Speciality.class, specialityId));
            }
            db.setSpecialities(specialities);
        }

        if (body.getAvailabilityIds() != null) {
            Set<Availability> availabilities = new HashSet<>();
            for (Long availabilityId : body.getAvailabilityIds()) {
                availabilities.add(entityManager.getReference(Availability.class, availabilityId));
            }
            db.setAvailabilities(availabilities);
        }

        return ResponseEntity.ok(odontologistRepository.save(db));
    }

    @GetMapping("/specialityId/{specialityId}")
    public ResponseEntity<List<Odontologist>> findBySpecialityId(@PathVariable("specialityId") Long specialityId) {

        List<Odontologist> odontologists = odontologistRepository.findBySpecialitiesId(specialityId);

        if (odontologists.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(odontologists);
    }
}
