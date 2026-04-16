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
@RequestMapping("/box")
public class BoxController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private BoxRepository boxRepository;

    @Autowired
    private StockBoxRepository stockBoxRepository;

	
	public BoxController(
			BoxRepository boxRepository
    ) {
        this.boxRepository = boxRepository;
    }

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Box>> getBox(@PathVariable("id") Long idBox)
			throws IOException {
		Optional<Box> box = boxRepository.findById(idBox);
		if (box.isPresent()) {
			return ResponseEntity.ok(box);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Box>> getAll() throws IOException {
		List<Box> boxes = boxRepository.findAll();
		return ResponseEntity.ok(boxes);
	}
	
	@PostMapping("/{id}/assign-materials")
    public ResponseEntity<String> assignMaterials(
            @PathVariable("id") Long id, 
            @RequestParam("date") String date) {
        try {
            LocalDate fechaProgramada = LocalDate.parse(date);
            List<StockBox> materialesDelBox = stockBoxRepository.findByBoxId(id);

            if (materialesDelBox.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No hay utensilios asignados a este box para programar.");
            }

            for (StockBox item : materialesDelBox) {
                item.setDay(fechaProgramada);
                item.setStocked(false); 
                stockBoxRepository.save(item);
            }

            return ResponseEntity.ok("Éxito: " + materialesDelBox.size() + " materiales programados.");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la asignación: " + e.getMessage());
            
        }
    }
	@GetMapping("/{id}/materials")
	public ResponseEntity<List<StockBox>> getMaterialsByDay(
	        @PathVariable("id") Long id, 
	        @RequestParam("date") String date) {
	    try {
	        LocalDate fechaSolicitada = LocalDate.parse(date);
	        List<StockBox> materiales = stockBoxRepository.findByBoxIdAndDay(id, fechaSolicitada);
	        return ResponseEntity.ok(materiales);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	@PostMapping("/{id}/update-status")
	public ResponseEntity<String> updateStockStatus(
	        @PathVariable("id") Long id, 
	        @RequestParam("date") String date,
	        @RequestParam("status") boolean status) { 
	    try {
	        LocalDate fecha = LocalDate.parse(date);
	        List<StockBox> materiales = stockBoxRepository.findByBoxIdAndDay(id, fecha);
	        
	        if (materiales.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay materiales para este día");
	        }

	        for (StockBox material : materiales) {
	            material.setStocked(status);
	        }
	        
	        stockBoxRepository.saveAll(materiales);
	        return ResponseEntity.ok("Estado actualizado correctamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar");
	    }
	}
	
}

