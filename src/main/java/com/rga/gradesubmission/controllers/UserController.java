package com.rga.gradesubmission.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rga.gradesubmission.domain.documents.User;
import com.rga.gradesubmission.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	public static final String REGISTER_PATH = "/register";

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable String id) {
		return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<User>> findAll() {
		return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
	}

	@PostMapping(REGISTER_PATH)
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		this.userService.saveUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
