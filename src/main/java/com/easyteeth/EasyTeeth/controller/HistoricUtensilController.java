package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.HistoricUtensil;
import com.easyteeth.EasyTeeth.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historicUtensil")
public class HistoricUtensilController {

    @Autowired
    private HistoricUtensilRepository historicUtensilRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @PostMapping("/new")
    public ResponseEntity<?> createHistoricUtensil(@RequestBody HistoricUtensilRequest req) {
        List<String> errors = new ArrayList<>();

        if (req == null) {
            errors.add("Body vacío");
        } else {
            if (req.getName() == null || req.getName().isBlank()) {
                errors.add("name es obligatorio");
            }
            if (req.getBrand() == null || req.getBrand().isBlank()) {
                errors.add("brand es obligatorio");
            }
            if (req.getModel() == null || req.getModel().isBlank()) {
                errors.add("model es obligatorio");
            }
            if (req.getPrice() < 0) {
                errors.add("price debe ser mayor a 0");
            }
            if (req.getSupplierId() == null) {
                errors.add("supplierId es obligatorio");
            }
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Supplier supplier = supplierRepository.findById(req.getSupplierId()).orElse(null);
        if (supplier == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(List.of("No existe supplier con id " + req.getSupplierId()));
        }

        HistoricUtensil historicUtensil = new HistoricUtensil();
        historicUtensil.setName(req.getName().trim());
        historicUtensil.setBrand(req.getBrand().trim());
        historicUtensil.setModel(req.getModel().trim());
        historicUtensil.setPrice(req.getPrice());
        historicUtensil.setSupplier(supplier);
        
        if (req.getDateAdded() != null) {
            historicUtensil.setDateAdded(req.getDateAdded());
        } else {
            historicUtensil.setDateAdded(LocalDateTime.now());
        }

        HistoricUtensil saved = historicUtensilRepository.save(historicUtensil);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<HistoricUtensil>> getHistoricUtensil(@PathVariable("id") Long idHistoricUtensil) throws IOException {
        Optional<HistoricUtensil> historicUtensil = historicUtensilRepository.findById(idHistoricUtensil);
        if (historicUtensil.isPresent()) {
            return ResponseEntity.ok(historicUtensil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/index")
    public ResponseEntity<List<HistoricUtensil>> getAll() throws IOException {
        List<HistoricUtensil> historicUtensils = historicUtensilRepository.findAll();
        return ResponseEntity.ok(historicUtensils);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHistoricUtensil(
            @PathVariable("id") Long idHistoricUtensil,
            @RequestBody HistoricUtensilRequest req
    ) {
        Optional<HistoricUtensil> optionalHistoricUtensil = historicUtensilRepository.findById(idHistoricUtensil);
        if (optionalHistoricUtensil.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        HistoricUtensil historicUtensilDB = optionalHistoricUtensil.get();
        List<String> errors = new ArrayList<>();

        if (req.getName() != null) {
            String name = req.getName().trim();
            if (!name.isEmpty()) {
                historicUtensilDB.setName(name);
            } else {
                errors.add("name no puede estar vacío");
            }
        }

        if (req.getBrand() != null) {
            String brand = req.getBrand().trim();
            if (!brand.isEmpty()) {
                historicUtensilDB.setBrand(brand);
            } else {
                errors.add("brand no puede estar vacío");
            }
        }

        if (req.getModel() != null) {
            String model = req.getModel().trim();
            if (!model.isEmpty()) {
                historicUtensilDB.setModel(model);
            } else {
                errors.add("model no puede estar vacío");
            }
        }

        if (req.getPrice() > 0) {
            historicUtensilDB.setPrice(req.getPrice());
        } else if (req.getPrice() == 0) {
            errors.add("price debe ser mayor a 0");
        }

        if (req.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(req.getSupplierId()).orElse(null);
            if (supplier == null) {
                errors.add("No existe supplier con id " + req.getSupplierId());
            } else {
                historicUtensilDB.setSupplier(supplier);
            }
        }

        if (req.getDateAdded() != null) {
            historicUtensilDB.setDateAdded(req.getDateAdded());
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        HistoricUtensil updated = historicUtensilRepository.save(historicUtensilDB);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistoricUtensil(@PathVariable("id") Long idHistoricUtensil) {
        try {
            if (!historicUtensilRepository.existsById(idHistoricUtensil)) {
                return ResponseEntity.notFound().build();
            }

            historicUtensilRepository.deleteById(idHistoricUtensil);
            return ResponseEntity.ok("HistoricUtensil eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el HistoricUtensil: " + e.getMessage());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<HistoricUtensil>> findByName(@PathVariable("name") String name) {
        List<HistoricUtensil> historicUtensils = historicUtensilRepository.findByNameContainingIgnoreCase(name);

        if (historicUtensils.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(historicUtensils);
        }
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<HistoricUtensil>> findByBrand(@PathVariable("brand") String brand) {
        List<HistoricUtensil> historicUtensils = historicUtensilRepository.findByBrand(brand);

        if (historicUtensils.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(historicUtensils);
        }
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<HistoricUtensil>> findBySupplier(@PathVariable("supplierId") Long supplierId) {
        List<HistoricUtensil> historicUtensils = historicUtensilRepository.findBySupplierId(supplierId);

        if (historicUtensils.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(historicUtensils);
        }
    }

    @GetMapping("/nameAndModel/{name}/{model}")
    public ResponseEntity<Optional<HistoricUtensil>> findByNameAndModel(
            @PathVariable("name") String name,
            @PathVariable("model") String model
    ) {
        Optional<HistoricUtensil> historicUtensil = historicUtensilRepository.findByNameAndModel(name, model);

        if (historicUtensil.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(historicUtensil);
        }
    }
}
