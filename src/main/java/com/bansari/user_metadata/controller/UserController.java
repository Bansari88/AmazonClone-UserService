package com.bansari.user_metadata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bansari.user_metadata.auth.AuthenticationRequest;
import com.bansari.user_metadata.auth.AuthenticationResponse;
import com.bansari.user_metadata.model.User;
import com.bansari.user_metadata.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/amazon/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/createuser")
	public ResponseEntity<User> createUserAccount(@RequestBody User user) {
		User userResponse = userService.createUserAccount(user);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PostMapping("/verifyuser")
	public ResponseEntity<User> verifyUserAccount(@RequestBody User user) {
		User userResponse = userService.verifyUserAccount(user);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(userService.authenticate(request));
	}
}
