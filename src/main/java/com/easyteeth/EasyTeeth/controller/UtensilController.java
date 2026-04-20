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
@RequestMapping("/utensil")
public class UtensilController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UtensilRepository utensilRepository;

	@Autowired
	private SupplierRepository supplierRepository;

	
	public UtensilController(
			UtensilRepository utensilRepository,
			SupplierRepository supplierRepository
    ) {
        this.utensilRepository = utensilRepository;
        this.supplierRepository = supplierRepository;
    }

	@PostMapping("/new")
	public ResponseEntity<?> createUtensil(@RequestBody UtensilRequest req) {
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

		Utensil utensil = new Utensil();
		utensil.setName(req.getName().trim());
		utensil.setBrand(req.getBrand().trim());
		utensil.setModel(req.getModel().trim());
		utensil.setPrice(req.getPrice());
		utensil.setSupplier(supplier);

		Utensil saved = utensilRepository.save(utensil);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Utensil>> getUtensil(@PathVariable("id") Long idUtensil)
			throws IOException {
		Optional<Utensil> utensil = utensilRepository.findById(idUtensil);
		if (utensil.isPresent()) {
			return ResponseEntity.ok(utensil);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Utensil>> getAll() throws IOException {
		List<Utensil> utensils = utensilRepository.findAll();
		return ResponseEntity.ok(utensils);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUtensil(
			@PathVariable("id") Long idUtensil,
			@RequestBody UtensilRequest req
	) {
		Optional<Utensil> optionalUtensil = utensilRepository.findById(idUtensil);
		if (optionalUtensil.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Utensil utensilDB = optionalUtensil.get();
		List<String> errors = new ArrayList<>();

		if (req.getName() != null) {
			String name = req.getName().trim();
			if (!name.isEmpty()) {
				utensilDB.setName(name);
			} else {
				errors.add("name no puede estar vacío");
			}
		}

		if (req.getBrand() != null) {
			String brand = req.getBrand().trim();
			if (!brand.isEmpty()) {
				utensilDB.setBrand(brand);
			} else {
				errors.add("brand no puede estar vacío");
			}
		}

		if (req.getModel() != null) {
			String model = req.getModel().trim();
			if (!model.isEmpty()) {
				utensilDB.setModel(model);
			} else {
				errors.add("model no puede estar vacío");
			}
		}

		if (req.getPrice() > 0) {
			utensilDB.setPrice(req.getPrice());
		} else if (req.getPrice() == 0) {
			errors.add("price debe ser mayor a 0");
		}

		if (req.getSupplierId() != null) {
			Supplier supplier = supplierRepository.findById(req.getSupplierId()).orElse(null);
			if (supplier == null) {
				errors.add("No existe supplier con id " + req.getSupplierId());
			} else {
				utensilDB.setSupplier(supplier);
			}
		}

		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		Utensil updated = utensilRepository.save(utensilDB);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUtensil(@PathVariable("id") Long idUtensil) {
		try {
			if (!utensilRepository.existsById(idUtensil)) {
				return ResponseEntity.notFound().build();
			}

			utensilRepository.deleteById(idUtensil);
			return ResponseEntity.ok("Utensil eliminado correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar el utensil: " + e.getMessage());
		}
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Utensil>> findByName(@PathVariable("name") String name) {
		List<Utensil> utensils = utensilRepository.findByNameContainingIgnoreCase(name);

		if (utensils.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(utensils);
		}
	}

	@GetMapping("/brand/{brand}")
	public ResponseEntity<List<Utensil>> findByBrand(@PathVariable("brand") String brand) {
		List<Utensil> utensils = utensilRepository.findByBrand(brand);

		if (utensils.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(utensils);
		}
	}

	@GetMapping("/supplier/{supplierId}")
	public ResponseEntity<List<Utensil>> findBySupplier(@PathVariable("supplierId") Long supplierId) {
		List<Utensil> utensils = utensilRepository.findBySupplierId(supplierId);

		if (utensils.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(utensils);
		}
	}

	@GetMapping("/nameAndModel/{name}/{model}")
	public ResponseEntity<Optional<Utensil>> findByNameAndModel(
			@PathVariable("name") String name,
			@PathVariable("model") String model
	) {
		Optional<Utensil> utensil = utensilRepository.findByNameAndModel(name, model);

		if (utensil.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(utensil);
		}
	}

}