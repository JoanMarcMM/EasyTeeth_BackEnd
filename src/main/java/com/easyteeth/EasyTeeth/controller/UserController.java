package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.io.IOException;
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


@RestController
@RequestMapping("/user")

public class UserController {

	@Autowired
	private UserRepository userRepository;

	
	@PostMapping(value="/login",consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> login(@RequestBody User user) throws IllegalArgumentException, IOException {

		List<User> list = userRepository.findAll();
		ArrayList<User> users = new ArrayList<User>(list);

		boolean loggedin = false;
		User userToGiveBack = new User();

		for (User user2 : users) {
			if (user2.getUsername().equals(user.getUsername()) && user2.getPassword().equals(user.getPassword())) {
				loggedin = true;
				userToGiveBack=user2;
				break;
			}
		}

		if (loggedin) {
			return ResponseEntity.ok(userToGiveBack);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping(value="/create", consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		// Validate that username and password aren't empty
		if (user.getUsername() == null || user.getUsername().isEmpty() ||
			user.getPassword() == null || user.getPassword().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		try {
			User savedUser = userRepository.save(user);
			return ResponseEntity.ok(savedUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Username might already exist
		}
	}

	@GetMapping(value="/all", produces = "application/json")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = userRepository.findAll();
			return ResponseEntity.ok(users);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(value="/{id}", produces = "application/json")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				return ResponseEntity.ok(user.get());
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PutMapping(value="/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				User existingUser = user.get();
				
				if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
					existingUser.setUsername(userDetails.getUsername());
				}
				if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
					existingUser.setPassword(userDetails.getPassword());
				}
				
				User updatedUser = userRepository.save(existingUser);
				return ResponseEntity.ok(updatedUser);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				userRepository.deleteById(id);
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@PutMapping("/{id}/password")
	public ResponseEntity<?> changePassword(
	    @PathVariable Long id,
	    @RequestBody ChangePasswordRequest request
	) {
	    try {
	        // 1. Find user by ID
	        User user = userRepository.findById(id).orElseThrow(() -> 
	            new RuntimeException("Usuario no encontrado"));
	        
	        // 2. Verify old password matches
	        if (!user.getPassword().equals(request.getOldPassword())) {
	            return ResponseEntity.status(401).body("La contraseña actual no es correcta");
	        }
	        
	        // 3. Update password
	        user.setPassword(request.getNewPassword());
	        userRepository.save(user);
	        
	        return ResponseEntity.ok("Contraseña cambiada correctamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("Error al cambiar contraseña: " + e.getMessage());
	    }
	}

}