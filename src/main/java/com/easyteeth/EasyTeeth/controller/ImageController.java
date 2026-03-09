package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@RestController
@RequestMapping("/image")
public class ImageController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ImageRepository imageRepository;
    private PatientRepository patientRepository;

    public ImageController(
            ImageRepository imageRepository,
            PatientRepository patientRepository
    ) {
        this.imageRepository = imageRepository;
        this.patientRepository = patientRepository;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createImage(@RequestBody ImageRequest req) {
        try {

            if (req == null ||
                req.getImage() == null ||
                req.getImage().length == 0 ||
                req.getType() == null || req.getType().trim().isEmpty() ||
                req.getPatientId() == null) {

                return ResponseEntity.badRequest().body("Faltan campos obligatorios");
            }

            Patient patient = patientRepository.findById(req.getPatientId()).orElse(null);

            if (patient == null) return ResponseEntity.badRequest().body("patientId no existe");

            Image image = new Image();
            image.setImage(req.getImage());
            image.setType(req.getType().trim());
            image.setPatient(patient);

            Image saved = imageRepository.save(image);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Image>> getImage(@PathVariable("id") Long idImage) throws IOException {
        Optional<Image> image = imageRepository.findById(idImage);
        if (image.isPresent()) {
            return ResponseEntity.ok(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable long id) {

        if (imageRepository.existsById(id)) {
            imageRepository.deleteById(id);
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @GetMapping("/index")
    public ResponseEntity<List<Image>> getAll() throws IOException {
        List<Image> images = imageRepository.findAll();
        return ResponseEntity.ok(images);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateImage(@PathVariable Long id,
                                         @RequestBody ImageRequest body) {

        Image db = imageRepository.findById(id).orElse(null);
        if (db == null) return ResponseEntity.notFound().build();

        if (body.getImage() != null && body.getImage().length > 0) {
            db.setImage(body.getImage());
        }

        if (body.getType() != null && !body.getType().trim().isEmpty()) {
            db.setType(body.getType().trim());
        }

        if (body.getPatientId() != null) {
            db.setPatient(entityManager.getReference(Patient.class, body.getPatientId()));
        }

        return ResponseEntity.ok(imageRepository.save(db));
    }

    @GetMapping("/patientId/{patientId}")
    public ResponseEntity<List<Image>> findByPatientId(@PathVariable("patientId") Long patientId) {

        List<Image> images = imageRepository.findByPatientId(patientId);

        if (images.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Image>> findByType(@PathVariable("type") String type) {

        List<Image> images = imageRepository.findByType(type);

        if (images.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(images);
    }
}