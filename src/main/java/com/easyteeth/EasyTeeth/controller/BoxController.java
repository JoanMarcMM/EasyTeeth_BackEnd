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
            BoxRepository boxRepository,
            StockBoxRepository stockBoxRepository
    ) {
        this.boxRepository = boxRepository;
        this.stockBoxRepository = stockBoxRepository;
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

    @GetMapping("/{id}/materials")
    public ResponseEntity<List<StockBox>> getMaterialsByDay(
            @PathVariable("id") Long boxId,
            @RequestParam String date) throws IOException {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<StockBox> materials = stockBoxRepository.findByBoxIdAndDay(boxId, localDate);
            return ResponseEntity.ok(materials);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/update-status")
    public ResponseEntity<?> updateStockStatus(
            @PathVariable("id") Long boxId,
            @RequestParam String date,
            @RequestParam Boolean status) throws IOException {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<StockBox> stockBoxes = stockBoxRepository.findByBoxIdAndDay(boxId, localDate);
            
            for (StockBox stockBox : stockBoxes) {
                stockBox.setStocked(status);
                stockBoxRepository.save(stockBox);
            }
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}