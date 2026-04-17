package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/stockBox")
public class StockBoxController {

    @PersistenceContext
    private EntityManager entityManager;

    private StockBoxRepository stockBoxRepository;
    private UtensilRepository utensilRepository;
    private BoxRepository boxRepository;

    public StockBoxController(
            StockBoxRepository stockBoxRepository,
            UtensilRepository utensilRepository,
            BoxRepository boxRepository
    ) {
        this.stockBoxRepository = stockBoxRepository;
        this.utensilRepository = utensilRepository;
        this.boxRepository = boxRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<StockBox>> getStockBox(@PathVariable("id") Long idStockBox)
            throws IOException {
        Optional<StockBox> stockBox = stockBoxRepository.findById(idStockBox);
        if (stockBox.isPresent()) {
            return ResponseEntity.ok(stockBox);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/index")
    public ResponseEntity<List<StockBox>> getAll() throws IOException {
        List<StockBox> stockBoxes = stockBoxRepository.findAll();
        return ResponseEntity.ok(stockBoxes);
    }

    @GetMapping("/byUtensil/{utensilId}")
    public ResponseEntity<List<StockBox>> getByUtensil(@PathVariable("utensilId") Long utensilId)
            throws IOException {
        List<StockBox> stockBoxes = stockBoxRepository.findByUtensilId(utensilId);
        return ResponseEntity.ok(stockBoxes);
    }

    @GetMapping("/byBox/{boxId}")
    public ResponseEntity<List<StockBox>> getByBox(@PathVariable("boxId") Long boxId)
            throws IOException {
        List<StockBox> stockBoxes = stockBoxRepository.findByBoxId(boxId);
        return ResponseEntity.ok(stockBoxes);
    }

    @GetMapping("/byStocked/{stocked}")
    public ResponseEntity<List<StockBox>> getByStocked(@PathVariable("stocked") boolean stocked)
            throws IOException {
        List<StockBox> stockBoxes = stockBoxRepository.findByStocked(stocked);
        return ResponseEntity.ok(stockBoxes);
    }

    @GetMapping("/byDay/{day}")
    public ResponseEntity<List<StockBox>> getByDay(@PathVariable("day") LocalDate day)
            throws IOException {
        List<StockBox> stockBoxes = stockBoxRepository.findByDay(day);
        return ResponseEntity.ok(stockBoxes);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createStockBox(@RequestBody StockBoxRequest req) {
        try {
            if (req == null ||
                req.getUtensilId() == null ||
                req.getBoxId() == null) {
                return ResponseEntity.badRequest().body("Faltan campos obligatorios");
            }

            Utensil utensil = utensilRepository.findById(req.getUtensilId()).orElse(null);
            Box box = boxRepository.findById(req.getBoxId()).orElse(null);

            if (utensil == null) return ResponseEntity.badRequest().body("utensilId no existe");
            if (box == null) return ResponseEntity.badRequest().body("boxId no existe");

            // Check if StockBox already exists for this utensil, box, and day
            Optional<StockBox> existingStockBox = stockBoxRepository.findByUtensilIdAndBoxIdAndDay(
                    req.getUtensilId(),
                    req.getBoxId(),
                    req.getDay()
            );

            StockBox stockBox;
            if (existingStockBox.isPresent()) {
                // If exists, add quantity to existing
                stockBox = existingStockBox.get();
                stockBox.setQuantity(stockBox.getQuantity() + req.getQuantity());
                stockBox.setStocked(req.isStocked());
                if (req.getDay() != null) {
                    stockBox.setDay(req.getDay());
                }
            } else {
                // If doesn't exist, create new
                stockBox = new StockBox();
                stockBox.setUtensil(utensil);
                stockBox.setBox(box);
                stockBox.setQuantity(req.getQuantity());
                stockBox.setStocked(req.isStocked());
                stockBox.setDay(req.getDay());
            }

            StockBox savedStockBox = stockBoxRepository.save(stockBox);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStockBox);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el StockBox: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStockBox(@PathVariable("id") Long id, @RequestBody StockBoxRequest req) {
        try {
            Optional<StockBox> existingStockBox = stockBoxRepository.findById(id);
            if (!existingStockBox.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            StockBox stockBox = existingStockBox.get();

            if (req.getQuantity() > 0) {
                stockBox.setQuantity(req.getQuantity());
            }

            stockBox.setStocked(req.isStocked());
            
            if (req.getDay() != null) {
                stockBox.setDay(req.getDay());
            }

            StockBox updatedStockBox = stockBoxRepository.save(stockBox);
            return ResponseEntity.ok(updatedStockBox);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el StockBox: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStockBox(@PathVariable("id") Long id) {
        try {
            Optional<StockBox> stockBox = stockBoxRepository.findById(id);
            if (!stockBox.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            stockBoxRepository.deleteById(id);
            return ResponseEntity.ok("StockBox eliminado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el StockBox: " + e.getMessage());
        }
    }

    @DeleteMapping("/byDay/{day}")
    public ResponseEntity<?> deleteByDay(@PathVariable("day") LocalDate day) {
        try {
            List<StockBox> stockBoxes = stockBoxRepository.findByDay(day);
            if (stockBoxes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            stockBoxRepository.deleteByDay(day);
            return ResponseEntity.ok("Se eliminaron " + stockBoxes.size() + " StockBox(es) con la fecha especificada");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar los StockBox por fecha: " + e.getMessage());
        }
    }

    @PutMapping("/markStocked/{boxId}/{day}")
    public ResponseEntity<?> markStockedByBoxAndDay(
            @PathVariable("boxId") Long boxId,
            @PathVariable("day") LocalDate day) {
        try {
            List<StockBox> stockBoxes = stockBoxRepository.findByBoxIdAndDay(boxId, day);
            
            if (stockBoxes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron StockBox para el box " + boxId + " en la fecha " + day);
            }

            int updatedCount = 0;
            for (StockBox stockBox : stockBoxes) {
                stockBox.setStocked(true);
                stockBoxRepository.save(stockBox);
                updatedCount++;
            }

            return ResponseEntity.ok("Se marcaron como stocked " + updatedCount + " StockBox(es)");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al marcar como stocked: " + e.getMessage());
        }
    }

    @PostMapping("/reduce")
    public ResponseEntity<?> reduceStockForBox(@RequestBody StockReductionRequest request) {
        try {
            LocalDate date = LocalDate.parse(request.getDate());
            
            for (ItemReductionRequest item : request.getItems()) {
                // Find all StockBox entries for this box and date
                List<StockBox> stockBoxes = stockBoxRepository.findByBoxIdAndDay(
                    request.getBoxId(), 
                    date
                );
                
                for (StockBox stockBox : stockBoxes) {
                    // Check if this is the utensil we want to reduce
                    if (stockBox.getUtensil().getId().equals(item.getUtensilId())) {
                        // Reduce the quantity
                        int newQuantity = stockBox.getQuantity() - item.getQuantity();
                        if (newQuantity <= 0) {
                            // Delete if quantity becomes 0 or negative
                            stockBoxRepository.delete(stockBox);
                        } else {
                            // Update with new quantity
                            stockBox.setQuantity(newQuantity);
                            stockBoxRepository.save(stockBox);
                        }
                        break;
                    }
                }
            }
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error reducing stock: " + e.getMessage());
        }
    }

}