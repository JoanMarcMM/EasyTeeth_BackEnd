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

}