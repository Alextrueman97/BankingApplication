package com.bankingApp.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.app.models.UserAccount;
import com.bankingApp.app.service.UserAccountService;

@RestController
@RequestMapping("/userAccount")
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerAccount(
			 @RequestParam("username") String username,
	            @RequestParam("email") String email,
	            @RequestParam("password") String password,
	            @RequestParam("confirmPassword") String confirmPassword,
	            @RequestParam("firstName") String firstName,
	            @RequestParam("lastName") String lastName) {
		if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            return ResponseEntity.badRequest().body("All fields are required");
        }
		if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }
		UserAccount ua = new UserAccount(username, email, password, firstName, lastName);
        try {
            UserAccount savedInfo = userAccountService.register(ua);
            return ResponseEntity.ok().body("Registration successful");
        } catch(DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body("Account Not Created Please try again!");
        }
	}
}
