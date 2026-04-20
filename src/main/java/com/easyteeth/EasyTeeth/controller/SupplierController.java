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
@RequestMapping("/supplier")
public class SupplierController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SupplierRepository supplierRepository;

	
	public SupplierController(
			SupplierRepository supplierRepository
    ) {
        this.supplierRepository = supplierRepository;
    }

	@PostMapping("/new")
	public ResponseEntity<?> createSupplier(@RequestBody SupplierRequest req) {
		List<String> errors = new ArrayList<>();

		if (req == null) {
			errors.add("Body vacío");
		} else {
			if (req.getName() == null || req.getName().isBlank()) {
				errors.add("name es obligatorio");
			}
			if (req.getEmail() == null || req.getEmail().isBlank()) {
				errors.add("email es obligatorio");
			}
			if (req.getPhone() <= 0) {
				errors.add("phone debe ser mayor a 0");
			}
		}

		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		Supplier supplier = new Supplier();
		supplier.setName(req.getName().trim());
		supplier.setEmail(req.getEmail().trim());
		supplier.setPhone(req.getPhone());

		Supplier saved = supplierRepository.save(supplier);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Supplier>> getSupplier(@PathVariable("id") Long idSupplier)
			throws IOException {
		Optional<Supplier> supplier = supplierRepository.findById(idSupplier);
		if (supplier.isPresent()) {
			return ResponseEntity.ok(supplier);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/index")
	public ResponseEntity<List<Supplier>> getAll() throws IOException {
		List<Supplier> suppliers = supplierRepository.findAll();
		return ResponseEntity.ok(suppliers);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateSupplier(
			@PathVariable("id") Long idSupplier,
			@RequestBody SupplierRequest req
	) {
		Optional<Supplier> optionalSupplier = supplierRepository.findById(idSupplier);
		if (optionalSupplier.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Supplier supplierDB = optionalSupplier.get();
		List<String> errors = new ArrayList<>();

		if (req.getName() != null) {
			String name = req.getName().trim();
			if (!name.isEmpty()) {
				supplierDB.setName(name);
			} else {
				errors.add("name no puede estar vacío");
			}
		}

		if (req.getEmail() != null) {
			String email = req.getEmail().trim();
			if (!email.isEmpty()) {
				supplierDB.setEmail(email);
			} else {
				errors.add("email no puede estar vacío");
			}
		}

		if (req.getPhone() > 0) {
			supplierDB.setPhone(req.getPhone());
		} else if (req.getPhone() == 0) {
			errors.add("phone debe ser mayor a 0");
		}

		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		Supplier updated = supplierRepository.save(supplierDB);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable("id") Long idSupplier) {
		try {
			if (!supplierRepository.existsById(idSupplier)) {
				return ResponseEntity.notFound().build();
			}

			supplierRepository.deleteById(idSupplier);
			return ResponseEntity.ok("Supplier eliminado correctamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar el supplier: " + e.getMessage());
		}
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Supplier>> findByName(@PathVariable("name") String name) {
		List<Supplier> suppliers = supplierRepository.findByNameContainingIgnoreCase(name);

		if (suppliers.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(suppliers);
		}
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<List<Supplier>> findByEmail(@PathVariable("email") String email) {
		List<Supplier> suppliers = supplierRepository.findByEmail(email);

		if (suppliers.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(suppliers);
		}
	}

}